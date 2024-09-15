#### 1. Описание решения и сервисов
- реализованы сервисы:
1. *device-management-api* - сервис для получения запросов от пользователей из различных каналов (web-ui, mobile app). Задача этого сервиса - обрабатывать запросы от клиентов. Общая схема работы следующая:
   1. получить запрос от клиента;
   2. отправить сообщение на *device-api-adapter*. При этом запоминается *messageId* отправленного сообщения;
   3. в ответ клиенту отправляется *messageId*. С помощью полученного *messageId* клиент будет снова обращаться на *device-management-api* и проверять не пришел ли ответ на его сообщение. Когда ответ на сообщение придет, то он сможет его получить по *messageId*;

2. *smart-home-monolith* - это монолит, который предполагается заменить. Для него настроена публикация сообщений в *kafka*. Эти сообщения считываются сервисом *device-api-adapter*;

3. *device-api-adapter* - это сервис, который получает сообщения от *device-management-api* и *smart-home-monolith*. Его задачи:
   1. получить сообщения из *kafka*;
   2. преобразовать полученное сообщение в *api*, которое совместимо с соответствующими устройствами умного дома (преобразовать сообщение в некий *device-api*);
   3. отправить *REST*-запрос на соответствующее устройство;
   4. после получения ответа на запрос отправить результат в *kafka*;
   5. этот результат далее считывается сервисами, чтобы отдать передать его пользователю;

#### 2. Допущения в учебных целях
- отсутствуют ответы на действия (*actions*), отправленные из сервиса *device-management-api*. То есть, когда *device-management-api* отправляет сообщение на *device-api-adapter*, то выглядит так, как будто *device-api-adapter* просто отправляет *REST*-запрос на соответствующее устройство, чтобы оно выполнило действие. После этого *device-api-adapter* не отдает никаких ответов;
- интеграция *smart-home-monolith* с *kafka* приведена для примера для самого простого действия. Детали опущены;
- не реализован кеш для телеметрии;

#### 3. Как запустить проект
#### 3.1. Docker compose
1. в корне папки task2 лежит docker-compose.yml. Первым этапом запускается он:

```sh
cd ./task2

docker compose up
```

- это действие запускает:
1. сервисы:
   1. *device-management-api*
   2. *device-api-adapter*
   3. *smart-home-monolith* (модифицированный - который умеет отправлять сообщения в *kafka*, а не тот, который дан в исходных данных к заданию);

2. *API Gateway* - *Kong*:
   1. `localhost:8000` - порт для запросов на *route*'ы
   2. `localhost:8001` - порт для административных запросов в *Kong*
   3. `localhost:8002` - *GUI* для *Kong*;

3. *Kafka*:
   1. `localhost:9092` - внешний (*EXTERNAL*) *url* *kafka*
   2. `kafka-service:9094` - внутренний (*INTERNAL*) *url* *kafka* - для взаимодействия внутри *docker compose*;
   3. `localhost:9090` - *GUI* для *Kafka*;

4. БД:
   1. postgresql - для *device-management-api*;
   2. postgresql - для *smart-home-monolith*;
   3. postgresql - для *kong*;
   4. redis - для *device-management-api*;

#### 3.2. Задание services и routes для *Kong API Gateway*
- в той же папке task лежит скрипт `gateway_init.sh`. При его запуске в *Kong API Gateway* создаются:
1. два *service*'а (имеется ввиду *service* - как сущность *Kong API Gateway* - https://docs.konghq.com/gateway/latest/key-concepts/services/):
   1. device-management-api
   2. smart-home-monolith

2. для каждого созданного сервиса создается по одному *route*'у (https://docs.konghq.com/gateway/latest/key-concepts/routes/):
   1. service: device-management-api -> route: device-management
   2. service: smart-home-monolith -> route: smart-home-monolith-route

3. запуск скрипта:
```sh
./gateway_init.sh
```

#### 4. Как отправить запросы/посмотреть промежуточные результаты/понять response
#### 4.1. Запрос на регистрацию нового устройства через *device-management-api*
1. Отправляем запрос на *gateway* для регистрации нового устройства:
```sh
curl -XPOST -H "Content-type: application/json" -d '{
    "accountId": 1,
    "serialNumber": "serNum1"
}' 'http://localhost:8000/device-management/device/register/init'
```

2. получаем ответ:
```json
{
    "accountId": 1,
    "serialNumber": "serNum1",
    "messageId": "428ac101-173f-4f0a-99ff-5adb3135b7ed"
}
```

3. отправляем повторный запрос, используя полученный *messageId*:
```sh
curl -XGET -H "Content-type: application/json" 'http://localhost:8000/device-management/device/register/data/428ac101-173f-4f0a-99ff-5adb3135b7ed'
```

4. Полученный ответ - означает, что новое устройство было успешно зарегистрировано на пользователя *accountId*:
```json
{
  "status": "SUCCESS",
  "accountId": 1,
  "serialNumber": "serNum1",
  "deviceName": "device1",
  "description": "description for device1",
  "deviceType": "LIGHTING_DEVICE",
  "deviceActions": [
    {
      "action": "POWER",
      "value": [
        "TURN_ON",
        "TURN_OFF"
      ]
    }
  ]
}
```

- используя *kafka-ui* можно увидеть сообщения, которыми обменялись *device-management-api* и *device-api-adapter*;

#### 4.2. Запрос на получение телеметрии для устройства
1. отправляем запрос на *gateway* для получения всей истории телеметрии для устройства:
```sh
curl -XPOST -H "Content-type: application/json" -d '{
    "accountId": 1,
    "serialNumber": "serNum1"
}' 'http://localhost:8000/device-management/device/telemetry/request/all'
```
2. ответ:
```json
{
    "accountId": 1,
    "serialNumber": "serNum1",
    "messageId": "3ff2f534-9b1a-41ba-bd7e-aebab765282a"
}
```

3. запрос для получения результата по *messageId*:
```sh
curl -XGET -H "Content-type: application/json" 'http://localhost:8000/device-management/device/telemetry/data/3ff2f534-9b1a-41ba-bd7e-aebab765282a'
```

4. ответ - получили всю телеметрию:
```json
{
    "status": "SUCCESS",
    "accountId": 1,
    "serialNumber": "serNum1",
    "updatedAt": "2024-09-15T13:38:21.08037755",
    "data": "All telemetry"
}
```

- используя *kafka-ui* можно увидеть сообщения, которыми обменялись *device-management-api* и *device-api-adapter*;

#### 4.3. Запрос на выполнение действия для устройства
1. отправляем запрос на *gateway* для выполнения заданного действия (предполагается, что наше устройство отвечает за свет в доме, и мы хотим выключить свет):
```sh
curl -XPOST -H "Content-type: application/json" -d '{
    "accountId": 1,
    "serialNumber": "serNum1",
    "actionType": "LIGHT",
    "actionValue": "TURN_OFF"
}' 'http://localhost:8000/device-management/device/action'
```

2. ответ - в текущей реализации это фактически означает, что *device-management-api* опубликовал сообщение в *kafka*, *device-api-adapter* его прочитал и отправил *REST*-запрос на устройство, чтобы выполнить действие:
```json
{
    "status": "SUCCESS"
}
```

- используя *kafka-ui* можно увидеть сообщения, которыми обменялись *device-management-api* и *device-api-adapter*;
- результат действия можно посмотреть в логах *device-api-adapter* на уровне *INFO*:
```sh
2024-09-15 16:45:34 2024-09-15T13:45:34.896Z  INFO 6 --- [device-api-adapter] [ntainer#0-0-C-1] r.y.p.d.service.MockResult               : complete action: LIGHT 
2024-09-15 16:45:34 2024-09-15T13:45:34.898Z  INFO 6 --- [device-api-adapter] [ntainer#0-0-C-1] r.y.p.d.service.MockResult               : new value: TURN_OFF 
```

#### 4.4. Интеграция *smart-home-monolith* с *kafka*
1. отправляем запрос на *gateway* для выполнения действия. В качестве *route*'а указываем *smart-home-monolith-route*:
```sh
curl -XPOST -H "Content-type: application/json" 'http://localhost:8000/smart-home-monolith/api/heating/1/turn-off'
```

2. в ответе приходит код 204 - как в оригинальном *API*;
3. фактически, помимо ранее реализованных действий было отправлено сообщение в *kafka*. Это сообщение считал *device-api-adapter* и выполнил действие с устройством;

- используя *kafka-ui* можно увидеть сообщения, которыми обменялись *smart-home-monolith* и *device-api-adapter*;
- результат действия можно посмотреть в логах *device-api-adapter* на уровне *INFO*:
```sh
2024-09-15 16:51:54 2024-09-15T13:51:54.837Z  INFO 6 --- [device-api-adapter] [ntainer#0-0-C-1] r.y.p.d.service.MockResult               : complete action: POWER 
2024-09-15 16:51:54 2024-09-15T13:51:54.839Z  INFO 6 --- [device-api-adapter] [ntainer#0-0-C-1] r.y.p.d.service.MockResult               : new value: OFF 
```
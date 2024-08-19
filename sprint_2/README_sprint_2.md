### Задание 2. Онлайн-магазин

- инструкция по запуску проекта:
1. перейти в директорию `sharding-repl-cache` и запустить docker-compose:
```bash
docker compose -f compose.yaml up -d
```

2. перейти в директорию `sharding-repl-cache/scripts` и выполнить скрипты по очереди - в порядке их нумерации - от 1 до 3:
```bash
./1_cfgServer_initiate.sh
./2_shards_initiate.sh
./3_router_initiate.sh
```
- на этом этапе настроено два шарда, в каждом из которых по три реплики;

3. добавляем данные в БД, используя четвертый скрипт. В директории `sharding-repl-cache/scripts` выполняем скрипт:
```bash
./4_insert_data.sh
```

4. проверить суммарное количество данных во всех шардах:
   1. Заходим в *router*
   ```bash
   docker exec -it mongos_router mongosh --port 27020
   ```

   2. выполняем команды:
   ```mongosh
   use somedb
   db.helloDoc.countDocuments() 
   ```

5. проверить количество данных в одной из реплик шарда 1:
   1. Заходим в реплику шарда 1:
   ```sh
   docker exec -it shard11 mongosh --port 27018
   #docker exec -it shard12 mongosh --port 27018
   #docker exec -it shard13 mongosh --port 27018
   ```

   2. выполняем команды:
   ```mongosh
   use somedb
   db.helloDoc.countDocuments() 
   ```

6. проверить количество данных в одной из реплик шарда 2:
   1. Заходим в реплику шарда 2:
   ```sh
   docker exec -it shard21 mongosh --port 27018
   #docker exec -it shard22 mongosh --port 27018
   #docker exec -it shard23 mongosh --port 27018
   ```

   2. выполняем команды:
   ```mongosh
   use somedb
   db.helloDoc.countDocuments() 
   ```

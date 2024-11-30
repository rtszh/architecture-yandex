- для упрощения задачи все пользователи создаются в одном namespace. При использовании в системе нужно было бы разделить пользователей на разные namespace - в соответствии с их системами. Например:
1. Бухгалтер - namespace1
2. Клиент - namespace2
3. Собственник - namespace3
4. Менеджер - namespace2, namespace3

- создание пользователей, ролей и roleBindings:
1. для выполнения всех скриптов необходимо заранее запустить minikube;
2. создание пользователей осуществляется через выпуск клиентских сертификатов;
3. для запуска скрипта создания пользователей запустить скрипт 1_users.sh:
   - перейти в папку со скриптом 1_users и выполнить команду:
```sh
./1_users.sh
```
- в результате создаются пользователи внутри kubernetes-кластера;
4. для создания ролей необходимо запустить скрипт ./2_roles:
```sh
./2_roles.sh
```
5. для создания rolebindings необходимо запустить скрипт ./3_rolebindings:
```sh
./3_rolebindings.sh
```
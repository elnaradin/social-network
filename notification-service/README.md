# Локальный запуск приложения

Перед запуском приложения через IDE необходимо выполнить команду, которая сгенерирует необходимые исходники  
`mvn generate-sources`

Для её запуска потребуются переменные окружения, используемые в `pom.xml`. После генерации кода, рекомендуется
перезагрузить мавен проекты.

# Необходимые переменные окружения

```
SN_DB_HOST
SN_DB_PORT
SN_DB_NAME
SN_DB_USER
SN_DB_PASSWORD
SN_NOTIFICATIONS_HOST
SN_NOTIFICATIONS_PORT
SN_AGGREGATOR_HOST
SN_AGGREGATOR_PORT
SN_AGGREGATOR_PROTOCOL
SN_WEBSOCKET_HTTP_ENDPOINT

SN_SMTP_HOST
SN_SMTP_PORT
SN_SMTP_USERNAME
SN_SMTP_PASSWORD
```

# JMapper

Для работы JMapper'а необходимо добавить `VM option`:  
`--add-opens java.base/java.lang=ALL-UNNAMED`

# Создание Docker образа

#### Образ микросервиса уведомлений [тут](https://hub.docker.com/r/intouchgroup/notification-serv)

****Шаги 2, 3, 4 необязательные, если ранее уже что-то собиралось****

1. Скомпилировать в jar

   ```shell
   mvn clean package
   ```

2. Установить Docker BuildKit, если его еще нет
   ```shell
   docker buildx install
   ```

3. Создать новый конструктор
   ```shell
   docker buildx create --name mybuilder
   ```

4. Запустить конструктор
   ```shell
   docker buildx use mybuilder
   ```
5. Перейти в нужный модуль
   ```shell
   cd notification-service/main
   ```
6. Собрать образ и запушить. Для этого выполнить команду
   ```shell
   docker buildx build --platform linux/amd64 --tag intouchgroup/notification-serv:latest . --push
   ```
   где
    * `--platform linux/amd64` - хотим собрать образ для архитектуры x86_64
    * `intouchgroup` - логин на docker hub
    * `notification-serv` - желаемое имя образа
    * `--push` - можно заменить на `--load`, если нужно создать локально, а не пушить на docker hub

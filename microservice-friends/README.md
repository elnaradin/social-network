# Необходимые переменные окружения

```
SN_DB_HOST
SN_DB_NAME
SN_DB_PASSWORD
SN_DB_PORT
SN_DB_USER

SN_FRIENDS_HOST
SN_FRIENDS_PORT

SN_NOTIFICATION_EVENT
SN_KAFKA_HOST
SN_KAFKA_PORT
```

# Создание Docker образа

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
   cd friends-service
   ```
6. Собрать образ и запушить. Для этого выполнить команду
   ```shell
   docker buildx build --platform linux/amd64 --tag intouchgroup/friends-serv:latest . --push
   ```
   где
   * `--platform linux/amd64` - хотим собрать образ для архитектуры x86_64
   * `intouchgroup` - логин на docker hub
   * `friends-serv` - желаемое имя образа
   * `--push` - можно заменить на `--load`, если нужно создать локально, а не пушить на docker hub

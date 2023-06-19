# Docker образ

Докер образ создан по гайду из `notification-service`. Сам образ
находится [тут](https://hub.docker.com/repository/docker/intouchgroup/aggregator/general)

# Необходимые переменные окружения

```
SN_POST_HOST
SN_POST_PORT
SN_ACCOUNT_HOST
SN_ACCOUNT_PORT
SN_AGGREGATOR_PORT
SN_FRIENDS_HOST
SN_FRIENDS_PORT
SN_NOTIFICATIONS_HOST
SN_NOTIFICATIONS_PORT
SN_NOTIFICATION_SERV
SN_MESSAGE_SERV
SN_MESSAGE_EVENT
SN_MESSAGE_HOST
SN_MESSAGE_PORT
SN_STORAGE_HOST
SN_STORAGE_PORT
SN_WEBSOCKET_HTTP_ENDPOINT=ws/send
SN_PROTOCOL // http:// OR https://
JWT_SIGNATURE
SN_KAFKA_HOST
SN_KAFKA_PORT
```

# Подключение микросервиса к агрегатору

1. Добавить зависимость в api модуль своего микросервиса
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
        <version>${org.springframework.boot.version}</version>
    </dependency>
    ```
2. Указать в конфигурациях своего микросервиса уникальный порт, отличающийся от `8080`, т.к. на нем будет работать
   агрегатор. Например, для микросервиса уведомлений используется порт `8888`.
3. Создать в api модуле клиент. Клиент - это публичный интерфейс с аннотацией `@FeignClient`, где в аргументах
   указывается имя микросервиса, его домен (`url`), а также можно задать префикс для всех маршрутов микросервиса. Далее,
   в данном клиенте перечисляются все эндпоинты микросервиса, с такими же аннотациями, как в контроллерах. Можно создать
   несколько клиентов, если в микросервисе много эндпоинтов. Имена методов не играют роли, можно задать свои, но
   рекомендуется делать похожие имена методов, чтобы проще было найти метод контроллера. Пример можно
   посмотреть [тут](../notification-service/api/src/main/java/ru/itgroup/intouch/client/NotificationServiceClient.java)
4. В `pom.xml` прописать зависимости на нужный микросервис, где `{microservice}` - подключаемый сервис
   ```xml
   <dependencies>
       <!-- Другие, уже существующие зависимости-->
   
       <dependency>
           <groupId>ru.itgroup.intouch</groupId>
           <artifactId>ru.itgroup.intouch.{microservice}.api</artifactId>
           <version>1.0.0-SNAPSHOT</version>
       </dependency>
   </dependencies>
   ```
5. Создать новый контроллер в соответствующем пакете агрегатора.
6. Внедрить зависимость на клиенте (тот самый интерфейс из 3 пункта).
7. Прописать эндпоинты добавляемого микросервиса. Здесь всё аналогично обычному контроллеру, включая аннотации класса.
   Методы рекомендуется назвать также, как они называются в контроллерах микросервиса. В теле методов нужно только
   вернуть вызов соответствующего метода клиента. Также рекомендуется перенести валидацию из контроллеров микросервиса
   сюда. Пример можно
   посмотреть [тут](src/main/java/ru/itgroup/intouch/aggregator/controller/NotificationController.java).
8. Запустить через IDE (или любым другим способом) добавляемый микросервис и агрегатор.
9. Проверить работу по пути `http://localhost:8080`. Порт должен быть `8080`, т.к. здесь уже тестируется "имитация"
   эндпоинтов микросервиса.

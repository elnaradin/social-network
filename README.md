# Запуск проекта

Проект запускается при помощи `docker compose`.

1. Перед запуском убедитесь, что в вашей системе существуют все необходимые переменные среды:

   ```
   SN_DB_PASSWORD
   SN_DB_NAME
   SN_DB_USER
   SN_DB_PORT
   SN_NOTIFICATIONS_PORT
   SN_AGGREGATOR_PORT=8080
   SN_NOTIFICATIONS_PORT=8888
   ```
   Везде где указаны выше указаны значения, это рекомендуемые по умолчанию значения.  
   Переменная `SN_NOTIFICATIONS_HOST` - это хост для подключения к микросервису уведомлений внутри контейнера. При
   подключении своего микросервиса необходимо добавить хост своего микросервиса аналогичным образом. По умолчанию
   значение переменной совпадает с именем контейнера и указывается как переменная контейнеров микросервиса и агрегатора.
2. Запуск происходит командой
    ```shell
    docker compose up -d
    ```

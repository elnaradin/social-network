# Запуск проекта

Проект запускается при помощи `docker compose`.

1. Перед запуском убедитесь, что в вашей системе существуют все необходимые переменные среды:

    ```
    SN_DB_PASSWORD
    SN_DB_NAME
    SN_DB_USER
    SN_DB_PORT
    SN_NOTIFICATIONS_PORT
    ```
2. Запуск происходит командой
    ```shell
    docker compose up -d
    ```

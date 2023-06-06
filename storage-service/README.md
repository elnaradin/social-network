Данный сервис используется для загрузки фотографий на сервер Cloudinary.

POST метод на /api/v1/storage

+ обязательно приложить фото как multipart file (сохраняет фото в разрешении 720х480)
+ не обязательно объект класса Transformation, у которого есть методы для изменения параметров фото

возвращает: 
DTO {
String photoName
String photoPath
}


GET метод на /api/v1/storage

+ обязательно объект класса Transformation, у которого есть методы для изменения параметров фото
для получения ссылки с измененной фотографией (на сервере при этом фото не редактируется)

возвращет:
String photoPath


Сервис использует переменные окружения:

SN_API_KEY
SN_API_SECRET
SN_CLOUD_NAME
SN_DB_HOST
SN_DB_PORT
SN_DB_NAME
SN_DB_USERNAME
SN_DB_PASSWORD
SN_STORAGE_HOST
SN_STORAGE_PORT
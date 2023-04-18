## Модуль public-data. Порядок установки.

Модуль public-data - библиотека многомодульного проекта social-network (версия 0.1.2). Содержит общую логику проекта
(необходимые Entity и общий config для микросервисов).

Для использования необходимо залить модуль public-data в свой проект и запустить Maven install в public-data.

Далее в pom.xml проекта добавляем зависимость

```xml

<dependency>
    <groupId>ru.itgroup.intouch</groupId>
    <artifactId>ru.itgroup.intouch.public-data</artifactId>
    <version>${ver}</version>
</dependency>
```

После обновления Maven'a все переменные и классы public-data станут доступны в проекте для использования.

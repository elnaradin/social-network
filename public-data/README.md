<h4> Модуль public-data. Порядок установки.
</h4>
<div>
Модуль public-data - библиотека многомодульного проекта socialnetwork (версия 0.1.0). Содержит общую логику проекта (необходимые Entity и общий config для микросервисов).
</div>
<br>
<div>
Для использования необходимо залить модуль public-data в свой проект и запустить Maven install в public-data. 
</div>
<br>
<div>
Далее в pom.xml проекта добавляем зависимость
</div>
 < dependency> <br>
            < groupId>ru.itgroup.intouch< /groupId> <br>
            < artifactId>ru.itgroup.intouch.public-data< /artifactId> <br>
            < version>${ver}< /version> <br>
        < /dependency> <br>
<br>
После обновления Mavena все переменные и классы public-data станут доступны в проекте для использования.
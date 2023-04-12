<h4> Модуль public-data. Порядок установки.
</h4>
<div>
Модуль public-data - один из модулей многомодульного проекта socialnetwork. Содержит общую логику проекта (model и config базы данных).
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
            < version>${revision}${sha1}${changelist}< /version> <br>
        < /dependency> <br>
<br>
После обновления Mavena все переменные и классы public-data станут доступны в проекте для использования.
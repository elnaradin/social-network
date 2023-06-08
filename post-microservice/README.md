
SN_POST_PORT<br>
SN_POST_HOST<br>
SN_DB_NAME <br>
SN_DB_USER <br>
SN_DB_PASSWORD <br>
SN_DB_HOST <br>
SN_DB_PORT

для запуска контейнера на localhost<br>
docker run -e SN_POST_HOST=localhost -e SN_POST_PORT=8085 -e SN_DB_HOST=172.17.0.2 -e SN_DB_PORT=5432 -e SN_DB_PASSWORD=postgrespw -e SN_DB_NAME=postgres -e SN_DB_USER=user -d --add-host=host.docker.internal:host-gateway -p 8085:8085 intouchgroup/post-service:latest <br>

переменные заменяются на свои


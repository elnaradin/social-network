FROM amazoncorretto:17
ARG JAR_FILE=main/target/ru.itgroup.intouch.message-service.main-1.0.0-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} /app/message-service.jar
ENTRYPOINT ["java","-jar","/app/message-service.jar"]

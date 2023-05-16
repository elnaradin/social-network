FROM openjdk:19
ARG JAR_FILE=main/target/ru.itgroup.intouch.account-service.main-1.0.0-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} /app/account-service.jar
ENTRYPOINT ["java","-jar","/app/account-service.jar"]
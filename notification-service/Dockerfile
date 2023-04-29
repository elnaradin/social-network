FROM khipu/openjdk17-alpine
COPY main/target/ru.itgroup.intouch.notification-service.main-1.0.0-SNAPSHOT.jar /app/notifications-service.jar
WORKDIR /app
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "/app/notifications-service.jar"]

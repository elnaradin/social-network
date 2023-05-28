FROM khipu/openjdk17-alpine
COPY main/target/ru.itgroup.intouch.friends-service.main-1.0.1.jar /app/friends-service.jar
WORKDIR /app
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "/app/friends-service.jar"]

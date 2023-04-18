FROM khipu/openjdk17-alpine
COPY target/ru.itgroup.intouch.main-1.0.0-SNAPSHOT.jar .
WORKDIR /app
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "ru.itgroup.intouch.main-1.0.0-SNAPSHOT.jar"]

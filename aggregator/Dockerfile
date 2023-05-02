FROM khipu/openjdk17-alpine
COPY target/ru.itgroup.intouch.aggregator-1.0.0.jar /app/aggregator.jar
WORKDIR /app
CMD ["java", "-jar", "/app/aggregator.jar"]

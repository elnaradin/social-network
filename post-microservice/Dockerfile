FROM khipu/openjdk17-alpine
COPY main/target/post-service.main-1.0.0-SNAPSHOT.jar /app/post-service.jar
WORKDIR /app
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "/app/post-service.jar"]


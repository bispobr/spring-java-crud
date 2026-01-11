FROM openjdk:21-ea-1-jdk-slim

WORKDIR /app

COPY target/crud-0.0.1-SNAPSHOT.jar /app/crud.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/crud.jar"]



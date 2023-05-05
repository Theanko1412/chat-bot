FROM openjdk:17.0.1-jdk-slim

VOLUME /application

COPY build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

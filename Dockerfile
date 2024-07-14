FROM openjdk:21-slim
MAINTAINER ruslan
WORKDIR /app
COPY ./build/libs/*.jar app-file-service.jar
ENTRYPOINT ["java", "-jar", "app-file-service.jar"]

FROM openjdk:17-jdk-alpine

LABEL authors="Chaima Guezmir"

EXPOSE 8089

ADD target/gestion-station-ski-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

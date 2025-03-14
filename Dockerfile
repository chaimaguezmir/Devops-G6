# Utilisation d'une image JDK plus récente
FROM openjdk:17-jdk-alpine

LABEL authors="Anas Bettouzia"

EXPOSE 8089

ADD target/gestion-station-ski-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

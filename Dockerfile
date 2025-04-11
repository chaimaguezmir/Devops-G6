# Utilisation d'une image JDK plus récente
FROM openjdk:17-jdk-alpine

LABEL authors="Ahlem Trabelsi"

EXPOSE 8089

# Ajouter votre JAR
ADD target/gestion-station-ski-1.2.2.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
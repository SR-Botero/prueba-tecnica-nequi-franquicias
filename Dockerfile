# Se usa una imagen base con OpenJDK (Java 17, pero si usas otro puedes cambiarlo)
FROM openjdk:21-jdk-slim

# Se añade la aplicación al contenedor
COPY target/nequi-app-0.0.1-SNAPSHOT.jar app.jar

# Exposición del puerto en el que la aplicación va a correr
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]

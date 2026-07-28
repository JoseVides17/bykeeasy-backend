# Etapa 1: Compilación con Maven y Java 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecución con Java 17 JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Opciones para optimizar el uso de memoria en Render (Plan Gratuito)
ENV JAVA_TOOL_OPTIONS="-Xmx380m -Xms256m"

ENTRYPOINT ["java", "-jar", "app.jar"]
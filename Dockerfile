# Etapa 1: Compilación del JAR omitiendo los tests
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Imagen ligera para ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Restricción de memoria para no exceder los 512 MB del plan gratuito
ENV JAVA_TOOL_OPTIONS="-Xmx380m -Xms256m"

ENTRYPOINT ["java", "-jar", "app.jar"]
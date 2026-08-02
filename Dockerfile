#Etapa 1: Compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build
LABEL authors="mario"

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

#etapa de execução
FROM eclipse-temurin:21-jre
LABEL org.opencontainers.image.authors = "José Mário"
LABEL org.opencontainers.image.title="Simulador Banco Digital"
LABEL org.opencontainers.image.version="1.0"

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "app.jar"]
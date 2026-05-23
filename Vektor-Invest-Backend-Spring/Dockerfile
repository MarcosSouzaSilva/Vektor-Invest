# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-22 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package

# Etapa 2: Executar a aplicação
FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
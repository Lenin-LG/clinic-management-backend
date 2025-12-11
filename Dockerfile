# -------- Etapa 1: Build --------
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiamos pom y código
COPY pom.xml .
COPY src ./src

# Compilamos y empaquetamos
RUN mvn clean package -DskipTests

# -------- Etapa 2: Runtime --------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/target/Proyecto-Clinica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

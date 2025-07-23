# Этап сборки
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=railway
# или вместо строки выше, можно это:
# ENTRYPOINT ["java", "-Dspring.profiles.active=railway", "-jar", "app.jar"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
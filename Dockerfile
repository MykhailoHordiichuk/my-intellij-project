# 🔧 Этап сборки
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 🔧 Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /app

# копируем собранный jar из этапа сборки
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
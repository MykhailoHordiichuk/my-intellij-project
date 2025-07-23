# 🔧 Этап сборки
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 🔧 Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# ✅ Здесь ключевой момент: переменная PORT будет правильно подставлена
CMD exec java -Dspring.profiles.active=railway -Dserver.port=$PORT -jar app.jar
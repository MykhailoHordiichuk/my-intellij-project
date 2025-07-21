# 🔧 Этап сборки
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /app

# wait-for-it скрипт
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

# копируем собранный jar из этапа сборки
COPY --from=build /app/target/spring_data_rest-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--timeout=30", "--strict", "--", "java", "-jar", "app.jar"]
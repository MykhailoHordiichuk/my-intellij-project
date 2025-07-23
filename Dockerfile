FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# 🔥 ВАЖНО: говорим JVM использовать Railway порт
ENTRYPOINT ["java", "-Dspring.profiles.active=railway", "-Dserver.port=${PORT}", "-jar", "app.jar"]
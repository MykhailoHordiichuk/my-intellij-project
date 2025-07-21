# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный jar-файл в контейнер
COPY wait-for-it.sh .
COPY target/spring_data_rest-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Ждём, пока MySQL на db:3306 будет доступен, потом запускаем приложение
ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--timeout=30", "--strict", "--", "java", "-jar", "app.jar"]
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/monitoring-app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

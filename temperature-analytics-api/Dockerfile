FROM arm64v8/eclipse-temurin:17-jre

WORKDIR /app
COPY ./target/temperature-analytics-api-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "temperature-analytics-api-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080

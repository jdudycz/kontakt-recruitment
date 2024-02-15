FROM arm64v8/eclipse-temurin:17-jre

WORKDIR /app
COPY ./target/anomaly-detector-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "anomaly-detector-0.0.1-SNAPSHOT.jar"]

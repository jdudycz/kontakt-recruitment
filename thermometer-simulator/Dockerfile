FROM arm64v8/eclipse-temurin:17-jre

WORKDIR /app
COPY ./target/thermometer-simulator-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "thermometer-simulator-0.0.1-SNAPSHOT.jar"]

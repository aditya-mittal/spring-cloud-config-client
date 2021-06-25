FROM openjdk:11.0.3-jre-slim

RUN apt-get update && apt-get install -y curl

COPY target/configclient-1.0.0-SNAPSHOT.jar /app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
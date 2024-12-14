FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/it-offers-0.0.1-SNAPSHOT.jar it-offers-0.0.1-SNAPSHOT.jar
COPY logback.xml /app/logback.xml
EXPOSE 8080
ENTRYPOINT ["java","-jar","/it-offers-0.0.1-SNAPSHOT.jar"]
FROM openjdk:17-jdk-slim
COPY target/it-offers-0.0.1-SNAPSHOT.jar it-offers-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dlogback.configurationFile=logback.xml","-jar","/it-offers-0.0.1-SNAPSHOT.jar"]
FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ordernotificatopnawspubsub.jar
ENTRYPOINT ["java","-jar","/ordernotificatopnawspubsub.jar"]

FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} customermanagement.jar
ENTRYPOINT ["java","-jar","/customermanagement.jar"]
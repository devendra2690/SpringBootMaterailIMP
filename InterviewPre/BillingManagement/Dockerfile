FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} billingmanagement.jar
ENTRYPOINT ["java","-jar","/billingmanagement.jar"]

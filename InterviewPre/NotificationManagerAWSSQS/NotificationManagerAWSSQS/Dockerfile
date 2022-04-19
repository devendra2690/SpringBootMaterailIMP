FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notificationmanagerawssqs.jar
ENTRYPOINT ["java","-jar","/notificationmanagerawssqs.jar"]

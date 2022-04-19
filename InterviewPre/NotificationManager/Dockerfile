FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notificationmanager.jar
ENTRYPOINT ["java","-jar","/notificationmanager.jar"]

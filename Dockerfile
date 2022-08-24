FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} medigo.jar
ENTRYPOINT ["java","-jar","/medigo.jar"]
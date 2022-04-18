# Build the application first using Maven
FROM maven:3.8-openjdk-8 as build
WORKDIR /
COPY . .
RUN mvn clean install

# Inject the JAR file into a new container to keep the file small
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

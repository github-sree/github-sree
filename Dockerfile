# Build the application first using Maven
FROM maven:3.8-openjdk-8 as build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean install -DskipTests

# Inject the JAR file into a new container to keep the file small
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /tmp/target/*.jar spring-app-0.0.1.jar
ENTRYPOINT ["java","-jar","spring-app-0.0.1.jar"]
EXPOSE 8090

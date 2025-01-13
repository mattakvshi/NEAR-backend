# Use the official Maven image to build the app
FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /opt/app

# Copy the pom.xml and download dependencies
COPY mvnw pom.xml ./

# Copy the application source code and build the application
COPY ./src ./src
RUN mvn clean install -DskipTests

# Use the official OpenJDK image as a base image for running the app
FROM openjdk:17-jdk
WORKDIR /opt/app

# Expose the port that the application will run on
EXPOSE 8002

# Copy the Spring Boot JAR file from the build stage
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

# Set the entrypoint command to run the JAR file
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
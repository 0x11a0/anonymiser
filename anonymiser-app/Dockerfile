# Use a base image with Java 21
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/anonymiser-app-1.0-SNAPSHOT.jar /app/anonymiser-app.jar

# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "anonymiser-app.jar"]
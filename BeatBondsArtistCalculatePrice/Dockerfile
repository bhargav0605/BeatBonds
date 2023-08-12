# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory within the container
WORKDIR /app

# Copy the packaged Spring Boot JAR file into the container
COPY ./target/BeatBondsArtistCalculatePrice-0.0.1-SNAPSHOT.jar app.jar

# Define the command to run when the container starts
CMD ["java", "-jar", "app.jar"]
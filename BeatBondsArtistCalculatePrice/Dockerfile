# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory within the container
WORKDIR /app

# Copy the packaged Spring Boot JAR file into the container
COPY ./target/BeatBondsArtistCalculatePrice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8181

ENV CORE_POOL_SIZE="6" \
	MAX_CORE_POOL_SIZE="20" \
	PAGE_SIZE="10" \
	CHUNK_SIZE="10" \
	LOG_LEVEL="INFO"

# Define the command to run when the container starts
CMD ["java", "-jar", "app.jar"]
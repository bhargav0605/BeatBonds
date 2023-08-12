# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory within the container
WORKDIR /app

# Copy the packaged Spring Boot JAR file into the container
COPY ./target/BeatBondsArtistCall-0.0.1-SNAPSHOT.jar app.jar

ENV SPOTIFY_CLINT_ID="<Your-Client-ID>" \
	SPOTIFY_CLIENT_SECRET="<Your-Client-Secret>" \
	CORE_POOL_SIZE="6" \
	MAX_CORE_POOL_SIZE="20" \
	PAGE_SIZE="10" \
	PRICING_SERVICE_URL="http://localhost:8181/start-calculate-price" \
	SPOTFY_SEARCH_URL="https://api.spotify.com/v1/search" \
	SPOTIFY_TOKEN_URL="https://accounts.spotify.com/api/token" \
	CHUNK_SIZE="10" \
	LOG_LEVEL="INFO"

# Define the command to run when the container starts
CMD ["java", "-jar", "app.jar"]

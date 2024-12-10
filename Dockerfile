# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Setting working directory
WORKDIR /app

# Copy the pom.xml and download dependencies before copying the source code
# This step is important to take advantage of Docker caching.
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create Runtime Image
FROM openjdk:17-jdk-slim

# Set a non-root user to run the application
RUN useradd -m appuser

# Set the working directory
WORKDIR /app

# Define environment variables
ENV IMG_PATH=/img
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Copy the JAR file from the previous stage
COPY --from=build /app/target/teset-0.0.1-SNAPSHOT.jar /app/teset.jar

# Create necessary volumes and directories
VOLUME /temp
RUN mkdir -p /img

# Set the ARG to receive the value at build time
ARG JWT_SECRET_KEY
# Set the environment variable using the ARG value
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV HOST_MYSQL=${HOST_MYSQL} 
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME} 
ENV DB_PASSWORD=${DB_PASSWORD}

# Expose the necessary port
EXPOSE 8080

# Use the non-root user created earlier
USER appuser

# Run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -DIMG_PATH=${IMG_PATH} -DJWT_SECRET_KEY=${JWT_SECRET_KEY} -jar /app/gifa_api.jar"]

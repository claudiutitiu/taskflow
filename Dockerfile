# Stage 1: Build the application
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Build the JAR file (skipping tests to avoid needing the database during build)
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# Stage 2: Create the runtime image
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy only the compiled JAR from the builder stage
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
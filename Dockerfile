    # Stage 1: Build the application using Maven and a JDK image
FROM maven:3.9-eclipse-temurin-21 AS build
    # Set the working directory inside the image
WORKDIR /app
    # Copy the Maven project definition
COPY pom.xml .
    # Download dependencies (separate layer for better caching)
    # Instead of downloading all dependencies, let Maven handle it during package
    # RUN mvn dependency:go-offline -B

    # Copy the rest of the application source code
COPY src ./src
    # Package the application into an executable JAR file
    # Skip tests during Docker build (assume tests run separately in CI/CD)
RUN mvn package -DskipTests -B


    # Stage 2: Create the final, smaller image using only the JRE
    # Use an appropriate Java runtime image (Temurin is a good OpenJDK distribution)
FROM eclipse-temurin:21-jre-jammy
    # Set working directory
WORKDIR /app
    # Copy the executable JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
    # Expose the port the application runs on (defined in application.properties or default 8080)
EXPOSE 8080
    # Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
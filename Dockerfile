FROM eclipse-temurin:21-jre
WORKDIR ./

# Copy the generated JAR from the build stage
COPY ./target/*.jar simwebapp.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "simwebapp.jar"]
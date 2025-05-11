# Stage 1: Build with Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy pom + sources, then build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run on slim JDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /build/target/customer-ms-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8092

ENTRYPOINT ["java","-jar","app.jar"]

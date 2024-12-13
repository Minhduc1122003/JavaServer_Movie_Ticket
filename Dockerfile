# Stage 1: Build
FROM jelastic/maven:3.9.5-openjdk-21 as build
COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:21-jdk-slim
COPY --from=build /target/APP_MT-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]

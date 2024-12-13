FROM maven:3.8.3-openjdk-17 as build
COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/APP_MOVIE_TICKET_ONLINE-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
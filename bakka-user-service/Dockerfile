#Generate build
FROM maven:3-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true



#Dockerize
FROM eclipse-temurin:17.0.9_9-jdk-focal
WORKDIR /app
COPY --from=build /app/target/bakka-user-service-0.0.1-SNAPSHOT.jar ./bakka-user-service.jar
EXPOSE 5661
ENTRYPOINT ["java", "-jar", "bakka-user-service.jar"]
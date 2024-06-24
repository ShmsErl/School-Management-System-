FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/school-management-system-0.0.1-SNAPSHOT.jar /app/school-management-system.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/school-management-system.jar"]

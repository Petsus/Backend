FROM openjdk:11
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/app.jar"]
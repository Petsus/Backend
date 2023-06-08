FROM openjdk:11

COPY . .

RUN ./gradlew clean
RUN ./gradlew bootJar

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
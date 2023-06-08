FROM gradle:8.1.1-jdk-alpine as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle clean
RUN gradle build

FROM openjdk:11

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
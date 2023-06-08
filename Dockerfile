FROM gradle:8.1.1-jdk-alpine as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle clean
RUN gradle bootjar

FROM openjdk:11

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
@echo off
call .\gradlew clean
call .\gradlew bootJar
call docker compose down --rmi all
call docker compose up --build -d
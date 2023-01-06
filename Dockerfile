#Image de base for Docker
FROM openjdk:18-alpine
LABEL maintainer="sir@formation.com"
VOLUME /main-app
ADD target/*.jar app.jar
EXPOSE 8080
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
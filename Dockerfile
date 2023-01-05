#Image de base
FROM openjdk:18-alpine
LABEL maintainer="sir@formation.com"
VOLUME /main-app
ADD target/dema-test-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
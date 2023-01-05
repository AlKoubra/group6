#Image de base
FROM openjdk:18-alpine
LABEL maintainer="sir@projectgroupe6.com"
VOLUME /main-appformation
ADD target/formation-demo1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
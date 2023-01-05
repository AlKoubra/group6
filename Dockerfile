#Image de base
FROM openjdk:18-alpine
LABEL maintainer="sir@formation.com"
VOLUME /main-app
ADD target/formation-demo1-0.0.1-SNAPSHOT.jar formation-demo1.jar
EXPOSE 8080
# java -jar app.jar
ENTRYPOINT ["java","-jar","/formation-demo1.jar"]
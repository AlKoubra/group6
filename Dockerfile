#Image de base
FROM openjdk:18-alpine
LABEL maintainer="sir@groupe6.com"
VOLUME /main-app6
ADD target/formation-demo1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8077
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
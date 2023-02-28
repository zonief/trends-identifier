FROM openjdk:19-jdk-alpine
MAINTAINER zonief.com
ENV LC_ALL fr_FR.UTF-8
ENV LANG fr_FR.UTF-8
ENV LANGUAGE fr_FR:fr
COPY target/trends-identifier-1.0.0-SNAPSHOT.jar trends-identifier-1.0.0.jar
ENTRYPOINT ["java","-jar","/trends-identifier-1.0.0.jar"]
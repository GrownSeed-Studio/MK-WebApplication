FROM openjdk:18-ea-jdk-slim-buster

WORKDIR /workdir

COPY target/movies*.jar /workdir/movies.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "movies.jar"]
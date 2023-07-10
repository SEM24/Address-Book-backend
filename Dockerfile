FROM openjdk:17
COPY target/chi-software-1.0.0-SNAPSHOT.jar chi-software-1.0.0-SNAPSHOT.jar
LABEL authors="SEM"
ENTRYPOINT ["java", "-jar", "chi-software-1.0.0-SNAPSHOT.jar"]
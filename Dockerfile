FROM openjdk:17-jdk-alpine
COPY target/RentAll-0.0.1-SNAPSHOT.jar RentAll-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/RentAll-0.0.1-SNAPSHOT.jar"]
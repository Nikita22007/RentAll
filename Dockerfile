FROM openjdk:17-jdk-alpine
COPY target/springhelloworld-0.0.1-SNAPSHOT.jar springhelloworld-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/springhelloworld-0.0.1-SNAPSHOT.jar"]
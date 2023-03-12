FROM openjdk:19-jdk-alpine

EXPOSE 8081

COPY target/springBootDemo-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]
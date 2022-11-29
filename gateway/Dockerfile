FROM maven:3.8.3-openjdk-17

COPY target/*.jar /gateway.jar

ENTRYPOINT ["java","-jar","/gateway.jar"]

FROM maven:3.8.3-openjdk-17

COPY target/*.jar /namingserver.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/namingserver.jar"]


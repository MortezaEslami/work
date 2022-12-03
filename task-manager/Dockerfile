FROM maven:3.8.3-openjdk-17

COPY target/*.jar /taskmanager.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/taskmanager.jar"]

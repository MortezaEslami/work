FROM maven:3.8.3-openjdk-17

COPY target/*.jar /usermanager.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","/usermanager.jar"]

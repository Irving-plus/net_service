FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 1000
EXPOSE 100
ADD net_service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=sever","-c"]

FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD target/*.jar Observation.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","Observation.jar"]
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/docker-sipd.jar docker-sipd.jar
ENTRYPOINT ["java","-jar","docker-sipd.jar"]

FROM openjdk:8-jdk-alpine
EXPOSE 8100 8102

RUN mkdir logs
RUN mkdir config

COPY /target/docker-sipd.jar /app/docker-sipd.jar
COPY /target/classes/application.yaml /config/application.yaml

ENTRYPOINT ["java","-jar","app/docker-sipd.jar","--spring.config.location=./config/application.yaml"]



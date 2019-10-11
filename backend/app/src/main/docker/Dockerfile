FROM ubuntu:latest

MAINTAINER Medhanie Mihreteab "medhanie@outlook.com"

RUN apt-get update && apt-get install -y openjdk-8-jdk

WORKDIR /usr/local/bin/

ADD maven/akr-web-main-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "eri-ent-main-0.0.1-SNAPSHOT.jar"]
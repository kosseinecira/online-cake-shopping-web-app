FROM ubuntu:latest AS builder

RUN apt-get update
RUN apt install openjdk-17-jdk -y
RUN apt-get install maven -y
COPY ./back-end .

WORKDIR myapp
RUN mvn clean package -DskipTests
ARG JAR_FILE=/myapp/target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17-jdk-slim

COPY --from=builder myapp/dependencies/ ./
COPY --from=builder myapp/spring-boot-loader/ ./
COPY --from=builder myapp/snapshot-dependencies/ ./
COPY --from=builder myapp/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8080





FROM eclipse-temurin:17-jre AS builder
WORKDIR /myapp
ARG JAR_FILE=/back-end/target/**.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jre
WORKDIR /myapp
COPY --from=builder myapp/dependencies/ ./
COPY --from=builder myapp/spring-boot-loader/ ./
COPY --from=builder myapp/snapshot-dependencies/ ./
COPY --from=builder myapp/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]

EXPOSE 8080

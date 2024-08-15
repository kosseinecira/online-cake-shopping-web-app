FROM eclipse-temurin:17-jre AS builder
WORKDIR /myapp
ARG JAR_FILE=back-end/target/Online-Cake-Shopping-Webapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre
WORKDIR /myapp
COPY --from=builder myapp/dependencies/ ./
COPY --from=builder myapp/spring-boot-loader/ ./
COPY --from=builder myapp/snapshot-dependencies/ ./
COPY --from=builder myapp/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8080

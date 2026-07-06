FROM maven:3.9-eclipse-temurin-21 AS buildstage
WORKDIR /app

COPY pom.xml .
COPY src /app/src
RUN mvn clean package

FROM eclipse-temurin:21-jre

COPY --from=buildstage /app/target/ms-productor-mensajes-1.0.0.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]

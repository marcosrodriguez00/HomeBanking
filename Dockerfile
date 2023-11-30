FROM gradle:8.2.1-jdk11-alpine

COPY . .

EXPOSE 8080

RUN gradle build

ENTRYPOINT ["java", "-jar", "build/libs/homebanking-0.0.1-SNAPSHOT.jar"]
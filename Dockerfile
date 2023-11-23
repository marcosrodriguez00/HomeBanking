FROM gradle:8.2.1-jdk11-alpine

COPY . .

RUN gradle build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/homebanking-0.0.1-SNAPSHOT.jar"]
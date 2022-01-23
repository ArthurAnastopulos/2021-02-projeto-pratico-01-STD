FROM openjdk:11

WORKDIR /src/app
COPY . /src/app

RUN ./gradlew jar

ENTRYPOINT [ "java", "-cp", "build/libs/Auditor-1.0-SNAPSHOT.jar", "app.auditor.Auditor", "12345" ]
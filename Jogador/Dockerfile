FROM openjdk:11

WORKDIR /src/app
COPY . /src/app

RUN ./gradlew jar

ENTRYPOINT [ "java", "-cp", "build/libs/Jogador-1.0-SNAPSHOT.jar", "app.jogador.Jogador", "12345" ]
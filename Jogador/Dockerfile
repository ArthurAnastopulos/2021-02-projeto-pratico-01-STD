FROM openjdk:11

WORKDIR /src/app
COPY . /src/app

RUN ./gradlew jar

ENTRYPOINT [ "java", "-cp", "build/libs/Jogador-1.0-SNAPSHOT.jar", "app.jogador.Jogador" ]
## Caso Não seja enviado o arg[0] da estrategia do jogador esta estrategia padrão será enviada (Leva a vitoria do Jogador 1):
CMD [ "cima,direita,cima,cima,direita,direita" ]
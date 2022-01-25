# Projeto Prático 1: Jogo de captura de bandeiras

## Descrição

A captura de bandeiras é um jogo que conta obrigatoriamente com dois jogadores e um auditor. O auditor é responsável por acompanhar a movimentação dos jogadores pelo mapa, determinar qual jogador capturou quais bandeiras e, no final da partida, indicar qual jogador foi o vencedor. Jogadores e auditor são processos distribuídos executados em computadores distintos e usam a rede de computadores para se comunicarem.

## Compilar e Executar o Projeto

Primeiro deve-se compilar ambos os containers (Auditor e Jogador):
```bash
docker compose build
```

Para executar o Auditor (Servidor):
```bash
docker compose up auditor
```

Para executar o Jogador (Cliente):
```bash
docker compose run jogador $estrategia
```
A estrategia deve ser composta pelos comandos que robo ira utilizar na partida, estes são: `cima`, `baixo`, `direita`, `esquerda`.

Por exemplo para ao Jogador o executar o processo cliente:
```bash
docker compose run jogador cima,direita,cima,cima,direita,direita,baixo
```

Já para o processo Monitor que deve estar fora de containers, deve se entar na pasta do Monitor e compilar o mesmo:
```
./gradlew jar
 java -cp build/libs/MonitorGUI-1.0-SNAPSHOT.jar:libs/algs4.jar app.monitor.Monitor 
```

## Funcionalidades Implementadas

|Funcionalidade|Implentação|
|---	|---	|
|Aplicação Jogador| ok |
|Aplicação Auditor| ok |
|Aplicação MonitorGUI| ok |
|Docker-Compose| Parcial |
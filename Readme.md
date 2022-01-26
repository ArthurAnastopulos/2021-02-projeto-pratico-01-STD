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

Para executar cada Jogador (Cliente) - Sendo necessário subir dois containers clientes um para cada jogador:
```bash
docker compose run jogador $estrategia
```
A estrategia deve ser composta pelos comandos que robo ira utilizar na partida, estes são: `cima`, `baixo`, `direita`, `esquerda`, estes devem ser uma única linha argumento, seperados por virgulas.

Por exemplo para um Jogador executar o processo cliente:
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
|Docker-Compose| Parcial* |

## Explicação da Solução Obtida

Primeiramente foi projetado a implementação do Auditor, que foi desenvolvida com RMI, está cria o mapa ao ser instanciada, e quando um Jogador é conectado cria o Objeto jogador no Mapa, e inicia uma partida automaticamente assim que os dois jogadores se conectarem, como previsto na solução a ser desenvolvida. O mesmo também ao encerrar a partida, envia o resultado para todos os jogadores, indicando quantas bandeiras cada jogador capturou e quem foi o vencedor, em seguida, o processo auditor é encerrado.

Em seguida foi implementado o processo Jogador, neste foi feito a troca de mensagens entre o Auditor e Jogador, onde por meio de uma estratégia, enviada por argumento, faz movimentação do jogador, assim não tendo uma inteface para iteração com o usuário. Foi implementado como sugerido, quando se deslocar para uma coordenada, deve dormir por um tempo aleatório, não menor que 1 segundo e não maior que 2 segundos, antes de enviar a mensagem ao Auditor informando sua nova coordenada.


package app.jogador;

import app.IAUD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jogador {
    private static String serverName = "127.0.0.1";
    private static int port = 12345;
    private static final String OBJDISTNAME = "MyApp";
    private static String identificador = "";
    private static ArrayList<String> comandos;

    public static void main(String[] args) {
        if(args.length > 0) {
           String str = args[0];
           String[] strArr = str.split("\\s*,\\s*");
           comandos = new ArrayList<String>(Arrays.asList(strArr));
        }
        try
        {
            System.out.println("> Conectando no servidor "+ serverName);
            Registry registro = LocateRegistry.getRegistry(serverName, port);
            IAUD stub =  (IAUD) registro.lookup(OBJDISTNAME);
            String opcao = "1";

            boolean isRunning = true;
            System.out.println(menuInicial());
            while (isRunning){
                switch (opcao) {
                    case "1":
                        if(stub.conectar()){ //verfica quantos cliente estão conectados
                            whatPlayer(stub.getConectados());
                            stub.userConnect();
                            app(stub);
                        } else {
                            System.out.println("O Auditor já possui 2 Jogadores.");
                        }
                        opcao = "2";
                        break;
                    case "2":
                        System.out.println("Encerando o progama");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Opção invalida");
                        break;
                }
            }

        }catch (RemoteException | NotBoundException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String menuInicial(){
        return "==== Bem Vindo! ==== \n\n\n";
    }

    public static String gameInfo(){
        return "Instruções de Uso: \n" + "Para se mover no Tabuleiro: 'cima', 'baixo', 'esquerda' ou 'direita' (A estratégia composta desses movimentos é enviado por cada jogador por argumentos ao iniciar o processo Jogador.) \n"
                + "O jogo encerra quando todas as Bandeiras forem capturadas! Ou quando todas as instruções da estratégia dos jogadores acabarem! \n" + "Bom Jogo!";
    }

    public static String gameStart(){
        return "Os dois jogadores foram conectados! Iniciando a partida de Capture a Bandeira. \n" + "Partida começa com o Jogador 1 (player01)!!! \n\n";
    }

    public static String posicaoJogadores(IAUD stub) throws RemoteException {
        return "O Jogador 1 (player01) está atualmente nas Cordenadas: [" +  stub.getPlayer_CoordX("player01") + "," + stub.getPlayer_CoordY("player01") + "] \n"
                + "O Jogador 2 (player02) está atualmente nas Cordenadas: [" + stub.getPlayer_CoordX("player02") + "," + stub.getPlayer_CoordY("player02") + "]";
    }

    public static String posicaoBandeiras(IAUD stub) throws RemoteException{
        return stub.getBandeirasPosicao();
    }

    public static void whatPlayer(int conectados){
        if(conectados == 1){
            identificador = "player01";
        }
        if(conectados == 2){
            identificador = "player02";
        }
    }

    public static void changeTurn(IAUD stub) throws RemoteException {
        if(stub.getPlayerTurn().equals("player01")){
            stub.setTurn("player02");
        }
        else {
            stub.setTurn("player01");
        }
    }

    public static void app(IAUD stub) throws RemoteException, InterruptedException {
        System.out.println(gameInfo());
        System.out.println("Estão Conectados: " + stub.getConectados() + " Jogadores. \n" + "Sua identificação é " + identificador + ". \n" + "Esperando Outro jogador se conectar.");

        while (true){
            if(stub.encerrar() || comandos.size() == 0){
                System.out.println("A Pontuação Final é: \n Jogaodor 1:" + stub.getPlayer_Pontuacao("player01") + " Pontos \n Jogador 2: " + stub.getPlayer_Pontuacao("player2") + " Pontos \n\n");
                return;
            }
            if(stub.iniciar()){
                System.out.println(gameStart() + posicaoJogadores(stub));
                runningGame(stub);
            }
        }
    }

    public static void runningGame(IAUD stub) throws RemoteException, InterruptedException {
        while (true){
            if(stub.encerrar() || comandos.size() == 0){
                System.out.println("A Jogo foi Encerrado!");
                return;
            }
            if(stub.isPlayerTurn(identificador) && !stub.encerrar()){
                playerTurn(stub);
                changeTurn(stub);
            }
        }
    }

    public static void playerTurn(IAUD stub) throws RemoteException, InterruptedException {
        System.out.println("Posição Atual dos Jogadores: \n" + posicaoJogadores(stub) + "\n" + "Lendo o próximo sentido de sua estratégia para se movimentar.");
        System.out.println(posicaoBandeiras(stub) + "\n\n");

        boolean isRunning = true;
        while (isRunning){
            if (! stub.isPlayerTurn(identificador) || comandos.size() == 0) isRunning = false;

            String opcao = comandos.get(0);
            comandos.remove(0);

            TimeUnit.SECONDS.sleep(generateRandomNumber());

            if(stub.movimentarJogador(identificador, opcao)) {
                if(stub.checkForBandeira(stub.getPlayer_CoordX(identificador), stub.getPlayer_CoordY(identificador), identificador)) {
                    System.out.println("Bandeira Capturada! \n");
                }
                System.out.println("O " + identificador + " moveu com sucesso! \n");
                isRunning = false;
            } else {
                System.out.println("Opção Inválida! (Está direção leva para fora da dimensão do Tabuleiro.) Passando a rodada para o proximo Jogador\n");
                isRunning = false;
            }

        }
    }

    public static int generateRandomNumber() {
        return (Math.random() <= 0.5) ? 1 : 2;
    }
}

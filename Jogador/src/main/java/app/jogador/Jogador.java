package app.jogador;

import app.IAUD;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jogador {
    private static String serverName = "127.0.0.1";
    private static int port = 12345;
    private static final String OBJDISTNAME = "MyApp";
    private static String identificador = "";

    public static void main(String[] args) {
        try
        {
            System.out.println("> Conectando no servidor "+ serverName);
            Registry registro = LocateRegistry.getRegistry(serverName, port);
            IAUD stub =  (IAUD) registro.lookup(OBJDISTNAME);
            Scanner teclado = new Scanner(System.in);

            boolean isRunning = true;
            while (isRunning){
                System.out.println(menuInicial());
                System.out.print("Opção: ");
                String opcao = teclado.nextLine();

                switch (opcao) {
                    case "1":
                        if(stub.conectar()){ //verfica quantos cliente estão conectados
                            whatPlayer(stub.getConectados());
                            stub.userConnect();
                            app(stub);
                        } else {
                            System.out.println("O Auditor já possui 2 Jogadores.");
                        }
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
        return "==== Bem Vindo! ====" + "\n" + "Digite 1 - Para Conectar-se a uma partida | 2 - Sair" + "\n"
                + "========";
    }

    public static String gameInfo(){
        return "Instruções de Uso: \n" + "Digite: 'cima', 'baixo', 'esquerda' ou 'direita' para se mover no Tabuleiro \n"
                + "O jogo encerra quando todas as Bandeiras forem capturadas! \n" + "Bom Jogo!";
    }

    public static String gameStart(){
        return "Os dois jogadores foram conectados! Iniciando a partida de Capture a Bandeira. \n" + "Partida começa com o player01!!! \n\n";
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
        System.out.println("Estão Conectados: " + stub.getConectados() + "Jogadores. \n" + "Sua identificação é " + identificador + ". \n" + "Esperando Outros jogadores se conectarem.");

        boolean isRunning = true;
        while (isRunning){
            if(stub.encerrar()){
                System.out.println("A Jogo foi Encerrado!");
                return;
            }
            if(stub.iniciar()){
                System.out.println(gameStart() + posicaoJogadores(stub));
                runningGame(stub);
            }
        }
    }

    public static void runningGame(IAUD stub) throws RemoteException, InterruptedException {
        boolean isRunning = true;
        while (isRunning){
            if(stub.encerrar()){
                System.out.println("A Jogo foi Encerrado! 2");
                return;
            }
            if(stub.isPlayerTurn(identificador) && !stub.encerrar()){
                playerTurn(stub);
                changeTurn(stub);
            }
            else{
                System.out.println("Ainda não é o seu Turno! Esperando o movimento do outro Jogador" + "\n\n\n");
                TimeUnit.SECONDS.sleep(5); //sleep para fazer com que a mensagem de esperar para acabar o turno do player oposto não fique sendo printada acada segundo.
            }
        }
    }

    public static void playerTurn(IAUD stub) throws RemoteException, InterruptedException {
        System.out.println("Posição Atual dos Jogadores: \n" + posicaoJogadores(stub) + "\n" + "Escolha um sentido para se movimentar.");
        System.out.println(posicaoBandeiras(stub) + "\n\n");
        Scanner teclado = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            if (! stub.isPlayerTurn(identificador)) isRunning = false;

            System.out.print("Opção: ");
            String opcao = teclado.nextLine();

            TimeUnit.SECONDS.sleep(2);

            if(stub.movimentarJogador(identificador, opcao)) {
                if(stub.checkForBandeira(stub.getPlayer_CoordX(identificador), stub.getPlayer_CoordY(identificador), identificador)) {
                    System.out.println("Bandeira Capturada!");
                }
                System.out.println("O " + identificador + " moveu com sucesso!");
                isRunning = false;
            } else {
                System.out.println("Opção Inválida! Tente novamente");
            }

        }
    }
}

package app.monitor;

import app.IAUD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {
    private static String serverName = "127.0.0.1";
    private static int port = 12345;
    private static final String OBJDISTNAME = "MyApp";

    public static void main(String[] args) {
        try
        {
            System.out.println("> Conectando no servidor " + serverName);
            Registry registro = LocateRegistry.getRegistry(serverName, port);
            IAUD stub =  (IAUD) registro.lookup(OBJDISTNAME);

            Desenhar desenhar = new Desenhar(stub.getDimensao_Monitor() + 1);

            for (int i = 0; i < 3; i++){
                String idx = Integer.toString(i + 1);
                int x = stub.getCoordX_Bandeiras_Monitor(idx);
                int y = stub.getCoordY_Bandeiras_Monitor(idx);
                desenhar.addBandeiras(x, y , idx);
            }

            TimeUnit.SECONDS.sleep(1);
            desenhar.desenharTela();

            boolean isRunning = true;
            while (isRunning) {
                if(stub.encerrar()) isRunning = false;

                if(stub.howManyConnected() && !stub.encerrar()) {
                    System.out.println("Dois Jogadores Conectados no Auditor!");

                    int x1 = stub.getPlayer_CoordX("player01");
                    int y1 = stub.getPlayer_CoordY("player01");
                    desenhar.addJogador(x1, y1, "player01");

                    int x2 = stub.getPlayer_CoordX("player02");
                    int y2 = stub.getPlayer_CoordY("player02");
                    desenhar.addJogador(x2, y2, "player02");

                    TimeUnit.SECONDS.sleep(1);
                    desenhar.desenharTela();


                    app(stub, desenhar);
                    if(stub.encerrar()) isRunning = false;
                }
            }

        }catch (RemoteException | NotBoundException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void app(IAUD stub, Desenhar desenhar) throws RemoteException, InterruptedException {
        boolean isRunning = true;
        while (isRunning){
            if(stub.encerrar()) {
                isRunning = false;
                return;
            }

            int x1 = stub.getPlayer_CoordX("player01");
            int y1 = stub.getPlayer_CoordY("player01");
            if (houveMudancaPlayer(x1, y1, "player01", desenhar)){

                TimeUnit.SECONDS.sleep(1);

                desenhar.desenharTela();

            }

            int x2 = stub.getPlayer_CoordX("player02");
            int y2 = stub.getPlayer_CoordY("player02");
            if (houveMudancaPlayer(x2, y2, "player02", desenhar)){

                TimeUnit.SECONDS.sleep(1);

                desenhar.desenharTela();
            }

            if (bandeiraCapturada(stub, desenhar)){
                TimeUnit.SECONDS.sleep(1);

                desenhar.desenharTela();
            }

        }
    }

    public static boolean houveMudancaPlayer(int x, int y, String player, Desenhar desenhar){
        if(x != desenhar.getCirculo_CoordX(player)){
            desenhar.setCirculo_CoordX(x, player);
            if (y != desenhar.getCirculo_CoordY(player)){
                desenhar.setCirculo_CoordY(y, player);
                return true;
            }
            return true;
        }

        if(y != desenhar.getCirculo_CoordY(player)){
            desenhar.setCirculo_CoordY(y, player);
            if (x != desenhar.getCirculo_CoordX(player)){
                desenhar.setCirculo_CoordX(x, player);
                return true;
            }
            return true;
        }

        return false;
    }

    public static boolean bandeiraCapturada( IAUD stud, Desenhar desenhar) throws RemoteException {
        int cont = 0;
        if (stud.isBandeiraCapturada("1")) {
            if (! desenhar.isBandeiraCaptura("1")){
                desenhar.setBandeiraCaptura("1");
                cont++;
            }
        }

        if (stud.isBandeiraCapturada("2")) {
            if (! desenhar.isBandeiraCaptura("2")){
                desenhar.setBandeiraCaptura("2");
                cont++;
            }
        }

        if (stud.isBandeiraCapturada("3")) {
            if (! desenhar.isBandeiraCaptura("3")){
                desenhar.setBandeiraCaptura("3");
                cont++;
            }
        }

        return cont > 0;
    }
}

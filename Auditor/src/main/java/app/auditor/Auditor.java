package app.auditor;

import app.IAUD;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Auditor {
    private static String serverName = "127.0.0.1";
    private static int port = 12345;
    private  static final String OBJDISTNAME = "MyApp";

    public static void main(String[] args) {
        try {
            MainApp app = new MainApp();

            System.setProperty("java.rmi.app.server.hostname", serverName);
            IAUD std = (IAUD)
                    UnicastRemoteObject.exportObject( app, 0);

            Registry registry = LocateRegistry.createRegistry(port);

            registry.bind(OBJDISTNAME, std);
            System.out.println("Servidor pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");
        }
        catch (RemoteException | AlreadyBoundException ex)
        {
            Logger.getLogger(Auditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAUD extends Remote {

    //Interface Monitor
    public int getDimensao_Monitor() throws RemoteException;
    public int getCoordX_Bandeiras_Monitor(String identificador) throws RemoteException;
    public int getCoordY_Bandeiras_Monitor(String identificador) throws RemoteException;
    public int getPlayer_CoordX(String indentificador) throws RemoteException; //tbm utilizado no jogador
    public int getPlayer_CoordY(String indentificador) throws RemoteException; //tbm utilizado no jogador
    public boolean howManyConnected() throws RemoteException;
    public boolean encerrar() throws RemoteException; //tbm utilizado no jogador
    public boolean isBandeiraCapturada(String identificador) throws RemoteException;

}
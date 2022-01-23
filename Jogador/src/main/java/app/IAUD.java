package app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAUD extends Remote {
    public boolean conectar() throws RemoteException;
    public boolean iniciar() throws RemoteException;
    public boolean userConnect() throws RemoteException;
    public boolean encerrar() throws RemoteException;
    public boolean movimentarJogador(String indentificador, String sentido) throws RemoteException;
    public boolean checkForBandeira(int x, int y, String identificador) throws RemoteException;
    public boolean isPlayerTurn(String Identificador) throws RemoteException;
    public String getPlayerTurn() throws RemoteException;
    public int getConectados() throws  RemoteException;
    public void setTurn(String player) throws  RemoteException;
    public int getPlayer_CoordX(String indentificador) throws RemoteException;
    public int getPlayer_CoordY(String indentificador) throws RemoteException;
    public int getPlayer_Pontuacao(String indentificador) throws RemoteException;
    public String getBandeirasPosicao() throws RemoteException;
}

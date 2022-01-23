package app.auditor;

import app.IAUD;

import java.rmi.RemoteException;

public class MainApp implements IAUD {
    private final Mapa mapa;
    private int conectados = 0;
    private String turn = null;

    public MainApp() {
        this.mapa = new Mapa();
    }

    //Metodos para a Interface do Jogador

    @Override
    public boolean conectar() throws RemoteException {
        if(this.conectados < 2) {
            this.conectados++;
            return true;
        }
        return false;
    }

    @Override
    public boolean iniciar() throws RemoteException {
        if (this.mapa.howManyPlayers() != 2) {
            return  false;
        }
        this.turn = "player01";
        return true;

    }

    @Override
    public boolean userConnect() throws RemoteException {
        if(this.mapa.howManyPlayers() != 2){
            this.mapa.createPlayer(this.conectados);
            return true;
        }
        return false;

    }

    @Override
    public boolean encerrar() throws RemoteException {
        return this.mapa.encerrarJogo();
    }

    @Override
    public boolean movimentarJogador(String indentificador, String sentido) throws RemoteException {
        return this.mapa.mover(sentido.toLowerCase(), indentificador.toLowerCase());
    }

    @Override
    public boolean checkForBandeira(int x, int y, String identificador) throws RemoteException{
        return  this.mapa.checkBandeira(x, y, identificador);
    }

    @Override
    public boolean isPlayerTurn(String identificador) throws RemoteException
    {
        return identificador.equals(this.turn);
    }

    @Override
    public String getPlayerTurn() throws RemoteException
    {
        return this.turn;
    }

    @Override
    public int getConectados() throws  RemoteException{
        return conectados;
    }

    @Override
    public void setTurn(String player) throws  RemoteException{
        this.turn = player;
    }

    @Override
    public int getPlayer_CoordX(String indentificador) throws RemoteException{
        return this.mapa.getPlayer_CoordX(indentificador);
    }

    @Override
    public int getPlayer_CoordY(String indentificador) throws RemoteException{
        return this.mapa.getPlayer_CoordY(indentificador);
    }

    @Override
    public int getPlayer_Pontuacao(String indentificador) throws RemoteException{
        return this.mapa.getPlayer_Pontuacao(indentificador);
    }

    @Override
    public String getBandeirasPosicao() throws RemoteException{
        return this.mapa.getBandeira_Posição();
    }

    //Metodos para a Interface do Monitor

    @Override
    public int getDimensao_Monitor() throws RemoteException{
        return this.mapa.getDimensao();
    }

    @Override
    public int getCoordX_Bandeiras_Monitor(String identificador) throws RemoteException{
        return this.mapa.getBandeira_CoordX(identificador);
    }

    @Override
    public int getCoordY_Bandeiras_Monitor(String identificador) throws RemoteException{
        return this.mapa.getBandeira_CoordY(identificador);
    }

    @Override
    public boolean howManyConnected()throws RemoteException{
        return this.conectados == 2;
    }

    @Override
    public boolean isBandeiraCapturada(String identificador) throws RemoteException{
        return this.mapa.isBandeiraCapturada(identificador);
    }
}

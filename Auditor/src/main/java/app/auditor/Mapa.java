package app.auditor;

import java.util.ArrayList;

public class Mapa {
    private int dimensao;
    private ArrayList<Bandeira> bandeiras = new ArrayList<Bandeira>();
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();

    public Mapa() {
        this.dimensao = 5;
        for (int i = 0; i < 3; i++) {
            Bandeira bandeira = new Bandeira(Integer.toString(i+1));
            if(i == 0)
                bandeira.setCoords(3, 3);
            if(i == 1)
                bandeira.setCoords(1, 1);
            if(i == 2)
                bandeira.setCoords(4, 4);
            this.bandeiras.add(bandeira);
        }
    }

    public int howManyPlayers(){
        return this.jogadores.size();
    }

    public void createPlayer(int conectados){
        if(conectados == 1)
        {
            Jogador jogador = new Jogador("player01", 0, 0);
            this.jogadores.add(jogador);
        }
        if (conectados == 2)
        {
            Jogador jogador = new Jogador("player02", 5, 5);
            this.jogadores.add(jogador);
        }
    }

    public boolean mover(String direcao, String player)
    {
        int idx = 0;
        if(! player.equals("player01")) idx = 1;
        Jogador jogador = this.jogadores.get(idx);

        int x = jogador.getCoord_x();
        int y = jogador.getCoord_y();

        if(direcao.equals("cima")){
            if(y + 1 > 5) return false;
            jogador.movimentar(x, (y + 1));
            return true;
        }
        if(direcao.equals("baixo")){
            if(y - 1 < 0) return false;
            jogador.movimentar(x, (y - 1));
            return true;
        }
        if(direcao.equals("esquerda")){
            if(x - 1 < 0) return false;
            jogador.movimentar((x - 1), y);
            return true;
        }
        if(direcao.equals("direita")){
            if(x + 1 > 5) return false;
            jogador.movimentar((x + 1), y);
            return true;
        }
        return false;
    }

    public boolean checkBandeira(int x, int y, String player){
        for ( Bandeira bandeira: this.bandeiras ) {
            if(x == bandeira.getCoord_x()){
                if (y == bandeira.getCoord_y()) {
                    if(!bandeira.isCapturada()) {
                        bandeira.setCapturada(true, player);
                    }
                    else return false;

                    int idx = 0;
                    if(! player.equals("player01")) idx = 1;
                    Jogador jogador = this.jogadores.get(idx);
                    jogador.setPontuacao();

                    return true;
                }
            }
        }
        return false;
    }

    public boolean encerrarJogo(){
        if (this.jogadores.size() != 2) return false;
        return this.getPlayer_Pontuacao("player01") + this.getPlayer_Pontuacao("player02") == 3;
    }

    public int getPlayer_CoordX(String player){
        int idx = 0;
        if(! player.equals("player01")) idx = 1;
        Jogador jogador = this.jogadores.get(idx);
        return jogador.getCoord_x();
    }

    public int getPlayer_CoordY(String player){
        int idx = 0;
        if(! player.equals("player01")) idx = 1;
        Jogador jogador = this.jogadores.get(idx);
        return jogador.getCoord_y();
    }

    public int getPlayer_Pontuacao(String player){
        int idx = 0;
        if(! player.equals("player01")) idx = 1;
        Jogador jogador = this.jogadores.get(idx);
        return jogador.getPontuacao();
    }

    public String getBandeira_Posição(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Bandeira bandeira: this.bandeiras ) {
           stringBuilder.append("Bandeira " + bandeira.getIdentificador() + " => Coordenas [" + bandeira.getCoord_x() + ", " + bandeira.getCoord_y() + "]");
           if (bandeira.isCapturada()) stringBuilder.append(" Capturada pelo " + bandeira.getWhoCapture() + " \n");
           else stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    //Metodos para a Interface do Monitor

    public int getDimensao(){
        return this.dimensao;
    }

    public int getBandeira_CoordX(String identificador){
        int idx = 0;
        if(identificador.equals("2")) idx = 1;
        if(identificador.equals("3")) idx = 2;
        Bandeira bandeira = this.bandeiras.get(idx);
        return bandeira.getCoord_x();
    }

    public int getBandeira_CoordY(String identificador){
        int idx = 0;
        if(identificador.equals("2")) idx = 1;
        if(identificador.equals("3")) idx = 2;
        Bandeira bandeira = this.bandeiras.get(idx);
        return bandeira.getCoord_y();
    }

    public boolean isBandeiraCapturada(String identificador){
        for (Bandeira bandeira : this.bandeiras) {
            if (bandeira.getIdentificador().equals(identificador)) {
                if (bandeira.isCapturada()) return true;
            }
        }
        return false;
    }
}

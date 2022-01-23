package app.monitor;

import edu.princeton.cs.algs4.Draw;

import java.awt.*;
import java.util.ArrayList;

public class Desenhar {
    private int dimensao;
    private ArrayList<Circulo> jogadores = new ArrayList<Circulo>();
    private ArrayList<Quadrado> bandeiras = new ArrayList<Quadrado>();
    private Draw draw;

    public Desenhar(int dimensao){
        this.draw = new Draw();

        this.dimensao = dimensao;
        this.draw.setXscale(0, this.dimensao);
        this.draw.setYscale(0, this.dimensao);
        this.draw.enableDoubleBuffering();
    }

    public void desenharTela(){
        draw.clear(Color.LIGHT_GRAY);
        this.desenharMapa();
        if(this.howManyPlayers()) {
            this.desenharJogadores(this.draw, "player01");
            this.desenharJogadores(this.draw, "player02");
        }
        if (this.howManyBandeiras()) {
            for (Quadrado bandeira : this.bandeiras) {
                if (!bandeira.isCapturada()) bandeira.desenhar(this.draw);
            }
        }
        draw.show();
    }

    public void desenharMapa(){
        draw.setPenColor(Color.BLACK);
        for (int x = 0; x <= this.dimensao; x++) draw.line(x, 0, x, this.dimensao);
        for (int y = 0; y <= this.dimensao; y++) draw.line(0, y, this.dimensao, y);
    }

    public void desenharJogadores(Draw draw, String identificador){
        int idx = 0;
        if (identificador.equals("player02")) idx = 1;
        Circulo jogador = this.jogadores.get(idx);
        jogador.desenhar(draw, identificador);
    }

    public void addBandeiras(int x, int y, String identificador){
        Quadrado bandeira = new Quadrado(x, y, identificador);
        this.bandeiras.add(bandeira);
    }

    public void  addJogador(int x, int y, String player){
        Circulo jogador = new Circulo(x, y, player);
        this.jogadores.add(jogador);
    }

    public int getCirculo_CoordX(String player){
        int idx = 0;
        if (player.equals("player02")) idx = 1;
        Circulo jogador = this.jogadores.get(idx);
        return (int) jogador.getCoord_X();
    }

    public int getCirculo_CoordY(String player){
        int idx = 0;
        if (player.equals("player02")) idx = 1;
        Circulo jogador = this.jogadores.get(idx);
        return (int) jogador.getCoord_Y();
    }

    public void setCirculo_CoordX(int x, String player){
        int idx = 0;
        if (player.equals("player02")) idx = 1;
        Circulo jogador = this.jogadores.get(idx);
        jogador.setCoord_X(x);
    }

    public void setCirculo_CoordY(int y, String player){
        int idx = 0;
        if (player.equals("player02")) idx = 1;
        Circulo jogador = this.jogadores.get(idx);
        jogador.setCoord_Y(y);
    }

    public boolean howManyPlayers(){
        return this.jogadores.size() == 2;
    }

    public boolean howManyBandeiras(){
        return this.bandeiras.size() == 3;
    }

    public void setBandeiraCaptura(String identifcador) {
        for (Quadrado bandeira: this.bandeiras){
            if(bandeira.getIdentificador().equals(identifcador)){
                bandeira.setCapturada(true);
            }
        }
    }

    public boolean isBandeiraCaptura(String identifcador) {
        for (Quadrado bandeira: this.bandeiras){
            if(bandeira.getIdentificador().equals(identifcador)){
                return bandeira.isCapturada();
            }
        }
        return false;
    }
}

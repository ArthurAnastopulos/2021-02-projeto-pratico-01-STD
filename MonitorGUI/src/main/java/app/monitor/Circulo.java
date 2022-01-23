package app.monitor;

import edu.princeton.cs.algs4.Draw;

import java.awt.*;

public class Circulo {
    private String player;
    private double coord_X;
    private double coord_Y;

    public Circulo(int x, int y, String player) {
        this.coord_X = Math.floor(x);
        this.coord_Y = Math.floor(y);
        this.player = player;
    }

    public void desenhar(Draw desenho, String identificador){
        Color color = null;
        if(identificador.equals("player01")){
            color = Color.RED;
        }
        if(identificador.equals("player02")){
            color = Color.BLUE;
        }
        desenho.setPenColor(color);
        desenho.filledCircle(this.coord_X + 0.5, this.coord_Y + 0.5, 0.375);
    }

    public double getCoord_X() {
        return this.coord_X;
    }

    public double getCoord_Y() {
        return this.coord_Y;
    }

    public void setCoord_X(double coord_X) {
        this.coord_X = Math.floor(coord_X);
    }

    public void setCoord_Y(double coord_Y) {
        this.coord_Y = Math.floor(coord_Y);
    }
}

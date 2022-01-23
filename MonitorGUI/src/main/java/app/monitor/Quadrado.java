package app.monitor;

import edu.princeton.cs.algs4.Draw;

import java.awt.*;

public class Quadrado {
    private String identificador;
    private double coordX;
    private double coordY;
    private boolean capturada = false;

    public Quadrado (int x, int y, String identificador){
        this.coordX = Math.floor(x);
        this.coordY = Math.floor(y);
        this.identificador = identificador;
    }

    public void desenhar(Draw draw){
        Color color = (this.isCapturada()) ? Color.ORANGE : Color.GREEN;
        draw.setPenColor(color);
        draw.filledSquare(this.coordX + 0.5, this.coordY + 0.5, .5);
    }

    public String getIdentificador() {
        return identificador;
    }
    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public boolean isCapturada() {
        return capturada;
    }

    public void setCapturada(boolean capturada) {
        this.capturada = capturada;
    }


}

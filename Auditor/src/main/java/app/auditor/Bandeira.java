package app.auditor;

import java.util.Random;

public class Bandeira {
    private String identificador;
    private boolean capturada = false;
    private String whoCapture = "";
    private int coord_x = 0;
    private int coord_y = 0;

    public Bandeira(String identificador) {
        this.identificador = identificador;
    }

    public void setCoords(int x, int y)
    {
        this.coord_x = x;
        this.coord_y = y;
    }

    public int getCoord_x() {
        return coord_x;
    }

    public int getCoord_y() {
        return coord_y;
    }

    public void setCapturada(boolean capturada, String player) {
        this.whoCapture = player;
        this.capturada = capturada;
    }

    public boolean isCapturada() {
        return capturada;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getWhoCapture() {
        return whoCapture;
    }
}

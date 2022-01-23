package app.auditor;

public class Jogador {
    private String identificador = null;
    private int pontuacao = 0;
    private int coord_x = 0;
    private int coord_y = 0;


    public Jogador(String identificador, int coord_x, int coord_y) {
        this.identificador = identificador;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
    }

    public void movimentar(int x, int y)
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

    public void setPontuacao() {
        this.pontuacao++;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}

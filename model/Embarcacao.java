import java.io.Serializable;

public abstract class Embarcacao implements Serializable {
    protected int tamanho;
    protected char simbolo;

    public int getTamanho() {
        return tamanho;
    }

    public char getSimbolo() {
        return simbolo;
    }
}

import java.io.Serializable;

public class EstadoDoJogo implements Serializable {
    private static final long serialVersionUID = 1L;

    public Jogador jogador1;
    public Jogador jogador2;
    public Mapa mapa1;
    public int embarcacoesAbatidasJogador1;
    public int embarcacoesAbatidasJogador2;
}

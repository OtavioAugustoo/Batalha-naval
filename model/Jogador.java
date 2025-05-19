import java.io.Serializable;

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void pontuar(Embarcacao embarcacao) {
        this.pontuacao += embarcacao.getTamanho();
    }
}

import java.io.Serializable;

public class Jogo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Jogador jogador1;
    private Jogador jogador2;
    private Mapa mapa1;
    int embarcacoesAbatidasJogador1 = 0;
    int embarcacoesAbatidasJogador2 = 0;

    public Jogo(String nome1, String nome2) {
        this.jogador1 = new Jogador(nome1);
        this.jogador2 = new Jogador(nome2);
        this.mapa1 = new Mapa();
    }

    public Jogo(EstadoDoJogo estado) {
        this.jogador1 = estado.jogador1;
        this.jogador2 = estado.jogador2;
        this.mapa1 = estado.mapa1;
        this.embarcacoesAbatidasJogador1 = estado.embarcacoesAbatidasJogador1;
        this.embarcacoesAbatidasJogador2 = estado.embarcacoesAbatidasJogador2;
    }

    public boolean receberJogada(int x, int y, boolean jogador1Atacando) {
        Jogador jogador = jogador1Atacando ? jogador1 : jogador2;
        char[][] interno = mapa1.getMapaInterno();
        char[][] visivel = mapa1.getMapaVisivel();
        char alvo = interno[x][y];

        if (alvo != 'V' && alvo != 'X' && alvo != 'Y') {
            destruirNavioConectado(x, y, alvo, mapa1);
            jogador.pontuar(new Embarcacao() {{
                simbolo = alvo;
                tamanho = 1;
            }});
            if (jogador1Atacando) embarcacoesAbatidasJogador1++;
            else embarcacoesAbatidasJogador2++;
        } else {
            visivel[x][y] = 'X';
        }

        salvarEstado();
        return embarcoesAbatidasSuficiente(jogador1Atacando);
    }

    private void destruirNavioConectado(int x, int y, char simbolo, Mapa mapa) {
        if (!estaDentro(x, y, mapa.getTamanho())) return;
        char[][] interno = mapa.getMapaInterno();
        char[][] visivel = mapa.getMapaVisivel();
        if (interno[x][y] != simbolo) return;

        interno[x][y] = 'Y';
        visivel[x][y] = 'X';

        destruirNavioConectado(x + 1, y, simbolo, mapa);
        destruirNavioConectado(x - 1, y, simbolo, mapa);
        destruirNavioConectado(x, y + 1, simbolo, mapa);
        destruirNavioConectado(x, y - 1, simbolo, mapa);
        destruirNavioConectado(x + 1, y + 1, simbolo, mapa);
        destruirNavioConectado(x - 1, y - 1, simbolo, mapa);
        destruirNavioConectado(x + 1, y - 1, simbolo, mapa);
        destruirNavioConectado(x - 1, y + 1, simbolo, mapa);
    }

    private boolean estaDentro(int x, int y, int tamanho) {
        return x >= 0 && y >= 0 && x < tamanho && y < tamanho;
    }

    private boolean embarcoesAbatidasSuficiente(boolean jogador1Atacando) {
        return jogador1Atacando ? embarcacoesAbatidasJogador1 >= 5 : embarcacoesAbatidasJogador2 >= 5;
    }

    private void salvarEstado() {
        EstadoDoJogo estado = new EstadoDoJogo();
        estado.jogador1 = jogador1;
        estado.jogador2 = jogador2;
        estado.mapa1 = mapa1;
        estado.embarcacoesAbatidasJogador1 = embarcacoesAbatidasJogador1;
        estado.embarcacoesAbatidasJogador2 = embarcacoesAbatidasJogador2;
        Persistencia.salvarEstado(estado);
    }

    public String nomeDaEmbarcacao(char simbolo) {
        return switch (simbolo) {
            case 'a' -> "Porta-Aviões";
            case 'd' -> "Destroyer";
            case 's' -> "Submarino";
            case 'f' -> "Fragata";
            case 'b' -> "Bote";
            default -> "Desconhecida";
        };
    }

    public Jogador getJogador1() { return jogador1; }
    public Jogador getJogador2() { return jogador2; }
    public Mapa getMapa1() { return mapa1; }
}

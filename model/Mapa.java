import java.io.Serializable;
import java.util.*;

public class Mapa implements Serializable {
    private final int tamanho = 17;
    private char[][] mapaVisivel;
    private char[][] mapaInterno;
    private Random random = new Random();

    public Mapa() {
        mapaVisivel = new char[tamanho][tamanho];
        mapaInterno = new char[tamanho][tamanho];
        inicializarMapas();
    }

    private void inicializarMapas() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mapaVisivel[i][j] = '~';
                mapaInterno[i][j] = 'V';
            }
        }
    }
    public void mostrarMapaVisivel() {

        System.out.print("   ");
        for (int j = 0; j < tamanho; j++) {
            System.out.printf("%2d", j);
        }
        System.out.println();
    
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < tamanho; j++) {
                System.out.print(mapaVisivel[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    
    public void mostrarMapaInterno() {
        System.out.print("   ");
        for (int j = 0; j < tamanho; j++) {
            System.out.printf("%2d", j);
        }
        System.out.println();
    
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < tamanho; j++) {
                System.out.print(mapaInterno[i][j] + " ");
            }
            System.out.println();
        }
    }
    

    public char[][] getMapaVisivel() {
        return mapaVisivel;
    }

    public char[][] getMapaInterno() {
        return mapaInterno;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void alocarEmbarcacoesAutomaticamente() {
        Embarcacao[] todas = criarTodasEmbarcacoes();
        String[] direcoes = {"H", "V", "D"};

        for (Embarcacao e : todas) {
            boolean alocado = false;
            while (!alocado) {
                int x = random.nextInt(tamanho);
                int y = random.nextInt(tamanho);
                String direcao = direcoes[random.nextInt(direcoes.length)];
                if (podeAlocar(x, y, direcao, e.getTamanho())) {
                    alocar(x, y, direcao, e);
                    alocado = true;
                }
            }
        }
    }

    private boolean podeAlocar(int x, int y, String direcao, int tamanhoNavio) {
        int dx = 0, dy = 0;
        switch (direcao) {
            case "H": dy = 1; break;
            case "V": dx = 1; break;
            case "D": dx = 1; dy = 1; break;
        }

        for (int i = 0; i < tamanhoNavio; i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;
            if (!estaDentro(nx, ny) || !ehAreaLivre(nx, ny)) return false;
        }
        return true;
    }

    private void alocar(int x, int y, String direcao, Embarcacao e) {
        int dx = 0, dy = 0;
        switch (direcao) {
            case "H": dy = 1; break;
            case "V": dx = 1; break;
            case "D": dx = 1; dy = 1; break;
        }

        for (int i = 0; i < e.getTamanho(); i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;
            mapaInterno[nx][ny] = e.getSimbolo();
        }
    }

    private boolean estaDentro(int x, int y) {
        return x >= 0 && y >= 0 && x < tamanho && y < tamanho;
    }

    private boolean ehAreaLivre(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (estaDentro(nx, ny)) {
                    if (mapaInterno[nx][ny] != 'V') return false;
                }
            }
        }
        return true;
    }

    private Embarcacao[] criarTodasEmbarcacoes() {
        List<Embarcacao> lista = new ArrayList<>();
        for (int i = 0; i < 2; i++) lista.add(new PortaAvioes());
        for (int i = 0; i < 3; i++) lista.add(new Destroyer());
        for (int i = 0; i < 4; i++) lista.add(new Submarino());
        for (int i = 0; i < 5; i++) lista.add(new Fragata());
        for (int i = 0; i < 6; i++) lista.add(new Bote());
        return lista.toArray(new Embarcacao[0]);
    }
}

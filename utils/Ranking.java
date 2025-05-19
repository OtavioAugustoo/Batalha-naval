import java.io.*;
import java.util.*;

public class Ranking {
    private static final String ARQUIVO = "ranking.txt";

    public static void registrarJogador(String nome, int pontos) {
        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(nome + "," + pontos + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exibirRanking(String nomeJogador) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            List<String[]> lista = new ArrayList<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                lista.add(partes);
            }

            lista.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

            System.out.println("\n Ranking de Jogadores:");
            int posicao = 1;
            for (String[] entrada : lista) {
                System.out.println(posicao + ". " + entrada[0] + " - " + entrada[1] + " pontos");
                if (entrada[0].equals(nomeJogador)) {
                    System.out.println("Sua posição: " + posicao);
                }
                posicao++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

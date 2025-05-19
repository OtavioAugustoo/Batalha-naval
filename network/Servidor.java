import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(4000)) {
            System.out.println("Aguardando dois jogadores...");

            Socket socket1 = servidor.accept();
            System.out.println("Jogador 1 conectado!");
            BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);

            Socket socket2 = servidor.accept();
            System.out.println("Jogador 2 conectado!");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);

            
            String nome1 = in1.readLine();
            String nome2 = in2.readLine();

            Jogo jogo;
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Deseja continuar a partida anterior? (s/n): ");
            String resposta = console.readLine();
            
            if (resposta.equalsIgnoreCase("s")) {
                EstadoDoJogo estado = Persistencia.carregarEstado();
                if (estado != null) {
                    jogo = new Jogo(estado);
                    System.out.println("✔ Partida restaurada com sucesso!");
                } else {
                    System.out.println(" Nenhuma partida salva encontrada. Iniciando nova...");
                    jogo = new Jogo(nome1, nome2);
                    jogo.getMapa1().alocarEmbarcacoesAutomaticamente();
                }
            } else {
                jogo = new Jogo(nome1, nome2);
                jogo.getMapa1().alocarEmbarcacoesAutomaticamente();
            }
            
            boolean turnoJogador1 = true;
            boolean jogoAtivo = true;

            while (jogoAtivo) {
                PrintWriter outAtual = turnoJogador1 ? out1 : out2;
                BufferedReader inAtual = turnoJogador1 ? in1 : in2;
                String nomeJogadorAtual = turnoJogador1 ? jogo.getJogador1().getNome() : jogo.getJogador2().getNome();

                outAtual.println("SUA VEZ " + nomeJogadorAtual + " (linha,coluna): ");
                String jogada = inAtual.readLine();

                if (jogada == null || jogada.isEmpty()) continue;

                String[] partes = jogada.split(",");
                if (partes.length != 2) continue;

                int x = Integer.parseInt(partes[0]);
                int y = Integer.parseInt(partes[1]);

                
                char alvo = jogo.getMapa1().getMapaInterno()[x][y];
                String nomeNavio = jogo.nomeDaEmbarcacao(alvo);

                
                boolean venceu = jogo.receberJogada(x, y, turnoJogador1);

                
                if (alvo != 'V' && alvo != 'X' && alvo != 'Y') {
                    outAtual.println(" Você abateu um " + nomeNavio + "!");
                } else {
                    outAtual.println(" Água! Nenhuma embarcação ali.");
                }

                
                StringBuilder mapaTexto = new StringBuilder("MAPA ATUALIZADO:");
                for (char[] linha : jogo.getMapa1().getMapaVisivel()) {
                    mapaTexto.append("\n");
                    for (char c : linha) mapaTexto.append(c).append(" ");
                }
                out1.println(mapaTexto.toString());
                out2.println(mapaTexto.toString());

                if (venceu) {
                    out1.println( nomeJogadorAtual + " venceu!");
                    out2.println( nomeJogadorAtual + " venceu!");
                    Ranking.registrarJogador(jogo.getJogador1().getNome(), jogo.getJogador1().getPontuacao());
                    Ranking.registrarJogador(jogo.getJogador2().getNome(), jogo.getJogador2().getPontuacao());
                    Ranking.exibirRanking(nomeJogadorAtual);
                    break;
                }

                turnoJogador1 = !turnoJogador1;
            }

            socket1.close();
            socket2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

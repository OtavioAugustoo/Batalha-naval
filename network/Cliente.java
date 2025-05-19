import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4000)) {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
            System.out.print("Digite seu nome: ");
            String nome = teclado.readLine();
            saida.println(nome);
        
            String linha;
            while ((linha = entrada.readLine()) != null) {
                System.out.println(linha);
                if (linha.contains("SUA VEZ")) {
                    System.out.print("Jogada (linha,coluna): ");
                    String jogada = teclado.readLine();
                    saida.println(jogada);
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;

public class Persistencia {
    public static void salvarEstado(EstadoDoJogo estado) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            out.writeObject(estado);
        } catch (IOException e) {
            System.err.println("Erro ao salvar estado: " + e.getMessage());
        }
    }

    public static EstadoDoJogo carregarEstado() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"))) {
            return (EstadoDoJogo) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}

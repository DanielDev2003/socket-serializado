package servidor;

import cliente.ClientDb;
import model.Habilidade;
import model.Personagem;
import serializador.Persistencia;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private static final int PORT = 4444;
    public static List<Personagem> personagens;
    public static List<Habilidade> habilidades;
    public static void main(String[] args) {
        carregarPersonagens();
        carregarHabilidades();
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Servidor ouvindo na porta: " + PORT);

            while (true){
                Socket socket = serverSocket.accept();
                new ClientDb(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarPersonagens(){
        Object object = Persistencia.deserializarPersonagens();
        if(object != null){
            personagens = (List<Personagem>) object;
            System.out.println("Personagens carregados do arquivo");
        }else{
            System.out.println("Não existe personagem na lista");
        }
    }

    public static void carregarHabilidades(){
        Object object = Persistencia.deserializarHabilidades();
        if(object != null){
            habilidades = (List<Habilidade>) object;
            System.out.println("Habilidades carregadas do arquivo");
        }else {
            System.out.println("Nao existe habilidade no arquivo");
        }
    }
}

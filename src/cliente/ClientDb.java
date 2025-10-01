package cliente;

import model.Mensagem;
import model.Personagem;
import serializador.Persistencia;
import servidor.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientDb extends Thread{
    private Socket socket;

    public ClientDb(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());)
        {
            InetAddress ip = socket.getInetAddress();
            int porta = socket.getPort();
            System.out.println("Cliente conectado do IP " + ip + " na porta " + porta);

            boolean ativo = true;
            while (ativo){
                Mensagem mensagem = (Mensagem) in.readObject();
                switch (mensagem.getOpcao()){
                    case 1:
                        out.writeObject(new Mensagem(1, Server.personagens));
                        break;
                    case 2:
                        Personagem personagem = (Personagem) mensagem.getObjeto();
                        Server.personagens.add(personagem);
                        Persistencia.serializarPersonagens(Server.personagens);
                        Server.carregarPersonagens();
                        out.writeObject(new Mensagem(2, "Personagem adicionado com sucesso!"));
                        break;
                    case 3:
                        out.writeObject(new Mensagem(3, Server.habilidades));
                        break;
                    case 4:
                        out.writeObject(new Mensagem(4, "Saindo do servidor..."));
                        ativo = false;
                        break;
                    default:
                        out.writeObject(new Mensagem(0, "Opcao Invalida"));
                        break;
                }
            }
            socket.close();
            System.out.println("Cliente desconectado!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

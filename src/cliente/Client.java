package cliente;

import model.Habilidade;
import model.Mensagem;
import model.Personagem;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4444)){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            boolean ativo = true;

            while (ativo){

                System.out.println("\n=== Bem-vindo à Matrix ===");
                System.out.println("1 - Listar personagens");
                System.out.println("2 - Adicionar Personagem");
                System.out.println("3 - Listar habilidades");
                System.out.println("4 - Sair");
                System.out.print("Escolha: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                if(opcao == 2){
                    System.out.println("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite uma descricao: ");
                    String descricao = scanner.nextLine();
                    Personagem personagem = new Personagem(nome, descricao);
                    out.writeObject(new Mensagem(opcao, personagem));
                }else{
                    out.writeObject(new Mensagem(opcao, null));
                }

                Mensagem mensagem = (Mensagem) in.readObject();
                switch (mensagem.getOpcao()){
                    case 1:
                        List<Personagem> personagems = (List<Personagem>) mensagem.getObjeto();
                        System.out.println("\n=== Personagens de Matrix ===\n");
                        personagems.forEach(p -> System.out.println(
                                "- " + p.getNome() + ": " + p.getDescricao()
                        ));
                        System.out.println("\n=============================\n");
                        break;
                    case 2:
                        System.out.println(mensagem.getObjeto());
                        break;
                    case 3:
                        List<Habilidade> habilidades = (List<Habilidade>) mensagem.getObjeto();
                        System.out.println("\n--- Habilidades ---\n");
                        habilidades.forEach(h-> System.out.println(
                                "- " + h.getNome() + ": " + h.getDescricao()
                        ));
                        System.out.println("\n=============================\n");
                        break;
                    case 4:
                        System.out.println(mensagem.getObjeto());
                        ativo = false;
                        break;
                    default:
                        System.out.println(mensagem.getObjeto());
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


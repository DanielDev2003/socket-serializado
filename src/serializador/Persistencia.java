package serializador;

import model.Habilidade;
import model.Personagem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Persistencia {

    public static void serializarPersonagens(Object objeto){
        Path path = Paths.get("dados/personagem.ser");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))){
            objectOutputStream.writeObject(objeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserializarPersonagens(){
        Path path = Paths.get("dados/personagem.ser");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))){
            List<Personagem> list = (List<Personagem>) objectInputStream.readObject();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static void serializarHabilidades(Object object){
        Path path = Paths.get("dados/habilidade.ser");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))){
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object deserializarHabilidades(){
        Path path = Paths.get("dados/habilidade.ser");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))){
            List<Habilidade> list = (List<Habilidade>) objectInputStream.readObject();
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

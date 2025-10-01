package model;

import java.io.Serializable;
import java.util.Objects;

public class Mensagem implements Serializable {
    private int opcao;
    private Object objeto;

    public Mensagem(int opcao, Object objeto){
        this.opcao = opcao;
        this.objeto = objeto;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return opcao == mensagem.opcao && Objects.equals(objeto, mensagem.objeto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opcao, objeto);
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "opcao=" + opcao +
                ", objeto=" + objeto +
                '}';
    }
}

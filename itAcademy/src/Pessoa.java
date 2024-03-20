import java.util.ArrayList;

public class Pessoa {
    private String nome;
    private String cpf;
    private ArrayList<Aposta> apostas;

    public Pessoa(String cpf, String nome, Aposta aposta) {
        this.cpf = cpf;
        this.nome = nome;
        this.apostas = new ArrayList<>();
        apostas.add(aposta);
    }

    public void adicionaAposta(Aposta aposta) {
        apostas.add(aposta);
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public ArrayList<Aposta> getApostas() {
        return apostas;
    }
}

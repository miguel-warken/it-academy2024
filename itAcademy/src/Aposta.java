import java.util.ArrayList;

public class Aposta {
    int id;
    Pessoa pessoaDona;
    ArrayList<Integer> numerosAposta;

    public Aposta(int id, ArrayList<Integer> numerosAposta) {
        this.id = id;
        this.numerosAposta = numerosAposta;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getNumerosAposta() {
        return numerosAposta;
    }

    public void setPessoaDona(Pessoa pessoa) {
        this.pessoaDona = pessoa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoaDona() {
        return pessoaDona;
    }

    public void setNumerosAposta(ArrayList<Integer> numerosAposta) {
        this.numerosAposta = numerosAposta;
    }

}
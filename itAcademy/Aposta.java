import java.util.ArrayList;

public class Aposta {
    int id;
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
}
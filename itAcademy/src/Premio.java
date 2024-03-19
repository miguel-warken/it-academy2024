import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Premio {
    Sorteio sorteio;

    public Premio(Sorteio sorteio) {
        this.sorteio = sorteio;
        criaPremio();
    }

    private void premiacao() {

        // Se houve alguma aposta vencedora
        if (!getSorteio().getApostasVencedoras().isEmpty()) {
            criaPremio();
        } else {
            System.out.println("Não houveram quaisquer apostas vencedoras!");
        }
    }

    private void criaPremio() {
        // Caracteres que irão compor o código
        List<String> nomesVencedores = ordenaVencedoras();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("premiacao.txt"))) {
            writer.write("PARABÉNS! OS PREMIADOS FORAM:\n\n");

            for (int i = 0; i < nomesVencedores.size(); i++) {
                writer.write((i + 1) + ") " + nomesVencedores.get(i) + "\n");
            }

            writer.write("\n==================================================\n\n");

            for (int i = 0; i < nomesVencedores.size(); i++) {
                String codigo = criaCodigo();
                writer.write((i + 1) + ") " + codigo + "\n");
            }

            writer.write("\nOBRIGADO E VOLTEM SEMPRE!");

            writer.write("\n\n==================================================\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String criaCodigo() {
        // Serão selecionados 8 caracteres para nosso código de 12 dígitos
        List<Character> carateresUtilizados = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        // Caracteres possíveis para nosso codigo
        String caracteresPossiveis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Para selecionar um elemento aleatorio dos caracteres
        Random r = new Random(System.currentTimeMillis());

        // Gerar um grupo de 8 caracteres
        for (int i = 0; i < 8; i++) {
            carateresUtilizados.add(caracteresPossiveis.charAt(r.nextInt()));
        }

        // Agora montar o código
        for (int i = 0; i < 12; i++) {
            int index = r.nextInt();
            sb.append(carateresUtilizados.get(index));
        }

        // Substituir 2 locais por '-'
        sb.insert(4, "-");
        sb.insert(9, "-");

        return sb.toString();
    }

    private ArrayList<String> ordenaVencedoras() {
        ArrayList<String> nomesVencedores = new ArrayList<>();
        // Se alguma aposta venceu
        if (!getSorteio().getApostasVencedoras().isEmpty()) {
            Collections.sort(getSorteio().getApostasVencedoras(), new Comparator<Aposta>() {
                @Override
                public int compare(Aposta a1, Aposta a2) {
                    return a1.getPessoaDona().getNome().compareTo(a2.getPessoaDona().getNome());
                }
            });

            // 4.2 Imprimi a lista ordenada
            for (Aposta aposta : getSorteio().getApostas()) {
                nomesVencedores.add(aposta.getPessoaDona().getNome());
            }
        }
        return nomesVencedores;
    }

    public Sorteio getSorteio() {
        return sorteio;
    }

}

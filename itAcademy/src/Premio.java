import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Premio {
    Jogo jogo;

    public Premio(Jogo jogo) {
        this.jogo = jogo;
        premiacao();
    }

    private void premiacao() {

        // Definir estado do jogo
        jogo.setEstadoJogo(EstadoJogo.PREMIANDO);
        System.out.println("\nNo momento, estamos " + jogo.getEstadoJogo() + "\n");

        // Se houve alguma aposta vencedora
        try {
            if (!getJogo().getSorteio().getApostasVencedoras().isEmpty()) {
                criaPremio();
                System.out.println("-> Premio criado! Cheque 'premiacao.txt'!\n");
            } else {
                System.out.println("-> Não houveram quaisquer apostas vencedoras na última partida!");
            }
        } catch (NullPointerException e) {
            System.out.println("-> Não houveram quaisquer apostas vencedoras na última partida!");
        }

        // Definir estado do jogo
        jogo.setEstadoJogo(EstadoJogo.APOSTANDO);
    }

    private void criaPremio() {
        // Caracteres que irão compor o código
        List<String> nomesVencedores = ordenaVencedoras();

        // Para selecionar um elemento aleatorio dos caracteres para o código.
        // Instanciado aqui para não gerar o mesmo código para todas os vencedores.
        Random r = new Random(System.currentTimeMillis());

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("premiacao.txt"), StandardCharsets.UTF_8))) {

            writer.write("==================================================\n");
            writer.write("PARABÉNS! OS PREMIADOS DA ÚLTIMA PARTIDA FORAM:\n\n");

            for (int i = 0; i < nomesVencedores.size(); i++) {
                writer.write((i + 1) + ") " + nomesVencedores.get(i) + "\n");
            }

            writer.write("\n==================================================\n");
            writer.write("APROVEITEM SEUS GIFTCARDS! (EXCLUSIVO POR CPF):\n\n");

            for (int i = 0; i < nomesVencedores.size(); i++) {
                String codigo = criaCodigo(r);
                writer.write((i + 1) + ") " + codigo + " | DESTINADO A: " + nomesVencedores.get(i) + "\n");
            }

            writer.write("\nOBRIGADO E VOLTEM SEMPRE!");
            writer.write("\n==================================================\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String criaCodigo(Random r) {
        // Serão selecionados 8 caracteres para nosso código de 12 dígitos
        StringBuilder sb = new StringBuilder();

        // Caracteres possíveis para nosso codigo
        String caracteresPossiveis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Montar o código
        for (int i = 0; i < 12; i++) {
            char escolhido = caracteresPossiveis.charAt(r.nextInt(caracteresPossiveis.length()));
            sb.append(escolhido);
        }

        // Substituir 2 locais por '-'
        sb.insert(4, "-");
        sb.insert(9, "-");

        return sb.toString();
    }

    private ArrayList<String> ordenaVencedoras() {
        ArrayList<String> nomesVencedores = new ArrayList<>();

        // Se alguma aposta venceu (nao esta vazio)
        Collections.sort(getJogo().getSorteio().getApostasVencedoras(), new Comparator<Aposta>() {
            @Override
            public int compare(Aposta a1, Aposta a2) {
                return a1.getPessoaDona().getNome().compareTo(a2.getPessoaDona().getNome());
            }
        });

        // Cria lista ordenada
        for (Aposta aposta : getJogo().getSorteio().getApostasVencedoras()) {
            nomesVencedores.add(aposta.getPessoaDona().getNome());
        }
        return nomesVencedores;
    }

    public Jogo getJogo() {
        return this.jogo;
    }

}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Sorteio {
    ArrayList<Aposta> apostas;
    ArrayList<Aposta> apostasVencedoras;
    Set<Integer> numerosSorteados;
    Jogo jogo;

    public Sorteio(ArrayList<Aposta> apostas, Jogo jogo) {
        this.apostas = apostas;
        this.jogo = jogo;
        this.apostasVencedoras = new ArrayList<>();
        this.numerosSorteados = new HashSet<>();
        realizaSorteio();
    }

    private void realizaSorteio() {

        // Inicialização de estruturas
        int rodadas = 1;
        boolean houveVencedora = false;
        Random r = new Random(System.currentTimeMillis()); // Número realmente aleatório
        ArrayList<Aposta> apostasVencedoras = new ArrayList<>();

        // Definir estado do Jogo
        jogo.setEstadoJogo(EstadoJogo.SORTEANDO);

        // 1. Sortear números
        for (int i = 1; i <= 5; i++) {
            this.numerosSorteados.add(r.nextInt(50) + 1);
            // this.numerosSorteados.add(i);
        }

        // 2. Definir estado do Jogo
        jogo.setEstadoJogo(EstadoJogo.APURANDO);

        // 3. Se já existir uma aposta com os 5 números
        for (Aposta aposta : getApostas()) {

            // Selecionar os números da aposta
            ArrayList<Integer> numerosDaAposta = aposta.getNumerosAposta();

            boolean temTodosNumerosSorteados = true;

            for (Integer numero : numerosDaAposta) {
                if (!numerosSorteados.contains(numero)) {
                    temTodosNumerosSorteados = false;
                    break;
                }
            }

            if (temTodosNumerosSorteados) {
                apostasVencedoras.add(aposta);
                houveVencedora = true;
            }
        }

        // Se houverem vencedores na primeira rodada
        if (houveVencedora) {
            mostraResultados(rodadas);
            novoJogo();
        } else {
            System.out.println("Não houveram apostas vencedoras com o conjunto sorteado " + getnumerosSorteados()
                    + ", iniciando rodadas bônus!\n");
        }

        // Serão realizadas 25 rodadas no total
        while (rodadas <= 25) {

            int numeroAdicionado = r.nextInt(50) + 1;

            // Novo número não pode já fazer parte dos sorteados
            while (this.numerosSorteados.contains(numeroAdicionado)) {
                numeroAdicionado = r.nextInt(50) + 1;
            }

            // Quando o novo número não fizer parte dos sorteados
            this.numerosSorteados.add(numeroAdicionado);

            // TODO Corrigir Verificar apostas com novo número
            for (Aposta aposta : getApostas()) {

                boolean temTodosNumerosSorteados = true;

                for (Integer numero : aposta.getNumerosAposta()) {
                    if (!numerosSorteados.contains(numero)) {
                        temTodosNumerosSorteados = false;
                        continue;
                    }
                }
                // Se ganhou, acaba direto
                if (temTodosNumerosSorteados) {
                    this.apostasVencedoras.add(aposta);
                    mostraResultados(rodadas);
                }
            }
            rodadas++;
        }
        mostraResultados(rodadas);
    }

    public void setNumerosSorteados(Set<Integer> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    private void mostraResultados(int rodadas) {

        // 1. Lista de numeros sorteados
        System.out.println("Números sorteados: " + getnumerosSorteados());

        // 2. Quantas rodadas passaram
        System.out.println("Rodadas realizadas: " + rodadas);

        // 3. Quantidade de apostas vencedoras:
        if (getApostasVencedoras().isEmpty()) {
            System.out.println("Nenhuma aposta venceu!" + getApostasVencedoras());
            novoJogo();
        } else {
            System.out.println("Numero de apostas vencedoras: " + getApostasVencedoras().size());
        }

        // 4. Lista de apostas vencedoras:

        // 4.1 Ordena lista alfabeticamente

        // Se alguma aposta venceu
        if (!getApostasVencedoras().isEmpty()) {
            Collections.sort(getApostasVencedoras(), new Comparator<Aposta>() {
                @Override
                public int compare(Aposta a1, Aposta a2) {
                    return a1.getPessoaDona().getNome().compareTo(a2.getPessoaDona().getNome());
                }
            });

            // 4.2 Imprimi a lista ordenada
            for (Aposta aposta : apostas) {
                System.out.println(aposta.getPessoaDona().getNome() + " ganhou com a aposta [" + aposta.getId() + "]!");
            }
        }
        // TODO 5. Numeros mais escolhidos aos menos

        // Limpa a lista de apostas realizadas
        novoJogo();
    }

    private void novoJogo() {
        this.apostas.clear();
        this.apostasVencedoras.clear();
        // TODO verificar exception
        this.jogo.menuInicial();
    }

    public ArrayList<Aposta> getApostas() {
        return this.apostas;
    }

    public ArrayList<Aposta> getApostasVencedoras() {
        return apostasVencedoras;
    }

    public Set<Integer> getnumerosSorteados() {
        return this.numerosSorteados;
    }
}

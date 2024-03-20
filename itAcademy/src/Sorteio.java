import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Sorteio {
    ArrayList<Aposta> apostas;
    ArrayList<Aposta> apostasVencedoras;
    Set<Integer> numerosSorteados;
    Jogo jogo;
    int rodadas;

    public Sorteio(ArrayList<Aposta> apostas, Jogo jogo) {
        this.rodadas = 1;
        this.apostas = apostas;
        this.jogo = jogo;
        this.numerosSorteados = new HashSet<>();
        this.apostasVencedoras = new ArrayList<>();
        realizaSorteio();
        novoJogo();
    }

    private void realizaSorteio() {

        // Checa para ver se existem apostas
        if(getApostas().isEmpty()){
            System.out.println("Não foram feitas quaisquer apostas nesta partida ainda!");
            return;
        }

        // Inicialização de estruturas
        int rodadas = 1;
        boolean houveVencedora = false;
        Random r = new Random(System.currentTimeMillis()); // Número realmente aleatório

        // Definir estado do Jogo
        jogo.setEstadoJogo(EstadoJogo.SORTEANDO);
        System.out.println("\nNo momento, estamos " + jogo.getEstadoJogo() + "\n");

        // 1. Sortear números
        for (int i = 1; i <= 5; i++) {
            this.numerosSorteados.add(r.nextInt(50) + 1);
        }

        // 2. Definir estado do Jogo
        jogo.setEstadoJogo(EstadoJogo.APURANDO);
        System.out.println("No momento, estamos " + jogo.getEstadoJogo() + "\n");

        // 3. Se já existir uma aposta com os 5 números
        for (Aposta aposta : getApostas()) {

            // Selecionar os números da aposta
            ArrayList<Integer> numerosDaAposta = aposta.getNumerosAposta();

            boolean temTodosNumerosSorteados = true;

            // Para cada numero dos numeros de uma aposta
            for (Integer numero : numerosDaAposta) {
                // Se não houver um dos numeros, não vai ter os 5
                if (!numerosSorteados.contains(numero)) {
                    temTodosNumerosSorteados = false;
                    break;
                }
            }
            // Se houver os 5 números
            if (temTodosNumerosSorteados) {
                this.apostasVencedoras.add(aposta);
                houveVencedora = true;
            }
        }

        // Se houverem vencedores na primeira rodada
        if (houveVencedora) {
            mostraResultados(rodadas);
            return;
        } else {
            System.out.println("Não houveram apostas vencedoras com o conjunto sorteado " + getnumerosSorteados()
                    + ", iniciando rodadas bônus!\n");
        }

        // Serão realizadas 25 rodadas no total
        while (this.rodadas <= 25) {

            int numeroAdicionado = r.nextInt(50) + 1;

            // Novo número não pode já fazer parte dos sorteados
            while (this.numerosSorteados.contains(numeroAdicionado)) {
                numeroAdicionado = r.nextInt(50) + 1;
            }

            // Quando o novo número não fizer parte dos sorteados
            this.numerosSorteados.add(numeroAdicionado);

            // Para cada aposta na lista de apostas feitas
            for (Aposta aposta : getApostas()) {

                // Mesma lógica de antes

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
                    break;
                }
            }
            this.rodadas++;
        }
        mostraResultados(rodadas);
    }

    private void mostraResultados(int rodadas) {

        // 1. Lista de numeros sorteados
        System.out.println("-> Números sorteados: " + getnumerosSorteados());

        // 2. Quantas rodadas passaram
        System.out.println("\n-> Rodadas realizadas: " + rodadas);

        // 3. Quantidade de apostas vencedoras:
        if (getApostasVencedoras().isEmpty()) {
            System.out.println("\n-> Nenhuma aposta venceu nesta partida...");
        } else {
            System.out.println("\n-> Numero de apostas vencedoras: " + getApostasVencedoras().size());
             // 4. Lista de apostas vencedoras:
            ordenaVencedorasEImprime();
        }

        // 5. Mais escolhidos aos menos
        numerosMaisEscolhidos();

        System.out.println("\n-> Inicializando novo jogo! Todas apostas zeradas!");
    }

    private void numerosMaisEscolhidos(){

        List<Integer> todosNumeros = new ArrayList<>();
        Map<Integer, Integer> freqNumeros = new HashMap<>(); 

        // Coletar todos os números de todas as Apostas feitas
        for(Aposta aposta : getApostas()){  
            todosNumeros.addAll(aposta.getNumerosAposta());
        }

        // Colocar as frequências de cada número
        for(Integer numero : todosNumeros){
            freqNumeros.put(numero, freqNumeros.getOrDefault(numero, 0) + 1);
        }

        System.out.println("-> Números e frequência que foram escolhidos: \n");

        // Ordernar e imprimir
        freqNumeros.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) 
            .forEach(numero -> System.out.println(numero.getKey() + " | " + numero.getValue()));
    }

    private void ordenaVencedorasEImprime() {
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
                System.out.println("\n"+ aposta.getPessoaDona().getNome() + " ganhou com a aposta [" + aposta.getId() + "]!");
            }
        }
        System.out.println();
    }

    private void novoJogo() {
        this.apostas.clear();
        this.jogo.setEstadoJogo(EstadoJogo.APOSTANDO);
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

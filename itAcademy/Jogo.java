import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Jogo {
    private EstadoJogo estadoJogo;
    private ArrayList<Aposta> apostas;
    private ArrayList<Integer> numDisponiveis;
    private ArrayList<Pessoa> pessoas;

    public Jogo() {
        this.estadoJogo = EstadoJogo.APOSTANDO;
        this.apostas = new ArrayList<>();
        this.pessoas = new ArrayList<>();
        menuInicial();
    }

    public void menuInicial() {
        System.out.println("\nPor favor, escolha uma opcão:\n1) Registrar Aposta\n" +
                "2) Listar Apostas do Jogo atual\n" +
                "3) Iniciar sorteio com apostas existentes\n" +
                "0) Sair!\n");

        Scanner sc = new Scanner(System.in);
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                registraApostas();
                break;

            case 2:
                listaTodasApostas();
                break;

            case 3:
                Sorteio sorteio = new Sorteio(getApostas(), this);
                break;

            default:
                System.out.println("Saindo do jogo!");
                break;
        }
        sc.close();
    }

    private void registraApostas() {

        ArrayList<Integer> numerosAposta = new ArrayList<>();

        System.out.println("Por favor, insira seu nome e CPF para fazer uma aposta!\nNome:");

        // TODO apenas nome string
        Scanner sc = new Scanner(System.in);
        String novoNome = "a";

        System.out.println("CPF:");
        String novoCpf = "11111111111";

        // TODO apenas CPF numerico
        while (novoCpf.length() != 11) {
            System.out.println("CPF Invalido. Apenas numeros sem acentuacoes ou letras!\nCPF:");
            novoCpf = sc.next();
        }

        System.out.println("Por favor, escolha uma opcao!\n1) Escolher os numeros\n2) Surpresinha\n");
        mostraNumeros();

        int opcao = sc.nextInt();

        // TODO deixar menor
        // se quiser escolher
        if (opcao == 1) {
            System.out.println("Agora, escolha 5 numeros, um de cada vez!");

            for (int i = 1; i <= 5; i++) {
                System.out.println(i + "º Numero:");

                // permitir apenas numeros depois

                int numEscolhido = sc.nextInt();

                if ((numEscolhido >= 1 && numEscolhido <= 50) && (!numerosAposta.contains(numEscolhido))) {
                    numerosAposta.add(numEscolhido);
                } else {
                    System.out.println("Numero invalido. Apenas entre 1 e 50 e sem repetidos.");
                    i--;
                }
            }
            mostraNumeros();
        } else if (opcao == 2) {

            System.out.println("Numeros escolhidos: ");
            Random r = new Random();

            for (int i = 1; i <= 5; i++) {
                int numEscolhido = r.nextInt(50) + 1;
                if (!numerosAposta.contains(numEscolhido)) {
                    numerosAposta.add(numEscolhido);
                    System.out.print(numEscolhido + " | ");
                } else {
                    i--;
                }
            }
            System.out.println("\n");
            mostraNumeros();
        }

        Aposta novaAposta = new Aposta(1000 + apostas.size(), numerosAposta);
        this.apostas.add(novaAposta);

        Pessoa novaPessoa = new Pessoa(novoCpf, novoNome, novaAposta);
        novaAposta.setPessoaDona(novaPessoa);

        // Confere se a pessoa ja existe
        if (!pessoas.isEmpty()) {
            for (int i = 0; i < pessoas.size(); i++) {
                Pessoa pessoa = pessoas.get(i);
                // se pessoa ja existe
                if (pessoa.getCpf().equals(novoCpf)) {
                    pessoa.adicionaApostaPessoaExistente(novaAposta);
                    break;

                } else {
                    pessoas.add(novaPessoa);
                }
            }
        } else {
            pessoas.add(novaPessoa);
        }
        menuInicial();
    }

    private void mostraNumeros() {
        for (int i = 1; i <= 50; i++) {
            System.out.printf("%2d |   ", i);
            if (i % 10 == 0) {
                System.out.println();
            }
        }
    }

    private void listaTodasApostas() {
        if (!getApostas().isEmpty()) {
            System.out.println("Numero de apostas: " + getApostas().size());
            for (Aposta aposta : getApostas()) {
                System.out.println("Aposta [" + aposta.getId() + "] : " + aposta.getNumerosAposta());
            }

        } else {
            System.out.println("\nAinda não foram feitas apostas nesta partida!");
        }
        menuInicial();
    }

    public void setEstadoJogo(EstadoJogo estadoJogo) {
        this.estadoJogo = estadoJogo;
    }

    public EstadoJogo getEstadoJogo() {
        return estadoJogo;
    }

    public ArrayList<Integer> getNumsDisponiveis() {
        return this.numDisponiveis;
    }

    public ArrayList<Aposta> getApostas() {
        return apostas;
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }
}
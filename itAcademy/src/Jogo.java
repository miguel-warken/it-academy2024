import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Jogo {
    private EstadoJogo estadoJogo;
    private ArrayList<Aposta> apostas;
    private ArrayList<Pessoa> pessoas;
    private Sorteio sorteio;

    public Jogo() {
        this.estadoJogo = EstadoJogo.APOSTANDO;
        this.apostas = new ArrayList<>();
        this.pessoas = new ArrayList<>();
        menuInicial();
    }

    public void menuInicial() {
        
        System.out.println(menuDeOpcoes());

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
                this.sorteio = new Sorteio(getApostas(), this);
                break;

            case 4:
                new Premio(this);
                break;

            default:
                System.out.println("Saindo do jogo!");
                return;
        }
        menuInicial();
    }

    private void registraApostas() {
        Scanner sc = new Scanner(System.in);

        // Cadastrar CPF
        String novoCpf = cadastraEValidaCpf(sc);

        // Se a pessoa já existir, pula o nome e adiciona a aposta na lista de apostas
        // dela
        Pessoa pessoaExistente = verificaSeCpfExistente(novoCpf);
        if (pessoaExistente != null) {
            Aposta novaAposta = criaAposta(sc);
            pessoaExistente.adicionaAposta(novaAposta);
            novaAposta.setPessoaDona(pessoaExistente);
            apostas.add(novaAposta);
        } else {
            // Senão, cria nova Pessoa com a nova Aposta feita
            String novoNome = cadastraEValidaNome(sc);
            Aposta novaAposta = criaAposta(sc);
            apostas.add(novaAposta);
            Pessoa novaPessoa = new Pessoa(novoCpf, novoNome, novaAposta);
            pessoas.add(novaPessoa);
            novaAposta.setPessoaDona(novaPessoa);
        }
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
            System.out.println("Número de apostas: " + getApostas().size());
            for (Aposta aposta : getApostas()) {
                System.out.println("Aposta [" + aposta.getId() + "] : " + aposta.getNumerosAposta());
            }
        } else {
            System.out.println("\nAinda não foram feitas apostas nesta partida!");
        }
        System.out.println();
    }

    private Aposta criaAposta(Scanner sc) {

        ArrayList<Integer> numerosAposta = new ArrayList<>();
        String entrada;
        int numEscolhido = 0;

        // Escolher uma opcao para sorteio
        System.out.println("Por favor, escolha uma opcao!\n1) Escolher os numeros\n2) Surpresinha\n");
        mostraNumeros();

        String opcao = sc.next();

        // Se quiser escolher
        if (opcao.equals("1")) {
            System.out.println("Agora, escolha 5 números, um de cada vez!");

            // 5 numeros
            for (int i = 1; i <= 5; i++) {
                System.out.println(i + "º Numero:");
                entrada = sc.next();

                // Apenas numéricos
                try {
                    numEscolhido = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, insira um número inteiro válido.");
                }

                if ((numEscolhido >= 1 && numEscolhido <= 50) && (!numerosAposta.contains(numEscolhido))) {
                    numerosAposta.add(numEscolhido);
                } else {
                    System.out.println("Numero inválido. Apenas entre 1 e 50 e sem repetidos.");
                    i--;
                }
            }
            // Função surpresinha
        } else if (opcao.equals("2")) {

            System.out.println("Números escolhidos: ");
            Random r = new Random(System.currentTimeMillis());

            // 5 números
            for (int i = 1; i <= 5; i++) {
                numEscolhido = r.nextInt(50) + 1;
                // Para não repetir
                if (!numerosAposta.contains(numEscolhido)) {
                    numerosAposta.add(numEscolhido);
                    System.out.print(numEscolhido + " | ");
                } else {
                    i--;
                }
            }
        }
        System.out.println("\n-> Aposta Criada com " + numerosAposta + "!\n");
        return new Aposta(1000 + getApostas().size(), numerosAposta);
    }

    private String cadastraEValidaCpf(Scanner sc) {
        System.out.println("\nPor favor, insira seu CPF e nome para fazer uma aposta!\nCPF: ");
        String novoCpf = sc.next();
        while (!novoCpf.matches("[0-9]+") || novoCpf.length() != 11) {
            System.out.println("CPF Inválido! Insira apenas com números!\nCPF: ");
            novoCpf = sc.next();
        }
        return novoCpf;
    }

    private String cadastraEValidaNome(Scanner sc) {
        System.out.println("Nome:");
        String novoNome = sc.nextLine();
        while (!novoNome.matches("[a-zA-Z]+")) {
            System.out.println("Por favor, insira apenas seu primeiro nome, sem numéricos ou acentuação!");
            novoNome = sc.nextLine();
        }
        return novoNome;
    }

    private Pessoa verificaSeCpfExistente(String novoCpf) {
        for (Pessoa pessoa : getPessoas()) {
            if (pessoa.getCpf().equals(novoCpf)) {
                return pessoa;
            }
        }
        return null;
    }

    private String menuDeOpcoes(){
        return 
        "==============================\n" +
        "No momento, estamos " + getEstadoJogo() +
        "\n==============================\n" +
        "Por favor, escolha uma opcão:\n1) Registrar Aposta\n" +
                "2) Listar Apostas do Jogo atual\n" +
                "3) Iniciar sorteio com apostas existentes\n" +
                "4) Premiar todas as apostas vencedoras até agora\n" +
                "0) Sair!\n" +
        "==============================\n";
    }

    public Sorteio getSorteio() {
        return this.sorteio;
    }

    public void setEstadoJogo(EstadoJogo estadoJogo) {
        this.estadoJogo = estadoJogo;
    }

    public EstadoJogo getEstadoJogo() {
        return estadoJogo;
    }

    public ArrayList<Aposta> getApostas() {
        return apostas;
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }


}

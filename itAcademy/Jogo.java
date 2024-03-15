import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Jogo{
    private EstadoJogo estadoJogo;
    //talvez alterar por um arraylist de apostas
    private int numApostasTotal;
    private ArrayList<Integer> numDisponiveis;
    private ArrayList<Pessoa> pessoas;
    
    public Jogo(){
        this.estadoJogo = estadoJogo.APOSTANDO;
        this.numApostasTotal = 0;
        this.numDisponiveis = recarregaNumerosDisponiveis();
        this.pessoas = new ArrayList<>();
        menuInicial();
    }
    
    private ArrayList<Integer> recarregaNumerosDisponiveis(){
        ArrayList<Integer> arr = new ArrayList<>();
        
        for(int i = 1; i <= 50; i++){
            arr.add(i);
        }
        
        return arr;
    }
    
    private void menuInicial(){
        System.out.println("Por favor, escolha uma opcao!");
	    System.out.println("1) Registrar Aposta\n2) Listar Apostas do Jogo atual\n3) Iniciar sorteio com apostas existentes\n0) Sair");
	    
	    Scanner sc = new Scanner(System.in);
	    int opcao = sc.nextInt();
	    
	    switch (opcao)
	    {
	       case 1: 
                registraApostas();
                break;
	           
	       default: 
	           System.out.println("Saindo do jogo");
	    }
    }
    
    private void registraApostas(){
        ArrayList<Integer> numerosAposta = new ArrayList<>();
        
        System.out.println("Por favor, insira seu nome e CPF para fazer uma aposta!\nNome:");
        
        Scanner sc = new Scanner(System.in);
	    String novoNome = sc.nextLine();
	    
	    System.out.println("CPF:");
	    String novoCpf = sc.next();
	    
	    while(novoCpf.length() != 11){
	       System.out.println("CPF Invalido. Apenas numeros sem acentuacoes ou letras!\nCPF:");
	       novoCpf = sc.next();
	    }

	    System.out.println("Agora, escolha 5 numeros, um de cada vez! Os disponiveis sao:");
	    mostraNumerosDisponiveis();
	    
	    for(int i = 1; i <= 5; i++){
	        System.out.println(i + "º Numero:");
	        int numEscolhido = sc.nextInt();
	        
	        if (numEscolhido >= 1 && numEscolhido <= 50) {
	            
	            if (getNumsDisponiveis().get(numEscolhido - 1) == 0) {
                    System.out.println("Numero ja escolhido. Por favor, escolha outro número.");
                    i--;
	            }
                else {
                    getNumsDisponiveis().set(numEscolhido - 1, 0);
                    numerosAposta.add(numEscolhido);
                }
                
            } else {
                System.out.println("Numero invalido. Apenas entre 1 e 50.");
                i--;
            }
	    }
	    mostraNumerosDisponiveis();
	    Aposta novaAposta = new Aposta(1000+numApostasTotal, numerosAposta);
	    
	    //Confere se a pessoa ja existe
	    for(Pessoa pessoa : pessoas){
	        //se pessoa ja existe
	        if(pessoa.getCpf() == novoCpf){
	            pessoa.adicionaApostaPessoaExistente(novaAposta);
	        }
	        else{
	            Pessoa novaPessoa = new Pessoa(novoCpf, novoNome, novaAposta);
	        }
	    }
	    menuInicial();
    }
    
    private void mostraNumerosDisponiveis(){
        int count = 0;
        for(Integer num : getNumsDisponiveis()){
            if(num != 0){
                System.out.printf("%3d | ", num);
            }
            count++;
            if (count % 10 == 0) {
                System.out.println();
            }
        }
    }
    
    public EstadoJogo getEstadoJogo(){
        return estadoJogo;
    }

    public ArrayList<Integer> getNumsDisponiveis(){
        return this.numDisponiveis;
    }
}
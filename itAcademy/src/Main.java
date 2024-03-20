import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		System.out.println(boasVindas());

		Scanner sc = new Scanner(System.in);
		int opcao = sc.nextInt();

		switch (opcao) {
			case 1:
				Jogo jogo = new Jogo();
				break;
			default:
				System.out.println("Saindo do jogo");
		}
		sc.close();
	}

	private static String boasVindas(){
		return 
			"==============================\n" +
			"Bem-vindo ao novíssimo sistema de apostas!\n" +
			"Por favor, escolha uma opcao!             \n" +
			"1) Iniciar o Jogo                         \n" +
			"0) Sair                                   \n" +
			"==============================";
	}
}

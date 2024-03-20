import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		System.out.println(boasVindas());

		Scanner sc = new Scanner(System.in);

		int opcao = 0;

		// Apenas 0 e 1
		while (opcao != 1 && opcao != 2) {
			String entrada = sc.next();
			try {
				opcao = Integer.parseInt(entrada);
				if (opcao != 1 && opcao != 0) {
					System.out.println("Por favor, insira uma opção válida!\nOpção: ");
				}
			} catch (NumberFormatException e) {
				System.out.println("Por favor, insira uma opção válida!\nOpção: ");
			}
		}

		switch (opcao) {
			case 1:
				Jogo jogo = new Jogo();
				break;
			default:
				System.out.println("Saindo do jogo!");
		}
		sc.close();
	}

	private static String boasVindas() {
		return "==============================\n" +
				"Bem-vindo ao novíssimo sistema de apostas!\n" +
				"Por favor, escolha uma opcao!             \n" +
				"1) Iniciar o Jogo                         \n" +
				"2) Sair                                   \n" +
				"==============================";
	}
}

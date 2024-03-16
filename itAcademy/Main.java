import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Bem-vindo ao novissimo sistema de apostas!\nPor favor, escolha uma opcao!");
		System.out.println("1) Iniciar o Jogo\n0) Sair");

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
}

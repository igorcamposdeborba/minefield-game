import java.util.Scanner;

import model.Tabuleiro;
import view.Terminal;

public class Principal {
	public static void main(String [] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Digite o numero do tamanho do tabuleiro ");
		int table = input.nextInt();
		System.out.println("Digite o numero da quantidade de bombas ");
		int bomb = input.nextInt();
		while (bomb >= table) {
			System.out.println("Digite o numero da quantidade de bombas menor que o tamanho do tabuleiro ");
			bomb = input.nextInt();
		}
		
		Tabuleiro tabuleiro = new Tabuleiro(table, table, bomb);
		new Terminal(tabuleiro);
		
		System.out.println(tabuleiro);
	}
}

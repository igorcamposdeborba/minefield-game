package view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import exception.ExitException;
import exception.ExplosionException;
import model.Tabuleiro;

public class Terminal {
	private Tabuleiro tabuleiro;
	private Scanner scanner = new Scanner(System.in);
	
	public Terminal(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		runGame();
	}
	
	
	private void runGame() {
		try {
			boolean isToContinue = true;
			
			while (isToContinue == true) {
				
				System.out.println("Jogar jogo (S/n)? ");
				String reponse = scanner.nextLine();					
				
				
				if ("n".equalsIgnoreCase(reponse)) {
					isToContinue = false;
					throw new ExitException();
				} else {
					cycleGame();
				}
			}
		} catch (ExitException e) {
			System.out.println("Voce saiu do jogo");
		
		} finally {
			scanner.close();
		}
	}
	
	private void cycleGame() {
		try {
			
			while(tabuleiro.reachedObjective() == false) {
				System.out.println(tabuleiro);
				
				String digited = getDigitedValue("Digite o numero da LINHA e da COLUNA: x,y ");
				Iterator<Integer> xy = Arrays.stream(digited.split(","))
										.map(i -> Integer.parseInt(i.trim()))
										.iterator();
				
				digited = getDigitedValue("Digite o numero 1 para abrir ou 2 para (des)marcar ");
				if ("1".equals(digited)) {
					tabuleiro.open(xy.next(), xy.next());
				
				} else if ("2".equals(digited)) {
					tabuleiro.changeFlag(xy.next(), xy.next());
				}
			}
			
			System.out.println(tabuleiro);
			System.out.println("Voce ganhou o jogo. Parabens!");
			tabuleiro.restart();
		
		} catch (ExplosionException e) {
			System.out.println(tabuleiro);
			System.out.println("Voce perdeu o jogo");
		}
	}
	
	private String getDigitedValue(String text) {
		System.out.print(text);
		String digited = scanner.nextLine();
		
		if ("sair".equalsIgnoreCase(digited)) {
			throw new ExitException();
		}
		return digited;
	}
}

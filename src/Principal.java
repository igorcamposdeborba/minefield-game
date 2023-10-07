import model.Tabuleiro;
import view.Terminal;

public class Principal {
	public static void main(String [] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 1);
		new Terminal(tabuleiro);
		
		System.out.println(tabuleiro);
	}
}

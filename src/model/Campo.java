package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exception.ExplosionException;

public class Campo {
	private final int row,
					  column;
	private boolean mined = false;
	private boolean flagged = false;
	private boolean opened = false;
	
	private List<Campo> neighborList = new ArrayList<>(); // guardar vizinhos não minados deste field atual
	
	public Campo(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean addNeighbor(Campo neighbor) { // neighbor é um objeto (field) que tem atributos que descrevem se ele estã minado,
		boolean differentRow = row != neighbor.row;    //  aberto ou com flag e em qual posição do tabuleiro ele está (row, column).
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentRow && differentColumn;
		
		int deltaRow = Math.abs(this.row - neighbor.row);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int deltaGeneral = deltaRow + deltaColumn;
		
		boolean result = false;
		if (deltaGeneral == 1 && !diagonal) {
			neighborList.add(neighbor);
			result = true;
			
		} else if (deltaGeneral == 2 && diagonal) {
			neighborList.add(neighbor);
			result = true;
		}
		return result;
	}
	
	public void changeFlag() {
		if (opened == false) 
			flagged = !flagged;
	}
	
	public void mine() {
		mined = true;
	}
	
	public boolean open() throws ExplosionException {
		
		if (opened == false && flagged == false) { // happy path
			
			if (mined == true) { // game over
				throw new ExplosionException(); // exemplo de uso de exception que não é um erro, mas um corner case (que no caso é game over)
			}
			
			opened = true; // abrir campo
			
			if (secureNeighborhood() == true) {
				neighborList.forEach(i -> i.open()); // recursão para abrir (opened = true) todos os campos da lista de campos seguros deste objeto atual (o field));
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean secureNeighborhood() {
		return neighborList.stream().noneMatch(i -> i.mined == true);
	}
	
	public boolean objectiveReached() { // bloquar campo aberto ou marcado E com bomba
		boolean discovered = opened == true && mined == false;
		boolean markedAndHasBomb = flagged == true && mined == true;
		
		return discovered || markedAndHasBomb;
	}
	
	public long numberOfMinesInNeighborhood() {
		return (short) neighborList.stream().filter(i -> i.isMined()).count(); // mostrar o número de minas na vizinhança
	}
	
	public void restart() {
		opened = false;
		mined = false;
		flagged = false;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	public boolean isMined() {
		return mined;
	}
	public boolean isOpened() {
		return opened;
	}
	protected void setOpened(boolean opened) {
		this.opened = opened; 
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	public String toString() {
		final String ANSI_RESET = "\u001B[0m"; 
		final String ANSI_RED = "\u001b[43m"; 
		final String ANSI_BLUE = "\u001B[46m"; 
		
		String result = "?";
		
		if(flagged == true) {
			result = ANSI_RED + "X" + ANSI_RESET;
			
		} else if (opened == true && mined == true) {
			result = "*";
			
		} else if (opened == true && numberOfMinesInNeighborhood() > 0) {
			result = ANSI_BLUE + Long.toString(numberOfMinesInNeighborhood()) + ANSI_RESET;
		
		} else if (opened == true) {
			result = " ";
		}
		return result;
		
	}
}

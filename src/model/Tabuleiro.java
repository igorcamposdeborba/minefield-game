package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import exception.ExplosionException;

public class Tabuleiro {
	private int rows,
				columns,
				mines;
	
	private final List<Campo> camp = new ArrayList<>();
	
	
	public Tabuleiro(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		bindNeighbor();
		randomMines();
	}
	
	public void open(int row, int column) {
		try {
			camp.stream()
				.filter(i -> i.getRow() == row && i.getColumn() == column)
				.forEach(i -> i.open());
		} catch (ExplosionException e) {
			
			camp.forEach(i -> i.setOpened(true));
			throw e;
		}
	}
	
	public void changeFlag(int row, int column) {
		camp.stream()
				.filter(i -> i.getRow() == row && i.getColumn() == column)
				.forEach(i -> i.changeFlag());
	}
	
	private void generateFields() {
		for(int i=0; i < rows; i++) {
			for (int k=0; k < columns; k++) {
				camp.add(new Campo(i, k)); // adicionar cada field (linha e coluna) na lista camp
			}
		}
	}
	
	private void bindNeighbor() {
		for(Campo i : camp) {
			for (Campo j : camp) {
				i.addNeighbor(j); // associar vizinhos (addNeighbor valida se é um vizinho)
			}
		}
	}
	
	private void randomMines() {
		long armedMines = 0;
		Predicate<Campo> mined = i -> i.isMined(); 
		
		while(armedMines < mines) {
			int random = (int) (Math.random() * camp.size());			
			camp.get(random).mine();
			armedMines = camp.stream().filter(mined).count();
		}
	}
	
	public boolean reachedObjective() {
		return camp.stream().allMatch(i -> i.objectiveReached());
	}
	
	public void restart() {
		camp.stream().forEach(i -> i.restart());
		randomMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for(int i=0; i<columns; i++) {
			sb.append(" ");
			sb.append(i);
			sb.append(" ");
		}
		sb.append("\n");
		
		int actualField = 0;
		for(int i=0; i < rows; i++) {
			sb.append(i);
			sb.append(" ");
			
			for (int k=0; k < columns; k++) {
				sb.append(" ");
				sb.append(camp.get(actualField));
				sb.append(" ");
				
				actualField++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}

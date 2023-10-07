package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Campo {
	private final int row,
					  column;
	private boolean mined = false;
	private boolean flagged = false;
	private boolean opened = false;
	
	private List<Campo> neighborList = new ArrayList<>();
	
	public Campo(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean addNeighbor(Campo neighbor) {
		boolean differentRow = row != neighbor.row;
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
}

import java.util.Map;

public class Cell {

	public static enum direction {
		NORTH, SOUTH, EAST, WEST;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Cell Attributes
	private Map<direction, Cell> neighbors;
	private Character character;
	private Room room;
	private int col;
	private int row;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Cell(int aCol, int aRow, Room aRoom) {
		col = aCol;
		row = aRow;
		room = aRoom;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Map<direction, Cell> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Map<direction, Cell> neighbors) {
		this.neighbors = neighbors;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}		
	
	public String toString() {
		return "Yet to implement";
	}
}
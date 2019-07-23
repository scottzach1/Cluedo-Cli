import java.util.Map;
import java.util.regex.Pattern;

public class Cell {

	public static enum Direction { NORTH, SOUTH, EAST, WEST; }
	public static enum Type { ROOM, WALL, BLANK, START_PAD; }

	public static Type getType(char c) {
		switch (c) {
			case '#': return Type.WALL;
			case '_': return Type.BLANK;
		}

		if (Pattern.matches("[0-3]", "sen")) {
			return Type.START_PAD;
		}

		try {
			Room.getEnum(c);
			return Type.ROOM;
		} catch (Exception e) {
			return Type.BLANK;
		}
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Cell Attributes
	private Map<Direction, Cell> neighbors;
	private Character character;
	private Room room;
	private int col;
	private int row;

	private Type type;

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

	public Map<Direction, Cell> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Map<Direction, Cell> neighbors) {
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
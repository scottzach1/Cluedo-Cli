import java.util.HashMap;
import java.util.regex.Pattern;

public class Cell {

	public enum Direction {NORTH, SOUTH, EAST, WEST;}
	public enum Type {WALL, BLANK, START_PAD;}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Cell Attributes
	private Character character;
	private Room room;
	private int col;
	private int row;
	private Type type;
	private HashMap<Direction, Cell> neighbors;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Cell(int aCol, int aRow, Cell.Type aType) {
		col = aCol;
		row = aRow;
		type = aType;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Type getType() {return type;}

	public Character getCharacter() {return character;}
	public void setCharacter(Character character) {this.character = character;}

	public Room getRoom() {return room;}
	public void setRoom(Room room) {this.room = room;}

	public int getCol() {return col;}
	public int getRow() {return row;}
	
	public HashMap<Direction, Cell> getNeighbors(){return neighbors;}
	public void setNeighbor(Direction dir, Cell cell) {neighbors.put(dir, cell);}

	public static Type getType(char c) {
		if (c == '#') return Type.WALL;
		if (Pattern.matches("\\d", c + "")) return Type.START_PAD;
		return Type.BLANK;
	}	

	public String toString() {
		if (character != null) return character.toString();
		if (type == Type.BLANK) return "_";
		else if (type == Type.WALL) return "#";
		else if (type == Type.START_PAD) return "$";		
		return "ERROR ON TYPE";
	}
}
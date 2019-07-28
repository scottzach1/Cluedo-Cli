package src;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Cell {

	public enum Direction {NORTH, SOUTH, EAST, WEST}
	public enum Type {ROOM, WALL, BLANK, START_PAD}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Cell Attributes
	private Sprite sprite;
	private Room room;
	private int col;
	private int row;
	private Type type;
	private HashMap<Direction, Cell> neighbors;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Cell(int row, int col, Cell.Type aType) {
		this.row = row;
		this.col = col;
		type = aType;
		neighbors = new HashMap<>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Type getType() {return type;}

	public Sprite getSprite() {return sprite;}
	void setSprite(Sprite sprite) {this.sprite = sprite;}

	public Room getRoom() {return room;}
	void setRoom(Room room) {this.room = room;}

	int getCol() {return col;}
	int getRow() {return row;}
	
	public HashMap<Direction, Cell> getNeighbors(){return neighbors;}
	void setNeighbor(Direction dir, Cell cell) {neighbors.put(dir, cell);}

	static Type getType(char c) {
		if (c == '#') return Type.WALL;
		if (Pattern.matches("\\d", c + "")) return Type.START_PAD;
		if (Pattern.matches("[A-Z]", c + "")) return Type.ROOM;		
		return Type.BLANK;
	}

	public String printCoordinates() { return "" + ((char) (col + 'A')) + (row + 1); }

	public String toString() {
		if (sprite != null) return sprite.toString();
		if (type == Type.ROOM) return "_";
		if (type == Type.BLANK) return "_";
		else if (type == Type.WALL) return "#";
		else if (type == Type.START_PAD) return "$";		
		return "ERROR ON TYPE";
	}
}
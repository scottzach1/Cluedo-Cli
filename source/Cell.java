import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Cell {

	public enum Direction { NORTH, SOUTH, EAST, WEST; }
	public enum Type { ROOM, WALL, BLANK, START_PAD, WEAPON; }

	public static Type getType(char c) {
		switch (c) {
			case '#': return Type.WALL;
			case '_': return Type.BLANK;
			case 'W': return Type.WEAPON;
		}

		if (Pattern.matches("[0-5]", c + "")) {
			return Type.START_PAD;
		}

		try {
			Room.getEnum(c);
			return Type.ROOM;
		} catch (Exception e) {
			return Type.BLANK;
		}
	}

	Cell go(Direction dir) {
		Cell goal = null;

		switch (dir) {
			case EAST: 	goal = board.getCell(row, col + 1); break;
			case WEST: 	goal = board.getCell(row, col - 1); break;
			case NORTH:	goal = board.getCell(row - 1, col); break;
			case SOUTH: goal = board.getCell(row + 1, col); break;
		}

		// Only return cell if valid cell to land on.
		return (goal == null || (goal.type != Type.ROOM && goal.type != Type.BLANK)) ? null : goal;
	}

	Set<Cell> getNeighbours() {
		Set<Cell> neighbours = new HashSet<>();
		for (Direction direction : Direction.values()) {
			neighbours.add(go(direction));
		}
		return neighbours;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Cell Attributes
	private Board board;

	private Character character;
	private Weapon weapon;
	private Room room;
	private int col;
	private int row;

	private Type type;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Cell(int aCol, int aRow, Cell.Type aType, Board board) {
		col = aCol;
		row = aRow;
		type = aType;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Type getType() { return type; }

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	void setWeapon(Weapon weapon) { this.weapon = weapon; }
	Weapon getWeapon() { return weapon; }

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getCol() { return col; }
	public int getRow() { return row; }
	
	public String toString() {
		return "Yet to implement";
	}
}
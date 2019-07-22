import java.util.HashMap;
import java.util.Map;

public class Cell {

	public static enum direction {
		NORTH, SOUTH, EAST, WEST;
	}

	private int col, row;
	private Map<direction, Cell> neighbors;
	private char character;

	public Cell(int c, int r) {
		col = c;
		row = r;
		neighbors = new HashMap<>();
	}

	public void addNeighbor(direction dir, Cell neighbor) {
		neighbors.put(dir, neighbor);
	}
	
	public Cell getNeighbor(direction dir) {
		return neighbors.get(dir);
	}
	
	public void addCharacter(char c) {
		character = c;
	}
	
	public String toString() {
		String str = "_";	
		if (character != '\u0000') {
			str = "" + character + "";
		}
		return str.toString();
	}
}

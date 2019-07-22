package testingPackage;
import java.util.HashMap;
import java.util.Map;
public class CellTest {

	public static enum direction {
		NORTH, SOUTH, EAST, WEST;
	}

	private int col, row;
	private Map<direction, CellTest> neighbors;
	private char character;

	public CellTest(int c, int r) {
		col = c;
		row = r;
		neighbors = new HashMap<>();
	}

	public void addNeighbor(direction dir, CellTest neighbor) {
		neighbors.put(dir, neighbor);
	}
	
	public CellTest getNeighbor(direction dir) {
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

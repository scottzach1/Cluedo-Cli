import java.util.Map;

public class Testing_Code {
	
	public void main(String[]... args) {
		
	}

}

public class Cell{
	
	public static enum direction {
		NORTH, SOUTH, EAST, WEST;
	}
	
	private int col, row;
	private Map<direction, Cell> neighbors;
	
	public Cell(int c, int r) {
		col = c;
		row = r;
		neighbors = new HashMap<>();
	}
	
	public void addNeighbor(direction dir, Cell neighbor) {
		neighbors.put(dir, neighbor);
	}
	
	@Override
	public String toString() {
		
	}
	
	
	
	
}



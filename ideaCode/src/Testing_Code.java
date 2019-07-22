import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Testing_Code {

	public static void main(String args[]) {
		int cols = 24;
		int rows = 25;
		Map<String, Cell> board = new HashMap<>();

		Random rand = new Random();
		
		// Create board:
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Cell cell = new Cell(col, row);				
				String name = col + "," + row;
				board.put(name, cell);
				
				//Maybe put a person in it.
				int n = rand.nextInt(100);
				if (n < 6) {
					int ci = rand.nextInt(25);
					ci += 'a';
					char c = (char) ci;					
					cell.addCharacter(c);
				}
			}
		}

		// Add neighbors:
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				String key = col + "," + row;
				Cell curCell = board.get(key);

				String north = col + "," + (row - 1);
				String south = col + "," + (row + 1);
				String west = (col - 1) + "," + row;
				String east = (col + 1) + "," + row;

				if (row - 1 >= 0) {
					Cell otherCell = board.get(north);
					curCell.addNeighbor(Cell.direction.NORTH, otherCell);
				}
				if (row + 1 < rows) {
					Cell otherCell = board.get(south);
					curCell.addNeighbor(Cell.direction.SOUTH, otherCell);
				}
				if (col - 1 >= 0) {
					Cell otherCell = board.get(west);
					curCell.addNeighbor(Cell.direction.WEST, otherCell);
				}
				if (col + 1 < cols) {
					Cell otherCell = board.get(east);
					curCell.addNeighbor(Cell.direction.EAST, otherCell);
				}
			}
		}

		// Print board:
		System.out.print("     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _   \n");
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				
				if (col == 0) {
					String rowNum = row + "";
					while (rowNum.length() < 2) {
						rowNum = "0" + rowNum;
					}
					System.out.print(rowNum + "|");
				}
				
				
				String key = col + "," + row;
				Cell curCell = board.get(key);
				System.out.print(curCell.toString());
				
				
				System.out.print("|");
			}
			System.out.println();
		}

	}

}
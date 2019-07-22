import java.util.HashMap;
import java.util.Map;

public class Testing_Code {

	public static void main(String args[]) {
		int cols = 24;
		int rows = 25;
		Map<String, Cell> board = new HashMap<>();

		System.out.println("Insert your String here \033[1m Insert your String here \033[0m");
		for (int i=33; i<256; i++) {
			System.out.println(i + ": " + (char) i);
		}


		// Create board:
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Cell cell = new Cell(col, row);
				String name = col + "," + row;
				board.put(name, cell);
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
		for (int row = 0; row < rows; row++) {

			if (row == 0) {
				System.out.print(".");
				for (int col = 0; col < cols; col++) {
					System.out.print(" - .");
				}
			}

			System.out.print("\n|");

			for (int col = 0; col < cols; col++) {
				String key = col + "," + row;
				Cell curCell = board.get(key);
				Cell eastCell = curCell.getNeighbor(Cell.direction.EAST);
				
				if (eastCell != null)
					System.out.print("    ");
				else
					System.out.print("   |");
			}

			System.out.print("\n.");
			for (int col = 0; col < cols; col++) {
				String key = col + "," + row;
				Cell curCell = board.get(key);
				Cell southCell = curCell.getNeighbor(Cell.direction.SOUTH);
				
				if (southCell != null)
					System.out.print("   .");
				else
					System.out.print(" - .");
			}
		}
	}

}
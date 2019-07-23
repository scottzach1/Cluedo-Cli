import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Board Attributes
	private Map<Room.RoomAlias, Room> rooms;

	private Cell[][] cells;
	private int rows, cols;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board() {

		String fname = "MapBase.txt";

		rooms = new HashMap<>();

		for (Room.RoomAlias alais : Room.RoomAlias.values()) {
			rooms.put(alais, new Room(alais.toString()));
		}

		try {
			Scanner sc = new Scanner(new File(fname));

			if (!sc.next().equals("MAP")) throw new Exception("Invalid File Type");

			rows = sc.nextInt();
			cols = sc.nextInt();

			System.out.println("MAP " + rows + " " + cols);

			cells = new Cell[rows][cols];

			sc.next(); // skip '\r'
			sc.nextLine(); // _ _ _ _ _ ...

			for (int row = 0; row != rows; ++row) {
				String line = sc.nextLine();

				int lineIndex = 3; // 01 -> # <- |# ...

				for (int col = 0; col != cols; ++col, lineIndex += 2) {
					char c = line.charAt(lineIndex);


					Cell.Type type = Cell.getType(c);
					Cell cell = new Cell(row, col, type);
					cells[row][col] = cell;

					if (type == Cell.Type.ROOM) {
						cell.setRoom(rooms.get(Room.getEnum(c)));
					}

//					System.out.println(row + " " + col + ": " + c + " " + cell.getType());
				}
			}


		} catch (Exception e) { System.out.println("File Exception: " + e); }
	}

	public static void main(String args[]) {
		Board b = new Board();
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	
	public String toString() {
		return "not yet implemented";
	}
}
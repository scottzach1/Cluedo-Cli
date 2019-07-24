import java.io.File;
import java.util.*;

public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Board Attributes
	private Map<Room.RoomAlias, Room> rooms;
	private Map<Character.CharacterAlias, Character> characters;

	private Cell[][] cells;
	private int rows, cols;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board() {

		String fname = "MapBase.txt";

		// Setup Rooms
		rooms = new HashMap<>();

		for (Room.RoomAlias alias : Room.RoomAlias.values()) {
			rooms.put(alias, new Room(alias.toString()));
		}

		// Setup Characters
		characters = new HashMap<>();

		for (Character.CharacterAlias alias : Character.CharacterAlias.values()) {
			characters.put(alias, new  Character(alias.toString(), null));
		}

		try {
			Scanner sc = new Scanner(new File(fname));

			if (!sc.next().equals("MAP")) throw new Exception("Invalid File Type");

			rows = sc.nextInt();
			cols = sc.nextInt();

			System.out.println("MAP " + rows + " " + cols);

			cells = new Cell[rows][cols];

			sc.next(); // skip '\r'
			sc.nextLine(); // skip _ _ _ _ _ ...

			for (int row = 0; row != rows; ++row) {
				String line = sc.nextLine();

				int lineIndex = 3; // skip to cell 01 -> # <- |# ...

				for (int col = 0; col != cols; ++col, lineIndex += 2) {
					char c = line.charAt(lineIndex);

					Cell.Type type = Cell.getType(c);
					Cell cell = new Cell(row, col, type);
					cells[row][col] = cell;

					if (type == Cell.Type.ROOM) {
						cell.setRoom(rooms.get(Room.getEnum(c)));
					}

					// TODO: Add get cell.Direction. -> Is there any unique directionality?

//					System.out.println(row + " " + col + ": " + c + " " + cell.getType());
				}
			}


		} catch (Exception e) { System.out.println("File Exception: " + e); }
	}

	public Stack<Cell> getPath(Cell start, Cell end, int numSteps) {
		Stack<Cell> path = new Stack<>();
		path.push(start);
		return getPathHelper(path, end, numSteps);
	}

	public Stack<Cell> getPathHelper(Stack<Cell> path, Cell end, int numStepsLeft) {
		return null;
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
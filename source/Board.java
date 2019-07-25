import java.io.File;
import java.util.*;

public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Board Attributes
	private Map<Room.RoomAlias, Room> rooms;
	private Map<Character.CharacterAlias, Character> characters;
	private Map<Weapon.WeaponAlais, Weapon> weapons;

	private Cell[][] cells;
	private int rows, cols;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------
	Board() {

		// Generate room cards
		rooms = new HashMap<>();
		for (Room.RoomAlias alias : Room.RoomAlias.values()) {
			rooms.put(alias, new Room(alias.toString()));
		}

		// Generate character cards
		characters = new HashMap<>();
		for (Character.CharacterAlias alias : Character.CharacterAlias.values()) {
			characters.put(alias, new Character(alias.toString()));
		}

		// Generate Weapon cards
		weapons = new HashMap<>();
		for (Weapon.WeaponAlais alias : Weapon.WeaponAlais.values()) {
			weapons.put(alias, new Weapon(alias.toString()));
		}

		// Load Map Based off file layout
		try {
			String fname = "MapBase.txt";
			Scanner sc = new Scanner(new File(fname));

			if (!sc.next().equals("MAP"))
				throw new Exception("Invalid File Type");

			// First two indexes = Row Col of map
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

					if (type == Cell.Type.START_PAD) {
						try {
							Character currentCharacter = characters.get(Character.parseAliasFromOrdinal(c));
							currentCharacter.setPosition(cell);
							cell.setCharacter(currentCharacter);
							// DEBUG:
							System.out.println(row + " " + col + ": " + c + " " + cell.getType());
							System.out.println(currentCharacter.getName());
						} catch (Exception e) {
							System.out.println("Not a number cell!");
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("File Exception: " + e);
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Map<Character.CharacterAlias, Character> getCharacters() {
		return characters;
	}

	public Map<Room.RoomAlias, Room> getRooms() {
		return rooms;
	}

	public Map<Weapon.WeaponAlais, Weapon> getWeapons() {
		return weapons;
	}

	public Stack<Cell> getPath(Cell start, Cell end, int numSteps) {
		Stack<Cell> path = new Stack<>();
		path.push(start);
		return getPathHelper(path, end, numSteps);
	}

	private Stack<Cell> getPathHelper(Stack<Cell> path, Cell end, int numStepsLeft) {
		// TODO: Implement path finding.
		return null;
	}

	public Cell getCell(int row, int col) {
		if (row < 0 || row >= rows - 1)
			return null;
		if (col < 0 || col >= cols - 1)
			return null;

		return cells[row][col];
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void printBoardState() {
		System.out.println("\t\t   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
		for (int row = 0; row < rows; row++) {
			System.out.print("\t\t");
			for (int col = 0; col < cols; col++) {
				// Print the row number at the start of each line
				if (col == 0) {
					String factoredRowNum = row+"";
					while (factoredRowNum.length() < 2)
						factoredRowNum = "0"+factoredRowNum;
					System.out.print(factoredRowNum + "|");
				}
				
				System.out.print(cells[row][col].toString());
				System.out.print("|");
			}
			//New line for every row
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		Board b = new Board();
		b.printBoardState();
	}
}
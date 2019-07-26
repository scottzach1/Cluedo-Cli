import java.util.*;

/* Created by Harrison Cook and Zac Scott - 2019 */

/**
 * Game: Controls the steps of the game, creation of objects, and run time
 * actions.
 */
public class CluedoGame {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Attributes
	private Board board;
	private List<User> users;
	private Card[] solution;
	private LUI lui;
	private Card[] observations;
	private String status;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	private CluedoGame() {
		status = "";
		lui = new LUI();
		board = new Board();
		this.users = new ArrayList<>();

		// Create solution
		generateSolution();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	private void gameController() {
		// Run the main menu first
		mainMenu();
		// If the player has chosen to quit
		if (status.equals("3"))
			return;

		// Next set up the game
		gameSetup();

		// Play the rounds
		rounds();
	}

	private void mainMenu() {
		// GAME MENU (below) --------------------------------------------------
		status = "";

		// Start up menu
		while (!status.equals("1")) {
			// Intro:
			status = lui.startUpMenu();

			// Switch cases of status, 1: Play, 2: How to Play, 3: Quit
			if (status.contentEquals("2")) {
				status = lui.howToPlay() + "MENU";
			} else if (status.contentEquals("3")) {
				System.out.println("Thanks for playing");
				return;
			}
		}

	}

	private void gameSetup() {
		LUI.loading("");

		status = lui.gameSetup();

		// USER INFORMATION (below) --------------------------------------------------

		String[] components = status.split("\\W");

		/**
		 * Format of components: [0] String : "Players" [1] Integer : Player count [2]
		 * String: "Player" [3] Integer : Player number [4] String: "Character" [5]
		 * CharacterAlias : Players Character
		 */

		int playerCount = 0;
		try {
			playerCount = Integer.parseInt(components[1]);
		} catch (Exception e) {
			LUI.loading("ERROR ON LOADING GAME");
			gameController();
			return;
		}

		// Get rid of the first two (now useless) bits of information
		String[] userInformation = Arrays.copyOfRange(components, 2, components.length);

		for (int user = 0; user < playerCount; user++) {

			// This is the users entered number
			int userNumber = 0;
			try {
				userNumber = Integer.parseInt(userInformation[1 + (6 * user)]);
			} catch (Exception e) {
				LUI.loading("ERROR ON LOADING GAME");
				gameController();
				return;
			}

			// The users entered name preference
			String userName = userInformation[3 + (6 * user)];

			// This is the users character alias
			String userCharacterName = userInformation[5 + (6 * user)];
			Character.CharacterAlias userCharacterAlias = Character.CharacterAlias.valueOf(userCharacterName);

			// Create a new user object and store in the games list of users
			User userObj = new User();
			userObj.setUserName(userName);
			userObj.setCharacter(board.getCharacters().get(userCharacterAlias));
			users.add(userObj);
		}

		// Create users hands
		if (users.size() > 2)
			generateHands();
	}

	private void rounds() {
		// Run the game by doing rounds, int user is the users turn
		String error = "";
		int userNum = 0;
		while (!status.equals("9")) {
			LUI.clearConsole();
			board.printBoardState();
			User user = users.get(userNum);
			Cell position = user.getCharacter().getPosition();

			if (position.getType() == Cell.Type.ROOM)
				System.out.println(position.getRoom().toString());

			status = lui.round(user, error);
			error = "";
			// 1: Move, 2: Hand, 3: Observations, 4: Suggest, 5: Accuse (Solve), 8: Next
			// User, 9: Quit Game

			if (status.length() == 0)
				continue;

			if (status.charAt(0) == '1') {
				String[] components = status.split("-");
				try {
					int row = Integer.parseInt(components[1]);
					int col = Integer.parseInt(components[2]);
					Cell cell = board.getCell(row, col);
					int diceRoll = Integer.parseInt(components[3]);
					status = makeMove(cell, diceRoll, user);
				} catch (RuntimeException rt) {
					error = "[" + components[1] + "][" + components[2] + "]" + " can not be reached";
				} catch (Exception e) {
					error = "[" + components[1] + "][" + components[2] + "]" + " is not a valid cell";
				}
			}

			// [8] Change the user after prev user has exited their turn
			if (status.equals("8")) {
				userNum = (userNum + 1) % users.size();
			}
			// [9] Finish the game by exiting this while loop
			if (status.equals("9")) {
				System.out.println("Thanks for playing");
			}
		}
	}

	private String makeMove(Cell end, int diceRoll, User user) throws Exception {
		Cell start = user.getCharacter().getPosition();
		if (board.calcPath(start, end, diceRoll)) {
			board.moveCharacter(user, start, end);
			return "8";
		}
		throw new RuntimeException("Invalid Move - Not enough steps");

	}

	private void generateSolution() {
		solution = new Card[3];
		Random random = new Random();

		Room room = board.getRooms().get(Room.parseAliasFromOrdinalInt(random.nextInt(9)));
		Character character = board.getCharacters().get(Character.parseAliasFromOrdinalInt(random.nextInt(6)));
		Weapon weapon = board.getWeapons().get(Weapon.parseAliasFromOrdinalInt(random.nextInt(6)));

		solution[0] = character;
		solution[1] = weapon;
		solution[2] = room;

	}

	private void generateHands() {
		Map<Character.CharacterAlias, Character> nonSolutionCharacters = new HashMap<>(board.getCharacters());
		Map<Weapon.WeaponAlias, Weapon> nonSolutionWeapons = new HashMap<>(board.getWeapons());
		Map<Room.RoomAlias, Room> nonSolutionRooms = new HashMap<>(board.getRooms());

		nonSolutionCharacters.remove(((Character) solution[0]).getCharAlias());
		nonSolutionWeapons.remove(((Weapon) solution[1]).getWeaponAlias());
		nonSolutionRooms.remove(((Room) solution[2]).getRoomAlias());

		int userNum = 0;

		for (Character c : nonSolutionCharacters.values()) {
			User user = users.get(userNum);
			user.addToHand(c);
			userNum = (userNum + 1) % users.size();
		}

		for (Weapon w : nonSolutionWeapons.values()) {
			User user = users.get(userNum);
			user.addToHand(w);
			userNum = (userNum + 1) % users.size();
		}

		for (Room r : nonSolutionRooms.values()) {
			User user = users.get(userNum);
			user.addToHand(r);
			userNum = (userNum + 1) % users.size();
		}

	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		g.gameController();
	}

}
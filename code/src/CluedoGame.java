package src;

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
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	private void gameController() {

		while (!status.equals("3")) {
			board = new Board();
			this.users = new ArrayList<>();

			// Create solution
			generateSolution();
			
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

		String[] components = status.split(":");
		
		/**
		 * Format of components: [0] String : "Players", [1] Integer : playerCount, [2 *
		 * playerCount + 2] String: "Player", [3 * playerCount + 2] Integer :
		 * userNumber, [4 * playerCount + 2] String : "UserName", [5 * playerCount + 2]
		 * String : userName, [6 * playerCount + 2] String : "Sprite" [7 *
		 * playerCount + 2] SpriteAlias : Players Sprite
		 */

		int playerCount = 0;
		try {
			playerCount = Integer.parseInt(components[1]);
		} catch (Exception e) {
			LUI.loading("ERROR ON LOADING GAME: Invalid playerCount" + components[1]);
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
				LUI.loading("ERROR ON LOADING GAME: Invalid UserNumber" + userInformation[1 + (6 * user)]);
				gameController();
				return;
			}

			// The users entered name preference
			String userName = userInformation[3 + (6 * user)];

			// This is the users character alias
			String userCharacterName = userInformation[5 + (6 * user)];
			Sprite.SpriteAlias userSpriteAlias = Sprite.SpriteAlias.valueOf(userCharacterName);

			// Create a new user object and store in the games list of users
			User userObj = new User();
			userObj.setUserName(userName);
			userObj.setSprite(board.getCharacters().get(userSpriteAlias));
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
			// Refresh after option selection --------
			LUI.clearConsole();
			board.printBoardState();
			User user = users.get(userNum);
			Cell position = user.getSprite().getPosition();

			if (position.getType() == Cell.Type.ROOM)
				System.out.println(position.getRoom().toString());

			status = lui.round(user, error);
			error = "";
			// 1: Move, 2: Hand, 3: Observations, 4: Suggest, 5: Accuse (Solve), 8: Next
			// User, 9: Quit Game
			
			int playerChoice = status.charAt(0);

			// [1] Move the player
			if (playerChoice == '1') {
				String[] components = status.split(":");
				try {
					// Already confirmed row and col, but already doing the try catch, so might as
					// well put here
					int row = Integer.parseInt(components[1]);
					int col = Integer.parseInt(components[2]);
					int diceRoll = Integer.parseInt(components[3]);
					// Get the cell, if null then....
					Cell cell = board.getCell(row, col);
					// Make move will break the try
					status = makeMove(cell, diceRoll, user);
				} catch (RuntimeException rt) {
					error = "[" + components[0] + "][" + components[1] + "]" + " can not be reached";
				} catch (Exception e) {
					error = "[" + components[0] + "][" + components[1] + "]" + " is not a valid cell";
				}
			}
			
			// [2] refresh (do nothing)
			
			//

			// [8] Change the user after prev user has exited their turn
			if (playerChoice == '8') {
				userNum = (userNum + 1) % users.size();
			}
			// [9] Finish the game by exiting this while loop
			if (playerChoice == '9') {
				System.out.println("Thanks for playing");
			}
			
			if (!java.lang.Character.isDigit(playerChoice));
		}
	}


	private String makeMove(Cell end, int diceRoll, User user) throws Exception {

		// Throw an exception is the cell is null
		if (end == null)
			throw new NullPointerException("Undefined position (off the board)");

		Cell start = user.getSprite().getPosition();
		if (board.calcValidPath(start, end, diceRoll)) {
			board.moveCharacter(user, start, end);
			return "8";
		}
		throw new RuntimeException("Invalid Move - Not enough steps");
	}

	private void generateSolution() {
		solution = new Card[3];
		Random random = new Random();

		Room room = board.getRooms().get(Room.parseAliasFromOrdinalInt(random.nextInt(9)));
		Sprite sprite = board.getCharacters().get(Sprite.parseAliasFromOrdinalInt(random.nextInt(6)));
		Weapon weapon = board.getWeapons().get(Weapon.parseAliasFromOrdinalInt(random.nextInt(6)));

		solution[0] = sprite;
		solution[1] = weapon;
		solution[2] = room;

	}

	private void generateHands() {
		Map<Sprite.SpriteAlias, Sprite> nonSolutionCharacters = new HashMap<>(board.getCharacters());
		Map<Weapon.WeaponAlias, Weapon> nonSolutionWeapons = new HashMap<>(board.getWeapons());
		Map<Room.RoomAlias, Room> nonSolutionRooms = new HashMap<>(board.getRooms());

		nonSolutionCharacters.remove(((Sprite) solution[0]).getSpriteAlias());
		nonSolutionWeapons.remove(((Weapon) solution[1]).getWeaponAlias());
		nonSolutionRooms.remove(((Room) solution[2]).getRoomAlias());

		int userNum = 0;

		for (Sprite c : nonSolutionCharacters.values()) {
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
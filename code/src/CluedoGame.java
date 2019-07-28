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
	private PathFinder pathFinder;
	private List<User> users;
	private List<User> losers;
	private Card[] solution;
	private LUI lui;
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
			pathFinder = new PathFinder(board);
			this.users = new ArrayList<>();
			this.losers = new ArrayList<>();

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
				status = lui.howToPlay() + "NOT 1";

			} else if (status.contentEquals("3")) {
				System.out.println("Thanks for playing");
				return;
			}
		}

	}

	private void gameSetup() {
		LUI.loading();

		status = lui.gameSetup();

		// USER INFORMATION (below) --------------------------------------------------

		String[] components = status.split(":");

		/**
		 * Format of components: [0] String : "Players", [1] Integer : playerCount, [2 *
		 * playerCount + 2] String: "Player", [3 * playerCount + 2] Integer :
		 * userNumber, [4 * playerCount + 2] String : "UserName", [5 * playerCount + 2]
		 * String : userName, [6 * playerCount + 2] String : "Sprite" [7 * playerCount +
		 * 2] SpriteAlias : Players Sprite
		 */

		int playerCount = 0;
		try {
			playerCount = Integer.parseInt(components[1]);
		} catch (Exception e) {
			// Pre-checked integer, if failed here, java / programmer error
			LUI.printError(components[1], "is not an integer");
			LUI.loading();
			gameController();
			return;
		}

		// Get rid of the first two (now useless) bits of information
		String[] userInformation = Arrays.copyOfRange(components, 2, components.length);

		for (int user = 0; user < playerCount; user++) {

			// The users entered name preference
			String userName = userInformation[1 + (4 * user)];

			// This is the users character alias
			String userCharacterName = userInformation[3 + (4 * user)];
			Sprite.SpriteAlias userSpriteAlias = Sprite.SpriteAlias.valueOf(userCharacterName);

			// Create a new user object and store in the games list of users
			User userObj = new User();
			userObj.setUserName(userName);
			userObj.setSprite(board.getSprites().get(userSpriteAlias));
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
		LUI.rollDice();

		// Whilst a player has not quit
		while (!status.equals("9")) {

			// Refresh the in game menu panel
			LUI.clearConsole();
			board.printBoardState();

			// Get the user and their position
			User user = users.get(userNum);
			Cell position = user.getSprite().getPosition();

			if (losers.contains(user)) {
				continue;
			}

			// If they are in a room, display the rooms information too
			if (position.getType() == Cell.Type.ROOM)
				System.out.println(position.getRoom().toString());

			// Get the current users choice of what they want to do
			status = lui.round(user, error);
			error = "";

			// 1: Move, 2: Hand, 3: Observations, 4: Suggest, 5: Accuse (Solve), 8: Next
			// User, 9: Quit Game

			// [1] Move the player
			if (status.equals("1")) {
				LUI.clearConsole();
				board.printBoardState();
				status = lui.movePlayer(user);	
				try {
					// Get the cell, if null then....
					Cell cell = board.getCell(status);
					// Make move will break the try
					status = tryMove(cell, LUI.getDiceRoll(), user);
				} catch (NullPointerException np) {
					error = "Board cannot find position " + status;
				} catch (RuntimeException rt) {
					error = "Location can't be reached. \n\t  You can only move " + LUI.getDiceRoll() + " steps";
				} catch (Exception e) {
					error = "Unknown Error";
				}
			}

			// [2] refresh to menu (do nothing)
			if (status.equals("2")) {
				status = lui.showHand(user);
			}
			// [3] refresh to menu (do nothing)
			if (status.equals("3")) {
				status = lui.showObservations(user);
			}

				// [4] check if the next players have a possible card
			if (status.equals("4")) {
				if (user.getSprite().getPosition().getType() != Cell.Type.ROOM) {
					lui.readInput("You are not in a room, thus you can't make a suggestion \n-[Any] Okay", user.getUserName());
				} else {
					status = lui.selectThreeCards(user, "8");
					String[] components = status.split(":");
					boolean cardFound = false;
					if (components.length == 4) {
						Sprite s = board.getSprites().get(Sprite.SpriteAlias.valueOf(components[1]));
						Weapon w = board.getWeapons().get(Weapon.WeaponAlias.valueOf(components[2]));
						Room r = board.getRooms().get(Room.RoomAlias.valueOf(components[3]));

						for (int u = 0; u < users.size() - 1; u++) {
							ArrayList<Card> options = new ArrayList<>();
							// Get user next in the line
							int otherUserNum = (userNum + 1) % users.size();
							User other = users.get(otherUserNum);
							// If the other user has one of the suggested cards add it to the list of
							// options
							if (other.getHand().contains(s)) {
								options.add(s);
							}
							if (other.getHand().contains(w)) {
								options.add(w);
							}
							if (other.getHand().contains(r)) {
								options.add(r);
							}

							// Can only get one card from the closest user to you
							if (options.size() > 0) {
								Card choosenCard = lui.chooseCardToGiveAway(other, options);

								System.out.println(
										other.getUserName() + " has shown " + user.getUserName() + " the card " + "\n\t"
												+ choosenCard.getClass().getName() + ": " + choosenCard.getName());
								lui.readInput("-[ANY] Sweet as", user.getUserName());

								cardFound = true;
								break;
							}

						}

						if (!cardFound) {
							lui.readInput("No card found for your suggestion: \n" + s.getName() + " - " + w.getName()
									+ " - " + r.getName(), user.getUserName());
						}

						status = "8";
					}
				}
			}

			if (status.equals("5")) {
				lui.selectThreeCards(user, "9");
				String[] components = status.split(":");
				if (components.length == 4) {
					Sprite s = board.getSprites().get(Sprite.SpriteAlias.valueOf(components[1]));
					Weapon w = board.getWeapons().get(Weapon.WeaponAlias.valueOf(components[2]));
					Room r = board.getRooms().get(Room.RoomAlias.valueOf(components[3]));

					if (s.equals(solution[0]) && w.equals(solution[1]) && r.equals(solution[2])) {
						System.out.println("\n------------------------------------------------");
						System.out.println("The Best Detective and WINNER of this game is: " + user.getUserName());
						System.out.println("------------------------------------------------\n");
					} else {
						lui.readInput(user.getUserName()
								+ "Your accusation is incorrect, you can no longer win the game \n\n-[Any] Awwhh man",
								user.getUserName());
					}
					status = "8";
				}
			}

			// [8] Change the user after prev user has exited their turn
			if (status.equals("8")) {
				userNum = (userNum + 1) % users.size();
				LUI.rollDice();
			}

			// [9] Finish the game by exiting this while loop
			if (status.equals("9")) {
				System.out.println("Thanks for playing");
			}

			if (status.length() > 0 && !java.lang.Character.isDigit(status.charAt(0))) {
				error = "UNKNOWN INPUT";
			}
		}

	}

	private String tryMove(Cell end, int diceRoll, User user) throws Exception {

		// Throw an exception is the cell is null
		if (end == null)
			throw new NullPointerException();

		Cell start = user.getSprite().getPosition();

		if (pathFinder.checkValidPath(start, end, diceRoll)) {
			board.moveUser(user, start, end);
			return "8";
		}
		throw new RuntimeException();
	}

	private void generateSolution() {
		solution = new Card[3];
		Random random = new Random();

		Room room = board.getRooms().get(Room.parseAliasFromOrdinalInt(random.nextInt(9)));
		Sprite sprite = board.getSprites().get(Sprite.parseAliasFromOrdinalInt(random.nextInt(6)));
		Weapon weapon = board.getWeapons().get(Weapon.parseAliasFromOrdinalInt(random.nextInt(6)));

		solution[0] = sprite;
		solution[1] = weapon;
		solution[2] = room;

	}

	private void generateHands() {
		Map<Sprite.SpriteAlias, Sprite> nonSolutionSprites = new HashMap<>(board.getSprites());
		Map<Weapon.WeaponAlias, Weapon> nonSolutionWeapons = new HashMap<>(board.getWeapons());
		Map<Room.RoomAlias, Room> nonSolutionRooms = new HashMap<>(board.getRooms());

		nonSolutionSprites.remove(((Sprite) solution[0]).getSpriteAlias());
		nonSolutionWeapons.remove(((Weapon) solution[1]).getWeaponAlias());
		nonSolutionRooms.remove(((Room) solution[2]).getRoomAlias());

		int userNum = 0;

		for (Sprite s : nonSolutionSprites.values()) {
			User user = users.get(userNum);
			user.addToHand(s);
			user.addToObservedCards(s);
			userNum = (userNum + 1) % users.size();
		}

		for (Weapon w : nonSolutionWeapons.values()) {
			User user = users.get(userNum);
			user.addToHand(w);
			user.addToObservedCards(w);
			userNum = (userNum + 1) % users.size();
		}

		for (Room r : nonSolutionRooms.values()) {
			User user = users.get(userNum);
			user.addToHand(r);
			user.addToObservedCards(r);
			userNum = (userNum + 1) % users.size();
		}

	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		g.gameController();
	}

}
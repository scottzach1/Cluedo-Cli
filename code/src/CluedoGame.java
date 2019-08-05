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

	/**
	 * CluedoGame: Constructor
	 */
	private CluedoGame() {
		status = "";
		lui = new LUI();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * gameController: Maintains the order of the game
	 */
	private void gameController() {

		while (!status.equals("3")) {
			User.resetUserNoCounter();
			board = new Board();
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

			while (!status.equals("1") && !status.equals("2"))
				status = lui.readInput("\nPlay again?\n-[1] YEAH!\n -[2] Not today", "USER");

			if (status.contentEquals("2"))
				return;
		}
	}

	/**
	 * mainMenu: Orders and runs the LUI methods for the main menu to print, and
	 * acts upon user input
	 */
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

	/**
	 * gameSetup: Orders and runs the LUI methods for the game set up stage to
	 * print, and acts upon user input
	 */
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

	/**
	 * rounds: Acts like a forever loop, controlling what the LUI prints and acts
	 * upon user inputs
	 */
	/**
	 *
	 */
	private void rounds() {
		// Run the game by doing rounds, int user is the users turn
		String error = "";
		int userNum = 0;
		lui.rollDice();

		// Whilst a player has not quit
		while (!status.equals("9")) {

			// Refresh the in game menu panel
			LUI.clearConsole();

			// Get the user and their position
			User user = users.get(userNum);
			Cell position = user.getSprite().getPosition();

			// Check the play can continue
			if (users.size() == losers.size()) {
				System.out.println("\n------------------------------------------------");
				System.out.println("Unfortunately, no one can win the game :/");
				System.out.println("------------------------------------------------\n");
				status = "9";
				continue;
			}

			// Don't let players out of the game play
			if (losers.contains(user)) {
				userNum = (userNum + 1) % users.size();
				lui.rollDice();
				continue;
			}

			// Display board and moves
			board.printBoardState();
			System.out.println("\nMoves left: " + lui.getDiceRoll());

			// If they are in a room, display the rooms information too
			if (position.getType() == Cell.Type.ROOM)
				System.out.println(position.getRoom().toString());

			// Get the current users choice of what they want to do
			status = lui.round(user, error);
			error = "";

			// This number is used later, needs to be preserved here
			if (status.equals("6"))
				continue;

			// 1: Move, 2: Hand, 3: Observations, 4: Suggest, 5: Accuse (Solve), 8: Next
			// User, 9: Quit Game

			// [1] Move the player
			if (status.equals("1")) {

				if (lui.getDiceRoll() == 0) {
					error = "No moves left!\n\tChoose another option";
				}

				while (status.equals("1")) {
					// Clear screen
					LUI.clearConsole();
					board.printBoardState();
					System.out.println("\nMoves left: " + lui.getDiceRoll());

					// Print user error
					if (error.length() > 0) {
						if (error.charAt(0) == '1')
							LUI.printError("", "Not a valid cell on the board");
						if (error.charAt(0) == '2')
							LUI.printError("",
									"Move type was exact. \n\t There is no possible way to get there exactly.\n\t Try using shortest path");
						if (error.charAt(0) == '3')
							LUI.printError("", "Out of reach, check dice roll");
						if (error.charAt(0) == '4')
							LUI.printError("", "Please use the form 'H18'");
					}

					// Get the type of move, exact or shortest path
					String moveType = lui.moveType(user);

					if (moveType.equals("1")) {
						status = "1";
						break;
					}

					// Clear screen
					LUI.clearConsole();
					board.printBoardState();
					System.out.println("\nMoves left: " + lui.getDiceRoll());

					// get the users cell wanting to move to
					status = lui.movePlayer(user);

					if (status.equals("MENU")) {
						status = "1";
						break;
					}

					try {
						// Get the cell, if null then....
						Cell cell = board.getCell(status);
						// Make move will break the try
						status = tryMove(cell, user, moveType);
					} catch (NullPointerException np) {
						error = "1-NULLPOINTER ERROR";
						status = "1";
					} catch (IllegalArgumentException ae) {
						error = "2-ILLEGAL ERROR";
						status = "1";
					} catch (RuntimeException rt) {
						error = "3-RUNTIME ERROR";
						status = "1";
					} catch (Exception e) {
						error = "4-UNKNOWN ERROR";
						status = "1";
					}
				}
				error = "";
			}

			// [2] Show the users hand refresh to menu (do nothing)
			if (status.contentEquals("2")) {
				lui.showHand(user);
			}
			// [3] Show the users observations refresh to menu (do nothing)
			if (status.contentEquals("3")) {
				lui.showObservations(user);
			}

			// [4] check if the next players have a possible card
			if (status.equals("4")) {
				// Rule: Players must be in the room to make an suggestion
				if (user.getSprite().getPosition().getType() != Cell.Type.ROOM) {
					lui.readInput("You are not in a room, thus you can't make a suggestion \n-[Any] Okay",
							user.getUserName());
				}
				// If the player is in a room
				else {
					// select the three cards in 'NO_ROOM' mode, you can't select a room
					status = lui.selectThreeCards(user, "NO_ROOM");

					// Split the returned string into the three card strings
					String[] components = status.split(":");
					boolean cardFound = false;

					// If three cards are returned then
					if (components.length == 3) {
						// Get the three characters
						Sprite s = board.getSprites().get(Sprite.SpriteAlias.valueOf(components[0]));
						Weapon w = board.getWeapons().get(Weapon.WeaponAlias.valueOf(components[1]));
						Room r = board.getRooms().get(Room.RoomAlias.valueOf(components[2]));

						// Move the sprite to this room (random spot)
						for (Cell c : r.getCells()) {
							if (c.getSprite() == null) {
								s.setPosition(c);
								break;
							}
						}
						// Swap the places of the weapon in the room and the selected weapon
						if (r.getWeapon() != null)
							r.getWeapon().setRoom(w.getRoom());
						w.setRoom(r);

						// For each user, get a list of cards that match this suggestion
						// If a user has at least one card that matches, break
						for (int u = 1; u < users.size() - 1; u++) {
							ArrayList<Card> options = new ArrayList<>();
							// Get user next in the line
							int otherUserNum = (userNum + u) % users.size();
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

								user.addToObservedCards(choosenCard);

								cardFound = true;
								break;
							}

						}

						if (!cardFound) {
							lui.readInput("No card found for your suggestion: \n" + s.getName() + " - " + w.getName()
									+ " - " + r.getName(), user.getUserName());
						}

						// End the turn
						status = "8";
						lui.removeMovesFromRoll(lui.getDiceRoll());
					}
				}
			}

			// [5] AN ACCUSATION! if the player has the solution right, then end the game,
			// and congratulate them
			// If a player has the accusation wrong, add them to the losers list removing
			// their turn from
			// circulation.
			if (status.equals("5")) {
				// Get three cards allowing them to select a room
				status = lui.selectThreeCards(user, "ROOM");
				String[] components = status.split(":");

				// If the user has entered three cards
				if (components.length == 3) {
					// Get the three cards
					Sprite s = board.getSprites().get(Sprite.SpriteAlias.valueOf(components[0]));
					Weapon w = board.getWeapons().get(Weapon.WeaponAlias.valueOf(components[1]));
					Room r = board.getRooms().get(Room.RoomAlias.valueOf(components[2]));

					// Check if the user has the answer, end the game if so ...
					if (s.equals(solution[0]) && w.equals(solution[1]) && r.equals(solution[2])) {
						System.out.println("\n------------------------------------------------");
						System.out.println("The Best Detective and WINNER of this game is: " + user.getUserName());
						System.out.println("------------------------------------------------\n");
						return;
					}
					// ... else put them on the losers list, no longer allowed to play
					else {
						lui.readInput(user.getUserName()
								+ " your accusation is incorrect, you can no longer win the game \n\n-[Any] Awwhh man",
								user.getUserName());
						losers.add(user);
					}
					// End the turn
					status = "8";
					lui.removeMovesFromRoll(lui.getDiceRoll());
				}
			}

			// Not an option, checking shortest path has not used all the players moves
			if (status.equals("6")) {
				if (user.getSprite().getPosition().getType() == Cell.Type.ROOM
						|| (lui.getOriginalDiceRoll() - lui.getDiceRoll()) > 0) {
					continue;
				} else {
					status = "8";
				}
			}

			// [8] Change the user after prev user has exited their turn
			if (status.equals("8")) {
				if (lui.getDiceRoll() == lui.getOriginalDiceRoll() || lui.getDiceRoll() == 0 || user.getSprite().getPosition().getType() == Cell.Type.ROOM) {
					userNum = (userNum + 1) % users.size();
					if (losers.size() != 1)
						lui.rollDice();
					continue;
				} else {
					error = user.getUserName() + ", you need to use the rest of you moves";
				}
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

	/**
	 * tryMove: Uses the PathFinder test is a suggested path is feasible
	 *
	 * @param end      - Target Cell
	 * @param diceRoll - Moves player can make
	 * @param user     - Who is attempting the move
	 * @return String - "8" allowing for the next player to take their turn if
	 *         successful
	 */
	private String tryMove(Cell end, User user, String moveType) {

		// Throw an exception is the cell is null
		if (end == null || user == null)
			throw new NullPointerException();

		// Get the starting cell (character position)
		Cell start = user.getSprite().getPosition();

		// Check that path is valid
		pathFinder = new PathFinder(board);
		int movesUsed = -1;

		// Shortest path option
		if (moveType.equals("3")) {
			if ((movesUsed = pathFinder.findShortestPath(start, end)) <= lui.getDiceRoll()) {
				board.moveUser(user, end);
				lui.removeMovesFromRoll(movesUsed);
				return "6";
			}
			throw new RuntimeException();
		}

		// Exact path option
		if (pathFinder.findExactPath(start, end, lui.getDiceRoll())) {
			board.moveUser(user, end);
			lui.removeMovesFromRoll(lui.getDiceRoll());
			return "8";
		}
		throw new IllegalArgumentException();
	}

	/**
	 * generateSolution: Randomly selects 3 cards, 1 Player, 1 Weapon, 1 Room, and
	 * places in the field array 'solution'
	 */
	private void generateSolution() {
		// Create new array (empty array), make random object
		solution = new Card[3];
		Random random = new Random();

		// Randomly get a room, sprite, weapon from the board
		Room room = board.getRooms().get(Room.parseAliasFromOrdinalInt(random.nextInt(9)));
		Sprite sprite = board.getSprites().get(Sprite.parseAliasFromOrdinalInt(random.nextInt(6)));
		Weapon weapon = board.getWeapons().get(Weapon.parseAliasFromOrdinalInt(random.nextInt(6)));

		// Set the three cards
		solution[0] = sprite;
		solution[1] = weapon;
		solution[2] = room;
	}

	/**
	 * generateHands: Randomly deal out the cards that are not solution cards
	 */
	private void generateHands() {
		// Get the list for each sprite, weapon, room
		Map<Sprite.SpriteAlias, Sprite> nonSolutionSprites = new HashMap<>(board.getSprites());
		Map<Weapon.WeaponAlias, Weapon> nonSolutionWeapons = new HashMap<>(board.getWeapons());
		Map<Room.RoomAlias, Room> nonSolutionRooms = new HashMap<>(board.getRooms());

		// Remove solution cards
		nonSolutionSprites.remove(((Sprite) solution[0]).getSpriteAlias());
		nonSolutionWeapons.remove(((Weapon) solution[1]).getWeaponAlias());
		nonSolutionRooms.remove(((Room) solution[2]).getRoomAlias());

		// Create lists of random order for the three maps
		ArrayList<Sprite> randomSprites = new ArrayList<>(nonSolutionSprites.values());
		Collections.shuffle(randomSprites);
		ArrayList<Weapon> randomWeapons = new ArrayList<>(nonSolutionWeapons.values());
		Collections.shuffle(randomWeapons);
		ArrayList<Room> randomRooms = new ArrayList<>(nonSolutionRooms.values());
		Collections.shuffle(randomRooms);

		// Deal the cards out, change user every deal
		int userNum = 0;

		for (int s = 0; s < randomSprites.size(); s++) {
			// Get the user
			User user = users.get(userNum);
			// Get a random sprite
			Sprite randomSprite = randomSprites.get(s);
			// Add this sprite to the users hand and observed cards
			user.addToHand(randomSprite);
			user.addToObservedCards(randomSprite);
			// Change user
			userNum = (userNum + 1) % users.size();
		}

		for (int w = 0; w < randomWeapons.size(); w++) {
			// Get the user
			User user = users.get(userNum);
			// Get a random weapon
			Weapon randomWeapon = randomWeapons.get(w);
			// Add this weapon to the users hand and observed cards
			user.addToHand(randomWeapon);
			user.addToObservedCards(randomWeapon);
			// Change user
			userNum = (userNum + 1) % users.size();
		}

		for (int r = 0; r < randomRooms.size(); r++) {
			// Get the user
			User user = users.get(userNum);
			// Get a random room
			Room randomRoom = randomRooms.get(r);
			// Add this room to the users hand an observed cards
			user.addToHand(randomRoom);
			user.addToObservedCards(randomRoom);
			// Change the user
			userNum = (userNum + 1) % users.size();
		}

	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		g.gameController();
	}

}
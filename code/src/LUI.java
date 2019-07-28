package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Harri
 *
 */
public class LUI {

	// diceRoll field means that players can't re-roll by reloading the menu
	private static int diceRoll = -1;

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * startUpMenu: Displays the title graphic
	 * 
	 * @void
	 */
	public void title() {
		clearConsole();
		// Starting sequence
		System.out.println(
				" ____   ____   ____   ____   ____   ____\n||C || ||l || ||u || ||e || ||d || ||o ||\n||__|| ||__|| ||__|| ||__|| ||__|| ||__||\n|/__\\| |/__\\| |/__\\| |/__\\| |/__\\| |/__\\|");
		System.out.println(".........The murder mystery game.........");
	}

	/**
	 * startUpMenu: Displays the players choice at the main menu
	 * 
	 * @return String - indication of players choice
	 */
	public String startUpMenu() {
		title();
		return readInput("-[1] Play game\n -[2] How to play\n  -[3] Quit\n", "USER");
	}

	/**
	 * howToPlay: Displays the rules of the game
	 * 
	 * 
	 * @return String - nothing, allows user get use to game set up
	 */
	public String howToPlay() {
		return readInput(
				"Aim:\t\tFigure out the mystery to who murdered the butler, what weapon they used, and in what room."
						+ "\nGame:\t\tThe game is turn based. Upon starting, each player is dealt a hand of cards. These cards are secret"
						+ "\n\t\tand give you evidence as to who, what and where the murder DIDN'T take place."
						+ "\n\t\tOn your turn, you roll two dice and navigate your around the map. To find out what other players have,"
						+ "\n\t\tyou will need to make a suggestion about the weapon, room and person. You can only make a suggestion in"
						+ "\n\t\ta room, using said room as your piece of evidence. You will also need to suggest a person and weapon which will be "
						+ "\n\t\ttransported to that room. If you are accused of a crime, you will be transported to the room of which your accuser is in."
						+ "\n\t\tOnce a suggestion has been made, going clockwise, each player is asked to provide ONE piece of evidence to dispute the claim."
						+ "\n\t\tIf a piece of evidence is shown, no more evidence is needed to dispute the claim. You can not hold back evidence,"
						+ "\n\t\tthis is seen as cheating a players found doing so will have their hand shown to all."
						+ "\nNavigation:\tThis game is console based. To make any actions, a series of choices will be shown to you with a"
						+ "\n\t\tkey inside '[]'. Type a key to choose that option." + "\n\n[ANY] Back to main menu\n",
				"USER");
	}

	/**
	 * gameSetup: public method for CluedoGame to use to get all the relavant
	 * information from the beginning set up of the game. I.e usernames, characters
	 * choices, player count
	 * 
	 * 
	 * @return String - colon separated information for each user
	 */
	public String gameSetup() {
		clearConsole();
		StringBuilder str = new StringBuilder();

		// Get amount of players
		int players = playerCount();
		str.append("Players:" + players + ":");

		// Get player characters
		// -----------------------
		str.append(characterSelection(players));

		return str.toString();

	}

	/**
	 * userNameCreation: Asks user for the amount of players in the game
	 * 
	 * 
	 * @return Integer - the amount of players in the game
	 */
	private int playerCount() {
		String input = "";
		int players = 0;
		// Loop until a valid input is entered
		while (players <= 0) {
			input = readInput("How many players do we have today? (3-6 needed)", "USER");
			players = stringToInt(input);
			if ((players < 3 || players > 6) && players != -1) {
				players = 0;
				printError(input, "is not a number from 3 to 6");
			}
		}
		return players;
	}

	/**
	 * userNameCreation: Asks user for their user name, ensure that it doesnt
	 * contain ':' as that is used to split string information in CluedoGame
	 * 
	 * 
	 * 
	 * @param name - temporary player name 'Player1'
	 * @return String - username
	 */
	private String userNameCreation(String name) {
		String input = ":";
		// Ensure that the user name has no ':' in it
		while (input.contains(":")) {
			input = readInput(name + ", What is your name?", name);
			if (input.contains(":"))
				printError(input, "contains ':', which is not allowed");
		}
		return input;
	}

	/**
	 * characterSelection: Used in the pregame, displays and retrives the users
	 * information. I.e Character choice, user name
	 * 
	 * 
	 * 
	 * @param players - how many people are playing
	 * @return String - user information
	 */
	private String characterSelection(int players) {
		String input = "";
		StringBuilder str = new StringBuilder();

		// Setup for character names
		List<String> characters = new ArrayList<>();
		Sprite.SpriteAlias[] ca = Sprite.SpriteAlias.values();

		// Put character names in list/set
		for (int i = 0; i < Sprite.SpriteAlias.values().length; i++) {
			Sprite.SpriteAlias c = ca[i];
			characters.add(c.name());
		}

		clearConsole();

		// Ask each player what they want from all players available.
		for (int p = 0; p < players; p++) {
			// Get username
			String userName = userNameCreation("Player " + (p + 1));

			// Set up for players output;
			String characterChoice = "";

			// Print an receive
			while (characterChoice.isEmpty()) {
				System.out.println("Available Characters:\n");
				// Cycle characters
				for (int i = 0; i < characters.size(); i++)
					System.out.println("[" + (i + 1) + "]" + " " + characters.get(i));
				// Ask player for a character
				input = readInput("Choose your character", userName);

				// Set the players character
				int characterNumber = stringToInt(input) - 1;
				if (characterNumber >= 0 && characterNumber <= 5) {
					try {
						characterChoice = characters.get(characterNumber);
						characters.remove(characterNumber);
					} catch (Exception e) {
						printError(input, "is not longer available");
					}
				} else if (characterNumber + 1 != -1) {
					printError(input, "is not a valid number");
				}
			}

			clearConsole();

			str.append("UserName:" + userName + ":" + "Sprite:" + characterChoice + ":");
		}

		return str.toString();

	}

	/**
	 * round: Displays the in game menu for each player, indicating their options
	 * for their turn
	 * 
	 * 
	 * @param user - who's turn is it error - a message to display under the board
	 * @return String - indication of the players choice on what to do
	 */
	public String round(User user, String error) {
		if (error.length() > 0)
			printError("", error);

		return readInput(user.getUserName() + " it's your turn:" + "\n  " + user.getSprite().getName() + ": '"
				+ user.getSprite().toString() + "' -> [" + ((char) (user.getSprite().getPosition().getCol() + 'A'))
				+ String.format("%02d", (user.getSprite().getPosition().getRow() + 1)) + "]\n" + "\n-[1] Move "
				+ "\n -[2] Hand" + "\n  -[3] Observations" + "\n   -[4] Suggest" + "\n    -[5] Accuse (Solve)"
				+ "\n     -[8] Skip turn" + "\n      -[9] Main Menu", user.getUserName());
	}

	/**
	 * movePlayer: Get's the users inputs and makes sure that the input is a
	 * char+int. Unable to check validity of cell without board
	 * 
	 * 
	 * @param user - who's character is being moved
	 * @return String - indication of the players choice on what to do
	 */
	public String movePlayer(User user) {
		// Clear all strings
		String input = "";
		String cellCoordinates = "";

		// Whilst a cell is not valid
		while (cellCoordinates.length() == 0) {
			// Get the users choice of cell to move to, display their dice roll too
			input = readInput(user.getUserName() + " it's your turn:" + "\n  " + user.getSprite().getName() + ": '"
					+ user.getSprite().toString() + "' -> [" + ((char) (user.getSprite().getPosition().getCol() + 'A'))
					+ String.format("%02d", (user.getSprite().getPosition().getRow() + 1)) + "]"
					+ "\n\nEnter cell position you would like to move to (e.g 'H18')." + "\n DICE ROLL = " + diceRoll
					+ "\n-[1] Back to Menu\n -['col+row' + Enter] Enter Cell Position", user.getUserName());

			// Return nothing to indicate no cell has been choosen
			if (input.equals("1"))
				return "";

			// check that the input conforms to general outline
			if (Character.isAlphabetic(input.charAt(0))) {
				try {
					char col = input.charAt(0);
					int row = Integer.parseUnsignedInt(input.substring(1));
					cellCoordinates = col + "" + row;
				} catch (Exception e) {
					printError(input, "does not match the suggested layout");
				}
			}
		}
		return cellCoordinates;
	}

	/**
	 * showHand: Used by the user to show their hand
	 * 
	 * @param user - who's hand is being shown
	 * @return String - indication of the players choice on what to do
	 */
	public String showHand(User user) {
		List<Card> usersHand = user.getHand();
		int handIndex = 0, handSize = usersHand.size();

		// Print out all the users sprites
		System.out.println("Sprites:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Sprite) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}
		// Print out all the users weapons
		System.out.println("\nWeapons:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Weapon) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}
		// Print out all the users rooms
		System.out.println("\nRooms:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Room) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}

		return readInput("-[ANY] Back to menu", user.getUserName());
	}

	/**
	 * showObservations: Used in the CluedoGame class, public as CluedoGame needs to
	 * parse a list of all the possible cards. Which are displayed to indicate what
	 * cards the player has seen.
	 * 
	 * @param user - who's observations are being displayed allCards - all the
	 *             possible cards in the game
	 * @return String - indication of the players choice on what to do
	 */
	public String showObservations(User user) {
		// Print out all the sprites
		System.out.println("\nSprites:");
		for (Sprite.SpriteAlias s : Sprite.SpriteAlias.values()) {
			System.out.print("   " + s.name());
			// If the user knows about this card, then indicate they know it isn't the
			// solution
			if (user.observedContainsAlias(s.name()))
				System.out.print(" --------------- SEEN");
			System.out.println();
		}

		// Print out all the Weapons
		System.out.println("\nWeapons:");
		for (Weapon.WeaponAlias w : Weapon.WeaponAlias.values()) {
			System.out.print("   " + w.name());
			// If the user knows about this card, then indicate they know it isn't the
			// solution
			if (user.observedContainsAlias(w.name()))
				System.out.print(" --------------- SEEN");
			System.out.println();
		}

		// Print out all the Rooms
		System.out.println("\nRooms:");
		for (Room.RoomAlias r : Room.RoomAlias.values()) {
			System.out.print("   " + r.name());
			// If the user knows about this card, then indicate they know it isn't the
			// solution
			if (user.observedContainsAlias(r.name()))
				System.out.print(" --------------- SEEN");
			System.out.println();
		}

		return readInput("-[ANY] Back to menu", user.getUserName());
	}

	public String selectThreeCards(User user, String code) {
		// Setup for getting the three cards
		StringBuilder str = new StringBuilder();
		int playerChoice = -1;
		String accusation = "";

		// Allows to reset your accusation
		while (accusation.isEmpty()) {
			// Reset the accusation process
			clearConsole();
			String input, spriteSuspect = "", weaponSuspect = "", roomSuspect = "";
			int restart, back;

			// CHARACTER SELECTION
			// -----------------------------------------------------------------------
			// Loop until a valid option is selected
			while (spriteSuspect.isEmpty()) {
				// Print all the choices of sprites
				for (int s = 0; s < Sprite.SpriteAlias.values().length; s++) {
					System.out.println(indent(s) + "-[" + (s + 1) + "] " + Sprite.SpriteAlias.values()[s].name());
				}

				// Create a reset selection and back to menu option
				restart = Sprite.SpriteAlias.values().length + 1;
				back = Sprite.SpriteAlias.values().length + 2;
				System.out.println(indent(restart - 1) + "-[" + (restart) + "] " + "Reset Selection");
				System.out.println(indent(back - 1) + "-[" + (back) + "] " + "Back to menu");

				// Get the players input
				input = readInput("Choose who you wish to blame!", user.getUserName());

				// Check it is a valid integer
				playerChoice = stringToInt(input);
				if (playerChoice == -1) {
					continue;
				}
				// Check if restarting selection process
				if (playerChoice == restart)
					break;
				// Check if returning to menu
				if (playerChoice == back)
					return "";

				// Check if number is valid for a sprite selection
				if (playerChoice < 1 || playerChoice > Sprite.SpriteAlias.values().length) {
					printError(input, "is not a valid choice");
					continue;
				}

				// Get the playerChoice - 1 for indexing
				spriteSuspect = Sprite.SpriteAlias.values()[(playerChoice - 1)].name();
				str.append(spriteSuspect + ":");
			}

			// WEAPON SELECTION
			// -----------------------------------------------------------------------
			// Loop until a valid option is selected
			while (!spriteSuspect.isEmpty() && weaponSuspect.isEmpty()) {
				// Print all the choices of weapons
				for (int w = 0; w < Weapon.WeaponAlias.values().length; w++) {
					System.out.println(indent(w) + "-[" + (w + 1) + "] " + Weapon.WeaponAlias.values()[w].name());
				}

				// Create a reset selection and back to menu option
				restart = Weapon.WeaponAlias.values().length + 1;
				back = Weapon.WeaponAlias.values().length + 2;
				System.out.println(indent(restart - 1) + "-[" + (restart) + "] " + "Reset Selection");
				System.out.println(indent(back - 1) + "-[" + (back) + "] " + "Back to menu");

				// Get the players input
				input = readInput(spriteSuspect + "???\n... what did they use then?", user.getUserName());

				// Check it is a valid integer
				playerChoice = stringToInt(input);
				if (playerChoice == -1) {
					continue;
				}

				// Check if returning to menu
				if (playerChoice == restart)
					break;
				// Check if returning to menu
				if (playerChoice == back)
					return "";

				// Check if number is valid for a sprite selection
				if (playerChoice < 1 || playerChoice > Weapon.WeaponAlias.values().length) {
					printError(input, "is not a valid choice");
					continue;
				}

				// Get the playerChoice - 1 for indexing
				weaponSuspect = Weapon.WeaponAlias.values()[(playerChoice - 1)].name();
				str.append(weaponSuspect + ":");
			}

			// ROOM SELECTION
			// -----------------------------------------------------------------------
			// Loop until a valid option is selected
			while (!spriteSuspect.isEmpty() && !weaponSuspect.isEmpty() && roomSuspect.isEmpty()
					&& code.equals("ROOM")) {
				// Print all the choices of rooms
				for (int r = 0; r < Room.RoomAlias.values().length; r++) {
					System.out.println(indent(r) + "-[" + (r + 1) + "] " + Room.RoomAlias.values()[r].name());
				}

				// Create a reset selection and back to menu option
				restart = Room.RoomAlias.values().length + 1;
				back = Room.RoomAlias.values().length + 2;
				System.out.println(indent(restart - 1) + "-[" + (restart) + "] " + "Reset Selection");
				System.out.println(indent(back - 1) + "-[" + (back) + "] " + "Back to menu");

				// Get the players input
				input = readInput(
						spriteSuspect + " used the " + weaponSuspect + "???\n... so where did this take place?",
						user.getUserName());

				// Check it is a valid integer
				playerChoice = stringToInt(input);
				if (playerChoice == -1) {
					printError(input, "is not a valid choice");
					continue;
				}

				// Check if returning to menu
				if (playerChoice == restart)
					break;
				// Check if returning to menu
				if (playerChoice == back)
					return "";

				// Check if number is valid for a sprite selection
				if (playerChoice < 1 || playerChoice > Room.RoomAlias.values().length) {
					printError(input, "is not a valid choice");
					continue;
				}

				// Get the playerChoice - 1 for indexing
				roomSuspect = Room.RoomAlias.values()[(playerChoice - 1)].name();
				str.append(roomSuspect);
			}

			// If we skipped the room selection, then get the players current room as it is
			// a suggestion
			if (code.equals("NO_ROOM")) {
				roomSuspect = user.getSprite().getPosition().getRoom().getName();
				str.append(roomSuspect);
			}

			// We know if a room is selected then three cards have been selected
			// Now give the option to confirm their selection
			while (!roomSuspect.isEmpty()) {
				input = readInput(spriteSuspect + " used the " + weaponSuspect + " in the " + roomSuspect + "?"
						+ "\n-[1] Yes / Confirm" + "\n -[2] No / Try Again", user.getUserName());

				// Accusation and break will break out of all the loops / CONFIRM
				if (input.equals("1")) {
					accusation = str.toString();
					break;
				}
				// Breaking the loop will restart the selection process / TRY AGAIN
				else if (input.equals("2"))
					break;
				// Else loop the confirmation process to get a valid answer
				else
					printError(input, "is not an option here");
			}
		}

		return str.toString();
	}

	/**
	 * chooseCardToGiveAway: forces a player to choose a card in question to display
	 * 
	 * @param other - the user giving up the card
	 * @param cards - a list of the other users possible cards to give up
	 * @return Card - what card the other user chooses
	 */
	public Card chooseCardToGiveAway(User other, ArrayList<Card> cards) {
		Card choosenCard = null;
		// Forces other user to give up a card
		while (choosenCard == null) {
			// Keeps the screen clean
			clearConsole();
			
			// Display all the possible options
			for (int i = 0; i < cards.size(); i++) {
				System.out.println(indent(i) + "-[" + (i + 1) + "]" + cards.get(i).getName());
			}
			
			// Get the users input
			String input = readInput(other.getUserName() + ", please select a card that you are required to show.",
					other.getUserName());

			// Ensure that the input is valid
			int playerChoice = stringToInt(input);
			if (playerChoice >= 1 && playerChoice <= cards.size()) {
				choosenCard = cards.get(playerChoice - 1);
			}
		}

		clearConsole();
		return choosenCard;
	}

	/**
	 * rollDice: returns the sum of two randomly generated numbers. Two randomly
	 * generated numbers were used to keep the probabilities of sums maintained.
	 * Sets the diceRoll field to the sum.
	 * @void
	 */
	public static void rollDice() {
		Random dice = new Random();
		int num1 = dice.nextInt(6) + 1;
		int num2 = dice.nextInt(6) + 1;
		diceRoll = num1 + num2;
	}


	/**
	 * getDiceRoll: diceRoll getter.
	 * @return Integer - the current sum of the two dice rolled
	 */
	public static int getDiceRoll() {
		return diceRoll;
	}

	// ------------------------
	// INTERFACE: Helpful methods
	// ------------------------

	/**
	 * readInput: Prints a message and reads the users input using
	 * System.in.read(byte[]). Can read up to 50 bytes of information (about 50
	 * characters) and will convert the input into a string to then return.
	 * 
	 * @param message - A String to be printed before user input. player - A String
	 *                for the
	 * @return The users input.
	 */
	public String readInput(String message, String player) {
		// Create necessary variables for input reading.
		String input = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Print message and read input
		try {
			System.out.print("\n" + message + "\n\n" + player + ":  ");
			input = reader.readLine();
			System.out.println();
		} catch (Exception e) {
			printError(input, "READER MALFUNCTION");
		}

		// Return the string so that the function calling it can use it.
		return input.toUpperCase();

	}

	/**
	 * stringToInt: Checks if a string is an integer
	 * @param input - The string checking for integer
	 * @return Integer - The integer value if a valid input, else -1
	 */
	public static final int stringToInt(String input) {
		// Get rid of all the excess 
		input.replaceAll("\\D\\s", "");
		int isInteger = -1;
		// Try parse the string to and int
		try {
			isInteger = Integer.parseInt(input);
		} catch (Exception e) {
			printError(input, "is not a number");
		}

		return isInteger;
	}

	/**
	 * printError: Generalized display for any error
	 * @param input - What the user input
	 * @param error - What is wrong with the input
	 */
	public static final void printError(String input, String error) {
		System.out.println("\n\n-------------------------------------------" + "\n ERROR: " + input + " " + error + "."
				+ "\n-------------------------------------------\n \n");

	}

	/**
	 * loading: Useless code that creates a loading sequence
	 */
	public static final void loading() {
		System.out.print("Loading ");
		for (int i = 0; i < 5; i++) {
			System.out.print(" .");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	/**
	 * indent: Allows for loops to create a string of spaces maintaining consistency in design
	 * @param length - Amount of spaces
	 * @return String - String of spaces based on length
	 */
	private String indent(int length) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			str.append(" ");
		}

		return str.toString();
	}

	/**
	 * clearConsole: Prints 52 lines, keeps the current state of the game clean
	 */
	public final static void clearConsole() {
		for (int i = 0; i < 52; i++)
			System.out.println();
	}

}

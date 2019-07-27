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

	// Keywords within the interface

	// ------------------------
	// INTERFACE
	// ------------------------

	/** 
	 * 
	 * 
	 * */
	public void title() {
		clearConsole();
		// Starting sequence
		System.out.println(
				" ____   ____   ____   ____   ____   ____\n||C || ||l || ||u || ||e || ||d || ||o ||\n||__|| ||__|| ||__|| ||__|| ||__|| ||__||\n|/__\\| |/__\\| |/__\\| |/__\\| |/__\\| |/__\\|");
		System.out.println(".........The murder mystery game.........");
	}

	/** 
	 * 
	 * 
	 * */
	public String startUpMenu() {
		title();
		return readInput("\n-[1] Play game\n -[2] How to play\n  -[3] Quit\n", "USER").toUpperCase();
	}

	public String howToPlay() {
		return readInput(
				"\nAim:\t\tFigure out the mystery to who murdered the butler, what weapon they used, and in what room."
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

	public int playerCount() {
		String input = "";
		int players = 0;
		while (players == 0) {
			input = readInput("How many players do we have today? (3-6 needed)", "USER");
			players = stringToInt(input);
			if (players < 3 || players > 6) {
				players = 0;
				System.out.println("Problem: Need to have 3 to 6 players.\n");
			}
		}
		return players;
	}

	public String userNameCreation(String name) {
		String input = "";
		int wordsInName = 2;
		while (wordsInName != 1) {
			input = readInput(name + ", What is your name?", name);
			wordsInName = input.split(":").length;
			if (wordsInName > 2)
				System.out.println("':' Can not be used in user name");
		}
		return input;
	}

	public String characterSelection(int players) {
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
		// Ask each player what they want from all players available.
		clearConsole();
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
						System.out.println("Sprite " + input + " is no longer available");
					}
				} else {
					System.out.println("Your input '" + input + "' is not a valid entry");
				}
			}

			clearConsole();
			str.append("Player:" + p + ":" + "UserName:" + userName + ":" + "Sprite:" + characterChoice + ":");
		}

		System.out.println(str.toString());
		return str.toString();

	}

	public String round(User user, String error) {
		String input = "";

		// Stage one, choose whether to Move, Accuse, Suggest, look at hand, look at
		// cheat sheet.
		input = stageOne(user, error);

		// Deal with the choice made in stage one.
		input = stageTwo(input, user);

		return input;
	}

	private String stageOne(User user, String error) {
		if (error.length() > 0)
			System.out.println("\n\n-------------------------------------------" + "\n ERROR: " + error
					+ "\n-------------------------------------------\n \n");

		return readInput(
				user.getUserName() + " it's your turn:" + "\n  " + user.getSprite().getName() + ": '"
						+ user.getSprite().toString() + "' -> ["
						+ ((char) (user.getSprite().getPosition().getCol() + 'A'))
						+ String.format("%02d", (user.getSprite().getPosition().getRow() + 1)) + "]\n"
						+ "\n-[1] Move " + "\n -[2] Hand" + "\n  -[3] Observations" + "\n   -[4] Suggest"
						+ "\n    -[5] Accuse (Solve)" + "\n     -[8] Skip turn" + "\n      -[9] Main Menu",
				user.getUserName());
	}

	public String stageTwo(String status, User user) {
		// Do something based on stage 1's output
		if (status.contentEquals("1"))
			return "1:" + movePlayer(user);
		if (status.contentEquals("2"))
			return "2" + showHand(user);
		if (status.contentEquals("3"))
			return "3:" + showObservations(user);
		if (status.contentEquals("4"))
			return "4:" + suggestion(user);
		if (status.contentEquals("5"))
			return "5:" + accusation(user);
		if (status.contentEquals("8"))
			return "8";
		if (status.contentEquals("9"))
			return "9";

		return status;
	}

	private String movePlayer(User user) {
		//
		int diceRoll = rollDice();
		String input = "";
		String cellCoordinates = "";
		while (cellCoordinates.length() == 0) {
			input = readInput("Enter cell position you would like to move to (e.g 'H,18')." + "\n Dice Roll = "
					+ diceRoll + "\n\n-[B] Back to Menu\n -[Enter] Enter Cell Position", user.getUserName())
							.toUpperCase();

			if (input.equals("B"))
				return "MENU";

			String[] position = input.split(",");

			if (position.length == 2) {
				int row, col;
				try {
					row = Integer.parseInt(position[1]) - 1;
					col = position[0].charAt(0) - 'A';
					cellCoordinates = row + ":" + col;
				} catch (Exception e) {
					System.out.println("invalid co-ordinates");
					cellCoordinates = "";
				}
			} else {
				System.out.println("Unable to read coordinates");
			}

		}
		return cellCoordinates + ":" + diceRoll;
	}

	private String showHand(User user) {
		List<Card> usersHand = user.getHand();
		int handIndex = 0, handSize = usersHand.size();
		System.out.println("Characters:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Sprite) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}
		System.out.println("Weapons:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Weapon) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}
		System.out.println("Rooms:");
		while (handIndex < handSize && usersHand.get(handIndex) instanceof Room) {
			System.out.println("  " + usersHand.get(handIndex).getName());
			handIndex++;
		}

		return readInput("\n-[ANY] Go back to menu", user.getUserName());
	}

	private String showObservations(User user) {

		return "";
	}

	private String suggestion(User user) {
		// TODO
		return "";
	}

	private String accusation(User user) {
		// TODO
		return "";
	}

	private int rollDice() {
		Random dice = new Random();
		int num1 = dice.nextInt(6) + 1;
		int num2 = dice.nextInt(6) + 1;
		return num1 + num2;
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
			System.out.print(message + "\n\n" + player + ":  ");
			input = reader.readLine();
			System.out.println();
		} catch (Exception e) {
			// TODO
		}

		// Return the string so that the function calling it can use it.
		return input.toUpperCase();

	}

	public static final int stringToInt(String input) {
		input.replaceAll("\\D\\s", "");
		int isInteger = -1;
		try {
			isInteger = Integer.parseInt(input);
		} catch (Exception e) {
			System.out.println("String not and Integer");
		}

		return isInteger;
	}

	public static final void loading(String message) {
		System.out.println(message);
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

	public final static void clearConsole() {
		for (int i = 0; i < 52; i++)
			System.out.println();
	}

}

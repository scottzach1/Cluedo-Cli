import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;

/**
 * @author Harri
 *
 */
public class LUI {

	// Keywords within the interface
	public static final String UNKNOWN = "???", MENU = "M", PLAY = "P", HOW = "H", QUIT = "Q";

	// ------------------------
	// INTERFACE
	// ------------------------
	private String input = "";

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

		input = readInput("\n-[P] Play game\n -[H] How to play\n  -[Q] Quit\n", "USER").toUpperCase();

		switch (input) {
		case HOW:
			return HOW;
		case PLAY:
			return PLAY;
		case QUIT:
			return QUIT;
		case MENU:
			return MENU;
		default:
			return UNKNOWN;
		}
	}

	public String howToPlay() {
		input = readInput(
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
						+ "\n\t\tkey inside '[]'. Type a key to choose that option."
						+ "\n\n[ANY] Back to main menu\n",
				"USER");
		return MENU;
	}

	public String gameSetup() {
		clearConsole();
		StringBuilder str = new StringBuilder();

		// Get amount of players
		int players = playerCount();
		str.append("Players-" + players + "\n");

		// Get player characters
		// -----------------------
		str.append(characterSelection(players));

		return str.toString();

	}

	public int playerCount() {
		int players = 0;
		while (players == 0) {
			input = readInput("How many player do we have today? (3-6 needed)", "USER");
			players = stringToInt(input);
			if (players < 3 || players > 6) {
				players = 0;
				System.out.println("Can only have 3 to 6 players.");
			}
		}
		return players;
	}

	public String characterSelection(int players) {
		StringBuilder str = new StringBuilder();
		// Setup for character names
		List<String> characters = new ArrayList<>();
		Character.CharacterAlias[] ca = Character.CharacterAlias.values();
		// Put character names in list/set
		for (int i = 0; i < Character.CharacterAlias.values().length; i++) {
			Character.CharacterAlias c = ca[i];
			characters.add(c.name());
		}
		// Ask each player what they want from all players available.
		clearConsole();
		for (int p = 0; p < players; p++) {
			// Set up for players output;
			String characterChoice = "";

			// Print an receive
			while (characterChoice.isEmpty()) {
				System.out.println("Available Characters:\n");
				// Cycle characters
				for (int i = 0; i < characters.size(); i++)
					System.out.println("[" + i + 1 + "]" + " " + characters.get(i));
				// Ask player for a character
				input = readInput("Choose your character", "Player " + (p + 1));

				// Set the players character
				int characterNumber = stringToInt(input);
				if (characterNumber > 0) {
					try {
						characterChoice = characters.get(characterNumber - 1);
						characters.remove(characterNumber - 1);
					} catch (Exception e) {
						System.out.println("Character " + characterNumber + " is not a valid selection");
					}
				}
			}

			clearConsole();
			str.append("Player-" + p + ":" + "Character-" + characterChoice + "\n");
		}

		return str.toString();

	}

	// ------------------------
	// INTERFACE: Helpful methods
	// ------------------------

	/**
	 * readInput: Prints a message and reads the users input using
	 * System.in.read(byte[]). Can read up to 50 bytes of information (about 50
	 * characters) and will convert the input into a string to then return.
	 * 
	 * @param message - A String to be printed before user input.
	 * @return The users input.
	 */
	public String readInput(String message, String player) {
		// Create necessary variables for input reading.
		String input = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Print message and read input
		try {
			System.out.print(message + "\n\n" + player + ":\t");
			input = reader.readLine();
			System.out.println();
		} catch (Exception e) {
			// TODO
		}

		// Return the string so that the function calling it can use it.
		return input.toUpperCase();

	}

	public int stringToInt(String input) {
		input.replaceAll("\\D\\s", "");
		int isInteger = -1;
		try {
			isInteger = Integer.parseInt(input);
		} catch (Exception e) {
			// TODO
		}

		return isInteger;
	}

	public final static void clearConsole() {
		for (int i = 0; i < 52; i++)
			System.out.println();
	}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;

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
	private Board map;
	private Set<Character> characters;
	private Set<Weapon> weapons;
	private Set<Room> rooms;
	private Card solution[];
	private LUI lui;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public CluedoGame() {
		String status = "";
		lui = new LUI();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void run() {
		//GAME MENU (below) --------------------------------------------------
		String status = "";

		// Start up menu
		while (!status.equals(LUI.PLAY)) {
			// Intro:
			status = lui.startUpMenu();

			// Switch cases of status
			if (status.contentEquals(LUI.HOW)) {
				status = lui.howToPlay();
			} else if (status.contentEquals(LUI.QUIT)) {
				System.out.println("Thanks for playing");
				return;
			} else if (!status.contentEquals(LUI.MENU) && !status.contentEquals(LUI.PLAY)) {
				System.out.println("Sorry, '" + status + "' is not a valid input.");
			}
		}

		LUI.loading("");
		
		status = lui.gameSetup();
		//GAME MENU (above) --------------------------------------------------

		String components [] = status.split("\\W");
		
/**		Format of components:
		[0] String : "Players"
		[1] Integer : Player count
		[2] String: "Player" 
		[3] Integer : Player number
		[4] String: "Character"
		[5] CharacterAlias : Players Character */
		
		int playerCount = 0;
		try {
		playerCount = Integer.parseInt(components[1]);
		} catch (Exception e) {
			LUI.loading("ERROR ON LOADING GAME");
			run();
			return;
		}
		
		// Get rid of the first two (now useless) bits of information
		String userInformation [] = Arrays.copyOfRange(components, 2, components.length);

		
		for (int user = 0; user < playerCount; user++) {
// ZAC -- Create a users here
			// This is the users entered number
			int userNumber = 0;
			try {
				userNumber = Integer.parseInt(userInformation[1 + (4*user)]);
			} catch (Exception e) {
				LUI.loading("ERROR ON LOADING GAME");
				run();
				return;
			}
			// This is the users character alias and string (use whatever, delete the other)
			String usersCharacterName = userInformation[3 + (4*user)];
			Character.CharacterAlias usersCharacterAlias = Character.CharacterAlias.valueOf(usersCharacterName);
		}
		
		
	}

	public Board getMap() {
		return map;
	}

	public void setMap(Board map) {
		this.map = map;
	}

	public Set<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Character> characters) {
		this.characters = characters;
	}

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(Set<Weapon> weapons) {
		this.weapons = weapons;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Card[] getSolution() {
		return solution;
	}

	public void setSolution(Card[] solution) {
		this.solution = solution;
	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		// Play
		g.run();
	}

}
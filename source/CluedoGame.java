import java.util.List;
import java.util.Set;

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
		while(!status.contentEquals("EXIT")) {
			lui = new LUI();
			status = run();
			System.out.println("STATUS: " + status + "\n\n\n\n\n\n");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public String run() {
		// Intro:
		// Players
		int intPlayers = 0;
		try {
			String strPlayers = lui.readInput("How many players are there for this game?");
			if (lui.checkExit(strPlayers)) return "EXIT";
			if (lui.checkBack(strPlayers)) return "BACK";
			
			
			intPlayers = Integer.parseInt(strPlayers);
			
			
		} catch (Exception ex) {
			return "UNABLE! " + ex;
		}
		
		return intPlayers + "";

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
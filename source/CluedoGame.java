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
		lui = new LUI();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public String run() {
		// Intro:
		String input = "";
		lui.startUpMenu();
		return"";
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
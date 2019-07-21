import java.util.List;
import java.util.Set;

/* Created by Harrison Cook and Zac Scott - 2019 */

/**
 * Game: Controls the steps of the game, creation of objects, and run time
 * actions.
 */
public class Game {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Attributes
	private Board map;
	private Set<Character> characters;
	private Set<Weapon> weapons;
	private Set<Room> rooms;
	private Card solution[];

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Game() {

	}

	// ------------------------
	// INTERFACE
	// ------------------------

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

}
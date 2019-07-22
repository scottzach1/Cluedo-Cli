import java.util.Set;
public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Board Attributes
	private Set<Room> rooms;
	private Set<Cell> cells;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board() {

	}
	// ------------------------
	// INTERFACE
	// ------------------------

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Set<Cell> getCells() {
		return cells;
	}

	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}

	
	
	public String toString() {
		return "not yet implemented";
	}
}
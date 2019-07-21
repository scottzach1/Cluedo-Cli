public class Weapon extends Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Weapon Attributes
	private Room location;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Weapon(String aName) {
		super(aName);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Room getLocation() {
		return location;
	}

	public void setLocation(Room location) {
		this.location = location;
	}	

	public String toString() {
		return "";
	}
}
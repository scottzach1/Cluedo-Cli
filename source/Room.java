import java.util.Set;

public class Room extends Card {
	
	public static enum roomAlias{
		 KITCHEN, 
		 BALLROOM, 
		 CONSERVATORY, 
		 DINING_ROOM,
		 BILLARD_ROOM,
		 LIBRARY,
		 LOUNGE,
		 HALL,
		 STUDY;
	}
	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Room Attributes
	private Set<Cell> cells;
	private Set<User> inThisRoom;
	private Weapon weapon;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Room(String aName, Set<Cell> aCells, Set<User> aInThisRoom, Weapon aWeapon) {
		super(aName);
		cells = aCells;
		inThisRoom = aInThisRoom;
		weapon = aWeapon;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Set<Cell> getCells() {
		return cells;
	}

	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}

	public Set<User> getInThisRoom() {
		return inThisRoom;
	}

	public void setInThisRoom(Set<User> inThisRoom) {
		this.inThisRoom = inThisRoom;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
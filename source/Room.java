import java.util.Set;

public class Room extends Card {
	
	public static enum RoomAlias {
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

	public static RoomAlias getEnum(char c) {
		switch (c) {
			case 'K': return RoomAlias.KITCHEN;
			case 'B': return RoomAlias.BALLROOM;
			case 'C': return RoomAlias.CONSERVATORY;
			case 'A': return RoomAlias.BILLARD_ROOM;
			case 'D': return RoomAlias.DINING_ROOM;
			case 'L': return RoomAlias.LIBRARY;
			case 'E': return RoomAlias.LOUNGE;
			case 'H': return RoomAlias.HALL;
			case 'S': return RoomAlias.STUDY;
			default: throw new IllegalStateException("Unexpected Character For Room: " + c);
		}
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

	public Room(String aName) {
		super(aName);
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
import java.util.HashSet;
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
		cells = new HashSet<>();
		inThisRoom = new HashSet<>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Set<Cell> getCells() {
		return cells;
	}

	public void addCell(Cell cell) {
		this.cells.add(cell);
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

	public static RoomAlias parseAliasFromOrdinalChar(char c) {
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
	
	
	public static RoomAlias parseAliasFromOrdinalInt(int i) {
		switch (i) {
			case 0: return RoomAlias.KITCHEN;
			case 1: return RoomAlias.BALLROOM;
			case 2: return RoomAlias.CONSERVATORY;
			case 3: return RoomAlias.BILLARD_ROOM;
			case 4: return RoomAlias.DINING_ROOM;
			case 5: return RoomAlias.LIBRARY;
			case 6: return RoomAlias.LOUNGE;
			case 7: return RoomAlias.HALL;
			case 8: return RoomAlias.STUDY;
			default: throw new IllegalStateException("Unexpected Character For Room: " + i);
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for (User user : inThisRoom) {
			str.append("\t" + user.getUserName() + "\n");
		}
		
		if (inThisRoom.isEmpty()) str.append(b)
		
		
		return getName() + ": " + str.toString();
	}
}
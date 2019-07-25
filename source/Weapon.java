
public class Weapon extends Card {

	public enum WeaponAlais {
		CANDLE_STICK,
		DAGGER,
		LEAD_PIPE,
		REVOLVER,
		ROPE,
		SPANNER;
	}

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
	
	public static WeaponAlais parseAliasFromOrdinalInt(int i) {
		int sizeOfCharacterValues = Weapon.WeaponAlais.values().length;
		if (i >= 0 && i < sizeOfCharacterValues)
			return Weapon.WeaponAlais.values()[i];
		throw new IllegalStateException("Error parsing " + i + " as an ordinal for WeaponAlais.");
	}
	

	public String toString() {
		return "";
	}
}
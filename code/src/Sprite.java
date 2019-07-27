package src;

public class Sprite extends Card {

	public enum SpriteAlias {
		MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM, MISS_SCARLETT, COLONEL_MUSTARD;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	private User user;
	private SpriteAlias charAlias;
	private Cell positionCell;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Sprite(String aName) {
		super(aName);

		// Set character alias
		switch (aName) {
		case "MRS_WHITE":
			charAlias = SpriteAlias.MRS_WHITE;
			break;
		case "MR_GREEN":
			charAlias = SpriteAlias.MR_GREEN;
			break;
		case "MRS_PEACOCK":
			charAlias = SpriteAlias.MRS_PEACOCK;
			break;
		case "PROFESSOR_PLUM":
			charAlias = SpriteAlias.PROFESSOR_PLUM;
			break;
		case "MISS_SCARLETT":
			charAlias = SpriteAlias.MISS_SCARLETT;
			break;
		case "COLONEL_MUSTARD":
			charAlias = SpriteAlias.COLONEL_MUSTARD;
			break;
		default:
			throw new RuntimeException("Sprite alias not found.");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SpriteAlias getCharAlias() {
		return charAlias;
	}

	public void setCharAlias(SpriteAlias charAlias) {
		this.charAlias = charAlias;
	}

	public void setPosition(Cell position) {
		this.positionCell = position;
	}

	public Cell getPosition() {
		return positionCell;
	}

	public static SpriteAlias parseAliasFromOrdinalChar(char c) {
		int i = Integer.parseInt(c + "");
		for (SpriteAlias alias : SpriteAlias.values()) {
			if (alias.ordinal() == i)
				return alias;
		}
		throw new IllegalStateException("Error parsing " + c + " as an ordinal for SpriteAlias.");
	}	
	
	public static SpriteAlias parseAliasFromOrdinalInt(int i) {
		int sizeOfCharacterValues = SpriteAlias.values().length;
		if (i >= 0 && i < sizeOfCharacterValues)
			return SpriteAlias.values()[i];
		throw new IllegalStateException("Error parsing " + i + " as an ordinal for SpriteAlias.");
	}
	
	public String toString() {
		String str = "";
		switch(charAlias) {
		case MRS_WHITE:
			str = "W";
			break;
		case MR_GREEN:
			str = "G";
			break;
		case MRS_PEACOCK:
			str = "Q";
			break;
		case PROFESSOR_PLUM:
			str = "P";
			break;
		case MISS_SCARLETT:
			str = "S";
			break;
		case COLONEL_MUSTARD:
			str = "M";
			break;
		default:
			throw new RuntimeException("Sprite alias not found.");
		}
		
		
		return str;
	}

}
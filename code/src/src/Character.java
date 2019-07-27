package src;

public class Character extends Card {

	public enum CharacterAlias {
		MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM, MISS_SCARLETT, COLONEL_MUSTARD;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	private User user;
	private CharacterAlias charAlias;
	private Cell positionCell;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Character(String aName) {
		super(aName);

		// Set character alias
		switch (aName) {
		case "MRS_WHITE":
			charAlias = CharacterAlias.MRS_WHITE;
			break;
		case "MR_GREEN":
			charAlias = CharacterAlias.MR_GREEN;
			break;
		case "MRS_PEACOCK":
			charAlias = CharacterAlias.MRS_PEACOCK;
			break;
		case "PROFESSOR_PLUM":
			charAlias = CharacterAlias.PROFESSOR_PLUM;
			break;
		case "MISS_SCARLETT":
			charAlias = CharacterAlias.MISS_SCARLETT;
			break;
		case "COLONEL_MUSTARD":
			charAlias = CharacterAlias.COLONEL_MUSTARD;
			break;
		default:
			throw new RuntimeException("Character alias not found.");
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

	public CharacterAlias getCharAlias() {
		return charAlias;
	}

	public void setCharAlias(CharacterAlias charAlias) {
		this.charAlias = charAlias;
	}

	public void setPosition(Cell position) {
		this.positionCell = position;
	}

	public Cell getPosition() {
		return positionCell;
	}

	public static CharacterAlias parseAliasFromOrdinalChar(char c) {
		int i = Integer.parseInt(c + "");
		for (CharacterAlias alias : CharacterAlias.values()) {
			if (alias.ordinal() == i)
				return alias;
		}
		throw new IllegalStateException("Error parsing " + c + " as an ordinal for CharacterAlias.");
	}	
	
	public static CharacterAlias parseAliasFromOrdinalInt(int i) {
		int sizeOfCharacterValues = Character.CharacterAlias.values().length;
		if (i >= 0 && i < sizeOfCharacterValues)
			return Character.CharacterAlias.values()[i];
		throw new IllegalStateException("Error parsing " + i + " as an ordinal for CharacterAlias.");
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
			throw new RuntimeException("Character alias not found.");
		}
		
		
		return str;
	}

}
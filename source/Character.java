public class Character extends Card {
	
	public static enum CharacterAlias {
		 MIS_SCARLETT, 
		 COLONEL_MUSTARD, 
		 MRS_WHITE, 
		 MR_GREEN,
		 MRS_PEACOCK,
		 PROFESSOR_PLUM;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Character Associations
	private User user;
	private CharacterAlias charAlias;
	

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Character(String aName, User aUser) {	
		super(aName);
		
		// Set user
		if (aUser == null || aUser.getCharacter() != null) {
			throw new RuntimeException("Unable to create Character due to invalid User");
		}
		user = aUser;
		
		// Set character alias
		switch(aName) {
		case "MIS_SCARLETT":
			charAlias = CharacterAlias.MIS_SCARLETT;
			break;
		case "COLONEL_MUSTARD":
			charAlias = CharacterAlias.COLONEL_MUSTARD;
			break;
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
}
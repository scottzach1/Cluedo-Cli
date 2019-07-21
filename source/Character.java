import java.util.*;

public class Character extends Card {
	
	public static enum characterAlias{
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
	private characterAlias charAlias;
	

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
			charAlias = characterAlias.MIS_SCARLETT;
			break;
		case "COLONEL_MUSTARD":
			charAlias = characterAlias.COLONEL_MUSTARD;
			break;
		case "MRS_WHITE":
			charAlias = characterAlias.MRS_WHITE;
			break;
		case "MR_GREEN":
			charAlias = characterAlias.MR_GREEN;
			break;
		case "MRS_PEACOCK":
			charAlias = characterAlias.MRS_PEACOCK;
			break;
		case "PROFESSOR_PLUM":
			charAlias = characterAlias.PROFESSOR_PLUM;
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


	public characterAlias getCharAlias() {
		return charAlias;
	}


	public void setCharAlias(characterAlias charAlias) {
		this.charAlias = charAlias;
	}
}
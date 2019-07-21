import java.util.*;

public class Character implements Card {
	
	public static enum characterAlias{
		 Miss_Scarlett, 
		 Colonel_Mustard, 
		 Mrs_White, 
		 Mr_Green,
		 Mrs_Peacock,
		 Professor_Plum;
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

	public Character(User aUser, String aName) {		
		// Set user
		if (aUser == null || aUser.getCharacter() != null) {
			throw new RuntimeException("Unable to create Character due to invalid User");
		}
		user = aUser;
		
		// Set characterAlias
		
		
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	@Override
	public String getName(String aName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getName() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Yet to implement";
	}

}
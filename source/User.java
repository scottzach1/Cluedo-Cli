/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/

import java.util.*;

// line 37 "model.ump"
// line 95 "model.ump"
public class User {
	public static enum playerNo {
		PLAYER_0, 
		PLAYER_1, 
		PLAYER_2, 
		PLAYER_3, 
		PLAYER_4, 
		PLAYER_5;
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// User Attributes
	private int userNo;
	private String userName;
	private Set<Card> hand;
	private Set<Card> knownCards;
	private Character character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------


	public User() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public Set<Card> getKnownCards() {
		return knownCards;
	}

	public void setKnownCards(Set<Card> knownCards) {
		this.knownCards = knownCards;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public String toString() {
		return "";
	}
}
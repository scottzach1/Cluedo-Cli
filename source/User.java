import java.util.*;

public class User {
	
	public static int USERS = 0;
	
	public static enum userNo {
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

	private userNo userNum;
	private String userName;
	private Set<Card> hand;
	private Set<Card> observedCards;
	private Character character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------


	public User() {
		User.USERS++;
		userNum = userNo.values()[User.USERS];
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public userNo getUserNo() {
		return userNum;
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

	public Set<Card> getObservedCards() {
		return observedCards;
	}

	public void setObservedCards(Set<Card> knownCards) {
		this.observedCards = knownCards;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public String toString() {
		return userName + "(" + userNum + ")";
	}
}
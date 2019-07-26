import java.util.*;

public class User {
	
	private static int USERS = 0;
	
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

	userNo getUserNo() {
		return userNum;
	}

	String getUserName() {
		return userName;
	}

	void setUserName(String userName) {
		this.userName = userName;
	}

	Set<Card> getHand() {
		return hand;
	}

	void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	Set<Card> getObservedCards() {
		return observedCards;
	}

	void setObservedCards(Set<Card> knownCards) {
		this.observedCards = knownCards;
	}

	Character getCharacter() {
		return character;
	}

	void setCharacter(Character character) {
		this.character = character;
	}
	
	public String toString() {
		return userName + "(" + userNum + ")";
	}
}
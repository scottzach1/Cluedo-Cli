package src;

import java.util.*;

public class User {

	private static int USERS = 0;

	public enum userNo {
		PLAYER_0,
		PLAYER_1,
		PLAYER_2,
		PLAYER_3,
		PLAYER_4,
		PLAYER_5
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	private userNo userNum;
	private String userName;
	private List<Card> hand;
	private Set<Card> observedCards;
	private Character character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------


	public User() {
		User.USERS++;
		userNum = userNo.values()[User.USERS];
		hand = new ArrayList<>();
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

	public List<Card> getHand() {
		return hand;
	}

	public void addToHand(Card card) {
		this.hand.add(card);
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
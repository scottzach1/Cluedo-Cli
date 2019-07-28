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
	private Sprite sprite;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------


	public User() {
		User.USERS++;
		userNum = userNo.values()[User.USERS];
		hand = new ArrayList<>();
		observedCards = new HashSet<>();
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

	public void addToObservedCards(Card knownCard) {
		this.observedCards.add(knownCard);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		if (sprite.getUser() == this) return;
		sprite.setUser(this);
	}
	
	public boolean observedContainsAlias(String s) {
		for (Card c : observedCards) {
			if (c.getName().equals(s))
				return true;
		}
		return false;
	}

	public String toString() {
		return userName + "(" + userNum + ")";
	}

	public static void resetUserNoCounter() { USERS = 0; }
	public static int getUserNoCounter() { return USERS; }
}

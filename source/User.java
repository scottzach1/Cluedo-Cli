/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 37 "model.ump"
// line 95 "model.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private int userNo;
  private String userName;
  private List hand;
  private List knownCards;

  //User Associations
  private List<Card> cards;
  private Character character;
  private List<Game> enabled_by;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(int aUserNo, String aUserName, List aHand, List aKnownCards, Character aCharacter)
  {
    userNo = aUserNo;
    userName = aUserName;
    hand = aHand;
    knownCards = aKnownCards;
    cards = new ArrayList<Card>();
    if (aCharacter == null || aCharacter.getUser() != null)
    {
      throw new RuntimeException("Unable to create User due to aCharacter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    character = aCharacter;
    enabled_by = new ArrayList<Game>();
  }

  public User(int aUserNo, String aUserName, List aHand, List aKnownCards, String aNameForCharacter)
  {
    userNo = aUserNo;
    userName = aUserName;
    hand = aHand;
    knownCards = aKnownCards;
    cards = new ArrayList<Card>();
    character = new Character(aNameForCharacter, this);
    enabled_by = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserNo(int aUserNo)
  {
    boolean wasSet = false;
    userNo = aUserNo;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
    wasSet = true;
    return wasSet;
  }

  public boolean setHand(List aHand)
  {
    boolean wasSet = false;
    hand = aHand;
    wasSet = true;
    return wasSet;
  }

  public boolean setKnownCards(List aKnownCards)
  {
    boolean wasSet = false;
    knownCards = aKnownCards;
    wasSet = true;
    return wasSet;
  }

  public int getUserNo()
  {
    return userNo;
  }

  public String getUserName()
  {
    return userName;
  }

  public List getHand()
  {
    return hand;
  }

  public List getKnownCards()
  {
    return knownCards;
  }
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_GetOne */
  public Character getCharacter()
  {
    return character;
  }
  /* Code from template association_GetMany */
  public Game getEnabled_by(int index)
  {
    Game aEnabled_by = enabled_by.get(index);
    return aEnabled_by;
  }

  public List<Game> getEnabled_by()
  {
    List<Game> newEnabled_by = Collections.unmodifiableList(enabled_by);
    return newEnabled_by;
  }

  public int numberOfEnabled_by()
  {
    int number = enabled_by.size();
    return number;
  }

  public boolean hasEnabled_by()
  {
    boolean has = enabled_by.size() > 0;
    return has;
  }

  public int indexOfEnabled_by(Game aEnabled_by)
  {
    int index = enabled_by.indexOf(aEnabled_by);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCardsValid()
  {
    boolean isValid = numberOfCards() >= minimumNumberOfCards() && numberOfCards() <= maximumNumberOfCards();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCards()
  {
    return 6;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    if (numberOfCards() >= maximumNumberOfCards())
    {
      return wasAdded;
    }

    cards.add(aCard);
    if (aCard.indexOfUser(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCard.addUser(this);
      if (!wasAdded)
      {
        cards.remove(aCard);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    if (!cards.contains(aCard))
    {
      return wasRemoved;
    }

    if (numberOfCards() <= minimumNumberOfCards())
    {
      return wasRemoved;
    }

    int oldIndex = cards.indexOf(aCard);
    cards.remove(oldIndex);
    if (aCard.indexOfUser(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCard.removeUser(this);
      if (!wasRemoved)
      {
        cards.add(oldIndex,aCard);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setCards(Card... newCards)
  {
    boolean wasSet = false;
    ArrayList<Card> verifiedCards = new ArrayList<Card>();
    for (Card aCard : newCards)
    {
      if (verifiedCards.contains(aCard))
      {
        continue;
      }
      verifiedCards.add(aCard);
    }

    if (verifiedCards.size() != newCards.length || verifiedCards.size() < minimumNumberOfCards() || verifiedCards.size() > maximumNumberOfCards())
    {
      return wasSet;
    }

    ArrayList<Card> oldCards = new ArrayList<Card>(cards);
    cards.clear();
    for (Card aNewCard : verifiedCards)
    {
      cards.add(aNewCard);
      if (oldCards.contains(aNewCard))
      {
        oldCards.remove(aNewCard);
      }
      else
      {
        aNewCard.addUser(this);
      }
    }

    for (Card anOldCard : oldCards)
    {
      anOldCard.removeUser(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfEnabled_byValid()
  {
    boolean isValid = numberOfEnabled_by() >= minimumNumberOfEnabled_by() && numberOfEnabled_by() <= maximumNumberOfEnabled_by();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEnabled_by()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfEnabled_by()
  {
    return 6;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Game addEnabled_by(Board aMap, List aCharacters, List aWeapons, List aRooms, List aSolution, Board aControls, Card aCollects)
  {
    if (numberOfEnabled_by() >= maximumNumberOfEnabled_by())
    {
      return null;
    }
    else
    {
      return new Game(aMap, aCharacters, aWeapons, aRooms, aSolution, aControls, this, aCollects);
    }
  }

  public boolean addEnabled_by(Game aEnabled_by)
  {
    boolean wasAdded = false;
    if (enabled_by.contains(aEnabled_by)) { return false; }
    if (numberOfEnabled_by() >= maximumNumberOfEnabled_by())
    {
      return wasAdded;
    }

    User existingEnables = aEnabled_by.getEnables();
    boolean isNewEnables = existingEnables != null && !this.equals(existingEnables);

    if (isNewEnables && existingEnables.numberOfEnabled_by() <= minimumNumberOfEnabled_by())
    {
      return wasAdded;
    }

    if (isNewEnables)
    {
      aEnabled_by.setEnables(this);
    }
    else
    {
      enabled_by.add(aEnabled_by);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEnabled_by(Game aEnabled_by)
  {
    boolean wasRemoved = false;
    //Unable to remove aEnabled_by, as it must always have a enables
    if (this.equals(aEnabled_by.getEnables()))
    {
      return wasRemoved;
    }

    //enables already at minimum (3)
    if (numberOfEnabled_by() <= minimumNumberOfEnabled_by())
    {
      return wasRemoved;
    }
    enabled_by.remove(aEnabled_by);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEnabled_byAt(Game aEnabled_by, int index)
  {  
    boolean wasAdded = false;
    if(addEnabled_by(aEnabled_by))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEnabled_by()) { index = numberOfEnabled_by() - 1; }
      enabled_by.remove(aEnabled_by);
      enabled_by.add(index, aEnabled_by);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEnabled_byAt(Game aEnabled_by, int index)
  {
    boolean wasAdded = false;
    if(enabled_by.contains(aEnabled_by))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEnabled_by()) { index = numberOfEnabled_by() - 1; }
      enabled_by.remove(aEnabled_by);
      enabled_by.add(index, aEnabled_by);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEnabled_byAt(aEnabled_by, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Card> copyOfCards = new ArrayList<Card>(cards);
    cards.clear();
    for(Card aCard : copyOfCards)
    {
      if (aCard.numberOfUsers() <= Card.minimumNumberOfUsers())
      {
        aCard.delete();
      }
      else
      {
        aCard.removeUser(this);
      }
    }
    Character existingCharacter = character;
    character = null;
    if (existingCharacter != null)
    {
      existingCharacter.delete();
    }
    for(int i=enabled_by.size(); i > 0; i--)
    {
      Game aEnabled_by = enabled_by.get(i - 1);
      aEnabled_by.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userNo" + ":" + getUserNo()+ "," +
            "userName" + ":" + getUserName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "hand" + "=" + (getHand() != null ? !getHand().equals(this)  ? getHand().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "knownCards" + "=" + (getKnownCards() != null ? !getKnownCards().equals(this)  ? getKnownCards().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "character = "+(getCharacter()!=null?Integer.toHexString(System.identityHashCode(getCharacter())):"null");
  }
}
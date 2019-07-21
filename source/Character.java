/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 61 "model.ump"
// line 113 "model.ump"
public class Character extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Character Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Character(String aName, User aUser)
  {
    super(aName);
    if (aUser == null || aUser.getCharacter() != null)
    {
      throw new RuntimeException("Unable to create Character due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    user = aUser;
  }

  public Character(String aName, int aUserNoForUser, String aUserNameForUser, List aHandForUser, List aKnownCardsForUser)
  {
    super(aName);
    user = new User(aUserNoForUser, aUserNameForUser, aHandForUser, aKnownCardsForUser, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }

  public void delete()
  {
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.delete();
    }
    super.delete();
  }

}
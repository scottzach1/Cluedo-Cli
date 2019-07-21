/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 48 "model.ump"
// line 102 "model.ump"
public class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private String name;

  //Card Associations
  private List<User> users;
  private List<Game> collected_by;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName)
  {
    name = aName;
    users = new ArrayList<User>();
    collected_by = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getCollected_by(int index)
  {
    Game aCollected_by = collected_by.get(index);
    return aCollected_by;
  }

  public List<Game> getCollected_by()
  {
    List<Game> newCollected_by = Collections.unmodifiableList(collected_by);
    return newCollected_by;
  }

  public int numberOfCollected_by()
  {
    int number = collected_by.size();
    return number;
  }

  public boolean hasCollected_by()
  {
    boolean has = collected_by.size() > 0;
    return has;
  }

  public int indexOfCollected_by(Game aCollected_by)
  {
    int index = collected_by.indexOf(aCollected_by);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfUsersValid()
  {
    boolean isValid = numberOfUsers() >= minimumNumberOfUsers() && numberOfUsers() <= maximumNumberOfUsers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUsers()
  {
    return 6;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    if (numberOfUsers() >= maximumNumberOfUsers())
    {
      return wasAdded;
    }

    users.add(aUser);
    if (aUser.indexOfCard(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aUser.addCard(this);
      if (!wasAdded)
      {
        users.remove(aUser);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    if (!users.contains(aUser))
    {
      return wasRemoved;
    }

    if (numberOfUsers() <= minimumNumberOfUsers())
    {
      return wasRemoved;
    }

    int oldIndex = users.indexOf(aUser);
    users.remove(oldIndex);
    if (aUser.indexOfCard(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aUser.removeCard(this);
      if (!wasRemoved)
      {
        users.add(oldIndex,aUser);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setUsers(User... newUsers)
  {
    boolean wasSet = false;
    ArrayList<User> verifiedUsers = new ArrayList<User>();
    for (User aUser : newUsers)
    {
      if (verifiedUsers.contains(aUser))
      {
        continue;
      }
      verifiedUsers.add(aUser);
    }

    if (verifiedUsers.size() != newUsers.length || verifiedUsers.size() < minimumNumberOfUsers() || verifiedUsers.size() > maximumNumberOfUsers())
    {
      return wasSet;
    }

    ArrayList<User> oldUsers = new ArrayList<User>(users);
    users.clear();
    for (User aNewUser : verifiedUsers)
    {
      users.add(aNewUser);
      if (oldUsers.contains(aNewUser))
      {
        oldUsers.remove(aNewUser);
      }
      else
      {
        aNewUser.addCard(this);
      }
    }

    for (User anOldUser : oldUsers)
    {
      anOldUser.removeCard(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCollected_byValid()
  {
    boolean isValid = numberOfCollected_by() >= minimumNumberOfCollected_by() && numberOfCollected_by() <= maximumNumberOfCollected_by();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfCollected_by()
  {
    return 21;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCollected_by()
  {
    return 21;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCollected_by()
  {
    return 21;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Game addCollected_by(Board aMap, List aCharacters, List aWeapons, List aRooms, List aSolution, Board aControls, User aEnables)
  {
    if (numberOfCollected_by() >= maximumNumberOfCollected_by())
    {
      return null;
    }
    else
    {
      return new Game(aMap, aCharacters, aWeapons, aRooms, aSolution, aControls, aEnables, this);
    }
  }

  public boolean addCollected_by(Game aCollected_by)
  {
    boolean wasAdded = false;
    if (collected_by.contains(aCollected_by)) { return false; }
    if (numberOfCollected_by() >= maximumNumberOfCollected_by())
    {
      return wasAdded;
    }

    Card existingCollects = aCollected_by.getCollects();
    boolean isNewCollects = existingCollects != null && !this.equals(existingCollects);

    if (isNewCollects && existingCollects.numberOfCollected_by() <= minimumNumberOfCollected_by())
    {
      return wasAdded;
    }

    if (isNewCollects)
    {
      aCollected_by.setCollects(this);
    }
    else
    {
      collected_by.add(aCollected_by);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCollected_by(Game aCollected_by)
  {
    boolean wasRemoved = false;
    //Unable to remove aCollected_by, as it must always have a collects
    if (this.equals(aCollected_by.getCollects()))
    {
      return wasRemoved;
    }

    //collects already at minimum (21)
    if (numberOfCollected_by() <= minimumNumberOfCollected_by())
    {
      return wasRemoved;
    }
    collected_by.remove(aCollected_by);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<User> copyOfUsers = new ArrayList<User>(users);
    users.clear();
    for(User aUser : copyOfUsers)
    {
      if (aUser.numberOfCards() <= User.minimumNumberOfCards())
      {
        aUser.delete();
      }
      else
      {
        aUser.removeCard(this);
      }
    }
    for(int i=collected_by.size(); i > 0; i--)
    {
      Game aCollected_by = collected_by.get(i - 1);
      aCollected_by.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



// line 2 "model.ump"
// line 75 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private Board map;
  private List characters;
  private List weapons;
  private List rooms;
  private List solution;

  //Game Associations
  private Board controls;
  private User enables;
  private Card collects;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetControls;
  private boolean canSetEnables;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Board aMap, List aCharacters, List aWeapons, List aRooms, List aSolution, Board aControls, User aEnables, Card aCollects)
  {
    cachedHashCode = -1;
    canSetControls = true;
    canSetEnables = true;
    map = aMap;
    characters = aCharacters;
    weapons = aWeapons;
    rooms = aRooms;
    solution = aSolution;
    if (aControls == null || aControls.getControlled_by() != null)
    {
      throw new RuntimeException("Unable to create Game due to aControls. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    controls = aControls;
    boolean didAddEnables = setEnables(aEnables);
    if (!didAddEnables)
    {
      throw new RuntimeException("Unable to create enabled_by due to enables. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCollects = setCollects(aCollects);
    if (!didAddCollects)
    {
      throw new RuntimeException("Unable to create collected_by due to collects. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Game(Board aMap, List aCharacters, List aWeapons, List aRooms, List aSolution, List aRoomsForControls, List aCellsForControls, Cell aCellForControls, Room aRoomForControls, User aEnables, Card aCollects)
  {
    map = aMap;
    characters = aCharacters;
    weapons = aWeapons;
    rooms = aRooms;
    solution = aSolution;
    controls = new Board(aRoomsForControls, aCellsForControls, aCellForControls, aRoomForControls, this);
    boolean didAddEnables = setEnables(aEnables);
    if (!didAddEnables)
    {
      throw new RuntimeException("Unable to create enabled_by due to enables. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCollects = setCollects(aCollects);
    if (!didAddCollects)
    {
      throw new RuntimeException("Unable to create collected_by due to collects. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMap(Board aMap)
  {
    boolean wasSet = false;
    map = aMap;
    wasSet = true;
    return wasSet;
  }

  public boolean setCharacters(List aCharacters)
  {
    boolean wasSet = false;
    characters = aCharacters;
    wasSet = true;
    return wasSet;
  }

  public boolean setWeapons(List aWeapons)
  {
    boolean wasSet = false;
    weapons = aWeapons;
    wasSet = true;
    return wasSet;
  }

  public boolean setRooms(List aRooms)
  {
    boolean wasSet = false;
    rooms = aRooms;
    wasSet = true;
    return wasSet;
  }

  public boolean setSolution(List aSolution)
  {
    boolean wasSet = false;
    solution = aSolution;
    wasSet = true;
    return wasSet;
  }

  public Board getMap()
  {
    return map;
  }

  public List getCharacters()
  {
    return characters;
  }

  public List getWeapons()
  {
    return weapons;
  }

  public List getRooms()
  {
    return rooms;
  }

  public List getSolution()
  {
    return solution;
  }
  /* Code from template association_GetOne */
  public Board getControls()
  {
    return controls;
  }
  /* Code from template association_GetOne */
  public User getEnables()
  {
    return enables;
  }
  /* Code from template association_GetOne */
  public Card getCollects()
  {
    return collects;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setEnables(User aEnables)
  {
    boolean wasSet = false;
    if (!canSetEnables) { return false; }
    //Must provide enables to enabled_by
    if (aEnables == null)
    {
      return wasSet;
    }

    //enables already at maximum (6)
    if (aEnables.numberOfEnabled_by() >= User.maximumNumberOfEnabled_by())
    {
      return wasSet;
    }
    
    User existingEnables = enables;
    enables = aEnables;
    if (existingEnables != null && !existingEnables.equals(aEnables))
    {
      boolean didRemove = existingEnables.removeEnabled_by(this);
      if (!didRemove)
      {
        enables = existingEnables;
        return wasSet;
      }
    }
    enables.addEnabled_by(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setCollects(Card aCollects)
  {
    boolean wasSet = false;
    //Must provide collects to collected_by
    if (aCollects == null)
    {
      return wasSet;
    }

    //collects already at maximum (21)
    if (aCollects.numberOfCollected_by() >= Card.maximumNumberOfCollected_by())
    {
      return wasSet;
    }
    
    Card existingCollects = collects;
    collects = aCollects;
    if (existingCollects != null && !existingCollects.equals(aCollects))
    {
      boolean didRemove = existingCollects.removeCollected_by(this);
      if (!didRemove)
      {
        collects = existingCollects;
        return wasSet;
      }
    }
    collects.addCollected_by(this);
    wasSet = true;
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Game compareTo = (Game)obj;
  
    if (getControls() == null && compareTo.getControls() != null)
    {
      return false;
    }
    else if (getControls() != null && !getControls().equals(compareTo.getControls()))
    {
      return false;
    }

    if (getEnables() == null && compareTo.getEnables() != null)
    {
      return false;
    }
    else if (getEnables() != null && !getEnables().equals(compareTo.getEnables()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getControls() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getControls().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getEnables() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getEnables().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetControls = false;
    canSetEnables = false;
    return cachedHashCode;
  }

  public void delete()
  {
    Board existingControls = controls;
    controls = null;
    if (existingControls != null)
    {
      existingControls.delete();
    }
    User placeholderEnables = enables;
    this.enables = null;
    if(placeholderEnables != null)
    {
      placeholderEnables.removeEnabled_by(this);
    }
    Card placeholderCollects = collects;
    this.collects = null;
    if(placeholderCollects != null)
    {
      placeholderCollects.removeCollected_by(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "map" + "=" + (getMap() != null ? !getMap().equals(this)  ? getMap().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "characters" + "=" + (getCharacters() != null ? !getCharacters().equals(this)  ? getCharacters().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weapons" + "=" + (getWeapons() != null ? !getWeapons().equals(this)  ? getWeapons().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "rooms" + "=" + (getRooms() != null ? !getRooms().equals(this)  ? getRooms().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "solution" + "=" + (getSolution() != null ? !getSolution().equals(this)  ? getSolution().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "controls = "+(getControls()!=null?Integer.toHexString(System.identityHashCode(getControls())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "enables = "+(getEnables()!=null?Integer.toHexString(System.identityHashCode(getEnables())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "collects = "+(getCollects()!=null?Integer.toHexString(System.identityHashCode(getCollects())):"null");
  }
}
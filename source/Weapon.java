/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 66 "model.ump"
// line 118 "model.ump"
public class Weapon extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private Room location;

  //Weapon Associations
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String aName, Room aLocation, Room aRoom)
  {
    super(aName);
    location = aLocation;
    if (aRoom == null || aRoom.getWeapon() != null)
    {
      throw new RuntimeException("Unable to create Weapon due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    room = aRoom;
  }

  public Weapon(String aName, Room aLocation, String aNameForRoom, List aCellsForRoom, Cell aCellForRoom)
  {
    super(aName);
    location = aLocation;
    room = new Room(aNameForRoom, aCellsForRoom, aCellForRoom, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLocation(Room aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
    wasSet = true;
    return wasSet;
  }

  public Room getLocation()
  {
    return location;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }

  public void delete()
  {
    Room existingRoom = room;
    room = null;
    if (existingRoom != null)
    {
      existingRoom.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "location" + "=" + (getLocation() != null ? !getLocation().equals(this)  ? getLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null");
  }
}
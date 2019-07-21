/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



// line 19 "model.ump"
// line 82 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private List rooms;
  private List cells;

  //Board Associations
  private Cell cell;
  private Room room;
  private Game controlled_by;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(List aRooms, List aCells, Cell aCell, Room aRoom, Game aControlled_by)
  {
    rooms = aRooms;
    cells = aCells;
    boolean didAddCell = setCell(aCell);
    if (!didAddCell)
    {
      throw new RuntimeException("Unable to create board due to cell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddRoom = setRoom(aRoom);
    if (!didAddRoom)
    {
      throw new RuntimeException("Unable to create board due to room. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aControlled_by == null || aControlled_by.getControls() != null)
    {
      throw new RuntimeException("Unable to create Board due to aControlled_by. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    controlled_by = aControlled_by;
  }

  public Board(List aRooms, List aCells, Cell aCell, Room aRoom, Board aMapForControlled_by, List aCharactersForControlled_by, List aWeaponsForControlled_by, List aRoomsForControlled_by, List aSolutionForControlled_by, User aEnablesForControlled_by, Card aCollectsForControlled_by)
  {
    rooms = aRooms;
    cells = aCells;
    boolean didAddCell = setCell(aCell);
    if (!didAddCell)
    {
      throw new RuntimeException("Unable to create board due to cell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddRoom = setRoom(aRoom);
    if (!didAddRoom)
    {
      throw new RuntimeException("Unable to create board due to room. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    controlled_by = new Game(aMapForControlled_by, aCharactersForControlled_by, aWeaponsForControlled_by, aRoomsForControlled_by, aSolutionForControlled_by, this, aEnablesForControlled_by, aCollectsForControlled_by);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRooms(List aRooms)
  {
    boolean wasSet = false;
    rooms = aRooms;
    wasSet = true;
    return wasSet;
  }

  public boolean setCells(List aCells)
  {
    boolean wasSet = false;
    cells = aCells;
    wasSet = true;
    return wasSet;
  }

  public List getRooms()
  {
    return rooms;
  }

  public List getCells()
  {
    return cells;
  }
  /* Code from template association_GetOne */
  public Cell getCell()
  {
    return cell;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }
  /* Code from template association_GetOne */
  public Game getControlled_by()
  {
    return controlled_by;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setCell(Cell aCell)
  {
    boolean wasSet = false;
    //Must provide cell to board
    if (aCell == null)
    {
      return wasSet;
    }

    //cell already at maximum (600)
    if (aCell.numberOfBoards() >= Cell.maximumNumberOfBoards())
    {
      return wasSet;
    }
    
    Cell existingCell = cell;
    cell = aCell;
    if (existingCell != null && !existingCell.equals(aCell))
    {
      boolean didRemove = existingCell.removeBoard(this);
      if (!didRemove)
      {
        cell = existingCell;
        return wasSet;
      }
    }
    cell.addBoard(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    //Must provide room to board
    if (aRoom == null)
    {
      return wasSet;
    }

    //room already at maximum (9)
    if (aRoom.numberOfBoards() >= Room.maximumNumberOfBoards())
    {
      return wasSet;
    }
    
    Room existingRoom = room;
    room = aRoom;
    if (existingRoom != null && !existingRoom.equals(aRoom))
    {
      boolean didRemove = existingRoom.removeBoard(this);
      if (!didRemove)
      {
        room = existingRoom;
        return wasSet;
      }
    }
    room.addBoard(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Cell placeholderCell = cell;
    this.cell = null;
    if(placeholderCell != null)
    {
      placeholderCell.removeBoard(this);
    }
    Room placeholderRoom = room;
    this.room = null;
    if(placeholderRoom != null)
    {
      placeholderRoom.removeBoard(this);
    }
    Game existingControlled_by = controlled_by;
    controlled_by = null;
    if (existingControlled_by != null)
    {
      existingControlled_by.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rooms" + "=" + (getRooms() != null ? !getRooms().equals(this)  ? getRooms().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cells" + "=" + (getCells() != null ? !getCells().equals(this)  ? getCells().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cell = "+(getCell()!=null?Integer.toHexString(System.identityHashCode(getCell())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "controlled_by = "+(getControlled_by()!=null?Integer.toHexString(System.identityHashCode(getControlled_by())):"null");
  }
}
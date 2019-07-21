/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 53 "model.ump"
// line 107 "model.ump"
public class Room extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private List cells;

  //Room Associations
  private Cell cell;
  private List<Board> boards;
  private Weapon weapon;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String aName, List aCells, Cell aCell, Weapon aWeapon)
  {
    super(aName);
    cells = aCells;
    boolean didAddCell = setCell(aCell);
    if (!didAddCell)
    {
      throw new RuntimeException("Unable to create room due to cell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boards = new ArrayList<Board>();
    if (aWeapon == null || aWeapon.getRoom() != null)
    {
      throw new RuntimeException("Unable to create Room due to aWeapon. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    weapon = aWeapon;
  }

  public Room(String aName, List aCells, Cell aCell, String aNameForWeapon, Room aLocationForWeapon)
  {
    super(aName);
    cells = aCells;
    boolean didAddCell = setCell(aCell);
    if (!didAddCell)
    {
      throw new RuntimeException("Unable to create room due to cell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boards = new ArrayList<Board>();
    weapon = new Weapon(aNameForWeapon, aLocationForWeapon, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCells(List aCells)
  {
    boolean wasSet = false;
    cells = aCells;
    wasSet = true;
    return wasSet;
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
  /* Code from template association_GetMany */
  public Board getBoard(int index)
  {
    Board aBoard = boards.get(index);
    return aBoard;
  }

  public List<Board> getBoards()
  {
    List<Board> newBoards = Collections.unmodifiableList(boards);
    return newBoards;
  }

  public int numberOfBoards()
  {
    int number = boards.size();
    return number;
  }

  public boolean hasBoards()
  {
    boolean has = boards.size() > 0;
    return has;
  }

  public int indexOfBoard(Board aBoard)
  {
    int index = boards.indexOf(aBoard);
    return index;
  }
  /* Code from template association_GetOne */
  public Weapon getWeapon()
  {
    return weapon;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCell(Cell aCell)
  {
    boolean wasSet = false;
    if (aCell == null)
    {
      return wasSet;
    }

    Cell existingCell = cell;
    cell = aCell;
    if (existingCell != null && !existingCell.equals(aCell))
    {
      existingCell.removeRoom(this);
    }
    cell.addRoom(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfBoardsValid()
  {
    boolean isValid = numberOfBoards() >= minimumNumberOfBoards() && numberOfBoards() <= maximumNumberOfBoards();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfBoards()
  {
    return 9;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoards()
  {
    return 9;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfBoards()
  {
    return 9;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Board addBoard(List aRooms, List aCells, Cell aCell, Game aControlled_by)
  {
    if (numberOfBoards() >= maximumNumberOfBoards())
    {
      return null;
    }
    else
    {
      return new Board(aRooms, aCells, aCell, this, aControlled_by);
    }
  }

  public boolean addBoard(Board aBoard)
  {
    boolean wasAdded = false;
    if (boards.contains(aBoard)) { return false; }
    if (numberOfBoards() >= maximumNumberOfBoards())
    {
      return wasAdded;
    }

    Room existingRoom = aBoard.getRoom();
    boolean isNewRoom = existingRoom != null && !this.equals(existingRoom);

    if (isNewRoom && existingRoom.numberOfBoards() <= minimumNumberOfBoards())
    {
      return wasAdded;
    }

    if (isNewRoom)
    {
      aBoard.setRoom(this);
    }
    else
    {
      boards.add(aBoard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoard(Board aBoard)
  {
    boolean wasRemoved = false;
    //Unable to remove aBoard, as it must always have a room
    if (this.equals(aBoard.getRoom()))
    {
      return wasRemoved;
    }

    //room already at minimum (9)
    if (numberOfBoards() <= minimumNumberOfBoards())
    {
      return wasRemoved;
    }
    boards.remove(aBoard);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    Cell placeholderCell = cell;
    this.cell = null;
    if(placeholderCell != null)
    {
      placeholderCell.removeRoom(this);
    }
    for(int i=boards.size(); i > 0; i--)
    {
      Board aBoard = boards.get(i - 1);
      aBoard.delete();
    }
    Weapon existingWeapon = weapon;
    weapon = null;
    if (existingWeapon != null)
    {
      existingWeapon.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cells" + "=" + (getCells() != null ? !getCells().equals(this)  ? getCells().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cell = "+(getCell()!=null?Integer.toHexString(System.identityHashCode(getCell())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "weapon = "+(getWeapon()!=null?Integer.toHexString(System.identityHashCode(getWeapon())):"null");
  }
}
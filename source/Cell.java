/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 28 "model.ump"
// line 89 "model.ump"
public class Cell
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cell Attributes
  private Map neighbors;
  private Character p;
  private Room r;
  private int row;
  private int col;

  //Cell Associations
  private List<Board> boards;
  private List<Room> rooms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cell(Map aNeighbors, Character aP, Room aR, int aRow, int aCol)
  {
    neighbors = aNeighbors;
    p = aP;
    r = aR;
    row = aRow;
    col = aCol;
    boards = new ArrayList<Board>();
    rooms = new ArrayList<Room>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNeighbors(Map aNeighbors)
  {
    boolean wasSet = false;
    neighbors = aNeighbors;
    wasSet = true;
    return wasSet;
  }

  public boolean setP(Character aP)
  {
    boolean wasSet = false;
    p = aP;
    wasSet = true;
    return wasSet;
  }

  public boolean setR(Room aR)
  {
    boolean wasSet = false;
    r = aR;
    wasSet = true;
    return wasSet;
  }

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setCol(int aCol)
  {
    boolean wasSet = false;
    col = aCol;
    wasSet = true;
    return wasSet;
  }

  public Map getNeighbors()
  {
    return neighbors;
  }

  public Character getP()
  {
    return p;
  }

  public Room getR()
  {
    return r;
  }

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
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
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
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
    return 600;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoards()
  {
    return 600;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfBoards()
  {
    return 600;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Board addBoard(List aRooms, List aCells, Room aRoom, Game aControlled_by)
  {
    if (numberOfBoards() >= maximumNumberOfBoards())
    {
      return null;
    }
    else
    {
      return new Board(aRooms, aCells, this, aRoom, aControlled_by);
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

    Cell existingCell = aBoard.getCell();
    boolean isNewCell = existingCell != null && !this.equals(existingCell);

    if (isNewCell && existingCell.numberOfBoards() <= minimumNumberOfBoards())
    {
      return wasAdded;
    }

    if (isNewCell)
    {
      aBoard.setCell(this);
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
    //Unable to remove aBoard, as it must always have a cell
    if (this.equals(aBoard.getCell()))
    {
      return wasRemoved;
    }

    //cell already at minimum (600)
    if (numberOfBoards() <= minimumNumberOfBoards())
    {
      return wasRemoved;
    }
    boards.remove(aBoard);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Room addRoom(String aName, List aCells, Weapon aWeapon)
  {
    return new Room(aName, aCells, this, aWeapon);
  }

  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    Cell existingCell = aRoom.getCell();
    boolean isNewCell = existingCell != null && !this.equals(existingCell);
    if (isNewCell)
    {
      aRoom.setCell(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a cell
    if (!this.equals(aRoom.getCell()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=boards.size(); i > 0; i--)
    {
      Board aBoard = boards.get(i - 1);
      aBoard.delete();
    }
    for(int i=rooms.size(); i > 0; i--)
    {
      Room aRoom = rooms.get(i - 1);
      aRoom.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "p" + ":" + getP()+ "," +
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "neighbors" + "=" + (getNeighbors() != null ? !getNeighbors().equals(this)  ? getNeighbors().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "r" + "=" + (getR() != null ? !getR().equals(this)  ? getR().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
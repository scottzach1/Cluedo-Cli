import com.sun.tools.jdeprscan.scan.Scan;
import sun.text.resources.ext.JavaTimeSupplementary_es_UY;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Board Attributes
	private Set<Room> rooms;
	private Set<Cell> cells;

	private int rows, cols;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board(String fname) {

		try {
			Scanner sc = new Scanner(new File(fname));

			if (!sc.next().equals("MAP")) throw new Exception("Invalid File Type");

			rows = sc.nextInt();
			cols = sc.nextInt();

			for (int row = 0; row != rows; ++row) {
				String line = sc.nextLine();

				for (int col = 0; col != cols; ++col) {
					sc.
				}
			}


		} catch (Exception e) { System.out.println("File Exception: " + e); }
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Set<Cell> getCells() {
		return cells;
	}

	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}

	
	
	public String toString() {
		return "not yet implemented";
	}
}
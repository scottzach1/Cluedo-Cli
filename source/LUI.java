/**
 * @author Harri
 *
 */
public class LUI {
	
	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public LUI() {
		// Starting sequence
		System.out.println(" ____   ____   ____   ____   ____   ____\n||C || ||l || ||u || ||e || ||d || ||o ||\n||__|| ||__|| ||__|| ||__|| ||__|| ||__||\n|/__\\| |/__\\| |/__\\| |/__\\| |/__\\| |/__\\|");
		System.out.println(".........The murder mystery game.........");
	}
	

	// ------------------------
	// INTERFACE
	// ------------------------


	
	
	
	// ------------------------
	// INTERFACE: Helpful methods
	// ------------------------

	
	/** readInput:  Prints a message and reads the users input using System.in.read(byte[]).
	 * 						Can read up to 50 bytes of information (about 50 characters) and will convert
	 * 						the input into a string to then return. 
	 * 
	 * @param message - A String to be printed before user input.
	 * @return The users input.
	 */
	public String readInput(String message) {
		// Create necessary variables for input reading.
		StringBuilder str = new StringBuilder();
		byte input[] = new byte[50];
		boolean inputStatus = false;

		// While the input is not okay, ask for it again
		while (inputStatus != true) {
			System.out.println(message);
			try {
				System.in.read(input);
				inputStatus = true;
			} catch (Exception e) {
				System.out.println("Error on input, please try again.");
			}
		}
		
		// Convert the characters in the byte [] into a string
		int i = 0;
		char c;
		while ((c = (char) input[i]) != '\0' && (c = (char) input[i]) != '\n') {
			str.append(c + "");
			i++;
		}
		
		// Return the string so that the function calling it can use it.
		return str.toString();

	}
	
	
	/** checkExit:	Check the message passed in to see if the message was "Exit".
	 * 						Used to check if the user wants to exit the game. 
	 * @param message - A string to be checked.
	 * @return True if the message was "Exit", else false.
	 */
	public boolean checkExit(String message) {
		String str = message.toUpperCase();
		
		if (str.equals("EXIT")) 
			return true;
		
		return false;
	}	

	/** checkExit:	Check the message passed in to see if the message was "Back".
	 * 						Used to check if the user wants to go back a step.. 
	 * @param message - A string to be checked.
	 * @return True if the message was "Back", else false.
	 */
	boolean checkBack(String message) {
		String str = message.toUpperCase();
		
		if (str.equals("BACK")) 
			return true;
		
		return false;
	}

}

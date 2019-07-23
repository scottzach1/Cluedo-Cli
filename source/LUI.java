import java.io.IOException;
import java.io.InputStream;

public class LUI {

	public LUI() {
		// Starting sequence
		System.out.println(" ____   ____   ____   ____   ____   ____\n||C || ||l || ||u || ||e || ||d || ||o ||\n||__|| ||__|| ||__|| ||__|| ||__|| ||__||\n|/__\\| |/__\\| |/__\\| |/__\\| |/__\\| |/__\\|");
		System.out.println(".........The murder mystery game.........");
	}

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
		while ((c = (char) input[i]) != '\0') {
			str.append(c + "");
			i++;
		}
		
		// Return the string so that the function calling it can use it.
		return str.toString();

	}

	public static void main(String[] args) {
		LUI l = new LUI();
	}

}

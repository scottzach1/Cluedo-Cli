import java.util.*;

/* Created by Harrison Cook and Zac Scott - 2019 */

/**
 * Game: Controls the steps of the game, creation of objects, and run time
 * actions.
 */
public class CluedoGame {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Attributes
	private Board board;
	private List<User> users;
	private Card solution[];
	private LUI lui;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public CluedoGame() {
		String status = "";
		lui = new LUI();
		board = new Board();
		this.users = new ArrayList<>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void run() {
		//GAME MENU (below) --------------------------------------------------
		String status = "";

		// Start up menu
		while (!status.equals(LUI.PLAY)) {
			// Intro:
			status = lui.startUpMenu();

			// Switch cases of status
			if (status.contentEquals(LUI.HOW)) {
				status = lui.howToPlay();
			} else if (status.contentEquals(LUI.QUIT)) {
				System.out.println("Thanks for playing");
				return;
			} else if (!status.contentEquals(LUI.MENU) && !status.contentEquals(LUI.PLAY)) {
				System.out.println("Sorry, '" + status + "' is not a valid input.");
			}
		}

		LUI.loading("");
		
		status = lui.gameSetup();
		
		
		//USER INFORMATION (below) --------------------------------------------------

		String components [] = status.split("\\W");
		
/**		Format of components:
		[0] String : "Players"
		[1] Integer : Player count
		[2] String: "Player" 
		[3] Integer : Player number
		[4] String: "Character"
		[5] CharacterAlias : Players Character */
		
		int playerCount = 0;
		try {
		playerCount = Integer.parseInt(components[1]);
		} catch (Exception e) {
			LUI.loading("ERROR ON LOADING GAME");
			run();
			return;
		}
		
		// Get rid of the first two (now useless) bits of information
		String userInformation [] = Arrays.copyOfRange(components, 2, components.length);
		
		for (int user = 0; user < playerCount; user++) {
			
			// This is the users entered number
			int userNumber = 0;
			try {
				userNumber = Integer.parseInt(userInformation[1 + (6*user)]);
			} catch (Exception e) {
				LUI.loading("ERROR ON LOADING GAME");
				run();
				return;
			}
			
			// The users entered name preference
			String userName = userInformation[3 + (6*user)];
			
			// This is the users character alias
			String userCharacterName = userInformation[5 + (6*user)];
			Character.CharacterAlias userCharacterAlias = Character.CharacterAlias.valueOf(userCharacterName);

			// Create a new user object and store in the games list of users
			User userObj = new User();
			userObj.setUserName(userName);
			userObj.setCharacter(board.getCharacters().get(userCharacterAlias));
			users.add(userObj);
		}
	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		// Play
		g.run();
	}

}
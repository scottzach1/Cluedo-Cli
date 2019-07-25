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
	private Card observations[];
	private String status;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public CluedoGame() {
		status = "";
		lui = new LUI();
		board = new Board();
		this.users = new ArrayList<>();
		
		// Create solution
		generateSolution();
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	
	public void gameController() {
		// Run the main menu first
		mainMenu();
		// If the player has chosen to quit
		if (status.equals("3")) return;
		
		
		// Next set up the game
		gameSetup();
		
		// Run the game by doing rounds, int user is the users turn
		int user = 0;
		while (!status.equals("3")) {
			LUI.clearConsole();
			board.printBoardState();
			
				status = lui.round(users.get(user));	
				// 1: Move, 2: Hand, 3: Observations, 4: Suggest, 5: Accuse (Solve), 8: Next User, 9: Quit Game
				
				// Change the user after prev user has exited their turn
				if (status.equals("8")) { user = (user+1) % users.size();}
		}
	}
	

	public void mainMenu() {
		//GAME MENU (below) --------------------------------------------------
		status = "";

		// Start up menu
		while (!status.equals("1")) {
			// Intro:
			status = lui.startUpMenu();

			// Switch cases of status, 1: Play, 2: How to Play, 3: Quit
			if (status.contentEquals("2")) {
				status = lui.howToPlay() + "MENU";
			} else if (status.contentEquals("3")) {
				System.out.println("Thanks for playing");
				return;
			}
		}
		
	}
	
	public void gameSetup() {
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
			gameController();
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
				gameController();
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
	
	
	public void generateSolution(){
		solution = new Card[3];
		Random random = new Random();
		
		Room room = board.getRooms().get(Room.parseAliasFromOrdinalInt(random.nextInt(9)));
		Character character = board.getCharacters().get(Character.parseAliasFromOrdinalInt(random.nextInt(6)));		
		Weapon weapon = board.getWeapons().get(Weapon.parseAliasFromOrdinalInt(random.nextInt(6)));		
		
		solution[0] = character;
		solution[1] = weapon;
		solution[2] = room;
		
	}

	public static void main(String[] args) {
		// Setup
		CluedoGame g = new CluedoGame();
		g.gameController();
	}

}
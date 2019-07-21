public abstract class Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Card Attributes
	private String name;
	


	// ------------------------
	// CONSTRUCTOR
	// 
	
	public Card(String aName) { name = aName; }

	// ------------------------
	// INTERFACE
	// ------------------------

	public String getName() {return name;};
	
	public void setName(String aName) {name = aName;};
	
	@Override
	public String toString() {
		char icon = name.charAt(0);
		String str = icon + "";
		return str;
	}
}
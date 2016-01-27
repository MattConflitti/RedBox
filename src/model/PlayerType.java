package model;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * PlayerType enum is treated more like a class. Each enumerated type
 * is constructed with one field as an array of Strings which are all
 * possible ways the enumerated type could be referred to. The 
 * methods allow for extraction of this information.
 **********************************************************************/
public enum PlayerType {
	
	//construct each enumeration with different string variation
	//first string is what will be returned when String displayed
	Xbox360("XBox 360", "xbox360"), 
	
	PS4("PS4", "playstation 4", "playstation4", "play station 4", 
			"play station4"), 
	
	Xbox720("XBox 720", "xbox720"), 
	
	PS3("PS3", "playstation 3", "playstation3", "play station 3", 
			"play station3"), 
	
	GameCube("GameCube", "game cube");

	/** stores possible references of enumerated type */
	String[] value;

	/*******************************************************************
	 * Constructor allows for any number of strings to passed as params
	 * into the value field of the enumerated type
	 * 
	 * @param text list of CSV strings
	 ******************************************************************/
	PlayerType(String ... text) {
		value = text;
	}

	/*******************************************************************
	 * Get the String representation of the enumerated type
	 * 
	 * @return the first String in the value array
	 ******************************************************************/
	public String getText() {
		return value[0];
	}

	/*******************************************************************
	 * Method compares string in param to the array of values in
	 * each enumerated type's field.  If there is a match it returns
	 * the corresponding enumerated type.
	 * 
	 * @param text String to search for in enumeration
	 * @return PlayerType corresponding to search text otherwise null
	 ******************************************************************/
	public static PlayerType fromString(String text) {
		if (text != null) {
			
			//loop through each enumerated type
			for (PlayerType p : PlayerType.values()) {
				for(int i =0; i<p.value.length; i++) {
				
					//check each string in array ignoring case 
					if (text.equalsIgnoreCase(p.value[i])) {
						return p;
					}
				}
			}
		}
		return null;
	}


}


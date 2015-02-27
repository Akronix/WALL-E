package tp.pr5;

/**
 * An enum class that represents the compass directions (North, east, south and west) 
 * plus a value that represents an unknown direction.
 * @author Abel Serrano Juste
 */

public enum Direction {
	
	EAST {
			public Direction opposite(){return WEST;}
			public Direction right(){return SOUTH;}
			public Direction left(){return NORTH;}
		},
	NORTH {
			public Direction opposite(){return SOUTH;}
			public Direction right() {return EAST;}
			public Direction left() {return WEST;}
		},
	SOUTH {
			public Direction opposite(){return NORTH;}
			public Direction right(){return WEST;}
			public Direction left(){return EAST;}
		},
	WEST {
			public Direction opposite(){return EAST;}
			public Direction right(){return NORTH;}
			public Direction left(){return SOUTH;}
		},
	UNKNOWN {
			public Direction opposite(){return UNKNOWN;}
			public Direction right(){return UNKNOWN;}
			public Direction left(){return UNKNOWN;}
		};
		
		
	/**
	 * This method convert a string representation of one direction to a Direction enum value.
	 * @param stringDir String which represent a direction
	 * @return Direction Direction.enum value which corresponds with the input string. If it isn't recognized, it returns <code>UNKWNOWN</code>
	 */
	public static Direction parseDirection (String stringDir){
		switch (stringDir.toUpperCase()){
			case "EAST": return Direction.EAST;
			case "NORTH": return Direction.NORTH;
			case "WEST": return Direction.WEST;
			case "SOUTH": return Direction.SOUTH;
			default: return Direction.UNKNOWN;
		}
	}
	
	/**
	 * Analyze if two directions are equals.
	 * @param dir1 direction 1
	 * @param dir2 direction 2
	 * @return if they're equals.
	 */
	public static boolean equalsDirection (Direction dir1, Direction dir2){
		return (dir1.name().equals(dir2.name()));
	}
	
	/**
	 * This method calculate the cardinal opposite direction.<br/>
	 * It does anything if the Direction value is UNKNOWN
	 * @return NORTH <-> SOUTH or WEST <-> EAST or UNKOWN
	*/	
	public abstract Direction opposite();
	
	/**
	 * This method returns the direction to the right of the direction inserted
	 * @return NORTH->EAST->SOUTH->WEST->
	 */
	public abstract Direction right();
	
	/**
	 * This method returns the direction to the left of the direction inserted
	 * @return NORTH->WEST->SOUTH->EAST->
	 */
	public abstract Direction left();
}

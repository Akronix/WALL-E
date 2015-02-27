package tp.pr5;

/**
 * An enum class that represents in which direction the robot rotates (left or right)
 *  plus a value that represents an unknown direction.
 *  @author Abel Serrano Juste
 */
public enum Rotation { 
	
	LEFT {public Rotation opposite(){return RIGHT;}},
	RIGHT {public Rotation opposite(){return LEFT;}},
	UNKNOWN {public Rotation opposite(){return UNKNOWN;}},; //Only for test requirements
	
	/**
	 * Converts input String to the corresponding rotation. If the input is not recognized it returns UNKNOWN.
	 * @param stringDir String which we want parse. Does not distinguish between lowercase and uppercase
	 * @return LEFT or RIGHT . UNKNOWN otherwise
	 */
	public static Rotation parseRotation(String stringDir){
		switch (stringDir.toUpperCase()){
			case "LEFT": return Rotation.LEFT;
			case "RIGHT": return Rotation.RIGHT;
			default: return Rotation.UNKNOWN;
				//throw new WrongInstructionFormatException("Only are recognized two senses to rotation: LEFT or RIGHT");
		}
	}
	
	/**
	 * This method calculate the opposite rotation.<br/>
	 * It does anything if the Rotation value is UNKNOWN
	 * @return LEFT <-> RIGHT or UNKOWN
	*/	
	public abstract Rotation opposite();
	

}
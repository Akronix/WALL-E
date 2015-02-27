package tp.pr5.instructions;

//local packages:
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * Its execution rotates the robot.
 * This Instruction works if the robot writes TURN LEFT or RIGHT or GIRAR LEFT or RIGHT
 * @author Abel Serrano Juste
 *
 */
public class TurnInstruction extends Commands {

	private Rotation rotation;
	
	/**
	 * Default constructor, only for testing purposes. Not common way to create new TurnInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public TurnInstruction(){
		super();
	}
	
	/**
	 * Constructor with rotation. Not common way to create new TurnInstruction objects.
	 * 'Parse' is a factory method for it
	 * @param rot Rotation for turning. 
	 */
	private TurnInstruction(Rotation rot){
		super();
		rotation=rot;
	}
	
	/**
	 * Turns the robot left or right
	 * @throws InstructionExecutionException When the rotation is unknown
	 */
	public void execute() throws InstructionExecutionException{
		navi.rotate(rotation);
		robot.addFuel(-5);
	}

	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return the command syntax TURN | GIRAR < LEFT|RIGHT >
	 */
	public String getHelp() {
		return ("TURN {RIGHT | LEFT}: It rotates the robot. " +
				"It need rotation field with right or left" +
				"This Instruction is executed if the player writes TURN LEFT or RIGHT or GIRAR LEFT or RIGHT");
	}

	/**
	 * Parses the String returning a TurnInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference pointing to an instance of a Instruction subclass, if it is corresponding to the String cad
	 * @throws WrongInstructionFormatException When the String is not TURN LEFT or RIGHT or GIRAR LEFT or RIGHT
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		Rotation rot;
		if (length==2 && (words[0].equalsIgnoreCase("TURN") || words[0].equalsIgnoreCase("GIRAR"))){
			rot = Rotation.parseRotation(words[1]);
			
			if (rot!=Rotation.UNKNOWN)
				return new TurnInstruction(rot);
			
			else
				throw new WrongInstructionFormatException("Incorrect Format for instruction 'TURN'");
		}
		
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'TURN'");
	}
	
	/**
	 * Undo turn instruction, this implies rotate in the opposite direction and restore the energy consumed in the rotation.
	 */
	public void undo() {
		navi.rotate(rotation.opposite()); //First, we perform an opposite rotation
		robot.addFuel(+5); //Later we restore the energy inverted in the rotation
	}
	
	@Override
	public String getSyntax() {
		return "TURN <LEFT | RIGHT>";
	}

}

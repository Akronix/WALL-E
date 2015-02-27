package tp.pr5.instructions;

//local package:
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * This Instruction shows the description of the current place and the items in it. This Instruction works if the user writes RADAR
 * @author Abel Serrano Juste
 */
public class RadarInstruction extends Commands {
	
	/**
	 * Default constructor. Not common way to create new RadarInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public RadarInstruction(){
		super();
	}
	
	/**
	 * Show information about items which are available in the current place.
	 */
	public void execute() {
		navi.scanCurrentPlace();
	}

	@Override
	public String getHelp() {
		return ("RADAR: This Instruction shows the description of the current place and the items located on it. " +
				"This Instruction is executed if the user writes RADAR");
	}

	/**
	 * Parses the String returning an instance of RadarInstruction or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of RadarInstruction
	 * @throws WrongInstructionFormatException When the String is not RADAR
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if (length==1 && words[0].equalsIgnoreCase("RADAR")){
			return new RadarInstruction();
		}
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'RADAR'");
	}
	
	@Override
	public String getSyntax() {
		return "RADAR";
	}

}

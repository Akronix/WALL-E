package tp.pr5.instructions;

//local packages:
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * Its execution request the robot to finish the simulation. This Instruction works if the user writes QUIT or SALIR
 * @author Abel Serrano Juste
 */
public class QuitInstruction extends Commands {
	
	
	/**
	 * Default constructor. Not common way to create new QuitInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public QuitInstruction (){
		super();
	}
	
	/**
	 * Request the robot to stop the simulation
	 */
	public void execute(){
		robot.requestQuit();
	}
	
	@Override
	public String getHelp(){
		return ("QUIT: It request the robot to finish the simulation. " +
				"This Instruction is executed if the user writes QUIT or SALIR");
	}
	
	
	/**
	 Parsers the String returning an instance of QuitInstruction or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction reference to an instance of QuitInstruction
	 * @throws WrongInstructionFormatException When the String is not QUIT | SALIR
	 */
	public QuitInstruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 1) && (words[0].equalsIgnoreCase("QUIT") || words[0].equalsIgnoreCase("SALIR")))
			return new QuitInstruction();
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	
	@Override
	public String getSyntax() {
		return "QUIT";
	}
	 
}

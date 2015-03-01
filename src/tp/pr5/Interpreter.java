package tp.pr5;

//local packages:
import tp.pr5.instructions.*;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;


/**
 * The interpreter is in charge of converting the user input in an instruction for the robot.<br/>
 *@author Abel Serrano Juste
 */
public class Interpreter {
	
	public static final String LINE_SEPARATOR =	System.getProperty("line.separator");
	public static final String DONT_UNDERSTAND_MESSAGE = "WALL·E says: I do not understand. Please repeat";
	public static String  HELP_SYNTAX_MESSAGE; //It returns a message help with the syntax of all the instructions for this game
			
	
	/* This an attribute with all available Instructions which WALL·E understands. 
	It's necessary for manage all the instructions from Interpreter */
	private static final Instruction[] INSTRUCTIONS = 
		{new DropInstruction(),
		new HelpInstruction(),
		new MoveInstruction(),
		new OperateInstruction(),
		new PickInstruction(),
		new QuitInstruction(),
		new RadarInstruction(),
		new ScanInstruction(),
		new TurnInstruction(),
		new UndoInstruction(),
		new ShopInstruction(),
		new BuyInstruction(),
		new DefuseInstruction(),
		new TriggerInstruction(),
		new StormInstruction(),
		};

	static {
		StringBuilder draft = new StringBuilder ("The valid instructions for WALL·E are:"+Interpreter.LINE_SEPARATOR);
		for (Instruction ins : INSTRUCTIONS){
			draft.append(ins.getSyntax()+LINE_SEPARATOR);
		}
		HELP_SYNTAX_MESSAGE = draft.toString();
	}	
	
	
	/**
	 * Generates a new instruction according to the user input.
	 * Note: I don't like at all this implementation.
	  It is written like that just because lecturer forced us to do it in this way 
	 * @param line A string with the user input
	 * @return The instruction read from the given line. If the instruction is not correct, then it throws an exception.
	 * @throws WrongInstructionFormatException If the instruction isn't recognized or is wrong typed.
	 */
	public static Instruction generateInstruction(String line) throws WrongInstructionFormatException{
		Instruction ins = null;
		boolean matchInstruction = false;
		
		//loop for parse the line trying to parse it with all the possibles instructions
		for (int it = 0; it<INSTRUCTIONS.length && !matchInstruction; it++){
			try{
				
				//we try to parse input line with the i-InstructionType
				ins = INSTRUCTIONS[it].parse(line);
				
				//if everything has done well, then we know that the i-instructionType matches with the input and we can finalize the search	
				matchInstruction = true;
			}
			
			//else we increment counter and repeat
			catch (WrongInstructionFormatException wife){
				//Nothing, we're looping until we've tried with all the possible types of instruction.
			}
		}

		if (matchInstruction)
			return ins;
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	
	/**
	 * It returns information about all the instructions that the robot understands
	 * @return A string with the information about all the available instructions
	 */
	public static String interpreterHelp(){
		StringBuffer helpText = new StringBuffer("Up to now, the valid instructions are:"+LINE_SEPARATOR);
		
		for (Instruction ins : INSTRUCTIONS){
			helpText.append("*** ");
			helpText.append(ins.getHelp());
			helpText.append(" ***"+LINE_SEPARATOR);
		}
		
		return (helpText.toString());
	}
	
}

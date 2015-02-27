package tp.pr5.instructions;

import tp.pr5.Interpreter;
//local packages
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * Shows the game help with all the instructions that the robot can execute.<br/>
 * This instruction works if the user writes HELP or AYUDA
 * @author Abel Serrano Juste
 */
public class HelpInstruction extends Commands{
	
	String target;
	
	/**
	 * Default constructor. Not common way to create new PickInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public HelpInstruction(){
		super();
	}
	
	public HelpInstruction(String input){
		super();
		target = input;
	}
	
	/**
	 * Prints the help string of every instruction. It delegates to the Interpreter class.
	 */
	public void execute(){
		robot.requestHelp();
		
	}
	
	/**
	 * Returns a description of the Help syntax.
	 * @return the instruction syntax HELP
	 */
	public String getHelp(){
		return ("HELP [ <syntax | instructionName | all > ]: With no params, shows this message." +
				"*syntax: Shows syntax for all the instructions that the robot can execute." +
				"*instructionName: Shows especified help for input instruction" +
				"*all: Shows help for all the instructions that the robot can execute."+
				"This instruction is executed if the user writes HELP or AYUDA");
	}
	
	
	/**
	 * Parses the String returning an instance its corresponding subclass if the string fits the instruction's syntax.
	 * This is a factory method, it's the only way to create a new HelpInstruction object.
	 * @param cad Input string where is defined the help Instruction
	 * @return HelpInstruction which extends Instruction, defined from input string.
	 * @throws WrongInstructionFormatException When the String is not HELP
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if (words[0].equalsIgnoreCase("HELP") || words[0].equalsIgnoreCase("AYUDA")){
		if (length == 1)
			return new HelpInstruction();
		}
		
		//else if the HelpInstruction has not been created:
		throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	

	@Override
	public String getSyntax() {
		return "HELP";
	}
	 
}

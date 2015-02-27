package tp.pr5.instructions;

//local packages:
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * Instruction to undo the last instruction performed by the robot. <br/>
 * It also has to back up the state of the robot before to do that operation.
 * @author Abel Serrano Juste
 */
public class UndoInstruction extends Commands{
	
	private Instruction lastInstruction;
	
	/**
	 * Default constructor. Not common way to create new UndoInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public UndoInstruction(){
		super();
	}
	
	@Override
	public void execute() throws InstructionExecutionException {
		lastInstruction = this.robot.getLastInstruction();
		if (lastInstruction != null) { 	//Undo can't be the first instruction of the program
			robot.saySomething("Go back in time..."+Interpreter.LINE_SEPARATOR+
					"Last "+lastInstruction.getClass()+" that never happened."+Interpreter.LINE_SEPARATOR);
			lastInstruction.undo();
		}
		else {
			throw new InstructionExecutionException("WALL·E haven't performed any instruction yet"+Interpreter.LINE_SEPARATOR);
		}
	}

	@Override
	public String getHelp() {
		return ("UNDO: It undo the last inlastInstruction.undo();struction made by the robot. " +
				"This Instruction is executed if the user writes UNDO or DESHACER");
	}

	/**
	 * Parses the String returning an instance of UndoInstruction or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of UndoInstruction
	 * @throws WrongInstructionFormatException When the String is not UNDO or DESHACER
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		if (length==1 && (words[0].equalsIgnoreCase("UNDO") || words[0].equalsIgnoreCase("DESHACER"))){
			return new UndoInstruction();
		}
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'UNDO'");
	}
	
	/**
	 * Undoing an undo => redo the last instruction 
	 */
	public void undo() {
		robot.saySomething("undoing an undo => redo"+Interpreter.LINE_SEPARATOR);
		robot.communicateRobot(lastInstruction);
	}
	
	@Override
	public String getSyntax() {
		return "UNDO";
	}

}

package tp.pr5.instructions;

//local packages
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.*;

/**
 * Its execution moves the robot from one place to the next one in the current direction. 
 * This instruction works if the user writes MOVE or MOVER
 * @author Abel Serrano Juste
 */
public class MoveInstruction extends Commands {

	/**
	 * Default constructor. Not common way to create new MoveInstruction objects.
	 * Parse is <i>factory method</i> for it.
	 */
	public MoveInstruction(){
		super();
	}

	/**
	 * Moves from the current place to the next place on the current Direction. An opened street must exist between both places to be moved
	 * @throws InstructionExecutionException When the robot cannot go to other place (there is a wall, a closed street...)
	 */
	public void execute() throws InstructionExecutionException{
		navi.move();
		robot.addFuel(-5);
	}

	@Override
	public String getHelp() {
		return ("MOVE: It moves the robot from one place to the next one in the current direction. " +
				"This instruction is executed if the user writes MOVE or MOVER");
	}

	/**
	 * Parses the String returning a MoveInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of MoveInstruction
	 * @throws WrongInstructionFormatException When the String is not MOVE
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 1) && (words[0].equalsIgnoreCase("MOVE") || words[0].equalsIgnoreCase("MOVER"))){
			return new MoveInstruction();
		}
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	
	/**
	 * Undo move instruction, this implies go back to the place where WALL·E was previously to execute this instruction.<br/>
	 * Also, it has to restore the energy consumed in the movement.
	 */
	public void undo() {
		this.navi.goInReverse();
		this.robot.addFuel(+5);
		robot.saySomething(navi.getCurrentPlace().toString()+Interpreter.LINE_SEPARATOR);
	}
	
	@Override
	public String getSyntax() {
		return "MOVE";
	}
}

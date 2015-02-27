package tp.pr5.instructions;

//local packages:
import tp.pr5.*;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.ItemContainer;

/**
 * This class contains the information parsed from the input. <br/>
 * It represents an instruction that can be interpreted by the robot.<br/>
 * An instruction encapsulates an Action and a Rotation (if the action is TURN)
 * @author Abel Serrano Juste
 */
public interface Instruction {

	/**
	 * Set the execution context.
	 * @param engine
	 * @param navigation
	 * @param robotContainer
	 */
	public abstract void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer);
	
	/**
	 * Executes the Instruction. It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute () throws InstructionExecutionException;
	
	/**
	 * Returns a short statement of the Instruction syntax.
	 * @return The Instruction's syntax.
	 */
	public abstract String getSyntax();
	
	/**
	 * Returns a description of how to use and which is the effect of this Instruction.
	 * @return The Instruction's use.
	 */
	public abstract String getHelp();
	
	
	/**
	 * Parses the String returning an instance its corresponding subclass if the string fits the instruction's syntax. 
	 * Otherwise it throws an WrongInstructionFormatException. 
	 * Each non abstract subclass must implement its corresponding parse.
	 * @param cad Text String
	 * @return Instruction Reference pointing to an instance of a Instruction subclass, if it is corresponding to the String cad
	 * @throws WrongInstructionFormatException When the String cad does not fit the Instruction syntax.
	 */
	public abstract Instruction	parse(String cad) throws WrongInstructionFormatException;
	 
	/**
	 * Undo this instruction, returning to the state previous of the robot to perform this instruction<br/>
	 * We assume that the instruction was correctly executed previously, so it only throw exception 
	 * when it isn't possible undo the instruction.
	 * @throws InstructionExecutionException If the instruction can't be undone.
	 */
	public abstract void undo() throws InstructionExecutionException;
	
}

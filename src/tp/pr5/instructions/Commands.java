package tp.pr5.instructions;

//local packages:
import tp.pr5.Interpreter;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.ItemContainer;

/**
 * Abstract class for all the common instructions which executes WALL·E.
 * It implements the method configureContext of Instruction interface
 * @author Abel Serrano Juste
 */
public abstract class Commands implements Instruction {

	protected RobotEngine robot;
	protected NavigationModule navi;
	protected ItemContainer inventory;
	
	
	/**
	 * There are constructor for testing purposes and others, but it's not natural way no create new commands.<br/>
	 * 'Parse' is a <i>factory method</i> for creating new Instruction objects.
	 */
	protected Commands(){
		robot=null;
		navi=null;
		inventory=null;
	}

	/**
	 * Set the execution context.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		robot=engine;
		navi=navigation;
		inventory=robotContainer;
	}
	
	/**
	 * Undo this instruction, returning to the state previous of the robot to perform this instruction.<br/>
	 * We assume that the instruction was correctly executed previously, so it shouldn't throw any exception.<br/>
	 * <b>Override this if undo the inherited instruction implies any change in the robot.</b>
	 */
	public void undo() throws InstructionExecutionException{
			throw new InstructionExecutionException("Upss nothing to undo..."+Interpreter.LINE_SEPARATOR);
		}

}

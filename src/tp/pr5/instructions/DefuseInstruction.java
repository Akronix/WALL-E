package tp.pr5.instructions;

//local packages:
import tp.pr5.*;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.Bomb;
import tp.pr5.items.Item;

/**
 * This instruction try to defuse a bomb
 * @author Abel Serrano Juste
 */
public class DefuseInstruction extends Commands {

	private String itemId;
	
	//Messages
	public static final String NOT_FOUND_MESSAGE = "<itemName> not found.";
	public static final String CANNOT_MESSAGE = "It seems that <itemName> cannot be defused";
	public static final String SUCCESS_MESSAGE = "<itemName> has been defused";
	
	/**
	 * Default constructor. Not common way to create new MoveInstruction <br/>
	 * Parse is a <i>factory method</i> for creating new DropInstruction objects.
	 */
	public DefuseInstruction(){
		super();
	}
	
	/**
	 * Contructor with id. Not common way to create new MoveInstruction <br/>
	 * 'Parse' is a factory method for creating new OperateInstruction objects
	 * @param id name of the item
	 */
	public DefuseInstruction(String id){
		super();
		itemId=id;
	}
	
	/**
	 * The robot drops an Item from its inventory in the current place, if the item exists
	 * @throws InstructionExecutionException When the robot inventory does not contain an item with this name or when there is another item with the same id in the current place
	 */
	public void execute() throws InstructionExecutionException{

		Item auxItem = navi.pickItemFromCurrentPlace(itemId);
		
		if (auxItem != null) {
			
			navi.dropItemAtCurrentPlace(auxItem);
			
			if (auxItem instanceof Bomb) {
				Bomb bomb = (Bomb) auxItem;
				boolean desactivable = bomb.isActivated()
							&& bomb.getPoints() <= robot.getRecycledMaterial();
				if (desactivable) {
					robot.addRecycledMaterial(-bomb.getPoints());
					bomb.setActivated(false);
					robot.saySomething(SUCCESS_MESSAGE.replace("<itemName>",itemId)+Interpreter.LINE_SEPARATOR);
				}
				else {
					throw new InstructionExecutionException (DefuseInstruction.CANNOT_MESSAGE.replace("<itemName>",itemId)+Interpreter.LINE_SEPARATOR);
				}
					
			}
			else
				throw new InstructionExecutionException (DefuseInstruction.CANNOT_MESSAGE.replace("<itemName>",itemId)+Interpreter.LINE_SEPARATOR);
		}
		else
			throw new InstructionExecutionException (DefuseInstruction.NOT_FOUND_MESSAGE.replace("<itemName>",itemId)+Interpreter.LINE_SEPARATOR);
			
	}
	
	/**
	 * The instruction syntax<br/><br/>
	 * 
	 * DEFUSE <id>
	 */
	public String getHelp() {
		return ("DEFUSE<id>: This instruction tries to defuse a bomb in this place " +
				"It need id field. "+
				"This instruction is executed if the user writes DEFUSE or DESACTIVAR ");
	}

	/**
	 * Parses the String returning an instance of DropInstruction or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of DropInstruction
	 * @throws WrongInstructionFormatException When the String is not <br/> <br/>
	 * 
	 * 			DROP <id>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 2) && (words[0].equalsIgnoreCase("DEFUSE") || words[0].equalsIgnoreCase("DESACTIVAR")))
			return new DefuseInstruction(words[1]);
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	
	/**
	 * Undo this instruction, returning to the state previous of the robot to perform this instruction.<br/>
	 * We assume that the instruction was correctly executed previously, so it shouldn't throw any exception.<br/>
	 * <b>Override this if undo the inherited instruction implies any change in the robot.</b>
	 */
	public void undo() {
		Bomb bomb = (Bomb) this.navi.pickItemFromCurrentPlace(itemId);
		robot.addRecycledMaterial(bomb.getPoints());
		bomb.setActivated(true);
		this.navi.dropItemAtCurrentPlace(bomb);
	}
	
	@Override
	public String getSyntax() {
		return "DEFUSE <id>";
	}
	
}

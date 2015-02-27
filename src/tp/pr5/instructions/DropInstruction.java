package tp.pr5.instructions;

//local packages:
import tp.pr5.*;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.Item;

/**
 * This instruction drops an Item from the current place and puts it the robot inventory. This instruction works if the user writes DROP or SOLTAR
 * @author Abel Serrano Juste
 */
public class DropInstruction extends Commands {

	private String itemId;
	
	/**
	 * Default constructor. Not common way to create new MoveInstruction <br/>
	 * Parse is a <i>factory method</i> for creating new DropInstruction objects.
	 */
	public DropInstruction(){
		super();
	}
	
	/**
	 * Contructor with id. Not common way to create new MoveInstruction <br/>
	 * 'Parse' is a factory method for creating new OperateInstruction objects
	 * @param id name of the item
	 */
	public DropInstruction(String id){
		super();
		itemId=id;
	}
	
	/**
	 * The robot drops an Item from its inventory in the current place, if the item exists
	 * @throws InstructionExecutionException When the robot inventory does not contain an item with this name or when there is another item with the same id in the current place
	 */
	public void execute() throws InstructionExecutionException{

		if (!navi.getCurrentPlace().isSpecialPlace()){
			Item auxItem = inventory.pickItem(itemId);
			if (auxItem==null)
				throw new InstructionExecutionException("You do not have any "+itemId+".");
	
			if (navi.dropItemAtCurrentPlace(auxItem)) {
				robot.saySomething("WALL·E says: Great! I have dropped "+itemId+Interpreter.LINE_SEPARATOR);
			}
			else {
				inventory.addItem(auxItem);
				throw new InstructionExecutionException("Item "+itemId+" is already in "+navi.getCurrentPlace().getPlaceName());
			}
		}
		else
			throw new InstructionExecutionException("You cannot drop Items at this place");
			
	}
	
	/**
	 * The instruction syntax<br/><br/>
	 * 
	 * DROP <id>
	 */
	public String getHelp() {
		return ("DROP<id>: This instruction drops an Item in the current place from the inventory. " +
				"It need id field. "+
				"This instruction is executed if the user writes DROP or SOLTAR ");
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
		if ((length == 2) && (words[0].equalsIgnoreCase("DROP") || words[0].equalsIgnoreCase("SOLTAR")))
			return new DropInstruction(words[1]);
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}

	/**
	 * Undo drop instruction, this implies restore (pick) the item to the inventory of WALL·E.
	 */
	public void undo() {
		Item auxItem = navi.pickItemFromCurrentPlace(itemId); //First we pick the item from the current place
		this.inventory.addItem(auxItem); //Later we restore it to the inventory of WALL·E
	}

	@Override
	public String getSyntax() {
		return "DROP <id>";
	}
	
}

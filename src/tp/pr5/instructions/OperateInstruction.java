package tp.pr5.instructions;

//local packages:
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.Item;

/**
 * The Instruction for using an item. This Instruction works if the user writes OPERATE id or OPERAR id
 * @author Abel Serrano Juste
 *
 */
public class OperateInstruction extends Commands {

	private String itemId;
	
	private Item itemToOperate; //Variable of Item type of the item to operate, necessary store for can perform the undo method.
								//It isn't assigned in the constructor, it's assigned when execute() is called, so when you undo item it can't be null
	
	/**
	 * Default constructor. Not common way to create new OperateInstruction objects
	 * Parse is <i>factory method</i> for it.
	 */
	public OperateInstruction(){
		super();
	}
	
	/**
	 * Contructor with id. Not common way to create new OperateInstruction objects <br/>
	 * 'Parse' is a factory method for it.
	 * @param id name of the item 
	 */
	public OperateInstruction(String id){
		super();
		itemId=id;
	}
	
	/**
	 * The robot uses the requested item.
	 * @throws InstructionExecutionException When the robot inventory does not contain an item with this name or when the item cannot be used
	 */
	public void execute() throws InstructionExecutionException{
			itemToOperate = inventory.getItem(itemId);
			if (itemToOperate!=null){
				boolean works = itemToOperate.use(robot, navi);
				if (works){
					if (!itemToOperate.canBeUsed()){
						robot.saySomething("WALL·E says: What a pity! I have no more "
								+itemToOperate.getId()+" in my inventory"+Interpreter.LINE_SEPARATOR);
						inventory.pickItem(itemToOperate.getId());
					}
				}
				else
					throw new InstructionExecutionException("WALL·E says: I have problems using the object "+itemId);
				}
			else
				throw new InstructionExecutionException("WALL·E says: I have not such object");
	}

	@Override
	public String getHelp() {
		return ("OPERATE<id>: This is for using an item. " +
				"It need id field. "+
				"This Instruction is executed if the user writes OPERATE id or OPERAR id");
	}

	/**
	 * Parses the String returning an instance of OperateInstruction or throwing a WrongInstructionFormatException()
	 * @param cad String to parse
	 * @return Instruction Reference to an instance of OperateInstruction
	 * @throws WrongInstructionFormatException When the String is not <br/><br/>
				OPERATE|OPERAR <ID>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 2) && (words[0].equalsIgnoreCase("OPERATE") || words[0].equalsIgnoreCase("OPERAR")))
			return new OperateInstruction(words[1]);
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}

	/**
	 * Undo operate instruction, this implies revert the state of fuel and recycled material of WALL·E previously
	 * and restore the uses of the item if appropriate.<br/>
	 * It uses the item store in OperateInstruction, which it must be correct assigned in executed.<br/>
	 */
	public void undo() {
		/*Note that itemToOperate never can be null because then the instruction would throw an InstructionExecutionException
		and the app can't call undo under it.*/
		itemToOperate.revert(robot, navi); //First, we undo the effects of the item.
		inventory.pickItem(itemId); //Later we eliminate remainder of the element from the inventory to avoid repetition and less uses of Fuel items.
		inventory.addItem(itemToOperate); //Now we add the item in its state previously to be operate.
		
	}
	
	@Override
	public String getSyntax() {
		return "OPERATE <id>";
	}
	
}

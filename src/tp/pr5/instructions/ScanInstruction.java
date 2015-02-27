package tp.pr5.instructions;

//local packages:
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;

/**
 * The execution of this instruction shows the information of the inventory of the robot or the complete description about the item with identifier id contained in the inventory.
 *  This Instruction works if the player writes SCAN or ESCANEAR (id is not mandatory)
 * @author Abel Serrano Juste
 *
 */
public class ScanInstruction extends Commands {

	private String itemId;
	
	/**
	 * Default constructor. Not common way to create new ScanInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public ScanInstruction(){
		super();
		this.itemId="";
	}
	
	/**
	 * Contructor with id. Not common way to create new ScanInstruction objects.
	 * 'Parse' is a factory method for it
	 * @param id name of the item 
	 */
	private ScanInstruction(String id){
		super();
		this.itemId=id;
	}
	
	/**
	 * Prints the description of a concrete item or the complete inventory of the robot
	 * @throws InstructionExecutionException When the robot does not contain the item to be scanned
	 */
	public void execute() throws InstructionExecutionException {
		
		if (itemId.isEmpty()){
			if (!this.inventory.isEmpty())
				robot.saySomething(this.inventory.toString()+Interpreter.LINE_SEPARATOR);
			else
				throw new InstructionExecutionException("WALL·E says: My inventory is empty");
		}
		else{
			Item auxItem=inventory.getItem(itemId);
			if (auxItem!=null)
				robot.saySomething("WALL·E says: "+auxItem.toString()+Interpreter.LINE_SEPARATOR);
			else
				throw new InstructionExecutionException("WALL·E says: I have not such object");
		}
			
	}

	@Override
	public String getHelp() {
		return ("SCAN[<id>] :The execution of this instruction shows the items which has get WALL·E or the complete description about one item contained in that inventory. " +
				"id field is optional. "+
				"This Instruction is executed if the player writes SCAN or ESCANEAR");
	}

	/**
	 * Parses the String returning a ScanInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of ScanInstruction
	 * @throws WrongInstructionFormatException When the String is not - SCAN | ESCANEAR [id]
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		if (words[0].equalsIgnoreCase("SCAN") || words[0].equalsIgnoreCase("ESCANEAR")){
			if (length==1)
				return new ScanInstruction();
			else if (length==2)
				return new ScanInstruction(words[1]);
			else
				throw new WrongInstructionFormatException("Incorrect Format for instruction 'SCAN'. Incorrect number of words");
		}
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'SCAN'");
	}
	
	@Override
	public String getSyntax() {
		return "SCAN [ <id> ]";
	}

}

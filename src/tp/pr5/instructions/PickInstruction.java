package tp.pr5.instructions;

//local packages
import tp.pr5.Interpreter;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Bomb;
import tp.pr5.items.Item;

/**
 * This instruction tries to pick an Item from the current place and puts it the robot inventory. 
 * This instruction works if the user writes PICK id or COGER id
 * @author Abel Serrano Juste
 */
public class PickInstruction extends Commands {

	private String itemId;
	
	/**
	 * Default constructor. Not common way to create new PickInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public PickInstruction(){
		super();
	}
	
	/**
	 * Contructor with id. Not common way to create new PickInstruction objects.
	 * 'Parse' is a factory method for it
	 * @param id name of the item 
	 */
	private PickInstruction(String id){
		super();
		itemId=id;
	}

	/**
	 * The robot adds an item to its inventory from the current place, if it exists
	 * @throws InstructionExecutionException When the place does not contain an item with this name or when there is another item with the same id in the robot inventory
	 */
	public void execute() throws InstructionExecutionException{

		if (!navi.getCurrentPlace().isSpecialPlace()){
			Item auxItem = navi.pickItemFromCurrentPlace(itemId);
			if (auxItem != null){
				if (auxItem instanceof Bomb) {
						Bomb bomb = (Bomb) auxItem;
						//It's a bomb & it's activated
						if (bomb.isActivated()) {
							Integer damage = bomb.getDamage();
							robot.addFuel(-damage);
							throw new InstructionExecutionException("BOOOOMMMBBBBB. You lose <points> life points.".replace("<points>", damage.toString())+Interpreter.LINE_SEPARATOR);
						}
						//It's a bomb but it's deactivated
						else if (this.inventory.addItem(bomb)) {
							robot.saySomething("WALL·E says: I am happy! Now I have "+itemId+Interpreter.LINE_SEPARATOR);
						}
				}
				//It's not a bomb
				else if (this.inventory.addItem(auxItem)) {
					robot.saySomething("WALL·E says: I am happy! Now I have "+itemId+Interpreter.LINE_SEPARATOR);
				}
				else {
					this.navi.dropItemAtCurrentPlace(auxItem);
					throw new InstructionExecutionException("WALL·E says: I am stupid! I had already the object "+itemId);
				}
			}
			else
				throw new InstructionExecutionException("WALL·E says: Ooops, this place has not the object "+itemId);
		}
		else
			throw new InstructionExecutionException("You cannot pick Items at this place");

	}

	@Override
	public String getHelp() {
		return ("PICK<id>: This instruction tries to pick an Item from the current place and puts it the robot inventory. " +
				"It need a id field "+
				"This instruction is executed if the user writes PICK id or COGER id");
	}

	/**
	 * Parses the String returning an instance of PickInstruction or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @throws WrongInstructionFormatException When the String is not <br/><br/>
	 * 
			PICK|COGER <id>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 2) && (words[0].equalsIgnoreCase("PICK") || words[0].equalsIgnoreCase("COGER")))
			return new PickInstruction(words[1]);
		else
			throw new WrongInstructionFormatException(Interpreter.DONT_UNDERSTAND_MESSAGE);
	}
	
	/**
	 * Undo pick instruction, this implies drop the item to the place where it was taken.
	 */
	public void undo() {
		Item auxItem = inventory.pickItem(itemId); //First we have to take out the item from the inventory
		navi.dropItemAtCurrentPlace(auxItem); //Later we have to drop it to the current place.
	}
	
	@Override
	public String getSyntax() {
		return "PICK <id>";
	}

}

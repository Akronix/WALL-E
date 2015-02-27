/**
 * 
 */
package tp.pr5.instructions;

import tp.pr5.Shop;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;

/**
 * @author Abel Serrano Juste
 *
 */
public class BuyInstruction extends Commands {
	
	private String itemId;

	public BuyInstruction() {
		super();
	}
	
	public BuyInstruction( String id) {
		this();
		this.itemId = id;
	}


	/**
	 * Try to buy the input item by from the shop.<br/>
	 * Precond:
	 * The shop has that item in stock
	 * WALLE has enough points to buy it
	 * WALLE haven't got the item yet.
	 * PostCond:
	 * WALLE lost points that item costs
	 * Shop drop the item
	 */
	@Override
	public void execute() throws InstructionExecutionException {
		//Check if the shop has the item
		Shop shop = navi.getShop();
		Item stockItem = shop.lookForItemInStock(itemId);
		if (stockItem != null){
			int itemPrice = stockItem.getPrice();
			int robotPoints = robot.getRecycledMaterial();
			if (itemPrice <= robotPoints) {
				
				if (this.inventory.addItem(stockItem)){
					robot.addRecycledMaterial(-itemPrice);
					shop.removeItemOfStock(itemId);
				}
			
				else
					throw new InstructionExecutionException("WALL·E says: I am stupid! I had already the object "+itemId);
			}	
			else
				throw new InstructionExecutionException("WALL·E says: Not have enough money. The item cost "+itemPrice+ " and I only have "+ robotPoints);
				
		}
		else
			throw new InstructionExecutionException("WALL·E says: <"+itemId+"> It isn't in stock of the shop");
			

	}

	/* (non-Javadoc)
	 * @see tp.pr5.instructions.Instruction#getHelp()
	 */
	@Override
	public String getHelp() {
		return ("BUY<id>: This instruction buys an Item of the shop paying its price with the points of WALL·E " +
				"It need id field. "+
				"This instruction is executed if the user writes BUY or COMPRAR ");
	}

	/* (non-Javadoc)
	 * @see tp.pr5.instructions.Instruction#parse(java.lang.String)
	 */
	@Override
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		if ((length == 2) && (words[0].equalsIgnoreCase("BUY") || words[0].equalsIgnoreCase("COMPRAR")))
			return new BuyInstruction(words[1]);
		else
			throw new WrongInstructionFormatException("WALL·E says: I do not understand. Please repeat");
	}
	
	/**
	 * Undo this instruction, returning to the state previous of the robot to perform this instruction.<br/>
	 * We assume that the instruction was correctly executed previously, so it shouldn't throw any exception.<br/>
	 * <b>Override this if undo the inherited instruction implies any change in the robot.</b>
	 */
	public void undo() throws InstructionExecutionException{
			Item itBought = this.inventory.pickItem(itemId);
			robot.addRecycledMaterial(+itBought.getPrice());
			navi.getShop().addItemToStock(itBought);
		}

	@Override
	public String getSyntax() {
		return "BUY <id>";
	}

}

package tp.pr5.instructions;
import tp.pr5.Interpreter;
import tp.pr5.Shop;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * @author Abel Serrano Juste
 *
 */
public class ShopInstruction extends Commands {
	
	private Shop shop;

	/**
	 * 
	 */
	public ShopInstruction() {
		super();
	}

	/* (non-Javadoc)
	 * @see tp.pr5.instructions.Instruction#execute()
	 */
	@Override
	public void execute() throws InstructionExecutionException {
		shop = navi.getShop();
		robot.saySomething(shop.toString());
	}

	/* (non-Javadoc)
	 * @see tp.pr5.instructions.Instruction#getHelp()
	 */
	@Override
	public String getHelp() {
		return ("shop : This instructions shows the items available to buy in the shop"+ Interpreter.LINE_SEPARATOR);
	}

	/* (non-Javadoc)
	 * @see tp.pr5.instructions.Instruction#parse(java.lang.String)
	 */
	@Override
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		String[] words = cad.split(" ");
		int length = words.length;
		if (length==1 && words[0].equalsIgnoreCase("SHOP")){
			return new ShopInstruction();
		}
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'SHOP'");
	}
	
	@Override
	public String getSyntax() {
		return "SHOP [ <id> ]";
	}

}

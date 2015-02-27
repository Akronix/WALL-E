package tp.pr5.instructions;

//local packages
import tp.pr5.StormType;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * This instruction tries to pick an Item from the current place and puts it the robot inventory. 
 * This instruction works if the user writes PICK id or COGER id
 * @author Abel Serrano Juste
 */
public class StormInstruction extends Commands {

	private StormType stormType;
	
	/**
	 * Default constructor. Not common way to create new PickInstruction objects. 
	 * Parse is a <i>factory method</i> for it.
	 */
	public StormInstruction(){
		super();
	}
	
	/**
	 * Contructor with id. Not common way to create new PickInstruction objects.
	 * 'Parse' is a factory method for it
	 * @param stormType name of the item 
	 */
	private StormInstruction(StormType stormType){
		super();
		this.stormType=stormType;
	}

	/**
	 * 
	 * @throws InstructionExecutionException 
	 */
	public void execute() throws InstructionExecutionException{
		double damage = this.stormType.getDamage();
		damage -= damage * this.navi.getCurrentPlace().getProtection();
		robot.addShield(-damage);
	}

	@Override
	public String getHelp() {
		return ("STORM<id>: This instruction tries to pick an Item from the current place and puts it the robot inventory. " +
				"It need a id field "+
				"This instruction is executed if the user writes STORM type or TORMENTA tipo");
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
		StormType type;
		if (length==2 && (words[0].equalsIgnoreCase("STORM") || words[0].equalsIgnoreCase("TORMENTA"))){
			type = StormType.parseStormType((words[1]));
			
			if (type != null)
				return new StormInstruction(type);
			
			else
				throw new WrongInstructionFormatException("Incorrect Format for instruction 'STORM'");
		}
		
		else
			throw new WrongInstructionFormatException("Incorrect Format for instruction 'STORM'");
	}
	
	/**
	 * Undo storm instruction.
	 */
	public void undo() {
		double damage = this.stormType.getDamage();
		damage -= damage * this.navi.getCurrentPlace().getProtection();
		robot.addShield(damage);
	}
	
	@Override
	public String getSyntax() {
		return "STORM <Type>";
	}

}

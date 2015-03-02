package tp.pr5.items;

//local packages:
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * A CodeCard can open or close the door placed in the streets. 
 * The card contains a code that must match the street code in order to perform the action.
 * @author Abel Serrano Juste
 */
public class CodeCard extends PlaceableItem{
	
	private String code;
	
	/**
	 * Code card constructor
	 * @param id Code card name
	 * @param description Code card description
	 * @param code Secret code stored in the code card
	 */
	public CodeCard(String id, String description, String code){
		super(id, description);
		this.code=code;
	}

	
	/**
	 * A code card always can be used. Since the robot has the code card it never loses it
	 * @return <code>true</code> because it always can be used
	 */
	public boolean canBeUsed(){
		return true;
	}
	
	
	/**
	 * The method to use a code card. If the robot is in a place which contains a street in the direction he is looking at, then the street is opened or closed if the street code and the card code match.
	 * @param r The robot engine employed to use the card.
	 * @return <code>true</code> if the code card can complete the action of opening or closing a street. Otherwise it returns <code>false</code>.
	 */
	public boolean use(RobotEngine r, NavigationModule nm){
		return nm.useCard(code);
	}
	
	/**
	 * It closed the street it this card opened it and viceversa.
	 * @param r The robot engine employed to use the card.
	 * @return code>true</code> if the code card can complete the action of undo opening or closing a street. Otherwise it returns <code>false</code>.
	 */
	public boolean revert(RobotEngine r, NavigationModule nm){
		return nm.useCard(code);
	}
	
	
	/**
	 * Gets the code stored in the code card
	 * @return A String that represents the code
	 */
	public String getCode(){
		return (this.code);
	}
	
	@Override
	/**
	 * String representation of this CodeCard Object
	 * @return The item name + the item description
	 */
	public String toString(){
		return super.toString();
	}
	

	@Override
	/**
	 * Returns a deep copy of this object
	 */
	public Item clone() {
		return new CodeCard(this.id,this.descp,this.code);
	}
	
}

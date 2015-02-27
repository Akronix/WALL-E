package tp.pr5.items;

//local packages:
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * The superclass of every type of item. <br/>
 * It contains the common information for all the items and it defines the interface that the items must match
 * One item is defined by a name and a description.<br/>
 * Each item has one effect that is performed when the method use() is executed. You can revert that effect with revert()<br/
 * Two items are equals if they have the same id ignoring case.
 * @author Abel Serrano Juste
 */
public abstract class Item implements Comparable<Item>, Cloneable{

	protected String id;
	protected String descp;
	protected int price;
	
	/**
	 * Builds an item from a given identifier and description
	 * @param id Item identifier
	 * @param description Item description
	 */
	public Item(String id, String description){
		this.id=id;
		this.descp=description;
		this.price = 0;
	}
	
	/**
	 * Checks if the item can be used. Subclasses must override this method
	 * @return <code>true</code> if the item can be used
	 */
	public abstract boolean canBeUsed();
	
	/**
	 * Try to use the item with a robot in a given place. It returns whether the action was completed. Subclasses must override this method
	 * @param r The robot that uses the item
	 * @param nm The navigation module for knowing where WALL·E uses the item.
	 * @return <code>true</code> if the action was completed. Otherwise, it returns <code>false</code>
	 */
	public abstract boolean use(RobotEngine r, NavigationModule nm);
	
	/**
	 * Revert the effect of the item under the robot engine and the navigation module.<br/>
	 * It's the opposite of use() and it's needed to undo operate instructions.
	 * @param r The robot that u the item
	 * @param nm The navigation module for knowing where WALL·E used the item.
	 * @return <code>true</code> if the action was completed. Otherwise, it returns <code>false</code><br/>
	 * 	 * It's expected it always return <code>true</code> if we're reverting a valid previous use()
	 */
	public boolean revert(RobotEngine r, NavigationModule nm) {
		return false;
	}
	
	//Getters & Setters

	/**
	 * Get id field of the item
	 * @return String id field
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Se the id field of the item
	 * @param s String of the id to set
	 */
	public void setId(String s){
		this.id=s;
	}
	
	/**
	 * Get description of the item
	 * @return String description field
	 */
	public String getDescp(){
		return this.descp;
	}
	
	/**
	 * Set description field of the item
	 * @param s SString description to set
	 */
	public void setDescp(String s){
		this.descp=s;
	}
	
	@Override
	public int compareTo(Item i){
		int index = 0;
		String idExplicit=i.id.toUpperCase();
		String idImplicit=this.id.toUpperCase();
		boolean carryOn = true;
		int result;
		
		//Check char by char if they are equals
		while (index<idImplicit.length() && index<idExplicit.length() && carryOn){
			carryOn = (idExplicit.charAt(index) == idImplicit.charAt(index));
			index++;
		}
		
		if (carryOn){ //we've processed all the chars of at least one string
			
			if (idExplicit.length()==idImplicit.length()) //If they have same length, they are equals
				result = 0;
			else 
				if (idImplicit.length()>idExplicit.length()) //If this is larger, then return 1
					result = 1;
				else 										//Else, if this is least, then return -1
					result = -1;
		}
			
		else{
			--index; //Going to last position compared
			
			//If last compared is greater in this string, then return 1
			if (idImplicit.charAt(index)>idExplicit.charAt(index))
				result = 1;
			
			//If last compared is least in this string, then return -1
			else //if (idImplicit.charAt(index)<idExplicit.charAt(index))
				result = -1;
		}
		
		return result;
	}
	
	public void setPrice( int price){
		this.price = price;
	}
	
	public int getPrice(){
		return this.price;
	}

	@Override
	/**
	 * Standard eclipse method of hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this item.<br/>
	 * Two items are equal when they have the same id, ignoring case.
	 */
	@Override
	public boolean equals(Object it2){
		return (it2 instanceof Item) && this.id.equalsIgnoreCase(((Item) it2).getId());
	}

	/**
	 * String representation of this Item
	 * @return The item name + the item description
	 */
	@Override
	public String toString(){
		return (this.id+": "+this.descp);
	}
	
}

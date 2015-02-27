/**
 * 
 */
package tp.pr5;

import tp.pr5.items.Item;

/**
 * @author Abel Serrano Juste
 *
 */
public class Bunker extends Place {

	/**
	 * @param n
	 * @param descp
	 */
	public Bunker(String n, String descp) {
		super(n, descp);
		this.protection = 1;
	}
	
	/**
	 * It determines if WALL·E is in the SpaceShip
	 * @return <code>true</code> if the place represents a spaceship; else <code>false</code>.
	 */
	public boolean isSpaceship(){
		return false;
	}
	
	/**
	 * Overrides toString method. Returns the place name, its description and the list of items contained in the place. <br/>
	 * It doesn't show if WALL·E is the Spaceship
	 */
	@Override
	public String toString() {
		
		return (this.name+Interpreter.LINE_SEPARATOR+
				this.description+Interpreter.LINE_SEPARATOR+
				this.getPlaceItemsDescp()+Interpreter.LINE_SEPARATOR+
				"This place is a Bunker"+Interpreter.LINE_SEPARATOR);
		
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the place. If the action was completed the item must be removed from the place.
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItem (String id){
		 return null;
	}
	
	/**
	 * Tries to add an item to the place. The operation can fail (if the place already contains an item with the same name)
	 * @param it The item to be added
	 * @return <code>true</code> if and only if the item can be added to the place, i.e., the place does not contain an item with the same name
	 */
	public boolean addItem(Item it){
		return false;
	}
	
	/**
	 * Drop an item in this place. The operation can fail, returning false
	 * @param it The name of the item to be dropped.
	 * @return <code>true</code> if and only if the item is dropped in the place, i.e., an item with the same identifier does not exists in the place
	 */
	public boolean dropItem(Item it){
		return false;
	}
	
	/**
	 * Checks if an item is in this place
	 * @param itemId Identifier of an item
	 * @return <code>true</code> if and only if the place contains the item identified by id
	 */
	public boolean existItem(String itemId){
		return false;
	}

	@Override
	public boolean equals(Object obj){
		boolean same;
		if (obj.getClass().equals(this.getClass())) {
			Bunker explicitPlace = (Bunker) obj;
			same = this.name.equals(explicitPlace.getPlaceName()); //same name
		}
		else
			same = false;
		
		return same;
	}
	
	public boolean isSpecialPlace(){
		return true;
	}
	
	/**
	 * Return the items contained in this place
	 * @return String with the corresponding place message and a list of the items id contained in this place
	 */
	@Override
	public String getPlaceItemsDescp() {
		return Place.PLACE_EMPTY_MSG;
	}



}

package tp.pr5;

//local packages:
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * It represents a place in the city.<br/>
 *  Places are connected by streets according to the 4 compass directions: North, East, South and West. <br/>
 *  Every place has a name and a textual description about itself. This description is displayed when the robot arrives at the place.<br/>
 *  A place can represent the spaceship where the robot is safe.
 *  When the robot arrives at this place, the robot shuts down and the application will finish.
 *  @author Abel Serrano Juste
 */

public class Place implements PlaceInfo{
	
	protected String name;
	private boolean isSpaceShip;
	protected String description;
	private ItemContainer items;
	protected double protection;
	
	public static final String PLACE_EMPTY_MSG = "The place is empty. There are no objects to pick"+Interpreter.LINE_SEPARATOR;
	public static final String PLACE_WITH_ITEMS_MSG = "The place contains these objects:"+Interpreter.LINE_SEPARATOR;
	
	/**
	 * Creates the place with arguments: name and description
	 * @param n Place name
	 * @param descp Place description
	 */
	public Place(String n, String descp) {
		this.name=n;
		this.description=descp;
	}
	
	/**
	 * Creates the place with arguments: name, isSpaceship and description
	 * @param n Place name
	 * @param iSS Is it a spaceship?
	 * @param descp Place description
	 */
	public Place (String n,boolean iSS,String descp){
		this(n,descp);
		this.isSpaceShip=iSS;
		this.items = new ItemContainer();
		this.setProtection(0);
	}

	/**
	 * It determines if WALL·E is in the SpaceShip
	 * @return <code>true</code> if the place represents a spaceship; else <code>false</code>.
	 */
	public boolean isSpaceship(){
		return isSpaceShip;
	}
	
	/**
	 * Overrides toString method. Returns the place name, its description and the list of items contained in the place. <br/>
	 * It doesn't show if WALL·E is the Spaceship
	 */
	@Override
	public String toString() {
		
		return (this.name+Interpreter.LINE_SEPARATOR+
				this.description+Interpreter.LINE_SEPARATOR+
				this.getPlaceItemsDescp()+Interpreter.LINE_SEPARATOR);
		
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the place. If the action was completed the item must be removed from the place.
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItem (String id){
		 return this.items.pickItem(id);
		
	}
	
	/**
	 * Tries to add an item to the place. The operation can fail (if the place already contains an item with the same name)
	 * @param it The item to be added
	 * @return <code>true</code> if and only if the item can be added to the place, i.e., the place does not contain an item with the same name
	 */
	public boolean addItem(Item it){
		return items.addItem(it);
	}
	
	/**
	 * Drop an item in this place. The operation can fail, returning false
	 * @param it The name of the item to be dropped.
	 * @return <code>true</code> if and only if the item is dropped in the place, i.e., an item with the same identifier does not exists in the place
	 */
	public boolean dropItem(Item it){
		return items.addItem(it);
	}
	
	/**
	 * Checks if an item is in this place
	 * @param itemId Identifier of an item
	 * @return <code>true</code> if and only if the place contains the item identified by id
	 */
	public boolean existItem(String itemId){
		return items.containsItem(itemId);
	}

	@Override
	public boolean equals(Object obj){
		boolean same;
		if (obj.getClass().equals(this.getClass())) {
			Place explicitPlace = (Place) obj;
			same = this.name.equals(explicitPlace.getPlaceName()) //same name
					&& this.isSpaceShip==explicitPlace.isSpaceShip  //in both there is space ship.
					&& this.items.equals(explicitPlace.items); //same items. Use default of object in the wake of efficiency.
		}
		else
			same = false;
		
		return same;
	}
	
	public boolean isSpecialPlace(){
		return false;
	}
	
	/**
	 * Return the place name
	 * @return The place name
	 */
	@Override
	public String getPlaceName(){
		return name;
	}
	
	/**
<<<<<<< HEAD
	 * Return an string copy of the description of this place
=======
	 * Return the place description
>>>>>>> june2013
	 * @return description
	 */
	@Override
	public String getDescp(){
		return description;
	}
	
	/**
	 * Return the items contained in this place
	 * @return String with the corresponding place message and a list of the items id contained in this place
	 */
	@Override
	public String getPlaceItemsDescp() {
		return this.items.isEmpty()?
				PLACE_EMPTY_MSG
				:PLACE_WITH_ITEMS_MSG+this.items.toString();
	}

	/**
	 * @return the protection
	 */
	public double getProtection() {
		return protection;
	}

	/**
	 * @param protection the protection to set
	 */
	public void setProtection(double protection) {
		this.protection = protection;
	}
	
}


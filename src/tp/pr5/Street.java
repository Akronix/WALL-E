package tp.pr5;

/**
 * A street links two places A and B. All streets are two-way streets. <br/>
 * If a street is defined as Street(A,NORTH,B) this means that Place B is at NORTH of Place A and Place A is at south of Place B
 * @author Abel Serrano Juste
 */

public class Street implements Cloneable{
	
	private Place targetPlace;
	private Place sourcePlace;
	private Direction direction;
	private boolean opened;
	private String code;
	
	
	/**
	 * Creates an open street and it have not any code to open or close it
	 * @param source Source place
	 * @param direction Represents how is placed the target place with respect to the source place.
	 * @param target Target place
	 */
	public Street(Place source, Direction direction, Place target){
		this.sourcePlace=source;
		this.direction=direction;
		this.targetPlace=target;
		this.opened=true;
		this.code="";
	}

	
	/**
	 * Street Constructor with all the arguments
	 * @param sP Source place (in the example A).
	 * @param d Direction. Represents how is placed the target place with respect to the source place.
	 * @param tP Target place (in the example B).
	 * @param isOpen Determines is the street is opened or closed
	 * @param code  The code that opens and closes the street
	 */
	public Street (Place sP,Direction d, Place tP, boolean isOpen, String code) {
		this(sP,d,tP);
		this.opened=isOpen;
		this.code=code;
	}
	
	
	/**
	 * Checks if the street comes out from a place in a given direction.<br/>
	 * Remember that streets are two-way
	 * @param place The place to check
	 * @param where which direction used.
	 * @return Returns <code>true</code> if the street comes out from the input Place (as sourcePlace either targetPlace)
	 */
	public boolean comeOutFrom(Place place,	Direction where){
		
		return place.equals(sourcePlace) && (where.equals(direction)) ||
			place.equals(targetPlace) && (direction.opposite().equals(where));
		
	}
			
	/**
	 * Returns the place of the other side of the street from the place whereAmI.
	 * @return <code>null</code> if <code>whereAmI</code> does not belong to the street.
	 * @param whereAmI The place where I am.
	 */
	public Place nextPlace(Place whereAmI){
		Place auxPlace;
		if (whereAmI.equals(sourcePlace)) 
			auxPlace = targetPlace;
		
		else if (whereAmI.equals(targetPlace)) 
			auxPlace = sourcePlace;
			
		else
			auxPlace = null;
		
		return auxPlace;
	}
	
	/**
	 * Checks if the street is open or closed
	 * @return <code>true</code>, if the street is open, and <code>false</code> when the street is closed
	 */
	public boolean isOpen(){
		return this.opened;
	}
	
	/**
	 * Try to open a street using a code card. Codes must match in order to complete this action<br/>
	 * It only works when the street is closed
	 * @param card A code card to open the street
	 * @return <code>true</code> if the street has been opened with the codecard.<br/>
	 * Note that if the street isn't closed or it isn't the incorrect code, this return <code>false</code>
	 */
	public boolean open(String key){
	//clave OR clavecarta=1111. clave carta tiene que ser XOR de clave o "1111"
		
		if (this.code.equals(key))
			this.opened=true;
		
		return this.opened;
	}
	
	/**
	 * Try to close a street using a code card. Codes must match in order to complete this action.<br/>
	 * It only works when the street is open
	 * @param card A code card to close the street
	 * @return true if the action has been completed, <code>false</code> otherwise
	 */
	public boolean close(String key){
	//clave OR clavecarta=1111. clave carta tiene que ser XOR de clave o "1111"
		boolean completed;
		if (this.code.equals(key)){ 
			this.opened=false;
			completed = true;
		}
		else
			completed = false;
		
		return completed;
	}
	
	//////////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/**
	 * Get direction of this street respect place given
	 * @param p place respect we want to know the direction (It depends if it's the source place or the target place of the street)
	 * @return Direction. UNKOWN if the place is not the source place neither target place.
	 */
	public Direction getDirection (Place p){
		if (p.equals(this.sourcePlace))
			return this.direction;
		else if (p.equals(targetPlace))
			return this.direction.opposite();
		
		//Only for avoiding runtime errors. Not necessary:
		else 
			return Direction.UNKNOWN;
	}
	
	/**
	 * Get source place for this street
	 * @return source place
	 */
	public Place getSourcePlace(){
		return this.sourcePlace;
	}
	
	/**
	 * Get target place for this street
	 * @return target place
	 */
	public Place getTargetPlace(){
		return this.targetPlace;
	}
	
	//////////////////////// Override methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	@Override
	public String toString(){
		return "street join "+this.targetPlace.getPlaceName()+" in "+this.direction+" to "+this.sourcePlace.getPlaceName();
	}
	
	@Override
	/**
	 * Make deep copy of this street
	 * @return an street copy of this street
	 */
	public Street clone(){
		return new Street (this.sourcePlace, this.direction, this.targetPlace);
	}
}

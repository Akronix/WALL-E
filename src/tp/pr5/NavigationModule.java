package tp.pr5;

//java packages:
import java.util.Vector;

//local packages:
import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;
import tp.pr5.view.observersInterfaces.NavigationObserver;

public class NavigationModule {

	private Place currentPlace;
	private Direction currentHeading;
	private City cityMap;
	
	//Vista components <
	private Vector<NavigationObserver> navigatorObservers;
	//>
	
	//messages<
	public static final String CLOSED_STREET_MESSAGE = "WALL·E says: Arrggg, there is a street but it is closed!";
	public static final String UNKNOWN_STREET_MESSAGE = "WALL·E says: There is no street in direction ";
	//>
	
	//Others
	public static final Direction INITIAL_DIRECTION = Direction.NORTH;
	/**
	 * Navigation module constructor.
	 * @param aCity the city for this game.
	 */
	public NavigationModule(City aCity){
		cityMap=aCity;
		navigatorObservers = new Vector<NavigationObserver>(1);
	}
	
	/**
	 * Checks if the robot has arrived at a spaceship
	 * @return <code>true</code> if an only if the current place is the spaceship
	 */
	public boolean atSpaceship(){
		return currentPlace.isSpaceship();
	}
	
	/**
	 * Drop an item in the current place.
	 * @param it The name of the item to be dropped.
	 * @return true if drop the item was successful
	 */
	public boolean dropItemAtCurrentPlace(Item it){
		boolean successfullyAdded = currentPlace.addItem(it);
		if (successfullyAdded)
			notifyPlaceItemsHasChanged();
		return successfullyAdded;
	}
	
	/**
	 * Checks if there is an item with a given id in this place
	 * @param id Identifier of the item we are looking for
	 * @return <code>true</code> if and only if an item with this id is in the current place
	 */
	public boolean findItemAtCurrentPlace(String id){
		
		return currentPlace.existItem(id);
	}
	
	/**
	 * Returns the robot heading
	 * @return The direction where the robot is facing to.
	 */
	public Direction getCurrentHeading(){
		return this.currentHeading;
	}
	
	/**
	 * Returns the current place where the robot is (for testing purposes)
	 * @return The current place
	 */
	public Place getCurrentPlace(){
		return this.currentPlace;
	}
	
	/**
	 * Returns the street opposite the robot
	 * @return The street which the robot is facing, or null, if there is not any street in this direction
	 */
	public Street getHeadingStreet(){
		return cityMap.lookForStreet(currentPlace, currentHeading);
		//return connectionsToStreets.get(currentHeading);
	}
	
	/**
	 * Initializes current heading and initial place with default values.<br/>
	 * Also updates views corresponding.<br/>
	 * Views in NavigationObserver shape must have been set to this before.
	 */
	public void initNavigator(){
		this.currentPlace = cityMap.getInitialPlace();
		this.currentHeading = INITIAL_DIRECTION;
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers) {
			Nobs.resetMap();
			Nobs.initNavigationModule(currentPlace, currentHeading);
		}
	}
	
	/**
	 * The method tries to move the robot following the current direction.<br/> 
	 * If the movement is not possible because there is no street, or there is a street which is closed, then it throws an exception. <br/>
	 * Otherwise the current place is updated according to the movement
	 * @throws InstructionExecutionException When the street is closed or there isn't one street in this direction
	 */
	public void move() throws InstructionExecutionException{
		
		/*
		 * First, It has to search if exist street from current place in currentHeading.
		 */
		Street auxStreet = this.getHeadingStreet();
		
		/*
		 * If It exists & it's open, then we can perform the movement.
		 */
		if (auxStreet!=null){
			if (auxStreet.isOpen()){
				this.currentPlace = auxStreet.nextPlace(this.currentPlace);
				//updateConnections();

				//updating views:
				for (NavigationObserver Nobs : navigatorObservers) {
					Nobs.updateMovingToPlace(currentPlace, currentHeading);
					Nobs.placeScanned(currentPlace);
				}
			}
			
			//it's possible street is closed, in this case it throws:
			else
				throw new InstructionExecutionException (CLOSED_STREET_MESSAGE);

		}
		
		//Otherwise, throw there isn't street
		else 
			throw new InstructionExecutionException (UNKNOWN_STREET_MESSAGE+this.currentHeading);
	}
	
	/**
	 * The method go back the robot, i. e. in the opposite direction at the source place.<br/> 
	 * Otherwise the current place is updated in the GUI as no visited <br/>
	 * We assume that if robot could move forward, it can go back without throw any exception.
	 */
	public void goInReverse(){
		this.currentHeading = this.currentHeading.opposite(); //We've to take the street in the opposite direction (go back)
		
		Street auxStreet = this.getHeadingStreet();
		
		this.currentPlace = auxStreet.nextPlace(this.currentPlace);
		//updateConnections();
		
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers)
			Nobs.updateGoingBack(currentHeading);
			
		this.currentHeading = this.currentHeading.opposite();

	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the current place.
	 * @param id The identifier of the item
	 * @return The item of identifier <code>id</code> if it exists in the place. Otherwise the method returns <code>null</code>
	 */
	public Item pickItemFromCurrentPlace(String id){
		Item itemPicked = this.currentPlace.pickItem(id);
		if (itemPicked != null)
			notifyPlaceItemsHasChanged();
		return itemPicked;
	}
	
	/**
	 * Updates the current direction of the robot according to the rotation
	 * @param rotation left or right
	 */
	public void rotate(Rotation rotation){
		if (rotation == Rotation.RIGHT)
			currentHeading = currentHeading.right();
		else if (rotation == Rotation.LEFT)
			currentHeading = currentHeading.left();
		
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers)
			Nobs.updateDirection(this.currentHeading);
	}
	
	/**
	 * Prints the information (description + inventory) of the current place
	 */
	public void scanCurrentPlace(){
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers)
			Nobs.placeScanned(this.currentPlace);
	}
	
	//// GUI \\\\
	
	/**
	 * Register a NavigationObserver to the model
	 * @param navigatorObserver 
	 */
	public void addNavigationObserver(NavigationObserver navigatorObserver) {
		if (!this.navigatorObservers.contains(navigatorObserver))
			navigatorObservers.add(navigatorObserver);
	}
	
	//// Notifications \\\\
	
	/**
	 * Notify to the navigation observers that the items of the current place has changed<br/>
	 * Typically this occurs when one item is picked or dropped
	 */
	private void notifyPlaceItemsHasChanged() {
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers)
			Nobs.placeHasChanged(currentPlace);
	}

	
	// Only for tests requirements:
	
	/**
	 * Navigation module constructor.
	 * @param aCity
	 * @param initialPlace
	 */
	public NavigationModule(City aCity, Place initialPlace){
		this(aCity);
		currentPlace=initialPlace;
	}
	
	/**
	 * Initializes the current heading according to the parameter
	 * @param heading New direction for the robot
	 */
	public void initHeading(Direction heading){
		this.currentHeading=heading;
		
		//updating views:
		for (NavigationObserver Nobs : navigatorObservers)
			Nobs.updateDirection(this.currentHeading);
	}

	public Shop getShop() {
		return this.cityMap.getShop();
	}

}

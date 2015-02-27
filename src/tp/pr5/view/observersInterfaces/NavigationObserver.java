package tp.pr5.view.observersInterfaces;

//java packages:

//local packages:
import tp.pr5.Direction;
import tp.pr5.PlaceInfo;

/**
 * Interface of the observers that want to be notified about the events related to the navigation module.<br/>
 * Classes that implement this interface will be informed when the robot changes its heading, when it arrives at a place, 
 * when the place is modified (because the robot picked or dropped an item) or when the user requests to use the radar.<br/>
 * It's not obligatory write code for each update. Only those that changes something of the observer representation.
 * @author Abel Serrano Juste
 */
public interface NavigationObserver extends ObserverInterface{
	
	/**
	 * Notifies that the navigation module is being initialized
	 * @param initialPlace initial place of the robot
	 * @param heading initial direction of the robot
	 */
	public abstract void initNavigationModule(PlaceInfo initialPlace, Direction heading);
	
	/**
	 * Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
	 * @param placeDescription
	 */
	public abstract void placeHasChanged(PlaceInfo placeDescription); // Difference with placeScanned?? only classes where is called???
	
	/*
	 * Notifies that the user requested a RADAR instruction
	 */
	public abstract void placeScanned(PlaceInfo placeDescription);

	/**
	 * Notifies that the robot has arrived at a place
	 * @param place The place where the robot arrives
	 * @param heading The robot movement direction
	 */
	public abstract void updateMovingToPlace(PlaceInfo place, Direction heading);
	
	/**
	 * Notifies that the robot has gone back in the opposite of currentHeading.
	 * @param oppositeOfCurrentHeading The direction which the robot makes the move to go back. It must be the opposite of the current heading of the robot.
	 */
	public abstract void updateGoingBack(Direction oppositeOfCurrentHeading);
	
	/**
	 * Notifies that the robot heading has changed
	 * @param newHeading New robot heading
	 */
	public abstract void updateDirection(Direction newHeading);

	/**
	 * Clear the previous map representation and put it blank<br/>
	 * It's required in order to create a new game.
	 */
	public abstract void resetMap();
	
}

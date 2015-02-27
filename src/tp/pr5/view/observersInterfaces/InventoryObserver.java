package tp.pr5.view.observersInterfaces;

//java packages:

//local packages:


/**
 * Interface of the observers that want to be notified about the events occurred in the robot inventory.<br/>
 * The container will notify its observer every change in the container (when the robot picks or drops items) and when an item is removed from the container.<br/>
 * The container will also notify when the user requests to scan an item or the whole container<br/>
 * It's not obligatory write code for each update. Only those that changes something of the observer representation.
 * @author Abel Serrano Juste
 */
public interface InventoryObserver extends ObserverInterface{
	
	/**
	 * Notifies that the item container has changed
     * @param itemsIdDescp String matrix with the id and description of each item of the inventory robot
	 */
	public abstract void updateInventory(String[][] itemsIdDescp);
	
}

package tp.pr5.items;

//Java packages:
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.Vector;
//Local packages:
import tp.pr5.Interpreter;
import tp.pr5.view.observersInterfaces.InventoryObserver;

/**
 * A container of items. It can be employed by any class that stores items. A container cannot store two items with the same identifier.<br/>
 *
 * It provides methods to add new items, access them and remove them from the container.<br/>
 * 
 * This collection is designed to ignore case of the input.
 *
 *@author Abel Serano Juste
 */
 
public class ItemContainer implements Iterator<Item>{

	private List<Item> items;
	private Vector<InventoryObserver> observers;
	private Iterator<Item> iterator;
	
	private static final boolean debug = false;
	
	/**
	 * Creates the empty container
	 */
	public ItemContainer(){
		this.items = new ArrayList <Item>();
		this.observers = new Vector<InventoryObserver>(1);
	}
	
	/**
	 * Returns the number of items contained
	 * @return the number of items in the container
	 */
	public int numberOfItems(){
		return this.items.size();
	}
	
	/**
	 * Add an item to the container. The operation can fail, returning <code>false</code>
	 * @param item The name of the item to be added.
	 * @return <code>true</code> if and only if the item is added, i.e., an item with the same identifier does not exists in the container
	 */
	public boolean addItem(Item item){
		int index = Collections.binarySearch(this.items,item);
		boolean isIn = index>=0;
		
		if (!isIn){ //Si la bS devuelve <0 es que el itemContainer no contiene al elemento y debemos insertarlo en -(index+1)
			items.add(-(index+1),item);
			//updating possible observers:
			notifyObserversItemContainerhasChanged();
		}
		//else (index>=0) Si la bS devuelve >=0 es que el elemento ya está en el contenedor.
		
		return !isIn;
			
	}
	

	/**
	 * Returns the item from the container according to the item name
	 * @param id Item name
	 * @return Item with that name or <code>null</code> if the container does not store an item with that name.
	 */
	public Item getItem(String id){
		int index = Collections.binarySearch(this.items,new PlaceableItem(id));
		boolean isIn = index>=0;
		Item outputItem;
		if (isIn)
		   outputItem = items.get(index);
		
		else
		   outputItem = null;
		
		return outputItem;
	}
	
	/**
	 * Returns and deletes an item from the inventory. This operation can fail, returning <code>null</code>
	 * @param id Name of the item
	 * @return An item if and only if the item identified by id exists in the inventory. Otherwise it returns <code>null</code>
	 */
	public Item pickItem(String id){
		int index = Collections.binarySearch(this.items,new PlaceableItem(id));
		Item auxItem;
		if (index>=0){
			auxItem = items.get(index);
		    items.remove(index);
		    //updating possible observers:
		    notifyObserversItemContainerhasChanged();
		}
		else
			auxItem = null;
		
		return auxItem;
	}
	

	/**
	 * Generates a String with information about the items contained in the container. <br/>
	 * @return String with the name of each item sorted by item name
	 */
	public String toString(){
		String s ="";
		Item auxItem;
		Iterator<Item> it = this.items.iterator();
		while (it.hasNext()){
			auxItem = (Item)it.next();
			s+="   "+auxItem.getId()+Interpreter.LINE_SEPARATOR;
		}
		return s;
	}
	
	/**
	 * Notice if this ED is empty or not.
	 * @return <code>true</code> if the container no have items, <code>false</code> it is has one item at least.
	 */
	public boolean isEmpty(){
		return this.items.isEmpty();
	}
	
	/**
	 * Checks if the Item with this id exists in the container.
	 * @param itemId Name of the item.
	 * @return <code>true</code> if the container as an item with that name.
	 */
	public boolean containsItem (String itemId){
		return Collections.binarySearch(this.items,new PlaceableItem(itemId)) >= 0;
	}
	
	/**
	 * Use an item, removing the item from the inventory if the item can not be used any more.<br/>
	 * This method don't call to item.use() method.
	 * @param item to be used
	 */
	public void	useItem(Item item){
		if (!item.canBeUsed()){
			pickItem(item.getId());
			//updating possible observers:
			notifyObserversItemContainerhasChanged();
		}
	}
	
	/**
	 * Make a string matrix with two columns, one for the item name and other for the description.<br/>
	 * Each item of this container is one row.
	 * @return string matrix [n][2], with n number of items
	 */
	private String[][] toIdDescpMatrix() {
		String [][] matrix = new String[this.items.size()][2];
		Iterator<Item> it = this.items.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Item auxItem = it.next();
			matrix[i][0] = auxItem.getId();
			matrix[i][1] = auxItem.getDescp();
			if (debug) System.out.println(auxItem);
		}
		return matrix;
	}
	
	/**
	 * Notifying possible observers:
	 */
	private void notifyObserversItemContainerhasChanged() {
		if (!observers.isEmpty()) {
			String[][] IdDescpItemsToUpdate = this.toIdDescpMatrix();
			for (InventoryObserver obs : observers) 
				obs.updateInventory(IdDescpItemsToUpdate);
		}
	}
	
	/**
	 * Register one observer to this item container
	 * @param itemContainerObserver the itemContainerObserver to be added.
	 */
	public void registerItemContainerObserver (InventoryObserver itemContainerObserver) {
		if (!this.observers.contains(itemContainerObserver))
			this.observers.add(itemContainerObserver);
	}
	
	//Extras: New game
	
	/**
	 * It removes all the items of the item container and warns to all its observers that this item container has changed.
	 */
	public void clear() {
		this.items.clear();
		notifyObserversItemContainerhasChanged();
	}
	
	//Methods for Iterator:

	public Iterator<Item> iterator() {
		return this.items.iterator();
	}

	@Override
	public boolean hasNext() {
		iterator = this.iterator();
		return this.iterator.hasNext();
	}

	@Override
	public Item next() {
		iterator = this.iterator();
		return this.iterator.next();
	}

	@Override
	public void remove() {
		iterator = this.iterator();
		this.iterator.remove();
	}
	
}

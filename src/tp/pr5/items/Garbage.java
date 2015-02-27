package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * The garbage is a type of item that generates recycled material after using it. 
 * The garbage can be used only once. After using it, it must be removed from the robot inventory
 * @author Abel Serrano Juste
 */
public class Garbage extends PlaceableItem{
	
	private int weight;
	private boolean used;
	
	/**
	 * Garbage constructor
	 * @param id Item id
	 * @param description Item description
	 * @param recycledMaterial The amount of recycled material that the item generates
	 */
	public Garbage(String id, String description, int recycledMaterial){
		super(id, description);
		this.weight=recycledMaterial;
	}
	
	/**
	 * Garbage can be used only once
	 * @return <code>true</code> if the item has not been used yet
	 */
	public boolean canBeUsed(){
		return !used;
	}

	/**
	 * The garbage generates recycled material for the robot that uses it
	 * @param r The robot that uses the item
	 * @param nm The navigation module for knowing where WALL·E uses the item.
	 * @return <code>true</code> if the garbage was transformed in recycled material,
	 *  <code>false</code> otherwise, i. e., when this item has already been used.
	 */
	public boolean use(RobotEngine r, NavigationModule nm){
		boolean success;
		if (canBeUsed()){
			r.addRecycledMaterial(this.weight);
			success = this.used=true;
		}
		else
			success = false;
		
		return success;
	}
	
	/**
	 * Undo make recycled material from this garbage
	 * @param r The robot that transform this garbage
	 * @param nm The navigation module for knowing where WALL·E used the item.
	 * @return <code>true</code> if the recycled material was transformed in garbage,
	 *  <code>false</code> otherwise, i. e., when this item hasn't been used yet.
	 */
	public boolean revert(RobotEngine r, NavigationModule nm){
		boolean revertable = used;
		if (revertable) {
			r.addRecycledMaterial(-this.weight);
			this.used=false;
		}
		
		return revertable;
	}
	
	@Override
	/**
	 * String representation of this CodeCard Object
	 * @return The item name + the item description + the recycled material value
	 */
	public String toString(){
		return (super.toString()+"// recycled material ="+this.weight);
	}


	@Override
	public Item clone() {
		return new Garbage(this.id,this.descp,this.weight);
	}
	
}

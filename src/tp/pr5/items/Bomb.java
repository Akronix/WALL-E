/**
 * Created on Jun 20, 2013
 */
package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * @author Abel Serrano Juste
 */
public class Bomb extends PlaceableItem implements Cloneable{

	private int damage;
	private int points;
	private boolean activated;
	
	/**
	 * Garbage constructor
	 * @param id Item id
	 * @param description Item description
	 * @param minimalPoints 
	 * @param damage 
	 */
	public Bomb(String id, String description, int minimalPoints, int damage){
		super(id, description);
		this.damage = damage;
		this.points = minimalPoints;
		this.activated = true;
	}
	
	/**
	 * Garbage constructor
	 * @param id Item id
	 * @param description Item description
	 * @param minimalPoints 
	 * @param damage 
	 * @param activated 
	 */
	public Bomb(String id, String description, int minimalPoints, int damage, boolean activated){
		this(id, description, minimalPoints, damage);
		this.activated = activated;
	}
	
	/**
	 * Garbage can be used only once
	 * @return <code>true</code> if the item has not been used yet
	 */
	public boolean canBeUsed(){
		return true;
	}

	/**
	 * This do nothing.
	 * @param r The robot that uses the item
	 * @param nm The navigation module for knowing where WALL·E uses the item.
	 * @return <code>true</code> if the garbage was transformed in recycled material,
	 *  <code>false</code> otherwise, i. e., when this item has already been used.
	 */
	public boolean use(RobotEngine r, NavigationModule nm){
		return true;
	}
	
	/**
	 * Undo make recycled material from this garbage
	 * @param r The robot that transform this garbage
	 * @param nm The navigation module for knowing where WALL·E used the item.
	 * @return <code>true</code> if the recycled material was transformed in garbage,
	 *  <code>false</code> otherwise, i. e., when this item hasn't been used yet.
	 */
	public boolean revert(RobotEngine r, NavigationModule nm){
		return true;
	}
	
	@Override
	/**
	 * String representation of this CodeCard Object
	 * @return The item name + the item description + the recycled material value
	 */
	public String toString(){
		return (super.toString()+"// damage ="+this.damage);
	}


	@Override
	public Item clone() {
		return new Bomb(this.id,this.descp,this.points,this.damage,this.activated);
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

}

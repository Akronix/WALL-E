package tp.pr5.items;

//local packages:
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * An item that represents fuel. This item can be used at least once and it provides power energy to the robot. 
 * When the item is used the configured number of times, then it must be removed from the robot inventory
 * @author Abel Serrano Juste
 */
public class Fuel extends PlaceableItem{
	
	private int power;
	private int times;
	
	/**
	 * Fuel constructor
	 * @param id Item id
	 * @param description Item description
	 * @param power The amount of power energy that this item provides the robot
	 * @param times Number of times the robot can use the item
	 */
	public Fuel(String id, String description, int power, int times){
		super(id,description);
		this.power=power;
		this.times=times;
	}
	
	/**
	 * Fuel can be used as many times as it was configured
	 * @return <code>true</code> it the item still can be used
	 */
	public boolean canBeUsed(){
		return (this.times>0);
	}
	
	/**
	 * Using the fuel provides energy to the robot (if it can be used)
	 * @param r The robot that is going to use the fuel
	 * @return <code>true</code> if the fuel has been used
	 */
	public boolean use(RobotEngine r, NavigationModule nm){
		boolean usable = canBeUsed();
		if (usable){
			times--;
			r.addFuel(power);
		}
		//If not is usable it do anything and return false.
		
		return usable; 
	}
	
	/**
	 * Undo the previous provision of fuel to the robot
	 * @param r The robot that used the fuel
	 * @return <code>true</code> if the effect of the fuel has been correctly reverted
	 */
	public boolean revert(RobotEngine r, NavigationModule nm){
		r.addFuel(-power);
		times++;
		return true;
	}
	
	@Override
	/**
	 * String representation of this Fuel Object
	 * @return The item name + the power value + the times value
	 */
	public String toString(){
		return (super.toString()+"// power = "+this.power+", times = "+this.times);
	}
		
	@Override
	/**
	 * Returns a deep copy of this object
	 */
	public Item clone() {
		return new Fuel(this.id,this.descp,this.power,this.times);
	}
	
}

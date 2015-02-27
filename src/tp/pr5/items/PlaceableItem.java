package tp.pr5.items;

//local packages:
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * Class father of all the items which can be located and taken from one place.<br/>
 * It inherits from Item and add a no args contructor. Also it implements clone() and equals() <br/>
 * It's not the purpose of this class create objects which interacts on the game, instead of it will be done with inherited classes of this.
 *  *@author Abel Serano Juste
 */
public class PlaceableItem extends Item implements Comparable<Item>, Cloneable{

	public PlaceableItem(String id){
		super(id,"");
	}
	
	public PlaceableItem(String id, String description) {
		super(id, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean use(RobotEngine r, NavigationModule nm) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Item clone(){
		return new PlaceableItem(this.getId(),this.getDescp());
		}

}

/**
 * Created on May 15, 2013
 */
package tp.pr5.view.console;

//local packages
import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngine;
import tp.pr5.view.observersInterfaces.InventoryObserver;
import tp.pr5.view.observersInterfaces.NavigationObserver;
import tp.pr5.view.observersInterfaces.RobotEngineObserver;

/**
 * The view that displays the application on console. <br/>
 * It implements all the observer interfaces in order to be notified about every event that occur when the robot is running.
 * @author Abel Serrano Juste
 */
public class ConsoleView implements RobotEngineObserver,NavigationObserver,InventoryObserver{

	@Override
	public void communicationCompleted() {
		System.out.print(RobotEngine.QUIT_MESSAGE);
	}

	@Override
	public void communicationHelp(String helpMessage) {
		System.out.print(helpMessage);
	}

	@Override
	public void engineOff(String msg) {
		//Farewell messages:
		System.out.print(msg);
	}

	@Override
	public void raiseError(String msg) {
		System.out.print(msg);
	}

	@Override
	public void robotSays(String message) {
		System.out.print(message);
	}

	@Override
	public void updateFuelRecycledMaterial(int fuel, int rm, double shield) {
		System.out.print("      * My power is "+fuel+Interpreter.LINE_SEPARATOR);
		System.out.print("      * My recycled material is "+rm+Interpreter.LINE_SEPARATOR);
		System.out.print("      * My shield is "+shield+Interpreter.LINE_SEPARATOR);
		System.out.print(Interpreter.LINE_SEPARATOR);
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		System.out.print(initialPlace.toString());
		System.out.print("WALL·E is looking at direction "+heading.toString()+Interpreter.LINE_SEPARATOR);
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		//Nothing at the moment
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		System.out.print(placeDescription.toString());
	}

	@Override
	public void updateMovingToPlace(PlaceInfo place, Direction heading) {
		System.out.print("WALL·E says: Moving in direction "+heading+Interpreter.LINE_SEPARATOR);
	}

	@Override
	public void updateGoingBack(Direction oppositeOfCurrentHeading) {
		//Nothing at the moment
	}

	@Override
	public void updateDirection(Direction newHeading) {
		System.out.print("WALL·E is looking at direction "+newHeading+Interpreter.LINE_SEPARATOR);
	}

	@Override
	public void updateInventory(String[][] inventory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetMap() {
		// TODO Auto-generated method stub
		
	}

}

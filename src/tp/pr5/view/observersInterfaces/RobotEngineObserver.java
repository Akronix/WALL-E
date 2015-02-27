/**
 * Created on May 7, 2013
 */
package tp.pr5.view.observersInterfaces;

//local packages:

/**
 * Interface which must implement all the classes "Observer", i.e., all the classes which depend of changes in the robot engine.<br/>
 * The robot engine will notify the changes in the robot (fuel and recycled material), will inform about communication problems, 
 * errors and when the robot wants to say something.<br/>
 * Finally, the engine will also notify when the user requests help and when the robot shuts down (because the robot run out of fuel or when it arrived at the spaceship)
 * It's not obligatory write code for each update. Only those that changes something of the observer representation.
 * @author Abel Serrano Juste
 */
public interface RobotEngineObserver extends ObserverInterface{
	
	/**
	 * The robot engine informs that the communication is over.
	 */
	public abstract void communicationCompleted();
	
	/**
	 * The robot engine informs that the help has been requested
	 * @param help A string with information help
	 */
	public abstract void communicationHelp(String help);
	
	/**
	 * The robot engine informs that the robot has shut down (because it has arrived at the spaceship or it has run out of fuel)
	 * @param message true if the robot shuts down because it has arrived at the spaceship or false if it has run out of fuel
	 */
	public abstract void engineOff(String message);

	
	/**
	 * The robot engine informs that it has raised an error
	 * @param msg Error message
	 */
	public abstract void raiseError(String msg);
	
	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message The robot message
	 */
	public abstract void robotSays (String message);
	
	/**
	 * Method to update the values of fuel and recycled material of the robot on the view
	 * @param fuel new fuel of the robot
	 * @param rm new amount of recycled material of the robot
	 * @param shield TODO
	 */
    public abstract void updateFuelRecycledMaterial(int fuel, int rm, double shield);
	
}

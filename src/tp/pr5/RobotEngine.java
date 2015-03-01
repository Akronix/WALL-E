package tp.pr5;

//Java packages:
import java.util.Vector;

//local packages:
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.ItemContainer;
import tp.pr5.view.observersInterfaces.InventoryObserver;
import tp.pr5.view.observersInterfaces.NavigationObserver;
import tp.pr5.view.observersInterfaces.RobotEngineObserver;

/** This class represents the robot engine. It controls robot movements by processing 
 * the instructions introduced with the keyboard. <br/>
 * The engine stops when the robot arrives at the spaceship or receives a quit instruction.
 * @author Abel Serrano Juste
 */
public class RobotEngine {
	
	//Fields of the robot:
	private NavigationModule navigator;
	@SuppressWarnings("unused")
	private City city; //It's used for calling to the NavigationModule constructor
	private ItemContainer inventory;
	private int fuel;
	private int rm;
	private double shield;
	
	//View references
	private Vector<RobotEngineObserver> engineObservers;
	
	//Messages
	public static final String RUN_OUT_FUEL_MESSAGE = "WALL·E says: I run out of fuel. I cannot move. Shutting down..."+Interpreter.LINE_SEPARATOR;
	public static final String SHIELD_RUN_OUT_MESSAGE = "WALL·E says: My shield has been destroyed. Shutting down";
	public static final String REACHED_SPACESHIP_MESSAGE = "WALL·E says: I am at my spaceship. Bye bye"+ Interpreter.LINE_SEPARATOR;
	public static final String QUIT_MESSAGE = "WALL·E says: I have communication problems. Bye bye"+Interpreter.LINE_SEPARATOR;
	
	//Others
	private Instruction previousInstruction; //Store the last instruction executes to can undo it.
	private boolean updatedInstruction; /*Avoid to store more than one instruction in each prosecution of one instruction (communicateRobot calls)
										It's necessary to not push undo after make and undo of an undo and get into infinite loop*/
	
	
	/**
	 * Creates the robot engine in an initial place, facing an initial direction and with a city map. Initially the robot has not any items or recycled material but it has an initial amount of fuel (50).
	 * @param initialPlace The city where the robot wanders
	 * @param dir The initial direction where the robot is facing.
	 * @param cityMap The city where the robot wanders
	 */
	public RobotEngine(City cityMap, Place initialPlace, Direction dir){
		this.inventory= new ItemContainer();
		this.navigator= new NavigationModule(cityMap);
		this.city = cityMap;
		
		this.engineObservers = new Vector<RobotEngineObserver>(1);

	}
	
	
	/////Start engine\\\\\
	
	/**
	 * Requests the engine to inform the observers that the simulation starts.<br/>
	 * The simulation finishes when the robot arrives at the space ship, the user types "QUIT", or the robot runs out of fuel<br/>
	 * It has be called by startControllerIt and only after controller is created and all the observers have been registered.
	 */
	public void requestStart() {
		//Initialize variables
		this.rm = 0;
		this.fuel = 100;
		this.shield = 100;

		this.previousInstruction = null;
		this.updatedInstruction = false;
		this.navigator.initNavigator();
		this.inventory.clear();
		
		//updating views:
		for (RobotEngineObserver REobs : engineObservers)
			REobs.updateFuelRecycledMaterial(fuel, rm, shield);

	}
	
	///// Interaction with others parts of the app\\\\
	
	/**
	 * Prints the information about all possible instructions
	 */
	public void requestHelp(){
		for (RobotEngineObserver obs : engineObservers)
			obs.communicationHelp(Interpreter.HELP_SYNTAX_MESSAGE+Interpreter.LINE_SEPARATOR);
			//obs.communicationHelp(Interpreter.interpreterHelp()); Other possible message usage instructions help with the how-to-use of each instruction
	}
	
	/**
	 * Requests the end of the simulation
	 */
	public void requestQuit(){
		//updating views:
		for (RobotEngineObserver obs : engineObservers)
			obs.communicationCompleted();
		
		System.exit(0);
	}
	
	/**
	 * It executes an instruction. The instruction must be configured with the context before executing it. 
	 * It controls the end of the simulation. 
	 * If the execution of the instruction throws an exception, then the corresponding message is printed
	 * @param c The instruction to be executed
	 */
	public void communicateRobot(Instruction c) {
		try{
			updatedInstruction = false;
			c.configureContext(this,navigator,inventory);
			c.execute();
			if (!updatedInstruction) {
				previousInstruction = c;
				updatedInstruction = true;
			}
			
		}
		catch (InstructionExecutionException iee){
			for(RobotEngineObserver obs : engineObservers) {
				obs.raiseError(iee.getMessage()+Interpreter.LINE_SEPARATOR);
			}
				
		}
	}
	
	/**
	 * Checks if the game is over, i. e. the robot is run of fuel or it's at spaceship.
	 * @return <code>true</code> if the game has finished
	 */
	public boolean isOver() {
		boolean fin = false;
		
		if (fuel==0) {
			for (RobotEngineObserver obs : engineObservers) 
				obs.engineOff(RobotEngine.RUN_OUT_FUEL_MESSAGE);
			fin = true;
		}
		else if (this.navigator.atSpaceship()) {
			for (RobotEngineObserver obs : engineObservers) 
				obs.engineOff(RobotEngine.REACHED_SPACESHIP_MESSAGE
						+"Total Score: "+this.rm+Interpreter.LINE_SEPARATOR);
			fin = true;
		}
		else if (this.shield ==0) {
			for (RobotEngineObserver obs : engineObservers) 
				obs.engineOff(RobotEngine.SHIELD_RUN_OUT_MESSAGE);
			fin = true;
		}
		
		return (fin);
	}
	
	/**
	 * Request the engine to inform of something to the view components corresponding.
	 * @param message The message to say
	 */
	public void saySomething(String message) {
		for (RobotEngineObserver obs : engineObservers)
			obs.robotSays(message);
	}


		///// Getters & Setters \\\\
	
	/**
	 * Adds an amount of fuel to the robot (it can be negative)
	 * @param fuelPower Amount of fuel added to the robot
	 */
	public void addFuel(int fuelPower){
		this.fuel+=fuelPower;
		if (this.fuel<0) 
			this.fuel=0;
		
		//updating views:
		notifyFuelRecycledMaterialhasChanged();
	}
	
	/**
	 * Increases the amount of recycled material of the robot
	 * @param weight Amount of recycled material
	 */
	public void addRecycledMaterial(int weight){
		this.rm+=weight;
		
		//updating views:
		notifyFuelRecycledMaterialhasChanged();
	}
	
	/**
	 * Returns the current weight of recycled material that the robot carries. This method is mandatory FOR TESTING PURPOSES
	 * @return The current recycled material of the robot
	 */
	public int getRecycledMaterial(){
		return this.rm;
	}
	
	/**
	 * A getter for fuel value
	 * @return Fuel value which WALL·E has
	 */
	public int getFuel(){
		return this.fuel;
	}
	
	/**
	 * @return the shield value
	 */
	public double getShield() {
		return shield;
	}


	/**
	 * @param shieldToAdd  the shield to set
	 */
	public void addShield(double shieldToAdd) {
		this.shield += shieldToAdd;
		if (this.shield<0) 
			this.shield=0;
		
		//updating views:
		notifyFuelRecycledMaterialhasChanged();
	}


	/**
	 * Take the last correct instruction performed (remove it)  by the robot and return it.
	 * @return The last correct instruction
	 */
	public Instruction getLastInstruction() {
		return previousInstruction;
	}
	
		////Notifications\\\\
	
	/**
	 * Notify to all robot engine observers that the fuel and the recycled material values for WALL·E has changed.
	 */
	private void notifyFuelRecycledMaterialhasChanged() {
		for (RobotEngineObserver Eobs : engineObservers)
			Eobs.updateFuelRecycledMaterial(fuel, rm, shield);
	}
	
		///// GUI Observers \\\\
	
	/**
	 * Registers an EngineObserver to the model
	 * @param observer The observer that wants to be registered
	 */
	public void	addEngineObserver(RobotEngineObserver observer) {
		if (!engineObservers.contains(observer)) {
			this.engineObservers.add(observer);
		}
	}
	
	/**
	 * Registers one ItemContainerObserver to the model
	 * @param observer The observer that wants to be registered
	 */
	public void addInventoryObserver(InventoryObserver observer) {
		this.inventory.registerItemContainerObserver(observer);
	}

	/**
	 * Registers one NavigationObserver to the model
	 * @param observer The observer that wants to be registered
	 */
	public void addNavigationObserver(NavigationObserver observer) {
		this.navigator.addNavigationObserver(observer);
		
	}
	
}

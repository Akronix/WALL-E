/**
 * Created on 13 May 2013
 */
package tp.pr5.controller;

//java packages:
import java.util.Vector;

//local packages:
import tp.pr5.view.observersInterfaces.InventoryObserver;
import tp.pr5.view.observersInterfaces.NavigationObserver;
import tp.pr5.view.observersInterfaces.RobotEngineObserver;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * Controller is the front-end to work with the controller of the app.<br/>
 * Through this we can abstract us from the concrete implementation controller.
 * @author Abel Serrano Juste
 */
public abstract class Controller {
	
	protected RobotEngine engine;
	
	protected Vector<RobotEngineObserver> engineObservers;
	protected Vector<InventoryObserver> inventoryObservers;
	protected Vector<NavigationObserver> navigatorObservers;
	
	/**
	 * Common memory reservation for controller instances.<br/>
	 * This has to be overridden by their inherited classes in order to reserve memory for particular components of different controllers.
	 * @param model the main class of the model to associate this controller to it.
	 */
	protected Controller (RobotEngine model) {
		//Set model
		this.engine = model;
		
		//Create vectors for observers
		this.engineObservers = new Vector<RobotEngineObserver>();
		this.inventoryObservers = new Vector<InventoryObserver>();
		this.navigatorObservers = new Vector<NavigationObserver>();
	}
	
	/**
	 * Associate the model to the controller (RobotEngine), register observers, make configurations and startup the game.<br/>
	 * It must be Override depends on the type of interface chosen by the user.<br/>
	 * It's absolutely essential that this method call to engine.requestStart()
	 */
	public abstract void startController();
	
	/**
	 * For registering robot engine observers to the controller and the robot engine of walle
	 * @param robotEngineObserver observer to register
	 */
	public void registerEngineObserver (RobotEngineObserver robotEngineObserver) {
		if (!engineObservers.contains(robotEngineObserver)) {
			this.engine.addEngineObserver(robotEngineObserver);
			this.engineObservers.add(robotEngineObserver);
		}
	}
	/**
	 * For registering inventory observers to the controller and the inventory of walle
	 * @param itemContainerObserver observer to register
	 */
	 public void registerItemContainerObserver (InventoryObserver itemContainerObserver) {
		 if (!inventoryObservers.contains(itemContainerObserver)) {
			 this.engine.addInventoryObserver(itemContainerObserver);
			 this.inventoryObservers.add(itemContainerObserver);
		 }
	 }
	 
	 /**
	 * For registering navigation observers to the controller and the navigation module of walle
	  * @param navigationObserver observer to register
	  */
		public void registerNavigationObserver(NavigationObserver navigationObserver) {
			if (!navigatorObservers.contains(navigationObserver)) {
				 this.engine.addNavigationObserver(navigationObserver);
				 this.navigatorObservers.add(navigationObserver);
			 }
		}
	
	/**
	 * Process String parameter trying to convert it to one Instruction object and tells to the engine game to perform it.<br/>
	 * It request to the model show an error if the instruction syntax is bad formulated. 
	 * @param input String which claims be an instruction.
	 * @return true if the processing was well.
	 */
	protected boolean processInstruction(String input) {
		boolean ok = true;
		try{
			Instruction ins = Interpreter.generateInstruction(input);
			engine.communicateRobot(ins);
			ok = !engine.isOver();
		}
		catch(WrongInstructionFormatException wife){
			for (RobotEngineObserver REObs : engineObservers)
				REObs.raiseError(wife.getMessage()+Interpreter.LINE_SEPARATOR);
		}
		return ok;
	}

	/**
	 * Request end the application from the view.<br/>
	 * By default it calls to engine.requestQuit(). If one concrete implementation must closed or shut down anything else of the app,
	 * its controller have to override this method (but it can call to it with super)
	 */
	public void requestQuit() {
		engine.requestQuit();
	}

}

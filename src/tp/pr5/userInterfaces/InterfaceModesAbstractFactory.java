/**
 * Created on May 7, 2013
 */
package tp.pr5.userInterfaces;

//local packages:
import tp.pr5.RobotEngine;
/**
 * 	Abstract Factory to give a factory depending on the interface mode which wants the user to use<br/>
 * 	Each Factory has to be able to create the view components, create the appropriate controller and
 * 	 associate the corresponding view elements to that controller.<br/>
 *  In this way, you can change or add new interface modes implementations while extends
 *  this abstract class and the app should works seamlessly.<br/>
 * @author Abel Serrano Juste
 */
public abstract class InterfaceModesAbstractFactory {
	
	/** Reference to the model of the application */
	protected RobotEngine model;
	
	/**
	 * Common constructor for all the factories. Take in a reference to the model.
	 * @param model Reference to the model of the application
	 */
	protected InterfaceModesAbstractFactory(RobotEngine model) {
		this.model = model;
	}

	/**
	 * Returns the appropriate interface mode factory and prepare it to be launched.
	 * @param interfaceMode Interface Mode selected by the user
	 * @param robotEngine model reference
	 * @return Interface Modes Factory to launch the app.
	 */
	public static InterfaceModesAbstractFactory configureInterface(InterfaceModesEnum interfaceMode, RobotEngine robotEngine) {
		InterfaceModesAbstractFactory interfaceFactory = null;
		
		switch(interfaceMode){
		case swing: 
			interfaceFactory = new SimpleGUIFactory(robotEngine);
			break;
			
		case console: 
			interfaceFactory = new ConsoleFactory(robotEngine);
			break;
			
		case both: 
			interfaceFactory = new BothFactory(robotEngine);
			
			break;
			
		case multiple:
			interfaceFactory = new MultipleGUIFactory(robotEngine);
			break;
		}
		return interfaceFactory;
	}
	
	/**
	 * Run the application for this interface.
	 */
	public abstract void runInterface();
	
	/**
	 * create the controller for this app. Also associate the observers to the controller.
	 */
	protected abstract void createController();
	
	/**
	 * create the views for this app.
	 */
	protected abstract void createView();
	
}

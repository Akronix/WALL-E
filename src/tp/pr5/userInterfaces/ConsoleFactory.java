/**
 * Created on May 25, 2013
 */
package tp.pr5.userInterfaces;

import tp.pr5.RobotEngine;
import tp.pr5.controller.ConsoleController;
import tp.pr5.view.console.ConsoleView;

/**
 * @author Abel Serrano Juste
 */
public class ConsoleFactory extends InterfaceModesAbstractFactory {
	
	private ConsoleController appController;
	private ConsoleView consoleView;
	
	public ConsoleFactory(RobotEngine robotEngine) {
		super(robotEngine);
		createView();
		createController();
	}

	/* (non-Javadoc)
	 * @see tp.pr5.userInterfaces.InterfaceModesAbstractFactory#runInterface()
	 */
	@Override
	public void runInterface() {
		//Initialize engine
		model.requestStart();
		//start app
		appController.startController();
	}

	/* (non-Javadoc)
	 * @see tp.pr5.userInterfaces.InterfaceModesAbstractFactory#createController()
	 */
	@Override
	protected void createController() {
		appController = new ConsoleController(model,consoleView);
		
		//Register observers
		this.appController.registerEngineObserver(consoleView);
		this.appController.registerItemContainerObserver(consoleView);
		this.appController.registerNavigationObserver(consoleView);
	}

	/* (non-Javadoc)
	 * @see tp.pr5.userInterfaces.InterfaceModesAbstractFactory#createView()
	 */
	@Override
	protected void createView() {
		
		consoleView = new ConsoleView();
		
	}

}

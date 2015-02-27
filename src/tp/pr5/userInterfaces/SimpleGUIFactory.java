/**
 * Created on May 25, 2013
 */
package tp.pr5.userInterfaces;

import tp.pr5.RobotEngine;
import tp.pr5.controller.GUIController;
import tp.pr5.view.gui.InstructionsPanel;
import tp.pr5.view.gui.MainWindow;
import tp.pr5.view.gui.NavigationPanel;
import tp.pr5.view.gui.RobotPanel;

/**
 * Factory for the interface with swing interface.
 * @author Abel Serrano Juste
 */
public class SimpleGUIFactory extends InterfaceModesAbstractFactory {
	
	private GUIController appController;
	
	//View elements
	private MainWindow window;
	private RobotPanel robotPanel;
	private NavigationPanel navigationPanel;
	private InstructionsPanel instructionsPanel;

	/**
	 * Constructor for SimpleGUIFactory
	 * @param robotEngine reference to the model
	 */
	public SimpleGUIFactory(RobotEngine robotEngine) {
		super(robotEngine);
		createView();
		createController();
	}

	@Override
	public void runInterface() {
		//Initialize engine
		model.requestStart();
		//start app
		appController.startController();
	}

	@Override
	protected void createController() {
		//Create GUI controller
		appController = new GUIController(model, window, navigationPanel, robotPanel, instructionsPanel);
		
		//Set the GUI controller to the view components
		window.setController(appController);
		navigationPanel.setController(appController);
		robotPanel.setController(appController);
		instructionsPanel.setController(appController);
		
		//Register view observers of the model via controller
		appController.registerEngineObserver(window);
		appController.registerEngineObserver(robotPanel);
		appController.registerItemContainerObserver(robotPanel);
		appController.registerNavigationObserver(navigationPanel);
	}

	@Override
	protected void createView() {
		//Create swing components
		navigationPanel = new NavigationPanel();
		robotPanel = new RobotPanel();
		instructionsPanel = new InstructionsPanel();
		window = new MainWindow(navigationPanel,robotPanel,instructionsPanel);
	}

}

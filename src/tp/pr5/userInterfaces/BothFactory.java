/**
 * Created on May 25, 2013
 */
package tp.pr5.userInterfaces;

import tp.pr5.RobotEngine;
import tp.pr5.controller.GUIController;
import tp.pr5.view.console.ConsoleView;
import tp.pr5.view.gui.InstructionsPanel;
import tp.pr5.view.gui.MainWindow;
import tp.pr5.view.gui.NavigationPanel;
import tp.pr5.view.gui.RobotPanel;

/**
 * Factory for the interface which combines the swing view with the output of the console.
 * @author Abel Serrano Juste
 */
public class BothFactory extends InterfaceModesAbstractFactory {
	
	private GUIController appController;
	
	//View elements
	private MainWindow window;
	private RobotPanel robotPanel;
	private NavigationPanel navigationPanel;
	private InstructionsPanel instructionsPanel;

	private ConsoleView consoleView;
	
	/**
	 * Constructor for this factory
	 * @param model reference to the model
	 */
	public BothFactory(RobotEngine model) {
		super(model);
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
		
		//Register swing observers of the model
		appController.registerEngineObserver(window);
		appController.registerEngineObserver(robotPanel);
		appController.registerItemContainerObserver(robotPanel);
		appController.registerNavigationObserver(navigationPanel);
		
		//Register console observer
		this.appController.registerEngineObserver(consoleView);
		this.appController.registerItemContainerObserver(consoleView);
		this.appController.registerNavigationObserver(consoleView);
	}

	@Override
	protected void createView() {
		//Create swing components
		navigationPanel = new NavigationPanel();
		robotPanel = new RobotPanel();
		instructionsPanel = new InstructionsPanel();
		window = new MainWindow(navigationPanel,robotPanel,instructionsPanel);
		
		//Create console view
		consoleView = new ConsoleView();
		
	}

}

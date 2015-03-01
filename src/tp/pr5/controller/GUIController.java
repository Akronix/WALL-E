/**
 * Created on May 7, 2013
 */
package tp.pr5.controller;

//java packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

//local packages
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngine;
import tp.pr5.view.gui.InstructionsPanel;
import tp.pr5.view.gui.MainWindow;
import tp.pr5.view.gui.NavigationPanel;
import tp.pr5.view.gui.PlaceCell;
import tp.pr5.view.gui.RobotPanel;
/**
 *The controller employed when the application is configured as a swing application. <br/>
 *It is responsible for requesting the robot engine start and it redirects the actions performed by the user on the window to the robot engine.<br/>
 *This class is only visible to package level.
 * @author Abel Serrano Juste
 */
public class GUIController extends Controller implements ActionListener,
													MenuListener
{
	
	//Swing components
	private MainWindow window;
	private NavigationPanel navigationPanel;
	private RobotPanel robotPanel;
	private InstructionsPanel instructionsPanel;
	
	//Others
	private static boolean debug = false;
	
	/**
	 * Constructor for the Graphic User Interface Controller
	 * @param model the model to associate this controller to it.
	 * @param window the main window of the GUI view
	 * @param navigationPanel the panel with the places and the places description.
	 * @param robotPanel the panel with the inventory of the robot
	 * @param instructionsPanel the panel with the instruction buttons for commanding the robot
	 */
	public GUIController(RobotEngine model, MainWindow window, NavigationPanel navigationPanel, 
			RobotPanel robotPanel, InstructionsPanel instructionsPanel) {
		super(model);
		this.window = window;
		this.navigationPanel = navigationPanel;
		this.robotPanel	= robotPanel;
		this.instructionsPanel = instructionsPanel;
	}

	/**
	 * Configure, set and run the graphic interface
	 */
	@Override
	/**
	 * It launch the graphic user interface
	 */
	public void startController() {
		//Launch graphics
		window.run();
	}

	@Override
	/**
	 * It handles the events from the action buttons, menu items and places on the map.
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		AbstractButton source = (AbstractButton) arg0.getSource();
		String commandFromButton = new String (source.getActionCommand());
		if (debug) System.out.println("Pulsed button: "+commandFromButton);
		switch (commandFromButton) {
		
		// Commands for the whole app \\
		case "QUIT": window.userWantsToQuit(); break;
		case "NEWGAME": this.engine.requestStart(); break;
		case "ABOUT": window.showAbout(); break;
		
		// Commands for WALL-E \\
			//Commands from InstructionsPanel 
		case "TURN": this.processInstruction("TURN "+instructionsPanel.getRotation()); break;
			
		case "PICK": this.processInstruction("PICK "+instructionsPanel.getItemIdToPick()); break;
		
		case "BUY": this.processInstruction("BUY "+instructionsPanel.getItemIdToPick()); break;

		case "DROP": this.processInstruction("DROP "+robotPanel.getSelectedId()); break;
		
		case "USE": this.processInstruction("OPERATE "+robotPanel.getSelectedId()); break;
		
			//Following commands don't have to take more arguments & can be called directly with the command:
		case "MOVE": this.processInstruction("MOVE"); break;
			
			//From MainWindow:
		case "UNDO": this.processInstruction("UNDO"); break;
		
		// Storms \\
		case "sandstorm": this.processInstruction("STORM SANDSTORM"); break;
		case "acidrain": this.processInstruction("STORM ACIDRAIN"); break;
		case "tornado": this.processInstruction("STORM TORNADO"); break;
		
		// Others: \\
		//PlaceCells:
		case "PlaceDescription": 
			PlaceInfo placeToShow = ((PlaceCell) source).getPlace();
			navigationPanel.placeScanned(placeToShow); 
			break;
		
		}
	}	
	
	@Override
	/**
	 * It hides or shows the undo instruction depends on it's available to execute it in the robot engine.
	 */
	public void menuSelected(MenuEvent arg0) {
		if (engine.getLastInstruction() != null)
			window.enableUndoMenuItem(true);
		else
			window.enableUndoMenuItem(false);
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		//Nothing. Obligation from MenuListener interface
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		//Nothing. Obligation from MenuListener interface
	}
	
	@Override
	/**
	 * Return String representation of this object
	 * @return "GUIController"
	 */
	public String toString(){
		return ("GUIController");
	}
	
}

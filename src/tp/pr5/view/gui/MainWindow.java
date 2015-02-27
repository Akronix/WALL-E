/**
 * Created on Mar 19, 2013
 */
package tp.pr5.view.gui;

//java packages
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


import tp.pr5.RobotEngine;
//local packages
import tp.pr5.controller.GUIController;
import tp.pr5.view.gui.RobotPanel;
import tp.pr5.view.observersInterfaces.RobotEngineObserver;

/**
 * This class creates the window for the Swing interface. The MainWindow contains the following components:
 * A menu with the QUIT action
 * An Action panel with several buttons to perform MOVE, TURN, OPERATE, PICK, and DROP actions. Additionally it has a combo box of turn rotations and a text field to write the name of the item that the robot wants to pick from the current place
 * A RobotPanel that displays the robot information (fuel and recycled material) and the robot inventory, which shows a table with item names and descriptions that the robot carries. The user can select the items contained in the inventory in order to DROP or OPERATE an item
 * A CityPanel that represents the city. It shows the places that the robot has visited and an icon that represents the robot heading. The robot heading is updated when the user performs a TURN action. 
 * The visible places are updated when the robot performs a MOVE action. Once a place is visited, the user can click on it in order to display the place description (similar to the RADAR command).
 * @author Abel Serrano Juste
 */
public class MainWindow extends JFrame implements RobotEngineObserver{
	
	//view components<
	private JPanel contentPane;
	
	private JLabel executionMessage;
	
	private NavigationPanel navigationPanel;
	private RobotPanel robotPanel;
	private InstructionsPanel instructionsPanel;
	
	private JPanel[] panels = new JPanel[3];
	
	private JMenuBar menuBar;
	
		//menu bar components<
		private JMenu extras;
		private JMenuItem undo;
	
		//>
	
	//controller references
		private GUIController controller;

		private JMenuItem quit;
	//>

		private JMenuItem newGame;

		private JMenuItem storm;

		private JMenuItem sandstorm;

		private JMenuItem acidrain;

		private JMenuItem tornado;
	
	//Others
		
		//KeyStrokes
		//private static final KeyStroke ctrlLKeyStroke = KeyStroke.getKeyStroke("control L");
		private static final KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
		private static final KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
		private static final KeyStroke ctrlZKeyStroke = KeyStroke.getKeyStroke("control Z");
		
		//Icons
		public static final ImageIcon WALLE_SAD = new ImageIcon("images/walleEyes.png");
		public static final ImageIcon WALLE_SPACESHIP = new ImageIcon("images/walleAtSpaceShip.png");
		public static final ImageIcon WALLE_UNPOWERED = new ImageIcon("images/walleSad.png");
		
		//Auxiliary

	//>
	
	/**
	 * Creates the window and the panels using Swing Components. 
	 * @param navigationPanel2 
	 * @param robotPanel2
	 * @param instructionsPanel2
	 */
	public MainWindow(NavigationPanel navigationPanel2, RobotPanel robotPanel2,
			InstructionsPanel instructionsPanel2) {
		
		super("WALL·E The garbage collector");

		this.panels[0] = this.navigationPanel = navigationPanel2;
		this.panels[1] = this.robotPanel = robotPanel2;
		this.panels[2] = this.instructionsPanel = instructionsPanel2;

		initComponents();
	}
		
		
	/**
	 * Creates the window and the panels using Swing Components. <br/>
	 * It stores a reference to the Controller object and provides the panels to the robot controller in order to communicate the simulation events.
	 * @param navigationPanel2 
	 * @param robotPanel2 
	 * @param instructionsPanel2 
	 * @param controller The Controller for this app
	 */
	public MainWindow(NavigationPanel navigationPanel2, RobotPanel robotPanel2,
			InstructionsPanel instructionsPanel2, GUIController controller) {
		this(navigationPanel2, robotPanel2, instructionsPanel2);
		this.setController(controller);
	}

	/**
	 * Generic method to create, configure and add each component for this frame
	 */
	private void initComponents(){
		this.setSize(900, 625);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);
		
		JPanel intermediatePanel = new JPanel(new BorderLayout());
		
		intermediatePanel.add(instructionsPanel,BorderLayout.WEST);
		
		intermediatePanel.add(robotPanel,BorderLayout.CENTER);
	
		intermediatePanel.add(navigationPanel,BorderLayout.SOUTH);
		
		contentPane.add(intermediatePanel,BorderLayout.CENTER);
		
		executionMessage = new JLabel("Probando",SwingConstants.CENTER);
		contentPane.add(executionMessage,BorderLayout.SOUTH);
			
		//Now, we can add the contentPane to the JFrame
		this.setContentPane(contentPane);
		
		//We add a MenuBar
		cookMenuBar();
		this.setJMenuBar(menuBar);

	}
	
	/**
	 * Method to create and configure the menu bar.
	 */
	private void cookMenuBar(){
		menuBar = new JMenuBar();
		
		//// File menu \\\\
		JMenu file = new JMenu("File");
		
		
		newGame = new JMenuItem("New Game",KeyEvent.VK_N);
		newGame.setAccelerator(ctrlNKeyStroke);
		newGame.setActionCommand("NEWGAME");
		file.add(newGame);
		
		/* Possible future addons
		//JMenuItem loadMap = new JMenuItem("Load map...",KeyEvent.VK_L);
	    //loadMap.setAccelerator(ctrlLKeyStroke);
		//file.add(loadMap);
		*/
		file.add(new JSeparator());
		
		
		//Quit option \\
		quit = new JMenuItem("QUIT",KeyEvent.VK_Q);
		quit.setAccelerator(ctrlQKeyStroke);
		file.add(quit);
		
		menuBar.add(file);
		
		////Storm menu\\\\
		storm = new JMenu("Storm");;
		
		//storm options\\
		sandstorm = new JMenuItem("Sand Storm",KeyEvent.VK_S);
		sandstorm.setActionCommand("sandstorm");
		
		acidrain = new JMenuItem("acidrain",KeyEvent.VK_A);
		acidrain.setActionCommand("acidrain");
		
		tornado = new JMenuItem("tornado",KeyEvent.VK_T);
		tornado.setActionCommand("tornado");
		
		storm.add(acidrain);
		storm.add(sandstorm);
		storm.add(tornado);
		
		menuBar.add(storm);
		
		//// Extras menu \\\\
		extras = new JMenu("Extras");
		
		//undo option \\
		undo = new JMenuItem("Undo",KeyEvent.VK_U);
		undo.setActionCommand("UNDO");
		undo.setAccelerator(ctrlZKeyStroke);
		
		extras.add(undo);
		
		menuBar.add(extras);

	}

	/**
	 * Method for request quit the app from the GUI interface.<br/>
	 * It ask confirmation before to the user.
	 */
	public void userWantsToQuit() {
		int choice = JOptionPane.showConfirmDialog(this, 
				"Are you sure do you want to exit of the game?",
				"Confirm exit", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				WALLE_SAD);
		
		if (choice == JOptionPane.YES_OPTION)
			controller.requestQuit();
		else {} //if (choice == JOptionPane.YES_OPTION) or (choice == JOptionPane.CLOSED_OPTION) do anything
	}
	
	/**
	 * Enable the menu item for the instruction undo
	 * @param enable true or false
	 */
	public void enableUndoMenuItem(boolean enable) {
		this.undo.setEnabled(enable);
	}

    /**
     * This method is for running the Main Window of the graphic interface.<br/>
     * This method do NOT create a instance of MainWindow, you have to create it before.
     */
    public void run(){
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow.this.pack();
                MainWindow.this.setSize(900,700);
                MainWindow.this.setVisible(true);
                
            }
    	});
    }

	@Override
	public void communicationCompleted() {
		//Nothing
	}

	@Override
	public void communicationHelp(String help) {
		//Nothing
	}
	

	/**
	 * Use when the game is over. It shows a dialog.
	 */
	@Override
	public void engineOff(String msg) {
		ImageIcon image = null;
		String message = null;
		if (msg.equals(RobotEngine.REACHED_SPACESHIP_MESSAGE)) {
			image = WALLE_SPACESHIP;
			message = msg;
		}
			
		else { //Robot runs out of fuel
			image = WALLE_UNPOWERED;
			message = msg;
		}
			
		JOptionPane.showMessageDialog(
				this,
				message,
				"GAME OVER",
				JOptionPane.INFORMATION_MESSAGE,
				image);
		
		this.controller.requestQuit();
		//for (int i = 0; i<panels.length; i++) this.setEnabled(false); //Disabling all the components of the view
		
	}
	
	@Override
	public void raiseError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void robotSays(String message) {
		this.executionMessage.setText(message);
		
	}

	@Override
	public void updateFuelRecycledMaterial(int fuel, int rm, double shield) {
		robotSays("Robot attributes has been updated: ("+fuel+", "+rm+", "+shield+')');
	}

	/**
	 * Set the controller for this Panel
	 * @param appController a GUIController
	 */
	public void setController(GUIController appController) {
		this.controller = appController;
		
		//Standard commands
		newGame.addActionListener(controller);
		quit.addActionListener(controller);
		undo.addActionListener(controller);
		
		extras.addMenuListener(controller);
		
		//Storms:
		this.acidrain.addActionListener(controller);
		this.sandstorm.addActionListener(controller);
		this.tornado.addActionListener(controller);
		
	}

	// Only For testing the interface
	/*
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		try {
			createAndShowGUI();
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	*/
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	/*
    private static void createAndShowGUI() {
    	
        //Create and set up the window.
    	MainWindow test = new MainWindow(new NavigationPanel(), new RobotPanel(), new InstructionsPanel ());
    	
        //Display the window.
        test.run();
    }
	 */
	
}

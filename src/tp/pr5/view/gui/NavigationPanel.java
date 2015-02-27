/**
 * 
 * Created on Mar 19, 2013
 */
package tp.pr5.view.gui;

//java packages
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//local packages
import tp.pr5.PlaceInfo;
import tp.pr5.controller.GUIController;
import tp.pr5.view.observersInterfaces.NavigationObserver;
import tp.pr5.Direction;

/**
 * This class is in charge of the panel that displays the information about the robot heading and the city that is traversing.
 * It contains the grid that represents the city in the Swing interface, a text area to show the place descriptions, and a label with an icon which represents the robot heading
 * The 11x11 grid contains PlaceCell objects and the first place starts at (5,5).
 * This panel will update the visited places when the robot moves from one place to another.
 * Additionally it will show the place description on a text area if the user clicks on a visited place.
 * @author Abel Serrano Juste
 */
public class NavigationPanel extends JPanel implements NavigationObserver{

	//view components<
	private PlaceCell[][] places;
	private JPanel map;
	
	private JLabel icon; //Icon which represent the current heading of the robot
	
	private JTextArea outputStream;
	private JScrollPane scrollPaneForOutputStream;
	//>
	
	//controller references
	private GUIController controller;
	
	//utility elements<
	private int actualCol;
	private int actualRow;
	//>
	
	//Others<
		/*Icons*/
		public static final ImageIcon WALLE_WEST = new ImageIcon("images/walleWest.png");
		public static final ImageIcon WALLE_NORTH = new ImageIcon("images/walleNorth.png");
		public static final ImageIcon WALLE_EAST = new ImageIcon("images/walleEast.png");
		public static final ImageIcon WALLE_SOUTH = new ImageIcon("images/walleSouth.png");
		
		/** Initial  row for the navigation panel*/
		public final int INITIAL_ROW = 5;
		/** Initial  col for the navigation panel*/
		public final int INITIAL_COL = 5;
	
		//Border titles
		private static final String PLACECELLS_TITLE = "City Map";
		private static final String OUTPUTSTREAM_TITLE = "Log";
	//>
	
	/**
	 * No args constructor. It uses BorderLayout.
	 */
	public NavigationPanel (){
		super(new BorderLayout());
		initComponents();
	}
	
	/**
	 * Constructor which initialize with an initial Place.
	 * @param controller The Controller that receives the place scannned actions made by the user
	 */
	public NavigationPanel (GUIController controller){
		this();
		this.setController(controller);
	}
	
	/**
	 * Method to initialize and add the components to this panel.
	 */
	private void initComponents (){
		
		//Load WALL·E icon as label
		icon = new JLabel();
		icon.setName("IconDirection");
		icon.setVisible(true);
		
		//Set the place cells
		places = new PlaceCell[11][11];
		map = new JPanel(new GridLayout(11,11));
		map.setName("City Map");
		map.setBorder(BorderFactory.createTitledBorder(PLACECELLS_TITLE));
		for (int i=0;i<places.length;i++){
			for(int j=0;j<places[i].length;j++)
					map.add(places[i][j] = new PlaceCell());
				}
		
		//Set the log textArea
		outputStream = new JTextArea (10,200);
		outputStream.setName("Log");
		outputStream.setBorder(BorderFactory.createTitledBorder(OUTPUTSTREAM_TITLE)); 
		outputStream.setEditable(false);
		
		//Create the scroll pane and add the JTextArea to it
		scrollPaneForOutputStream = new JScrollPane(outputStream);
		
		//Add all the components
		this.add(icon,BorderLayout.WEST);
		this.add(map,BorderLayout.CENTER);
		this.add(scrollPaneForOutputStream,BorderLayout.SOUTH);
	}
	
	/**
	 * Set text in the log text area
	 * @param message Message to be printed
	 */
	public void printInLog(String message){
		this.outputStream.setText(message);
	}
	
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		//Setup initial Place:
		actualCol = INITIAL_COL;
		actualRow = INITIAL_ROW;
		PlaceCell initialPlaceCell = this.places[INITIAL_ROW][INITIAL_COL];
		initialPlaceCell.setPlace(initialPlace);
		initialPlaceCell.visit(controller);
		initialPlaceCell.setBackground(Color.GREEN);
		placeScanned(initialPlace); //Show the description of the place
		
		//Setup initial heading:
		this.updateDirection(heading);
		
	}
	
	/**
	 * Update image of the robot which indicates where WALL·E is heading
	 * @param dir New direction where WALL·E is heading
	 */
	@Override
	public void updateDirection(Direction dir) {
		switch (dir) {
			case NORTH: this.icon.setIcon(WALLE_NORTH); break;
			case EAST: this.icon.setIcon(WALLE_EAST); break;
			case SOUTH: this.icon.setIcon(WALLE_SOUTH); break;
			case WEST: this.icon.setIcon(WALLE_WEST); break;
			default: break;
		}
	}
	
	/**
	 * Update the actual place where WALLE stays. The position of the place cell is chosen according to the direction of the movement.
	 * @param currentPlace new place where robot is in after move
	 * @param currentHeading heading to move.
	 */
	@Override
	public void updateMovingToPlace(PlaceInfo currentPlace, Direction currentHeading) {
		places[actualRow][actualCol].setBackground(Color.GRAY);
		
		//First, we change the coordinates of which PlaceCell we want to access according to the direction
		switch (currentHeading) {
			case NORTH: --actualRow; break;
			case EAST: ++actualCol; break;
			case SOUTH: ++actualRow; break;
			case WEST: --actualCol; break;
			default: break;
		}
		//We use an auxiliar variable which references to the vista PlaceCell which will be updated.
		PlaceCell nextPlace = places[actualRow][actualCol];
		
		if (!nextPlace.isVisited()){
			nextPlace.setPlace(currentPlace);
			nextPlace.visit(controller);
		}
		nextPlace.setBackground(Color.GREEN);
	}
	
	@Override
	public void updateGoingBack(Direction oppositeOfCurrentHeading) {
		//First we reset the place cell and set the color to blank
		PlaceCell actualPlace = places[actualRow][actualCol];
		actualPlace.reset();
		actualPlace.setBackground(Color.white);
		
		//Now, we change the coordinates of which PlaceCell we want to access according to the direction.
		switch (oppositeOfCurrentHeading) {
			case NORTH: --actualRow; break;
			case EAST: ++actualCol; break;
			case SOUTH: ++actualRow; break;
			case WEST: --actualCol; break;
			default: break;
		}
		//We use an auxiliar variable which references to the vista PlaceCell which will be updated.
		PlaceCell previousPlace = places[actualRow][actualCol];
		previousPlace.setBackground(Color.GREEN);
	}

	
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
		printInLog(placeDescription.toString());
		
	}
	

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
		printInLog(placeDescription.toString());
		
	}

	@Override
	public void resetMap() {
		for (int i=0;i<places.length;i++){
			for(int j=0;j<places[i].length;j++) {
					places[i][j].reset();
					places[i][j].setBackground(Color.white);
				}
		}
	}

	/**
	 * Set the controller for this Panel
	 * @param appController a GUIController
	 */
	public void setController(GUIController appController) {
		this.controller = appController;
	}

}

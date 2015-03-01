/**
 * 
 * Created on Mar 19, 2013
 */
package tp.pr5.view.gui;

//java packages
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
//local packages
import tp.pr5.PlaceInfo;

/**
 *Represents a Place in the city on the Swing interface. It is a button, which name is the place name.
 * A PlaceCell needs to store a reference to the place that it represents. However, this place should not be modified by the PlaceCell
 * When the user clicks on a PlaceCell the CityPanel will show the place description if the Place was previously visited.
 * @author Abel Serrano Juste
 */
public class PlaceCell extends JButton{
	
	//model elements<
	private PlaceInfo place;
	private boolean visited;
	private boolean discovered;
	//>
	
	/**
	 * Default constructor for PlaceCell. <br/>
	 * It doesn't have any place associated and it's marked as not visited.
	 */
	public PlaceCell(){
		super();
		this.place = null;
		this.visited = false;
		this.discovered = false;
		this.setEnabled(false);
		this.setVisible(true);
	}
	
	/**
	 * Set this place cell as visited.<br/>
	 * Put the name in the button and set enable it.
	 * @param listener action listener for this new visited place
	 */
	public void visit(ActionListener listener){
		this.visited = true;
		this.discovered = true;
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setText(place.getPlaceName());
		this.setName(place.getPlaceName());
		this.setEnabled(true);
		this.setBackground(Color.GREEN);
		this.addActionListener(listener);
		this.setActionCommand("PlaceDescription");
	}
	
	/**
	 * Set this place cell as discovered.<br/>
	 */
	public void discover(){
		this.discovered = true;
		this.setBackground(Color.RED);
	}
	
	/**
	 * Set the place associated to this PlaceCell
	 * @param p place
	 */
	public void setPlace(PlaceInfo p) {
		this.place = p;
	}
	
	/**
	 * It is need for calling to placeScanned
	 * @return PlaceInfo into this PlaceCeLL
	 */
	public PlaceInfo getPlace() {
		return this.place;
	}
	
	/**
	 * Getter for the information of the place
	 * @return place associated to this PlaceCell
	 */
	public String informationOfPlace(){
		return this.place.toString();
	}
	
	/**
	 * Tells if this placeCell has been visited
	 * @return <code>true</code> if it's flag as visited, <code>false</code> else
	 */
	public boolean isVisited(){
		return visited;
	}
	
	/**
	 * Tells if this placeCell has been discovered
	 * @return <code>true</code> if it's flag as discovered, <code>false</code> otherwise
	 */
	public boolean isDiscovered() {
		return this.discovered;
	}

	/**
	 * Undo visit this place cell and empty all its attributes.
	 */
	public void reset() {
		this.visited = false;
		this.discovered = false;
		this.place = null;
		this.setText("");
		this.setName("");
		this.setEnabled(false);
	}
	
}

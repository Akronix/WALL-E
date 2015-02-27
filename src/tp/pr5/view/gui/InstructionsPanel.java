/**
 * 
 * Created on Mar 19, 2013
 */
package tp.pr5.view.gui;

//java packages
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

//local packages
import tp.pr5.Rotation;
import tp.pr5.controller.GUIController;

/**
 * InstructionsPanel contains one button for each command (except scan & radar), and the fields for the rotation and for the id.
 * It need be able to access to the RobotPanel, in order to get the item from the inventory for commands drop and operate.
 * It communicates direct to the RobotEngine for performing the commands.
 * @author Abel Serrano Juste
 */
public class InstructionsPanel extends JPanel{
	
	//view components<
	private ArrayList<JButton> buttons;
	private JButton move;
	private JButton quit;
	private JButton turn;
	private JComboBox<Rotation> rotation;
	private JButton pick;
	private JTextField itemId;
	private JButton drop;
	private JButton operate;
	private JButton buy;

	//>
	
	//controller reference
	private GUIController controller;
	//>
	
	//others
		/** Title for InstructionsPanel*/
		private static final String INTRUCTIONS_PANEL_TITLE = "Actions";
	//>
	
	/**
	 * Default constructor for InstructionsPanel.
	 * Only useful for showing interface but not for interaction with it.
	 */
	public InstructionsPanel(){
		super(new GridLayout(4,2));
		buttons = new ArrayList<JButton>();
		initComponents();

	}
	
	/**
	 * Constructor with references to Controller, RobotPanel and MainWindow
	 * @param controller Controller of the app. Typically for panels is GUIController.
	 */
	public InstructionsPanel(GUIController controller){
		this();
		this.setController(controller);
	}
	
	/**
	 * Generic method to create, configure and add each component for this panel
	 */
	private void initComponents(){
		
		this.setBorder(BorderFactory.createTitledBorder(INTRUCTIONS_PANEL_TITLE));
		
		move = new JButton("MOVE");
		configButton(move);
		
		quit = new JButton("QUIT");
		configButton(quit);
		
		turn = new JButton("TURN");
		configButton(turn);
		
		Rotation[] objetos = {Rotation.LEFT,Rotation.RIGHT};
		this.add(rotation = new JComboBox<Rotation>(objetos));
		
		this.add(pick = new JButton("PICK"));
		configButton(pick);
		
		this.add(itemId = new JTextField("id"));
		
		this.add(drop = new JButton("DROP"));
		configButton(drop);
		
		this.add(operate = new JButton("USE"));
		configButton(operate);
		
		this.add(buy = new JButton("BUY"));
		configButton(buy);

		this.setVisible(true);
	}
	
	/**
	 * Set the action Command, add the action listener and add this button to this panel.
	 * @param button Button which is going to be configured
	 * @return <code>true</code> if everything goes well 
	 */
	private boolean configButton(JButton button){
		button.setActionCommand(button.getText());
		buttons.add(button);
		this.add(button);
		return true;
	}

	//Access to user input elements
	
	/**
	 * Take the rotation of the combo box and return it
	 * @return String text of the rotation selected
	 */
	public String getRotation() {
		return this.rotation.getSelectedItem().toString();
	}

	/**
	 * Take the string written in the id text field
	 * @return Text introduces by the user in the id text field
	 */
	public String getItemIdToPick() {
		return this.itemId.getText();
	}

	/**
	 * Set the controller for this Panel
	 * @param guiController a GUIController
	 */
	public void setController(GUIController guiController) {
		this.controller = guiController;
		for (JButton itButton : buttons){
			itButton.addActionListener(controller);
		}
	}
	
}

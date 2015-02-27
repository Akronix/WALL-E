/**
 * Created on Mar 19, 2013
 */
package tp.pr5.view.gui;

//java packages
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import tp.pr5.controller.Controller;
import tp.pr5.controller.GUIController;

//local packages
import tp.pr5.view.observersInterfaces.InventoryObserver;
import tp.pr5.view.observersInterfaces.RobotEngineObserver;

/**
 * RobotPanel displays information about the robot and its inventory.
 * More specifically, it contains labels to show the robot fuel and the weight of recycled material and a table that represents the robot inventory. 
 * Each row displays information about an item contained in the inventory.
 * @author Abel Serrano Juste
 */
public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver{
	
	//view components<
	private JLabel status;
	private JTable inventory;
	private JScrollPane inventoryContainer;
	//>
	
	//controller references
	@SuppressWarnings("unused")
	private Controller controller;
	//>

	//others
	private static final String FUEL_INTRODUCTION= "Fuel: ";
	private static final String RM_INTRODUCTION= " Recycled: ";
	private static final String SHIELD_INTRODUCTION= " Shield: ";
	//>
	
	/**
	 * I have to create my own TableModel for not allowing to edit cells, 
	 * because of this table is only an output table.
	 * @author Abel Serrano Juste
	 */
	private class InventoryTableModel extends AbstractTableModel{
			
			private String[] columnIdentifiers  = {"Id","Description"};
			
			//We know we have fixed columns, but we don't know how many items WALL·E'll carry.
			//That's why we need an array (Column fields) of ArrayList (every ID/description of one item)
			private ArrayList<String []> dataVector  = new ArrayList<String []>();
		
			
			public int getColumnCount() {
	            return columnIdentifiers.length;
	        }
	 
	        public int getRowCount() {
	            return dataVector.size();
	        }
	 
	        public String getColumnName(int col) {
	            return columnIdentifiers[col];
	        }
	 
	        /**
	         * Returns the value at the coordinates given
	         * @return value of the [row,col]
	         */
	        public Object getValueAt(int row, int col) {
	        	String [] rowOfJTable = dataVector.get(row);
	            return (String) rowOfJTable[col];
	        }
	 
	        /*
	         * JTable uses this method to determine the default renderer/
	         * editor for each cell.  If we didn't implement this method,
	         * then the last column would contain text ("true"/"false"),
	         * rather than a check box.
	         */
			public Class<?> getColumnClass(int c) {
	            return String.class;
	        }
	 
	        /*
	         * Don't need to implement this method unless your table's
	         * editable.
	         */
	        public boolean isCellEditable(int row, int col) {
	            return false;
	        }
	 
	        /*
	         * Don't need to implement this method unless your table's
	         * data can change.
	         */
	        public void setValueAt(Object value, int row, int col) {
	            String [] rowOfJTable;
	            if (dataVector.size()<=row) {
	            	rowOfJTable = new String [2];
	            	rowOfJTable[col] = (String) value;
	            	dataVector.add(rowOfJTable);
	            }
	            else {
	            	rowOfJTable = dataVector.get(row);
	            	rowOfJTable[col] = (String) value;
	            }
	            
	            fireTableCellUpdated(row, col); //Notice that the element at row,col has been changed
	            
	        }
	        
	        /**
	         * Clear all the data of the table
	         */
	        public void clear() {
	        	dataVector = new ArrayList<String []>();
	        }

	        /**
	         * Update the values of the inventory table.
	         * @param items String matrix with the id and description of each item of the inventory robot
	         */
			public void update(String[][] items) {
				// we get all the items and showing the id and description in the order of the arrayList
				for (int i = 0; i<items.length; i++) {
					this.setValueAt(items[i][0],i,0); //Get id of the ith-item and put on the first column of the table
					this.setValueAt(items[i][1],i,1); //Get description of the ith-item and put on the first column of the table
				}
			}
	    
	}
	
	/**
	 * Default constructor for Robot Panel
	 */
	
	public RobotPanel() {
		super(new GridLayout(2,1));
		initComponents();
	}
	
	
	public RobotPanel(GUIController controller){
		this();
		this.setController(controller);
	}


	/**
	 * Method to initialize and add the components to this panel.
	 */
	private void initComponents(){
		//Border for this panel
		this.setBorder(BorderFactory.createTitledBorder("Robot info"));
		
		//Label to show status of the robot: fuel and recycled material
		status = new JLabel(FUEL_INTRODUCTION+RM_INTRODUCTION+SHIELD_INTRODUCTION, SwingConstants.CENTER);
		this.add(status);
		
		//call to auxiliar method to configure features of the JTable inventory
		configureInventory();
	       
	    //Add the scroll pane with the JTable to this panel.
	    this.add(inventoryContainer);
		
	}
	
	@SuppressWarnings("synthetic-access")
	/**
	 * Configure the JTable which show the inventory content
	 */
	private void configureInventory() {
		//Create JTable
		inventory = new JTable(new InventoryTableModel());

		//Set size for the view port
		inventory.setPreferredScrollableViewportSize(new Dimension(20, 20));
		
		//configure to not allow reordering columns
        JTableHeader inventoryHeader = inventory.getTableHeader();
        inventoryHeader.setReorderingAllowed(false);
       
        //configure to select rows & only once
        inventory.setRowSelectionAllowed(true);
        inventory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
	    //Create the scroll pane and add the table to it.
	    inventoryContainer = new JScrollPane(inventory);
	}
	
	/**
	 * Get the Id for the selected row of the table
	 * @return String of the id field 
	 */
	public String getSelectedId() {
		int selectedRow	= this.inventory.getSelectedRow();
		String selectedId = "";
		
		if (selectedRow >= 0) //if the selected Row is -1 is because there is no any row selected.
			selectedId = (String) this.inventory.getValueAt(selectedRow, 0);

		return selectedId;
	}
    
	/**
	 * Method to update the values of fuel and recycled material of the robot on the view
	 * @param fuel new fuel of the robot
	 * @param rm new recycled material points of the robot
	 */
	@Override
    public void updateFuelRecycledMaterial(int fuel, int rm, double shield) {
    	status.setText(FUEL_INTRODUCTION+fuel+RM_INTRODUCTION+rm+SHIELD_INTRODUCTION+shield);
    }

    /**
     * Method to use when the inventory has been changed, to update it.
     * @param itemsIdDescp String matrix with the id and description of each item of the inventory robot
     */
    @Override
	public void updateInventory(String[][] itemsIdDescp) {
		//First, we reset the previous table data in order to avoid in inconsistencies
		InventoryTableModel inventoryModel = ((InventoryTableModel) inventory.getModel());
		
		inventoryModel.clear();
		
		//Later, we call to the inventory in order to correspond the data model (items) with the data content of the table.
		inventoryModel.update(itemsIdDescp);
		
		//Finally, we demand to redraw the table and so view the changes made in the inventory.
		inventory.updateUI();
				
	}
  

	@Override
	public void communicationCompleted() {
		// Nothing
	}

	@Override
	public void communicationHelp(String help) {
		// Nothing
	}

	@Override
	public void engineOff(String msg) {
		//Nothing
	}

	@Override
	public void raiseError(String msg) {
		//Nothing
	}

	@Override
	public void robotSays(String message) {
		//Nothing
	}

	/**
	 * Set the controller for this Panel
	 * @param appController a GUIController
	 */
	public void setController(GUIController appController) {
		this.controller = appController;
	}
	
	//Only for test this interface
    
  /*  public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RobotPanel.this.createAndShowGUI();
            }
        });
    }*/
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	/*
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        RobotPanel newContentPane = new RobotPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    */

}

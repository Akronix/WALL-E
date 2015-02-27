/**
 * Created on May 25, 2013
 */
package tp.pr5.userInterfaces;

import tp.pr5.RobotEngine;

/**
 * Factory for the multiple GUI interface mode.<br/>
 * It has a field to select the number of synchronized GUI interfaces which you want to run.
 * @author Abel Serrano Juste
 */
public class MultipleGUIFactory extends InterfaceModesAbstractFactory {
	
	private SimpleGUIFactory[] guis;

	private final int numberOfGUIS = 2;
	
	/**
	 * Constructor for this factory. It create new SimpleGUIFactory for each GUI required.
	 * @param model reference to the model
	 */
	protected MultipleGUIFactory(RobotEngine model) {
		
		super(model);
		
		guis = new SimpleGUIFactory[numberOfGUIS];
		for(int i = 0; i<numberOfGUIS; i++)
			guis[i] = new SimpleGUIFactory(model);

	}

	@Override
	/**
	 * Run each GUI interface
	 */
	public void runInterface() {
		for(SimpleGUIFactory gui : guis)
			gui.runInterface();
	}

	@Override
	protected void createController() {
		//Not used
	}

	@Override
	protected void createView() {
		//Not used
	}

}

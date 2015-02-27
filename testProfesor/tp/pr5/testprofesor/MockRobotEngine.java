package tp.pr5.testprofesor;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
import tp.pr5.Street;

public class MockRobotEngine extends RobotEngine {
	
	private Street headingStreet;
	private boolean quit;
	private boolean help;

	public MockRobotEngine(City city, Place initialPlace, Direction dir) {
		super(city, initialPlace, dir);
		setQuit(false);
		setHelp(true);
	}
	 
	public MockRobotEngine(Street head) {
		super(new MockCity(), new MockPlace(), Direction.NORTH);
		headingStreet = head;
	}
	
	public Street getHeadingStreet() {
		return headingStreet;
	}
	

	public void requestQuit() {
		setQuit(true);
	}	
	
	public void requestHelp(){
		setHelp(true);
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	public boolean isQuit() {
		return quit;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}

	public boolean isHelp() {
		return help;
	}

}

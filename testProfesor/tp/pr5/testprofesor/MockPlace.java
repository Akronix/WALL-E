package tp.pr5.testprofesor;

import tp.pr5.Place;

public class MockPlace extends Place {
	public static final String DEF_NAME = "MockPlaceName";
	public static final String DEF_DESC = "MockPlace Description"; 

	public MockPlace(String name, boolean isSpaceShip, String description) {
		super(name, isSpaceShip, description);
	}

	public MockPlace() {
		this(MockPlace.DEF_NAME, false, MockPlace.DEF_DESC);
	}
	
	public MockPlace(boolean isSpaceship) {
		this(MockPlace.DEF_NAME, isSpaceship, MockPlace.DEF_DESC);
	}

}

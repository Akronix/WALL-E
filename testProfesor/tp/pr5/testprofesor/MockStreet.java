package tp.pr5.testprofesor;

import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;

public class MockStreet extends Street {

	private boolean mockOpen;
	
	public MockStreet(Place source, Direction direction, Place target) {
		super(source, direction, target);
		mockOpen = true;
	}
	
	public boolean isOpen(){
		return mockOpen;
	}
	
	public void toggleOpen() {
		mockOpen = !mockOpen;
	}

}

package tp.pr5.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.Rotation;
import tp.pr5.Street;
import tp.pr5.instructions.exceptions.InstructionExecutionException;

public class NavigationModuleTest {
	
	private MockStreet testStreet;
	private Place testCurrentPlace;
	private Place testNextPlace;
	private NavigationModule testNavModule; 
	
	@Before
	public void setUp() {
		testCurrentPlace = new MockPlace();
		testNextPlace = new MockPlace(true);
		testStreet = new MockStreet(testCurrentPlace, Direction.NORTH, testNextPlace);
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), testCurrentPlace);
		testNavModule.initHeading(Direction.NORTH);
		
	}
	@Test
	public void testAtSpaceship() {
		assertFalse("ERROR: The current place is not the spaceship but atScaeship method returns true ",testNavModule.atSpaceship());
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), testNextPlace);
		assertTrue("ERROR: The current place is the spaceship but atScaeship method returns false ",testNavModule.atSpaceship());
	}


	@Test
	public void testGetHeadingStreet() {
		Street actualStreet;
		assertNotNull("ERROR: There is a street where the robot is heading but getHeadingStreet returns null", actualStreet = testNavModule.getHeadingStreet());
		assertEquals("ERROR: getHeadingStreet dos not return the correct street", testStreet, actualStreet);
		
		testNavModule = new NavigationModule(new tp.pr5.testprofesor.MockCity(), new MockPlace());
		assertNull("ERROR: There is not any street where the robot is heading but getHeadingStreet returns an object", testNavModule.getHeadingStreet());
	}

	@Test
	public void testRotate() {
		testNavModule.rotate(Rotation.LEFT);
		assertEquals("ERROR: Rotate method does not modify the heading direction properly",Direction.WEST, testNavModule.getCurrentHeading());
	}
	@Test
	public void testMoveOpenStreet() {
		try {
			testNavModule.move();
			assertEquals("ERROR: move does not change the current place", this.testNextPlace, testNavModule.getCurrentPlace());
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: Navigation module can move to another place in the current direction but move method throws an exception");
		}
		
		// There is no street in the heading direction 
		try {
			testNavModule.move();
			fail("ERROR: Navigation module cannot move to another place in the current direction but move method does not throw an exception");
		}
		catch (InstructionExecutionException e) {
			// No exception
		}
	}
	
	@Test
	public void testMoveClosedStreet() {
		// Close the street and try to move
		testStreet.toggleOpen();
		try {
			testNavModule.move();
			fail("ERROR: Navigation module cannot move to another place in the current direction but move method does not throw an exception");
		}
		catch (InstructionExecutionException e) {
			// No exception
		}
	}

}

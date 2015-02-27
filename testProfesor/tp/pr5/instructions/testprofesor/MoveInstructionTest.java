package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
import tp.pr5.Street;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.testprofesor.MockItem;
import tp.pr5.testprofesor.MockPlace;
import tp.pr5.testprofesor.MockRobotEngine;
import tp.pr5.testprofesor.MockStreet;

public class MoveInstructionTest {
	private Instruction testInstruction;
	private MockStreet testStreet;
	private Place testCurrentPlace;
	private Place testNextPlace;
	private NavigationModule testNavModule; 	
	private RobotEngine testEngine;
	@Before
	public void SetUp() throws Exception {
		testInstruction = new MoveInstruction();
		testCurrentPlace = new MockPlace();
		testNextPlace = new MockPlace(true);
		testStreet = new MockStreet(testCurrentPlace, Direction.NORTH, testNextPlace);
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), testCurrentPlace);
		testNavModule.initHeading(Direction.NORTH);
		testEngine = new MockRobotEngine(testStreet);
	}
	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("MOOve");
			fail("ERROR: A wrong instruction (MOOve) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("move");
			testInstruction.parse("mover");
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word MOVE", help.contains("MOVE"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word MOVER", help.contains("MOVER"));
	}
	
	@Test
	public void testMoveNoStreet() {
		testNavModule.initHeading(Direction.EAST);
		try {
			testInstruction = testInstruction.parse("move");
			testInstruction.configureContext(testEngine, testNavModule, null);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to move when there is no street to move");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}
	}
	
	@Test
	public void testMoveClosedStreet() {
		testStreet.toggleOpen();
		try {
			testInstruction = testInstruction.parse("move");
			testInstruction.configureContext(testEngine, testNavModule, null);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to move when there is a closed street");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}
	}
	
	@Test
	public void testMoveCorrect() {
		try {
			testInstruction = testInstruction.parse("move");
			testInstruction.configureContext(testEngine, testNavModule, null);
			testInstruction.execute();
			assertEquals("ERROR: The robot does not arrive at the correct place after moving",testNextPlace, testNavModule.getCurrentPlace());

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when the action is correct");
		}
	}
}

package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.Street;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.testprofesor.MockItem;
import tp.pr5.testprofesor.MockPlace;
import tp.pr5.testprofesor.MockRobotEngine;
import tp.pr5.testprofesor.MockStreet;


public class TurnInstructionTest {
	private Instruction testInstruction;
	private NavigationModule testNavModule; 	
	private RobotEngine testEngine;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new TurnInstruction();
		Street testStreet = new MockStreet(new MockPlace(), Direction.NORTH, new MockPlace());
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), new MockPlace());
		testNavModule.initHeading(Direction.NORTH);
		testEngine = new MockRobotEngine(testStreet);
	}
	
	@Test
	public void testNoParameters() {
		try {
			testInstruction.parse("turn");
			fail("ERROR: A turn instruction without parameters does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	
	@Test
	public void testWrongParameter() {
		try {
			testInstruction.parse("turn izda");
			fail("ERROR: A turn instruction with a wrong parameter does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("gira left");
			fail("ERROR: A wrong instruction (gira left) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("turn "+Rotation.LEFT);
			testInstruction.parse("girar "+Rotation.LEFT);
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word TURN", help.contains("TURN"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word GIRAR", help.contains("GIRAR"));
	}
	
	@Test
	public void testTurnCorrect() {
		try {
			testInstruction = testInstruction.parse("turn right");
			testInstruction.configureContext(testEngine, testNavModule, null);
			testInstruction.execute();
			assertEquals("ERROR: The robot is not heading to the correct direction after a turn instruction",Direction.EAST, testNavModule.getCurrentHeading());
			testInstruction = testInstruction.parse("turn left");
			testInstruction.configureContext(testEngine, testNavModule, null);
			testInstruction.execute();
			assertEquals("ERROR: The robot is not heading to the correct direction after a turn instruction",Direction.NORTH, testNavModule.getCurrentHeading());

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when the action is correct");
		}
	}
}

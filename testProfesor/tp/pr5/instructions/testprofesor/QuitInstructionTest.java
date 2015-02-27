package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.testprofesor.MockRobotEngine;


public class QuitInstructionTest {
	private Instruction testInstruction;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new QuitInstruction();
	}

	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("cuit");
			fail("ERROR: A wrong instruction (cuit) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("Quit");
			testInstruction.parse("salir");
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word QUIT", help.contains("QUIT"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word SALIR", help.contains("SALIR"));
	}
	
	@Test
	public void testQuit() {
		MockRobotEngine testEngine = new MockRobotEngine(null);
		try {
			testInstruction = testInstruction.parse("quit");
			testInstruction.configureContext(testEngine, null, null);
			testInstruction.execute();
			assertTrue("ERROR: Quit has not been requested in the robot engine after executing a quit instruction", testEngine.isQuit());

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when the action is correct");
		}	
	}
}

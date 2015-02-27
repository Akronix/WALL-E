package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.instructions.HelpInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.testprofesor.MockRobotEngine;


public class HelpInstructionTest {
	private Instruction testInstruction;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new HelpInstruction();
	}

	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("heLpp");
			fail("ERROR: A wrong instruction (heLpp) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("help");
			testInstruction.parse("ayuda");
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word HELP", help.contains("HELP"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word AYUDA", help.contains("AYUDA"));
	}
	
	@Test
	public void testHelp() {
		MockRobotEngine testEngine = new MockRobotEngine(null);
		try {
			testInstruction = testInstruction.parse("help");
			testInstruction.configureContext(testEngine, null, null);
			testInstruction.execute();
			assertTrue("ERROR: Help has not been requested in the robot engine after executing a help instruction", testEngine.isHelp());

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when the action is correct");
		}	
	}
}

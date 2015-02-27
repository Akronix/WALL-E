package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.ScanInstruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
import tp.pr5.items.testprofesor.MockItem;
import tp.pr5.testprofesor.MockRobotEngine;


public class ScanInstructionTest {
	private Instruction testInstruction;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new ScanInstruction();
	}
	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("sccan x");
			fail("ERROR: A wrong instruction (sccan x) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	
	@Test
	public void testParseCorrectNoParameters() {
		try {
			testInstruction.parse("scan");
			testInstruction.parse("escanear");
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A scan instruction without parameters throws an exception");
		}
	}
	
	@Test
	public void testParseCorrectWithParameters() {
		try {
			testInstruction.parse("scan "+MockItem.DEF_NAME);
			testInstruction.parse("escanear "+MockItem.DEF_NAME);
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word SCAN", help.contains("SCAN"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word ESCANEAR", help.contains("ESCANEAR"));
	}
	
	@Test
	public void testExecuteNoItemInContainer(){
		ItemContainer testContainer = new ItemContainer();
		RobotEngine testEngine = new MockRobotEngine(null);
		try {
			testInstruction = testInstruction.parse("scan "+MockItem.DEF_NAME);
			testInstruction.configureContext(testEngine, null, testContainer);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to scan an item that does not exist in the container");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}				
	}
}

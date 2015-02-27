package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.RadarInstruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;


public class RadarInstructionTest {
	private Instruction testInstruction;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new RadarInstruction();
	}

	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("aradar");
			fail("ERROR: A wrong instruction (aradar) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("RADAR");
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word RADAR", help.contains("RADAR"));
	}
}

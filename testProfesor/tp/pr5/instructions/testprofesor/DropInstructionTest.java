package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
import tp.pr5.items.testprofesor.MockItem;
import tp.pr5.testprofesor.MockPlace;
import tp.pr5.testprofesor.MockRobotEngine;


public class DropInstructionTest {
	private Instruction testInstruction;
	private Place testPlace; 
	private ItemContainer testContainer;
	private RobotEngine testEngine;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new DropInstruction();
		testPlace = new MockPlace();
		testContainer = new ItemContainer();
		testEngine = new MockRobotEngine(null);
	}
	
	@Test
	public void testNoParameters() {
		try {
			testInstruction.parse("DROP");
			fail("ERROR: A drop instruction without parameters does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("DROPP x");
			fail("ERROR: A wrong instruction (DROPP x) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("drop "+MockItem.DEF_NAME);
			testInstruction.parse("soltar "+MockItem.DEF_NAME);
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word DROP", help.contains("DROP"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word SOLTAR", help.contains("SOLTAR"));
	}
	
	@Test
	public void testExecuteNoItemInContainer(){
		NavigationModule navigation = new NavigationModule(null, testPlace);
		try {
			testInstruction = testInstruction.parse("drop "+MockItem.DEF_NAME);
			testInstruction.configureContext(testEngine, navigation, testContainer);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to drop an item that does not exist in the item container");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}				
	}
	
	@Test
	public void testExecuteItemAlreadyInPlace(){
		try {
			Item i = new MockItem();
			NavigationModule navigation = new NavigationModule(null, testPlace);
			testPlace.addItem(i);
			testContainer.addItem(i);
			testInstruction = testInstruction.parse("drop "+i.getId());
			testInstruction.configureContext(testEngine, navigation, testContainer);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to drop an item that already exists in the place");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}				
	}
	
	@Test
	public void testExecuteCorrect(){
		try {
			Item i = new MockItem();
			NavigationModule navigation = new NavigationModule(null, testPlace);
			testContainer.addItem(i);
			testInstruction = testInstruction.parse("drop "+i.getId());
			testInstruction.configureContext(testEngine, navigation, testContainer);
			testInstruction.execute();
			assertTrue("ERROR: Place does not contain the item", testPlace.existItem(i.getId()));
			assertFalse("ERROR: Container already contains the item", testContainer.containsItem(i.getId()));

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct command");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when when trying to drop an item that exists in the container");
		}				
	}
}

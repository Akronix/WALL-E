package tp.pr5.instructions.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.*;
import tp.pr5.items.testprofesor.MockItem;
import tp.pr5.testprofesor.MockCodeCard;
import tp.pr5.testprofesor.MockPlace;
import tp.pr5.testprofesor.MockRobotEngine;
import tp.pr5.testprofesor.MockStreet;

public class OperateInstructionTest {
	private Instruction testInstruction;
	private ItemContainer testContainer;
	private Street testStreet;
	private Place testCurrentPlace;
	private Place testNextPlace;
	private NavigationModule testNavModule; 	
	private RobotEngine testEngine;
	
	@Before
	public void SetUp() throws Exception {
		testInstruction = new OperateInstruction();
		testContainer = new ItemContainer();
		testCurrentPlace = new MockPlace();
		testNextPlace = new MockPlace(true);
		testStreet = new Street(testCurrentPlace, Direction.NORTH, testNextPlace, false, MockCodeCard.DEF_CODE);
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), testCurrentPlace);
		testNavModule.initHeading(Direction.NORTH);
		testEngine = new MockRobotEngine(testStreet);
	}
	
	@Test
	public void testNoParameters() {
		try {
			testInstruction.parse("operate");
			fail("ERROR: An operate instruction without parameters does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	
	@Test
	public void testWronginstruction() {
		try {
			testInstruction.parse("OPPERAte x");
			fail("ERROR: A wrong instruction (OPPERAte x) does not throw an exception");
		}
		catch (WrongInstructionFormatException e){
			// If we catch the exception then the parsing is correct
		}
	}
	@Test
	public void testParseCorrect() {
		try {
			testInstruction.parse("operate "+MockItem.DEF_NAME);
			testInstruction.parse("operar "+MockItem.DEF_NAME);
		}
		catch (WrongInstructionFormatException e){
			fail("ERROR: A correct instruction throws an exception");
		}	
	}
	
	@Test
	public void testGetHelp() {
		String help = testInstruction.getHelp();
		help.toUpperCase();
		assertTrue("ERROR: getHelp returns a description which does not contain the word OPERATE", help.contains("OPERATE"));
		assertTrue("ERROR: getHelp returns a description which does not contain the word OPERAR", help.contains("OPERAR"));
	}
	
	@Test
	public void testOperateNoItem() {
		try {
			testInstruction = testInstruction.parse("operate "+MockItem.DEF_NAME);
			testInstruction.configureContext(testEngine, testNavModule, testContainer);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when trying to operate an item that does not exist in the item container");

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
		}	
	}
	
	@Test
	public void testOperateFuel() {
		Garbage testItem = new Garbage (MockItem.DEF_NAME, MockItem.DEF_DESC, MockItem.DEF_VALUE); 
		testContainer.addItem(testItem);
		try {
			int currRecycledMaterial = testEngine.getRecycledMaterial();
			testInstruction = testInstruction.parse("operate "+MockItem.DEF_NAME);
			testInstruction.configureContext(testEngine, testNavModule, testContainer);
			testInstruction.execute();
			assertTrue("ERROR: The recycled material level is not updated properly after using a garbage item", currRecycledMaterial+MockItem.DEF_VALUE == testEngine.getRecycledMaterial());
			assertFalse("ERROR: The garbage has not been removed from the container although it can be used only once", testContainer.containsItem(MockItem.DEF_NAME));
			

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when when trying to operate an item that exists in the container");
		}	
	}
	
	@Test
	public void testOperateRecycledMaterial() {
		Fuel testItem = new Fuel (MockItem.DEF_NAME, MockItem.DEF_DESC, MockItem.DEF_VALUE, MockItem.DEF_TIMES+1); 
		testContainer.addItem(testItem);
		try {
			int currFuel = testEngine.getFuel();
			testInstruction = testInstruction.parse("operate "+MockItem.DEF_NAME);
			testInstruction.configureContext(testEngine, testNavModule, testContainer);
			testInstruction.execute();
			assertTrue("ERROR: The fuel level is not updated properly after using a fuel item", currFuel+MockItem.DEF_VALUE == testEngine.getFuel());
			assertTrue("ERROR: The fuel has been removed from the container but it can be used at least once", testContainer.containsItem(MockItem.DEF_NAME));
			testInstruction.execute();
			assertFalse("ERROR: The fuel has not been removed from the container but it is empty", testContainer.containsItem(MockItem.DEF_NAME));
			

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when when trying to operate an item that exists in the container");
		}	
	}
	
	@Test
	public void testOperateCodeCardCorrectCode() {
		CodeCard testItem = new MockCodeCard(); 
		testContainer.addItem(testItem);
		try {
			boolean streetState = testStreet.isOpen();
			testInstruction = testInstruction.parse("operate "+MockCodeCard.DEF_NAME);
			testInstruction.configureContext(testEngine, testNavModule, testContainer);
			testInstruction.execute();
			assertEquals("ERROR: The street has not changed its state after using a code card with the correct code", !streetState,testStreet.isOpen() );
			assertTrue("ERROR: The codecard has been removed from the container but it can be used always", testContainer.containsItem(MockCodeCard.DEF_NAME));
			streetState = testStreet.isOpen();
			testInstruction.execute();
			assertEquals("ERROR: The street has not changed its state after using a code card with the correct code", !streetState,testStreet.isOpen() );
			

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			fail("ERROR: execute throws an exception when when trying to operate an item that exists in the container");
		}	
	}
	
	@Test
	public void testOperateCodeCardWrongCode() {
		CodeCard testItem = new MockCodeCard(); 
		testContainer.addItem(testItem);
		testStreet = new Street(testCurrentPlace, Direction.NORTH, testNextPlace, false, MockCodeCard.WRONG_CODE);
		Street [] streets = {testStreet};
		testNavModule = new NavigationModule(new City(streets), testCurrentPlace);
		testNavModule.initHeading(Direction.NORTH);
		boolean streetState = testStreet.isOpen();
		try {
			testInstruction = testInstruction.parse("operate "+MockCodeCard.DEF_NAME);
			testInstruction.configureContext(testEngine, testNavModule, testContainer);
			testInstruction.execute();
			fail("ERROR: execute does not throw an exception when when trying to operate a codecard in a street with a wrong code");
			

		} catch (WrongInstructionFormatException e) {
			fail("ERROR: parse throws an exception with a correct instruction");
		}
		catch (InstructionExecutionException e) {
			assertEquals("ERROR: The street has changed its state after using a code card with a different code", streetState,testStreet.isOpen() );
			assertTrue("ERROR: The codecard has been removed from the container but it can be used always", testContainer.containsItem(MockCodeCard.DEF_NAME));
			
		}	
	}
}

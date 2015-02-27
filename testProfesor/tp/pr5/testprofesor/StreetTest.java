package tp.pr5.testprofesor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.items.CodeCard;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;


public class StreetTest {
	private Street nonclosedStreet;
	private Street openClosetreet;
	private Place targetPlace;
	private Place sourcePlace;

	@Before
	public void setUp() throws Exception {
		
		targetPlace = new Place ("Target Place", false, "Target place desc");
		sourcePlace = new Place ("Source Place", false, "Source place desc");
		nonclosedStreet = new Street(sourcePlace, Direction.NORTH, targetPlace);
		openClosetreet = new Street(sourcePlace, Direction.NORTH, targetPlace, false, MockCodeCard.DEF_CODE);
	}

	@Test
	public void testNextPlace() {
		assertEquals("ERROR: nextPlace(sourcePlace) does not return the correct place", targetPlace, nonclosedStreet.nextPlace(sourcePlace));
		assertEquals("ERROR: nextPlace(targetPlace) does not return the correct place", sourcePlace, nonclosedStreet.nextPlace(targetPlace));
	}
	
	@Test
	public void testNextPlaceWrongPlace() {
		Place ghostPlace = new Place("", false, "");
		assertNull("ERROR: nextPlace(ghostPlace) does not return null", nonclosedStreet.nextPlace(ghostPlace));
	}

	@Test
	public void testComeOutFromWithSourcePlace() {
		assertTrue("ERROR: comeOutFrom(sourcePlace, Direction.NORTH) does not return true with correct direction", nonclosedStreet.comeOutFrom(sourcePlace, Direction.NORTH));
		assertFalse("ERROR: comeOutFrom(sourcePlace, Direction.EAST) does not return false with a wrong direction", nonclosedStreet.comeOutFrom(sourcePlace, Direction.EAST));
	}
	
	@Test
	public void testComeOutFromWithTargetPlace() {
		assertTrue("ERROR: comeOutFrom(targetPlace, Direction.SOUTH) does not return true with correct direction", nonclosedStreet.comeOutFrom(targetPlace, Direction.SOUTH));
		assertFalse("ERROR: comeOutFrom(targetPlace, Direction.NORTH) does not return false with a wrong direction", nonclosedStreet.comeOutFrom(targetPlace, Direction.NORTH));
	}
	
	@Test
	public void testComeOutFromWithWrongPlace() {
		Place ghostPlace = new Place("", false, "");
		assertFalse("ERROR: comeOutFrom(ghostPlace, Direction.NORTH) does not return false with a wrong place", nonclosedStreet.comeOutFrom(ghostPlace, Direction.NORTH));
		assertFalse("ERROR: comeOutFrom(ghostPlace, Direction.SOUTH) does not return false with a wrong place", nonclosedStreet.comeOutFrom(ghostPlace, Direction.SOUTH));
	}
	
	@Test
	public void testIsOpen() {
		assertTrue("ERROR: A street created without a code is open by default", nonclosedStreet.isOpen());
		
		assertFalse("ERROR: The street was created closed but isOpen returns true", openClosetreet.isOpen());
		
		openClosetreet.open(new MockCodeCard());
		assertTrue("ERROR: After open the street with the correct card, isOpen should return true", openClosetreet.isOpen());
	}
	
	@Test
	public void testOpen() {
		if (!openClosetreet.isOpen()){
			CodeCard testCard = new MockCodeCard(MockCodeCard.DEF_NAME, MockCodeCard.DEF_DESC, MockCodeCard.WRONG_CODE);
			assertFalse("ERROR: A street with a wrong code cannot be opened but open returns true", openClosetreet.open(testCard));
			assertFalse("ERROR: A street cannot be openned with a card with a wrong code", openClosetreet.isOpen());
			
			testCard = new MockCodeCard();
			assertTrue("ERROR: A street opens with a correct code but open returns false", openClosetreet.open(testCard));
			assertTrue("ERROR: A street is open after using a card with a correct code", openClosetreet.isOpen());
			assertTrue("ERROR: A street opens with a correct code but open returns false", openClosetreet.open(testCard));
			assertTrue("ERROR: An open street remains open after open it again", openClosetreet.isOpen());
		}
		else 
			fail("ERROR: Test street must be closed");
	}
	
	@Test
	public void testClose() {
		openClosetreet = new Street(sourcePlace, Direction.NORTH, targetPlace, true, MockCodeCard.DEF_CODE);
		if (openClosetreet.isOpen()){
			CodeCard testCard = new MockCodeCard(MockCodeCard.DEF_NAME, MockCodeCard.DEF_DESC, MockCodeCard.WRONG_CODE);
			assertFalse("ERROR: A street with a wrong code cannot be closed but close returns true",openClosetreet.close(testCard));
			assertTrue("ERROR: A street cannot be closed with a card with a wrong code", openClosetreet.isOpen());
			
			testCard = new MockCodeCard();
			assertTrue("ERROR: A street closes with a correct code but open returns false", openClosetreet.close(testCard));
			assertFalse("ERROR: A street is closed after using a card with a correct code", openClosetreet.isOpen());
			assertTrue("ERROR: A street closes with a correct code but open returns false", openClosetreet.close(testCard));
			assertFalse("ERROR: An open street remains closed after open it again", openClosetreet.isOpen());
		}
		else 
			fail("ERROR: Test street must be open");
	}
}

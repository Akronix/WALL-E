package tp.pr5.testprofesor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.items.Item;
import tp.pr5.Place;
import tp.pr5.items.testprofesor.MockItem;

public class PlaceTest {

	Place placeTest;
	private String placeName;
	private String placeDescription;

	@Before
	public void setUp() throws Exception {
		placeName = "place name";
		placeDescription = "place description";
		placeTest = new Place(placeName, false, placeDescription);   
	}

	@Test
	public void testIsSpaceship() {
		assertFalse("ERROR: We have created a place that does not represent a spaceship but isSpaceship returns true",placeTest.isSpaceship());
		// Change the place
		placeTest = new Place("", true, "");
		assertTrue("ERROR: We have created a place that represents a spaceship but isSpaceship returns false",placeTest.isSpaceship());
	}

	@Test
	public void testToString() {
		assertTrue("ERROR: the place name does not appear in the string", placeTest.toString().contains(placeName));
		assertTrue("ERROR: the place description does not appear in the string", placeTest.toString().contains(placeDescription));
	}

	@Test
	public void testAddItem() {
		assertTrue("ERROR: The place is empty but addItem returns false", placeTest.addItem(new MockItem()));
		assertFalse("ERROR: The place contains an item with the same id but addItem returns true", placeTest.addItem(new MockItem()));
		assertTrue("ERROR: The place does not contain an item with the same id but addItem returns false", placeTest.addItem(new MockItem(MockItem.WRONG_NAME)));
	}

	@Test
	public void testPickItem() {
		assertNull("ERROR: The place is empty but pickItem returns an object", placeTest.pickItem(MockItem.DEF_NAME));
		Item testItem = new MockItem();
		if (placeTest.addItem(testItem)) {
			Item actualItem;
			assertNull("ERROR: The place does not contain an item with this id but pickItem returns an object", placeTest.pickItem(MockItem.WRONG_NAME));
			assertNotNull("ERROR: The place contains an item with this id but pickItem returns null", actualItem=placeTest.pickItem(MockItem.DEF_NAME));
			assertEquals("ERROR: The place contains an item with this id but pickItem returns null", testItem, actualItem);			
		}
		else {
			fail("ERROR: addItem is not working properly. Try first addItem tests");
		}
	}
	
	@Test
	public void testExistItem() {
		assertFalse("ERROR: The place is empty but existItem returns true", placeTest.existItem(MockItem.DEF_NAME));
		Item testItem = new MockItem();
		if (placeTest.addItem(testItem)) {
			assertFalse("ERROR: The place does not contain an item with this id but existItem returns true", placeTest.existItem(MockItem.WRONG_NAME));
			assertTrue("ERROR: The place contains an item with this id but existItem returns false", placeTest.existItem(MockItem.DEF_NAME));		
		}
		else {
			fail("ERROR: addItem is not working properly. Try first addItem tests");
		}
	}
	
	@Test
	public void testDropItem() {
		assertTrue("ERROR: The place is empty but dropItem returns false", placeTest.dropItem(new MockItem()));
		assertFalse("ERROR: The place contains an item with the same id but dropItem returns true", placeTest.dropItem(new MockItem()));
		assertTrue("ERROR: The place does not contain an item with the same id but dropItem returns false", placeTest.dropItem(new MockItem(MockItem.WRONG_NAME)));
	}
}

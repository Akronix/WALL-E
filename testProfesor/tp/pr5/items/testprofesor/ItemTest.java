package tp.pr5.items.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr5.items.Item;

public class ItemTest {
	Item iTest;
	String testId;
	String testDescription;
	@Before
	public void setUp() {
		testId=MockItem.DEF_NAME;
		testDescription = MockItem.DEF_DESC;
		iTest = new MockItem(testId, testDescription);		
	}

	@Test
	public void testGetId() {
		assertEquals("ERROR: Item id has been modified during Item construction", testId, iTest.getId());		
	}
	
	@Test
	public void testToString() {
		String itemString = iTest.toString();
		assertTrue("ERROR: String returned by toString method does not contain the item id", itemString.contains(testId));
		assertTrue("ERROR: String returned by toString method does not contain the item description", itemString.contains(testDescription));
	}
}

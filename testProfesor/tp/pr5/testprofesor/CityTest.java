package tp.pr5.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;

public class CityTest {

	@Test
	public void testLookForStreet() {
		Place source = new MockPlace();
		Place target = new MockPlace();
		Street testStreet = new Street(source, Direction.NORTH, target);
		Street [] streets = {testStreet};
		City testCity;
		try {
			testCity = new City();
			assertNull("ERROR: The city does not contain any street but lookForStreet returns an object", testCity.lookForStreet(source, Direction.EAST));
		}
		catch (NullPointerException e){
			fail("ERROR: Your code is trying to invoke a method in a null object.");
		}
		
		testCity = new City(streets);
		assertNull("ERROR: The city does not contain any street that starts from the given target but lookForStreet returns an object", testCity.lookForStreet(new MockPlace(), Direction.NORTH));
		assertNull("ERROR: The city does not contain any street in the given direction but lookForStreet returns an object", testCity.lookForStreet(source, Direction.EAST));
		Street actualStreet;
		assertNotNull("ERROR: The city contains a street from the current target in the given direction but lookForStreet returns null", actualStreet = testCity.lookForStreet(source, Direction.NORTH) );
		assertEquals("ERROR: The city contains a street from the current target in the given direction but lookForStreet returns a street different from the one employed during the initialization", testStreet, actualStreet);
	}

}

package tp.pr5.cityLoader.testprofesor;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;


public class CityLoaderTest {
	String file;
	String endl = System.getProperty("line.separator");

	private boolean loadString() {
		
		file = "BeginCity" + endl +
				"BeginPlaces" + endl +
				"place 0 Sol You_are_at_the_center_of_Madrid noSpaceShip" + endl +
				"place 1 Callao In_this_square_you_can_find_a_code_card noSpaceShip" + endl +
				"place 2 Colon People_concentrates_here_to_watch_football noSpaceShip" + endl + 
				"place 3 Exit Ok,_finally_you_have_found_your_spaceship... spaceShip" + endl +
				"EndPlaces" + endl +
				"BeginStreets" + endl +
				"street 0 place 0 south place 1 open" + endl +
				"street 1 place 1 east place 2 open" + endl +
				"street 2 place 2 north place 3 closed onetwothreefourfive" + endl +
				"EndStreets" + endl +
				"BeginItems" + endl +
				"fuel 0 Petrol from_olds_heatings 10 3 place 0" + endl +
				"fuel 1 Battery to_get_cracking -50 1 place 0" + endl +
				"codecard 2 Card The_key_is_too_easy onetwothreefourfive place 1" + endl +
				"garbage 3 Newspapers News_on_sport 30 place 2" + endl +
				"EndItems" + endl +
				"EndCity"; 
		return true;

	}

	@Test
	public void testloadCity() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();  		 
		/**/
		if (loadString()){
			for (int i = 1; i<file.length(); i++) {
				try {
					InputStream is = new ByteArrayInputStream(file.substring(0, i).getBytes());
					ml.loadCity(is);
				}
				catch (WrongCityFormatException e) {
					// Continue
				}
				catch (Exception e) {
					fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
				}
			}
		}
	}

	@Test
	public void testPlaceFirstId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();  		 
		/**/
		if (loadString()){
			file= file.replace("place 0 Sol You_are_at_the_center_of_Madrid noSpaceShip",
					// Change first place id
					"place 11 Sol You_are_at_the_center_of_Madrid noSpaceShip");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: First place is not place 0");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testPlaceConsecutiveId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();  		 
		/**/
		if (loadString()){
			file = file.replace("place 2 Colon People_concentrates_here_to_watch_football noSpaceShip",
					// Change with a non-consecutive room id
					"place 11 Colon People_concentrates_here_to_watch_football noSpaceShip");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: A place with id=11 after a place with id=1 is not correct");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testStreetFirstId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();		 
		/**/
		if (loadString()){
			file= file.replace("street 0 place 0 south place 1 open",
					// Change first door id
			"street 11 place 0 south place 1 open");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: First street is not street 0");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testStreetConsecutiveId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();		 
		/**/
		if (loadString()){
			file = file.replace("street 2 place 2 north place 3 closed onetwothreefourfive",
					// Change with a non-consecutive door id
					"street 11 place 2 north place 3 closed onetwothreefourfive");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: A street with id=11 after a street with id=1 is not correct");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testItemFirstId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();  		 
		/**/
		if (loadString()){
			file= file.replace("fuel 0 Petrol from_olds_heatings 10 3 place 0",
					// Change first item id
			"fuel 11 Petrol from_olds_heatings 10 3 place 0");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: First item id is not 0");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testItemConsecutiveId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();		 
		/**/
		if (loadString()){
			file = file.replace("garbage 3 Newspapers News_on_sport 30 place 2",
					// Change with a non-consecutive item id
			"garbage 11 Newspapers News_on_sport 30 place 2");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: An item with id=11 after an item with id=1 is not correct");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testStretBetweenWrongPlacesId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split();	 
		/**/
		if (loadString()){
			file = file.replace("street 0 place 0 south place 1 open",
					// Change target room with a wrong id
			"street 0 place 0 south place 12 open");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: loadMad should fail when trying to create a street between two places with wrong id");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}

	@Test
	public void testItemWrongPlaceId() {
		//*
		CityLoaderFromTxtFile ml = new CityLoaderFromTxtFile();
		/*/
		CityLoaderFromTxtFile_split ml = new CityLoaderFromTxtFile_split(); 		 
		/**/
		if (loadString()){
			file = file.replace("fuel 0 Petrol from_olds_heatings 10 3 place 0",
					// Change the room that will contain the item with a wrong id
			"fuel 0 Petrol from_olds_heatings 10 3 place 11");
			try {
				InputStream is = new ByteArrayInputStream(file.getBytes());
				ml.loadCity(is);
				fail("ERROR: loadMad should fail when trying to create an item in a place with wrong id");
			}
			catch (WrongCityFormatException e) {
				// Continue
			}
			catch (Exception e) {
				fail("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);					
			}
		}
	}
}

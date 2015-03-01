package tp.pr5.cityLoader;

//Java packages:
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Shop;
import tp.pr5.Street;
import tp.pr5.Bunker;
import tp.pr5.PlaceType;
import tp.pr5.Trench;

//Local packages:
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.*;
import tp.pr5.utilities.Miscellanous;

/**
 * City loader from a txt file
 * @author Abel Serrano Juste
 */
public class CityLoaderFromTxtFile {
	
	//Main attributes:
	private String testingFile;
	
	//Attributes for process the file:
	private Place[] placeMap;
	private Street[] streetMap;
	private Shop stockItems;

	private Scanner sc;
	
	//Constants:
	private static final String endl = System.getProperty("line.separator");
	private static final String NATURAL_PATTERN = "\\d+"; //regular expression which defines strings which represents natural number.
	private static final String INTEGER_PATTERN = "[-]"+"?"+NATURAL_PATTERN; //regular expression which defines integer which represents integer number.
	
	
	
	/**
	 * Inicialize arrays which we're going to use for loading the city (not necessary)
	 */
	public CityLoaderFromTxtFile(){
		this.placeMap=null;
		this.streetMap=null;
	}
	
	/**
	 * Create a new City from an InputStream. For this process, this method will read and check every definition field of the input file.<br/>
	 * These fields are: Places, Streets and Items.
	 * @param file The input stream where the city text definition is stored
	 * @return The City object defined on the input file
	 * @throws java.io.IOException When there is some format error in the file (WrongCityFormatException) or some errors in IO operations.
	 */
	public City loadCity(InputStream file) throws IOException{
		try {
			sc = new Scanner(file);
			if (sc.hasNextLine() && sc.nextLine().equals("BeginCity")){
				
				if (sc.hasNextLine() && sc.nextLine().equals("BeginPlaces"))
					readPlacesM();
				else
					throw new WrongCityFormatException("BeginPlaces introduction word expected");
				
				if (sc.hasNextLine() && sc.nextLine().equals("BeginStreets"))
					readStreetsM();
				else
					throw new WrongCityFormatException("BeginStreets introduction word expected");
				
				if (sc.hasNextLine() && sc.nextLine().equals("BeginItems"))
					readItemsM();
				else
					throw new WrongCityFormatException("BeginItems introduction word expected");
				
				if (sc.hasNextLine() && sc.nextLine().equals("BeginShop")){
					readShopM();
				}
				else
					throw new WrongCityFormatException("BeginShop introduction word expected");
			}
			else
				throw new WrongCityFormatException("BeginCity introduction word expected");

			return new City(streetMap,stockItems);
			
		} catch (SecurityException securityException) {
			throw new SecurityException(" Permission denied.");
		}
		finally{
			closeCity(file);
		}
	}
	

	/**
	 * Process all the places definition field through an Automata.<br/>
	 * It uses a ArrayList to add the places, in order, and finally it returns that in Array shape.
	 * @throws WrongCityFormatException When format isn't appropriate
	 */
	private void readPlacesM() throws WrongCityFormatException{
		//Initializing variables:
		ArrayList<Place> placesArray = new ArrayList<Place>();
		Place nextPlace;
		int position=0;
		String[] line;
		boolean end = false;
		
		Pattern PLACES_PATTERN = Pattern.compile(
				"(\\w+)\\s" + //place
				"(\\d+)\\s" + //place number
				"(\\w+)\\s" + //place name
				"([\"].*[\"]|\\S+)" + //place description
				"\\s(\\w+)"); //spaceShip/noSpaceship
		
		while (!end && sc.hasNextLine()){

			
			//First, we look for the pattern for places definition (description with _ either ""):
			String output = sc.findInLine(PLACES_PATTERN);
			
			//If it matches:
			if (output != null){
				
				//Uncomment for showing the string matched
				//System.out.println(output);
				
				//We obtain the match result of sc.findInLine
				MatchResult result = sc.match();
				
				line = new String [result.groupCount()];
				
				//Create an array of Strings with a token of the pattern in each position
				for (int i=0; i<result.groupCount();i++){
					
					//Uncomment for showing the tokens recognized
					//System.out.print(result.group(i+1)+endl);

					line[i] = result.group(i+1);
				
				}
				
				//Before to be able to process any more lines, we have to move the buffer reader to the next line.
				if (sc.hasNextLine()) sc.nextLine();
			
				/* Uncomment for look places reading
				System.out.print(endl+"line: ");
				for (int i = 0; i<line.length; i++)
					System.out.print(line[i]+" ");
				*/
				
				//After this, we can call the automata to process the line
				nextPlace = placeNFA(line,position);
				
				//If we have a output place (the place definition has been recognized), we add it to the arrayList and incr counter
				if (nextPlace != null){
					placesArray.add(position,nextPlace);
					position++;
				}
				else
					throw new WrongCityFormatException("Place number "+ position +" is incorrectly defined.");
			}
			
			//Are we reached the end of the definition places field?
			else if (sc.nextLine().equals("EndPlaces"))
				end = true;
			
			//Otherwise, the input is incorrect
			else
				throw new WrongCityFormatException("Place number "+ position +" is incorrectly defined.");
			
		}
		/* Uncomment for look placesArrayContent
		for (int i=0; i<placesArray.size(); i++)
			System.out.println(placesArray.get(i));
		*/
		
		//Finally, we convert ArrayList to common Array for better performance in the future.
		placeMap = new Place[placesArray.size()];
		placeMap = placesArray.toArray(placeMap);
		
		}
	
	
	
	/**
	 * Process all the places definition field through an Automata.<br/>
	 * It uses a ArrayList to add the streets, in order, and finally it returns that in Array shape.
	 * @throws WrongCityFormatException When format isn't appropriate
	 */
	private void readStreetsM() throws WrongCityFormatException{
		//Initializing variables:
		ArrayList<Street> streetsArray = new ArrayList<Street>();
		Street nextStreet;
		int position=0;
		String[] line;
		boolean end = false;
		
		Pattern STREET_PATTERN = Pattern.compile("(\\w+)\\s(\\d+)" + //street number
				"\\s(\\w+)\\s(\\d+)" + //source place number
				"\\s(\\w+)" + //direction
				"\\s(\\w+)\\s(\\d+)" + //target place number
				"\\s(\\w+)" + //open/closed
				"\\s?(\\w+)?"); //code);
		
		while (!end && sc.hasNextLine()){
			
			//First, we look for the pattern for streets definition (open either closed streets):
			String output = sc.findInLine(STREET_PATTERN);
			
			//If it matches:
			if (output != null){
				
				//Uncomment for showing the string matched
				//System.out.println(output);
				
				//We obtain the match result of sc.findInLine
				MatchResult result = sc.match();
				
				line = new String [result.groupCount()];
				
				//Create an array of Strings with a token of the pattern in each position
				for (int i=0; i<result.groupCount();i++){
					
					//Uncomment for showing the tokens recognized
					//System.out.print(result.group(i+1)+Interpreter.LINE_SEPARATOR);
					
					line[i] = result.group(i+1);
				
				}
				
				//Before to be able to process any more lines, we have to move the buffer reader to the next line.
				if (sc.hasNextLine()) sc.nextLine();
				
				/*Uncomment for look streets reading 
				System.out.print(endl+"line: ");
				for (int i = 0; i<line.length; i++)
					System.out.print(line[i]+" ");
				System.out.print(endl);
				*/
				
				//After we can call the automata to process the line
				nextStreet = streetNFA(line,position);
					
				//If we have a output street, we add it to the arrayList and incr counter
				if (nextStreet != null){
					streetsArray.add(position,nextStreet);
					position++;
				}
				else
					throw new WrongCityFormatException("Street number "+ position +" is incorrectly defined. (1)");
			}
			
			//Are we reached the end of the definition streets field?
			else if (sc.nextLine().equals("EndStreets"))
				end = true;
			
			//Otherwise, the input is incorrect
			else
				throw new WrongCityFormatException("Street number "+ position +" is incorrectly defined. (2)");
		}
		
		/* Uncomment for look StreetArray Content 
		for (int i=0; i<streetsArray.size(); i++)
			System.out.println(streetsArray.get(i));
		*/
		
		//Finally, we convert ArrayList to common Array for better performance in the future.
		streetMap = new Street[streetsArray.size()];
		streetMap = streetsArray.toArray(streetMap);
		
		}
	
	
	/**
	 * Process all the items definition field through an Automata.<br/>
	 * It add each item to the correspondent place in the place map.
	 * @throws WrongCityFormatException When format isn't appropriate
	 */
	private void readItemsM() throws WrongCityFormatException{
		//Initializing variables:
		Item nextItem;
		int position=0;
		String[] line;
		boolean end = false;
		
		Pattern ITEM_PATTERN = Pattern.compile("(\\w+)" + //item type
				"\\s(\\d+)" + //item number
				"\\s(\\w+)\\s" + //item name
				"([\"].*[\"]|\\S+)" + //item description
				"\\s(-?\\d+|\\w+)" + //power or code or damage
				"\\s?(\\d+)?" + //Only for fuel: times ; or only for bombs: minimal points
				"\\s?(\\w+)?" + //Only for bombs: on/off of the bomb
				"\\s(\\w+)" +  //place
				"\\s(\\d+)"); // number of place container
		
		while (!end && sc.hasNextLine()){
			
			//First, we look for the pattern for items definition (description with _ either "" and any tipe of item.):
			String output = sc.findInLine(ITEM_PATTERN);
			
			//If it matches:
			if (output != null){
				
				//Uncomment for showing the string matched
				//System.out.println(output);
				
				//We obtain the match result of sc.findInLine
				MatchResult result = sc.match();
				
				line = new String [result.groupCount()];
				
				//Create an array of Strings with a token of the pattern in each position
				for (int i=0; i<result.groupCount();i++){
					
					//Uncomment for showing the tokens recognized
					//System.out.print(result.group(i+1)+Interpreter.LINE_SEPARATOR);
					
					line[i] = result.group(i+1);
				
				}
				
				//Before to be able to process any more lines, we have to move the buffer reader to the next line.
				if (sc.hasNextLine()) sc.nextLine();
					
				//After we can call the automata to process the line
				nextItem = itemNFA(line,position);
					
				//If we have a output item, we increment counter
				if (nextItem != null){
					position++;
				}
				else
					throw new WrongCityFormatException("Item number "+position +" is incorrectly defined.");
			}
				
			//Are we reached the end of the definition items field?
			else if (sc.nextLine().equals("EndItems"))
				end = true;
			
			//Otherwise, the input is incorrect
			else
				throw new WrongCityFormatException("Item number "+ position +" is incorrectly defined or EndItems word expected");
		}
		
		// Uncomment for look items of every place 
		//for (int i=0; i<placeMap.length; i++)
			//System.out.println("******"+endl+placeMap[i].getPlaceName()+":"+endl+placeMap[i].getPlaceItems());
		
	}
	
	
	/**
	 * @throws WrongCityFormatException When format isn't appropriate
	 */
	private void readShopM() throws WrongCityFormatException{
		//Initializing variables:
		Item nextItem;
		int position=0;
		String[] line;
		boolean end = false;
		stockItems = new Shop();
		
		Pattern ITEM_PATTERN = Pattern.compile("(\\w+)" + //item type
				"\\s(\\d+)" + //item number
				"\\s(\\w+)\\s" + //item name
				"([\"].*[\"]|\\S+)" + //item description
				"\\s(-?\\d+|\\w+)" + //power or code
				"\\s?(\\d+)?" + //times?
				"\\s(\\d+)"); // price
		
		while (!end && sc.hasNextLine()){
			
			//First, we look for the pattern for items definition (description with _ either "" and any tipe of item.):
			String output = sc.findInLine(ITEM_PATTERN);
			
			//If it matches:
			if (output != null){
				
				//Uncomment for showing the string matched
				//System.out.println(output);
				
				//We obtain the match result of sc.findInLine
				MatchResult result = sc.match();
				
				line = new String [result.groupCount()];
				
				//Create an array of Strings with a token of the pattern in each position
				for (int i=0; i<result.groupCount();i++){
					
					//Uncomment for showing the tokens recognized
					//System.out.print(result.group(i+1)+Interpreter.LINE_SEPARATOR);
					
					line[i] = result.group(i+1);
				
				}
				
				//Before to be able to process any more lines, we have to move the buffer reader to the next line.
				if (sc.hasNextLine()) sc.nextLine();
					
				//After we can call the automata to process the line
				nextItem = shopItemNFA(line,position);
					
				//If we have a output item, we increment counter
				if (nextItem != null){
					this.stockItems.addItemToStock(nextItem);
					position++;
				}
				else
					throw new WrongCityFormatException("Item number "+position +" is incorrectly defined.");
			}
				
			//Are we reached the end of the definition items field?
			else if (sc.nextLine().equals("EndShop"))
				end = true;
			
			//Otherwise, the input is incorrect
			else
				throw new WrongCityFormatException("Item number "+ position +" is incorrectly defined or EndShop word expected");
			
			
		}
		
		// Uncomment for look every items of the shop 
		//System.out.println("******"+endl+placeMap[i].getPlaceName()+":"+endl+placeMap[i].getPlaceItemsDescp());
		
	}
	
	
	private Item shopItemNFA(String[] str, int position) {
		//Initialing variables
		
				//of Automata
				int state = 0;
				boolean emptySet = false;
				int FINALSTATE = 7;
				
				//Of item
				int nWords = str.length;
				int price = 0, times = 0,power = 0;
				String itemDescp = null,itemType = null,itemName = null,code =null;
				Item auxItem = null;
				
				//simulate AFN:
				do{
					switch (state){
					
						case 0: //Obtain itemType
							itemType = str[state];
							if (itemType.equals("garbage") || itemType.equals("fuel") || itemType.equals("codecard")) 
								state++;
							else
								emptySet = true;
							break;
							
						case 1: //Check order number
							if (str[state].matches(NATURAL_PATTERN) && Integer.parseInt(str[1])==position)
								state++;
							else
								emptySet = true;
							break;
							
						case 2: //Obtain item name
							itemName = str[state];
							state++;
							break;
							
						case 3: //Obtain item description
							itemDescp = str[state].replace('_',' ');
							state++;
							break;
							
						case 4: //For garbage & fuel obtain power, for code card obtain code
							switch (itemType){
								case "garbage":
									if (str[state].matches(INTEGER_PATTERN)){ //Other possible function for this matter: Miscellanous.isInteger(str[state])) {
										power = Integer.parseInt(str[state]);
										state+= 2; //state 5 is only for items of type fuel
									}
									else
										emptySet = true;
									break;
									
								case "fuel": 
									if (str[state].matches(INTEGER_PATTERN)){//) && nWords==7){
										power = Integer.parseInt(str[state]);
										state++; //go to state 5 to obtain times field
									}
									else
										emptySet = true;
									break;
									
								case "codecard":
									code = str[state];
									state+= 2; //state 5 is only for items of type fuel
									break;
								}
							break;
								
						case 5: //Obtain times for fuel items
							if (str[state].matches(NATURAL_PATTERN)){
								times = Integer.parseInt(str[state]);
								state++;
							}
							else
								emptySet = true;
							break;
							
						case 6: //get price
							if (str[nWords-1].matches(NATURAL_PATTERN)){
								price = Integer.parseInt(str[nWords-1]);
								state++;
							}
							else
								emptySet = true;
							break;
							
						case 7: //Create item
							switch(itemType){
								case "garbage": auxItem = new Garbage (itemName, itemDescp, power); auxItem.setPrice(price); break;
								case "fuel": auxItem = new Fuel (itemName,itemDescp,power,times); auxItem.setPrice(price); break;
								case "codecard": auxItem = new CodeCard (itemName,itemDescp,code); auxItem.setPrice(price); break;
							}
							emptySet=true; //equals to return
							break;
							
					}
				}while (!emptySet);
				
				//if the last state is final state it means that automata has recognized the item.
				if (state == FINALSTATE)
					return auxItem;
				
				else
					return null;
	}

	/**
=======
>>>>>>> june2013
	 * Simulates a nondeterministic-finite-automaton which one recognizes and process the place definition line
	 * @param str Words of the line to process
	 * @param position number of the place
	 * @return the places processed or <code>null</code> if it hasn't been able to process the strings.
	 */
	@SuppressWarnings("static-method")
	private Place placeNFA(String[] str, int position){
		//Initialing variables
		int state = 0;
		boolean emptySet = false;
		PlaceType placeType = null;
		String placeName = null, placeDescp = null;
		boolean iSS = false;
		Place auxPlace = null;
		
		//We simulate an AFN:
		do{
			switch (state){

				case 0:
					placeType = PlaceType.parsePlaceType(str[0]);//check place word
					if (placeType == PlaceType.NORMAL
						|| placeType == PlaceType.BUNKER
						|| placeType == PlaceType.TRENCH){
						state++;
					}
					else
						emptySet = true;
					break;
					
				case 1: //check number order
					if (str[1].matches(NATURAL_PATTERN) && Integer.parseInt(str[1])==position)
						state++;
					else
						emptySet = true;
					break;
					
				case 2: //obtain place name
					placeName = str[2]; 
					state++;
					break;
					
				case 3: //obtain place description
					placeDescp = str[3].replace('_', ' ');
					state++; 
					break;
					
				case 4: //obtain if the place has the space ship.
					if (str[4].equals("spaceShip") && placeType == PlaceType.NORMAL){ //Check also that only normal places can be space ship
						iSS = true; 
						state++;
					}
					else if (str[4].equals("noSpaceShip")){
						iSS = false;
						state++;
					}
					else
						emptySet = true;
					break;
						
				case 5: //create new place depending of place Type
					switch (placeType){
						case NORMAL: auxPlace = new Place(placeName,iSS,placeDescp); break;
						case BUNKER: auxPlace = new Bunker(placeName,placeDescp); break;
						case TRENCH: auxPlace = new Trench(placeName,placeDescp); break;
					}
					
					emptySet = true; //equals to return
					break;
			}
		}while (!emptySet);
		
		return auxPlace;
	}
	
	
	/**
	 * Simulates a nondeterministic-finite-automaton which one recognizes and process the street definition line
	 * @param str Words of the line to process
	 * @param position number of the street
	 * @return the street processed or <code>null</code> if it hasn't been able to process the strings.
	 */
	private Street streetNFA(String[] str, int position){
		//Initialing variables
		
		//of Automata
		int state = 0;
		boolean emptySet = false;
		int FINALSTATE = 9;
		
		//Of Street
		int placeIndex = 0;
		Place sourcePlace = null, targetPlace = null;
		Direction dir = null;
		boolean open = true;
		String code =null;
		Street auxStreet = null;
		
		//simulamos AFN:
		do{
			switch (state){
			
				case 0: //Check street word
					if (str[state].equals("street")) 
						state++;
					else
						emptySet = true;
					break;
					
				case 1: //check order number
					if (str[state].matches(NATURAL_PATTERN) && Integer.parseInt(str[1])==position)
						state++;
					else
						emptySet = true;
					break;
					
				case 2: case 5: //check place word
					//Both are same action, state 3 for source and state 6 for target place.
					
					if (str[state].equals("place"))
						state++;
					else
						emptySet = true;
					break;
					
				case 3: case 6: //obtain source/target place
					//Both are same action, state 3 for source and state 6 for target place.
					
					//Check if it's a number
					if (str[state].matches(NATURAL_PATTERN)){
						placeIndex = Integer.parseInt(str[state]);
						
						//check if the place is in the placeMap
						if (Miscellanous.intervalContains(0,placeMap.length-1,placeIndex)){
							
							//Assign to the correspondent place by the number of the state
							if (state == 3)
								sourcePlace = placeMap[placeIndex];
							
							else if (state == 6)
								targetPlace = placeMap[placeIndex];
						state++;
						}
						else
							emptySet = true;
					}
					else
						emptySet = true;
					break;
					
				case 4: //obtain direction
					dir = Direction.parseDirection(str[state]);
					if (dir != Direction.UNKNOWN)
						state++;
					else
						emptySet = true;
					break;
						
				case 7: //obtain open/closed property
					if (str[state].equals("open")){
						open = true;
						state = FINALSTATE; //We don't need any params more. 
					}

					else if (str[state].equals("closed")){
						open = false;
						state++; //state 8 is only for closed streets
					}
					else
						emptySet = true;
					break;
					
				case 8: //Obtain lock code

					if (str[state] != null) {
						code = str[state];
						state = FINALSTATE;
					}
					else
						emptySet = true;

					break;
					
				case 9: //Create new street
					if (open)
						auxStreet = new Street(sourcePlace,dir,targetPlace);
					else
						auxStreet = new Street(sourcePlace,dir,targetPlace,false,code);
					
					emptySet = true; //equals to return
					break;
			}
		}while (!emptySet);
		
		return auxStreet;
	}
	
	
	/**
	 * Simulates a nondeterministic-finite-automaton which one recognizes and process the street definition line
	 * @param str Words of the line to process
	 * @param position number of the street
	 * @return the street processed or <code>null</code> if it hasn't been able to process the strings.
	 */
	private Item itemNFA(String[] str, int position){
		//Initialing variables
		
		//of Automata
		int state = 0;
		boolean emptySet = false;
		int FINALSTATE = 11;
		
		//Of item
		int nWords = str.length;
		int placeIndex = 0;
		int times = 0,power = 0;
		String itemDescp = null,itemType = null,itemName = null,code =null;
		boolean activated = true; //only for bombs

		Item auxItem = null;
		
		//simulate AFN:
		do{
			switch (state){
			
				case 0: //Obtain itemType
					itemType = str[state];
					
					if (itemType.equals("garbage") 
						|| itemType.equals("fuel") 
						|| itemType.equals("codecard") 
						|| itemType.equals("bomb"))
							state++;

					else
						emptySet = true;
					break;
					
				case 1: //Check order number
					if (str[state].matches(NATURAL_PATTERN) && Integer.parseInt(str[1])==position)
						state++;
					else
						emptySet = true;
					break;
					
				case 2: //Obtain item name
					itemName = str[state];
					state++;
					break;
					
				case 3: //Obtain item description
					itemDescp = str[state].replace('_',' ');
					state++;
					break;
					
				case 4: //For garbage & fuel obtain power, for code card obtain code
					switch (itemType){
						case "garbage":
							if (str[state].matches(INTEGER_PATTERN)){ //Other possible function for this matter: Miscellanous.isInteger(str[state])) {
								power = Integer.parseInt(str[state]);
								state+= 3; //state 5 is only for items of type fuel or bomb
							}
							else
								emptySet = true;
							break;
							
						case "fuel": 
							if (str[state].matches(INTEGER_PATTERN)){
								power = Integer.parseInt(str[state]);
								state++; //go to state 5 to obtain times field
							}
							else
								emptySet = true;
							break;
							
						case "codecard":
							code = str[state];
							state+= 3; //state 5 is only for items of type fuel or bomb
							break;
						
						case "bomb":
							if (str[state].matches(INTEGER_PATTERN)){
								power = Integer.parseInt(str[state]);
								state++; //go to state 5 to obtain points field
							}
							else
								emptySet = true;
							break;
						
						}
					break;
						
				case 5: //Obtain times for fuel items or points for bombs
					if (str[state] != null && str[state].matches(NATURAL_PATTERN)){
						times = Integer.parseInt(str[state]);
						if (itemType.equals("bomb"))
							state++;
						else if (itemType.equals("fuel"))
							state+=2;
						else
							emptySet = true;
					}
					else
						emptySet = true;
					break;
					
				case 6: //Obtain activated or deactivated value for bombs
					if (str[state] != null && str[state].equals("on")) {
						activated = true;
						state++;
					}
					else if (str[state].equals("off")) {
						activated = false;
						state++;
					}
					else
						emptySet = true;
					break;

				case 7: //Create item
					switch(itemType){
						case "garbage": auxItem = new Garbage (itemName, itemDescp, power); break;
						case "fuel": auxItem = new Fuel (itemName,itemDescp,power,times); break;
						case "codecard": auxItem = new CodeCard (itemName,itemDescp,code); break;
						case "bomb": auxItem = new Bomb (itemName, itemDescp, power, times, activated); break;
					}
					state++;
					break;
					
				case 8: //Check place word
					if (str[nWords-2].equals("place")) 
						state++;
					else
						emptySet = true;
					break;
					
				case 9: //check if we can add item to place
					if (str[nWords-1].matches(NATURAL_PATTERN)){
						placeIndex = Integer.parseInt(str[nWords-1]);
						if (Miscellanous.intervalContains(0,placeMap.length-1,placeIndex))
							state++;
						else
							emptySet = true;
					}
					else
						emptySet = true;
					break;
				
				case 10: //add effectively item to place
					Place p = placeMap[placeIndex];
					if (!p.isSpecialPlace()){
						p.addItem(auxItem);
						state++;
					}
					else{
						auxItem = null;
						emptySet=true; //equals to return
					}
					break;
					
				case 11: //All done well
					emptySet = true;
					break;
			}
		}while (!emptySet);
		
		//if the last state is final state it means that automata has recognized the item.
		if (state == FINALSTATE)
			return auxItem;
		
		else
			return null;
	}
			
	
	/**
	 * Method for close the text file assigned to the scanner.
	 * @param file inputStream of the text file to close
	 * @throws IOException If the file is unreachable or can't be closed
	 */
	private void closeCity(InputStream file) throws IOException{
			if ( file != null )
				sc.close();
			}

	/**
	 * Load a the text of a City in the string testing file.<br/>
	 * Only for testing purposes
	 * @return true if all was well.
	 */
	/*
	private boolean loadString() {
			
			testingFile = "BeginCity" + endl +
					"BeginPlaces" + endl +
					"place 0 Sol \"gh hyk\" noSpaceShip" + endl +
					"place 1 Callao \"In this square you can find a code card\" noSpaceShip" + endl +
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
	*/

	/**
	 * Only for testing & debugging this class
	 * @param args null in this case
	 */
	/*
	public static void main(String[] args){
		City city = new City();
		CityLoaderFromTxtFile clftf = new CityLoaderFromTxtFile();
		if (clftf.loadString()){
			/*clftf.file = clftf.file.replace("street 0 place 0 south place 1 open",
					// Change first door id
			"street 11 place 0 south place 1 open");
			*/
	/*
			try {
				InputStream is = new ByteArrayInputStream(clftf.testingFile.getBytes());
				city = (clftf.loadCity(is));
			}
			catch (WrongCityFormatException e) {
				System.err.print(e);
			}
			catch (Exception e) {
				System.err.print("ERROR: loadCity throws an exception different from WrongCityFormatException: "+e);
				e.printStackTrace();
			}
			System.out.print(city);
		}
		else
				System.err.print("something was wrong");
	}
	*/

}


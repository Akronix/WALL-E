package tp.pr5;

// java packages:
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//Others:
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

//local packages:
import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.items.Bomb;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;

import tp.pr5.userInterfaces.InterfaceModesAbstractFactory;
import tp.pr5.userInterfaces.InterfaceModesEnum;

/**
 * Control simulator of a robot, called WALL·E, who have to travel through a city.<br/>
 * Its mission is recollect the greatest amount of material for recycling. But be careful! WALL·E has a limited amount of power
 * and It has to reach its spaceship before he lose all power.
 * When WALL·E arrives at the place which is the spaceship, WALL·E will disconnect and the simulator will have finished.
 * 
 * @author Abel Serrano Juste
 * @since February 2013
 * @version 4.1 Basic version with undo and little tunings.<br/>
 * Differences with other versions:
 * <ol>
 * <li>All those the statement requires.</li>
 * <li>Pictures added to JDialog when WALL·E dies, reach spaceship or user request quit </li>
 * <li>Log is update in each instruction</li>
 * <li>Perform undo instruction, remembering only the last instruction and performing redo when you undo twice</li>
 * </ol>
 */
public class Main {
	
	//Game references
	private static RobotEngine robotEngine;
	private static City city;
	
	//Interface launcher
	private static InterfaceModesAbstractFactory currentInterfaceFactory;
	private static InterfaceModesEnum interfaceChosen;
	
	//Commons-cli
	private static Options options;
	private static InputStream inputFile;
	
	private static String directoryMap;
	
	//Messages:
	public static final String WRONG_INTERFACE_MESSAGE = "Wrong type of interface"+Interpreter.LINE_SEPARATOR;
	public static final String MAP_NOT_SPECIFIED_MESSAGE = "Map file not specified"+Interpreter.LINE_SEPARATOR
			+ "->Loading default local testing map" + Interpreter.LINE_SEPARATOR;
	public static final String INTERFACE_NOT_SPECIFIED_MESSAGE = "Interface not specified"+Interpreter.LINE_SEPARATOR
			+ "->Loading default user interface: both" + Interpreter.LINE_SEPARATOR;
	
	
	//// Commons-cli \\\\
	
	/**
	 * Create the args syntax for running the app 
	 */
	private static void createOptions() {
		options = new Options();
		
		//Help option:
		options.addOption("h", "help", false, "Shows this help message");
		
		//interface option:
		Option interfaceOption = new Option("i", "interface", false, "The type of interface : console, swing or both");
		interfaceOption.setArgs(1);
		interfaceOption.setArgName("type");
		options.addOption(interfaceOption);
		
		//map option:
		Option mapOption = new Option("m", "map", false, "File with the description of the city");
		mapOption.setArgs(1);
		mapOption.setArgName("mapfile");
		options.addOption(mapOption);
		
	}
	
	/**
	 * Process the app call, check if it's correct, save what interface user has been chosen and make an inputStream of the map arg
	 * @param args args for the app to process
	 */
	private static void processArguments(String[] args) {
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args); //Try to parse the program arguments according to the options field
		} catch (ParseException exp) {
			System.err.print( "Parsing failed.  Reason: " + exp.getMessage()+Interpreter.LINE_SEPARATOR);
			printUsageMessage();
			System.exit(1);
		}
		
		String interfaceArg = null;
		
		if (cmd.hasOption('h')) {
			System.out.print("Execute this assignment with these parameters:"+Interpreter.LINE_SEPARATOR);
			printUsageMessage();
			System.exit(0);
		}
		
		//Check if input has interface field
		if (cmd.hasOption('i')) {
			interfaceArg = cmd.getOptionValue('i');
			interfaceChosen = InterfaceModesEnum.parse(interfaceArg);
					
			if (interfaceChosen == null) {
				System.err.print(WRONG_INTERFACE_MESSAGE);
				System.exit(3);
			}
		}
		else {
			System.out.print(INTERFACE_NOT_SPECIFIED_MESSAGE);
			interfaceChosen = InterfaceModesEnum.both;
		}
			
		//Check if input has the map field
		if (cmd.hasOption('m')){
			directoryMap = cmd.getOptionValue('m');
			try{
				inputFile = new FileInputStream (directoryMap);
			}
			//If file hasn't been found
			catch (FileNotFoundException e){
				System.err.print("Error reading the map file: "+directoryMap+" (No existe el fichero o el directorio)" + Interpreter.LINE_SEPARATOR);
				System.exit(2);						
			}
		}	
		else {
			System.out.print(MAP_NOT_SPECIFIED_MESSAGE);
			inputFile = null;
		}
			
	}
	
	
	/**
	 * Print the message of correct usage for calling the app
	 */
	private static void printUsageMessage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("tp.pr4.Main", options, true);
	}


	//// Loading map \\\\
	
	/**
	 * Load the city data from the file
	 * @param fileToLoad 
	 */
	private static void loadMap(InputStream fileToLoad) {
		CityLoaderFromTxtFile clftf;
		try{
			clftf = new CityLoaderFromTxtFile();
			city = clftf.loadCity(fileToLoad);
		}
		//If security conflict or bad format of the input file
		catch (IOException ioe){
			System.err.print("Error reading the map file: "+directoryMap+" "
					+ioe.toString());
			System.exit(2);
		}
	}
	
	//// main \\\\
	
	/**
	 * Main for this app
	 * @param args Arguments for the app
	 */
	public static void main(String[] args) {
		createOptions();
		
		interfaceChosen = null;
		processArguments(args);

		//Loading map
		if (inputFile != null)
			loadMap(inputFile);
		else
			loadLocalMap();
		
		System.out.print("------------------------------------------"+Interpreter.LINE_SEPARATOR);
		System.out.print("*********** WELCOME TO WALL-E by Akronix ***********"+Interpreter.LINE_SEPARATOR);
		System.out.print("------------------------------------------"+Interpreter.LINE_SEPARATOR);
		
		//Initializing game engine
		robotEngine = new RobotEngine (city,city.getInitialPlace(),Direction.NORTH);
		
		currentInterfaceFactory = InterfaceModesAbstractFactory.configureInterface(interfaceChosen,robotEngine);
		currentInterfaceFactory.runInterface();
			
	}

	
	
	//////////Default local map --for testing purposes \\\\\\\\\\\\
	
	private static void loadLocalMap(){
		Shop localShop = createShop();
		Place [] localPlaceMap = Main.createPlaces();
		Street[] localStreetMap = createStreets(localPlaceMap);
		
		city = new City(localStreetMap,localPlaceMap,localShop);
	}
	
	private static Shop createShop() {
		Shop shop = new Shop();
		shop.loadDefaultStock();
		
		return shop;
	}
	
	/**
	 * Set some default places for a sample city
	 * @return A <code>Place[]</code> array 
	 */
	private static Place[] createPlaces(){
	//Crea todos lugares, les añade los objetos que haya en ellos y los devuelve en un array de lugares
		Place [] places = new Place[4];
		places[0] = new Place("ENTRY",false,"hello");
		places[0].addItem(new CodeCard ("beatiful-key","This opens everything","1"));
		places[1] = new Place("DINER",false,"food foooood");
		places[2] = new Place("NAVE",true,"Job done! Goodbyyee");
		places[3] = new Place("Place", false,"Just a place");
		places[0].addItem(new Garbage("newspapers","news on sports",50));
        places[0].addItem(new Fuel("grapes","Just some healthy fruits",3,10));
        places[0].addItem(new Fuel("Coal","Be careful with this fuel because it is extremely contaminant",-60,1));
        places[0].addItem(new CodeCard("Spaceballs-card","This is the kind of combination an idiot would have on his luggage","12345"));
        places[0].addItem(new Garbage ("newspapers","Titular says: 'Akronix is the best!' OMG!!",5));
        places[0].addItem(new Fuel ("grapes","Just some healthy fruits",20,2));
        places[0].addItem(new Bomb ("stone","It's just a harmless stone...or not?",0,50));
		return places;
	}
	
	/**
	 * Set the connections between places creating the city streets
	 * @param places Array with all the places of the city
	 * @return An <code>Street[]</code> array 
	 */
	private static Street[] createStreets(Place[] places){
		Street[] streets = new Street[3];
		streets[0] = new Street(places[0], Direction.SOUTH, places[1]);
		streets[1] = new Street(places[0], Direction.EAST, places[2],false,"1");
		streets[2] = new Street(places[0], Direction.NORTH, places[3]);
		return streets;
		
	}	
	
}

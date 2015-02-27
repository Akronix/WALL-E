package tp.pr5;

//java packages:
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

//other packages:
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.SimpleGraph;

/**
 * This class represents the city where the robot is wandering.<br/>
 * It contains information about the streets and the places in the city
 * @author Abel Serrano Juste
 */
public class City {
	
	/*
	 * Attributes of the city
	 */
	private AbstractBaseGraph<Place,Street> map; //Graph which represents the map of the city
	private Place initialPlace; //It is used in other classes to get the spawn place for WALLE
	private Shop cityShop; // It is the shop for this map
	
	/**
	 * Default constructor. Needed for testing purposes
	 */
	public City(){
		this.map = new SimpleGraph<Place,Street>(Street.class); 
		//Constructor need a class on which to base creating edges (Street), although we won't never use this way for adding edges.
		this.cityShop = new Shop(); //default constructor for the shop
	}
	
	/**
	 * Creates a city using an array of streets
	 * @param cityMap Array of streets which describes the city map.
	 */
	public City (Street[] cityMap){
		this();

		Street auxStreet;
		Place auxSourcePlace, auxTargetPlace;
		
		//Define initial place as the source place of the first street:
		initialPlace = cityMap[0].getSourcePlace();
		
		//We process all the array of Streets
		for (int streetIndex = 0; streetIndex<cityMap.length ;streetIndex++){
			
			//extract street
			auxStreet = cityMap[streetIndex];
			
			//extract source place and add it to the map (It doesn't allow duplicates)
			auxSourcePlace = auxStreet.getSourcePlace();
			this.map.addVertex(auxSourcePlace);
			
			//extract target place and add it to the map (It doesn't allow duplicates)
			auxTargetPlace = auxStreet.getTargetPlace();
			this.map.addVertex(auxTargetPlace);
			
			//Finally, add the street with their touching places
			this.map.addEdge(auxSourcePlace, auxTargetPlace, auxStreet);
			
			// Uncomment for looking if the city is saving well the streets:
			// System.out.println(map.getEdge(auxSourcePlace, auxTargetPlace));
		}
	}
	
	/**
	 * Auxiliary way to create new City, this way from two arrays of Street and of Place.<br/>
	 * All source/target place on streets definition have to be present on placeMap, otherwise it throws a runtime exception.
	 * @param cityMap Array of streets which describes the road map.
	 * @param placeMap Array of Places which describes the sites where wall·e can stop.
	 */
	public City (Street[] cityMap, Place[] placeMap){
		this();

		Street auxStreet;
		Place auxPlace, auxSourcePlace, auxTargetPlace;
		
		//Define initial place as the first place of the place map array:
		initialPlace = placeMap[0];
		
		//First, we process all the array of Places
		for (int placeIndex = 0; placeIndex<placeMap.length; placeIndex++){

			//obtain place
			auxPlace = placeMap[placeIndex];
			
			//add the place to the map
			this.map.addVertex(auxPlace);
			
			// Uncomment for looking if the city is saving well the places:
			// System.out.println(auxPlace);
		}
		
		//We process all the array of Streets
		for (int streetIndex = 0; streetIndex<cityMap.length; streetIndex++){
			//extract street
			auxStreet = cityMap[streetIndex];
			
			//extract source place
			auxSourcePlace = auxStreet.getSourcePlace();
			
			//extract target place
			auxTargetPlace = auxStreet.getTargetPlace();
			
			//Finally, add the street with their touching places. 
			this.map.addEdge(auxSourcePlace, auxTargetPlace, auxStreet);
			
			// Uncomment for looking if the city is saving well the streets:
			// System.out.println(map.getEdge(auxSourcePlace, auxTargetPlace));
		}
	}
	
	public City(Street[] streetMap, Shop stockItems) {
		this(streetMap);
		this.cityShop = stockItems;
	}
	
	public City(Street[] streetMap, Place[] placeMap, Shop stockItems) {
		this(streetMap,placeMap);
		this.cityShop = stockItems;
	}

	/**
	 * Looks for the street that starts from the given place in the given direction.
	 * @param currentPlace The place where to look for the street
	 * @param currentHeading The direction to look for the street
	 * @return The street that stars from the given place in the given direction. It returns <code>null</code> if there is not any street in this direction from the given place
	 */
	public Street lookForStreet(Place currentPlace, Direction currentHeading){
		boolean found = false;
		Street auxStreet = null;
		Set <Street> touchingStreets = new HashSet<Street>();
		
		//First, check if the map is not empty and if contains the vertex for which we are looking
		if (!map.vertexSet().isEmpty() && map.containsVertex(currentPlace)){
			
			//Obtain all the streets adjacent to the given place:
			touchingStreets = this.map.edgesOf(currentPlace);
			
			//Create and initialize iterator
			Iterator <Street> streetIterator = touchingStreets.iterator();
			
			//Now, we look for the street with the same direction from the current place.
			while (streetIterator.hasNext() && !found){
				auxStreet = streetIterator.next();
				found = Direction.equalsDirection(auxStreet.getDirection(currentPlace),currentHeading);
			}
			
		}
		
		/* auxStreet could have one street, the last one next() before to finish 'while'. 
		 * That's why we've to check if we found the street for knowing if auxStreet it's correct output.
		 */
		if (!found)
			auxStreet = null; 
		
		return auxStreet;
		
	}
	
	/**
	 * This method returns a set up to 4 streets which are touching the place given.<br/>
	 * It's interesting to use for advancing the next possible streets
	 * @param p place to looking for touching streets
	 * @return Set up to 4 streets connected to the Place p.
	 */
	public Set<Street> getTouchingStreetsTo(Place p){
		
		Set<Street> touchingStreets;
		
		//First, check if the map is not empty and if contains the vertex for which we are looking for
				if (!map.vertexSet().isEmpty() && map.containsVertex(p)){
					
					//Return all the streets adjacent to the given place:
					touchingStreets = this.map.edgesOf(p);
				}
				else
					touchingStreets = null;
				
		return touchingStreets;
	}

	/**
	 * Returns the place where the robot will start the simulation
	 * @return The initial place
	 */
	public Place getInitialPlace(){
		return this.initialPlace;
	}
	
	@Override
	public String toString(){
		StringBuffer acumString = new StringBuffer(""); //Accumulator of the output
		Set <Place> allPlaces = new HashSet <Place>(); //Places container
		allPlaces = this.map.vertexSet();
		
		//'for each' for obtain all the string representation places and compress it in one only string.
		for (Place auxPlace : allPlaces){
			acumString.append(auxPlace.toString()+"*****"+Interpreter.LINE_SEPARATOR);
		}
		
		return acumString.toString();
	}

	public Shop getShop() {
		return this.cityShop;
	}
	
}

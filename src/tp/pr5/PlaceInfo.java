package tp.pr5;

/**
 * PlaceInfo defines a non-modifiable interface over a Place. It is employed by the classes that need to access the information contained in the place but that cannot modify the place itself.
 * @author Abel Serrano Juste
 */
public interface PlaceInfo {
	
	/**
	 * Return the place name
	 * @return The place name
	 */
	public abstract String getPlaceName();
	
	/**
	 * Return the place description
	 * @return the place description
	 */
	public abstract String getDescp();
	
	/**
	 * Return the items contained in this place
	 * @return String with the corresponding place message and a list of the items id contained in this place
	 */
	public abstract String getPlaceItemsDescp();
	
	@Override
	public abstract String toString(); 
	/*It's already implemented by Object,
	but I put it here for standing out I want to use toString() from the view classes, so it has to be overridden.*/

}

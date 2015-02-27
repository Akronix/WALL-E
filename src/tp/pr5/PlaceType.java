/**
 * 
 */
package tp.pr5;

/**
 * @author Abel Serrano Juste
 *
 */
public enum PlaceType {
	
	NORMAL
 {
		@Override
		public double getProtection() {
			return 0;
		}
	},
	TRENCH
 {
		@Override
		public double getProtection() {
			return 0.50;
		}
	},
	BUNKER
 {
		@Override
		public double getProtection() {
			return 1;
		}
	};
	
	/**
	 * This method convert a string to a PlaceType enum value.
	 * @param stringDir String which represent a place type
	 * @return Direction PlaceType.enum value which corresponds with the input string. If it isn't recognized, it returns <code>UNKWNOWN</code>
	 */
	public static PlaceType parsePlaceType (String stringDir){
		switch (stringDir.toUpperCase()){
			case "BUNKER": return PlaceType.BUNKER;
			case "TRENCH": return PlaceType.TRENCH;
			case "PLACE": return PlaceType.NORMAL;
			default: return null;
		}
	}
	
	/**
	 * protection for this place type
	 * @return protection
	*/	
	public abstract double getProtection();

}

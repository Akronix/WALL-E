/**
 * 
 */
package tp.pr5;

/**
 * @author Abel Serrano Juste
 *
 */
public enum StormType {
	
	acidrain
 {
		@Override
		public double getDamage() {
			// TODO Auto-generated method stub
			return 25;
		}
	},	sandStorm
 {
		@Override
		public double getDamage() {
			// TODO Auto-generated method stub
			return 50;
		}
	},	tornado
	
 {
		@Override
		public double getDamage() {
			// TODO Auto-generated method stub
			return 100;
		}
	};
	
	/**
	 * This method convert a string to a StormType enum value.
	 * @param stringDir String which represent a storm type
	 * @return Direction StormType.enum value which corresponds with the input string. If it isn't recognized, it returns <code>UNKWNOWN</code>
	 */
	public static StormType parseStormType (String stringDir){
		switch (stringDir.toUpperCase()){
			case "TORNADO": return StormType.tornado;
			case "SANDSTORM": return StormType.sandStorm;
			case "ACIDRAIN": return StormType.acidrain;
			default: return null;
		}
	}
	
	/**
	 * damage for this storm type
	 * @return damage
	*/	
	public abstract double getDamage();

}

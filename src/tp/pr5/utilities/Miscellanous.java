/**
 * Created on Mar 19, 2013
 */
package tp.pr5.utilities;

/**
 * Class of various utilities methods
 * @author Abel Serrano Juste
 */
public class Miscellanous {

	/**
	 * Check if an integer is between the low and high numbers.
	 * @param low left-extreme of the interval
	 * @param high right-extreme of the interval
	 * @param n number to check
	 * @return true if n is contained on the interval, <false> oc
	 */
	public static boolean intervalContains (int low, int high, int n) {
	    return n >= low && n <= high;
	}
	
	/**
	 * Check if an string represents an integer number.
	 * @param integer string to be checked
	 * @return true if it can known as integer, false oc.
	 */
	public static boolean isInteger(String integer) {
		
		if(integer==null || integer.length()==0)
			return false;
		
		else {
			int output = 0;
			boolean negative = integer.charAt(0) == '-';
			int i = negative? 1: 0;
			int multiplier = 1;
			Character number;
			
			for (;i<integer.length(); i++) {
				number = integer.charAt(i);
				if (Character.isDigit(number)) {
					output = integer.charAt(i) + output * multiplier;
					multiplier *= 10;
				}
				else
					return false;
			}
			
			if (negative)
				output = -output;
			
			return true;
		}
	}
	
}

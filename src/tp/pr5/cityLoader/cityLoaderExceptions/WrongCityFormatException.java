package tp.pr5.cityLoader.cityLoaderExceptions;

import java.io.IOException;

/**
 * Class for exceptions of loading a city from a txt file.
 * @author Abel Serrano Juste
 */
public class WrongCityFormatException extends IOException{

	/**
	 * Constructor without parameters (no message is given)
	 */
	public WrongCityFormatException(){
		super();
	}
	
	/**
	 * The exception thrown is created with a problem message.
	 * @param arg0 User-friendly string that explains the error.
	 */
	public WrongCityFormatException(String arg0){
		super(arg0);
	}

	/**
	 * Constructor to create the exception with a nested cause.
	 * @param arg0 Nested exception.
	 */

	public WrongCityFormatException(Throwable arg0){
		super(arg0);
	}
	
	/**
	 * Constructor to create the exception with a nested cause and an error message.
	 * @param arg0 User-friendly string that explains the error.
	 * @param arg1 Nested exception.
	 */
	public WrongCityFormatException(String arg0, Throwable arg1){
		super(arg0,arg1);
	}
}

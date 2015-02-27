package tp.pr5.instructions.exceptions;

/**
 * Exception thrown when a string cannot be parsed to create a command. 
 * The exception has a user-friendly message with an explanation about the error. 
 * This class has many different constructors, one for every constructor of the base class.
 * @author Abel Serrano Juste
 */
public class WrongInstructionFormatException extends Exception{
	
	/**
	 * Constructor without parameters (no message is given)
	 */
	public WrongInstructionFormatException(){
		super();
	}
	
	/**
	 * The exception thrown is created with a problem message.
	 * @param arg0 User-friendly string that explains the error.
	 */
	public WrongInstructionFormatException(String arg0){
		super(arg0);
	}

	/**
	 * Constructor to create the exception with a nested cause.
	 * @param arg0 Nested exception.
	 */

	public WrongInstructionFormatException(Throwable arg0){
		super(arg0);
	}
	
	/**
	 * Constructor to create the exception with a nested cause and an error message.
	 * @param arg0 User-friendly string that explains the error.
	 * @param arg1 Nested exception.
	 */
	public WrongInstructionFormatException(String arg0, Throwable arg1){
		super(arg0,arg1);
	}

}

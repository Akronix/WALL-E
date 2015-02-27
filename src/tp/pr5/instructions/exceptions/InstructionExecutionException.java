package tp.pr5.instructions.exceptions;

/**Exception thrown when a instruction execution fails. The exception has a user-friendly message with an explanation about the error. 
 * This class has many different constructors, one for every constructor of the base class.
 * @author Abel Serrano Juste
 */
public class InstructionExecutionException extends Exception{
	
	/**
	 * Constructor without parameters (no message is given)
	 */
	public InstructionExecutionException(){
		super();
	}
	
	/**
	 * The exception thrown is created with a problem message.
	 * @param arg0 User-friendly string that explains the error.
	 */
	public InstructionExecutionException(String arg0){
		super(arg0);
	}

	/**
	 * Constructor to create the exception with a nested cause.
	 * @param arg0 Nested exception.
	 */

	public InstructionExecutionException(Throwable arg0){
		super(arg0);
	}
	
	/**
	 * Constructor to create the exception with a nested cause and an error message.
	 * @param arg0 User-friendly string that explains the error.
	 * @param arg1 Nested exception.
	 */
	public InstructionExecutionException(String arg0, Throwable arg1){
		super(arg0,arg1);
	}

}

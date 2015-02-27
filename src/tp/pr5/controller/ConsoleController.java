/**
 * Created on May 7, 2013
 */
package tp.pr5.controller;

//java packages:
import java.util.Scanner;
import tp.pr5.RobotEngine;
//local packages:
import tp.pr5.view.console.ConsoleView;

/**
 * The controller employed when the application is configured as a console application.<br/>
 * It contains the simulation loop that executes the instructions written by the user on the console.<br/>
 * It handles and process the input from the keyboard by the user.<br/>
 * This class is only visible to package level.
 * @author Abel Serrano Juste
 */
public class ConsoleController extends Controller {

	private Scanner keyboard; //input reader
	@SuppressWarnings("unused")
	private ConsoleView view;

	/**
	 * Constructor for console controller
	 * @param model the model to associate this controller to it.
	 * @param view the view to associate this controller to it.
	 */
	public ConsoleController(RobotEngine model, ConsoleView view) {
		super(model);
		
		this.keyboard = new Scanner(System.in);
		this.view = view;
	}
	
	@Override
	/**
	 * It launch the console prompt and command to process the instructions introduced by the user.
	 */
	public void startController() {
	
	//Initialize variables
	String line;
				
	//Process instruction and execute them until robot reaches SpaceShip or runs out of energy
		do {
			line = userInterfacePrompt();
		}while (processInstruction(line));
	}
	
	/**
	 * Method for console prompt. It shows the head of each command (WALL·E) and read the user input
	 * @return The input introduced by the user via keyboard.
	 */
	private String userInterfacePrompt(){
		System.out.print("WALL·E> ");
		return keyboard.nextLine();
	}
	
	/**
	 * Close the input reader before finalize this object<br/>
	 * @throws Throwable Actually nothing. 
	 * The only possibility exception raised by this method it's from super implementation of finalize(), i.e. Object.
	 */
	@Override
	public void finalize() throws Throwable {
		keyboard.close();
		super.finalize();
	}
}

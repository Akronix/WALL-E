package tp.pr5.userInterfaces;

/**
 * Enum of the possible interfaces for the app
 * @author Abel Serrano Juste
 */
public enum InterfaceModesEnum { 
	console, swing, both, multiple;
	
	/**
	 * This method convert a string representation of one interface mode to a InterfaceModes enum value.
	 * @param interfaceModeStr String which represent a interface mode for this app
	 * @return The enum mode for the input string, <code>null</code> if it's not recognized as valid argument.
	 */
	public static InterfaceModesEnum parse (String interfaceModeStr){
		switch (interfaceModeStr.toLowerCase()){
			case "console": return InterfaceModesEnum.console;
			case "swing": return InterfaceModesEnum.swing;
			case "both": return InterfaceModesEnum.both;
			case "multiple": return InterfaceModesEnum.multiple;
			default: return null;
		}
	};
}

package gui.utils;

import java.util.ResourceBundle;

public class TextView {

	private static final ResourceBundle TEXT_BUNDLE = ResourceBundle.getBundle("texts");
	
	public static String get(String textID) {
		return TEXT_BUNDLE.getString(textID);
	}
	
	public static String makeFirstLetterUpper(String input) {
		if (input.length() > 1) {
			return input.substring(0, 1).toUpperCase() + input.substring(1);
		} else {
			return input;
		}
		
	}
}

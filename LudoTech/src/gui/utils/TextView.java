package gui.utils;

import java.util.ResourceBundle;

public class TextView {

	private static final ResourceBundle TEXT_BUNDLE = ResourceBundle.getBundle("texts");
	
	public static String get(String textID) {
		return TEXT_BUNDLE.getString(textID);
	}
}

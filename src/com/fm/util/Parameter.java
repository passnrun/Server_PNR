package com.fm.util;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Parameter {


	private static final String BUNDLE_NAME = "config";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Parameter() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
	public static int getInt(String key) {
		String str = getString(key);
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}
}



package com.fm.util;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Parameter {


	private static final String BUNDLE_NAME = "config";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Parameter() {
	}

	public static void main(String[] args) {
		System.out.println(getString("news.gameresult.body", new String[]{"A", "B"}));
	}
	public static String getString(String key, String[] params) {
		try {
			String val = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(val, (Object[])params);
		} catch (MissingResourceException e) {
			return null;
		}
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



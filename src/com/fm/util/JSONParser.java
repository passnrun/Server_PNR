package com.fm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
	
	public static Map<String, Object> parse(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean began = false;
		boolean isKey = true;
		boolean isList = false;
		String currentKey = "";
		String currentValue = "";
		List currentList = new ArrayList();
		int numOfBrankets = 0;
		boolean hasObject = false;
		
		for (int i = 0; i < msg.length(); i++) {
			// if the object not began yet, check for the enterance
			if (!began) {
				if (msg.charAt(i) == '{')
					began = true;
			} else {
				if (isKey) {
					if (msg.charAt(i) == ':')
						isKey = false;
					else
						currentKey += msg.charAt(i);
				} else {
					if (numOfBrankets > 0) {
						currentValue += msg.charAt(i);
					} else if (numOfBrankets == 0 && msg.charAt(i) == '{'){
						currentValue += msg.charAt(i);
					} else if (msg.charAt(i) == '['){
						isList = true;
						currentList = new ArrayList();
					} else if (isList && msg.charAt(i) == ']'){
						isList = false;
						map.put(strip(currentKey), currentList);
						currentKey = "";
					} else if (msg.charAt(i) == ',' || (numOfBrankets == 0 && msg.charAt(i) == '}')) {
						if (!isList){
							isKey = true;
							map.put(strip(currentKey), hasObject? parse(currentValue):strip(currentValue));
							currentKey = "";
						}
						currentValue = "";
						hasObject = false;
					} else
						currentValue += msg.charAt(i);

					if (msg.charAt(i) == '{')
						numOfBrankets++;
					else if (msg.charAt(i) == '}'){
						numOfBrankets--;
						if (isList){
							currentList.add(parse(currentValue));
							currentValue = "";
						}
					}
					
					if (numOfBrankets > 0)
						hasObject = true;
				}
			}
		}

		return map;
	}
	
	private static String strip(String val){
		if (val == null)
			return "";
		String[] vals = val.split("\"");
		if (vals.length>1)
			return vals[1].trim();
		return val.trim();
	}
	
}

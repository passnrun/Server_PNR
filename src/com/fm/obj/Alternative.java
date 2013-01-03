package com.fm.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alternative {
	public Object value;
	public int probability;
	
	public Alternative(Object v, int p) {
		value = v;
		probability = p;
	}
	
	public static Object select(List<Alternative> list){
		if (list == null || list.size() == 0)
			return null;
		return select(list.toArray());
	}

	private static Object select(Object[] list) {
		List<Object> objs = new ArrayList<Object>();
		int total = 0;
		for (int i = 0; i < list.length; i++) {
			Alternative alternative = (Alternative)list[i];
			for (int j = 0; j < alternative.probability; j++) {
				objs.add(alternative.value);
			}
			total+=alternative.probability;
		}
		int selected = (new Random()).nextInt(total);
		return objs.get(selected);
	}
}

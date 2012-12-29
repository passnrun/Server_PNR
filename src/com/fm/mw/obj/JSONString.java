package com.fm.mw.obj;

import com.fm.mw.JSON;

public class JSONString implements JSON{
	private String v;
	
	public JSONString(String str) {
		v = str;
	}
	
	@Override
	public String toJSON() {
		return "\""+v+"\"";
	}
}

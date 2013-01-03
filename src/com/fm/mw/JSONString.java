package com.fm.mw;

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

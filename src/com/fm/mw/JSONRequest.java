package com.fm.mw;

public class JSONRequest {
	private String service;
	private Object data;
	
	public JSONRequest(String s, Object d){
		service = s;
		data = d;
	}

	public String getService() {
		return service;
	}

	public Object getData() {
		return data;
	}
}

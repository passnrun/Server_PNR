package com.fm.mw;

public class JSONResponse implements JSON{
	public static final int ERROR_JSONFORMAT 			= -1;
	public static final int ERROR_DATEFORMAT 		= -2;
	public static final int ERROR_INVALID_SERVICE 	= -3;
	public static final int ERROR_NO_TEAM_FOUND 	= -11;
	
	private int result;
	private JSON data;
	
	public JSONResponse(int r, JSON d) {
		result = r;
		data = d;
	}

	@Override
	public String toJSON() {
		return "{ \"result\" : "+result+", \"data\" : "+data.toJSON()+" }";
	}
	
	
	
	
}

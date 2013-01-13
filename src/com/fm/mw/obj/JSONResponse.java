package com.fm.mw.obj;

import com.fm.mw.JSON;

public class JSONResponse implements JSON{
	public static final int ERROR_JSONFORMAT 		= -1;
	public static final int ERROR_DATEFORMAT 		= -2;
	public static final int ERROR_INVALID_SERVICE 	= -3;
	//Register Service
	public static final int ERROR_NO_TEAM_FOUND 	= -11;
	//Sync
	public static final int ERROR_NO_MANAGER_FOUND 	= -21;
	public static final int ERROR_NO_GAME_FOUND 	= -22;
	public static final int ERROR_NO_PLAYER_FOUND 	= -23;
	public static final int ERROR_NO_NEWS_FOUND 	= -24;
	
	private int result;
	private JSON data;
	
	public JSONResponse(int r, JSON d) {
		result = r;
		data = d;
	}

	@Override
	public String toJSON() {
		String str = "{ \"result\" : "+result+", \"data\" : "+data.toJSON()+" }";
		System.out.println("JSONResponse : " + str);
		return str;
	}
	
	
	
	
}

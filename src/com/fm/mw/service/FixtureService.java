package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dao.GameDAO;
import com.fm.mw.JSON;
import com.fm.mw.obj.JSGame;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class FixtureService {
	private static Logger logger = Logger.getLogger(FixtureService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("fixture request received :" + map);
		GameDAO dao = new GameDAO();
		List<Game> gameList = dao.getFixture(Integer.parseInt((String)map.get("leagueId")), Integer.parseInt((String)map.get("seasonId")));
		if (gameList == null || gameList.size() == 0)
			return new JSONResponse(JSONResponse.ERROR_NO_GAME_FOUND, new JSONString("No Game Found for League["+(Integer)map.get("leagueId")+"] and Season["+(Integer)map.get("seasonId")+"]"));
		JSList jstList = new JSList();
		jstList.setList(new ArrayList<JSON>());
		for (Iterator<Game> iterator = gameList.iterator(); iterator.hasNext();) {
			Game game = iterator.next();
			jstList.getList().add(new JSGame(game));
		}
		return new JSONResponse(0, jstList);
	}
}

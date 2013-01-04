package com.fm.mw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.ModifyCount;
import com.fm.dao.GameDAO;
import com.fm.dao.ModifyCountDAO;
import com.fm.mw.JSON;
import com.fm.mw.obj.JSGameResult;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class GameResultService {
private static Logger logger = Logger.getLogger(GameResultService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("game result request received :" + map);
		GameDAO dao = new GameDAO();
		ModifyCountDAO mcDao = new ModifyCountDAO();
		Integer managerId = Integer.parseInt((String)map.get("managerId"));
		Integer minId = Integer.parseInt((String)map.get("minId"));
		ModifyCount mc = mcDao.findByManager(managerId);
		List<Game> gameList = dao.getGameResults(mc.getLeagueId(), mc.getSeasonId(), minId, mc.getMaxGames());
		if (gameList == null)
			return new JSONResponse(JSONResponse.ERROR_NO_GAME_FOUND, new JSONString("No Game Found for League["+mc.getLeagueId()+"] and Season["+mc.getSeasonId()+"]"));
		JSList jstList = new JSList();
		jstList.setList(new ArrayList<JSON>());
		for (Iterator<Game> iterator = gameList.iterator(); iterator.hasNext();) {
			Game game = iterator.next();
			jstList.getList().add(new JSGameResult(game));
		}
		return new JSONResponse(0, jstList);
	}
	
	
public static void main(String[] args) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("managerId", "31");
	map.put("minId", 0);
	System.out.println(process(map).toJSON());

}
}

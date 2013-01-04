package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.GameDetail;
import com.fm.dal.PlayerPerformance;
import com.fm.dao.GameDetailDAO;
import com.fm.dao.PlayerPerformanceDAO;
import com.fm.mw.JSON;
import com.fm.mw.obj.JSGameDetail;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSTeamPlayerStats;

public class GameDetailService {

private static Logger logger = Logger.getLogger(GameResultService.class);
	
	public static JSONResponse teamStats(Map<String, Object> map){
		logger.info("team stats request received :" + map);
		PlayerPerformanceDAO dao = new PlayerPerformanceDAO();
		Integer gameId = Integer.parseInt((String)map.get("gameId"));
		List<PlayerPerformance> performances = dao.getGamePerformances(gameId);
		JSTeamPlayerStats jsTeamPlayers = new JSTeamPlayerStats(performances);
		return new JSONResponse(0, jsTeamPlayers);
	}
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("game detail request received :" + map);
		GameDetailDAO dao = new GameDetailDAO();
		Integer gameId = Integer.parseInt((String)map.get("gameId"));
		Integer logLevel = Integer.parseInt((String)map.get("logLevel"));
		List<GameDetail> details = dao.getGameDetails(gameId, logLevel);
		JSList jstList = new JSList();
		jstList.setList(new ArrayList<JSON>());
		for (Iterator<GameDetail> iterator = details.iterator(); iterator.hasNext();) {
			GameDetail detail = iterator.next();
			jstList.getList().add(new JSGameDetail(detail));
		}
		return new JSONResponse(0, jstList);
	}
}

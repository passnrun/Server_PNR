package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.bll.TacticsManager;
import com.fm.dal.Tactic;
import com.fm.dal.TacticPlayer;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;
import com.fm.mw.obj.JSTactic;

public class SquadService {
	private static Logger logger = Logger.getLogger(SquadService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("squad request received :" + map);
		Integer teamId = Integer.parseInt((String)map.get("teamId"));
		Tactic teamTactic = TacticsManager.getTeamTactic(teamId);
		return new JSONResponse(0, new JSTactic(teamTactic));
	}
	public static JSONResponse save(Map<String, Object> map){
		logger.info("save squad request received :" + map);
		Map tactic = (Map)map.get("tactic");
		int teamId = Integer.parseInt((String)tactic.get("id"));
		logger.debug("teamId:"+teamId);
		List<Map> squad = (List)tactic.get("squad"); 
		logger.debug("squad:"+squad);
		if (TacticsManager.saveTeamTactic(teamId, convertMapToTacticPlayers(squad)))
			return new JSONResponse(0, new JSONString("done"));
		else
			return new JSONResponse(-1, new JSONString("An Error Occured"));
	}
	private static List<TacticPlayer> convertMapToTacticPlayers(List<Map> squad) {
		List<TacticPlayer> players = new ArrayList<TacticPlayer>();
		for (Iterator iterator = squad.iterator(); iterator.hasNext();) {
			Map<String, String>playerMap = (Map<String, String>) iterator.next();
			TacticPlayer tp = new TacticPlayer();
			tp.setPlayerId(Integer.parseInt(playerMap.get("pid")));
			tp.setPosition(playerMap.get("position"));
			players.add(tp);
		}
		return players;
	}
}

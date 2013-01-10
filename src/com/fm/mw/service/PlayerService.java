package com.fm.mw.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.bll.PlayerPerformanceManager;
import com.fm.dal.Player;
import com.fm.dal.PlayerPerformance;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;
import com.fm.mw.obj.JSPlayerDetail;

public class PlayerService {
	private static Logger logger = Logger.getLogger(PlayerService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("player info request received :" + map);
		DAO dao = new DAO();
		Integer playerId = Integer.parseInt((String)map.get("playerId"));
		Player player = (Player)dao.findById(Player.class, playerId);
		if (player == null)
			return new JSONResponse(JSONResponse.ERROR_NO_PLAYER_FOUND, new JSONString("No Player Found with ID["+playerId+"]"));
		player.setTeamObj((Team)dao.findById(Team.class, player.getCurrentTeam()));
		PlayerPerformance seasonPerformance = PlayerPerformanceManager.getCurrentSeasonPerformance(player.getId());
		JSPlayerDetail playerDet = new JSPlayerDetail(player, seasonPerformance);
		return new JSONResponse(0, playerDet);
	}
}

package com.fm.mw.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Player;
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
		JSPlayerDetail playerDet = new JSPlayerDetail();
		return new JSONResponse(0, playerDet);
	}
}

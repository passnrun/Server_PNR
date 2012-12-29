package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.GameDetail;
import com.fm.dao.GameDAO;
import com.fm.dao.GameDetailDAO;
import com.fm.mw.JSON;
import com.fm.mw.obj.JSGameDetail;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class GameDetailService {

private static Logger logger = Logger.getLogger(GameResultService.class);
	
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

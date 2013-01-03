package com.fm.bll;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.GameDetail;

public class LogManager {
	Logger logger = Logger.getLogger(LogManager.class);
	
	public void info(Game game, int minute, String log){
		logger.info(log);
		GameDetail detail = new GameDetail(game, minute, log, 4);
		game.getGameDetails().add(detail);
	}
	public void debug(Game game, int minute, String log){
		logger.debug(log);
		GameDetail detail = new GameDetail(game, minute, log, 5);
		game.getGameDetails().add(detail);
	}
}

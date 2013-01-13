package com.fm.bll;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.GameDetail;
import com.fm.dao.DAO;

public class LogManager {
	Logger logger = Logger.getLogger(LogManager.class);
	DAO dao;
	private static LogManager INSTANCE = new LogManager();
	
	private LogManager(){
		dao = new DAO();
	}
	
	public static LogManager getInstance(){
		return INSTANCE;
	}
	public void save(GameDetail gd){
		dao.save(gd);
	}
	public void info(Game game, int minute, String log){
		logger.info(log);
		GameDetail detail = new GameDetail(game, minute, log, 4);
		dao.save(detail);
	}
	public void debug(Game game, int minute, String log){
		logger.debug(log);
		GameDetail detail = new GameDetail(game, minute, log, 5);
		game.getGameDetails().add(detail);
		dao.save(detail);
	}
}

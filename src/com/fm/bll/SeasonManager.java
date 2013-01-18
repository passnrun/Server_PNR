package com.fm.bll;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fm.dal.League;
import com.fm.dal.Season;
import com.fm.dao.DAO;
import com.fm.dao.SeasonDAO;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class SeasonManager {
	private static Logger logger = Logger.getLogger(SeasonManager.class);
	
	public static Season getCurrentSeason() {
		SeasonDAO dao = new SeasonDAO();
		return dao.getCurrentSeason();
	}
	public static JSONResponse initSeason() {
		DAO dao = new DAO();
		List<League> leagues = dao.findAll(League.class);
		Season season = createSeason();
		logger.info("New Season is created, processing all leagues["+leagues.size()+"]..");
		for (Iterator iterator = leagues.iterator(); iterator.hasNext();) {
			League league = (League) iterator.next();
			JSONResponse resp = LeagueManager.initiateLeague(league, season);
			if (!resp.isSuccessful())
				return resp;
		}
		return new JSONResponse(0, new JSONString("Done"));
	}
	public static Season createSeason() {
		Season season = new Season();
		DAO dao = new DAO();
		dao.save(season);
		return season;
	}

}

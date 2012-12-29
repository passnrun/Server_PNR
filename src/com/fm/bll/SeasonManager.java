package com.fm.bll;

import com.fm.dal.Season;
import com.fm.dao.DAO;

public class SeasonManager {

	public static Season createSeason() {
		Season season = new Season();
		DAO dao = new DAO();
		dao.save(season);
		return season;
	}

}

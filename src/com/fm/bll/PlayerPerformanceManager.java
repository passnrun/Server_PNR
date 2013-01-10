package com.fm.bll;

import com.fm.dal.Player;
import com.fm.dal.PlayerPerformance;
import com.fm.dao.PlayerPerformanceDAO;

public class PlayerPerformanceManager {

	public static PlayerPerformance getCurrentSeasonPerformance(int playerId){
		PlayerPerformanceDAO dao = new PlayerPerformanceDAO();
		Object[] vals = dao.getCurrentSeasonPlayerPerformance(playerId);
		PlayerPerformance pp = new PlayerPerformance();
		if (vals != null){
			pp.setSeasonId((Integer)vals[0]);
			pp.setPlayed(((Long)vals[1]).intValue());
			pp.setMins(((Long)vals[2]).intValue());
			pp.setGoal(((Long)vals[3]).intValue());
			pp.setAssist(((Long)vals[4]).intValue());
			pp.setForm(((Double)vals[5]).intValue());
		}else{
			pp.setGoal(0);
			pp.setAssist(0);
		}
		pp.setMorale(0);
		pp.setPosition(".");
		pp.setPlayer(new Player());
		return pp;
	}
}

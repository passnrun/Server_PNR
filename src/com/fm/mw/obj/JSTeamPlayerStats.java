package com.fm.mw.obj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fm.dal.Game;
import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;

public class JSTeamPlayerStats implements JSON {
	private JSList playersOfHome;
	private JSList playersOfAway;
	
	public JSTeamPlayerStats(Game game, List<PlayerPerformance> performances) {
		playersOfHome = new JSList(new ArrayList<JSON>());
		playersOfAway = new JSList(new ArrayList<JSON>());
		
		for (Iterator iterator = performances.iterator(); iterator.hasNext();) {
			PlayerPerformance pp = (PlayerPerformance) iterator.next();
			if (game.getHomeTeam().getId() == pp.getTeamId()){
				playersOfHome.getList().add(new JSPlayerPerformance(pp));
			}else{
				playersOfAway.getList().add(new JSPlayerPerformance(pp));
			}
		}
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{home\" :").append(playersOfHome.toJSON()).append(" ,");
		sb.append("\"away\" :").append(playersOfAway.toJSON()).append("}");
		return sb.toString();
	}

}

package com.fm.mw.obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fm.dal.Game;
import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;

public class JSTeamPlayerStats implements JSON {
	private JSList playersOfHome;
	private JSList playersOfAway;
	
	public JSTeamPlayerStats(Game game, List<PlayerPerformance> performances) {
		List<JSPlayerPerformance> homePlayers = new ArrayList<JSPlayerPerformance>();
		List<JSPlayerPerformance> awayPlayers = new ArrayList<JSPlayerPerformance>();
		for (Iterator iterator = performances.iterator(); iterator.hasNext();) {
			PlayerPerformance pp = (PlayerPerformance) iterator.next();
			if (game.getHomeTeam().getId() == pp.getTeamId())
				homePlayers.add(new JSPlayerPerformance(pp));
			else
				awayPlayers.add(new JSPlayerPerformance(pp));
		}
		Collections.sort(homePlayers);
		Collections.sort(awayPlayers);
		playersOfHome = new JSList(new ArrayList<JSON>());
		playersOfHome.getList().addAll(homePlayers);
		playersOfAway = new JSList(new ArrayList<JSON>());
		playersOfAway.getList().addAll(awayPlayers);
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{home\" :").append(playersOfHome.toJSON()).append(" ,");
		sb.append("\"away\" :").append(playersOfAway.toJSON()).append("}");
		return sb.toString();
	}

}

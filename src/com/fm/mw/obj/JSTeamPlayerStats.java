package com.fm.mw.obj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;

public class JSTeamPlayerStats implements JSON {
	private JSList playersOfTeam1;
	private JSList playersOfTeam2;
	private int team1 = -1;
	private int team2 = -1;
	
	public JSTeamPlayerStats(List<PlayerPerformance> performances) {
		playersOfTeam1 = new JSList(new ArrayList<JSON>());
		playersOfTeam2 = new JSList(new ArrayList<JSON>());
		
		for (Iterator iterator = performances.iterator(); iterator.hasNext();) {
			PlayerPerformance pp = (PlayerPerformance) iterator.next();
			if (team1 == -1 || team1 == pp.getTeamId()){
				team1 = pp.getTeamId();
				playersOfTeam1.getList().add(new JSPlayerPerformance(pp));
			}else if (team2 == -1 || team2 == pp.getTeamId()){
				team2 = pp.getTeamId();
				playersOfTeam2.getList().add(new JSPlayerPerformance(pp));
			}
		}
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"teamId1\" :").append(team1).append(" ,");
		sb.append(" \"teamId2\" :").append(team2).append(" ,");
		sb.append("\"team1\" :").append(playersOfTeam1.toJSON()).append(" ,");
		sb.append("\"team2\" :").append(playersOfTeam2.toJSON()).append("}");
		return sb.toString();
	}

}

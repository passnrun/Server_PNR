package com.fm.mw.obj;

import com.fm.dal.ModifyCount;
import com.fm.mw.JSON;

public class JSModifyCount extends ModifyCount implements JSON {
	public JSModifyCount(ModifyCount mc) {
		super(mc);
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{")
		.append("\"managerId\" :").append(getManagerId()).append(" , ")
		.append("\"teamId\" :").append(getTeamId()).append(" , ")
		.append("\"leagueId\" :").append(getLeagueId()).append(" , ")
		.append("\"seasonId\" :").append(getSeasonId()).append(" , ")
		.append("\"maxNews\" :").append(getMaxNews()).append(" , ")
		.append("\"maxGames\" :").append(getMaxGames()).append(" , ")
		.append("\"maxFinance\" :").append(getMaxFinance())	
		.append("}");
		return sb.toString();
	}

}

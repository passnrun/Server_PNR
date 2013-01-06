package com.fm.mw.obj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fm.dal.Game;
import com.fm.mw.JSON;

public class JSGame extends Game implements JSON {
	
	public JSGame(Game game) {
		super(game);
	}
	
	@Override
	public String toJSON() {
		DateFormat format = new SimpleDateFormat("dd.MM.yy");
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"team1\" :").append(getHomeTeam().getId()).append(" ,");
		sb.append("\"team2\" :").append(getAwayTeam().getId()).append(" ,");
		sb.append("\"seasonId\" :").append(getSeasonId()).append(" ,");
		sb.append("\"week\" :").append(getWeek()).append(" ,");
		sb.append("\"attendance\" :").append(getAttendance()).append(" ,");
		sb.append("\"date\" :").append("\""+format.format(getGameDate())+"\"}");
		return sb.toString();
	}

}

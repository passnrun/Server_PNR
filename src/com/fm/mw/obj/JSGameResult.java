package com.fm.mw.obj;

import com.fm.dal.Game;

public class JSGameResult extends JSGame {
	
	public JSGameResult(Game game) {
		super(game);
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"score1\" :").append(getScore1()).append(" ,");
		sb.append("\"score2\" :").append(getScore2()).append(" ,");
		sb.append("\"attendance\" :").append(getAttendance()).append("}");
		return sb.toString();
	}
}

package com.fm.mw.obj;

import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;
import com.fm.obj.Position;

public class JSPlayerPerformance extends PlayerPerformance implements JSON, Comparable<JSPlayerPerformance>{
	
	public JSPlayerPerformance(PlayerPerformance pp) {
		super(pp);
	}

	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{")
		.append("\"name\" :").append("\""+getPlayer().getShortName()+"\"").append(" , ")
		.append("\"position\" :").append("\""+getPosition()+"\"").append(" , ")
		.append("\"playerId\" :").append(getPlayer().getId()).append(" , ")
		.append("\"mins\" :").append(getMins()).append(" , ")
		.append("\"assist\" :").append(getAssist()).append(" , ")
		.append("\"goal\" :").append(getGoal()).append(" , ")
		.append("\"morale\" :").append(getMorale()).append(" , ")
		.append("\"played\" :").append(getPlayed()).append(" , ")
		.append("\"form\" :").append("\""+format(getForm())+"\"")
		.append("}");
		return sb.toString();
	}
	
	private static String format(Integer form) {
		if (form == null || form == 0)
			return "-";
		float val =  (float)form / 100;
		return String.format("%(.1f", val);
	}
	
	@Override
	public int compareTo(JSPlayerPerformance tp) {
		
		if (getPosition().matches("[S]\\d"))
			return getPosition().compareTo(tp.getPosition());
		else if ("R".equals(getPosition()))
			return getId() - tp.getId();
		else
			return new Position(getPosition()).compareTo(new Position(tp.getPosition()));
	}
}

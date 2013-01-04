package com.fm.mw.obj;

import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;

public class JSPlayerPerformance extends PlayerPerformance implements JSON{
	
	public JSPlayerPerformance(PlayerPerformance pp) {
		super(pp);
	}

	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{")
		.append("\"name\" :").append("\""+getPlayer().getShortName()+"\"").append(" , ")
		.append("\"position\" :").append("\""+getPosition()+"\"").append(" , ")
		.append("\"mins\" :").append(getMins()).append(" , ")
		.append("\"assist\" :").append(getAssist()).append(" , ")
		.append("\"goal\" :").append(getGoal()).append(" , ")
		.append("\"morale\" :").append(getMorale()).append(" , ")
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
}

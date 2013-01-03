package com.fm.mw.obj;

import com.fm.dal.GameDetail;
import com.fm.mw.JSON;

public class JSGameDetail extends GameDetail implements JSON{
	public JSGameDetail(GameDetail detail) {
		super(detail);
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"minute\" :").append(getMinute()).append(" ,");
		sb.append("\"action\" :").append(getAction()).append(" ,");
		sb.append("\"team\" :").append(0).append(" ,");
		sb.append("\"player\" :").append("\""+getPlayer()+"\", ");
		sb.append("\"comment\" :").append("\""+getLogMessage()+"\"}");
		return sb.toString();
	}
	
}

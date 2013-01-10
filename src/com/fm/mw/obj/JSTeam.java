package com.fm.mw.obj;

import com.fm.dal.Team;
import com.fm.mw.JSON;

public class JSTeam extends Team implements JSON{
	
	public JSTeam(Team t) {
		setId(t.getId());
		setName(t.getName());
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"name\" :").append("\""+getName()+"\"}");
		return sb.toString();
	}

}

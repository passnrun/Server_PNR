package com.fm.mw.obj;

import com.fm.mw.JSON;

public class IDList implements JSON{
	private int managerId;
	private int teamId;
	
	@Override
	public String toJSON() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{ ");
		if (managerId > 0)
			buffer.append("\"managerId\" : "+managerId);
		if (managerId > 0 && teamId > 0)
			buffer.append(" , ");
		if (teamId > 0)
			buffer.append("\"teamId\" : "+teamId);
		buffer.append(" }");
		return buffer.toString();
	}
	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
}

package com.fm.mw;

import com.fm.dal.Manager;
import com.fm.dal.Team;
import com.fm.dao.DAO;

public class JSTeam extends Team implements JSON {

	public JSTeam(Team team) {
		super(team);
	}


	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		Manager manager = null;
		if (getCurrentManager() != null && getCurrentManager()>0)
			manager = (Manager)(new DAO()).findById(Manager.class, getCurrentManager());
		sb.append("{\"id\" :").append(getId()).append(" ,")
		  .append("\"name\" :").append("\""+getName()+"\"").append(" ,")
		  .append("\"color1\" :").append("\""+getColor1()+"\"").append(" ,")
		  .append("\"color2\" :").append("\""+getColor2()+"\"").append(" ,")
		  .append("\"currentLeague\" :").append(getCurrentLeague()).append(" ,")
		  .append("\"manager\" :").append("\""+getManagerName(manager)+"\"").append(" ,")
		  .append("\"stadium\" :").append("\""+getStadium().getName()+"\"}");
		return sb.toString();
	}
	
	private String getManagerName(Manager m){
		if (m == null)
			return "NA";
		return m.getFirstname() + " " + m.getLastname();
	}

}

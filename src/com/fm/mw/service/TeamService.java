package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Team;
import com.fm.dao.TeamDAO;
import com.fm.mw.JSON;
import com.fm.mw.JSTeam;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class TeamService {
	private static Logger logger = Logger.getLogger(TeamService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("team list request received :" + map);
		TeamDAO dao = new TeamDAO();
		List<Team> teamList = dao.getLeagueTeams(Integer.parseInt((String)map.get("leagueId")));
		if (teamList == null || teamList.size() == 0)
			return new JSONResponse(JSONResponse.ERROR_NO_TEAM_FOUND, new JSONString("No Team Found for League["+(Integer)map.get("leagueId")+"]"));
		JSList jstList = new JSList();
		jstList.setList(new ArrayList<JSON>());
		for (Iterator<Team> iterator = teamList.iterator(); iterator.hasNext();) {
			Team team = iterator.next();
			jstList.getList().add(new JSTeam(team));
		}
		return new JSONResponse(0, jstList);
	}
}

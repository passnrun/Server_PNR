package com.fm.mw.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.bll.LeagueManager;
import com.fm.bll.NewsManager;
import com.fm.dal.Manager;
import com.fm.dal.Team;
import com.fm.dao.TeamDAO;
import com.fm.mw.obj.IDList;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class RegisterService {
	private static Logger logger = Logger.getLogger(RegisterService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("register request received :" + map);
		try {
			Manager m = convert2Manager(map);
			return registerManager(m);
		} catch (ParseException e) {
			logger.error("date format error", e);
			return new JSONResponse(JSONResponse.ERROR_DATEFORMAT, new JSONString("Invalid Date Format:"+map.get("birthdate")));
		}
	}
	
	public static Manager convert2Manager(Map<String, Object> data) throws ParseException{
		Manager m = new Manager();
		Map<String, Object> map = (Map<String, Object>)data.get("manager");
//		DateFormat format = new SimpleDateFormat("mmMMyyyy");
		m.setFirstname((String)map.get("firstName"));
		m.setLastname((String)map.get("lastName"));
		m.setTeamName((String)map.get("team"));
		m.setNationality((String)map.get("nationality"));
		m.setLanguage((String)map.get("language"));
		//m.setBirthdate(format.parse((String)map.get("birthdate")));
		m.setDevice((String)map.get("device"));
		m.setDeviceId((String)map.get("deviceId"));
		return m;
	}
	
	public static JSONResponse registerManager(Manager m){
		TeamDAO dao = new TeamDAO();
		m.setRegisterDate(new Date());
		Team team = dao.chooseTeamWithoutManager();
		if (team == null){
			LeagueManager.generateNewLeague(1, "Amator League", m.getNationality(), true);
			team = dao.chooseTeamWithoutManager();
		}
		if (team == null){
			return new JSONResponse(JSONResponse.ERROR_NO_TEAM_FOUND, new JSONString("No available team found"));
		}
		m.setCurrentTeam(team.getId());
		dao.save(m);
		team.setCurrentManager(m.getId());
		team.setName(m.getTeamName());
		dao.save(team);
		NewsManager.createNews(NewsManager.TYPE_WELLCOME, team, new String[]{});
		IDList ids = new IDList();
		ids.setManagerId(m.getId());
		ids.setTeamId(team.getId());
		return new JSONResponse(0, ids);
	}
}

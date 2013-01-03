package com.fm.mw.service;

import com.fm.dal.Manager;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class ResignService {

	public static JSONResponse resignManager(int mid){
		DAO dao = new DAO();
		Manager manager = (Manager)dao.findById(Manager.class, mid);
		if (manager == null)
			return new JSONResponse(JSONResponse.ERROR_NO_TEAM_FOUND, new JSONString("No manager found with id:"+mid));
		Team team = (Team)dao.findById(Team.class, manager.getCurrentTeam());
		if (team != null){
			team.setCurrentManager(null);
			dao.save(team);
		}
		dao.delete(manager);
		return new JSONResponse(0, new JSONString("Deleted manager with id:"+mid));
	}
}

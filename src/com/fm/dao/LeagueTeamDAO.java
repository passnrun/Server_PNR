package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.Game;
import com.fm.dal.LeagueTeam;

public class LeagueTeamDAO extends DAO {

	public List<LeagueTeam> getLeagueTable(int leagueId, int seasonId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<LeagueTeam> table = session.createQuery("from "+LeagueTeam.class.getName() + " where seasonId = " + seasonId + " and leagueId = "+leagueId
				+ " order by points desc, (forGoal - againstGoal) desc, forGoal desc").list();
		session.getTransaction().commit();
		session.close();
		return table;
	}
}

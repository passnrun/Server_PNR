package com.fm.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fm.dal.Team;

public class TeamDAO extends DAO{
	private static Logger logger = Logger.getLogger(TeamDAO.class);
	
	public List<Team> getLeagueTeams(int leagueId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Team> teams = session.createQuery("from "+Team.class.getName() + " where currentLeague = " + leagueId).list();
		session.getTransaction().commit();
		session.close();
		return teams;
	}
	
	public Team chooseTeamWithoutManager(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+Team.class.getName() + " where currentManager is null or currentManager = 0 order by id");
		query.setMaxResults(1);
		List<Team> list = query.list();
		session.getTransaction().commit();
		session.close();
		if (list != null && list.size() == 1)
			return list.get(0);
		else
			return null;
	}
	
	public static void main(String[] args) {
		TeamDAO dao = new TeamDAO();
		List<Team> teams = dao.getLeagueTeams(1);
		for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			Team team = (Team) iterator.next();
			System.out.println(team.getName());
		}
	}
}

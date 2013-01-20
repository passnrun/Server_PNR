package com.fm.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fm.dal.Team;

public class TeamDAO extends DAO{
	private static Logger logger = Logger.getLogger(TeamDAO.class);
	
	public Integer getAvailableTeamCount() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Object> values = session.createQuery("select count(*) from "+Team.class.getName() + " where currentManager = 0").list();
		session.getTransaction().commit();
		session.close();
		if (values != null && values.size() > 0){
			Long l = (Long)values.get(0);
			return l.intValue();
		}
		return 0;
	}
	public static void main(String[] args) {
		TeamDAO dao = new TeamDAO();
		System.out.println(dao.getLeagueTeamCount(1));
	}
	public Integer getLeagueTeamCount(int leagueId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Object> values = session.createQuery("select count(*) from "+Team.class.getName() + " where currentLeague = " + leagueId).list();
		session.getTransaction().commit();
		session.close();
		if (values != null && values.size() > 0){
			Long l = (Long)values.get(0);
			return l.intValue();
		}
		return 0;
	}
	public List<Team> getLeagueTeams(int leagueId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Team> teams = session.createQuery("from "+Team.class.getName() + " where currentLeague = " + leagueId + " order by currentLevel desc").list();
		session.getTransaction().commit();
		session.close();
		return teams;
	}
	public List<Team> getAvailableTeamsByLevel(int level, int maxResults) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+Team.class.getName() + " where currentLeague = 0 and currentLevel = " + level);
		query.setMaxResults(maxResults);
		List<Team> teams = query.list();
		session.getTransaction().commit();
		session.close();
		return teams;
	}
	
	public Team chooseTeamWithoutManager(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+Team.class.getName() + " where currentManager is null or currentManager = 0 order by currentLevel, id");
		query.setMaxResults(1);
		List<Team> list = query.list();
		session.getTransaction().commit();
		session.close();
		if (list != null && list.size() == 1)
			return list.get(0);
		else
			return null;
	}
	
}

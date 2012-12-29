package com.fm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fm.dal.Player;

public class PlayerDAO extends DAO{
	
	private static Logger logger = Logger.getLogger(PlayerDAO.class);
	
	public List<Player> getTeamPlayers(int teamId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Player> players = session.createQuery("from "+Player.class.getName() + " where currentTeam = " + teamId).list();
		session.getTransaction().commit();
		session.close();
		return players;
	}
}

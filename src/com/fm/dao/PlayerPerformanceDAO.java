package com.fm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fm.dal.PlayerPerformance;

public class PlayerPerformanceDAO extends DAO {

	public PlayerPerformance getPlayerPerformance(int gameId, int playerId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+PlayerPerformance.class.getName() + " where gameId = :gameId and playerId = :playerId");
		query.setParameter("gameId", gameId);
		query.setParameter("playerId", playerId);
		List<PlayerPerformance> playerPerfs = query.list();
		session.getTransaction().commit();
		session.close();
		if (playerPerfs != null && playerPerfs.size()>0)
			return playerPerfs.get(0);
		return null;
	}
}

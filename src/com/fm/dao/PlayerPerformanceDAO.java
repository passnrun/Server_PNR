package com.fm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fm.dal.PlayerPerformance;

public class PlayerPerformanceDAO extends DAO {

	public PlayerPerformance getPlayerPerformance(int gameId, int playerId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+PlayerPerformance.class.getName() + " where gameId = :gameId and player.id = :playerId");
		query.setParameter("gameId", gameId);
		query.setParameter("playerId", playerId);
		List<PlayerPerformance> playerPerfs = query.list();
		session.getTransaction().commit();
		session.close();
		if (playerPerfs != null && playerPerfs.size()>0)
			return playerPerfs.get(0);
		return null;
	}
	public List<PlayerPerformance> getGamePerformances(int gameId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+PlayerPerformance.class.getName() + " where gameId = :gameId");
		query.setParameter("gameId", gameId);
		List<PlayerPerformance> playerPerfs = query.list();
		session.getTransaction().commit();
		session.close();
		return playerPerfs;
	}
	public Object[] getCurrentSeasonPlayerPerformance(int playerId) {
		List<Object[]> performances = getPlayerPerformanceBySeason(playerId);
		if (performances != null && performances.size()>0)
			return performances.get(0);
		return null;
	}
	public List<Object[]> getPlayerPerformanceBySeason(int playerId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select seasonId, count(*), sum(mins), sum(goal), sum(assist), avg(form) from "+PlayerPerformance.class.getName() 
				+ " where player.id = :playerId group by seasonId order by seasonId desc");
		query.setInteger("playerId", playerId);
		List<Object[]> playerPerfs = query.list();
		session.getTransaction().commit();
		session.close();
		return playerPerfs;
	}
}

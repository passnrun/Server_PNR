package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.GameDetail;

public class GameDetailDAO extends DAO{
	public List<GameDetail> getGameDetails(int gameId, int logLevel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<GameDetail> details = session.createQuery("from "+GameDetail.class.getName() + " where game.id = " + gameId + " and logLevel <= "+logLevel + " order by minute").list();
		session.getTransaction().commit();
		session.close();
		return details;
	}
}

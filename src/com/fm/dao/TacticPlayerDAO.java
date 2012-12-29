package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.TacticPlayer;

public class TacticPlayerDAO extends DAO {
	
	public List<TacticPlayer> findByTactic(int tacticId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<TacticPlayer> players = (List<TacticPlayer>)session.createQuery("from "+TacticPlayer.class.getName() + " where tacticId="+tacticId).list();
		session.getTransaction().commit();
		session.close();
		return players;
	}
}

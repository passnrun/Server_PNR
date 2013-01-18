package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.League;

public class LeagueDAO extends DAO {

	public List<League> getLeagues() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<League> leagues = session.createQuery("from "+League.class.getName() + " order by level desc, id").list();
		session.getTransaction().commit();
		session.close();
		return leagues;
	}
}

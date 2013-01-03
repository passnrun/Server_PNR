package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.Tactic;

public class TacticDAO extends DAO {
	public Tactic findByTeam(int teamId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Tactic> tactics= (List<Tactic>)session.createQuery("from "+Tactic.class.getName() + " where teamId="+teamId).list();
		if (tactics != null && tactics.size() > 0)
		{
			session.getTransaction().commit();
			session.close();
			return tactics.get(0);
		}
		session.getTransaction().commit();
		session.close();
		return null;
	}
}

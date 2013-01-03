package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.Season;

public class SeasonDAO extends DAO {

	public Season getLastSeason() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Season> seasons = session.createQuery("from "+Season.class.getName() + " order by id desc").list();
		session.getTransaction().commit();
		session.close();
		if (seasons.size() > 0)
			return seasons.get(0);
		return null;
	} 
}

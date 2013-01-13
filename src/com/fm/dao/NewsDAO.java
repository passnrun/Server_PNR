package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.News;

public class NewsDAO extends DAO{

	public List<News> getNews(Integer managerId, Integer minId, Integer maxId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<News> news = session.createQuery("from "+News.class.getName() + " where managerId = " + managerId  + " and id > " + minId + " and id <= " + maxId).list();
		session.getTransaction().commit();
		session.close();
		return news;
	}
}

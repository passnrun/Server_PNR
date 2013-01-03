package com.fm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fm.dal.TeamTemplate;

public class TeamTemplateDAO extends DAO{
	Logger logger = Logger.getLogger(TeamTemplateDAO.class);
	
	public List<TeamTemplate> findAll(String country) {
		logger.info("findAll TeamTemplate");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<TeamTemplate> teamTempList = session.createQuery("from "+TeamTemplate.class.getName() + " where used is null " + (country!=null?" and country='"+country+"'":"")).list();
		session.getTransaction().commit();
		session.close();
		return teamTempList;
	}
	
}

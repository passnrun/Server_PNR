package com.fm.dao;

import java.util.List;

import org.hibernate.Session;

import com.fm.dal.ModifyCount;

public class ModifyCountDAO extends DAO {
//	private static Logger logger = Logger.getLogger(ModifyCountDAO.class);
	
	public ModifyCount findByManager(int managerId) {
		ModifyCount rec = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ModifyCount> countList = (List<ModifyCount>)session.createQuery("from "+ModifyCount.class.getName() + " where managerId = " + managerId).list();
		if (countList != null && countList.size() > 0)
			rec = countList.get(0);
		session.getTransaction().commit();
		session.close();
		return rec;
	}
}

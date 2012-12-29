package com.fm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fm.dal.League;

public class DAO {
	protected static final SessionFactory sessionFactory = createSessionFactory();
	private static Logger logger = Logger.getLogger(DAO.class);
	
	private static SessionFactory createSessionFactory() {
		return new Configuration()
							        .configure() // configures settings from hibernate.cfg.xml
							        .buildSessionFactory();
	}
	
	public void save(Object o){
		logger.info("Saving Object Instance["+o.getClass().getSimpleName()+"]");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate( o );
		session.getTransaction().commit();
		session.close();
	}
	public void delete(Object o){
		logger.info("Deleting Object Instance["+o.getClass().getSimpleName()+"]");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
		session.close();
	}

	
	public Object findById(Class clazz,  int id){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Object o = session.get(clazz, id);
		session.getTransaction().commit();
		session.close();
		return o;
	}
	public List findAll(Class clazz){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List list = session.createQuery("from "+clazz.getName()).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public static void main(String[] args) {
		DAO dao = new DAO();
		League league = (League)dao.findById(League.class, 1);
		System.out.println("League:"+league.getName());
	}
}

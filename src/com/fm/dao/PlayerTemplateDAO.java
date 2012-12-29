package com.fm.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fm.dal.PlayerTemplate;

public class PlayerTemplateDAO extends DAO {
	
	private static Logger logger = Logger.getLogger(PlayerTemplateDAO.class);
	
	public List<PlayerTemplate> findAll(String country) {
		logger.info("findAll PlayerTemplate");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<PlayerTemplate> playerTempList = session.createQuery("from "+PlayerTemplate.class.getName() + ((country!=null)?" where country='"+country+"'":"")).list();
		session.getTransaction().commit();
		session.close();
		return playerTempList;
	}
	
	public static void main(String[] args) {
		PlayerTemplateDAO dao = new PlayerTemplateDAO();
		List<PlayerTemplate> list = dao.findAll("italy");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PlayerTemplate player = (PlayerTemplate) iterator.next();
			System.out.println(player.getName() + " " + player.getSurname() + " " + player.getCountry());
		}
	}
}

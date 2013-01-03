package com.fm.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fm.dal.Game;
import com.fm.dal.GameDetail;

public class GameDAO extends DAO {
	public List<Game> getFixture(int leagueId, int seasonId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Game> games = session.createQuery("from "+Game.class.getName() + " where seasonId = " + seasonId + " and leagueId = "+leagueId).list();
		session.getTransaction().commit();
		session.close();
		return games;
	}

	public List<Game> getGameResults(Integer leagueId, Integer seasonId,Integer minId, Integer maxId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Game> games = session.createQuery("from "+Game.class.getName() + " where seasonId = " + seasonId + " and leagueId = "+leagueId 
				+ " and id > " + minId + " and id <= " + maxId).list();
		session.getTransaction().commit();
		session.close();
		return games;
	}
	public List<GameDetail> getGameDetails(Integer gameId, Integer level) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<GameDetail> details = session.createQuery("from "+GameDetail.class.getName() + " where game.id = " + gameId + " and logLevel <= "+level).list();
		session.getTransaction().commit();
		session.close();
		return details;
	}

	public List<Game> getGamesByDate(Date date) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+Game.class.getName() + " where gameDate <= :date and score1 is null and score2 is null");
		query.setParameter("date", date);
		List<Game> games = query.list();
		session.getTransaction().commit();
		session.close();
		return games;
	}
	public List<Game> getLastGamesOfTeam(int teamId, int count) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from "+Game.class.getName() + " where score1 is not null and score2 is not null and (homeTeam.id = :teamId or awayTeam.id = :teamId) order by gameDate desc");
		query.setParameter("teamId", teamId);
		query.setMaxResults(count);
		List<Game> games = query.list();
		session.getTransaction().commit();
		session.close();
		return games;
	}
}

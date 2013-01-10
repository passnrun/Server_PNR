package com.fm.bll;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.League;
import com.fm.dal.Manager;
import com.fm.dal.ModifyCount;
import com.fm.dal.Season;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.dao.ModifyCountDAO;
import com.fm.dao.TeamDAO;
import com.fm.mobile.IPhoneNotificationHandler;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;
import com.fm.util.DateUtil;
import com.fm.util.Parameter;

public class LeagueManager {
	private static Logger logger = Logger.getLogger(LeagueManager.class);
	
	public static JSONResponse initiateLeague(int leagueId){
		TeamDAO teamDAO = new TeamDAO();
		logger.info("initating league["+leagueId+"]");
		League league = (League)teamDAO.findById(League.class, leagueId);
		if (league == null)
			return new JSONResponse(-1, new JSONString("League["+leagueId+"] is not found"));
		if (league.getStatus() != League.STATUS_NEWSEASON)
			return new JSONResponse(-1, new JSONString("League["+leagueId+"] is already started"));
		Season season = SeasonManager.createSeason();
		logger.info("Season["+season.getId()+"] is created");
		List<Team> teams = teamDAO.getLeagueTeams(leagueId);
		TeamManager.createLeagueTeamRecords(teams, season.getId(), leagueId);
		generateFixture(season.getId(), leagueId);
		league.setStatus(League.STATUS_STARTED);
		teamDAO.save(league);
		for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			Team team = (Team) iterator.next();
			NewsManager.create(team);
		}
		return new JSONResponse(0, new JSONString("Done"));
	}

	private static void generateFixture(int seasonId, int leagueId) {
		DAO dao = new DAO();
		League league = (League)dao.findById(League.class, leagueId);
		int numOfGames = (league.getSize()-1)*2;
		Team[] teams = choseTeamsFromPot(leagueId);
		Calendar gameDate = DateUtil.getFirstGamePlayDate(Parameter.getInt("Fixture.FirstGameStartsIn"), Parameter.getInt("Fixture.GameTime"));
		for (int i = 0; i < numOfGames; i++) {
			createGamesForWeek(dao, teams, gameDate, i+1, seasonId, leagueId);
			gameDate.add(Calendar.DATE, Parameter.getInt("Fixture.DaysBetweenGames"));
		}
	}

	private static void createGamesForWeek(DAO dao, Team[] teams, Calendar matchTime, int week, int seasonId, int leagueId) {
		String weekTemplate = Parameter.getString("Week."+((week<teams.length)?week:(week - teams.length + 1)));
		String[] gamesTemplate = weekTemplate.split(",");
		for (int i = 0; i < gamesTemplate.length; i++) {
			String[] teamsStr = gamesTemplate[i].split("-");
			int homeTeamIndex = Integer.parseInt((week<teams.length)?teamsStr[0]:teamsStr[1]);
			int awayTeamIndex = Integer.parseInt((week<teams.length)?teamsStr[1]:teamsStr[0]);
			dao.save(new Game(teams[homeTeamIndex -1], teams[awayTeamIndex -1], matchTime, seasonId, leagueId, week));
		}
	}


	
	private static Team[] choseTeamsFromPot(int leagueId) {
		TeamDAO dao = new TeamDAO();
		List<Team> teamList = dao.getLeagueTeams(leagueId);
		Team[] teams = new Team[teamList.size()];
		int index = 0;
		Random r= new Random();
		while(teamList.size()>0)
			teams[index++] = teamList.remove(r.nextInt(teamList.size()));
		return teams;
	}

	public static int generateNewLeague(League parent, String name, String country){
		logger.info("Generation the league["+name+"] "+(parent!=null?"under ["+parent.getName()+"]":""));
		DAO dao = new DAO();
		int level = parent != null ? parent.getLevel() + 1 : 1;
		League league = fillLeagueInformation(level, name, parent);
		boolean ok = true;
		dao.save(league);
		for (int i = 0; ok && i < league.getSize(); i++) 
			ok = TeamManager.generateTeam(league, country);
		return league.getId();
	}

	private static League fillLeagueInformation(int level, String name, League parent) {
		League league = new League();
		league.setLevel(level);
		league.setName(name);
		if (parent != null)
			league.setParent(parent);
		league.setPlayoffs(Parameter.getInt("league."+level+".playoffs"));
		league.setPromotions(Parameter.getInt("league."+level+".promotions"));
		league.setRelegations(Parameter.getInt("league."+level+".relegations"));
		league.setSize(Parameter.getInt("league."+level+".size"));
		league.setStatus(League.STATUS_NEWSEASON);
		return league;
	}
	
	public static void main(String[] args) {
//		LeagueManager.generateNewLeague(null, "League 1", "turkey");
		LeagueManager.initiateLeague(2);
	}
}

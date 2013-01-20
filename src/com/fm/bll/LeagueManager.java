package com.fm.bll;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.League;
import com.fm.dal.Season;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.dao.GameDAO;
import com.fm.dao.TeamDAO;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;
import com.fm.util.DateUtil;
import com.fm.util.Parameter;

public class LeagueManager {
	private static Logger logger = Logger.getLogger(LeagueManager.class);
	
	public static Integer numberOfTeamsInLeague(int leagueId)
	{
		TeamDAO teamDAO = new TeamDAO();
		return teamDAO.getLeagueTeamCount(leagueId);
	}
	
	public static JSONResponse initiateLeague(League league, Season season){
		TeamDAO teamDAO = new TeamDAO();
		if (league == null)
			return new JSONResponse(-1, new JSONString("League is not found"));
		logger.info("initating league["+league.getId()+"]");
		if (league.getStatus() != League.STATUS_NEWSEASON)
			return new JSONResponse(-1, new JSONString("League["+league.getId()+"] is already started"));
		List<Team> teams = teamDAO.getLeagueTeams(league.getId());
		TeamManager.createLeagueTeamRecords(teams, season.getId(), league.getId());
		generateFixture(season.getId(), league.getId());
		league.setStatus(League.STATUS_STARTED);
		teamDAO.save(league);
		createNewsForTeams(season.getId(), league.getId());
		return new JSONResponse(0, new JSONString("Done"));
	}

	private static void createNewsForTeams(int seasonId, int leagueId) {
		GameDAO gameDao = new GameDAO();
		League league = (League)gameDao.findById(League.class, leagueId);
		List<Game> firstWeekGames = gameDao.getGamesByWeek(leagueId, seasonId, 1);
		DateFormat format = new SimpleDateFormat("dd.MM.yy");
		for (Iterator iterator = firstWeekGames.iterator(); iterator.hasNext();) {
			Game game = (Game) iterator.next();
			NewsManager.createNews(NewsManager.TYPE_FIXTURE, game.getHomeTeam(), new String[]{league.getName(), game.getAwayTeam().getName(), format.format(game.getGameDate())});
			NewsManager.createNews(NewsManager.TYPE_FIXTURE, game.getAwayTeam(), new String[]{league.getName(), game.getHomeTeam().getName(), format.format(game.getGameDate())});
		}
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

	public static int generateNewLeague(int level, String name, String country, boolean isCreateTeams){
		logger.info("Generation the league["+name+"]");
		DAO dao = new DAO();
		League league = fillLeagueInformation(level, name);
		dao.save(league);
		for (int i = 0; isCreateTeams && i < league.getSize(); i++) 
			isCreateTeams = TeamManager.generateTeam(league, country);
		return league.getId();
	}

	private static League fillLeagueInformation(int level, String name) {
		League league = new League();
		league.setLevel(level);
		league.setName(name);
		league.setPlayoffs(Parameter.getInt("league."+level+".playoffs"));
		league.setPromotions(Parameter.getInt("league."+level+".promotions"));
		league.setRelegations(Parameter.getInt("league."+level+".relegations"));
		league.setSize(Parameter.getInt("league."+level+".size"));
		league.setStatus(League.STATUS_NEWSEASON);
		return league;
	}
}

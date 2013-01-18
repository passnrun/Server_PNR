package com.fm.jobs;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fm.bll.LeagueManager;
import com.fm.bll.SeasonManager;
import com.fm.bll.TeamManager;
import com.fm.dal.League;
import com.fm.dal.LeagueTeam;
import com.fm.dal.Season;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.dao.LeagueDAO;
import com.fm.dao.TeamDAO;
import com.fm.util.Parameter;

public class EndOfSeasonJob implements Job {
	Logger logger = Logger.getLogger(EndOfSeasonJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Season season = SeasonManager.getCurrentSeason();
		if (!isCurrentSeasonEnded(season)){
			logger.info("Current Season is going on");
			return;
		}
		logger.info("Current Season is ended, starting EndOfSeason process");
		LeagueDAO dao = new LeagueDAO();
		List<League> leagues = dao.getLeagues();
		processPromotionAndRelegations(leagues, season.getId());
		logger.info("Promoted and Relegated teams are removed from leagues");
		for (Iterator iterator = leagues.iterator(); iterator.hasNext();) {
			League league = (League) iterator.next();
			placeTeamsToLeague(league);
		}
		logger.info("All Leagues are filled..");
		Map<Integer, List<Team>> teamsWithoutLeague = TeamManager.getTeamsWithoutLeague();
		if (teamsWithoutLeague.size() > 0){
			logger.info("There are teams without league.. Generating new leagues..");
			processTeamsWithoutLeague(teamsWithoutLeague);
		}
		ensureAvailableTeams();
	}
	
	//Before starting a new Season.. we should have at least #min_available_teams# empty teams, 
	//so that people will join existing leagues, instead of waiting for the next season.
	private void ensureAvailableTeams() {
		int availableTeams = TeamManager.getAvailableTeamCount();
		DAO dao = new DAO();
		while(availableTeams < Parameter.getInt("min_available_teams")){
			int leagueId = LeagueManager.generateNewLeague(1, "Amatour League", null);
			League league = (League)dao.findById(League.class, leagueId);
			availableTeams+=league.getSize();
		}
	}

	private void processTeamsWithoutLeague(Map<Integer, List<Team>> teamsWithoutLeague) {
		DAO dao = new DAO();
		for (Iterator iterator = teamsWithoutLeague.entrySet().iterator(); iterator.hasNext();) {
			Integer level = (Integer) iterator.next();
			List<Team> teams = teamsWithoutLeague.get(level);
			int leagueId = LeagueManager.generateNewLeague(level, "League L"+level, null);
			League league = (League)dao.findById(League.class, leagueId);
			for (int i = 0; i < teams.size(); i++) {
				Team t = (Team)teams.get(i);
				t.setCurrentLeague(leagueId);
				//If this is the last team for league, create new one
				if (i == league.getSize() - 1){
					leagueId = LeagueManager.generateNewLeague(level, "League L"+level, null);
					league = (League)dao.findById(League.class, leagueId);
				}
			}
		}
	}

	private void placeTeamsToLeague(League league) {
		int currentTeams = LeagueManager.numberOfTeamsInLeague(league.getId());
		int teamsNeeded = league.getSize() - currentTeams;
		if (teamsNeeded < 0){
			logger.error("teamsNeeded is["+teamsNeeded+"] for League["+league.getId()+"]");
			return;
		}else if (teamsNeeded == 0){
			logger.info("No team is needed for League["+league.getId()+"]");
			return;
		}
		TeamDAO dao = new TeamDAO();
		List<Team> teamsChosen = dao.getAvailableTeamsByLevel(league.getLevel(), teamsNeeded);
		for (Iterator iterator = teamsChosen.iterator(); teamsNeeded > 0 && iterator.hasNext();) {
			Team team = (Team) iterator.next();
			team.setCurrentLeague(league.getId());
			dao.save(team);
			teamsNeeded--;
		}
		if (teamsNeeded > 0){
			logger.info("League["+league.getId()+"] still needs "+teamsNeeded+" teams, generating..");
			boolean ok = true;
			for (int i = 0; ok && i < teamsNeeded; i++) {
				ok  = TeamManager.generateTeam(league, null);
			}
		}
		
	}

	//find the promoted teams, and set their currentLeague to 0, and currentLevel to currentLevel +1
	//find relegated teams, and set their currentLeague to 0, and currentLevel to currentLevel -1
	private void processPromotionAndRelegations(List<League> leagues, int seasonId) {
		for (Iterator iterator = leagues.iterator(); iterator.hasNext();) {
			League league = (League) iterator.next();
			List<LeagueTeam> table = TeamManager.getLeagueTable(seasonId, league.getId());
			if (league.getPromotions() != null && league.getPromotions()> 0){
				List<LeagueTeam> promoteds = table.subList(0, league.getPromotions());
				for (Iterator iterator2 = promoteds.iterator(); iterator2.hasNext();) {
					LeagueTeam leagueTeam = (LeagueTeam) iterator2.next();
					TeamManager.changeLeague(leagueTeam.getTeamId(), +1);
				}
			}
			if (league.getRelegations() != null && league.getRelegations()> 0){
				List<LeagueTeam> relegateds = table.subList(table.size()-league.getRelegations(),table.size());
				for (Iterator iterator2 = relegateds.iterator(); iterator2
				.hasNext();) {
					LeagueTeam leagueTeam = (LeagueTeam) iterator2.next();
					TeamManager.changeLeague(leagueTeam.getTeamId(), -1);
				}
			}
		}
		
	}
	private boolean isCurrentSeasonEnded(Season season) {
		Calendar endOfSeason = Calendar.getInstance();
		endOfSeason.setTime(season.getEndDate());
		endOfSeason.add(Calendar.DATE, Parameter.getInt("Fixture.DaysAfterSeasonEnds"));
		return endOfSeason.before(Calendar.getInstance());
	}

}

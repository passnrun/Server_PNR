package com.fm.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fm.dal.League;
import com.fm.dal.LeagueTeam;
import com.fm.dal.Player;
import com.fm.dal.Stadium;
import com.fm.dal.Team;
import com.fm.dal.TeamPlayer;
import com.fm.dal.TeamTemplate;
import com.fm.dao.DAO;
import com.fm.dao.LeagueTeamDAO;
import com.fm.dao.PlayerDAO;
import com.fm.dao.TeamDAO;
import com.fm.dao.TeamTemplateDAO;
import com.fm.util.Parameter;

public class TeamManager {
	Logger logger = Logger.getLogger(TeamManager.class);
	
	public static Integer getAvailableTeamCount(){
		TeamDAO dao = new TeamDAO();
		return dao.getAvailableTeamCount();
	}
	public static void changeLeague(int teamId, int delta){
		DAO dao = new DAO();
		Team team = (Team)dao.findById(Team.class, teamId);
		team.setCurrentLeague(0);
		team.setCurrentLevel(team.getCurrentLevel() + delta);
		dao.save(team);
	}
	public static Map<Integer, List<Team>> getTeamsWithoutLeague(){
		TeamDAO dao = new TeamDAO();
		Map<Integer, List<Team>> teamMap = new HashMap<Integer, List<Team>>();
		List<Team> teams = dao.getLeagueTeams(0);
		for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			Team team = (Team) iterator.next();
			Integer key = team.getCurrentLevel();
			List<Team> teamList = teamMap.get(key);
			if (teamList == null){
				teamList = new ArrayList<Team>();
				teamMap.put(key, teamList);
			}
			teamList.add(team);
		}
		return teamMap;
	}
	public static List<LeagueTeam> getLeagueTable(int seasonId, int leagueId){
		LeagueTeamDAO dao = new LeagueTeamDAO();
		return dao.getLeagueTable(leagueId, seasonId);
	}
	public static boolean generateTeam(League league, String country){
		DAO dao = new DAO();
		Team team = fillTeamInformation(country, league);
		Stadium stadium = generateStadium(league.getLevel(), team.getName());
		team.setStadium(stadium);
		dao.save(team);
		
		String rawSquadTemplate = Parameter.getString("league."+league.getLevel()+".team.squadTemplate");
		String rawPlayerTemplate = Parameter.getString("league."+league.getLevel()+".team.qualityTemplate");
		List<Player> playerList = PlayerGenerator.generateAllFromRaw(rawSquadTemplate, rawPlayerTemplate, team, country);
		for (Iterator<Player> iterator = playerList.iterator(); iterator.hasNext();) {
			Player player = iterator.next();
			dao.save(player);
		}
		TacticsManager.generateDefaultTactic(team);
		return true;
	}

	private static Stadium generateStadium(int level, String name) {
		DAO dao = new DAO();
		Stadium stadium = new Stadium();
		stadium.setCapacity(Parameter.getInt("league."+level+".stadiumCapacity"));
		stadium.setName(name + " Arena");
		dao.save(stadium);
		return stadium;
	}

	private static Team fillTeamInformation(String country, League league) {
		TeamTemplate teamTemplate = choseRandomTeamTemplate(country);
		Team team = new Team();
		team.setName(teamTemplate.getTeam());
		team.setColor1(teamTemplate.getColor1());
		team.setColor2(teamTemplate.getColor2());
		team.setCurrentLeague(league.getId());
		team.setCurrentLevel(league.getLevel());
		return team;
	}

	private static TeamTemplate choseRandomTeamTemplate(String country) {
		TeamTemplateDAO dao = new  TeamTemplateDAO();
		List<TeamTemplate> templateList =dao.findAll(country);
		int index = (new Random()).nextInt(templateList.size());
		TeamTemplate team = templateList.get(index);
		team.setUsed(1);
		dao.save(team);
		return team;
	}
	
	/*
	 * For Each Team, generate:
	 * - Team League Records (League Table)
	 * - Team Player Records (Player Performance)
	 * - Team Manager Records (Manager Performance)
	 */
	public static void createLeagueTeamRecords(List<Team> teams, int seasonId, int leagueId) {
		DAO dao = new DAO();
		PlayerDAO playerDao = new PlayerDAO();
		for (Iterator<Team> iterator = teams.iterator(); iterator.hasNext();) {
			Team team = iterator.next();
			dao.save(new LeagueTeam(leagueId, team.getId(), seasonId));
			if (team.getCurrentManager() != null && team.getCurrentManager() > 0)
				dao.save(new com.fm.dal.TeamManager(team.getCurrentManager(), team.getId(), seasonId));
			List<Player> players = playerDao.getTeamPlayers(team.getId());
			for (Iterator<Player> iterator2 = players.iterator(); iterator2.hasNext();) {
				Player player = iterator2.next();
				dao.save(new TeamPlayer(player.getId(), team.getId(), seasonId));
			}
		}
	}
}

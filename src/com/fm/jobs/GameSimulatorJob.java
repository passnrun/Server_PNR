package com.fm.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fm.bll.TacticsManager;
import com.fm.dal.Game;
import com.fm.dal.GameDetail;
import com.fm.dal.Player;
import com.fm.dal.PlayerPerformance;
import com.fm.dal.Tactic;
import com.fm.dao.DAO;
import com.fm.dao.GameDAO;
import com.fm.dao.PlayerPerformanceDAO;
import com.fm.dao.TacticDAO;
import com.fm.engine.GameEngine;
import com.fm.engine.obj.GameOutput;
import com.fm.obj.FMPlayer;

public class GameSimulatorJob implements Job {
	Logger logger = Logger.getLogger(GameSimulatorJob.class);
	private static boolean running = false;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if (running)
			return;
		logger.info("Game simulator Job is started");
		running = true;
		GameDAO dao = new GameDAO();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, 1);
		List<Game> gameList = dao.getGamesByDate(now.getTime());
		if (gameList == null || gameList.size() == 0){
			logger.info("No games to play..");
			running = false;
			return;
		}
		TacticDAO tacticDao = new TacticDAO();
		for (Iterator<Game> iterator = gameList.iterator(); iterator.hasNext();) {
			Game game = iterator.next();
			GameEngine engine = new GameEngine();
			Tactic tactic1 = tacticDao.findByTeam(game.getHomeTeam().getId());
			if (tactic1 == null)
				tactic1 = TacticsManager.generateDefaultTactic(game.getHomeTeam());
			Tactic tactic2 = tacticDao.findByTeam(game.getAwayTeam().getId());
			if (tactic2 == null)
				tactic2 = TacticsManager.generateDefaultTactic(game.getAwayTeam());
			initializaGame(game);
			GameOutput out = engine.simulate(game, tactic1, tactic2);
			processGameOutput(out);
			logger.info("Game["+game.getId()+"] is completed");
			if (true)
				break;
		}
		running = false;
	}
	private void processGameOutput(GameOutput out) {
		saveGameResult(out.getGame());
		savePlayerPerformances(out.getHomeTeam(), out.getGame());
		updatePlayers(out.getHomeTeam());
		savePlayerPerformances(out.getAwayTeam(), out.getGame());
		updatePlayers(out.getAwayTeam());
	}
	private void updatePlayers(FMPlayer[] team) {
		PlayerPerformanceDAO dao = new PlayerPerformanceDAO();
		int[] lastGameIds = findLastGameIds(team[0].getCurrentTeam());
		int notPlayed = 0;
		for (int j = 0; j < team.length; j++) {
			Player p = (Player)dao.findById(Player.class, team[j].getId());
			int totalMorale = 0;
			for (int k = 0; k < 5; k++) {
				if (k>=lastGameIds.length)
					totalMorale+=60;
				else{
					PlayerPerformance perf = dao.getPlayerPerformance(lastGameIds[k], p.getId());
					//TODO: Player Quality effect on notPlayed.. If important, notPlayed value should be higher..
					if (perf == null)
						totalMorale+=(60-notPlayed);
					else
						totalMorale+=perf.getMorale();
					notPlayed = perf!=null?0:(notPlayed+5);
				}
			}
			p.setMorale(totalMorale/5);
			p.setFitness(team[j].getFitness());
			dao.save(p);
		}
		
	}
	private int[] findLastGameIds(int teamId) {
		GameDAO dao = new GameDAO();
		List<Game> gameList = dao.getLastGamesOfTeam(teamId, 5);
		int[] gameIds = new int[gameList.size()];
		for (int i = 0; i < gameIds.length; i++) {
			gameIds[i] = gameList.get(i).getId();
		}
		return gameIds;
	}
	private void savePlayerPerformances(FMPlayer[] team, Game game) {
		DAO dao = new DAO();
		for (int i = 0; i < team.length; i++) {
			FMPlayer player = team[i];
			PlayerPerformance perf = new PlayerPerformance();
			perf.setAssist(player.getAssists());
			perf.setForm((int)(player.getPerformance()*100));
			perf.setGameId(game.getId());
			perf.setGoal(player.getGoals());
			perf.setMins(90);
			perf.setPlayerId(player.getId());
			perf.setSeasonId(game.getSeasonId());
			perf.setMorale(player.getMorale());
			dao.save(perf);
		}
	}
	
	private void saveGameResult(Game game) {
		DAO dao = new DAO();
		dao.save(game);
		logger.info("Saved game["+game.getId()+"] result: "+game.getHomeTeam().getName() + " - "+game.getAwayTeam().getName() + " : "+ game.getScore1()+"-"+game.getScore2());
		List<GameDetail> gameDetals = new ArrayList<GameDetail>();
		gameDetals.addAll(game.getGameDetails());
		Collections.sort(gameDetals);
		for (Iterator<GameDetail> iterator = gameDetals.iterator(); iterator.hasNext();) {
			GameDetail gameDetail = iterator.next();
			dao.save(gameDetail);
			logger.info("Saved GameDetail["+gameDetail.getId()+"]");
		}
	}
	private void initializaGame(Game game) {
		game.setScore1(0);
		game.setScore2(0);
		game.setGameDetails(new HashSet<GameDetail>());
		calculateAttandance(game);
	}
	private void calculateAttandance(Game game) {
		//25% Sadik taraftar + Ev sahibi takimin son 5 maçta aldigi puan x 4 + rakibin son 5 maçta aldigi puan = stad'in kaç % dolu oldugu..
		int capacity = game.getHomeTeam().getStadium().getCapacity();
		int percent = 25 + getLastGamePerf(game.getHomeTeam().getId(), 5) * 4 + getLastGamePerf(game.getAwayTeam().getId(), 5);
		int attendance = capacity * percent / 100;
		game.setAttendance(attendance);
		game.setPercentAtt(percent);
	}
	private int getLastGamePerf(int teamId, int total) {
		GameDAO dao = new GameDAO();
		List<Game> gameList = dao.getLastGamesOfTeam(teamId, total);
		int points = 0;
		for (int i = 0; i < total; i++) {
			if (gameList.size() > i){
				Game game = gameList.get(i);
				if (game.getScore1() == game.getScore2())
					points++;
				else if (game.getHomeTeam().getId() == teamId && game.getScore1() > game.getScore2())
					points+=3;
				else if (game.getAwayTeam().getId() == teamId && game.getScore1() < game.getScore2())
					points+=3;
			}else
				points+=2;
		}
		return points;
	}
	public static void main(String[] args) throws JobExecutionException {
		GameSimulatorJob job = new GameSimulatorJob();
		job.execute(null);
	}
}

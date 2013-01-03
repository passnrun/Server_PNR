package com.fm.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.Player;
import com.fm.dal.Tactic;
import com.fm.dal.TacticPlayer;
import com.fm.dao.TacticPlayerDAO;
import com.fm.engine.actions.FMAction;
import com.fm.engine.actions.Undefined;
import com.fm.engine.obj.GameOutput;
import com.fm.obj.Alternative;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;
import com.fm.util.Parameter;


/**
 * Game engine simulates a football match
 */
public class GameEngine {
	private static Logger log = Logger.getLogger(GameEngine.class);
	public static final int ATTACKS_IN_GAME = 10;
	private FMPlayer[] attackingTeam;
	private FMPlayer[] defendingTeam;
	
	/**
	 * Simulates the given game, for two teams
	 * Each game will have 10 attacks, and these attacks are given according to midfield skills of teams.
	 * For each attack, actions are selected, and then they are performed..
	 * Output is Game object with result, and GameDetails
	 */
	public GameOutput simulate(Game game, Tactic tactic1, Tactic tactic2){
		FMPlayer[] homeTeam = collectPlayers(tactic1, game.getHomeTeam().getId());
		FMPlayer[] awayTeam = collectPlayers(tactic2, game.getAwayTeam().getId());
		GameOutput out = new GameOutput(game, homeTeam, awayTeam);
		for (int j = 0; j < homeTeam.length; j++) 
			homeTeam[j].setTeam(homeTeam);
		for (int j = 0; j < awayTeam.length; j++) 
			awayTeam[j].setTeam(awayTeam);
		//attendance should affect home and away team morales
		affectPlayerMoralesWithAttendence(game.getPercentAtt(), homeTeam, awayTeam);
		for (int attackNum = 0; attackNum < ATTACKS_IN_GAME; attackNum++) {
			//For each attack, compare midfield skills and randomly select the attacking team
			int min = generateAttackTime(attackNum);
			log.info("Beginning Attack, "+min+" m.");
			chooseAttackingSide(homeTeam, awayTeam);
			FMPlayer performer = FMAction.chooseRandomPlayer(getMidfieldPlayers(attackingTeam));
			performer.setTeam(attackingTeam);
			//After we know which team is attacking, select an action
			FMAction action = new Undefined(performer);
			action.setStep(1);
			while (action != null){
				action.setMinute(min);
				action.setPreventer(defendingTeam);
				action.setGame(game);
				action = action.performAction();
			}
			for (int j = 0; j < attackingTeam.length; j++) {
				attackingTeam[j].return2position();
				attackingTeam[j].setUnavailableOn(0);
				attackingTeam[j].decreasePlayerFitness(5);
			}
			for (int j = 0; j < defendingTeam.length; j++) {
				defendingTeam[j].return2position();
				defendingTeam[j].setUnavailableOn(0);
				defendingTeam[j].decreasePlayerFitness(3);
			}
			if (attackNum == 4)//Half Time
			{
				for (int j = 0; j < awayTeam.length; j++) 
					awayTeam[j].increasePlayerFitness(10);
				for (int j = 0; j < homeTeam.length; j++) 
					homeTeam[j].increasePlayerFitness(10);
			}
			//Finally modify player morales and fitnesses after the attack..
		}
		log.info("End Of Game:"+game.getScore1()+"-"+game.getScore2());
		//set Performance Magnifiers
		for (int j = 0; j < awayTeam.length; j++) 
			awayTeam[j].setPerformanceMagnifier(game.getScore2()-game.getScore1());
		for (int j = 0; j < homeTeam.length; j++) 
			homeTeam[j].setPerformanceMagnifier(game.getScore1()-game.getScore2());
		return out;
	}
	
	
	private int generateAttackTime(int attackNum) {
		int dec = 90/ATTACKS_IN_GAME;
		int n = (new Random()).nextInt(dec);
		return dec * attackNum + n;
	}


	/**
	 * This method compares midfield skills of both teams
	 * then sets one team as attacking while the other as defending
	 */
	private void chooseAttackingSide(FMPlayer[] homeTeam, FMPlayer[] awayTeam) {
		List<FMPlayer> homeTeamMidfields = getMidfieldPlayers(homeTeam);
		List<FMPlayer> awayTeamMidfields = getMidfieldPlayers(awayTeam);
		int homeMidfieldSkillTotal = 0, awayMidfieldSkillTotal = 0;
		List<Alternative> alternatives = new ArrayList<Alternative>();
		for (int i = 0; i < homeTeamMidfields.size(); i++) 
			homeMidfieldSkillTotal += homeTeamMidfields.get(i).getMidfieldSkill();
		alternatives.add(new Alternative(homeTeam, homeMidfieldSkillTotal));
		for (int i = 0; i < awayTeamMidfields.size(); i++) 
			awayMidfieldSkillTotal += awayTeamMidfields.get(i).getMidfieldSkill();
		alternatives.add(new Alternative(awayTeam, awayMidfieldSkillTotal));
		attackingTeam = (FMPlayer[])Alternative.select(alternatives);
		if (attackingTeam == homeTeam)
			defendingTeam = awayTeam;
		else
			defendingTeam = homeTeam;
			
	}
	private List<FMPlayer> getMidfieldPlayers(FMPlayer[] team) {
		return getPlayersInPosition(team, new String[]{Position.DEFENSIVE_MIDFIELDER, Position.MIDFIELDER, Position.ATTACKING_MIDFIELDER});
	}
	public static List<FMPlayer> getPlayersInPosition(FMPlayer[] team, String[] positions) {
		String arr2str = "";
		if (positions != null)
			for (int i = 0; i < positions.length; i++) 
				arr2str+=("("+positions[i]+")");
		else
			arr2str="all";
		List<FMPlayer> players = new ArrayList<FMPlayer>();
		for (int i = 0; i < team.length; i++) 
			if (isPlayerOnPositions(team[i], positions))
				players.add(team[i]);
		return players;
	}
	public static List<FMPlayer> getPlayersInPositionAndSide(FMPlayer[] team, String[] positions, String[] sides) {
		String arr2str = "";
		if (positions != null)
			for (int i = 0; i < positions.length; i++) 
				arr2str+=("("+positions[i]+")");
		else
			arr2str="all";
		List<FMPlayer> players = new ArrayList<FMPlayer>();
		for (int i = 0; i < team.length; i++) 
			if (isPlayerOnPositions(team[i], positions))
				for (int j = 0; j < sides.length; j++) 
					if (sides[j].equals(team[i].getCurrentPosition().getSide()))
						players.add(team[i]);
		return players;
	}

	public static boolean isPlayerOnPositions(FMPlayer player, String[] positions) {
		if (positions == null || positions.length == 0)
			return true;
		for (int j = 0; j < positions.length; j++) 
			if (positions[j].equals(player.getCurrentPosition().getPosition()))
				return true;
		return false;
	}

	private FMPlayer[] collectPlayers(Tactic tactic, int team_id) {
		TacticPlayerDAO dao = new TacticPlayerDAO();
		List<TacticPlayer> tacticPlayers = tactic.getFirstElevent();
		FMPlayer[] players = new FMPlayer[tacticPlayers.size()];
		int i = 0;
		for (Iterator<TacticPlayer> iterator = tacticPlayers.iterator(); iterator.hasNext();) {
			TacticPlayer tacticPlayer = iterator.next();
			Player p = (Player)dao.findById(Player.class, tacticPlayer.getPlayerId());
			players[i++]=new FMPlayer(p, tacticPlayer.getPosition());
		}
		return players;
	}
	private void affectPlayerMoralesWithAttendence(int percent_att, FMPlayer[] homeTeam, FMPlayer[] awayTeam) {
		int deltaPercent = (percent_att - Parameter.getInt("normal_attendance_percent"))/4;
		for (int i = 0; i < homeTeam.length; i++) 
			homeTeam[i].increasePlayerMorale(deltaPercent);
		for (int i = 0; i < awayTeam.length; i++) 
			awayTeam[i].decreasePlayerMorale(deltaPercent);
	}
}

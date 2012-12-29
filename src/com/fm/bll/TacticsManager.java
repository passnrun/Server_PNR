package com.fm.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fm.dal.Player;
import com.fm.dal.Tactic;
import com.fm.dal.TacticPlayer;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.dao.PlayerDAO;
import com.fm.dao.TacticDAO;
import com.fm.dao.TacticPlayerDAO;
import com.fm.engine.obj.Formation;
import com.fm.engine.obj.PlayerComperator;
import com.fm.obj.Position;

public class TacticsManager {
	
	public static void main(String[] args) {
		Tactic tactic = TacticsManager.getTeamTactic(1);
		System.out.println(tactic);
	}
	public static Tactic getTeamTactic(int teamId) {
		DAO dao = new DAO();
		PlayerDAO pDao = new PlayerDAO();
		TacticDAO tDao = new TacticDAO();
		TacticPlayerDAO tpDao = new TacticPlayerDAO();
		List<Player> playerList = pDao.getTeamPlayers(teamId);
		Tactic tactic = tDao.findByTeam(teamId);
		if (tactic == null)
			tactic = generateDefaultTactic(new Team(teamId));
		List<TacticPlayer> tacticPlayers = tpDao.findByTactic(tactic.getId());
		for (Iterator iterator = tacticPlayers.iterator(); iterator.hasNext();) {
			TacticPlayer tacticPlayer = (TacticPlayer) iterator.next();
			boolean found = false;
			int i = -1;
			while (!found && ++i < playerList.size()){
				if (tacticPlayer.getPlayerId() == playerList.get(i).getId()){
					tacticPlayer.setPlayer(playerList.remove(i));
					found = true;
				}
			}
		}
		List<TacticPlayer> subs = new ArrayList<TacticPlayer>();
		for (Iterator iterator = playerList.iterator(); iterator.hasNext() && subs.size() < 8;) {
			Player p = (Player) iterator.next();
			TacticPlayer tp = new TacticPlayer();
			tp.setPlayerId(p.getId());
			tp.setPlayer(p);
			tp.setPosition(p.getPosition());
			subs.add(tp);
		}
		tactic.setTeamId(teamId);
		tactic.setFirstElevent(tacticPlayers);
		tactic.setSubs(subs);
		return tactic;
	}
	public static Tactic generateDefaultTactic(Team team) {
		DAO dao = new DAO();
		PlayerDAO pDao = new PlayerDAO();
		List<Player> playerList = pDao.getTeamPlayers(team.getId());
		Tactic tactic = new Tactic();
		Formation formation= Formation.getInstance(Formation.FORMATION_4_4_2);
		tactic.setFormation(Formation.FORMATION_4_4_2);
		tactic.setTeamId(team.getId());
		dao.save(tactic);
		for (int i = 0; i < formation.getPositions().length; i++) {
			Position pos = formation.getPositions()[i];
			TacticPlayer tp = new TacticPlayer();
			Player p = chooseBestPlayerForPosition(pos, playerList);
			playerList.remove(p);
			tp.setPlayerId(p.getId());
			tp.setPosition(pos.toString());
			tp.setTacticId(tactic.getId());
			dao.save(tp);
		}
		return tactic;
	}

	public static Player chooseBestPlayerForPosition(Position position, List<Player> playerList) {
		PlayerComperator playerComp = new PlayerComperator(position);
		Collections.sort(playerList, playerComp);
		return playerList.get(playerList.size() -1);
	}
	public static boolean saveTeamTactic(int teamId, List<TacticPlayer> players) {
		DAO dao = new DAO();
		TacticDAO tDao = new TacticDAO();
		TacticPlayerDAO tpDao = new TacticPlayerDAO();
		Tactic tactic = tDao.findByTeam(teamId);
		List<TacticPlayer> tacticPlayers = tpDao.findByTactic(tactic.getId());
		while(tacticPlayers.size() > 0){
			TacticPlayer tp = tacticPlayers.remove(0);
			TacticPlayer matchingTP = null;
			for (Iterator iterator = players.iterator(); iterator.hasNext();) {
				TacticPlayer updatedTP = (TacticPlayer) iterator.next();
				if (updatedTP.getPlayerId().intValue() == tp.getPlayerId().intValue()){
					matchingTP = updatedTP;
					if (!tp.getPosition().equals(updatedTP.getPosition())){
						tp.setPosition(updatedTP.getPosition());
						dao.save(tp);
					}
					break;
				}
			}
			if (matchingTP == null)
				dao.delete(tp);
			else
				players.remove(matchingTP);
		}
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			TacticPlayer newPlayer = (TacticPlayer) iterator.next();
			newPlayer.setTacticId(tactic.getId());
			dao.save(newPlayer);
		}
		return true;
	}
}





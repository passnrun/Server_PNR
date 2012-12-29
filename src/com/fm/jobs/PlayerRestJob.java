package com.fm.jobs;

import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fm.dal.Player;
import com.fm.dal.Team;
import com.fm.dao.PlayerDAO;

public class PlayerRestJob implements Job {
	public static final int DAILY_REST = 15;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PlayerDAO dao = new PlayerDAO();
		List<Team> teams = (List<Team>)dao.findAll(Team.class);
		for (Iterator<Team> iterator = teams.iterator(); iterator.hasNext();) {
			Team team = (Team) iterator.next();
			List<Player> players = dao.getTeamPlayers(team.getId());
			for (Iterator<Player> iterator2 = players.iterator(); iterator2.hasNext();) {
				Player player = (Player) iterator2.next();
				int prev = player.getFitness();
				int next = (prev + DAILY_REST)>100?100:(prev+DAILY_REST);
				if (next>prev){
					player.setFitness(next);
					dao.save(player);
				}
			}
		}
	}

}

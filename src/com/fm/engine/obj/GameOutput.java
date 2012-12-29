package com.fm.engine.obj;

import com.fm.dal.Game;
import com.fm.obj.FMPlayer;

public class GameOutput {
	private Game game;
	private FMPlayer[] homeTeam;
	private FMPlayer[] awayTeam;
	
	public GameOutput(Game g, FMPlayer[] hT, FMPlayer[] aT) {
		game = g;
		homeTeam = hT;
		awayTeam = aT;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public FMPlayer[] getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(FMPlayer[] homeTeam) {
		this.homeTeam = homeTeam;
	}
	public FMPlayer[] getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(FMPlayer[] awayTeam) {
		this.awayTeam = awayTeam;
	}
	
}

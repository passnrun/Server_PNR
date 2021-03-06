package com.fm.dal;

// Generated Nov 24, 2011 11:03:11 AM by Hibernate Tools 3.4.0.CR1

/**
 * GameDetail generated by hbm2java
 */
public class GameDetail implements java.io.Serializable, Comparable<GameDetail> {
    public static final int GOAL = 1;
    public static final int YELLOW = 2;
    public static final int RED = 3;
    public static final int IN = 4;
    public static final int OUT =5;
    
	private int id;
	private Game game;
	private Integer minute;
	private int action;
	private int team;
	private String player;
	private String logMessage;
	private Integer logLevel;

	public GameDetail() {
	}

	public GameDetail(int id) {
		this.id = id;
	}

	public GameDetail(GameDetail gd) {
		this.game = gd.getGame();
		this.minute = gd.getMinute();
		this.action = gd.getAction();
		this.player = gd.getPlayer();
		this.team = gd.getTeam();
		this.logLevel = gd.getLogLevel();
	}
	public GameDetail(Game game, Integer minute, int action, String performer, int team, Integer logLevel) {
		this.game = game;
		this.minute = minute;
		this.action = action;
		this.player = performer;
		this.team = team;
		this.logLevel = logLevel;
	}
	public GameDetail(Game game, Integer minute, String logMesssage, Integer logLevel) {
		this.game = game;
		this.minute = minute;
		this.logMessage = logMesssage;
		this.logLevel = logLevel;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public int compareTo(GameDetail gd) {
		return minute - gd.getMinute();
	}
	
	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

}

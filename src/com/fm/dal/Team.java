package com.fm.dal;

// Generated Nov 24, 2011 11:03:11 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Team generated by hbm2java
 */
public class Team implements java.io.Serializable {

	private int id;
	private Stadium stadium;
	private String name;
	private String color1;
	private String color2;
	private Integer currentManager;
	private Integer currentLeague;
	private Set<Game> gamesForTeam1 = new HashSet<Game>(0);
	private Set<Tactic> tactics = new HashSet<Tactic>(0);
	private Set<Game> gamesForTeam2 = new HashSet<Game>(0);

	public Team() {
	}

	public Team(int id) {
		this.id = id;
	}

	public Team(int id, Stadium stadium, String name, String color1,
			String color2, Integer currentManager, Set<Game> gamesForTeam1,
			Set<Tactic> tactics, Set<Game> gamesForTeam2) {
		this.id = id;
		this.stadium = stadium;
		this.name = name;
		this.color1 = color1;
		this.color2 = color2;
		this.currentManager = currentManager;
		this.gamesForTeam1 = gamesForTeam1;
		this.tactics = tactics;
		this.gamesForTeam2 = gamesForTeam2;
	}
	public Team(Team t) {
		this.id = t.getId();
		this.stadium = t.getStadium();
		this.name = t.getName();
		this.color1 = t.getColor1();
		this.color2 = t.getColor2();
		this.currentManager = t.getCurrentManager();
		this.gamesForTeam1 = t.getGamesForTeam1();
		this.tactics = t.getTactics();
		this.gamesForTeam2 = t.getGamesForTeam2();
		this.currentLeague = t.getCurrentLeague();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Stadium getStadium() {
		return this.stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor1() {
		return this.color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return this.color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public Integer getCurrentManager() {
		return this.currentManager;
	}

	public void setCurrentManager(Integer currentManager) {
		this.currentManager = currentManager;
	}

	public Set<Game> getGamesForTeam1() {
		return this.gamesForTeam1;
	}

	public void setGamesForTeam1(Set<Game> gamesForTeam1) {
		this.gamesForTeam1 = gamesForTeam1;
	}

	public Set<Tactic> getTactics() {
		return this.tactics;
	}

	public void setTactics(Set<Tactic> tactics) {
		this.tactics = tactics;
	}

	public Set<Game> getGamesForTeam2() {
		return this.gamesForTeam2;
	}

	public void setGamesForTeam2(Set<Game> gamesForTeam2) {
		this.gamesForTeam2 = gamesForTeam2;
	}

	public Integer getCurrentLeague() {
		return currentLeague;
	}

	public void setCurrentLeague(Integer currentLeague) {
		this.currentLeague = currentLeague;
	}

}

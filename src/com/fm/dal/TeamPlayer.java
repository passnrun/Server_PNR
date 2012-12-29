package com.fm.dal;

// Generated Nov 24, 2011 11:03:11 AM by Hibernate Tools 3.4.0.CR1

/**
 * TeamPlayer generated by hbm2java
 */
public class TeamPlayer implements java.io.Serializable {

	private int id;
	private Integer playerId;
	private Integer teamId;
	private Integer seasonId;
	private Integer played;
	private Integer goals;
	private Integer assists;
	private Integer against;
	private Integer yellowCard;
	private Integer redCard;
	private Integer avgForm;
	

	public TeamPlayer() {
	}

	public TeamPlayer(int id) {
		this.id = id;
	}
	public TeamPlayer( Integer playerId, Integer teamId, Integer seasonId){
		this(0, playerId, teamId, seasonId, 0, 0,0,0,0,0);
	}
	public TeamPlayer(int id, Integer playerId, Integer teamId,
			Integer seasonId, Integer played, Integer goals, Integer assists,
			Integer against, Integer yellowCard, Integer redCard) {
		this.id = id;
		this.playerId = playerId;
		this.teamId = teamId;
		this.seasonId = seasonId;
		this.played = played;
		this.goals = goals;
		this.assists = assists;
		this.against = against;
		this.yellowCard = yellowCard;
		this.redCard = redCard;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getSeasonId() {
		return this.seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public Integer getPlayed() {
		return this.played;
	}

	public void setPlayed(Integer played) {
		this.played = played;
	}

	public Integer getGoals() {
		return this.goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	public Integer getAssists() {
		return this.assists;
	}

	public void setAssists(Integer assists) {
		this.assists = assists;
	}

	public Integer getAgainst() {
		return this.against;
	}

	public void setAgainst(Integer against) {
		this.against = against;
	}

	public Integer getYellowCard() {
		return this.yellowCard;
	}

	public void setYellowCard(Integer yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Integer getRedCard() {
		return this.redCard;
	}

	public void setRedCard(Integer redCard) {
		this.redCard = redCard;
	}
}

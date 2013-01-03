package com.fm.dal;

import java.util.List;

// Generated Nov 24, 2011 11:03:11 AM by Hibernate Tools 3.4.0.CR1

/**
 * Tactic generated by hbm2java
 */
public class Tactic implements java.io.Serializable {
	
	public static final int FORMATION_442 = 1;
	public static final int FORMATION_352 = 2;
	
	private int id;
	private Integer teamId;
	private Integer formation;
	private transient List<TacticPlayer> firstElevent;
	private transient List<TacticPlayer> subs;
	private transient List<TacticPlayer> reserves;
	
	public List<TacticPlayer> getReserves() {
		return reserves;
	}
	public void setReserves(List<TacticPlayer> reserves) {
		this.reserves = reserves;
	}
	public Tactic(Tactic tactic) {
		this.teamId = tactic.getTeamId();
		this.firstElevent = tactic.getFirstElevent();
		this.subs = tactic.getSubs();
	}
	public Tactic() {
	}

	public Tactic(int id) {
		this.id = id;
	}

	public Tactic(int id, Integer teamId, Integer formation) {
		this.id = id;
		this.teamId = teamId;
		this.formation = formation;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getFormation() {
		return this.formation;
	}

	public void setFormation(Integer formation) {
		this.formation = formation;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public List<TacticPlayer> getFirstElevent() {
		return firstElevent;
	}

	public void setFirstElevent(List<TacticPlayer> firstElevent) {
		this.firstElevent = firstElevent;
	}

	public List<TacticPlayer> getSubs() {
		return subs;
	}

	public void setSubs(List<TacticPlayer> subs) {
		this.subs = subs;
	}

}

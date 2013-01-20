package com.fm.mw.obj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fm.dal.Player;
import com.fm.dal.PlayerPerformance;
import com.fm.mw.JSON;
import com.fm.mw.JSSkill;
import com.fm.util.DateUtil;

public class JSPlayerDetail extends Player implements JSON {
	
	private JSPlayerPerformance seasonPerformance;
	
	public JSPlayerDetail(Player p, PlayerPerformance seasonPerfm) {
		super(p);
		seasonPerformance = new JSPlayerPerformance(seasonPerfm);
	}
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		DateFormat format = new SimpleDateFormat("dd.MM.yy");
		JSSkill skill = new JSSkill(getPlayerSkill());
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"latestForm\" :").append("\""+getFormattedLatestForm()+"\"").append(" ,");
		sb.append("\"position\" :").append("\""+getPosition()+"\"").append(" ,");
		sb.append("\"quality\" :").append("\""+getQuality()).append("\" ,");
		sb.append("\"fitness\" :").append("\""+getFitness()).append("\" ,");
		sb.append("\"banned\" :").append("\""+getBanned()).append("\" ,");
		sb.append("\"morale\" :").append("\""+getMorale()).append("\" ,");
		sb.append("\"name\" :").append("\""+getFullName()+"\"").append(" ,");
		sb.append("\"birthdate\" :").append("\""+format.format(getBirthdate())+"\"").append(" ,");
		sb.append("\"age\" :").append(DateUtil.getAge(getBirthdate())).append(" ,");
		sb.append("\"team\" :").append("{\"id\" :").append(getTeamObj().getId()).append(" ,").append("\"name\" :").append("\""+getTeamObj().getName()+"\"}, ");
		sb.append("\"skill\" :").append(skill.toJSON()).append(" ,");
		sb.append("\"seasonPerf\" :").append(seasonPerformance.toJSON()).append(" }");
		return sb.toString();
	}
	public JSPlayerPerformance getSeasonPerformance() {
		return seasonPerformance;
	}
	public void setSeasonPerformance(JSPlayerPerformance seasonPerformance) {
		this.seasonPerformance = seasonPerformance;
	}

}

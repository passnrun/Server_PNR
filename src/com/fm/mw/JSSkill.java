package com.fm.mw;

import com.fm.dal.PlayerSkill;

public class JSSkill implements JSON{
	private PlayerSkill skill;
	
	public JSSkill(PlayerSkill ps) {
		skill = ps;
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"handling\" :").append(skill.getHandling()).append(" ,");
		sb.append("\"tackling\" :").append(skill.getTackling()).append(" ,");
		sb.append("\"passing\" :").append(skill.getPassing()).append(" ,");
		sb.append("\"creativity\" :").append(skill.getCreativity()).append(" ,");
		sb.append("\"positioning\" :").append(skill.getPositioning()).append(" ,");
		sb.append("\"teamwork\" :").append(skill.getTeamwork()).append(" ,");
		sb.append("\"heading\" :").append(skill.getHeading()).append(" ,");
		sb.append("\"shooting\" :").append(skill.getShooting()).append(" ,");
		sb.append("\"dribbling\" :").append(skill.getDribbling()).append(" ,");
		sb.append("\"reflexes\" :").append(skill.getReflexes()).append(" ,");
		sb.append("\"finishing\" :").append(skill.getFinishing()).append(" ,");
		sb.append("\"crossing\" :").append(skill.getCrossing()).append(" ,");
		sb.append("\"technique\" :").append(skill.getTechnique()).append(" ,");
		sb.append("\"firstTouch\" :").append(skill.getFirstTouch()).append(" ,");
		sb.append("\"composure\" :").append(skill.getComposure()).append(" ,");
		sb.append("\"agility\" :").append(skill.getAgility()).append(" ,");
		sb.append("\"acceleration\" :").append(skill.getAcceleration()).append(" ,");
		sb.append("\"stamina\" :").append(skill.getStamina()).append(" ,");
		sb.append("\"strength\" :").append(skill.getStrength()).append("}");
		return sb.toString();
	}
}

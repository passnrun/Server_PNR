package com.fm.mw.obj;

import com.fm.dal.TacticPlayer;
import com.fm.mw.JSON;
import com.fm.obj.Position;
import com.fm.util.DateUtil;

public class JSTacticPlayer extends TacticPlayer implements JSON, Comparable<JSTacticPlayer> {
	
	
	public JSTacticPlayer(TacticPlayer tp) {
		super(tp);
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getId()).append(" ,");
		sb.append("\"latestForm\" :").append("\""+format(getPlayer().getLatestForm())+"\"").append(" ,");
		sb.append("\"position\" :").append("\""+getPosition()+"\"").append(" ,");
		sb.append("\"nativePosition\" :").append("\""+getPlayer().getPosition()+"\"").append(" ,");
		sb.append("\"playerId\" :").append(getPlayerId()).append(" ,");
		sb.append("\"quality\" :").append("\""+getPlayer().getQuality()).append("\" ,");
		sb.append("\"name\" :").append("\""+getPlayer().getShortName()+"\"").append(" ,");
		sb.append("\"age\" :").append(DateUtil.getAge(getPlayer().getBirthdate())).append("}");
		return sb.toString();
	}

	private static String format(String latestForm) {
		if (latestForm == null || latestForm.trim().length() == 0)
			return "-";
		String[] vals = latestForm.split("/");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < vals.length; i++) {
			if (i > 0)
				buffer.append("/");
			float val =  Float.parseFloat(vals[i]) / 100;
			buffer.append(String.format("%(.0f", val));
		}
		return buffer.toString();
	}

	@Override
	public int compareTo(JSTacticPlayer tp) {
		Position p1 = new Position(getPosition());
		Position p2 = new Position(tp.getPosition());
		int diff = Position.getPositionIndex(p1.getPosition())-Position.getPositionIndex(p2.getPosition());
		if (diff == 0)
			return Position.getSideIndex(p1.getSide()) - Position.getSideIndex(p2.getSide());
		else
			return diff;
	}

}

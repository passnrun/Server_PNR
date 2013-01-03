package com.fm.mw.obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fm.dal.Tactic;
import com.fm.dal.TacticPlayer;
import com.fm.mw.JSON;

public class JSTactic extends Tactic implements JSON {
	private JSList jsFirstEleven;
	private JSList jsSubs;
	private JSList jsReserves;
	
	public JSTactic(Tactic tactic) {
		super(tactic);
		List<JSTacticPlayer> firstEleven = new ArrayList<JSTacticPlayer>();
		List<JSTacticPlayer> subs = new ArrayList<JSTacticPlayer>();
		List<JSTacticPlayer> reserves = new ArrayList<JSTacticPlayer>();
		for (Iterator iterator = tactic.getFirstElevent().iterator(); iterator.hasNext();) {
			TacticPlayer tp = (TacticPlayer) iterator.next();
			firstEleven.add(new JSTacticPlayer(tp));
		}
		for (Iterator iterator = tactic.getSubs().iterator(); iterator.hasNext();) {
			TacticPlayer tp = (TacticPlayer) iterator.next();
			subs.add(new JSTacticPlayer(tp));
		}
		for (Iterator iterator = tactic.getReserves().iterator(); iterator.hasNext();) {
			TacticPlayer tp = (TacticPlayer) iterator.next();
			reserves.add(new JSTacticPlayer(tp));
		}
		Collections.sort(firstEleven);
		Collections.sort(subs);
		jsFirstEleven = new JSList(new ArrayList<JSON>());
		jsFirstEleven.getList().addAll(firstEleven);
		jsSubs = new JSList(new ArrayList<JSON>());
		jsSubs.getList().addAll(subs);
		jsReserves = new JSList(new ArrayList<JSON>());
		jsReserves.getList().addAll(reserves);
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\" :").append(getTeamId()).append(" ,");
		sb.append("\"firstTeam\" :").append(jsFirstEleven.toJSON()).append(" ,");
		sb.append("\"reserves\" :").append(jsReserves.toJSON()).append(" ,");
		sb.append("\"subs\" :").append(jsSubs.toJSON()).append("}");
		return sb.toString();
	}

}

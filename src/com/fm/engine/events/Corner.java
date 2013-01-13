package com.fm.engine.events;

import com.fm.engine.GameEngine;
import com.fm.engine.actions.FMAction;
import com.fm.engine.actions.Heading;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Corner extends FMAction {
	public Corner(FMPlayer p) {
		setPerformer(p);
	}

	@Override
	public FMAction perform() {
		FMPlayer target = choosePlayerByHeading(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
				new String[]{Position.DEFENDER, Position.DEFENSIVE_MIDFIELDER, Position.MIDFIELDER, Position.ATTACKING_MIDFIELDER, Position.STRIKER}));
		return new Heading(target);
	}
	@Override
	public void updateFitnesses() {}

}

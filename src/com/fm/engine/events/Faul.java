package com.fm.engine.events;

import com.fm.engine.GameEngine;
import com.fm.engine.actions.FMAction;
import com.fm.engine.actions.Pass;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Faul extends FMAction{
	public Faul(FMPlayer p) {
		setPerformer(p);
	}
	
	@Override
	public FMAction perform() {
		int performance = performance(getPerformer().getFitness());
			
		FMPlayer player = choosePlayerByPositioning(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
				new String[]{Position.DEFENSIVE_MIDFIELDER, Position.MIDFIELDER, Position.ATTACKING_MIDFIELDER}));
		return new Pass(player);
	}
	@Override
	public void updateFitnesses() {}
}

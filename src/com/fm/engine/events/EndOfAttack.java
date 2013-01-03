package com.fm.engine.events;

import com.fm.engine.actions.FMAction;
import com.fm.obj.FMPlayer;

public class EndOfAttack extends FMAction {
	
	public EndOfAttack(FMPlayer p, FMPlayer def) {
		setPerformer(p);
		setPreventer(def);
	}

	@Override
	public FMAction perform() {
		return null;
	}
	@Override
	public void updateFitnesses() {}

}

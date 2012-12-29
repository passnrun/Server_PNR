package com.fm.engine.events;

import com.fm.dal.GameDetail;
import com.fm.engine.actions.Dribbling;
import com.fm.engine.actions.FMAction;
import com.fm.engine.actions.Heading;
import com.fm.engine.actions.LongPass;
import com.fm.engine.actions.Pass;
import com.fm.engine.actions.Shoot;
import com.fm.obj.FMPlayer;

public class Goal extends FMAction {
	public Goal(FMPlayer p) {
		setPerformer(p);
	}
	
	@Override
	public void setPreventer(FMPlayer p) {
	}

	@Override
	public FMAction perform() {
		int team = getPerformer().getCurrentTeam();
		int side = 0;
		if (team == game.getHomeTeam().getId()){
			game.setScore1(game.getScore1()+1);
			side = 0;
		}else if (team == game.getAwayTeam().getId()){
			game.setScore2(game.getScore2()+1);
			side = 1;
		}
		game.getGameDetails().add(new GameDetail(game, getMinute(), GameDetail.GOAL, getPerformer().getName(),side, 1));
		return null;
	}
	@Override
	public void updateFitnesses() {}
	@Override
	protected void updatePerformances() {
		FMAction action = previousAction;
		while (action != null){
			if (action instanceof Shoot || action instanceof Heading){
				//action.getPerformer().addPerformance(5);
				action.getPerformer().setGoals(action.getPerformer().getGoals()+1);
			}else if (action instanceof Pass || action instanceof LongPass || action instanceof Heading){
				//Assist
				//action.getPerformer().addPerformance(3);
				action.getPerformer().setAssists(action.getPerformer().getAssists()+1);
				break;
			}else if (action instanceof Dribbling){
				//Self Create
				//action.getPerformer().addPerformance(2);
			}
			action = action.previousAction;
		}
	}
}

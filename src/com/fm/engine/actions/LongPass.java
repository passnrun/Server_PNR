package com.fm.engine.actions;

import org.apache.log4j.Logger;

import com.fm.bll.LogManager;
import com.fm.engine.GameEngine;
import com.fm.engine.events.EndOfAttack;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class LongPass extends FMAction {
	private static LogManager logger = LogManager.getInstance();
	
	public LongPass(FMPlayer p) {
		setPerformer(p);
	}
	@Override
	public FMAction perform() {
		int performanceScore = performance(getActionSkill(getPerformer()));
		int defenderScore = 0;
		if (getPreventer() != null){
			defenderScore = performance(getDefendSkill(getPreventer()));
		}
		logger.info(game, minute, getPerformer().getShortName() + " performs a long pass...");
		result = compare(performanceScore, defenderScore);
		if (result >= 4){
			FMPlayer target = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
					new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER})));
			target.setTeam(getPerformer().getTeam());
			logger.debug(game, minute, "Great Pass! " + target.getShortName() +  " has the opportunity.");
			return new Shoot(target);
		}else if (result  > 1){
			FMPlayer target = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), new String[]{Position.findNextPosition(getPerformer().getCurrentPosition().getPosition(), 2)})));
			target.setTeam(getPerformer().getTeam());
			return new Undefined(target);
		}else if (result >= 0){
			FMPlayer target = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), new String[]{Position.findNextPosition(getPerformer().getCurrentPosition().getPosition(), 1)})));
			target.setTeam(getPerformer().getTeam());
			return new Undefined(target);
		}else{
			if (getPreventer() != null)
				logger.info(game, minute, getPreventer().getShortName()+" stole the ball! What a defence!");
			else
				logger.debug(game, minute, "Terrible pass by "+getPerformer().getShortName()+", what was he thinking!");
			
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}
	
	public static int getCoefficient(String position){
		if (Position.GOALKEEPER.equals(position))return 10;
		if (Position.DEFENDER.equals(position))return 9;
		if (Position.DEFENSIVE_MIDFIELDER.equals(position))return 6;
		return 0;
	}
	
	@Override
	public void updateFitnesses() {
		if (getPerformer() != null)
			getPerformer().decreasePlayerFitness(1);
		if (getPreventer() != null)
			getPreventer().decreasePlayerFitness(1);
	}

	//passing(2x) + creativity (x) + technique (x)
	public static int getActionSkill(FMPlayer p){
		int total = p.getPassing()*2 + p.getCreativity() + p.getTechnique();
		return total / 4;
	}
	
	//positioning(2x) + first touch(2x) + tackling(x) + heading (x)
	public static int getDefendSkill(FMPlayer p){
		int total = p.getPositioning()*2 + p.getFirstTouch()*2 + p.getTackling() + p.getHeading(); 
		return total / 6;
	}
}

package com.fm.engine.actions;

import com.fm.bll.LogManager;
import com.fm.engine.GameEngine;
import com.fm.engine.events.EndOfAttack;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Crossing extends FMAction {
	LogManager logger = new LogManager();
	
	public Crossing(FMPlayer p) {
		setPerformer(p);
	}
	
	/*
	 * Performer performs the action.. which is a numeric value how well the pass is performed..
	 * Then, according to performer's action performance (numeric value mentioned above) 
	 * one of the following results is selected: Bad Pass, Pass, Cross Pass, Goal Pass
	 * Then, according to selected result, a preventer can be selected and try to steal the ball.    
	 */
	@Override
	public FMAction perform() {
		int performanceScore = performance(getActionSkill(getPerformer()));
		int defendScore = 0;
		if (getPreventer() != null)
			defendScore =performance(getDefendSkill(getPreventer()));
		if (getPreventer() != null)
			logger.info(game, minute, getPerformer().getName() + " performs a cross["+performanceScore +"], while defender["+getPreventer().getName()+"] tries to defend["+defendScore+"]");
		result = compare(performanceScore, defendScore);
		String[] oppositeSides = oppositeSides(getPerformer().getCurrentPosition().getSide());
		if (result >= 4){
			logger.debug(game, minute, "Goal Cross: Great cross["+performanceScore+"], defend["+defendScore+"] couldn't even move.. ");
			FMPlayer scorer = choosePlayerByHeading(filterForStep(GameEngine.getPlayersInPositionAndSide(getPerformer().getTeam(), new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER},oppositeSides)));
			scorer.setTeam(getPerformer().getTeam());
			return new Heading(scorer);
		}else if (result  > 0){
			logger.debug(game, minute, "Good Cross: cross["+performanceScore+"] couldnt be defended["+defendScore+"] ");
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPositionAndSide(getPerformer().getTeam(), new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER}, oppositeSides)));
			scorer.setTeam(getPerformer().getTeam());
			return new Undefined(scorer);
		}else{
			logger.debug(game, minute, "Missed Ball: cross["+performanceScore+"].. good defense["+defendScore+"] ");
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}
	private String[] oppositeSides(String side){
		if (Position.LEFT.equals(side))
			return new String[]{Position.RIGHT, Position.CENTER};
		if (Position.RIGHT.equals(side))
			return new String[]{Position.LEFT, Position.CENTER};
		return new String[]{Position.CENTER};		
	}
	//crossing(4x) + creativity (x) + technique (2x)
	public static int getActionSkill(FMPlayer p){
		int total = p.getCrossing() * 4 + p.getCreativity() + p.getTechnique();
		return total / 6;
	}
	// positioning (3x) + heading (3x) + teamwork (2x) 
	public static int getDefendSkill(FMPlayer p){
		int total = p.getPositioning()*3 +p.getHeading()*3 + p.getTeamwork()*2;
		return total / 8;
	}
	public static int getCoefficient(Position position){
		if (Position.ATTACKING_MIDFIELDER.equals(position.getPosition())
				&& !Position.CENTER.equals(position.getSide()))
			return 10;
		return 0;
	}

	@Override
	public void updateFitnesses() {
		if (getPerformer()!=null)
			getPerformer().decreasePlayerFitness(2);
		if (getPreventer()!=null)
			getPreventer().decreasePlayerFitness(2);
	}
}
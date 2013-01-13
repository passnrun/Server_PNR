package com.fm.engine.actions;

import com.fm.bll.LogManager;
import com.fm.engine.GameEngine;
import com.fm.engine.events.EndOfAttack;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Crossing extends FMAction {
	LogManager logger = LogManager.getInstance();
	
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
		result = compare(performanceScore, defendScore);
		String[] oppositeSides = oppositeSides(getPerformer().getCurrentPosition().getSide());
		logger.info(game, minute, getPerformer().getShortName()+" with the cross!");
		if (result >= 4){
			FMPlayer scorer = choosePlayerByHeading(filterForStep(GameEngine.getPlayersInPositionAndSide(getPerformer().getTeam(), new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER},oppositeSides)));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, "Superb chance for "+getPerformer().getShortName());
			return new Heading(scorer);
		}else if (result  > 0){
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPositionAndSide(getPerformer().getTeam(), new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER}, oppositeSides)));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, "Into the area... "+getPerformer().getShortName() + " meets the ball.");
			return new Undefined(scorer);
		}else{
			logger.info(game, minute, "wasted opportunity... "+getPerformer().getShortName() + " sent the ball up to the hills.");
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
			getPerformer().decreasePlayerFitness(1);
		if (getPreventer()!=null)
			getPreventer().decreasePlayerFitness(1);
	}
}
package com.fm.engine.actions;

import org.apache.log4j.Logger;

import com.fm.bll.LogManager;
import com.fm.engine.GameEngine;
import com.fm.engine.events.EndOfAttack;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Pass extends FMAction {
	LogManager logger = LogManager.getInstance();
	
	public Pass(FMPlayer p) {
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
		if (getPreventer()!=null){
			defendScore = performance(getDefendSkill(getPreventer()));
		}
		
		result = compare(performanceScore, defendScore);
		if (result >= 4){
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
					new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER})));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, "Superb pass by "+getPerformer().getShortName());
			return new Shoot(scorer);
		}else if (result  > 0){
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
					new String[]{Position.STRIKER, Position.ATTACKING_MIDFIELDER, Position.MIDFIELDER})));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, "Well played by "+getPerformer().getShortName());
			return new Undefined(scorer);
		}else if (result == 0){
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
					new String[]{getPerformer().getCurrentPosition().getPosition()})));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, getPerformer().getShortName() + " plays to " + scorer.getShortName());
			return new Undefined(scorer);
		} else if (result > -3){
			FMPlayer scorer = choosePlayerByPositioning(filterForStep(GameEngine.getPlayersInPosition(getPerformer().getTeam(), 
					new String[]{Position.DEFENDER, Position.GOALKEEPER})));
			scorer.setTeam(getPerformer().getTeam());
			logger.info(game, minute, getPerformer().getShortName() + " plays back to " + scorer.getShortName());
			return new Undefined(scorer);
		}else{
			if (getPreventer() != null)
				logger.info(game, minute, "Intercepted by "+getPreventer().getShortName());
			else
				logger.info(game, minute, getPerformer().getShortName()+" wastes the ball.");
			return new EndOfAttack(getPerformer(), getPreventer());
			
		}
	}

	//passing(4x) + creativity (x) + technique (2x)
	public static int getActionSkill(FMPlayer p){
		int total = p.getPassing() * 4 + p.getCreativity() + p.getTechnique();
		return total / 6;
	}
	//positoning + first touch + tackling(x)*3 
	public static int getDefendSkill(FMPlayer p){
		int total = p.getPositioning() +p.getFirstTouch() + p.getTackling()*3; 
		return total / 5;
	}
	public static int getCoefficient(String position){
		if(Position.GOALKEEPER.equals(position))return 10;
		if(Position.DEFENDER.equals(position))return 10;
		if(Position.DEFENSIVE_MIDFIELDER.equals(position))return 10;
		if(Position.MIDFIELDER.equals(position))return 9;
		if(Position.ATTACKING_MIDFIELDER.equals(position))return 8;
		if(Position.STRIKER.equals(position))return 6;
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
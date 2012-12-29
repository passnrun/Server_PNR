package com.fm.engine.actions;

import com.fm.bll.LogManager;
import com.fm.engine.events.EndOfAttack;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Dribbling extends FMAction {
	private LogManager logger = new LogManager();
	public Dribbling(FMPlayer p) {
		setPerformer(p);
	}
	@Override
	public FMAction perform() {
		int performanceScore = performance(getActionSkill(getPerformer()));
		int defenderScore = 0;
		if (getPreventer() != null)
			defenderScore = performance(getDefendSkill(getPreventer()));
		if (getPreventer() != null)
			logger.info(game, minute, getPerformer().getName() + " performs a dribblings["+performanceScore+"] while defender tries to prevent["+defenderScore+"]");
		result = compare(performanceScore, defenderScore);
		if (result >= 0){
			getPerformer().advance();
			logger.info(game, minute, "Good dribbling["+performanceScore+"] defender couldnt handle him["+defenderScore+"]");
			return new Undefined(getPerformer());
		}else{
			logger.info(game, minute, "Good defense["+defenderScore+"], atacker couldnt keep the ball["+performanceScore+"]");
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}
	

	public static int getCoefficient(String position) {
		if (Position.STRIKER.equals(position))
			return 6;
		if (Position.ATTACKING_MIDFIELDER.equals(position))
			return 7;
		if (Position.MIDFIELDER.equals(position))
			return 7;
		return 0;
	}
	@Override
	public void updateFitnesses() {
		if (getPerformer() != null)
			getPerformer().decreasePlayerFitness(8);
		if (getPreventer() != null)
			getPreventer().decreasePlayerFitness(4);
	}

	//dribbling(3x) +  agility (2x) + acceleration(2x) + technique (x)
	public static int getActionSkill(FMPlayer p){
		int total = p.getDribbling()*3 + p.getAgility()*2 + p.getAcceleration()*2 + p.getTechnique();
		return total / 8;
	}
	//acceleration(4x) + agility(4x) + tackling(3x) + positioning(2x) + first touch(2x)
	public static int getDefendSkill(FMPlayer p){
		int total = p.getAcceleration()*4 + p.getAgility()*4 + p.getTackling()*3 + p.getPositioning()*2 + p.getFirstTouch()*2; 
		return total / 15;
	}
	

}

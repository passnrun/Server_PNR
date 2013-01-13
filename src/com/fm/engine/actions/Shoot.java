package com.fm.engine.actions;

import java.util.List;

import org.apache.log4j.Logger;

import com.fm.bll.LogManager;
import com.fm.engine.GameEngine;
import com.fm.engine.events.Corner;
import com.fm.engine.events.EndOfAttack;
import com.fm.engine.events.Goal;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Shoot extends FMAction{
	private static LogManager logger = LogManager.getInstance();
	
	public Shoot(FMPlayer p) {
		setPerformer(p);
	}
	@Override
	public FMAction perform() {
		int shootingValue = performance(getActionSkill(getPerformer()));
		int defendingValue = performance(getDefendSkill(getPreventer()));
		result = compare(shootingValue, defendingValue);
		logger.info(game, minute, getPerformer().getShortName()+" shoots!");
		if (result > 0){
			logger.info(game, minute, "Great Goal by " + getPerformer().getShortName() + "!");
			return new Goal(getPerformer());
		}else if (result == 0){
			boolean isCorner = (r.nextInt(10)%2)==0;
			if(isCorner){
				logger.info(game, minute, getPreventer().getShortName()+" prevented the goal, Corner!");
				return new Corner(getPerformer());
			}else{
				logger.info(game, minute, "Great shoot by " + getPerformer().getShortName()+" but he misses the target!");
				return new EndOfAttack(getPerformer(), getPreventer());
			}
		}else{ //means result < 0
			logger.debug(game, minute, getPerformer().getShortName()+" missed the opportunity, but "+getPreventer().getShortName()+" left him no space.");
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}
	
	@Override
	public void setPreventer(FMPlayer[] defenderTeam) {
		List<FMPlayer> gkList = GameEngine.getPlayersInPosition(defenderTeam, new String[]{Position.GOALKEEPER});
		if (gkList.size()>0)
			setPreventer(gkList.get(0));
	}
	
	public static int getCoefficient(String position){
		if (Position.STRIKER.equals(position))return 10;
		if (Position.ATTACKING_MIDFIELDER.equals(position))return 7;
		if (Position.MIDFIELDER.equals(position))return 5;
		if (Position.DEFENSIVE_MIDFIELDER.equals(position))return 4;
		if (Position.DEFENDER.equals(position))return 1;
		return 0;
	}
	
	@Override
	public void updateFitnesses() {
		getPerformer().decreasePlayerFitness(1);
		getPreventer().decreasePlayerFitness(1);
	}
	
	//finishing (2x) + technique (2x) + creativity(x) + composure(x)
	public static int getActionSkill(FMPlayer p){
		int total = p.getFinishing()*2 + p.getTechnique()*2 + p.getCreativity() + p.getComposure();
		return total / 6;
	}
	
	//reflexes (2x) + Handling(2x) + postioning(x) 
	public static int getDefendSkill(FMPlayer p){
		int total = p.getReflexes()*2 + p.getPositioning() + p.getHandling()*2;
		return total / 5;
	}

}

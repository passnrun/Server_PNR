package com.fm.engine.actions;

import com.fm.bll.LogManager;
import com.fm.dal.GameDetail;
import com.fm.engine.events.EndOfAttack;
import com.fm.engine.events.Faul;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public class Dribbling extends FMAction {
	private LogManager logger = LogManager.getInstance();
	public Dribbling(FMPlayer p) {
		setPerformer(p);
	}
	@Override
	public FMAction perform() {
		int performanceScore = performance(getActionSkill(getPerformer()));
		int defenderScore = 0;
		logger.info(game, minute, getPerformer().getShortName() + " is with the ball.");
		if (getPreventer() != null){
			defenderScore = performance(getDefendSkill(getPreventer()));
			if (defenderScore < 10)
				return processDefenderAction();
		}
		result = compare(performanceScore, defenderScore);
		if (result >= 0){
			getPerformer().advance();
			if (getPreventer() != null)
				logger.info(game, minute, getPerformer().getShortName()+ " passes "+getPreventer().getShortName());
			else
				logger.info(game, minute, getPerformer().getShortName()+ " found an opportunity");
			return new Undefined(getPerformer());
		}else{
			logger.info(game, minute, "Superb tackle by "+getPreventer().getShortName());
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}

	protected Faul processDefenderAction() {
		logger.info(game, minute, "Bad tackle by "+getPreventer().getShortName()+" refree whistles a faul.");
		int team = getPreventer().getCurrentTeam();
		int score = performance(50);
		if (score > 40)
			;
		else if (score > 10){
			if (getPreventer().getYellows() == 1){
				logger.info(game, minute, "Its a red card! "+getPreventer().getShortName()+" already had a yellow");
				LogManager.getInstance().save(new GameDetail(game, getMinute(), GameDetail.RED, getPreventer().getName(),team, 1));
				getPreventer().setRed(1);
			} else {
				logger.info(game, minute, "It is a yellow card! "+getPreventer().getShortName()+" should be more careful now");
				LogManager.getInstance().save(new GameDetail(game, getMinute(), GameDetail.YELLOW, getPreventer().getName(),team, 1));
				getPreventer().setYellows(1);
			}
		} else {
			logger.info(game, minute, "Its a red card! The refree directly showed "+getPreventer().getShortName()+" a red card!");
			LogManager.getInstance().save(new GameDetail(game, getMinute(), GameDetail.RED, getPreventer().getName(),team, 1));
			getPreventer().setRed(1);
		}
		return new Faul(getPerformer());
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
			getPerformer().decreasePlayerFitness(1);
		if (getPreventer() != null)
			getPreventer().decreasePlayerFitness(1);
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

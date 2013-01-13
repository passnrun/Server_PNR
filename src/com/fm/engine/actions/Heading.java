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

public class Heading extends FMAction {
	private static LogManager logger = LogManager.getInstance();
	public Heading(FMPlayer p) {
		setPerformer(p);
	}
	@Override
	public void setPreventer(FMPlayer[] defenderTeam) {
		List<FMPlayer> allButGK = GameEngine.getPlayersInPosition(defenderTeam, new String[]{Position.DEFENDER, Position.DEFENSIVE_MIDFIELDER, Position.MIDFIELDER, Position.ATTACKING_MIDFIELDER, Position.STRIKER});
		setPreventer(choosePlayerByHeading(allButGK));
	}
	@Override
	public void updateFitnesses() {
		if (getPerformer() != null)
			getPerformer().decreasePlayerFitness(1);
		if (getPreventer() != null)
			getPreventer().decreasePlayerFitness(1);
	}

	@Override
	public FMAction perform() {
		int attScore = performance(getActionSkill(getPerformer()));
		int defScore = 0;
		if (getPreventer() != null){
			defScore = performance(getDefendSkill(getPreventer()));
		}
		result = compare(attScore, defScore);
		logger.info(game, minute, getPerformer().getShortName()+" hits the ball!");
		if (result == 5){
			logger.info(game, minute, "Goal!!!, What a head!!!");
			return new Goal(getPerformer());
		}else if (result >= 3){
			List<FMPlayer> attacking = GameEngine.getPlayersInPosition(getPerformer().getTeam(), new String[]{Position.ATTACKING_MIDFIELDER, Position.STRIKER});
			FMPlayer owner = choosePlayerByPositioning(attacking);
			logger.info(game, minute, owner.getShortName()+" meets the ball");
			return new Undefined(owner);
		}else if (result >= 0){
			logger.info(game, minute, "Saved by the Goalkeeper! Corner!");
			return new Corner(getPerformer());
		}else{
			logger.info(game, minute, "Terrible heading by "+getPerformer().getShortName());
			return new EndOfAttack(getPerformer(), getPreventer());
		}
	}
		//heading(3x) + finishing(2x) + positioning(2x) + composure (x)
		public static int getActionSkill(FMPlayer p){
			int total = p.getHeading()*3 + p.getFinishing()*2 + p.getPositioning()*2 + p.getComposure();
			return total / 8;
		}
		
		//positioning (x) + heading(x)
		public static int getDefendSkill(FMPlayer p){
			int total = p.getPositioning() + p.getHeading(); 
			return total / 2;
		}
}

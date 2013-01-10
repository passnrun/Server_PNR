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
	private static LogManager logger = new LogManager();
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
			getPerformer().decreasePlayerFitness(2);
		if (getPreventer() != null)
			getPreventer().decreasePlayerFitness(2);
	}

	@Override
	public FMAction perform() {
		int attScore = performance(getActionSkill(getPerformer()));
		int defScore = 0;
		if (getPreventer() != null){
			defScore = performance(getDefendSkill(getPreventer()));
		}
		result = compare(attScore, defScore);
		if (result == 5){
			logger.info(game, minute, "Goal!: great heading["+attScore+"] couldnt be defended["+defScore+"], in the nets..");
			return new Goal(getPerformer());
		}else if (result >= 3){
			logger.info(game, minute, "Pass: good heading["+attScore+"] couldnt be defended["+defScore+"]");
			List<FMPlayer> attacking = GameEngine.getPlayersInPosition(getPerformer().getTeam(), new String[]{Position.ATTACKING_MIDFIELDER, Position.STRIKER});
			return new Undefined(choosePlayerByPositioning(attacking));
		}else if (result >= 0){
			logger.info(game, minute, "Corner: heading["+attScore+"] is defended["+defScore+"]");
			return new Corner(getPerformer());
		}else{
			logger.info(game, minute, "Wasted: heading["+attScore+"] is defended["+defScore+"]");
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

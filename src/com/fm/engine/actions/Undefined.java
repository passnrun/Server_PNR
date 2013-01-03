package com.fm.engine.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fm.obj.Alternative;
import com.fm.obj.FMPlayer;

public class Undefined extends FMAction {
	private static Logger log = Logger.getLogger(Undefined.class);
	
	public Undefined(FMPlayer p) {
		setPerformer(p);
	}

	@Override
	public void updateMorales() {}
	public void updateFitnesses() {}
	public int nextStep(){return getStep();}
	/**
	 * Choose an Action for the performer among all possible alternatives.
	 * First, calculate the probabilities according to some formulas..
	 * Then, randomly decide what to do..
	 */
	@Override
	public FMAction perform() {
		log.info("Choosing an action for performer:"+getPerformer().getName()+"," + getPerformer().getCurrentPosition());
		//Collect Alternatives for getPerformer()
		List<Alternative> alternatives = new ArrayList<Alternative>();
		//For Dribbling Possibility is : Dribbling Skill * Selfishness (MAX - TeamWork) * Position Coefficient
		Dribbling dribbling = new Dribbling(getPerformer());
		int dribblingPossibility = Dribbling.getActionSkill(getPerformer()) * getPerformer().getSelfishness() * Dribbling.getCoefficient(getPerformer().getCurrentPosition().getPosition()) / 10;
		log.debug("Dribbling possibility is:"+dribblingPossibility);
		alternatives.add(new Alternative(dribbling, dribblingPossibility));
		//For Long Pass Possibility is : Long Pass Skill * TeamWork * Position Coefficient
		LongPass longPass = new LongPass(getPerformer());
		int longPassPossibility = LongPass.getActionSkill(getPerformer()) * getPerformer().getTeamwork() * LongPass.getCoefficient(getPerformer().getCurrentPosition().getPosition())/ 10;
		alternatives.add(new Alternative(longPass, longPassPossibility));
		log.debug("Long Pass possibility is:"+longPassPossibility);
		//For Long Pass Possibility is : Long Pass Skill * TeamWork * Position Coefficient
		Crossing crossing = new Crossing(getPerformer());
		int crossingPossibility = Crossing.getActionSkill(getPerformer()) * getPerformer().getTeamwork() * Crossing.getCoefficient(getPerformer().getCurrentPosition())/ 10;
		alternatives.add(new Alternative(crossing, crossingPossibility));
		log.debug("Crossing possibility is:"+crossingPossibility);
		//For Pass Possibility is : Pass Skill * Teamwork * Position Coeffient
		Pass pass = new Pass(getPerformer());
		int passingPossibility = Pass.getActionSkill(getPerformer()) * getPerformer().getTeamwork() * Pass.getCoefficient(getPerformer().getCurrentPosition().getPosition()) / 10;
		alternatives.add(new Alternative(pass, passingPossibility));
		log.debug("Pass possibility is:"+passingPossibility);
		//For Shoot Possibility is : Shoot Skill * Selfishness (MAX - Teamwork) * Position Coefficient
		Shoot shoot = new Shoot(getPerformer());
		int shootingProbability = Shoot.getActionSkill(getPerformer()) * getPerformer().getSelfishness() * Shoot.getCoefficient(getPerformer().getCurrentPosition().getPosition()) / 10;
		alternatives.add(new Alternative(shoot, shootingProbability));
		log.debug("Shoot possibility is:"+shootingProbability);
		return (FMAction)Alternative.select(alternatives);
	}

}

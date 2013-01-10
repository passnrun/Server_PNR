package com.fm.engine.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.engine.GameEngine;
import com.fm.obj.Alternative;
import com.fm.obj.FMPlayer;
import com.fm.obj.Position;

public abstract class FMAction {
	public static int ACTIONCOMPARE_UNIT = 5;
	private static Logger logger = Logger.getLogger(FMAction.class);
	private FMPlayer performer;
	private FMPlayer preventer;
	protected int result;
	protected Random r = new Random();
	protected Game game;
	public FMAction previousAction;
	protected int minute;
	protected int step;
	
	public abstract void updateFitnesses();
	
	public FMAction performAction(){
		FMAction nextAction = perform();
		updateMorales();
		updateFitnesses();
		updatePerformances();
		updateStepInfo();
		if (nextAction != null){
			nextAction.previousAction = this;
			nextAction.setStep(nextStep());
		}
		return nextAction;
	}
	
	private void updateStepInfo() {
		if (performer != null)
			performer.setUnavailableOn(step+1);
		if (preventer != null)
			preventer.setUnavailableOn(step+1);
	}

	protected int nextStep() {
		return ++step;
	}

	protected void updatePerformances() {
		if (getPerformer()!=null)
			getPerformer().addPerformance(result);
		if (getPreventer()!=null)
			getPreventer().addPerformance(-1*result);
	}
	public FMPlayer getPreventer(){
		return preventer;
	}
	public FMPlayer getPerformer(){
		return performer;
	}
	
	public void updateMorales(){
		if (getPerformer()!=null)
			getPerformer().increasePlayerMorale(result);
		if (getPreventer()!=null)
			getPreventer().decreasePlayerMorale(result);
	}
	
	public void setPerformer(FMPlayer performer) {
		this.performer = performer;
	}
	public void setPreventer(FMPlayer[] defenderTeam) {
		String side = getPerformer().getCurrentPosition().getSide();
		String[] defendingPositions = Position.opposites(getPerformer().getCurrentPosition().getPosition());
		List<FMPlayer> possibleDefenders = filterForStep(GameEngine.getPlayersInPositionAndSide(defenderTeam, defendingPositions, new String[]{Position.oppositeSide(side)}));
		if (possibleDefenders == null || possibleDefenders.size() == 0)
			possibleDefenders = filterForStep(GameEngine.getPlayersInPositionAndSide(defenderTeam, defendingPositions, Position.oppositeSideNext(side)));
		List<Alternative> defenderAlternatives = new ArrayList<Alternative>();
		for (Iterator<FMPlayer> iterator = possibleDefenders.iterator(); iterator.hasNext();) {
			FMPlayer player = iterator.next();
			defenderAlternatives.add(new Alternative(player, Position.sideMatch(player.getCurrentPosition().getSide(), getPerformer().getCurrentPosition().getSide())));
		}
		preventer = (FMPlayer)Alternative.select(defenderAlternatives);
		if (preventer != null)
			preventer.moveTo(Position.opposite(getPerformer().getCurrentPosition()));
	}
	
	public List<FMPlayer> filterForStep(List<FMPlayer> players) {
		List<FMPlayer> list = new ArrayList<FMPlayer>();
		for (Iterator<FMPlayer> iterator = players.iterator(); iterator.hasNext();) {
			FMPlayer player = iterator.next();
			if (player.getUnavailableOn() != getStep())
				list.add(player);
		}
		return list;
	}

	public void setPreventer(FMPlayer p){
		preventer = p;
	}
	
	public abstract FMAction perform();
	
	
	
	/**
	 * For a skill of 80, the performance is calculated as:
	 * Skill : 80, Missing : 20
	 * Remove 40 records from 0-50
	 *TODO: Add another set in order to create a triangle.. so that performance value will be distributed more fairly
	 * Remove 10 records from 50-100
	 * and selected from the set of 50
	 */
	public static int performance(int skill){
		List<Integer> setLow = new ArrayList<Integer>(); //0-50
		List<Integer> setHigh = new ArrayList<Integer>();//50-100
		for(int i = 1; i<=50;i++){
			setLow.add(i);
			setHigh.add(i+50);
		}
		Random r = new Random();
		//Process Removal of Lows
		for (int i = 0; i < skill/2 && i < setLow.size(); i++) 
			setLow.remove(r.nextInt(setLow.size()));
		//Process Removal of Highs
		for (int i = 0; i < (FMPlayer.MAX_SKILLVALUE - skill)/2 && i < setHigh.size(); i++) 
			setHigh.remove(r.nextInt(setHigh.size()));
		List<Integer> set = new ArrayList<Integer>();
		set.addAll(setLow);set.addAll(setHigh);
		return set.get(r.nextInt(set.size()));
	}
	
	/**
	 * Choose a player randomly
	 */
	public static FMPlayer chooseRandomPlayer(List<FMPlayer> players) {
		List<Alternative> alternatives = new ArrayList<Alternative>();
		for (Iterator<FMPlayer> iterator = players.iterator(); iterator.hasNext();) {
			FMPlayer player = iterator.next();
			alternatives.add(new Alternative(player, 1));
		}
		return (FMPlayer)Alternative.select(alternatives);
	}
	/**
	 * Choose a player randomly
	 */
	public static FMPlayer choosePlayerByPositioning(List<FMPlayer> players) {
		List<Alternative> alternatives = new ArrayList<Alternative>();
		for (Iterator<FMPlayer> iterator = players.iterator(); iterator.hasNext();) {
			FMPlayer player = iterator.next();
			alternatives.add(new Alternative(player, player.getPositioning()));
		}
		return (FMPlayer)Alternative.select(alternatives);
	}
	/**
	 * Choose a player randomly
	 */
	public static FMPlayer choosePlayerByHeading(List<FMPlayer> players) {
		List<Alternative> alternatives = new ArrayList<Alternative>();
		for (Iterator<FMPlayer> iterator = players.iterator(); iterator.hasNext();) {
			FMPlayer player = iterator.next();
			alternatives.add(new Alternative(player, player.getHeading()));
		}
		return (FMPlayer)Alternative.select(alternatives);
	}
	
	/**
	 * Compare attacking and defender scores and find a number between +ACTIONCOMPARE_UNIT and -ACTIONCOMPARE_UNIT
	 * "+" means attack wins, while "-" means the defense..
	 * max(a, d) / ACTIONCOMPARE_UNIT defines 1 unit..
	 * (a -d) / one unit is the returning number..
	 * in case one unit means zero.. it should be calculated as 1.. but i dont think that's a probability
	 */
	public static int compare(int attackingScore, int defenderScore){
		logger.info("comparing attacking["+attackingScore+"] vs defender["+defenderScore+"]");
		int oneUnit = Math.max(attackingScore, defenderScore)/ACTIONCOMPARE_UNIT;
		if (oneUnit == 0){
			logger.warn("One unit is zero! making it 1");
			oneUnit = 1;
		}
		float diff = (float)(attackingScore-defenderScore)/oneUnit;
		return Math.round(diff);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}

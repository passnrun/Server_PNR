package com.fm.obj;

import com.fm.dal.Player;
import com.fm.dal.PlayerSkill;

/**
 * This class is customized for the game engine..
 * During the game play, player skills decrease as he gets tired..
 * or players can perform well as their morale level increases..
 */
public class FMPlayer extends Player {
	public static final int MAX_SKILLVALUE = 100;
	
	private int fitness;
	private int morale;
	private int performance = 60;
	private int perfMagnifier=100;
	private int goals=0;
	private int yellows=0;
	private int red=0;
	private int assists=0;
	private int selfCreate=0;
	//In which step the player made a performance (as peformer or preventer), so in next step he is unavailable
	private int unavailableOn=0;
	private Position currentPosition;
	private Position originalPosition;
	private PlayerSkill skill;
	private FMPlayer[] team;
	
	public FMPlayer(Player p, String position) {
		super(p.getId(), p.getShortName(), p.getPosition(), p.getBirthdate(), p.getCurrentTeam(), p.getFitness(), p.getMorale(), p.getQuality(), p.getPlayerSkill());
		fitness = p.getFitness();
		morale = p.getMorale();
		skill = p.getPlayerSkill();
		currentPosition = new Position(position);
		originalPosition = new Position(position);
		setQuality(p.getQuality());
	}
	
	public int getMidfieldSkill(){
		return (getCrossing() + getCreativity() + getTackling() + getAgility() + getPassing() + getTeamwork())/6;
	}

	public int getTackling(){
		return calculateCurrentSkill(skill.getTackling());
	}
	public Integer getFitness(){
		return fitness;
	}
	public Integer getMorale(){
		return morale;
	}
	
	public int getPassing(){
		return calculateCurrentSkill(skill.getPassing());
	}
	public int getCreativity(){
		return calculateCurrentSkill(skill.getCreativity());
	}
	public int getPositioning(){
		return calculateCurrentSkill(skill.getPositioning());
	}
	public int getTeamwork(){
		//Mental Skill doesn't change during the game
		return skill.getTeamwork();
	}
	public int getHeading(){
		return calculateCurrentSkill(skill.getHeading());
	}
	public int getShooting(){
		return calculateCurrentSkill(skill.getShooting());
	}
	
	private int calculateCurrentSkill(Integer value) {
		//perform fitness effect
		value = value * fitness / MAX_SKILLVALUE;
		//performs morale effect
		int moraleNormal = MAX_SKILLVALUE / 2;
		//if morale of the player is higher than normal, then the player performs better..
		//otherwise, he performs worse..
		if (morale > moraleNormal)
			value += value * (morale - moraleNormal) / MAX_SKILLVALUE;
		else
			value -= value * (morale - moraleNormal) / MAX_SKILLVALUE;
		if (value <= 0)
			value = 1;
		return value;
	}
	
	public void increasePlayerMorale(int delta){
		morale+=delta;
		if (morale>MAX_SKILLVALUE)
			morale = MAX_SKILLVALUE;
	}
	public void decreasePlayerMorale(int delta){
		morale-=delta;
	}
	public void increasePlayerFitness(int delta){
		fitness+=delta;
		if (fitness>MAX_SKILLVALUE)
			fitness=MAX_SKILLVALUE;
	}
	public void decreasePlayerFitness(int delta){
		fitness-=delta;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public int getDribbling() {
		return calculateCurrentSkill(skill.getDribbling());
	}

	public int getSelfishness() {
		return MAX_SKILLVALUE - getTeamwork();
	}

	public FMPlayer[] getTeam() {
		return team;
	}

	public void setTeam(FMPlayer[] team) {
		this.team = team;
	}
	
	public void advance(){
		currentPosition = new Position(Position.findNextPosition(currentPosition.getPosition(), 1)+"("+currentPosition.getSide()+")");
	}
	public void moveTo(Position p){
		if (!Position.GOALKEEPER.equals(currentPosition.getPosition()))
			currentPosition = p;
	}
	
	public void return2position(){
		currentPosition = originalPosition;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getName()+"["+getPosition()+"]("+getQuality()+")");
		buffer.append(",Morale:"+morale);
		buffer.append(",Fitness:"+fitness);
		buffer.append(",Performance:"+getPerformance());
		buffer.append(",Goals:"+getGoals());
		buffer.append(",Assists:"+getAssists());
		buffer.append(",Self Create:"+getSelfCreate());
		buffer.append(",Perf Magnifier:"+getPerfMagnifier());
		buffer.append(",Original Perf:"+performance);
		return buffer.toString();
	}
	
	public float getPerformance() {
		float perf =  (float)performance*perfMagnifier/1000;
		if (perf > 10)
			return 10f;
		return perf;
	}
	
	public void addPerformance(int delta){
		performance+=delta;
		if (performance > MAX_SKILLVALUE)
			performance = MAX_SKILLVALUE;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}
	public void setPerformanceMagnifier(int diff){
		if (diff < 0)
			perfMagnifier+=(diff*5);
		else
			perfMagnifier+=(diff*10);
		perfMagnifier+=(goals*10);
		perfMagnifier+=(assists*5);
		perfMagnifier+=(selfCreate*3);
	}
	public int getPerfMagnifier(){
		return perfMagnifier;
	}

	public int getSelfCreate() {
		return selfCreate;
	}

	public void setSelfCreate(int selfCreate) {
		this.selfCreate = selfCreate;
	}
	public Integer getReflexes() {
		return calculateCurrentSkill(skill.getReflexes());
	}

	public Integer getFinishing() {
		return calculateCurrentSkill(skill.getFinishing());
	}
	public Integer getCrossing() {
		return calculateCurrentSkill(skill.getCrossing());
	}

	public Integer getTechnique() {
		return calculateCurrentSkill(skill.getTechnique());
	}

	public Integer getFirstTouch() {
		return calculateCurrentSkill(skill.getFirstTouch());
	}

	public Integer getComposure() {
		return calculateCurrentSkill(skill.getComposure());
	}

	public Integer getAgility() {
		return calculateCurrentSkill(skill.getAgility());
	}

	public Integer getAcceleration() {
		return calculateCurrentSkill(skill.getAcceleration());
	}

	public Integer getStamina() {
		return calculateCurrentSkill(skill.getStamina());
	}

	public Integer getStrength() {
		return calculateCurrentSkill(skill.getStrength());
	}
	
	public Integer getHandling() {
		return calculateCurrentSkill(skill.getHandling());
	}
	
	public void setUnavailableOn(int c) {
		unavailableOn = c;
	}

	public int getYellows() {
		return yellows;
	}

	public void setYellows(int yellows) {
		this.yellows = yellows;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public boolean isUnavailable(int step) {
		return red > 0 || unavailableOn == step;
	}
	

	
}

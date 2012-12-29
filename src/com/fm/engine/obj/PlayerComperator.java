package com.fm.engine.obj;

import java.util.Comparator;

import com.fm.dal.Player;
import com.fm.obj.Position;

public class PlayerComperator implements Comparator<Player> {
	private Position target;
	
	public PlayerComperator(Position tp) {
		target = tp;
	}
	public int compare(Player p1, Player p2) {
		Position pos1 = new Position(p1.getPosition());
		Position pos2 = new Position(p2.getPosition());
		
		int score1 = getPositionScore(pos1) + getPositionSideScore(pos1) + p1.getQuality()*3;
		int score2 = getPositionScore(pos2) + getPositionSideScore(pos2) + p2.getQuality()*3;
		return score1 - score2;
	}
	private int getPositionSideScore(Position pos) {
		if (pos.getSide().equals(target.getSide()))
			return 18;
		if (Position.LEFT.equals(target.getSide()) || Position.RIGHT.equals(target.getSide()))
			if (Position.LEFT.equals(pos.getSide()) || Position.RIGHT.equals(pos.getSide()))
				return 12;
		return 6;
	}
	private int getPositionScore(Position pos) {
		 return 10 * (6 - Math.abs(Position.getPositionIndex(target.getPosition()) - Position.getPositionIndex(pos.getPosition())));
	}

}

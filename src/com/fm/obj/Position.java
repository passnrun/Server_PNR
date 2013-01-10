package com.fm.obj;

public class Position implements Comparable<Position>{
	
	//Positions
	public static final String GOALKEEPER						= "GK";
	public static final String DEFENDER 						= "D";
	public static final String MIDFIELDER 						= "M";
	public static final String DEFENSIVE_MIDFIELDER 			= "DM";
	public static final String ATTACKING_MIDFIELDER 			= "AM";
	public static final String STRIKER 							= "ST";
	
	//Sides
	public static final String LEFT			= "L";
	public static final String RIGHT		= "R";
	public static final String CENTER		= "C";

	public static final String[] positionArr = new String[]{GOALKEEPER, DEFENDER, MIDFIELDER, DEFENSIVE_MIDFIELDER, ATTACKING_MIDFIELDER, STRIKER};
	public static final String[] sideArr = new String[]{LEFT, CENTER, RIGHT};
	
	
	private String position;
	private String side;
	
	public Position(String code) {
		decriptCode(code);
	}

	private void decriptCode(String code) {
		String[] parts = code.split("\\(");
		position = parts[0];
		if (parts.length == 1){
			side = CENTER;
		}else{
			side = parts[1].replace(')', ' ');
			side = side.trim();
		}
	}

	public String getPosition() {
		return position;
	}

	public String getSide() {
		return side;
	}
	
	@Override
	public String toString() {
		if (GOALKEEPER.equals(position) || STRIKER.equals(position))
			return position;
		return position+"("+side+")";
	}
	
	public static void main(String[] args) {
		System.out.println(new Position("D(C)"));
	}

	public static String[] opposites(String pos) {
		if (GOALKEEPER.equals(pos))
			return new String[]{STRIKER, ATTACKING_MIDFIELDER};
		if (DEFENDER.equals(pos))
			return new String[]{STRIKER, ATTACKING_MIDFIELDER};
		if (DEFENSIVE_MIDFIELDER.equals(pos))
			return new String[]{ATTACKING_MIDFIELDER, MIDFIELDER};
		if (MIDFIELDER.equals(pos))
			return new String[]{MIDFIELDER, DEFENSIVE_MIDFIELDER};
		if (ATTACKING_MIDFIELDER.equals(pos))
			return new String[]{DEFENSIVE_MIDFIELDER, MIDFIELDER };
		if (STRIKER.equals(pos))
			return new String[]{DEFENDER, GOALKEEPER};
		return null;
	}
	public static String oppositeSide(String side) {
		if (LEFT.equals(side))
			return RIGHT;
		if (CENTER.equals(side))
			return CENTER;
		if (RIGHT.equals(side))
			return LEFT;
		return CENTER;
	}
	public static String[] oppositeSideNext(String side) {
		if (LEFT.equals(side) || RIGHT.equals(side))
			return new String[]{CENTER};
		if (CENTER.equals(side))
			return new String[]{LEFT, RIGHT};
		return new String[]{};
	}
	
	public static int sideMatch(String side1, String side2){
		if (CENTER.equals(side1)){
			if (CENTER.equals(side2))
				return 2;
			else
				return 1;
		}
		if (LEFT.equals(side1)){
			if (RIGHT.equals(side2))
				return 2;
			else if (CENTER.equals(side2))
				return 1;
			else
				return 0;
		}
		if (RIGHT.equals(side1)){
			if (LEFT.equals(side2))
				return 2;
			else if (CENTER.equals(side2))
				return 1;
			else
				return 0;
		}
		return 0;
	}
	
	public static int getPositionIndex(String position) {
		for (int i = 0; i < Position.positionArr.length; i++) 
			if (Position.positionArr[i].equals(position))
				return i;
		return 6;
	}
	public static int getSideIndex(String side) {
		for (int i = 0; i < Position.sideArr.length; i++) 
			if (Position.sideArr[i].equals(side))
				return i;
		return 3;
	}
	public static String findNextPosition(String position, int inc) {
		int currentPostiionIndex = 0;
		for (int i = 0; i < Position.positionArr.length; i++) 
			if (Position.positionArr[i].equals(position)){
				currentPostiionIndex = i;
				break;
			}
		if (position.length() <= (currentPostiionIndex + inc))
			return Position.STRIKER;
		else if (0 > (currentPostiionIndex + inc))
			return Position.GOALKEEPER;
		else
			return Position.positionArr[currentPostiionIndex + inc];
	}

	public static Position opposite(Position currentPosition) {
		String oppPos = opposites(currentPosition.getPosition())[0];
		String oppSide = oppositeSide(currentPosition.getSide());
		return new Position(oppPos+"("+oppSide+")");
	}

	@Override
	public int compareTo(Position p2) {
		int diff = getPositionIndex(getPosition())-Position.getPositionIndex(p2.getPosition());
		if (diff == 0)
			return getSideIndex(getSide()) - getSideIndex(p2.getSide());
		else
			return diff;
	}
}

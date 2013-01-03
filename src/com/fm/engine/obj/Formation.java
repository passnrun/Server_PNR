package com.fm.engine.obj;

import com.fm.obj.Position;

//Is only used by Default Tactics..
public class Formation {
	public static final int FORMATION_4_4_2 		= 0;
	public static final int FORMATION_4_5_1 		= 1;
	public static final int FORMATION_4_3_3 		= 2;
	public static final int FORMATION_4_3_1_2 		= 3;
	public static final int FORMATION_5_3_2 		= 4;
	public static final int FORMATION_SUBS 			= 5;
	
	private static final String[] formation_codes = new String[]{
			"GK,D(L),D(C),D(C),D(R),M(L),M(C),M(C),M(R),ST,ST",
			"GK,D(L),D(C),D(C),D(R),M(L),M(C),M(C),M(C),M(R),ST",
			"GK,D(L),D(C),D(C),D(R),M(C),M(C),M(C),ST,ST,ST",
			"GK,D(L),D(C),D(C),D(R),DM(C),M(C),M(C),AM(C),ST,ST",
			"GK,D(L),D(C),D(C),D(C),D(R),M(C),M(C),M(C),ST,ST",
			"GK,D(C),D(C),M(C),M(C),ST,ST"};
	
	private Position[] positions = new Position[11];
	
	private Formation(Position[] p){
		positions = p;
	}
	
	public static Formation getInstance(int formation){
		String code = formation_codes[formation];
		String[] positionCodes = code.split(",");
		Position[] positions = new Position[positionCodes.length];
		for (int i = 0; i < positionCodes.length; i++) 
			positions[i] = new Position(positionCodes[i]);
		return new Formation(positions);
	}

	public Position[] getPositions() {
		return positions;
	}
	@Override
	public String toString() {
		String line = "";
		for (int i = 0; i < positions.length; i++) {
			line+=("\n"+positions[i]);
		}
		return line;
	}
	
}

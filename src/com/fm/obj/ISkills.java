package com.fm.obj;

public interface ISkills {
	public static final int[] GK_SKILL_RELEVANCE = new int[]{
		2,  //Strength
		5,  //Reflexes
		5,  //Handling
		3,  //Tackling
		3,  //Passing
		0,  //Heading
		0,  //Finishing
		1,  //Dribbling
		1,  //Crossing
		2,  //Technique
		2,  //First Touch
		3,  //Creativity
		4,  //Composure
		5,  //Positioning
		4,  //Teamwork
		5,  //Agility
		2,  //Acceleration
		0   //Stamina
	};
	public static final int[] DEF_SKILL_RELEVANCE = new int[]{
		5,  //Strength
		0,  //Reflexes
		0,  //Handling
		5,  //Tackling
		3,  //Passing
		5,  //Heading
		1,  //Finishing
		2,  //Dribbling
		2,  //Crossing
		2,  //Technique
		2,  //First Touch
		2,  //Creativity
		2,  //Composure
		5,  //Positioning
		4,  //Teamwork
		3,  //Agility
		3,  //Acceleration
		3   //Stamina
	};
	public static final int[] DM_SKILL_RELEVANCE = new int[]{
		5,  //Strength
		0,  //Reflexes
		0,  //Handling
		4,  //Tackling
		4,  //Passing
		4,  //Heading
		2,  //Finishing
		3,  //Dribbling
		2,  //Crossing
		3,  //Technique
		5,  //First Touch
		2,  //Creativity
		2,  //Composure
		4,  //Positioning
		5,  //Teamwork
		3,  //Agility
		3,  //Acceleration
		5   //Stamina
	};
	public static final int[] M_SKILL_RELEVANCE = new int[]{
		4,  //Strength
		0,  //Reflexes
		0,  //Handling
		3,  //Tackling
		5,  //Passing
		3,  //Heading
		3,  //Finishing
		4,  //Dribbling
		4,  //Crossing
		4,  //Technique
		4,  //First Touch
		4,  //Creativity
		3,  //Composure
		3,  //Positioning
		5,  //Teamwork
		3,  //Agility
		4,  //Acceleration
		5   //Stamina
	};
	public static final int[] AM_SKILL_RELEVANCE = new int[]{
		2,  //Strength
		0,  //Reflexes
		0,  //Handling
		2,  //Tackling
		5,  //Passing
		3,  //Heading
		4,  //Finishing
		5,  //Dribbling
		4,  //Crossing
		5,  //Technique
		5,  //First Touch
		5,  //Creativity
		4,  //Composure
		2,  //Positioning
		3,  //Teamwork
		3,  //Agility
		4,  //Acceleration
		3   //Stamina
	};
	public static final int[] ST_SKILL_RELEVANCE = new int[]{
		4,  //Strength
		0,  //Reflexes
		0,  //Handling
		1,  //Tackling
		4,  //Passing
		5,  //Heading
		5,  //Finishing
		4,  //Dribbling
		2,  //Crossing
		4,  //Technique
		4,  //First Touch
		4,  //Creativity
		5,  //Composure
		1,  //Positioning
		3,  //Teamwork
		4,  //Agility
		4,  //Acceleration
		3   //Stamina
	};
	public static final int Strength      = 0;
	public static final int Reflexes      = 1;
	public static final int Handling      = 2;
	public static final int Tackling      = 3;
	public static final int Passing       = 4;
	public static final int Heading       = 5;
	public static final int Finishing     = 6;
	public static final int Dribbling     = 7;
	public static final int Crossing      = 8;
	public static final int Technique     = 9;
	public static final int FirstTouch   = 10;
	public static final int Creativity    = 11;
	public static final int Composure     = 12;
	public static final int Positioning   = 13;
	public static final int Teamwork      = 14;
	public static final int Agility       = 15;
	public static final int Acceleration  = 16;
	public static final int Stamina       = 17;
}

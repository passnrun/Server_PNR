package com.fm.bll;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fm.dal.Player;
import com.fm.dal.PlayerSkill;
import com.fm.dal.PlayerTemplate;
import com.fm.dal.Team;
import com.fm.dao.PlayerTemplateDAO;
import com.fm.engine.actions.Shoot;
import com.fm.obj.IPlayerQuality;
import com.fm.obj.ISkills;
import com.fm.obj.Position;

public class PlayerGenerator implements ISkills, IPlayerQuality{
//	private static Logger logger = Logger.getLogger(PlayerGenerator.class);
	
	/**
	 * Generates a new player with random skills for a given position
	 * @return
	 */
	public static Player generate(String position, int quality,  int team, String country){
		Player player = new Player();
		player.setBirthdate(generateBirthDate(quality));
		PlayerTemplate rndPlayer = findRandomTemplate(country);
		player.setName(rndPlayer.getName());
		player.setSurname(rndPlayer.getSurname());
		player.setPosition(position);
		player.setFitness(100);
		player.setMorale(70);
		player.setCurrentTeam(team);
		player.setPlayerSkill(generateSkill(position, quality));
		player.setQuality(quality);
		player.getPlayerSkill().setPlayer(player);
		return player;
	}
	private static PlayerTemplate findRandomTemplate(String country) {
		PlayerTemplateDAO dao = new PlayerTemplateDAO();
		List<PlayerTemplate> templateList = dao.findAll(country);
		Random r = new Random();
		PlayerTemplate p1 = templateList.get(r.nextInt(templateList.size()));
		PlayerTemplate p2 = templateList.get(r.nextInt(templateList.size()));
		PlayerTemplate player = new PlayerTemplate();
		player.setName(p1.getName());
		player.setSurname(p2.getSurname());
		return player;
	}
	private static Date generateBirthDate(int quality) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -17);
		Random r = new Random();
		switch (quality) {
			case HOTPROSPECT:
			case YOUNGSTER:
				cal.add(Calendar.DATE, -1 * r.nextInt(365 * 2));
				break;
			default:
				cal.add(Calendar.YEAR, -3);
				cal.add(Calendar.DATE, -1 * (r.nextInt(365 * 10)));
				break;
		}
		return cal.getTime();
	}
	private static PlayerSkill generateSkill(String rawPosition, int quality) {
		Position position = new Position(rawPosition);
		PlayerSkill skill = new PlayerSkill();
		skill.setAcceleration(generateSkillValue(quality, findRelevance(Acceleration, position.getPosition())));
		skill.setAgility(generateSkillValue(quality, findRelevance(Agility, position.getPosition())));
		skill.setReflexes(generateSkillValue(quality, findRelevance(Reflexes, position.getPosition())));
		skill.setHandling(generateSkillValue(quality, findRelevance(Handling, position.getPosition())));
		skill.setTackling(generateSkillValue(quality, findRelevance(Tackling, position.getPosition())));
		skill.setPassing(generateSkillValue(quality, findRelevance(Passing, position.getPosition())));
		skill.setHeading(generateSkillValue(quality, findRelevance(Heading, position.getPosition())));
		skill.setFinishing(generateSkillValue(quality, findRelevance(Finishing, position.getPosition())));
		skill.setDribbling(generateSkillValue(quality, findRelevance(Dribbling, position.getPosition())));
		skill.setCrossing(generateSkillValue(quality, findRelevance(Crossing, position.getPosition())));
		skill.setTechnique(generateSkillValue(quality, findRelevance(Technique, position.getPosition())));
		skill.setFirstTouch(generateSkillValue(quality, findRelevance(FirstTouch, position.getPosition())));
		skill.setCreativity(generateSkillValue(quality, findRelevance(Creativity, position.getPosition())));
		skill.setComposure(generateSkillValue(quality, findRelevance(Composure, position.getPosition())));
		skill.setPositioning(generateSkillValue(quality, findRelevance(Positioning, position.getPosition())));
		skill.setTeamwork(generateSkillValue(quality, findRelevance(Teamwork, position.getPosition())));
		skill.setStamina(generateSkillValue(quality, findRelevance(Stamina, position.getPosition())));
		skill.setStrength(generateSkillValue(quality, findRelevance(Strength, position.getPosition())));
		skill.setShooting(generateSkillValue(quality, findRelevance(Shooting, position.getPosition())));
		
		return skill;
	}
//	private static int generateSkillValue2(int quality, int relevance) {
//		   if (relevance == 0)
//		    return rnd(0, 20);
//		   switch (quality) {
//		    case INDISPENSIBLE :
//		    switch(relevance){
//		      case 5:
//		        return rnd(85,100);
//		      case 4:
//		        return rnd(70,95);
//		      case 3:
//		        return rnd(55,90);
//		      case 2:
//		        return rnd(40,85);
//		      case 1:
//		        return rnd(25,80);
//		    }
//		    case IMPORTANT  :
//		    switch(relevance){
//		      case 5:
//		        return rnd(75,95);
//		      case 4:
//		        return rnd(60,90);
//		      case 3:
//		        return rnd(45,85);
//		      case 2:
//		        return rnd(30,80);
//		      case 1:
//		        return rnd(15,75);
//		    }
//		    case FIRSTTEAM  :
//		    switch(relevance){
//		      case 5:
//		        return rnd(65,90);
//		      case 4:
//		        return rnd(50,85);
//		      case 3:
//		        return rnd(35,80);
//		      case 2:
//		        return rnd(20,75);
//		      case 1:
//		        return rnd(10,70);
//		    }
//		    case BACKUP  :
//		    switch(relevance){
//		      case 5:
//		        return rnd(55,85);
//		      case 4:
//		        return rnd(40,80);
//		      case 3:
//		        return rnd(25,75);
//		      case 2:
//		        return rnd(10,70);
//		      case 1:
//		        return rnd(5,65);
//		    }
//
//		    case HOTPROSPECT :
//		    switch(relevance){
//		      case 5:
//		        return rnd(50,80);
//		      case 4:
//		        return rnd(40,70);
//		      case 3:
//		        return rnd(30,65);
//		      case 2:
//		        return rnd(15,75);
//		      case 1:
//		        return rnd(10,60);
//		    }
//		    case YOUNGSTER  :
//		    switch(relevance){
//		      case 5:
//		        return rnd(35,70);
//		      case 4:
//		        return rnd(30,60);
//		      case 3:
//		        return rnd(10,50);
//		      case 2:
//		        return rnd(5,40);
//		      case 1:
//		        return rnd(0,40);
//		    }
//
//		     case NOTNEEDED :
//		    switch(relevance){
//		      case 5:
//		        return rnd(20,55);
//		      case 4:
//		        return rnd(15,50);
//		      case 3:
//		        return rnd(10,45);
//		      case 2:
//		        return rnd(5,40);
//		      case 1:
//		        return rnd(0,35);
//		    }
//		 }
//		 return 0;
//		 }
	private static int generateSkillValue(int quality, int relevance) {
		if (relevance == 0)
			return rnd(0, 20);
		switch (quality) {
			case INDISPENSIBLE 	:
				if (relevance <= 3)
					return rnd(50, 90);
				else
					return rnd(85, 100);
			case IMPORTANT 		:
				if (relevance <= 3)
					return rnd(50, 80);
				else
					return rnd(70, 90);
			case FIRSTTEAM 		:
				if (relevance <= 3)
					return rnd(30, 70);
				else
					return rnd(50, 70);
			case BACKUP 			:	
				if (relevance <= 3)
					return rnd(30, 40);
				else
					return rnd(40, 60);
			case NOTNEEDED 	:
				if (relevance <= 3)
					return rnd(10, 30);
				else
					return rnd(20, 40);
			case YOUNGSTER 		:
				if (relevance <= 3)
					return rnd(10, 20);
				else
					return rnd(20, 30);
			case HOTPROSPECT 	:
				if (relevance <= 3)
					return rnd(10, 40);
				else
					return rnd(40, 60);
		}
		return 0;
	}
	private static int findRelevance(int skill, String position) {
		if (Position.GOALKEEPER.equals(position))
			return ISkills.GK_SKILL_RELEVANCE[skill];
		if (Position.DEFENDER.equals(position))
			return ISkills.DEF_SKILL_RELEVANCE[skill];
		if (Position.DEFENSIVE_MIDFIELDER.equals(position))
			return ISkills.DM_SKILL_RELEVANCE[skill];
		if (Position.MIDFIELDER.equals(position))
			return ISkills.M_SKILL_RELEVANCE[skill];
		if (Position.ATTACKING_MIDFIELDER.equals(position))
			return ISkills.AM_SKILL_RELEVANCE[skill];
		if (Position.STRIKER.equals(position))
			return ISkills.ST_SKILL_RELEVANCE[skill];
		return 0;
	}

	private static Integer rnd(int min, int max) {
		Random r = new Random();
		return min + r.nextInt(max-min);
	}
	
	public static List<Player> generateAllFromRaw(String squadTemplate, String playerTemplate, Team team, String country) {
		String[] positions = squadTemplate.split(",");
		String[] qualities = playerTemplate.split(",");
		List<Player> playerList = new ArrayList<Player>();
		List<String> positionList = new ArrayList<String>();
		List<Integer> qualityList = new ArrayList<Integer>();
		for (int i = 0; i < positions.length; i++) 
			positionList.add(positions[i]);
		for (int i = 0; i < qualities.length; i++) 
			qualityList.add(Integer.parseInt(qualities[i]));
		Random r = new Random();
		while (positionList.size()>0 && qualityList.size()>0){
			int index = r.nextInt(positionList.size());
			String position = positionList.remove(index);
			index = r.nextInt(qualityList.size());
			Integer quality = qualityList.remove(index);
			playerList.add(generate(position,quality, team.getId(),country));
		}
		return playerList;
	}
}

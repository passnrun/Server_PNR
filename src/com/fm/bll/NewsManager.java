package com.fm.bll;

import java.util.Date;

import com.fm.dal.News;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.util.Parameter;

public class NewsManager {
	public static final int TYPE_WELLCOME 	= 1;
	public static final int TYPE_FIXTURE 	= 2;
	public static final int TYPE_GAMERESULT = 3;
	
	public static final String[] KEYS = new String[]{"", "welcome","leaguedraw","gameresult"};
	
	public static void create(Team t){};
	
	public static void createNews(int type, Team team, String[] params){
		if (team.getCurrentManager() == null || team.getCurrentManager() == 0)
			return;
		DAO dao = new DAO();
		News news = new News();
		news.setManagerId(team.getCurrentManager());
		news.setReceiveDate(new Date());
		news.setSubject(Parameter.getString("news."+KEYS[type]+".subject"));
		news.setBody(Parameter.getString("news."+KEYS[type]+".body", params));
		news.setMustAnswer(0);
		dao.save(news);
	}

}

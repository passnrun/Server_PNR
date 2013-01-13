package com.fm.mw.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.Game;
import com.fm.dal.ModifyCount;
import com.fm.dal.News;
import com.fm.dao.GameDAO;
import com.fm.dao.ModifyCountDAO;
import com.fm.dao.NewsDAO;
import com.fm.mw.JSON;
import com.fm.mw.obj.JSGameResult;
import com.fm.mw.obj.JSList;
import com.fm.mw.obj.JSNews;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class NewsService {
	private static Logger logger = Logger.getLogger(NewsService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("news sync request received :" + map);
		NewsDAO dao = new NewsDAO();
		ModifyCountDAO mcDao = new ModifyCountDAO();
		Integer managerId = Integer.parseInt((String)map.get("managerId"));
		Integer minId = Integer.parseInt((String)map.get("minId"));
		ModifyCount mc = mcDao.findByManager(managerId);
		List<News> newsList = dao.getNews(managerId, minId, mc.getMaxNews());
		if (newsList == null)
			return new JSONResponse(JSONResponse.ERROR_NO_NEWS_FOUND, new JSONString("No News Found for Manager["+mc.getManagerId()+"]"));
		JSList jstList = new JSList();
		jstList.setList(new ArrayList<JSON>());
		for (Iterator<News> iterator = newsList.iterator(); iterator.hasNext();) {
			News news = iterator.next();
			jstList.getList().add(new JSNews(news));
		}
		return new JSONResponse(0, jstList);
	}
}

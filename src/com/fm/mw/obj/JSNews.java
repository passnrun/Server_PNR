package com.fm.mw.obj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fm.dal.News;
import com.fm.mw.JSON;
import com.fm.util.DateUtil;

public class JSNews implements JSON {
	private News news;
	
	public JSNews(News n) {
		news = n;
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		DateFormat format = new SimpleDateFormat("dd.MM.yy");
		sb.append("{\"id\" :").append(news.getId()).append(" ,");
		sb.append("\"managerId\" :").append(news.getManagerId()).append(" ,");
		sb.append("\"date\" :").append("\""+format.format(news.getReceiveDate())+"\"").append(" ,");
		sb.append("\"subject\" :").append("\""+news.getSubject()+"\"").append(" }");
		return sb.toString();
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}

}

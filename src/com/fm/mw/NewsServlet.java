package com.fm.mw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fm.dal.News;
import com.fm.dao.NewsDAO;

public class NewsServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(NewsServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int newsId = Integer.parseInt(req.getParameter("newsId"));
		NewsDAO dao = new NewsDAO();
		News news = (News)dao.findById(News.class, newsId);
		writeResponse(resp, "<p style=\" font-family: Arial; font-size: 12px; color: white; font-weight: bold; \">"+news.getBody()+"</p>");
	}
	private void writeResponse(HttpServletResponse resp, String str) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.close();
	}
}

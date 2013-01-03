package com.fm.mw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fm.dal.GameDetail;
import com.fm.dao.GameDetailDAO;

public class GameDetailService extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(GameDetailService.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int gameId = Integer.parseInt(req.getParameter("gameId"));
		GameDetailDAO dao = new GameDetailDAO();
		List<GameDetail> details = dao.getGameDetails(gameId, 5);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<body>");
		for (Iterator iterator = details.iterator(); iterator.hasNext();) {
			GameDetail gameDetail = (GameDetail) iterator.next();
			if (gameDetail.getLogMessage() != null)
				buffer.append("<p>"+gameDetail.getMinute()+". "+gameDetail.getLogMessage()+"</p>");
		}
		buffer.append("</body>");
		writeResponse(resp, buffer.toString());
	}
	private void writeResponse(HttpServletResponse resp, String str) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.close();
	}
}

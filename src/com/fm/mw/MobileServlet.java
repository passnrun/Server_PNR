package com.fm.mw;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import test.LoadPlayerTemplate;

import com.fm.bll.LeagueManager;
import com.fm.jobs.JobScheduler;
import com.fm.mw.obj.JSONRequest;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;
import com.fm.mw.service.FixtureService;
import com.fm.mw.service.GameDetailService;
import com.fm.mw.service.GameResultService;
import com.fm.mw.service.NewsService;
import com.fm.mw.service.PlayerService;
import com.fm.mw.service.RegisterService;
import com.fm.mw.service.ResignService;
import com.fm.mw.service.SquadService;
import com.fm.mw.service.SyncService;
import com.fm.mw.service.TeamService;
import com.fm.util.JSONParser;


public class MobileServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(MobileServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inputMessage = extractMessage(req);
		JSONRequest jsonReq = parseJSONRequest(inputMessage);
		JSONResponse jsonReps = processRequest(jsonReq);
		writeResponse(resp,jsonReps.toJSON());
		System.out.println(jsonReps.toJSON());
	}

	private void writeResponse(HttpServletResponse resp, String json) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println(json);
		out.close();
	}
public static void main(String[] args) {
//	String content = "{\"service\" : \"createLeague\", \"name\" : \"Super Leage\", \"country\":\"turkey\"}";
//	String content = "{\"service\" : \"teamList\", \"leagueId\" : 1}";
//	String content = "{\"service\" : \"fixture\", \"leagueId\" : 1, \"seasonId\" : 1}";
	 String content = "{\"service\" : \"player\", \"playerId\" : 192, \"minGames\" =0\"}";
	JSONRequest req = parseJSONRequest(content);
	System.out.println(processRequest(req).toJSON());
}
	private static JSONResponse processRequest(JSONRequest jsonReq) {
		if (jsonReq.getService() == null)
			return new JSONResponse(JSONResponse.ERROR_JSONFORMAT, new JSONString("Invalid Message From Mobile Device"));
		
		if (jsonReq.getService().equals("register"))
			return RegisterService.process((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("sync"))
			return SyncService.process((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("teamList"))
			return TeamService.process((Map<String, Object>)jsonReq.getData());

		if (jsonReq.getService().equals("fixture"))
			return FixtureService.process((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("gameResult"))
			return GameResultService.process((Map<String, Object>)jsonReq.getData());

		if (jsonReq.getService().equals("gameDetails"))
			return GameDetailService.process((Map<String, Object>)jsonReq.getData());

		if (jsonReq.getService().equals("gameTeamStats"))
			return GameDetailService.teamStats((Map<String, Object>)jsonReq.getData());

		if (jsonReq.getService().equals("squad"))
			return SquadService.process((Map<String, Object>)jsonReq.getData());

		if (jsonReq.getService().equals("player"))
			return PlayerService.process((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("saveSquad"))
			return SquadService.save((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("news"))
			return NewsService.process((Map<String, Object>)jsonReq.getData());
		
		if (jsonReq.getService().equals("schedule")){
			JobScheduler scheduler = new JobScheduler();
			scheduler.scheduleJobs();
			return new JSONResponse(0, new JSONString("done"));
		}
		if (jsonReq.getService().equals("resign")){
			Map<String, Object> map = (Map<String, Object>)jsonReq.getData();
			return ResignService.resignManager(Integer.parseInt((String)map.get("id")));
		}		
		if (jsonReq.getService().equals("createLeague")){
			Map<String, Object> map = (Map<String, Object>)jsonReq.getData();
			int id = LeagueManager.generateNewLeague(null, (String)map.get("name"), (String) map.get("country"));
			return new JSONResponse(0, new JSONString("League:"+id));
		}
		if (jsonReq.getService().equals("initiate")){
			Map<String, Object> map = (Map<String, Object>)jsonReq.getData();
			return LeagueManager.initiateLeague(Integer.parseInt((String)map.get("leagueId")));
		}
		if (jsonReq.getService().equals("createPlayers")){
			try {
				LoadPlayerTemplate.loadPlayerNames();
				return new JSONResponse(0, new JSONString("Done"));
			} catch (Exception e) {
				return new JSONResponse(0, new JSONString("Exception:"+e.getMessage() ));
			}
		}
			
		return new JSONResponse(JSONResponse.ERROR_INVALID_SERVICE, new JSONString("Invalid Service Name:"+jsonReq.getService()));
	}

	private static JSONRequest parseJSONRequest(String msg) {
		logger.debug("Parsing Json Message:"+ msg);
		Map<String, Object> map = JSONParser.parse(msg);
		String service = (String)map.remove("service");
		return new JSONRequest(service, map);
	}

	private String extractMessage(HttpServletRequest req) {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		    		buffer.append(line);
		} catch (Exception e) { 
			logger.error("Error reading input message", e);
		}
		return buffer.toString();
	}
	
	
}

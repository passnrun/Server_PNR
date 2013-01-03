package com.fm.mw.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.fm.dal.ModifyCount;
import com.fm.dao.ModifyCountDAO;
import com.fm.mw.obj.JSModifyCount;
import com.fm.mw.obj.JSONResponse;
import com.fm.mw.obj.JSONString;

public class SyncService {
	private static Logger logger = Logger.getLogger(SyncService.class);
	
	public static JSONResponse process(Map<String, Object> map){
		logger.info("sync request received :" + map);
		ModifyCountDAO dao = new ModifyCountDAO();
		ModifyCount mc = dao.findByManager(Integer.parseInt((String)map.get("managerId")));
		if (mc == null)
			return new JSONResponse(JSONResponse.ERROR_NO_MANAGER_FOUND, new JSONString("No record found on server"));
		JSModifyCount jsmc = new JSModifyCount(mc);
		return new JSONResponse(0, jsmc);
	}
}

package com.fm.mobile;

import javapns.Push;
import javapns.notification.PushedNotifications;

import org.apache.log4j.Logger;

import com.fm.dal.Manager;
import com.fm.dal.Team;
import com.fm.dao.DAO;
import com.fm.util.Parameter;

public class IPhoneNotificationHandler {
	
	private static Logger logger = Logger.getLogger(IPhoneNotificationHandler.class);
	
	public static void sendPushNotification(String message, Team team){
		DAO dao = new DAO();
		if (team == null || team.getCurrentManager() == null)
			return;
		Manager manager = (Manager)dao.findById(Manager.class, team.getCurrentManager());
		if (manager == null)
			return;
		if (Manager.DEVICE_IPHONE.equals(manager.getDevice()) && manager.getDeviceId() != null)
			sendPushNotification(message, manager.getDeviceId());
	
	}
	public static void sendPushNotification(String message, String token){
		logger.debug("sending notification["+message+"] to token :"+token);
		try {
			PushedNotifications notification = Push.alert(message, Parameter.getString("iphone.certificate.path"), Parameter.getString("iphone.certificate.password"), true, token);
			logger.info("sendPushNotification :"+notification);
		} catch (Exception e) {
			logger.warn("sendPushNotification failed", e);
		} 
	}
}

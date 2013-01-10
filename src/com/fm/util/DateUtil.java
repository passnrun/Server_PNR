package com.fm.util;

import java.util.Calendar;
import java.util.Date;

import com.fm.dal.Player;
import com.fm.dao.DAO;

public class DateUtil {
	
	public static Calendar getFirstGamePlayDate(int daysAfter, int hour){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, daysAfter);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	public static int getAge(Date bd) {
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(bd);
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	}
	
	public static void main(String[] args) {
		DAO dao = new DAO();
		Player p = (Player) dao.findById(Player.class, 14);
		System.out.println(p.getBirthdate());
		System.out.println(getAge(p.getBirthdate()));
	}
}

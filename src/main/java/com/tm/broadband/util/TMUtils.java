package com.tm.broadband.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TMUtils {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat date1Format = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public TMUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCustomerOrderSerial(String login_name) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
	
		String serial = "TM" 
				+ login_name.toUpperCase() 
				+ String.valueOf(cal.get(Calendar.YEAR))
				+ String.valueOf(cal.get(Calendar.MONTH) + 1)
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
				+ String.valueOf(cal.get(Calendar.HOUR_OF_DAY))
				+ String.valueOf(cal.get(Calendar.MINUTE));
		
		return serial;
	}
	
	public static String dateFormatYYYYMMDDHHMMSS(Date date) {
		if (date != null) 
			return date2Format.format(date);
		return "";
	}
	
	public static String dateFormatYYYYMMDD(Date date) {
		if (date != null) 
			return dateFormat.format(date);
		return "";
	}

}

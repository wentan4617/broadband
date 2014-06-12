package com.tm.broadband.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Calculation4PlanTermInvoice {
	
	// Get service begin month's daily price, remaining days, total price
	public static Map<String, Object> servedMonthDetails(Date serviceGivingDate, Double monthlyCharge){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(serviceGivingDate);
		cal.add(Calendar.MONTH, 0);
		
		// BEGIN FIRST STEP, get maximum days in month & daily price
		
		// Specific month's maximum days
		Integer monthlyMaximumDays = cal.getActualMaximum(Calendar.DATE);
		// Encapsulating monthly maximum days and monthly charge into BigDecimal separately
		BigDecimal bigMaximumDays = new BigDecimal(monthlyMaximumDays);
		BigDecimal bigMonthlyCharge = new BigDecimal(monthlyCharge);
		
		// Term plan's daily price, monthlyCharge divide monthly maximum days
		Double dailyPrice = bigMonthlyCharge.divide(bigMaximumDays, 5, BigDecimal.ROUND_DOWN).doubleValue();
		resultMap.put("dailyPrice", Double.parseDouble(TMUtils.fillDecimalPeriod(String.valueOf(dailyPrice))));
		
		// BEGIN FIRST STEP, get maximum days in month & daily price
		
		
		// BEGIN SECOND STEP, get remaining days & total price
		
		// Specific month's maximum days minus beginDate's date number equals to remaining days
		Integer remainingDays = monthlyMaximumDays - Integer.parseInt(new SimpleDateFormat("dd").format(serviceGivingDate))+1;
		resultMap.put("remainingDays", remainingDays);
		
		// Encapsulating daily price and remaining days into BigDecimal separately
		BigDecimal bigDailyPrice = new BigDecimal(dailyPrice);
		BigDecimal bigRemainingDays = new BigDecimal(remainingDays);
		
		// Term plan's daily price, monthlyCharge divide monthly maximum days
		Double totalPrice = bigDailyPrice.multiply(bigRemainingDays).doubleValue();
		resultMap.put("totalPrice", Double.parseDouble(TMUtils.fillDecimalPeriod(String.valueOf(totalPrice))));
		
		
//		System.out.println(cal.getActualMaximum(Calendar.DATE));
//		System.out.println(remainingDays);
//		System.out.println(dailyPrice);
//		System.out.println(totalPrice);
		return resultMap;
	}
	
	public static Date getInvoiceDueDate(){
		// Get current month's 20th date
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String yyyy_MM = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).substring(0, 7);
		try {cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(yyyy_MM+"-20"));}catch(ParseException e){e.printStackTrace();}
		
		return cal.getTime();
	}
}

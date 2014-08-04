package com.tm.broadband.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.CustomerCallingRecordCallplusMapper;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.StatisticBilling;
import com.tm.broadband.model.StatisticCustomer;
import com.tm.broadband.pdf.InvoicePDFCreator;

public class TMUtils {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat date1Format = new SimpleDateFormat("dd/MM/yyyy");
	private final static SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final static SimpleDateFormat date3Format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private final static DecimalFormat numberFormat = new DecimalFormat("0.00");
	public final static DecimalFormat timeFormat = new DecimalFormat("00.00");
	private final static String[] pwds = { "1", "5", "9", "8", "7", "6", "4", "3",
		"2", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a",
		"s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v",
		"b", "n", "m", "0" };
	
	public TMUtils() {
	}
	
	public static String generateRandomString(int range) {
		StringBuffer str = new StringBuffer();
		Random random = new Random();
		int i = 0;
		while (i < range) {
			str.append(pwds[random.nextInt(36)]);
			i++;
		}
		return str.toString();
	}
	
	public static String strCapital(String str){
		return str.substring(0, 1).toUpperCase().concat(str.substring(1));
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
	
	public static String dateFormat1YYYYMMDD(Date date) {
		if (date != null) 
			return date1Format.format(date);
		return "";
	}
	
	public static Date parseDateYYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return dateFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Date parseDate2YYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date2Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Date parseDate1YYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date1Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Date parseDateDDMMYYYYHHMMSS(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date3Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static void setJSONErrorMap(JSONBean<?> json, BindingResult result) {
		List<FieldError> fields = result.getFieldErrors();
		for (FieldError field: fields) {
			System.out.println(field.getField() + ": " + field.getDefaultMessage());
			json.getErrorMap().put(field.getField(), field.getDefaultMessage());
		}
	}
	
	public static String createPath(String path){
		String temp = System.getenv("SystemRoot");
		String realPath = temp.substring(0, temp.indexOf("\\")+1) + path;
		
        File file = new File(realPath);
        if(!file.getParentFile().exists()){
        	file.getParentFile().mkdirs();
        }
        
        return realPath;
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static String fillDecimalPeriod(String sum){
		return numberFormat.format(Double.parseDouble(sum));
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static String fillDecimalPeriod(Double sum){
		return fillDecimalPeriod(String.valueOf(sum));
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static BigDecimal fillDecimalPeriod(BigDecimal bigDecimal){
		return new BigDecimal(numberFormat.format(bigDecimal));
	}
	
	// e.g.: fix time format, for example 2.77 change to 3.17 finally change to 3:17 and return
	public static String fillDecimalTime(String sum){
		sum = numberFormat.format(Double.parseDouble(sum));
		Integer sumInteger = Integer.parseInt(sum.substring(0, sum.indexOf(".")));
		Integer sumReminder = Integer.parseInt(sum.substring(sum.indexOf(".")+1));
		if(sumReminder > 59){
			sumReminder = sumReminder - 60;
			sumInteger ++;
		}
		return sumInteger+"."+sumReminder;
	}
	
	public static Boolean isReminder(String sum){
		Integer reminderLength = sum.substring(sum.indexOf(".")+1).length();
		String strTemp = sum.substring(sum.indexOf(".")+1, reminderLength>2 ?sum.indexOf(".")+3 : sum.indexOf(".")+2);
		return Integer.parseInt(strTemp)>0;
	}

	/**
	 * Methods from Calendar BEGIN
	 * @param registerCustomers
	 */
	public static void thisWeekDateForRegisterStatistic(List<StatisticCustomer> registerCustomers) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY); // Monday as first day
		
		StatisticCustomer statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Mon. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);
		
		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Tues. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Wed. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Thur. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Fri. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Sat. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = null;
		statistic = new StatisticCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Sun. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);
	}
	
	public static void thisMonthDateForRegisterStatistic(int year, int month, List<StatisticCustomer> registerCustomers) {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(Calendar.DAY_OF_MONTH, 1); // set date
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		StatisticCustomer statistic = null;
		for (int i = 0; i < maxDay; i++) {
			statistic = new StatisticCustomer();
			c.set(Calendar.DAY_OF_MONTH, i+1);
			statistic.setRegisterDate(c.getTime());
			statistic.setRegisterCount(0);
			registerCustomers.add(statistic);
		}
	}
	
	// Billing
	public static void thisWeekBillingStatistic(List<StatisticBilling> statisticBilling) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY); // Monday as first day
		
		StatisticBilling statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Mon. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);
		
		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Tues. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);

		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Wed. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);

		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Thur. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);

		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Fri. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);

		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Sat. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);

		statistic = null;
		statistic = new StatisticBilling();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		statistic.setBillingDate(cal.getTime());
		statistic.setBillingWeekDate_str("Sun. "+TMUtils.dateFormatYYYYMMDD(statistic.getBillingDate()));
		statistic.setBillingAmount(0d);
		statisticBilling.add(statistic);
	}
	
	public static void thisMonthDateForBillingStatistic(int year, int month, List<StatisticBilling> statisticBilling) {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(Calendar.DAY_OF_MONTH, 1); // set date
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		StatisticBilling statistic = null;
		for (int i = 0; i < maxDay; i++) {
			statistic = new StatisticBilling();
			c.set(Calendar.DAY_OF_MONTH, i+1);
			statistic.setBillingDate(c.getTime());
			statistic.setBillingAmount(0d);
			statisticBilling.add(statistic);
		}
	}
	
	/**
	 * Methods from Calendar END
	 */
	
	// Remove all non-numeric characters, if 0 is first char then remove it
	public static String formatPhoneNumber(String str){
		if(str!=null && !"".equals(str)){
			String pattern = "\\D+";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
			if(str.indexOf("0") == 0){
				str = str.substring(1, str.length());
			}
		}
		return str;
	}
	
	public static void printResultErrors(BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors)
				System.out.println(error.getObjectName() + ", " + error.getDefaultMessage());
		}
	}
	
	// BEGIN CustomerCallRecord OPERATION
	public static Double ccrRentalOperation(String pstn_number
			, List<CustomerInvoiceDetail> cids, Double totalAmountPayable
			, CustomerCallRecordMapper customerCallRecordMapper){
		
		Double totalAmountIncl = 0d;
		
		CustomerCallRecord ccrQuery = new CustomerCallRecord();
		ccrQuery.getParams().put("where", "query_unused_rental_records");
		ccrQuery.getParams().put("clear_service_id", 
				TMUtils.formatPhoneNumber(pstn_number)
		);
		ccrQuery.getParams().put("used", false);
		List<CustomerCallRecord> ccrs = customerCallRecordMapper.selectCustomerCallRecord(ccrQuery);
		
		if(ccrs!=null && ccrs.size()>0){
			for (CustomerCallRecord ccr : ccrs) {
				
				CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
				cid.setInvoice_detail_name(ccr.getBilling_description());
				cid.setInvoice_detail_desc(TMUtils.dateFormatYYYYMMDD(ccr.getDate_from())+" - "+TMUtils.dateFormatYYYYMMDD(ccr.getDate_to()));
				cid.setInvoice_detail_price(ccr.getAmount_incl());
				cid.setInvoice_detail_unit(1);
				
				cids.add(cid);
				
				totalAmountIncl = TMUtils.bigAdd(totalAmountIncl, ccr.getAmount_incl());
			}
			
			CustomerCallRecord ccr = new CustomerCallRecord();
			ccr.setUsed(true);
			ccr.getParams().put("clear_service_id", pstn_number);
			ccr.getParams().put("used", false);
			customerCallRecordMapper.updateCustomerCallRecord(ccr);
		}
		
		return TMUtils.bigAdd(totalAmountPayable, totalAmountIncl);
	}
	
	// BEGIN CustomerCallRecord OPERATION
	public static Double ccrOperation(List<CustomerOrderDetail> pcms
			, String pstn_number, List<CustomerInvoiceDetail> cids
			, InvoicePDFCreator invoicePDF, Double totalPayableAmouont
			, CustomerCallRecordMapper customerCallRecordMapper
			, CallInternationalRateMapper callInternationalRateMapper
			, CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper
			, String customerType){
		
		Calendar lastMonthFirst = Calendar.getInstance(Locale.CHINA);
		lastMonthFirst.set(Calendar.DAY_OF_MONTH, 1);
		lastMonthFirst.add(Calendar.MONTH, -1);
		lastMonthFirst.set(Calendar.HOUR_OF_DAY, 0);
		lastMonthFirst.set(Calendar.MINUTE, 0);
		lastMonthFirst.set(Calendar.SECOND, 0);
		
		// Total calling record
		List<CustomerCallRecord> ccrs = new ArrayList<CustomerCallRecord>();
		// Total chargeable amount
		Double totalAmountIncl = 0d;
		// Total credit back
		Double totalCreditBack = 0d;
		
		String callType = "";
		
		// BEGIN Chorus Style Calling Record
		CustomerCallRecord ccrTemp = new CustomerCallRecord();
		ccrTemp.getParams().put("where", "query_last_call_records");
		ccrTemp.getParams().put("orderby", "ORDER BY charge_date_time ASC");
		ccrTemp.getParams().put("last_month", lastMonthFirst.getTime());
		ccrTemp.getParams().put("record_type", "T1,T3");
		// PRODUCTION MODE CHANGE TO pstn_number
		ccrTemp.getParams().put("clear_service_id", //96272424
				TMUtils.formatPhoneNumber(pstn_number)
		);
		List<CustomerCallRecord> ccrsTemp = customerCallRecordMapper.selectCustomerCallRecord(ccrTemp);

		// ITERATIVELY ADD ALL CALL CHARGES
		for (CustomerCallRecord ccr : ccrsTemp) {

			// Residential local call free
			if("personal".equals(customerType.toLowerCase()) && "L".equals(ccr.getJuristiction())){
				continue;
			}
			
			// BEGIN Rate Operation
//			boolean is0900 = false; boolean isFax = false;
//			boolean isMobile = ccr.getBilling_description().toUpperCase().contains("MOBILE")
//					|| ccr.getBilling_description().toUpperCase().contains("OTH NETWRK");
//			boolean isNational = false; boolean isBusinessLocal = false;
			
			CallInternationalRate cir = new CallInternationalRate();
			switch (ccr.getJuristiction()) {
			case "O":	/* OTH NETWORK & Mobile */
				callType = "mobile";
				cir.getParams().put("area_prefix", "2");
				break;
			case "L":	/* Local (Auckland) */
				callType = "local";
				cir.getParams().put("area_prefix", "3");
				break;
			case "N":	/* National */
				callType = "national";
				cir.getParams().put("area_prefix", "3");
				break;
			case "I":	/* International */
				callType = "international";
				cir.getParams().put("area_prefix", ccr.getPhone_called().substring(0, ccr.getPhone_called().indexOf("-")));
				break;
			}
			List<CallInternationalRate> cirs = callInternationalRateMapper.selectCallInternationalRates(cir);
			boolean isOnRate = cirs!=null && cirs.size()>0 ? true : false;

			Double costPerMinute = 1d;
//			if(is0900){} if(isFax){}
//			if(isMobile){ costPerMinute = 0.19d ; }
//			if(isNational){} if(isBusinessLocal){}
			if(isOnRate){ costPerMinute = cirs.get(0).getRate_cost(); }
			
			// DURATION/SECONDS
			Double duration = Double.parseDouble(fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccr.getDuration(), 60d))));
			
			ccr.setCallType(strCapital(callType));
			
			if(//(is0900 || isFax || isNational || isBusinessLocal) || 
					isOnRate){

				// If have reminder, then cut reminder and plus 1 minute, for example: 5.19 change to 6
				if(isReminder(String.valueOf(duration))){
					String durationStr = String.valueOf(duration);
					duration =  Double.parseDouble(durationStr.substring(0, durationStr.indexOf(".")))+1d;
				}

				// Process Present Calling Minutes
				totalCreditBack = processPresentCallingMinutes(pcms
						,cirs
						,duration
						,totalCreditBack
						,costPerMinute
						,callType);
				
				ccr.setAmount_incl(TMUtils.bigMultiply(duration, costPerMinute));
				totalAmountIncl = TMUtils.bigAdd(totalAmountIncl, TMUtils.bigMultiply(duration, costPerMinute));
				
			} else {
				totalAmountIncl = TMUtils.bigAdd(totalAmountIncl, ccr.getAmount_incl());
			}
			
			// END Rate Operation
			
			// FORMAT DURATION(second) TO TIME STYLE
			ccr.setFormated_duration(timeFormat.format(Double.parseDouble(String.valueOf(duration))).replace(".", ":"));
			ccrs.add(ccr);
		}
		// END Chorus Style Calling Record
		

		// BEGIN Callplus Style Calling Record
		CustomerCallingRecordCallplus ccrcQuery = new CustomerCallingRecordCallplus();
		ccrcQuery.getParams().put("where", "query_last_call_records");
		ccrcQuery.getParams().put("orderby", "ORDER BY date ASC");
		ccrcQuery.getParams().put("last_month", lastMonthFirst);
		ccrcQuery.getParams().put("original_number", pstn_number);
		List<CustomerCallingRecordCallplus> ccrcs = customerCallingRecordCallplusMapper.selectCustomerCallingRecordCallplus(ccrcQuery);

		
		for (CustomerCallingRecordCallplus ccrc : ccrcs) {

			// Residential local call free
			if("personal".equals(customerType.toLowerCase()) && "L".equals(ccrc.getType())){
				continue;
			}
			
			CallInternationalRate cir = new CallInternationalRate();
			switch (ccrc.getType()) {
			case "M":	/* Mobile */
				callType = "mobile";
				cir.getParams().put("area_prefix", "2");
				break;
			case "MG":	/* Mobile Gsm */
				callType = "mobile";
				cir.getParams().put("area_prefix", "2");
				break;
			case "L":	/* Local (Auckland) */
				callType = "local";
				cir.getParams().put("area_prefix", "3");
				break;
			case "S":	/* Domestic */
				callType = "national";
				cir.getParams().put("area_prefix", "3");
				break;
			case "I":	/* International */
				callType = "international";
				cir.getParams().put("area_name", ccrc.getDescription());
				break;
			}
			
			List<CallInternationalRate> cirs = callInternationalRateMapper.selectCallInternationalRates(cir);
			boolean isOnRate = cirs!=null && cirs.size()>0 ? true : false;

			Double costPerMinute = 1d;
//			if(is0900){} if(isFax){}
//			if(isMobile){ costPerMinute = 0.19d ; }
//			if(isNational){} if(isBusinessLocal){}
			if(isOnRate){ costPerMinute = cirs.get(0).getRate_cost(); }
			
			// duration = length / 60
			Double duration = Double.parseDouble(fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccrc.getLength(), 60d))));
			
			CustomerCallRecord ccr = new CustomerCallRecord();
			ccr.setClear_service_id(ccrc.getOriginal_number());
			ccr.setCharge_date_time(ccrc.getDate());
			ccr.setPhone_called(ccrc.getDestination_number());
			ccr.setBilling_description(ccrc.getDescription());
			ccr.setCallType(strCapital(callType));
			ccr.setAmount_incl(ccrc.getCharged_fee());

			if(//(is0900 || isFax || isNational || isBusinessLocal) || 
					isOnRate){

				// If have reminder, then cut reminder and plus 1 minute, for example: 5.19 change to 6
				if(isReminder(String.valueOf(duration))){
					String durationStr = String.valueOf(duration);
					duration =  Double.parseDouble(durationStr.substring(0, durationStr.indexOf(".")))+1d;
				}
				
				// Process Present Calling Minutes
				totalCreditBack = processPresentCallingMinutes(pcms
						,cirs
						,duration
						,totalCreditBack
						,costPerMinute
						,callType);
				
				ccr.setAmount_incl(TMUtils.bigMultiply(duration, costPerMinute));
				totalAmountIncl = TMUtils.bigAdd(totalAmountIncl, TMUtils.bigMultiply(duration, costPerMinute));
				
			} else {
				totalAmountIncl = TMUtils.bigAdd(totalAmountIncl, ccr.getAmount_incl());
			}
			
			// END Rate Operation
			
			// FORMAT DURATION(second) TO TIME STYLE
			ccr.setFormated_duration(timeFormat.format(Double.parseDouble(String.valueOf(duration))).replace(".", ":"));
			ccrs.add(ccr);
			
		}
		
		// END Callplus Style Calling Record
		
		
		CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
		cid.setInvoice_detail_name("Calling Charge : ( " + pstn_number + " )");
		cid.setInvoice_detail_price(bigSub(totalAmountIncl, totalCreditBack));
		cid.setInvoice_detail_desc("Total Charge: "+ totalAmountIncl + "\nCredit Back: "+ fillDecimalPeriod(totalCreditBack));
		cid.setInvoice_detail_unit(1);
		
		cids.add(cid);
		
		invoicePDF.setCcrs(ccrs);
		
		totalAmountIncl =  bigSub(totalAmountIncl, totalCreditBack);
		
		lastMonthFirst = null;
		ccrTemp = null;
		ccrsTemp = null;
		// ADD TOTAL CALL FEE INTO INVOICE
		return TMUtils.bigAdd(totalPayableAmouont, totalAmountIncl);
	}
	// END CustomerCallRecord OPERATION
	
	public static Double processPresentCallingMinutes(List<CustomerOrderDetail> pcms
			,List<CallInternationalRate> cirs
			,Double duration
			,Double totalCreditBack
			,Double costPerMinute
			,String callType){
		
		// BEGIN PresentCallingMinutes
		if(pcms!=null && pcms.size()>0){
			int index = 0;
			for (CustomerOrderDetail pcm : pcms) {
				String cirAreaName = cirs.get(0).getArea_name().toUpperCase();
				String pcmDetailDesc = pcm.getDetail_desc().toUpperCase();
				
				// Specific callType
				if(pcmDetailDesc.equals(callType.toUpperCase())){
					
					totalCreditBack = getCallingTotalCreditBack(pcms, pcm, cirs, duration, totalCreditBack, costPerMinute, index);
					index++;
					
				// Specific countries
				} else {
					if(("BANGLADESH".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("MALAYSIA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("CAMBODIA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("SINGAPORE".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("CANADA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("KOREA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("CHINA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("USA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("HONG KONG".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("VIETNAM".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))
					|| ("INDIA".equals(cirAreaName) && pcmDetailDesc.contains(cirAreaName.split(" ")[0]))){
						totalCreditBack = getCallingTotalCreditBack(pcms, pcm, cirs, duration, totalCreditBack, costPerMinute, index);
						index++;
					} else if(pcmDetailDesc.contains(cirs.get(0).getArea_prefix()) 
							&& pcmDetailDesc.contains(cirAreaName.split(" ")[0])
							&& (!cirAreaName.contains("MOBILE") && !cirAreaName.contains("MOB"))){
						totalCreditBack = getCallingTotalCreditBack(pcms, pcm, cirs, duration, totalCreditBack, costPerMinute, index);
						index++;
					}
				}
				
			}
		}
		// END PresentCallingMinutes
		return totalCreditBack;
	}

	public static Double getCallingTotalCreditBack(List<CustomerOrderDetail> pcms
			,CustomerOrderDetail pcm
			,List<CallInternationalRate> cirs
			,Double duration
			,Double totalCreditBack
			,Double costPerMinute
			,Integer index){
		
		// If got sufficient given minutes
		if(pcm.getDetail_calling_minute() > 0){
			// If Present Minutes greater than duration
			if(pcm.getDetail_calling_minute() >= duration){
				// Decrease Present Minutes
				pcm.setDetail_calling_minute(TMUtils.bigOperationTwoReminders(pcm.getDetail_calling_minute().doubleValue(), duration, "sub").intValue());
				// Replace old one to new one
				Collections.replaceAll(pcms, pcms.get(index), pcm);
				totalCreditBack = bigAdd(totalCreditBack, TMUtils.bigMultiply(duration, costPerMinute));
			} else {
				totalCreditBack = bigAdd(totalCreditBack, TMUtils.bigMultiply(pcm.getDetail_calling_minute().doubleValue(), costPerMinute));
				// Decrease Present Minutes
				pcm.setDetail_calling_minute(0);
				// Replace old one to new one
				Collections.replaceAll(pcms, pcms.get(index), pcm);
			}
		}
		return totalCreditBack;
	}
	
	
	public static int judgeDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// BEGIN RetrieveMonthAbbrWithDate
	public static String retrieveMonthAbbrWithDate(Date date){
		if(date==null){
			return "";
		}
		String dateArr[] = dateFormatYYYYMMDD(date).split("-");
		String day = dateArr[2];
		StringBuffer finalDateStrBuff = new StringBuffer();
		if(Integer.parseInt(day) < 10){
			finalDateStrBuff.append(day.charAt(1)+"th ");
		} else {
			finalDateStrBuff.append(day+"th ");
		}
		switch (dateArr[1]) {
		case "01": finalDateStrBuff.append("Jan "); break;
		case "02": finalDateStrBuff.append("Feb "); break;
		case "03": finalDateStrBuff.append("Mar "); break;
		case "04": finalDateStrBuff.append("Apr "); break;
		case "05": finalDateStrBuff.append("May "); break;
		case "06": finalDateStrBuff.append("Jun "); break;
		case "07": finalDateStrBuff.append("Jul "); break;
		case "08": finalDateStrBuff.append("Aug "); break;
		case "09": finalDateStrBuff.append("Sep "); break;
		case "10": finalDateStrBuff.append("Oct "); break;
		case "11": finalDateStrBuff.append("Nov "); break;
		case "12": finalDateStrBuff.append("Dec "); break;
		}
		return finalDateStrBuff.append(dateArr[0]).toString();
	}
	// END RetrieveMonthAbbrWithDate
	
	// BEGIN EarlyTerminationChargeCalculations
	public static Map<String, Object> earlyTerminationDatesCalculation(Date serviceGivenDate
			, Date terminationDate){
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calHalfAnYearAfterServiceGivenDate = Calendar.getInstance(Locale.CHINA);
		Calendar calLegalTerminationDate = Calendar.getInstance(Locale.CHINA);
		
		calHalfAnYearAfterServiceGivenDate.setTime(serviceGivenDate);
		calHalfAnYearAfterServiceGivenDate.add(Calendar.MONTH, 6);
		map.put("charge_amount", terminationDate.getTime() > calHalfAnYearAfterServiceGivenDate.getTime().getTime() ? 99d : 199d);
		
		calLegalTerminationDate.setTime(serviceGivenDate);
		calLegalTerminationDate.add(Calendar.MONTH, 12);
		calLegalTerminationDate.add(Calendar.DAY_OF_MONTH, 1);
		map.put("legal_termination_date", calLegalTerminationDate.getTime());
		
		String serviceGivenDateStr = TMUtils.dateFormatYYYYMMDD(serviceGivenDate);
		String terminationDateStr = TMUtils.dateFormatYYYYMMDD(terminationDate);
		Integer serviceGivenDateMonth = Integer.parseInt(serviceGivenDateStr.substring(serviceGivenDateStr.indexOf("-")+1, serviceGivenDateStr.lastIndexOf("-")));
		Integer terminationDateMonth = Integer.parseInt(terminationDateStr.substring(terminationDateStr.indexOf("-")+1, terminationDateStr.lastIndexOf("-")));
		map.put("months_between_begin_end", terminationDateMonth - serviceGivenDateMonth);
		
		return map;
	}
	// END EarlyTerminationChargeCalculations

	// BEGIN GetInvoiceDueDate
	public static Date getInvoiceDueDate(Date invoiceCreateDay, Integer days){
		
		// set invoice due date begin
		Calendar calInvoiceDueDay = Calendar.getInstance();
		calInvoiceDueDay.setTime(invoiceCreateDay);
		calInvoiceDueDay.add(Calendar.DAY_OF_MONTH, days);
		
		return calInvoiceDueDay.getTime();
		// set invoice due date end

	}
	// END GetInvoiceDueDate
	
	// BEGIN GetLastDateOfMonth
	public static Date getLastDateOfMonth(Date date){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return cal.getTime();
	}
	// BEGIN GetLastDateOfMonth
	
	// BEGIN TerminationRefundCalculations
	public static Map<String, Object> terminationRefundCalculations(Date terminatedDate, Double monthlyCharge){
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date lastDate = TMUtils.getLastDateOfMonth(terminatedDate);
		Integer maxDay = Integer.parseInt(sdf.format(lastDate));
		Integer terminatedDay = Integer.parseInt(sdf.format(terminatedDate));
		Integer remainingDays = maxDay - terminatedDay;
		
		BigDecimal bigMaxDay = new BigDecimal(maxDay);
		BigDecimal bigMonthlyCharge = new BigDecimal(monthlyCharge);
		BigDecimal dailyCharge = bigMonthlyCharge.divide(bigMaxDay, 5, BigDecimal.ROUND_DOWN);
		BigDecimal bigRemainingDays = new BigDecimal(remainingDays);
		
		map.put("remaining_days", remainingDays);
		map.put("refund_amount", dailyCharge.multiply(bigRemainingDays).doubleValue());
		map.put("last_date_of_month", lastDate);
		
		return map;
	}
	// END TerminationRefundCalculations
	
	// BEGIN isDateFormat
	public static boolean isDateFormat(String dateStr, String separator){
		String rexpFormat = "^(\\d{4}\\" + separator + "\\d{1,2}\\" + separator + "\\d{1,2})|(\\d{1,2}\\" + separator + "\\d{1,2}\\" + separator + "\\d{4})$";
		Pattern pat = Pattern.compile(rexpFormat);
		Matcher mat = pat.matcher(dateStr);
		String rexpFormat2 = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pat2 = Pattern.compile(rexpFormat2);
		Matcher mat2 = pat2.matcher(dateStr);
		return mat.matches() && mat2.matches();
	}
	// END isDateFormat
	
	// BEGIN isNumber
	public static boolean isNumber(String numberStr){
		return Pattern.compile("[0-9]+").matcher(numberStr).matches();
	}
	// EDN isNumber
	
	// BEGIN isSameMonth
	public static boolean isSameMonth(Date compareDate1, Date compareDate2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(compareDate1).equals(sdf.format(compareDate2));
	}
	// END isSameMonth
	
	// BEGIN BigDecimal OPERATIONS
	// ADDITION: Double + Double
	public static Double bigAdd(Double addend1, Double addend2){
		BigDecimal bigAddend1 = new BigDecimal(addend1);
		BigDecimal bigAddend2 = new BigDecimal(addend2);
		return Double.parseDouble(fillDecimalPeriod(bigAddend1.add(bigAddend2).doubleValue()));
	}
	// SUBSTRACTION: Double - Double
	public static Double bigSub(Double minuend, Double subtrahend){
		BigDecimal bigMinuend = new BigDecimal(minuend);
		BigDecimal bigSubtrahend = new BigDecimal(subtrahend);
		return Double.parseDouble(fillDecimalPeriod(bigMinuend.subtract(bigSubtrahend).doubleValue()));
	}
	// MULTIPLICATION: Double * Double
	public static Double bigMultiply(Double multiplier1, Double multiplier2){
		BigDecimal bigMultiplier1 = new BigDecimal(multiplier1);
		BigDecimal bigMultiplier2 = new BigDecimal(multiplier2);
		return Double.parseDouble(fillDecimalPeriod(bigMultiplier1.multiply(bigMultiplier2).doubleValue()));
	}
	// MULTIPLICATION: Double * Integer
	public static Double bigMultiply(Double multiplier1, Integer multiplier2){
		BigDecimal bigMultiplier1 = new BigDecimal(multiplier1);
		BigDecimal bigMultiplier2 = new BigDecimal(multiplier2);
		return Double.parseDouble(fillDecimalPeriod(bigMultiplier1.multiply(bigMultiplier2).doubleValue()));
	}
	// DIVISION: Double / Double
	public static Double bigDivide(Double divisor1, Double divisor2){
		BigDecimal bigDivisor1 = new BigDecimal(divisor1);
		BigDecimal bigDivisor2 = new BigDecimal(divisor2);
		return Double.parseDouble(fillDecimalPeriod(bigDivisor1.divide(bigDivisor2, 5, BigDecimal.ROUND_DOWN).doubleValue()));
	}
	
	// FOUR OPERATIONS, BUT ONLY LEFT TWO REMINDERS
	public static Double bigOperationTwoReminders(Double operator1, Double operator2, String type){
		switch (type) {
		case "add":
			return bigAdd(operator1, operator2);
		case "sub":
			return bigSub(operator1, operator2);
		case "mul":
			return bigMultiply(operator1, operator2);
		case "div":
			return bigDivide(operator1, operator2);
		}
		return 0d;
	}
	// END BigDecimal OPERATIONS
}

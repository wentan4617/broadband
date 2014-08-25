package com.tm.broadband.util.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StevenTest {
	
	public static void main(String[] args) throws ParseException {
//		//CyberparkTopup30_5000_20140605.csv
//		String filePath = TMUtils.createPath("CyberparkTopup30_5000_20140605.csv");
//		List<Voucher> vs = VoucherRecordUtility.vs(filePath);
//		for (Voucher v : vs) {
//			System.out.print(v.getSerial_number()+"\t");
//			System.out.print(v.getCard_number()+"\t");
//			System.out.print(v.getFace_value()+"\t");
//			System.out.println(v.getComment());
//		}

//		Calendar beginCal = Calendar.getInstance();
//		beginCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH)-3);
//		beginCal.set(Calendar.DAY_OF_MONTH, 1);
//		beginCal.set(Calendar.HOUR_OF_DAY, 0);
//		beginCal.set(Calendar.MINUTE, 0);
//		beginCal.set(Calendar.SECOND, 0);
//		System.out.println(beginCal.getTime());
//
//		Calendar endCal = Calendar.getInstance();
//		endCal.set(Calendar.DAY_OF_MONTH, 1);
//		endCal.add(Calendar.DAY_OF_MONTH, -1);
//		endCal.set(Calendar.HOUR_OF_DAY, 0);
//		endCal.set(Calendar.MINUTE, 0);
//		endCal.set(Calendar.SECOND, 0);
//		System.out.println(endCal.getTime());

//		String filePath = TMUtils.createPath("CallPlus Calling Record June2014 90445601.csv");
//		List<CustomerCallingRecordCallplus> ccrs = CallingRecordUtility_CallPlus.ccrcs(filePath);
//		for (CustomerCallingRecordCallplus ccr : ccrs) {
//			System.out.println(ccr.getDate_str());
//		}
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.add(Calendar.DAY_OF_MONTH, -7);
//		System.out.println(cal.getTime());
		// Second Encode
//		String secondEncode = TMUtils.generateRandomString(3)+DigestUtils.md5Hex("cyberpark123z")+TMUtils.generateRandomString(3);
//		System.out.println(secondEncode.length());
//		System.out.println(secondEncode);
//		// Second Decode
//		String secondDecode = secondEncode.substring(3, secondEncode.length()-3);
//		System.out.println(secondDecode);
		
		
//		SimpleMapperCreator smc = new SimpleMapperCreator();
//		smc.setAuthor("StevenChen");
//		smc.setModel("TMSMaterialCategory");
//		smc.setTable("tms_material_category");
//		smc.initial();
		
//		Calendar cal = Calendar.getInstance(Locale.CHINA);
//		cal.setTime(new Date());
//		cal.add(Calendar.WEEK_OF_MONTH, -1);
//		cal.add(Calendar.DAY_OF_WEEK, -1);
//		System.out.println(TMUtils.dateFormatYYYYMMDD(cal.getTime()));
//		cal.add(Calendar.DAY_OF_WEEK, -2);
//		System.out.println(TMUtils.dateFormatYYYYMMDD(cal.getTime()));
		

//		Calendar lastMonthFirst = Calendar.getInstance(Locale.CHINA);
//		lastMonthFirst.set(Calendar.DAY_OF_MONTH, 1);
//		lastMonthFirst.add(Calendar.MONTH, -1);
//		lastMonthFirst.set(Calendar.HOUR_OF_DAY, 0);
//		lastMonthFirst.set(Calendar.MINUTE, 0);
//		lastMonthFirst.set(Calendar.SECOND, 0);
//		System.out.println(lastMonthFirst.getTime());
		
//		Calendar cal = Calendar.getInstance(Locale.CHINA);
//		cal.setTime(new Date());
//		cal.add(Calendar.WEEK_OF_MONTH, 1);
//		
//		System.out.println(TMUtils.dateFormatYYYYMMDD(cal.getTime()));
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.MONTH, 0);
//		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//		System.out.println(TMUtils.dateFormatYYYYMMDD(cal.getTime()));
		
//		List<StatisticBilling> annualInvoices = new ArrayList<StatisticBilling>();
//		TMUtils.thisYearDateForBillingStatistic(2014, annualInvoices);
//		
//		for (StatisticBilling sb : annualInvoices) {
//			System.out.println(sb.getBillingDate());
//			System.out.println(sb.getBillingAmount());
//		}
		
//		String str = "$asdasdasdas$3123123$5432535";
//		System.out.println(TMUtils.useDollarSymbolInReplace(str));
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
//		Date currentMonthMax = cal.getTime();
//		System.out.println(TMUtils.dateFormatYYYYMMDD(currentMonthMax));
//		cal.add(Calendar.MONTH, -3);
//		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
//		Date dueMonthMax = cal.getTime();
//		System.out.println(TMUtils.dateFormatYYYYMMDD(dueMonthMax));
		
		
		
//		ModelTest mt = new ModelTest();
//		mt.setId(1001);
//		mt.setName("Steven");
//		mt.setAge(24);
//		mt.setGendar("male");
//		mt.setJob("DiaoSiYuan");
//		mt.setSal(-0.01);
//		mt.setActive(true);
//		List<ModelTest> mts = new ArrayList<ModelTest>();
//		mts.add(mt);
//		mt.setAll(mts);
//		List<Object> objs = new ArrayList<Object>();
//		objs.add("111");
//		objs.add(false);
//		mt.setObjs(objs);
//		mt.setMt(mt);
//		mt.setBirth(new Date());
//
//		Console.log(mt);
		
//		Calendar cal_7th_1hr = Calendar.getInstance();
//		cal_7th_1hr.set(Calendar.DATE, 6);
//		cal_7th_1hr.set(Calendar.HOUR, 13);
//		cal_7th_1hr.set(Calendar.MINUTE, 0);
//		cal_7th_1hr.set(Calendar.SECOND, 0);
//		cal_7th_1hr.set(Calendar.MILLISECOND, 0);
//		
//		long seventh12hr = cal_7th_1hr.getTimeInMillis();
//		long currentTime = System.currentTimeMillis();
//		System.out.println(TMUtils.dateFormatYYYYMMDDHHMMSS(cal_7th_1hr.getTime())+", Millisecond: "+seventh12hr); 
//		
//		System.out.println(TMUtils.dateFormatYYYYMMDDHHMMSS(new Date())+", Millisecond: "+currentTime);
//		
//		// If less than seventh 1 o'clock of the month
//		if(currentTime<seventh12hr){
//			System.out.println("Less than seventh 1 o'clock!");
//			Calendar cal = cal_7th_1hr;
//			cal.set(Calendar.HOUR, 0);
//			System.out.println("Assign Next Invoice Create Date:"+TMUtils.dateFormatYYYYMMDDHHMMSS(cal.getTime()));
//		} else {
//			System.out.println("Greater than seventh 1 o'clock!");
//			Calendar cal = cal_7th_1hr;
//			cal.set(Calendar.HOUR, 0);
//			cal.add(Calendar.MONTH, 1);
//			System.out.println("Assign Next Invoice Create Date:"+TMUtils.dateFormatYYYYMMDDHHMMSS(cal.getTime()));
//		}

//		String fix_mobile_country[] = "BANGLADESH,MALAYSIA,CAMBODIA,SINGAPORE,CANADA,SOUTH KOREA,CHINA,USA,HONG KONG,VIETNAM,INDIA".split(",");
//		
//		for (String country : fix_mobile_country) {
//			System.out.println(country);
//		}
	}
}

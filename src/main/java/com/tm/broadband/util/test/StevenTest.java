package com.tm.broadband.util.test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerChorusBroadbandASIDRecord;
import com.tm.broadband.util.CallingRecordUtility;
import com.tm.broadband.util.SimpleMapperCreator;
import com.tm.broadband.util.TMUtils;


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
		
//		System.out.println(TMUtils.getRentalChargeFee(TMUtils.parseDateYYYYMMDD("2014-07-01"), 18d));
		
//		System.out.println("asdasdsd$".contains("$"));
		
		

//		SimpleMapperCreator smc = new SimpleMapperCreator();
//		smc.setAuthor("CyberPark");
//		smc.setModel("CustomerChorusBroadbandASIDRecord");
//		smc.setTable("tm_customer_chorus_broadband_asid_record");
//		smc.initial();
		
		
		
//		String str = "cyberpark/ccc/asd";
//		System.out.println(str.substring(0, str.lastIndexOf("/")+1));
		
//		String url = "/broadband-user/crm/customer/invoice/payment/credit-card/{id}/{redirect_from}/{invoice_status}";
//
//		String finalUrl = url.substring(0, url.lastIndexOf("/"));
//		finalUrl = finalUrl.substring(0, finalUrl.lastIndexOf("/"));
//		finalUrl = finalUrl.substring(0, finalUrl.lastIndexOf("/"));
//		
//		System.out.println(finalUrl);
		
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -2);
//		System.out.println(cal.getTime());
		
		/***
		 * 
		 * 1. Filter prefixes
		 * 		0064-, 0064, 64-, 64
		 * 
		 */
//		String phone_number = "92818799";
//		
////		System.out.println(TMUtils.retrieveNonAreaCodeVoIPNumber(phone_number));
//		
//		Double call_minutes = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.retrieveVoIPChargePerThreeMinutes(10)/60)));
//		
//		System.out.println(call_minutes);
		
		
//		List<CustomerCallRecord> ccrs = CallingRecordUtility.ccrs("E:\\项目\\ISP项目开发\\拨打记录账单\\账单电话拨打细目\\TMS_20140928_EB05.csv");
//		for (CustomerCallRecord ccr : ccrs) {
//			
//			if(ccr.getClear_service_id().toString().startsWith("1")){
//				
//				CustomerChorusBroadbandASIDRecord ccbasidr = new CustomerChorusBroadbandASIDRecord();
//				ccbasidr.setStatement_date(ccr.getStatement_date());
//				ccbasidr.setRecord_type(ccr.getRecord_type());
//				ccbasidr.setClear_service_id(ccr.getClear_service_id());
//				ccbasidr.setDate_from(ccr.getDate_from());
//				ccbasidr.setDate_to(ccr.getDate_to());
//				ccbasidr.setCharge_date_time(ccr.getCharge_date_time());
//				ccbasidr.setDuration(ccr.getDuration());
//				ccbasidr.setOot_id(ccr.getOot_id());
//				ccbasidr.setBilling_description(ccr.getBilling_description());
//				ccbasidr.setAmount_excl(ccr.getAmount_excl());
//				ccbasidr.setAmount_incl(ccr.getAmount_incl());
//				ccbasidr.setJuristiction(ccr.getJuristiction());
//				ccbasidr.setPhone_called(ccr.getPhone_called());
//				Console.log(ccbasidr);
//				
//			}
//		}
		
		
		
	}
	
}

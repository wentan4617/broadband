package com.tm.broadband.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.tm.broadband.model.Customer;


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

		Calendar beginCal = Calendar.getInstance();
		beginCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH)-3);
		beginCal.set(Calendar.DAY_OF_MONTH, 1);
		beginCal.set(Calendar.HOUR_OF_DAY, 0);
		beginCal.set(Calendar.MINUTE, 0);
		beginCal.set(Calendar.SECOND, 0);
		System.out.println(beginCal.getTime());

		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.DAY_OF_MONTH, 1);
		endCal.add(Calendar.DAY_OF_MONTH, -1);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		System.out.println(endCal.getTime());
	}
}

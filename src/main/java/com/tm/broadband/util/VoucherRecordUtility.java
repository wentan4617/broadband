package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tm.broadband.model.Voucher;

public class VoucherRecordUtility {
	
	private static Integer serialNumberIndex 	= 0;
	private static Integer cardNumberIndex 		= 1;
	private static Integer faceValueIndex 		= 2;
	private static Integer commentIndex			= 3;
	
	// Get vouchers
	public static List<Voucher> vs(String csvPath){
		
		List<Voucher> vs = new ArrayList<Voucher>();

		try {
			File csv = new File(csvPath); // CSV File
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			boolean header = true;
			while ((line = br.readLine()) != null) {
				if(header){ header=false; continue; }
				vs.add(getVoucher(line));
			}
			br.close();

		} catch (Exception e) { e.printStackTrace(); }
		return vs;
	}
	
	// Iterating voucher record
	private static Voucher getVoucher(String line){
		Voucher v = new Voucher();
		String arr[] = line.split(",");
		
		// String: Serial Number
		if(arr.length > serialNumberIndex){
			v.setSerial_number(arr[serialNumberIndex]);
			
			// String: Card Number
			if(arr.length > cardNumberIndex){
				v.setCard_number(arr[cardNumberIndex]);;
				
				// Double: Face value
				if(arr.length > faceValueIndex){
					v.setFace_value(Double.parseDouble(arr[faceValueIndex]));
					
					// String: Comment
					if(arr.length > commentIndex){
						v.setComment(arr[commentIndex]);
					}
				}
			}
		}
		return v;
	}
}

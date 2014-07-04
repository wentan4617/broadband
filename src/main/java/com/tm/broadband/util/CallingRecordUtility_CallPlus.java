package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tm.broadband.model.CustomerCallingRecordCallplus;

public class CallingRecordUtility_CallPlus {

	private static Integer originalNumberIndex			= 2;
	private static Integer destinationNumberIndex		= 3;
	private static Integer descriptionIndex 			= 4;
	private static Integer dateIndex 					= 9;
	private static Integer lengthIndex 					= 10;
	private static Integer chargedFeeIndex				= 11;
	private static Integer typeIndex					= 14;
	
	// Get vouchers
	public static List<CustomerCallingRecordCallplus> ccrcs(String csvPath){
		
		List<CustomerCallingRecordCallplus> ccrs = new ArrayList<CustomerCallingRecordCallplus>();

		try {
			File csv = new File(csvPath); // CSV File
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			boolean header = true;
			while ((line = br.readLine()) != null) {
				if(header){ header=false; continue; }
				// If contains
				if(line.contains("\"")){
					// Truncate middle part, generally referred to description
					String lineTempMiddle = line.substring(line.indexOf("\"")+1, line.lastIndexOf("\""));
					if(lineTempMiddle.contains(",")){
						// Truncate front part
						String lineTempFront = line.substring(0, line.indexOf("\""));
						// Truncate back part
						String lineTempBack = line.substring(line.lastIndexOf("\"")+1, line.length());
						// Process middle part
						lineTempMiddle = lineTempMiddle.substring(0, lineTempMiddle.indexOf(","))+"."+lineTempMiddle.substring(lineTempMiddle.indexOf(",")+1, lineTempMiddle.length());
						line = lineTempFront.concat(lineTempMiddle.concat(lineTempBack));
					}
				}
				ccrs.add(getCustomerCallingRecordCallplus(line));
			}
			br.close();

		} catch (Exception e) { e.printStackTrace(); }
		return ccrs;
	}
	
	// Iterating voucher record
	private static CustomerCallingRecordCallplus getCustomerCallingRecordCallplus(String line){
		CustomerCallingRecordCallplus ccr = new CustomerCallingRecordCallplus();
		String arr[] = line.split(",");
		
		// String: Original Number
		if(arr.length > originalNumberIndex){
			ccr.setOriginal_number(arr[originalNumberIndex]);
			
			// String: Destination Number
			if(arr.length > destinationNumberIndex){
				ccr.setDestination_number(arr[destinationNumberIndex]);
				
				// String: Description
				if(arr.length > descriptionIndex){
					ccr.setDescription(arr[descriptionIndex]);
					
					// Date: Date
					if(arr.length > dateIndex){
						String dateStr = arr[dateIndex];
						ccr.setDate(TMUtils.parseDateDDMMYYYYHHMMSS(dateStr.substring(0, dateStr.lastIndexOf(" "))));
						
						// Integer: Length
						if(arr.length > lengthIndex){
							ccr.setLength(Integer.parseInt(arr[lengthIndex]));

							// Double: ChargedFee
							if(arr.length > chargedFeeIndex){
								String chargedFeeStr = arr[chargedFeeIndex];
								ccr.setCharged_fee(Double.parseDouble(chargedFeeStr.replace("$", "")));

								// String: Type
								if(arr.length > typeIndex){
									ccr.setType(arr[typeIndex]);
								}
							}
						}
						
					}
				}
			}
		}
		return ccr;
	}
}

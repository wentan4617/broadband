package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tm.broadband.model.CallInternationalRate;

public class CallInternationalRateUtility {
	
	private static Integer areaPrefixIndex 			= 1;
	private static Integer rateTypeIndex 			= 2;
	private static Integer areaNameIndex 			= 3;
	private static Integer rateCostIndex			= 5;
	
	// Get call international rates
	public static List<CallInternationalRate> cirs(String csvPath){
		
		List<CallInternationalRate> cirs = new ArrayList<CallInternationalRate>();

		try {
			File csv = new File(csvPath); // CSV File
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			boolean header = true;
			while ((line = br.readLine()) != null) {
				if(header){ header=false; continue; }
				cirs.add(getCallInternationalRate(line));
			}
			br.close();

		} catch (Exception e) { e.printStackTrace(); }
		return cirs;
	}
	
	// Iterating calling record
	private static CallInternationalRate getCallInternationalRate(String line){
		CallInternationalRate cir = new CallInternationalRate();
		String arr[] = line.split(",");
		
		// String: Area Prefix
		if(arr.length > areaPrefixIndex){
			String areaPrefixStr = arr[areaPrefixIndex];
			if(!"".equals(areaPrefixStr.trim())){
				cir.setArea_prefix(areaPrefixStr.replaceFirst("^0*", ""));
			}
			
			// String: Rate Type
			if(arr.length > rateTypeIndex){
				cir.setRate_type(arr[rateTypeIndex]);
				
				// String: Area Name
				if(arr.length > areaNameIndex){
					cir.setArea_name(arr[areaNameIndex]);
					
					// Double: Rate Cost
					if(arr.length > rateCostIndex){
						if(arr[rateCostIndex] != null && !"".equals(arr[rateCostIndex].trim())){
							cir.setRate_cost(Double.parseDouble(arr[rateCostIndex]));
						}
					}
				}
			}
		}
		return cir;
	}
}

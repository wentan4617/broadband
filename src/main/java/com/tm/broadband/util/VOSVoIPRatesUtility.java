package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tm.broadband.model.VOSVoIPRate;

public class VOSVoIPRatesUtility {
	private static Integer areaPrefixIndex 			= 1;
	private static Integer rateTypeIndex 			= 2;
	private static Integer areaNameIndex 			= 3;
	private static Integer billed_rate				= 4;
	private static Integer billed_cycle				= 5;
	private static Integer billed_per_min			= 6;
	
	// Get call international rates
	public static List<VOSVoIPRate> cirs(String csvPath){
		
		List<VOSVoIPRate> cirs = new ArrayList<VOSVoIPRate>();

		try {
			File csv = new File(csvPath); // CSV File
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			boolean header = true;
			while ((line = br.readLine()) != null) {
				if(header){ header=false; continue; }
				cirs.add(getVOSVoIPRates(line));
			}
			br.close();

		} catch (Exception e) { e.printStackTrace(); }
		return cirs;
	}
	
	// Iterating calling record
	private static VOSVoIPRate getVOSVoIPRates(String line){
		VOSVoIPRate vosVoIPRates = new VOSVoIPRate();
		String arr[] = line.split(",");
		
		// String: Area Prefix
		if(arr.length > areaPrefixIndex){
			String areaPrefixStr = arr[areaPrefixIndex];
			if(!"".equals(areaPrefixStr.trim())){
				vosVoIPRates.setArea_prefix(areaPrefixStr.replaceFirst("^0*", ""));
			}
			
			// String: Rate Type
			if(arr.length > rateTypeIndex){
				vosVoIPRates.setRate_type(arr[rateTypeIndex]);
				
				// String: Area Name
				if(arr.length > areaNameIndex){
					vosVoIPRates.setArea_name(arr[areaNameIndex]);
					
					// Double: Billed Rate
					if(arr.length > billed_rate){
						if(arr[billed_rate] != null && !"".equals(arr[billed_rate].trim())){
							vosVoIPRates.setBilled_rate(Double.parseDouble(arr[billed_rate]));
						}
						
						// Double: Billed Cycle
						if(arr.length > billed_cycle){
							if(arr[billed_cycle] != null && !"".equals(arr[billed_cycle].trim())){
								vosVoIPRates.setBilled_cycle(Double.parseDouble(arr[billed_cycle]));
							}
						}
						
						// Double: Billed Per Minute
						if(arr.length > billed_per_min){
							if(arr[billed_per_min] != null && !"".equals(arr[billed_per_min].trim())){
								vosVoIPRates.setBilled_per_min(Double.parseDouble(arr[billed_per_min]));
							}
						}
					}
				}
			}
		}
		return vosVoIPRates;
	}
}

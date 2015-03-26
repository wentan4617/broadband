package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tm.broadband.model.VOSVoIPCallRecord;

public class VOSVoIPCallRecordUtility {

	private static Integer oriNumberIndex 			= 0;
	private static Integer destNumberIndex 			= 1;
	private static Integer callStartIndex 			= 2;
	private static Integer callLengthIndex			= 4;
	private static Integer chargeLengthIndex		= 5;
	private static Integer callFeeIndex				= 6;
	private static Integer callCostIndex			= 7;
	private static Integer callTypeIndex			= 17;
	private static Integer areaPrefixIndex			= 18;
	
	// Get VOS VoIP Call Records
	public static List<VOSVoIPCallRecord> vosVoIPCallRecords(String csvPath){
		
		List<VOSVoIPCallRecord> vosVoIPCallRecords = new ArrayList<VOSVoIPCallRecord>();

		try {
			File csv = new File(csvPath); // CSV File
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			boolean header = true;
			while ((line = br.readLine()) != null) {
				if(header){ header=false; continue; }
				String arr[] = line.split(",");
				if(arr.length > callCostIndex){
					// If cost or charge less equals than 0
					if(((arr[callCostIndex] != null && !"".equals(arr[callCostIndex].trim()) && Double.parseDouble(arr[callCostIndex])<=0)
					   && (arr[callFeeIndex] != null && !"".equals(arr[callFeeIndex].trim()) && Double.parseDouble(arr[callFeeIndex])<=0))){
						continue;
					}
					// If is Local or Inner-Net
					if(arr[callTypeIndex] != null && !"".equals(arr[callTypeIndex].trim()) && "Local".equals(arr[callTypeIndex]) || "Inner-Net".equals(arr[callTypeIndex])){
						continue;
					}
				}
				VOSVoIPCallRecord vosVoIPCallRecordHolder = getVOSVoIPCallRecords(line);
				if(vosVoIPCallRecordHolder!=null){
					vosVoIPCallRecords.add(vosVoIPCallRecordHolder);
				}
			}
			br.close();

		} catch (Exception e) { e.printStackTrace(); }
		return vosVoIPCallRecords;
	}
	
	// Iterating calling record
	private static VOSVoIPCallRecord getVOSVoIPCallRecords(String line){
		VOSVoIPCallRecord vosVoIPCallRecord = new VOSVoIPCallRecord();
		String arr[] = line.split(",");
		
		// String: Original Number
		if(arr.length > oriNumberIndex){
			vosVoIPCallRecord.setOri_number(arr[oriNumberIndex]);
			
			// String: Destination Number
			if(arr.length > destNumberIndex){
				vosVoIPCallRecord.setDest_number(arr[destNumberIndex]);
				
				// DateTime: Call Start Date
				if(arr.length > callStartIndex){
					String callStartStr = arr[callStartIndex];
					if(!"".equals(callStartStr.trim())){
						if(callStartStr.contains("/")){
							// if is yyyy/MM/dd
							if(callStartStr.indexOf("/")==4){
								vosVoIPCallRecord.setCall_start(TMUtils.parseDate5YYYYMMDDHHMM(callStartStr));
							}
							// if is dd/MM/yyyy
							if(callStartStr.indexOf("/")==1 || callStartStr.indexOf("/")==2){
								vosVoIPCallRecord.setCall_start(TMUtils.parseDate4YYYYMMDDHHMM(callStartStr));
							}
						} else {
							vosVoIPCallRecord.setCall_start(TMUtils.parseDate2YYYYMMDD(callStartStr));
						}
					}
					
					// Integer: Call Length
					if(arr.length > callLengthIndex){
						if(arr[callLengthIndex]!=null && !"".equals(arr[callLengthIndex].trim())){
							vosVoIPCallRecord.setCall_length(Double.parseDouble(arr[callLengthIndex]));
						}
						
						
						// If Not Satisfied Then Terminate
						if((arr[callLengthIndex]==null || Integer.parseInt(arr[callLengthIndex])<=0)
						&& (arr[chargeLengthIndex]==null || Integer.parseInt(arr[chargeLengthIndex])<=0)
						&& (arr[callFeeIndex]==null || Double.parseDouble(arr[callFeeIndex])<=0)
						&& (arr[callCostIndex]==null || Double.parseDouble(arr[callCostIndex])<=0)){
							return null;
						}
						
						
						// Integer: Charge Length
						if(arr.length > chargeLengthIndex){
							if(arr[chargeLengthIndex]!=null && !"".equals(arr[chargeLengthIndex].trim())){
								vosVoIPCallRecord.setCharge_length(Integer.parseInt(arr[chargeLengthIndex]));
							}
						}
						
						// Double: Call Fee
						if(arr.length > callFeeIndex){
							if(arr[callFeeIndex] != null && !"".equals(arr[callFeeIndex].trim())){
								vosVoIPCallRecord.setCall_fee(Double.parseDouble(arr[callFeeIndex]));
							}
						}
						
						// Double: Call Cost
						if(arr.length > callCostIndex){
							if(arr[callCostIndex] != null && !"".equals(arr[callCostIndex].trim())){
								vosVoIPCallRecord.setCall_cost(Double.parseDouble(arr[callCostIndex]));
							}
						}

						// String: Call Type
						if(arr.length > callTypeIndex){
							vosVoIPCallRecord.setCall_type(arr[callTypeIndex]);
						}

						// Integer: Area Prefix
						if(arr.length > areaPrefixIndex){
							if(arr[areaPrefixIndex]!=null && !"".equals(arr[areaPrefixIndex].trim())){
								vosVoIPCallRecord.setArea_prefix(Integer.parseInt(arr[areaPrefixIndex]));
							}
						}
					}
				}
			}
		}
		return vosVoIPCallRecord;
	}
}

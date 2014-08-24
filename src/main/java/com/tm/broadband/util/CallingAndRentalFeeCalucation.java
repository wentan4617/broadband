package com.tm.broadband.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.CustomerCallingRecordCallplusMapper;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.pdf.InvoicePDFCreator;

public class CallingAndRentalFeeCalucation {
	
	// BEGIN CustomerCallRecord OPERATION
	public static Double ccrRentalOperation(CustomerInvoice ci
			, Boolean isRegenerate, String pstn_number
			, List<CustomerInvoiceDetail> cids, Double totalAmountPayable
			, CustomerCallRecordMapper customerCallRecordMapper){
		
		Double totalAmountIncl = 0d;
		
		CustomerCallRecord ccrQuery = new CustomerCallRecord();
		
		if(isRegenerate){
			ccrQuery.getParams().put("where", "query_last_month_rental_records");
			ccrQuery.getParams().put("invoice_id", ci.getId());
		} else {
			ccrQuery.getParams().put("where", "query_unused_rental_records");
			ccrQuery.getParams().put("invoice_id", null);
		}
		ccrQuery.getParams().put("clear_service_id", TMUtils.formatPhoneNumber(pstn_number));
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
			
			if(!isRegenerate){
				CustomerCallRecord ccr = new CustomerCallRecord();
				ccr.setInvoice_id(ci.getId());
				ccr.getParams().put("where", "last_rental_records");
				ccr.getParams().put("clear_service_id", pstn_number);
				ccr.getParams().put("invoice_id", null);
				customerCallRecordMapper.updateCustomerCallRecord(ccr);
			}
		}
		
		return TMUtils.bigAdd(totalAmountPayable, totalAmountIncl);
	}
	
	// BEGIN CustomerCallRecord OPERATION
	public static Double ccrOperation(CustomerInvoice ci, String orderType, Boolean isRegenerate
			, List<CustomerOrderDetail> pcms
			, String pstn_number, List<CustomerInvoiceDetail> cids
			, InvoicePDFCreator invoicePDF, Double totalPayableAmouont
			, CustomerCallRecordMapper customerCallRecordMapper
			, CallInternationalRateMapper callInternationalRateMapper
			, CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper
			, String customerType){
		
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
		if(isRegenerate){
			ccrTemp.getParams().put("invoice_id", ci.getId());
		} else {
			ccrTemp.getParams().put("invoice_id", null);
		}
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
			if(isOnRate){ costPerMinute = cirs.get(0).getRate_cost(); }
			
			// DURATION/SECONDS
			Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccr.getDuration(), 60d))));
			
			ccr.setCallType(TMUtils.strCapital(callType));
			
			if(//(is0900 || isFax || isNational || isBusinessLocal) || 
					isOnRate && duration>0){

				// If have reminder, then cut reminder and plus 1 minute, for example: 5.19 change to 6
				if(TMUtils.isReminder(String.valueOf(duration))){
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
			ccr.setFormated_duration(duration<=0 ? "" : TMUtils.timeFormat.format(Double.parseDouble(String.valueOf(duration))).replace(".", ":"));
			ccrs.add(ccr);
		}
		
		if(!isRegenerate){
			if(ccrs!=null && ccrs.size()>0){
				CustomerCallRecord ccrUpdate = new CustomerCallRecord();
				ccrUpdate.setInvoice_id(ci.getId());
				ccrUpdate.getParams().put("where", "last_call_records");
				ccrUpdate.getParams().put("clear_service_id", TMUtils.formatPhoneNumber(pstn_number));
				ccrUpdate.getParams().put("invoice_id", null);
				customerCallRecordMapper.updateCustomerCallRecord(ccrUpdate);
				ccrUpdate = null;
			}
		}
		
		// END Chorus Style Calling Record
		

		// BEGIN Callplus Style Calling Record
		CustomerCallingRecordCallplus ccrcQuery = new CustomerCallingRecordCallplus();
		ccrcQuery.getParams().put("where", "query_last_calling_records");
		ccrcQuery.getParams().put("orderby", "ORDER BY date ASC");
		ccrcQuery.getParams().put("original_number", TMUtils.formatPhoneNumber(pstn_number));
		if(isRegenerate){
			ccrcQuery.getParams().put("invoice_id", ci.getId());
		} else {
			ccrcQuery.getParams().put("invoice_id", null);
		}
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
				String fix_mobile_country[] = "Bangladesh,Malaysia,Cambodia,Singapore,Canada,South Korea,China,Usa,Hong Kong,Vietnam,India".split(",");
				String fixMobileCountry = "";
				for (String country : fix_mobile_country) {
					if(ccrc.getDescription().contains(country)){
						fixMobileCountry = country;
					}
				}
				cir.getParams().put("area_name", "".equals(fixMobileCountry) ? ccrc.getDescription() : fixMobileCountry);
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
			Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccrc.getLength(), 60d))));
			
			CustomerCallRecord ccr = new CustomerCallRecord();
			ccr.setClear_service_id(ccrc.getOriginal_number());
			ccr.setCharge_date_time(ccrc.getDate());
			ccr.setPhone_called(ccrc.getDestination_number());
			ccr.setBilling_description(ccrc.getDescription());
			ccr.setCallType(TMUtils.strCapital(callType));
			ccr.setAmount_incl(ccrc.getCharged_fee());

			if(//(is0900 || isFax || isNational || isBusinessLocal) || 
					isOnRate){

				// If have reminder, then cut reminder and plus 1 minute, for example: 5.19 change to 6
				if(TMUtils.isReminder(String.valueOf(duration))){
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
			ccr.setFormated_duration(TMUtils.timeFormat.format(Double.parseDouble(String.valueOf(duration))).replace(".", ":"));
			ccrs.add(ccr);
			
		}
		
		if(!isRegenerate){
			if(ccrcs!=null && ccrcs.size()>0){
				CustomerCallingRecordCallplus ccrcUpdate = new CustomerCallingRecordCallplus();
				ccrcUpdate.setInvoice_id(ci.getId());
				ccrcUpdate.getParams().put("where", "last_calling_records");
				ccrcUpdate.getParams().put("original_number", TMUtils.formatPhoneNumber(pstn_number));
				ccrcUpdate.getParams().put("invoice_id", null);
				customerCallingRecordCallplusMapper.updateCustomerCallingRecordCallplus(ccrcUpdate);
				ccrcUpdate = null;
			}
		}
		
		// END Callplus Style Calling Record
		
		
		CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
		cid.setInvoice_detail_name("Calling Charge : ( " + pstn_number + " )");
		cid.setInvoice_detail_price(TMUtils.bigSub(totalAmountIncl, totalCreditBack));
		cid.setInvoice_detail_desc("Total Charge: "+ totalAmountIncl + "\nCredit Back: "+ TMUtils.fillDecimalPeriod(totalCreditBack));
		cid.setInvoice_detail_unit(1);
		cid.setInvoice_detail_type("calling-charge");
		
		cids.add(cid);
		
		invoicePDF.setCcrs(ccrs);
		
		totalAmountIncl = TMUtils.bigSub(totalAmountIncl, totalCreditBack);
		
		ccrTemp = null;
		ccrsTemp = null;
		ccrcQuery = null;
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
					
					String fix_mobile_country[] = "BANGLADESH,MALAYSIA,CAMBODIA,SINGAPORE,CANADA,SOUTH KOREA,CHINA,USA,HONG KONG,VIETNAM,INDIA".split(",");
					
					for (String country : fix_mobile_country) {
						if(cirAreaName.contains(country)){
							
							totalCreditBack = getCallingTotalCreditBack(pcms, pcm, cirs, duration, totalCreditBack, costPerMinute, index);
							index++;
							
						} else if(pcmDetailDesc.contains(cirs.get(0).getArea_prefix()) 
								&& pcmDetailDesc.contains(cirAreaName.split(" ")[0])
								&& (!cirAreaName.contains("MOBILE") && !cirAreaName.contains("MOB"))){
							totalCreditBack = getCallingTotalCreditBack(pcms, pcm, cirs, duration, totalCreditBack, costPerMinute, index);
							index++;
						}
					}
//					fix_mobile_country.contains(cirAreaName.split(" MOBILE")[0])
//					 ||fix_mobile_country.contains(cirAreaName.split(" MOBILE")
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
		if(pcm.getDetail_calling_minute()!=null && pcm.getDetail_calling_minute() > 0){
			// If Present Minutes greater than duration
			if(pcm.getDetail_calling_minute() >= duration){
				// Decrease Present Minutes
				pcm.setDetail_calling_minute(TMUtils.bigOperationTwoReminders(pcm.getDetail_calling_minute().doubleValue(), duration, "sub").intValue());
				// Replace old one to new one
				Collections.replaceAll(pcms, pcms.get(index), pcm);
				totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(duration, costPerMinute));
			} else {
				totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(pcm.getDetail_calling_minute().doubleValue(), costPerMinute));
				// Decrease Present Minutes
				pcm.setDetail_calling_minute(0);
				// Replace old one to new one
				Collections.replaceAll(pcms, pcms.get(index), pcm);
			}
		}
		return totalCreditBack;
	}
}

package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Next Topup Invoices With PDF
* 
* @author DON CHEN
*/ 
public class CustomerCreateTopupPlanInvoicePDFEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateTopupPlanInvoicePDFEachMonth(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerCreateTopupPlanInvoicePDFEachMonth(){
	}

	public void createTopupInvoicePDF() throws ParseException {
        
        this.crmService.createTopupPlanInvoice();
    } 
} 
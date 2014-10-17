package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Next Termed Invoices With PDF
* 
* @author DON CHEN
*/ 
public class CustomerCreateTermPlanInvoicePDFEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateTermPlanInvoicePDFEachMonth(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerCreateTermPlanInvoicePDFEachMonth(){
	}

	public void createTermInvoicePDF() throws ParseException {
        
        this.crmService.createTermPlanInvoice();
    } 
} 
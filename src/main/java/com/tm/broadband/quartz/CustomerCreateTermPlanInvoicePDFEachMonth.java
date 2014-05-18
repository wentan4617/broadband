package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Next Invoices With PDF When Given Date Match next_invoice_date
* 
* @author DON CHEN
*/ 
public class CustomerCreateTermPlanInvoicePDFEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateTermPlanInvoicePDFEachMonth(CRMService crmService){
		this.crmService = crmService;
	}

	public void createTermInvoicePDF() throws ParseException {
        
        this.crmService.createTermPlanInvoice();
    } 
} 
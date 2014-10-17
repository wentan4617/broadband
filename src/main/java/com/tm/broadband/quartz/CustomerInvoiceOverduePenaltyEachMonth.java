package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Matched Overdue Penalty invoices
* 
* @author DON CHEN
*/ 
public class CustomerInvoiceOverduePenaltyEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerInvoiceOverduePenaltyEachMonth(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerInvoiceOverduePenaltyEachMonth(){
	}

	public void createInvoiceOverduePenalty() throws ParseException {
        this.crmService.createInvoiceOverduePenalty();
    } 
} 
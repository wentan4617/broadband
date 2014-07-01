package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Matched Overdue Penalty invoices
* 
* @author DON CHEN
*/ 
public class CustomerCreateTermPlanInvoiceOverduePenaltyEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateTermPlanInvoiceOverduePenaltyEachMonth(CRMService crmService){
		this.crmService = crmService;
	}

	public void createTermPlanInvoiceOverduePenalty() throws ParseException {
        this.crmService.createTermPlanInvoiceOverduePenalty();
    } 
} 
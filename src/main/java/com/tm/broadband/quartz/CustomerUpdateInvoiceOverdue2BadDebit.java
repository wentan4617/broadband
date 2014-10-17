package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Update Matched Overdue 2 Bad Debit
* 
* @author DON CHEN
*/ 
public class CustomerUpdateInvoiceOverdue2BadDebit {
	
	private CRMService crmService;

	@Autowired
	public CustomerUpdateInvoiceOverdue2BadDebit(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerUpdateInvoiceOverdue2BadDebit(){
	}

	public void customerUpdateInvoiceOverdue2BadDebit() throws ParseException {
        this.crmService.customerUpdateInvoiceOverdue2BadDebit();
    } 
} 
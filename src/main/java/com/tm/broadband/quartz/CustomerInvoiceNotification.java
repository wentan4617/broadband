package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Notify Created Invoice and overdue invoice's customer to pay for invoice balance
* 
* @author DON CHEN
*/ 
public class CustomerInvoiceNotification {
	
	private CRMService crmService;

	@Autowired
	public CustomerInvoiceNotification(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerInvoiceNotification(){
	}

	public void checkInvoiceNotification() throws ParseException {
		crmService.checkInvoiceNotification();
    } 
} 
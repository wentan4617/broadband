package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Check All Due and After Due Most Recent unpaid Invoice's
* 
* @author DON CHEN
*/ 
public class CustomerInvoiceAfterDueNotice {
	
	private CRMService crmService;

	@Autowired
	public CustomerInvoiceAfterDueNotice(CRMService crmService){
		this.crmService = crmService;
	}

	public void checkInvoiceAfterDueNotice() throws ParseException {
		crmService.checkInvoiceAfterDueNotice();
    } 
} 
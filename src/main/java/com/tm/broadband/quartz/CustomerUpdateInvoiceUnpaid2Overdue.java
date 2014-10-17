package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/**
 * Automatically Update Matched Overdue 2 Bad Debit
 * 
 * @author DON CHEN
 */
public class CustomerUpdateInvoiceUnpaid2Overdue {

	private CRMService crmService;

	@Autowired
	public CustomerUpdateInvoiceUnpaid2Overdue(CRMService crmService) {
		this.crmService = crmService;
	}
	
	public CustomerUpdateInvoiceUnpaid2Overdue() {
	}

	public void customerUpdateInvoiceUnpaid2Overdue() throws ParseException {
		this.crmService.customerUpdateInvoiceUnpaid2Overdue();
	}
}
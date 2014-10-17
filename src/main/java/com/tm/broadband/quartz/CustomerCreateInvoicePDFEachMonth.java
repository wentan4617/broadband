package com.tm.broadband.quartz;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/**
 * Automatically Generating Next Invoices With PDF When Given Date Match
 * next_invoice_date
 * 
 * @author DON CHEN
 */
public class CustomerCreateInvoicePDFEachMonth {

	private CRMService crmService;

	@Autowired
	public CustomerCreateInvoicePDFEachMonth(CRMService crmService) {
		this.crmService = crmService;
	}

	public CustomerCreateInvoicePDFEachMonth() {
	}

	public void createNextInvoicePDF() throws ParseException {
		crmService.createNonDDPayPlanInvoice(new Date());
	}
}
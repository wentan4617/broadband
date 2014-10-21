package com.tm.broadband.quartz;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.service.CRMService;

/** 
* Automatically Generate Next Calling Invoices With PDF
* 
* @author DON CHEN
*/ 
public class CustomerCreateNextCallingInvoicePDF {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateNextCallingInvoicePDF(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerCreateNextCallingInvoicePDF(){
	}

	public void createNextCallingInvoicePDF() throws ParseException {
        this.crmService.createNextCallingInvoicePDF();
    } 
} 
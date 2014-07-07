package com.tm.broadband.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.service.CRMService;

/** 
* Automatically Generating Next Invoices With PDF When Given Date Match next_invoice_date
* 
* @author DON CHEN
*/ 
public class CustomerCreateInvoicePDFEachMonth {
	
	private CRMService crmService;

	@Autowired
	public CustomerCreateInvoicePDFEachMonth(CRMService crmService){
		this.crmService = crmService;
	}

	public void createNextInvoicePDF() throws ParseException {

        CustomerOrder customerOrder = new CustomerOrder();
        // only if the order is in using status
        customerOrder.getParams().put("where", "query_no_term");
        customerOrder.getParams().put("order_status", "using");
        
        // using new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-10") under testing environment
		// using new Date() under production environment
        customerOrder.getParams().put("next_invoice_create_date", new SimpleDateFormat("yyyy-MM-dd").parse("2014-11-25"));
        customerOrder.getParams().put("order_type", "order-no-term");
        customerOrder.getParams().put("is_ddpay", false);
        customerOrder.getParams().put("order_term_type", "order-term");
        
        // call Service Method
		crmService.createNextInvoice(customerOrder);
    } 
} 
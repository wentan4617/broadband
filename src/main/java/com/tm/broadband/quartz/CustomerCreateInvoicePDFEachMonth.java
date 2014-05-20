package com.tm.broadband.quartz;

import java.text.ParseException;
import java.util.Date;

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
        customerOrder.getParams().put("order_status", "using");
        
        // using new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-13") under testing environment
		// using new Date() under production environment
        customerOrder.getParams().put("next_invoice_create_date", new Date());
        customerOrder.getParams().put("order_type_no_term", "order-no-term"); 
        customerOrder.getParams().put("where", "query_no_term"); 
        
        // call Service Method
		crmService.createNextInvoice(customerOrder);
            
    } 
} 
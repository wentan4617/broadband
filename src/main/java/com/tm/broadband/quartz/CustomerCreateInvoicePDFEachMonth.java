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
	
		@Autowired
		private CRMService crmService;

		public void createNextInvoicePDF() throws ParseException {
                
                CustomerOrder customerOrder = new CustomerOrder();
                // only compare with the order which is in using status
                customerOrder.getParams().put("order_status", "using");
                
                // this is to compare the next_invoice_create_date, if matched then
                // generate that invoice into PDF form
                customerOrder.getParams().put("next_invoice_create_date", 
                		new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-30"));
                
                try {
                    // call Service Method
                	crmService.createNextInvoice(customerOrder);
				} catch (ParseException e) {
					e.printStackTrace();
				}
                
        } 
} 
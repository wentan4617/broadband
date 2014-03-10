package com.tm.broadband.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.email.ApplicationEmail;
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
			// setting properties and sending mail to customer email address
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee("394802198@qq.com"); // customer.getEmail()
			applicationEmail.setFrom("kanny87929@gmail.com");
			applicationEmail.setReplyTo("kanny87929@gmail.com");
			applicationEmail.setSubject("Invoice from Total Mobile Solution Limited");
			applicationEmail.setContent("Thank You! For using our service.");
                
            CustomerOrder customerOrder = new CustomerOrder();
            // only if the order is in using status
            customerOrder.getParams().put("order_status", "using");
            
            // production mode use new Date() instead SimpleDateFormat().parse()
            // this is to compare the next_invoice_create_date, if matched then
            // generate that invoice into PDF form
            customerOrder.getParams().put("next_invoice_create_date", 
            		new SimpleDateFormat("yyyy-MM-dd").parse("2014-07-29"));
            
            try {
                // call Service Method
            	crmService.createNextInvoice(customerOrder, applicationEmail);
			} catch (ParseException e) {
				e.printStackTrace();
			}
                
        } 
} 
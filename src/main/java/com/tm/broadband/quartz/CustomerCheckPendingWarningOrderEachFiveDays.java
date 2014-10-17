package com.tm.broadband.quartz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.service.CRMService;

/** 
* Automatically Check All Over Five Days Pending Warning Orders
* 
* @author DON CHEN
*/ 
public class CustomerCheckPendingWarningOrderEachFiveDays {
	
	private CRMService crmService;

	@Autowired
	public CustomerCheckPendingWarningOrderEachFiveDays(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerCheckPendingWarningOrderEachFiveDays(){
	}

	public void checkPendingWarningOrderPDF() throws ParseException {

        CustomerOrder customerOrder = new CustomerOrder();
        // only if the order is in pending status
        customerOrder.getParams().put("where", "query_pending_notice");
        customerOrder.getParams().put("order_status", "pending-warning");
        customerOrder.getParams().put("customer_type", "personal");
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -7);
        
        // using new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-10") under testing environment
		// using new Date() under production environment
        customerOrder.getParams().put("order_create_date", cal.getTime());
        
        // call Service Method
		crmService.checkPendingWarningOrder(customerOrder);
    } 
} 
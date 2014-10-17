package com.tm.broadband.quartz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.service.CRMService;

/** 
* Automatically Check All Over Three Days Pending Orders
* 
* @author DON CHEN
*/ 
public class CustomerCheckPendingOrderEachThreeDays {
	
	private CRMService crmService;

	@Autowired
	public CustomerCheckPendingOrderEachThreeDays(CRMService crmService){
		this.crmService = crmService;
	}
	
	public CustomerCheckPendingOrderEachThreeDays(){
	}

	public void checkPendingOrderPDF() throws ParseException {

        CustomerOrder customerOrder = new CustomerOrder();
        // only if the order is in pending status
        customerOrder.getParams().put("where", "query_pending_notice");
        customerOrder.getParams().put("order_status", "pending");
        customerOrder.getParams().put("customer_type", "personal");
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -3);
        
        // using new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-10") under testing environment
		// using new Date() under production environment
        customerOrder.getParams().put("order_create_date", cal.getTime());
        
        // call Service Method
		crmService.checkPendingOrder(customerOrder);
    } 
} 
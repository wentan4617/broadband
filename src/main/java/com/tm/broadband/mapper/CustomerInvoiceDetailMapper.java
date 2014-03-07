package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerInvoiceDetail;

/**
 * mapping tm_customer_invoice_detail
 * 
 * @author StevenChen
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerInvoiceDetailMapper {
	
	/* SELECT AREA */
	
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertCustomerInvoiceDetail(CustomerInvoiceDetail customerInvoiceDetail);
	
	List<CustomerInvoiceDetail> selectCustomerInvoiceDetailsByCustomerInvoiceId(int id);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */

}

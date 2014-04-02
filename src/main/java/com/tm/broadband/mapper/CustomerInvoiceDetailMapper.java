package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.Page;

/**
 * mapping tm_customer_invoice
 * 
 * @author Cook1fan
=======
import com.tm.broadband.model.CustomerInvoiceDetail;

/**
 * mapping tm_customer_invoice_detail
 * 
 * @author StevenChen
>>>>>>> 518d78062f301ea9e06aaa30e0947a73a20bdb91
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
	List<CustomerInvoiceDetail> selectCustomerInvoiceDetailsByCustomerInvoiceId(int id);

	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertCustomerInvoiceDetail(CustomerInvoiceDetail customerInvoiceDetail);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	void deleteCustomerInvoiceDetailByInvoiceId(int id);
	/* // END DELETE AREA */

}

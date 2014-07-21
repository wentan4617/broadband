package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.Page;

/**
 * mapping tm_customer_invoice
 * 
 * @author Cook1fan
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerInvoiceMapper {
	
	/* SELECT AREA */
	
	List<CustomerInvoice> selectCustomerInvoicesByPage(Page<CustomerInvoice> page);
	int selectCustomerInvoicesSum(Page<CustomerInvoice> page);
	
	CustomerInvoice selectCustomerInvoiceById(int id);
	CustomerInvoice selectCustomerLastInvoiceById(int id);
	String selectCustomerInvoiceFilePathById(int id);
	
	CustomerInvoice selectCustomerInvoice(CustomerInvoice customerInvoice);
	List<CustomerInvoice> selectCustomerInvoices(CustomerInvoice customerInvoice);
	
	CustomerInvoice selectInvoiceWithLastInvoiceIdById(int id);
	Double selectCustomerInvoicesBalanceByCidAndStatus(int cid, String status);
	
	List<CustomerInvoice> selectCustomerInvoicesByCreateDate(CustomerInvoice ci);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertCustomerInvoice(CustomerInvoice customerInvoice);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateCustomerInvoice(CustomerInvoice customerInvoice);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	void deleteCustomerInvoiceById(int id);
	
	void deleteCustomerInvoiceByCustomerId(int id);
	
	void deleteCustomerInvoiceByRepeat();
	/* // END DELETE AREA */
	
}

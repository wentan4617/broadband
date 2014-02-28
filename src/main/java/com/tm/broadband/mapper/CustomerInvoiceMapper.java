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
public interface CustomerInvoiceMapper {

	void insertCustomerInvoice(CustomerInvoice customerInvoice);
	
	List<CustomerInvoice> selectCustomerInvoicesByPage(Page<CustomerInvoice> page);
	
	int selectCustomerInvoicesSum(Page<CustomerInvoice> page);
	
	List<CustomerInvoice> selectCustomerInvoicesPageById(Page<CustomerInvoice> page);
	
	int selectCustomerInvoicesSumById(Page<CustomerInvoice> page);

	void updateCustomerInvoice(CustomerInvoice customerInvoice);
	
}

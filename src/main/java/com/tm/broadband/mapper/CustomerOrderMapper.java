package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Page;

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerOrderMapper {

	/* SELECT AREA */
	
	String selectCustomerOrderTypeById(int id);
	
	CustomerOrder selectCustomerOrder(CustomerOrder customerOrder);
	List<CustomerOrder> selectCustomerOrders(CustomerOrder customerOrder);
	
	List<CustomerOrder> selectCustomerOrdersByPage(Page<CustomerOrder> page);
	int selectCustomerOrdersSum(Page<CustomerOrder> page);
	
	List<CustomerOrder> selectCustomerOrdersByCustomerId(int customer_id); 
	CustomerOrder selectCustomerOrderById(int id);
	List<CustomerOrder> selectCustomerOrdersBySome(CustomerOrder customerOrder);
	
	String selectCustomerOrderFilePathById(int id);
	String selectCustomerCreditFilePathById(int id);
	String selectCustomerDDPayFormPathById(int id);
	String selectCustomerPreviousProviderInvoiceFilePathById(int id);
	String selectCustomerOrderingFormPathById(int id);
	String selectCustomerReceiptPathById(int id);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */

	void insertCustomerOrder(CustomerOrder customerOrder);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateCustomerOrder(CustomerOrder customerOrder);
	
	void updateCustomerOrderServiceGivingNextInvoiceEmpty(CustomerOrder customerOrder);
	
	void updateCustomerOrderSVCVLanRFSDateEmpty(CustomerOrder customerOrder);
	
	void updateCustomerOrderBroadbandASIDEmpty(CustomerOrder customerOrder);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	
	void deleteCustomerOrderByCustomerId(int id);
	
	/* // END DELETE AREA */
}

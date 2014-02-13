package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

/**
 * mapping tm_customer_order
 * 
 * @author Cook1fan
 * 
 */
public interface CustomerOrderMapper {

	void createCustomerOrder(CustomerOrder customerOrder);
	
	//void updateCustomerOrderStatus(String status);
	
	void updateCustomerOrderStatusByCustomerId(int customer_id, String status);
	
	void updateCustomerOrderStatusById(int id, String status);
	
	int selectCustomerOrderIdByCId(int customer_id);
}

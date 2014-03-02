package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrder;

/**
 * mapping tm_customer_order
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

public interface CustomerOrderMapper {

	/* SELECT AREA */
	
	List<CustomerOrder> selectCustomerOrdersByCustomerId(int customer_id); 
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */

	void insertCustomerOrder(CustomerOrder customerOrder);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateCustomerOrder(CustomerOrder customerOrder);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */
}

package com.tm.broadband.mapper;

import com.tm.broadband.model.CustomerOrderDetail;

/**
 * mapping tm_customer_order_detail
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

public interface CustomerOrderDetailMapper {
	
	/* SELECT AREA *//* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCustomerOrderDetail (CustomerOrderDetail customerOrderDetail);
	

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerOrderDetail(CustomerOrderDetail customerOrderDetail);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */

	
}
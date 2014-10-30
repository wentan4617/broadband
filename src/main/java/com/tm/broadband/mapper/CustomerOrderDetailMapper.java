package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrderDetail;

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerOrderDetailMapper {
	
	/* SELECT AREA */
	
	public List<CustomerOrderDetail> selectCustomerOrderDetails(CustomerOrderDetail cod);
	public List<CustomerOrderDetail> selectCustomerOrderDetailsByOrderId(int order_id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCustomerOrderDetail (CustomerOrderDetail customerOrderDetail);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerOrderDetail(CustomerOrderDetail customerOrderDetail);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	void deleteCustomerOrderDetailById(int id);
	void deleteCustomerOrderDetailByOrderId(int id);
	/* // END DELETE AREA */

	
}
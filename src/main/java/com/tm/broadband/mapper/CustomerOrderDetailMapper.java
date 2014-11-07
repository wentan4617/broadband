package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;

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
	List<CustomerOrderDetail> selectCustomerOrderDetailsByPage(Page<CustomerOrderDetail> page);
	int selectCustomerOrderDetailsSum(Page<CustomerOrderDetail> page);
	
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
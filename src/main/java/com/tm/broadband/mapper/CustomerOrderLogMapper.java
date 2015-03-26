package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderLog;
import com.tm.broadband.model.Page;

public interface CustomerOrderLogMapper {

/**
 * mapping tm_customer_order_log, customerOrderLog DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderLog> selectCustomerOrderLog(CustomerOrderLog col);
	List<CustomerOrderLog> selectCustomerOrderLogsByPage(Page<CustomerOrderLog> page);
	int selectCustomerOrderLogsSum(Page<CustomerOrderLog> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderLog(CustomerOrderLog col);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderLog(CustomerOrderLog col);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderLogById(int id);

	/* // END DELETE AREA */

}

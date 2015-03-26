package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerBillingLog;
import com.tm.broadband.model.Page;

public interface CustomerBillingLogMapper {

/**
 * mapping tm_customer_billing_log, customerBillingLog DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerBillingLog> selectCustomerBillingLog(CustomerBillingLog cbl);
	List<CustomerBillingLog> selectCustomerBillingLogsByPage(Page<CustomerBillingLog> page);
	int selectCustomerBillingLogsSum(Page<CustomerBillingLog> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerBillingLog(CustomerBillingLog cbl);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerBillingLog(CustomerBillingLog cbl);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerBillingLogById(int id);

	/* // END DELETE AREA */

}

package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.Page;

public interface CustomerCallingRecordCallplusMapper {

/**
 * mapping tm_customer_calling_record, customerCallingRecord DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<CustomerCallingRecordCallplus> selectCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc);
	List<CustomerCallingRecordCallplus> selectCustomerCallingRecordCallplussByPage(Page<CustomerCallingRecordCallplus> page);
	int selectCustomerCallingRecordCallplussSum(Page<CustomerCallingRecordCallplus> page);
	CustomerCallingRecordCallplus selectCustomerCallingRecordCallplusById(int id);
	List<CustomerCallingRecordCallplus> selectCustomerCallingRecordCallplussByDate(CustomerCallingRecordCallplus ccrc);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc);
	
	/* // END DELETE AREA */

}

package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerServiceRecord;
import com.tm.broadband.model.Page;

public interface CustomerServiceRecordMapper {

/**
 * mapping tm_customer_service_record, customerServiceRecord DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<CustomerServiceRecord> selectCustomerServiceRecord(CustomerServiceRecord csr);
	List<CustomerServiceRecord> selectCustomerServiceRecordsByPage(Page<CustomerServiceRecord> page);
	int selectCustomerServiceRecordsSum(Page<CustomerServiceRecord> page);
	CustomerServiceRecord selectCustomerServiceRecordById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCustomerServiceRecord(CustomerServiceRecord csr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerServiceRecord(CustomerServiceRecord csr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerServiceRecord(CustomerServiceRecord csr);
	
	/* // END DELETE AREA */

}

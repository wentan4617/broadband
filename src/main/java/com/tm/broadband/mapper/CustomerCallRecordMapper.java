package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.Page;

public interface CustomerCallRecordMapper {

/**
 * mapping tm_customer_call_record, customerCallRecord DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<CustomerCallRecord> selectCustomerCallRecord(CustomerCallRecord ccr);
	List<CustomerCallRecord> selectCustomerCallRecordsByPage(Page<CustomerCallRecord> page);
	int selectCustomerCallRecordsSum(Page<CustomerCallRecord> page);
	CustomerCallRecord selectCustomerCallRecordById(int id);
	List<CustomerCallRecord> selectCustomerCallRecordsByStatementDate(CustomerCallRecord ccr);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCustomerCallRecord(CustomerCallRecord ccr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerCallRecord(CustomerCallRecord ccr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerCallRecord(CustomerCallRecord ccr);
	
	/* // END DELETE AREA */

}

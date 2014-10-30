package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderDetailDeleteRecord;
import com.tm.broadband.model.Page;

public interface CustomerOrderDetailDeleteRecordMapper {

/**
 * mapping tm_customer_order_detail_delete_record, customerOrderDetailDeleteRecord DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderDetailDeleteRecord> selectCustomerOrderDetailDeleteRecord(CustomerOrderDetailDeleteRecord coddr);
	List<CustomerOrderDetailDeleteRecord> selectCustomerOrderDetailDeleteRecordsByPage(Page<CustomerOrderDetailDeleteRecord> page);
	int selectCustomerOrderDetailDeleteRecordsSum(Page<CustomerOrderDetailDeleteRecord> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderDetailDeleteRecord(CustomerOrderDetailDeleteRecord coddr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderDetailDeleteRecord(CustomerOrderDetailDeleteRecord coddr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderDetailDeleteRecordByDetailId(int id);

	/* // END DELETE AREA */

}

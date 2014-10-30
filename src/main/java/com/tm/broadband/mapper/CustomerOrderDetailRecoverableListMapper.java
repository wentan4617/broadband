package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderDetailRecoverableList;
import com.tm.broadband.model.Page;

public interface CustomerOrderDetailRecoverableListMapper {

/**
 * mapping tm_customer_order_detail_recoverable_list, customerOrderDetailRecoverableList DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderDetailRecoverableList> selectCustomerOrderDetailRecoverableList(CustomerOrderDetailRecoverableList codrl);
	List<CustomerOrderDetailRecoverableList> selectCustomerOrderDetailRecoverableListsByPage(Page<CustomerOrderDetailRecoverableList> page);
	int selectCustomerOrderDetailRecoverableListsSum(Page<CustomerOrderDetailRecoverableList> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderDetailRecoverableList(CustomerOrderDetailRecoverableList codrl);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderDetailRecoverableList(CustomerOrderDetailRecoverableList codrl);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderDetailRecoverableListByDetailId(int id);

	/* // END DELETE AREA */

}

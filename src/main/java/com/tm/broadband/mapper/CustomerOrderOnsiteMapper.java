package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderOnsite;
import com.tm.broadband.model.Page;

public interface CustomerOrderOnsiteMapper {

/**
 * mapping tm_customer_order_onsite, customerOrderOnsite DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderOnsite> selectCustomerOrderOnsite(CustomerOrderOnsite coo);
	List<CustomerOrderOnsite> selectCustomerOrderOnsitesByPage(Page<CustomerOrderOnsite> page);
	int selectCustomerOrderOnsitesSum(Page<CustomerOrderOnsite> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderOnsite(CustomerOrderOnsite coo);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderOnsite(CustomerOrderOnsite coo);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderOnsiteById(int id);

	/* // END DELETE AREA */

}

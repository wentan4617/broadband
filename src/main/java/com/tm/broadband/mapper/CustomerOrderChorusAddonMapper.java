package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderChorusAddon;
import com.tm.broadband.model.Page;

public interface CustomerOrderChorusAddonMapper {

/**
 * mapping tm_customer_order_chorus_addon, customerOrderChorusAddon DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderChorusAddon> selectCustomerOrderChorusAddon(CustomerOrderChorusAddon coca);
	List<CustomerOrderChorusAddon> selectCustomerOrderChorusAddonsByPage(Page<CustomerOrderChorusAddon> page);
	int selectCustomerOrderChorusAddonsSum(Page<CustomerOrderChorusAddon> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderChorusAddon(CustomerOrderChorusAddon coca);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderChorusAddon(CustomerOrderChorusAddon coca);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderChorusAddonById(int id);
	void deleteCustomerOrderChorusAddonByOrderId(int order_id);

	/* // END DELETE AREA */

}

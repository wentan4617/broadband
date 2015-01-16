package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderBroadbandASID;
import com.tm.broadband.model.Page;

public interface CustomerOrderBroadbandASIDMapper {

/**
 * mapping tm_customer_order_broadband_asid, customerOrderBroadbandASID DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderBroadbandASID> selectCustomerOrderBroadbandASID(CustomerOrderBroadbandASID cobasid);
	List<CustomerOrderBroadbandASID> selectCustomerOrderBroadbandASIDsByPage(Page<CustomerOrderBroadbandASID> page);
	int selectCustomerOrderBroadbandASIDsSum(Page<CustomerOrderBroadbandASID> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderBroadbandASID(CustomerOrderBroadbandASID cobasid);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderBroadbandASID(CustomerOrderBroadbandASID cobasid);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderBroadbandASIDById(int id);

	/* // END DELETE AREA */

}

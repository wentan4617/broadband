package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderProvisionChecklist;
import com.tm.broadband.model.Page;

public interface CustomerOrderProvisionChecklistMapper {

/**
 * mapping tm_customer_order_provision_checklist, customerOrderProvisionChecklist DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderProvisionChecklist> selectCustomerOrderProvisionChecklist(CustomerOrderProvisionChecklist copc);
	List<CustomerOrderProvisionChecklist> selectCustomerOrderProvisionChecklistsByPage(Page<CustomerOrderProvisionChecklist> page);
	int selectCustomerOrderProvisionChecklistsSum(Page<CustomerOrderProvisionChecklist> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderProvisionChecklist(CustomerOrderProvisionChecklist copc);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderProvisionChecklist(CustomerOrderProvisionChecklist copc);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderProvisionChecklistById(int id);

	/* // END DELETE AREA */

}

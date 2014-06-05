package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface UsageMapper {
	
	/* SELECT AREA */
	
	CustomerOrder selectDataCustomer(CustomerOrder co);
	List<NetworkUsage> selectUsages(NetworkUsage usage);
	List<NetworkUsage> selectCurrentMonthUsages(NetworkUsage usage);
	List<CustomerOrder> selectDataCustomersByPage(Page<CustomerOrder> page);
	int selectDataCustomersSum(Page<CustomerOrder> page);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertUsage(NetworkUsage usage);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	void deleteUsage(NetworkUsage usage);
	
	/* // END DELETE AREA */
}

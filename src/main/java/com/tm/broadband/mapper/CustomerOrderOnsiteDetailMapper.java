package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderOnsiteDetail;
import com.tm.broadband.model.Page;

public interface CustomerOrderOnsiteDetailMapper {

/**
 * mapping tm_customer_order_onsite_detail, customerOrderOnsiteDetail DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderOnsiteDetail> selectCustomerOrderOnsiteDetail(CustomerOrderOnsiteDetail cood);
	List<CustomerOrderOnsiteDetail> selectCustomerOrderOnsiteDetailsByPage(Page<CustomerOrderOnsiteDetail> page);
	int selectCustomerOrderOnsiteDetailsSum(Page<CustomerOrderOnsiteDetail> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderOnsiteDetail(CustomerOrderOnsiteDetail cood);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderOnsiteDetail(CustomerOrderOnsiteDetail cood);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderOnsiteDetailById(int id);

	/* // END DELETE AREA */

}

package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerDDPay;
import com.tm.broadband.model.Page;

public interface CustomerDDPayMapper {

/**
 * mapping tm_customer_ddpay, customerDDPay DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerDDPay> selectCustomerDDPay(CustomerDDPay cddp);
	List<CustomerDDPay> selectCustomerDDPaysByPage(Page<CustomerDDPay> page);
	int selectCustomerDDPaysSum(Page<CustomerDDPay> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerDDPay(CustomerDDPay cddp);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerDDPay(CustomerDDPay cddp);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerDDPayById(int id);

	/* // END DELETE AREA */

}

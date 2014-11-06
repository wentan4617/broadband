package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.Page;

/**
 * mapping tm_customer_invoice
 * 
 * @author DON CHEN
 *
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerCreditMapper {
	
	/* SELECT AREA */
	
	List<CustomerCredit> selectCustomerCredits(CustomerCredit customerCredit);
	List<CustomerCredit> selectCustomerCreditsByPage(Page<CustomerCredit> page);
	int selectCustomerCreditsSum(Page<CustomerCredit> page);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	void insertCustomerCredit(CustomerCredit customerCredit);
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	void updateCustomerCredit(CustomerCredit customerCredit);
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	void deleteCustomerCreditCardById(int id);
	/* // END DELETE AREA */

}

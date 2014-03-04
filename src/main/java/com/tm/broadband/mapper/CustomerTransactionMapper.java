package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Page;

/**
 * mapping tm_customer_transaction
 * 
 * @author Cook1fan
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerTransactionMapper {
	
	
	/* SELECT AREA */
	
	List<CustomerTransaction> selectCustomerTransactionsByPage(Page<CustomerTransaction> page);
	int selectCustomerTransactionsSum(Page<CustomerTransaction> page);
	
	CustomerTransaction selectCustomerTransactionByInvoiceId(int id);
	
	CustomerTransaction selectCustomerTransaction(CustomerTransaction customerTransaction);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertCustomerTransaction(CustomerTransaction customerTransaction);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCustomerTransaction(CustomerTransaction customerTransaction);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */
	
}

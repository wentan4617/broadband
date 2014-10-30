package com.tm.broadband.mapper;

import java.util.Date;
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
	
	List<CustomerTransaction> selectCustomerTransactions(CustomerTransaction customerTransactions);
	
	List<CustomerTransaction> selectCustomerTransactionsByPage(Page<CustomerTransaction> page);
	int selectCustomerTransactionsSum(Page<CustomerTransaction> page);
	
	List<CustomerTransaction> selectCustomerTransactionsByCustomerId(int id);
	
	CustomerTransaction selectCustomerTransaction(CustomerTransaction customerTransaction);
	
	List<CustomerTransaction> selectCustomerTransactionsByTransactionDate(Date start, Date end);
	
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
	/* DELETE AREA */
	void deleteCustomerTransactionByCustomerId(int id);
	void deleteCustomerTransactionById(int id);
	/* // END DELETE AREA */
	
}

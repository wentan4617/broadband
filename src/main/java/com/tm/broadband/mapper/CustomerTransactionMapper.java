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
public interface CustomerTransactionMapper {

	void insertCustomerTransaction(CustomerTransaction customerTransaction);
	
	List<CustomerTransaction> selectCustomerTransactionsByPage(Page<CustomerTransaction> page);
	
	int selectCustomerTransactionsSum(Page<CustomerTransaction> page);
	
	List<CustomerTransaction> selectCustomerTxsPageByCustomerId(Page<CustomerTransaction> page);
	
	int selectCustomerTxsSumByCustomerId(Page<CustomerTransaction> page);
	
	void updateCustomerTransaction(CustomerTransaction customerTransaction);

	
}

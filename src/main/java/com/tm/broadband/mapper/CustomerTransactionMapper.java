package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;

/**
 * mapping tm_customer_transaction
 * 
 * @author Cook1fan
 * 
 */
public interface CustomerTransactionMapper {

	void insertCustomerTransaction(CustomerTransaction customerTransaction);

	
}

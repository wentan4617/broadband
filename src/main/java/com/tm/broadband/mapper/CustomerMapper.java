package com.tm.broadband.mapper;

import java.util.Date;
import java.util.List;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.Page;

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CustomerMapper {
	
	/* SELECT AREA */
	
	List<Customer> selectCustomers(Customer customer);
	List<Customer> selectCustomersByPage(Page<Customer> page);
	int selectCustomersSum(Page<Customer> page);
	
	int selectExistCustomer(Customer customer);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertCustomer(Customer customer);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateCustomer(Customer customer);
	
	void updateCustomerStatus(Customer customer);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	void deleteCustomerById(int id);
	/* // END DELETE AREA */
	
}

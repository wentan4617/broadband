package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;

/**
 * mapping tm_customer, userDAO component
 * @author Cook1fan
 *
 */
public interface CustomerMapper {
	
	void insertCustomer(Customer customer);
	
	int selectExistCustomerByLoginName(String login_name);
	
	List<Customer> selectCustomersByStatus(String status);
	
	Customer selectCustomerByIdWithCustomerOrder(int id);
	
	List<Customer> selectCustomersByPage(Page<Customer> page);
	
	int selectCustomersSum(Page<Customer> page);
	
	void updateCustomerStatus(Customer customer);
	
	Customer selectCustomerWhenLogin(Customer customer);
}

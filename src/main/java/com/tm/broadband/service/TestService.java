package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;

@Service
public class TestService {

	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	
	public TestService(){}
	@Autowired
	public TestService(CustomerOrderMapper customerOrderMapper
			, CustomerMapper customerMapper){
		this.customerOrderMapper = customerOrderMapper;
		this.customerMapper = customerMapper;
	}
	
	@Transactional
	public void copyContactDetailsFromCustomer2Order(){
		
		List<Customer> cs = this.customerMapper.selectCustomers(new Customer());
		
		for (Customer c : cs) {
			
			CustomerOrder coQuery = new CustomerOrder();
			coQuery.getParams().put("customer_id", c.getId());
			
			List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrders(coQuery);
			for (CustomerOrder co : cos) {
				
				CustomerOrder coUpdate = new CustomerOrder();
				coUpdate.setTitle(c.getTitle());
				coUpdate.setFirst_name(c.getFirst_name());
				coUpdate.setLast_name(c.getLast_name());
				coUpdate.setAddress(c.getAddress());
				coUpdate.setMobile(c.getCellphone());
				coUpdate.setPhone(c.getPhone());
				coUpdate.setEmail(c.getEmail());
				coUpdate.setCustomer_type(c.getCustomer_type());
				coUpdate.getParams().put("id", co.getId());
				
				this.customerOrderMapper.updateCustomerOrder(coUpdate);
				
			}
			
		}
		
		
		
	}
	
}

package com.tm.broadband.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;

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
	
}

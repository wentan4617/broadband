package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;

@Service
public class ProvisionService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private ProvisionLogMapper provisionLogMapper;
	
	@Autowired
	public ProvisionService(CustomerMapper customerMapper, 
			CustomerOrderMapper customerOrderMapper,
			ProvisionLogMapper provisionLogMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.provisionLogMapper = provisionLogMapper;
	}

	public ProvisionService() {}
	
}

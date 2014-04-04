package com.tm.broadband.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerCreditMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.OrganizationMapper;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.Organization;

@Service
public class SaleService {
	
	private CustomerOrderMapper customerOrderMapper;
	private CustomerCreditMapper customerCreditMapper;
	private OrganizationMapper organizationMapper;
	
	@Autowired
	public SaleService(CustomerOrderMapper customerOrderMapper
			, CustomerCreditMapper customerCreditMapper
			, OrganizationMapper organizationMapper) {
		this.customerOrderMapper = customerOrderMapper;
		this.customerCreditMapper = customerCreditMapper;
		this.organizationMapper = organizationMapper;
	}

	public SaleService() {}
	
	public String queryCustomerOrderFilePathById(int id){
		return this.customerOrderMapper.selectCustomerOrderFilePathById(id);
	}
	
	public String queryCustomerCreditFilePathById(int id){
		return this.customerOrderMapper.selectCustomerCreditFilePathById(id);
	}
	
	@Transactional
	public void createCustomerCredit(CustomerCredit customerCredit){
		this.customerCreditMapper.insertCustomerCredit(customerCredit);
	}
	
	public Organization queryOrganizationByCustomerId(int id){
		return this.organizationMapper.selectOrganizationByCustomerId(id);
		
	}

}

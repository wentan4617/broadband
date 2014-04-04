package com.tm.broadband.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.broadband.mapper.CustomerOrderMapper;

@Service
public class SaleService {
	
	private CustomerOrderMapper coMapper;
	
	@Autowired
	public SaleService(CustomerOrderMapper coMapper) {
		this.coMapper = coMapper;
	}
	
	public String queryCustomerOrderFilePathById(int id){
		return this.coMapper.selectCustomerOrderFilePathById(id);
	}
	
	public String queryCustomerCreditFilePathById(int id){
		return this.coMapper.selectCustomerCreditFilePathById(id);
	}

	public SaleService() {}

}

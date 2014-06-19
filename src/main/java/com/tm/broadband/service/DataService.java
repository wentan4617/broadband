package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.RadacctMapper;
import com.tm.broadband.mapper.UsageMapper;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

@Service
public class DataService {
	
	private UsageMapper usageMapper;
	private RadacctMapper radacctMapper;
	
	@Autowired
	public DataService(UsageMapper usageMapper, RadacctMapper radacctMapper) {
		this.usageMapper = usageMapper;
		this.radacctMapper = radacctMapper;
	}
	
	public DataService(){
		
	}
	
	@Transactional
	public void calculatorUsage(NetworkUsage usage) {
		this.usageMapper.deleteUsage(usage);
		this.usageMapper.insertUsage(usage);
	}
	
	@Transactional
	public Page<CustomerOrder> queryDataCustomersByPage(Page<CustomerOrder> page) {
		page.setTotalRecord(this.usageMapper.selectDataCustomersSum(page));
		page.setResults(this.usageMapper.selectDataCustomersByPage(page));
		return page;
	} 
	
	@Transactional
	public List<NetworkUsage> queryUsages(NetworkUsage usage) {
		return this.usageMapper.selectUsages(usage);
	}
	
	@Transactional
	public List<NetworkUsage> queryCurrentMonthUsages(NetworkUsage usage) {
		return this.usageMapper.selectCurrentMonthUsages(usage);
	}
	
	@Transactional
	public CustomerOrder queryDataCustomer(CustomerOrder co) {
		return this.usageMapper.selectDataCustomer(co);
	}
	
	@Transactional
	public void removeRadacct() {
		this.radacctMapper.deleteRadacct();
	}

}

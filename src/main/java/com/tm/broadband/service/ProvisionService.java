package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;

@Service
public class ProvisionService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private ProvisionLogMapper provisionLogMapper;
	
	@Autowired
	public ProvisionService(CustomerMapper customerMapper, 
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			ProvisionLogMapper provisionLogMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
	}

	public ProvisionService() {}
	
	@Transactional
	public Page<CustomerOrder> queryCustomerOrdersByPage(Page<CustomerOrder> page) {
		page.setTotalRecord(this.customerOrderMapper.selectCustomerOrdersSum(page));
		page.setResults(this.customerOrderMapper.selectCustomerOrdersByPage(page));
		return page;
	}
	
	@Transactional
	public int queryCustomerOrdersSumByPage(Page<CustomerOrder> page) {
		return this.customerOrderMapper.selectCustomerOrdersSum(page);
	}
	
	@Transactional
	public CustomerOrder queryCustomerOrderById(int id) {
		return this.customerOrderMapper.selectCustomerOrderById(id);
	}
	

	@Transactional
	public void changeCustomerOrderStatus(List<CustomerOrder> list) {
		
		for (CustomerOrder customerOrder : list) {
			this.customerOrderMapper.updateCustomerOrder(customerOrder);
			this.provisionLogMapper.insertProvisionLog(customerOrder.getTempProvsionLog());
		}
	}
	
	@Transactional
	public void setHardwarePost(CustomerOrder co, CustomerOrderDetail cod) {
		this.customerOrderMapper.updateCustomerOrder(co);
		this.customerOrderDetailMapper.updateCustomerOrderDetail(cod);
	}
	
	
}

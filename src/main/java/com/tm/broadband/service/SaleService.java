
package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerCreditMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.OrganizationMapper;
import com.tm.broadband.mapper.UserMapper;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;

@Service
public class SaleService {
	
	private CustomerOrderMapper customerOrderMapper;
	private CustomerCreditMapper customerCreditMapper;
	private OrganizationMapper organizationMapper;
	private UserMapper userMapper;
	
	@Autowired
	public SaleService(CustomerOrderMapper customerOrderMapper
			, CustomerCreditMapper customerCreditMapper
			, OrganizationMapper organizationMapper
			, UserMapper userMapper) {
		this.customerOrderMapper = customerOrderMapper;
		this.customerCreditMapper = customerCreditMapper;
		this.organizationMapper = organizationMapper;
		this.userMapper = userMapper;
	}

	public SaleService() {}
	
	public String queryCustomerOrderFilePathById(int id){
		return this.customerOrderMapper.selectCustomerOrderFilePathById(id);
	}
	
	public String queryCustomerCreditFilePathById(int id){
		return this.customerOrderMapper.selectCustomerCreditFilePathById(id);
	}

	@Transactional
	public Page<CustomerOrder> queryOrdersByPage(Page<CustomerOrder> page) {
		page.setTotalRecord(this.customerOrderMapper.selectCustomerOrdersSum(page));
		page.setResults(this.customerOrderMapper.selectCustomerOrdersByPage(page));
	
		return page;
	}

	@Transactional
	public Page<CustomerOrder> queryOrdersSumByPage(Page<CustomerOrder> page) {
		page.setTotalRecord(this.customerOrderMapper.selectCustomerOrdersSum(page));
		return page;
	}
	
	@Transactional
	public void createCustomerCredit(CustomerCredit customerCredit){
		this.customerCreditMapper.insertCustomerCredit(customerCredit);
	}
	
	public Organization queryOrganizationByCustomerId(int id){
		return this.organizationMapper.selectOrganizationByCustomerId(id);
	}
	
	public List<User> queryUsersWhoseIdExistInOrder(){
		return this.userMapper.selectUsersWhoseIdExistInOrder();
	}

}

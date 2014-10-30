package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.OrganizationMapper;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;

@Service
public class TestService {

	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private OrganizationMapper organizationMapper;
	
	public TestService(){}
	@Autowired
	public TestService(CustomerOrderMapper customerOrderMapper
			, CustomerMapper customerMapper
			, OrganizationMapper organizationMapper){
		this.customerOrderMapper = customerOrderMapper;
		this.customerMapper = customerMapper;
		this.organizationMapper = organizationMapper;
	}
	
	@Transactional
	public void moveCustomerToCustomerOrder() {
		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(1);
		page.setPageSize(2000);
		List<CustomerOrder> orders = this.customerOrderMapper.selectCustomerOrdersByPage(page);
		for (CustomerOrder co : orders) {
			CustomerOrder coUpdate = new CustomerOrder();
			coUpdate.setCustomer_type(co.getCustomer().getCustomer_type());
			coUpdate.setAddress(co.getCustomer().getAddress());
			coUpdate.setMobile(co.getCustomer().getCellphone());
			coUpdate.setEmail(co.getCustomer().getEmail());
			coUpdate.getParams().put("id", co.getId());
			this.customerOrderMapper.updateCustomerOrder(coUpdate);
			
			if ("personal".equals(co.getCustomer().getCustomer_type())) {
				
			} else if ("business".equals(co.getCustomer().getCustomer_type())) {
				Organization o = new Organization();
				o.setOrder_id(co.getId());
				o.getParams().put("customer_id", co.getCustomer_id());
				this.organizationMapper.updateOrganization(o);
			}
			
			
		}
	}
}

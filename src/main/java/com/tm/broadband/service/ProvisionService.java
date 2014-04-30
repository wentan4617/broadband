package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.ContactUsMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;

@Service
public class ProvisionService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private ProvisionLogMapper provisionLogMapper;
	private ContactUsMapper contactUsMapper;
	
	@Autowired
	public ProvisionService(CustomerMapper customerMapper, 
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			ProvisionLogMapper provisionLogMapper,
			ContactUsMapper contactUsMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
		this.contactUsMapper = contactUsMapper;
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

	@Transactional
	public Page<ProvisionLog> queryProvisionLogsByPage(Page<ProvisionLog> page) {
		page.setTotalRecord(this.provisionLogMapper.selectProvisionLogsSum(page));
		page.setResults(this.provisionLogMapper.selectProvisionLogsByPage(page));
		return page;
	}
	
	@Transactional
	public ProvisionLog queryProvisionLogById(int id){
		return this.provisionLogMapper.selectProvisionLogById(id);
	}
	
	@Transactional
	public void editProvisionLog(ProvisionLog provisionLog){
		this.provisionLogMapper.updateProvisionLog(provisionLog);
	}
	
	@Transactional
	public void editContactUs(ContactUs contactUs){
		this.contactUsMapper.updateContactUs(contactUs);
	}
	
	@Transactional
	public Page<ContactUs> queryContactUssByPage(Page<ContactUs> page) {
		page.setTotalRecord(this.contactUsMapper.selectContactUssSum(page));
		page.setResults(this.contactUsMapper.selectContactUssByPage(page));
		return page;
	}
	
	@Transactional
	public int queryContactUssSumByPage(Page<ContactUs> page) {
		return this.contactUsMapper.selectContactUssSum(page);
	}
	
	@Transactional
	public ContactUs queryContactUsById(int id) {
		return this.contactUsMapper.selectContactUsById(id);
	}
	
}

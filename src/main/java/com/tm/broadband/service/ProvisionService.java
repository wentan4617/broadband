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
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Provision;
import com.tm.broadband.model.ProvisionLog;

@Service
public class ProvisionService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private ProvisionLogMapper provisionLogMapper;
	private ContactUsMapper contactUsMapper;
	
	@Autowired
	public ProvisionService(CustomerMapper customerMapper
			, CustomerOrderMapper customerOrderMapper
			, CustomerOrderDetailMapper customerOrderDetailMapper
			, ProvisionLogMapper provisionLogMapper
			, ContactUsMapper contactUsMapper ) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
		this.contactUsMapper = contactUsMapper;
	}

	public ProvisionService() {}

	public Provision queryOrdersSumAll() {
		
		Provision provision = new Provision();
		Page<CustomerOrder> p = new Page<CustomerOrder>();
		p.getParams().put("where", "query_order_status");
		p.getParams().put("status", "active");
		
		// New Order
		p.getParams().put("order_status", "pending");
		provision.setPendingSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "paid");
		provision.setPaidSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "pending-warning");
		provision.setPendingWarningSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		// Provision
		p.getParams().put("order_status", "ordering-pending");
		provision.setOrderingPendingSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "ordering-paid");
		provision.setOrderingPaidSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "rfs");
		provision.setRfsSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		// In Service
		p.getParams().put("order_status", "using");
		provision.setUsingSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		// Suspension
		p.getParams().put("order_status", "overflow");
		provision.setOverflowSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "suspended");
		provision.setSuspendedSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "waiting-for-disconnect");
		provision.setWaitingForDisconnectSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		p.getParams().put("order_status", "disconnected");
		provision.setDisconnectedSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
		
		// Void Order
		p.getParams().put("order_status", "void");
		provision.setVoidSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
				
		// Upgrade Order
		p.getParams().put("order_status", "upgrade");
		provision.setUpgradeSum(this.customerOrderMapper.selectCustomerOrdersSum(p));
				
		return provision;
	}
	
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
	public CustomerOrder queryCustomerOrderWithCustomerWithDetails(int orderid) {
		CustomerOrder coQ = new CustomerOrder();
		coQ.getParams().put("id", orderid);
		CustomerOrder order = this.customerOrderMapper.selectCustomerOrders(coQ).get(0);
		Customer cQ = new Customer();
		cQ.getParams().put("id", order.getCustomer_id());
		order.setCustomer(this.customerMapper.selectCustomers(cQ).get(0));
		CustomerOrderDetail codQ = new CustomerOrderDetail();
		codQ.getParams().put("order_id", order.getId());
		order.setCustomerOrderDetails(this.customerOrderDetailMapper.selectCustomerOrderDetails(codQ));
		return order;
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

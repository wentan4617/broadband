package com.tm.broadband.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.util.TMUtils;

/**
 * RMC Module service
 * 
 * @author Cook1fan
 * 
 */
@Service
public class CRMService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private CustomerTransactionMapper customerTransactionMapper;
	private CustomerInvoiceMapper customerInvoiceMapper;

	public CRMService() { }
	
	@Autowired
	public CRMService(CustomerMapper customerMapper,
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			CustomerTransactionMapper customerTransactionMapper,
			CustomerInvoiceMapper customerInvoiceMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerTransactionMapper = customerTransactionMapper;
		this.customerInvoiceMapper = customerInvoiceMapper;
	}
	
	@Transactional
	public void registerCustomer(Customer customer, Plan plan) {
		
		this.customerMapper.insertCustomer(customer);
		System.out.println("customer id: " + customer.getId());
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrder_serial(TMUtils.getCustomerOrderSerial(customer.getLogin_name()));
		customerOrder.setCustomer(customer);
		customerOrder.setOrder_total_price(plan.getPlan_price());
		customerOrder.setOrder_status("pending");
		
		this.customerOrderMapper.insertCustomerOrder(customerOrder);
		System.out.println("customer order id: " + customerOrder.getId());
		
		CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
		customerOrderDetail.setCustomerOrder(customerOrder);
		customerOrderDetail.setDetail_plan_name(plan.getPlan_name());
		customerOrderDetail.setDetail_plan_desc(plan.getPlan_desc());
		customerOrderDetail.setDetail_plan_price(plan.getPlan_price());
		customerOrderDetail.setDetail_data_flow(plan.getData_flow());
		customerOrderDetail.setDetail_plan_status(plan.getPlan_status());
		customerOrderDetail.setDetail_plan_type(plan.getPlan_type());
		customerOrderDetail.setDetail_plan_sort(plan.getPlan_sort());
		customerOrderDetail.setDetail_plan_memo(plan.getMemo());
		
		this.customerOrderDetailMapper.insertCustomerOrderDetail(customerOrderDetail);
		
	}
	
	@Transactional
	public void registerCustomer(Customer customer, Plan plan, CustomerTransaction customerTransaction) {
		
		customer.setRegister_date(new Date(System.currentTimeMillis()));
		customer.setActive_date(new Date(System.currentTimeMillis()));
		
		this.customerMapper.insertCustomer(customer);
		System.out.println("customer id: " + customer.getId());
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrder_serial(TMUtils.getCustomerOrderSerial(customer.getLogin_name()));
		customerOrder.setCustomer(customer);
		customerOrder.setOrder_status("paid");
		customerOrder.setOrder_type(plan.getPlan_group().replace("plan", "order"));
		customerOrder.setOrder_broadband_type(customer.getCustomerOrder().getOrder_broadband_type());
		
		if ("plan-topup".equals(plan.getPlan_group())) {
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
				customerOrder.setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getTopup().getTopup_fee());
			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {
				customerOrder.setOrder_total_price(plan.getTopup().getTopup_fee());
			}
		} else if ("plan-no-term".equals(plan.getPlan_group())) {
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
				customerOrder.setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getPlan_price() * plan.getPlan_prepay_months());
			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {
				customerOrder.setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			}
		} else if ("plan-term".equals(plan.getPlan_group())) {
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {

			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {

			}
		}
		
		this.customerOrderMapper.insertCustomerOrder(customerOrder);
		System.out.println("customer order id: " + customerOrder.getId());
		
		CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
		customerOrderDetail.setCustomerOrder(customerOrder);
		customerOrderDetail.setDetail_plan_name(plan.getPlan_name());
		customerOrderDetail.setDetail_plan_desc(plan.getPlan_desc());
		customerOrderDetail.setDetail_plan_price(plan.getPlan_price());
		customerOrderDetail.setDetail_data_flow(plan.getData_flow());
		customerOrderDetail.setDetail_plan_status(plan.getPlan_status());
		customerOrderDetail.setDetail_plan_type(plan.getPlan_type());
		customerOrderDetail.setDetail_plan_sort(plan.getPlan_sort());
		customerOrderDetail.setDetail_plan_group(plan.getPlan_group());
		customerOrderDetail.setDetail_plan_new_connection_fee(plan.getPlan_new_connection_fee());
		customerOrderDetail.setDetail_topup_fee(plan.getTopup().getTopup_fee());
		customerOrderDetail.setDetail_plan_memo(plan.getMemo());
		customerOrderDetail.setDetail_unit(plan.getPlan_prepay_months());
		
		this.customerOrderDetailMapper.insertCustomerOrderDetail(customerOrderDetail);
		
		customerTransaction.setCustomer(customer);
		customerTransaction.setCustomerOrder(customerOrder);
		customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
		
		this.customerTransactionMapper.insertCustomerTransaction(customerTransaction);
	}
	
	@Transactional
	public int queryExistCustomerByLoginName(String login_name) {
		return this.customerMapper.selectExistCustomerByLoginName(login_name);
	}
	
	@Transactional
	public Customer queryCustomerByIdWithCustomerOrder(int id) {
		return this.customerMapper.selectCustomerByIdWithCustomerOrder(id);
	}
	
	@Transactional
	public Page<Customer> queryCustomersByPage(Page<Customer> page) {
		page.setTotalRecord(this.customerMapper.selectCustomersSum(page));
		page.setResults(this.customerMapper.selectCustomersByPage(page));
		return page;
	}
	
	@Transactional
	public int queryCustomersSumByPage(Page<Customer> page) {
		return this.customerMapper.selectCustomersSum(page);
	}
	
	@Transactional
	public Customer queryCustomerWhenLogin(Customer customer) {
		return this.customerMapper.selectCustomerWhenLogin(customer);
	}
	
	@Transactional
	public List<CustomerOrder> queryCustomerOrdersByCustomerId(int customer_id) {
		return this.customerOrderMapper.selectCustomerOrdersByCustomerId(customer_id);
	}

	@Transactional
	public Page<CustomerInvoice> queryCustomerInvoicesByPage(Page<CustomerInvoice> page) {
		page.setTotalRecord(this.customerInvoiceMapper.selectCustomerInvoicesSum(page));
		page.setResults(this.customerInvoiceMapper.selectCustomerInvoicesByPage(page));
		return page;
	}

	@Transactional
	public Page<CustomerTransaction> queryCustomerTransactionsByPage(Page<CustomerTransaction> page) {
		page.setTotalRecord(this.customerTransactionMapper.selectCustomerTransactionsSum(page));
		page.setResults(this.customerTransactionMapper.selectCustomerTransactionsByPage(page));
		return page;
	}

	@Transactional
	public void editCustomerOrderCreateInvoice(CustomerOrder customerOrder, CustomerInvoice customerInvoice) {
		
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
		
		// insert provision
		
		// insert invoice
		this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);
		
		//select related transaction record ?
		
		
		// update transaction's invoice_id by customer id and order id
		CustomerTransaction customerTransaction = new CustomerTransaction();
		customerTransaction.setCustomer(customerInvoice.getCustomer());
		customerTransaction.setCustomerOrder(customerOrder);
		customerTransaction.setCustomerInvoice(customerInvoice);
		
		customerTransaction.getParams().put("", null);
		this.customerTransactionMapper.updateCustomerTransaction(customerTransaction);
		
		// create customer folder and generate invoice PDF
		
		
	}

}

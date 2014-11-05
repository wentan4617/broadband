package com.tm.broadband.service;

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

@Service
public class CustomerService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private CustomerInvoiceMapper customerInvoiceMapper;
	private CustomerTransactionMapper customerTransactionMapper;
	
	@Autowired
	public CustomerService(CustomerMapper customerMapper
			, CustomerOrderMapper customerOrderMapper
			, CustomerOrderDetailMapper customerOrderDetailMapper
			, CustomerInvoiceMapper customerInvoiceMapper
			, CustomerTransactionMapper customerTransactionMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerInvoiceMapper = customerInvoiceMapper;
		this.customerTransactionMapper = customerTransactionMapper;
	}

	public CustomerService() {}
	
	@Transactional
	public Customer queryCustomer(Customer customer) {
		List<Customer> customers = this.customerMapper.selectCustomers(customer);
		return customers != null && customers.size() > 0 ? customers.get(0) : null;
	}
	
	@Transactional
	public Customer queryCustomerWithOrderWithDetails(Customer customer) {
		List<Customer> customers = this.customerMapper.selectCustomers(customer);
		Customer customerReturn = customers != null && customers.size() > 0 ? customers.get(0) : null;
		List<CustomerOrder> orders = this.customerOrderMapper.selectCustomerOrders(customer.getCustomerOrder());
		CustomerOrder customerOrderReturn = orders != null && orders.size() > 0 ? orders.get(0) : null;
		customerReturn.setCustomerOrder(customerOrderReturn);
		CustomerOrderDetail codQ = new CustomerOrderDetail();
		codQ.getParams().put("order_id", customerOrderReturn.getId());
		List<CustomerOrderDetail> cods = this.customerOrderDetailMapper.selectCustomerOrderDetails(codQ);
		customerOrderReturn.setCustomerOrderDetails(cods);
		return customerReturn;
	}
	
	@Transactional
	public Customer queryCustomerWithOrderWithInvoice(Customer customer) {
		List<Customer> customers = this.customerMapper.selectCustomers(customer);
		Customer customerReturn = customers != null && customers.size() > 0 ? customers.get(0) : null;
		List<CustomerOrder> orders = this.customerOrderMapper.selectCustomerOrders(customer.getCustomerOrder());
		CustomerOrder customerOrderReturn = orders != null && orders.size() > 0 ? orders.get(0) : null;
		customerReturn.setCustomerOrder(customerOrderReturn);
		List<CustomerInvoice> invoices = this.customerInvoiceMapper.selectCustomerInvoices(customer.getCustomerInvoice());
		CustomerInvoice CustomerInvoiceReturn = invoices != null && invoices.size() > 0 ? invoices.get(0) : null;
		customerReturn.setCustomerInvoice(CustomerInvoiceReturn);
		return customerReturn;
	}
	
	@Transactional
	public void editCustomer(Customer customer) {
		this.customerMapper.updateCustomer(customer);
	}
	
	@Transactional
	public List<CustomerOrder> queryCustomerOrdersWithDetails(CustomerOrder co) {
		
		List<CustomerOrder> orders = this.customerOrderMapper.selectCustomerOrders(co);
		
		if (orders != null && orders.size() > 0) {
			for (CustomerOrder order : orders) {
				CustomerOrderDetail cod = new CustomerOrderDetail();
				cod.getParams().put("order_id", order.getId());
				List<CustomerOrderDetail> cods = this.customerOrderDetailMapper.selectCustomerOrderDetails(cod);
				order.setCustomerOrderDetails(cods);
			}
		}
		
		return orders;
		
	}
	
	@Transactional
	public Page<CustomerInvoice> queryCustomerInvoicesByPageWithTransaction(Page<CustomerInvoice> page) {
		
		page.setTotalRecord(this.customerInvoiceMapper.selectCustomerInvoicesSum(page));
		page.setResults(this.customerInvoiceMapper.selectCustomerInvoicesByPage(page));
		
		List<CustomerInvoice> invoices = page.getResults();
		if (invoices != null && invoices.size() > 0) {
			
			CustomerTransaction ct = new CustomerTransaction();
			ct.getParams().put("customer_id", page.getParams().get("customer_id"));
			ct.getParams().put("order_id", page.getParams().get("order_id"));
			ct.getParams().put("orderby", "order by transaction_date desc");
			
			List<CustomerTransaction> transactions = this.customerTransactionMapper.selectCustomerTransactions(ct);
			
			if (transactions != null && transactions.size() > 0) {
				for (CustomerTransaction transaction: transactions) {
					for (CustomerInvoice invoice : invoices) {
						if (transaction.getInvoice_id() != null && (transaction.getInvoice_id().intValue() == invoice.getId())) {
							invoice.getCts().add(transaction);
							break;
						}
					}
				}
			}
			
		}
		return page;
	}
	
	
}

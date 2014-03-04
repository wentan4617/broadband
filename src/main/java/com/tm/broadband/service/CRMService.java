package com.tm.broadband.service;


import java.util.Date;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.pdf.InvoicePDFCreator;
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
	private ProvisionLogMapper provisionLogMapper;

	public CRMService() { }
	
	@Autowired
	public CRMService(CustomerMapper customerMapper,
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			CustomerTransactionMapper customerTransactionMapper,
			CustomerInvoiceMapper customerInvoiceMapper,
			ProvisionLogMapper provisionLogMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerTransactionMapper = customerTransactionMapper;
		this.customerInvoiceMapper = customerInvoiceMapper;
		this.provisionLogMapper = provisionLogMapper;
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

	public void editCustomerOrderCreateInvoice(
			Customer customer,
			CustomerOrder customerOrder,
			CustomerInvoice customerInvoice,
			CustomerTransaction customerTransaction,
			User user,
			ProvisionLog proLog) {
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
		
		// insert provision
		this.provisionLogMapper.insertProvisionLog(proLog);
		
		// insert invoice
		CustomerTransaction tempCustomerTransaction = this.customerTransactionMapper.selectCustomerTransaction(customerTransaction);
		customerInvoice.setPaid_date(tempCustomerTransaction.getTransaction_date());
		customerInvoice.setPaid_type(tempCustomerTransaction.getCard_name());
		this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);
		
		// update transaction's invoice_id by customer id and order id and invoice_id is 'null' and transaction_sort is 'plan-no-term'
		customerTransaction.setCustomerInvoice(customerInvoice);
		this.customerTransactionMapper.updateCustomerTransaction(customerTransaction);
		
		/*
		 * 
		 * Invoice PDF generate manipulation begin
		 * 
		 */
		
		// store company detail begin
		CompanyDetail companyDetail = new CompanyDetail();
		companyDetail.setGst_registration_number("99 728 906");
		companyDetail.setName("Total Mobile Solution Limited");
		companyDetail.setAddress("PO Box 68177, Newton, Auckland 1145");
		companyDetail.setTelephone("64 9 880 1001");
		companyDetail.setFax("64 9 880 1009");
		companyDetail.setDomain("www.cyberpark.co.nz");
		//
		companyDetail.setBank_name("BNZ");
		companyDetail.setBank_account_name("WorldNet Services Limited");
		companyDetail.setBank_account_number("02-0192-0087576-000");
		// store company detail end
		
		// get Customer by customer_id
		Customer invoiceCustomer = this.customerMapper.selectCustomerByIdWithCustomerOrder(customer.getId());
		// get CustomerOrders by customer_id
		List<CustomerOrder> invoiceCustomerOrders = invoiceCustomer.getCustomerOrders();
		
		// get CustomerInvoice by invoice_id
		CustomerInvoice invoiceCustomerInvoice = this.customerInvoiceMapper.selectCustomerInvoiceById(customerInvoice.getId());
		invoiceCustomerInvoice.setInvoice_desc("Previous invoice balance");
		invoiceCustomerInvoice.setDue_date(null);;
		// get and set last CustomerInvoice by invoice_id
		CustomerInvoice lastCustomerInvoice = new CustomerInvoice();
		lastCustomerInvoice.setAmount_paid(0.0);
		lastCustomerInvoice.setAmount_payable(0.0);
		lastCustomerInvoice.setBalance(0.0);
		invoiceCustomerInvoice.setLastCustomerInvoice(lastCustomerInvoice);

		// create specific directories and generate invoice PDF
		String filePath = TMUtils.createPath(
				"broadband"
				+File.separator+"customers"
				+File.separator+customer.getId()
				+File.separator+customerInvoice.getId()+".pdf");
		customerInvoice.setInvoice_pdf_path(filePath);
		this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);
		
		// initialize invoice's important informations
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(invoiceCustomer);
		invoicePDF.setCustomerOrders(invoiceCustomerOrders);
		invoicePDF.setCustomerInvoice(invoiceCustomerInvoice);
		
		try {
			// generate invoice PDF
			invoicePDF.create(filePath);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		

		/*
		 * 
		 * Invoice PDF generate manipulation end
		 * 
		 */
	}

	@Transactional
	public void editCustomerOrderAndCreateProvision(
			CustomerOrder customerOrder,
			ProvisionLog proLog) {
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
		// insert provision
		this.provisionLogMapper.insertProvisionLog(proLog);
	}

	@Transactional 
	public String queryCustomerInvoiceFilePathById(int id){
		return this.customerInvoiceMapper.selectCustomerInvoiceFilePathById(id);
	}

}

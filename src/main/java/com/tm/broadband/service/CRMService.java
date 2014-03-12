package com.tm.broadband.service;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.mapper.CompanyDetailMapper;
import com.tm.broadband.mapper.CustomerInvoiceDetailMapper;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.NotificationMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
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
	private CustomerInvoiceMapper customerInvoiceMapper;
	private CustomerInvoiceDetailMapper customerInvoiceDetailMapper;
	private CustomerTransactionMapper customerTransactionMapper;
	private ProvisionLogMapper provisionLogMapper;
	private CompanyDetailMapper companyDetailMapper;
	private ApplicationMailer applicationMailer;
	private NotificationMapper notificationMapper;

	public CRMService() { }
	
	@Autowired
	public CRMService(CustomerMapper customerMapper,
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			CustomerInvoiceMapper customerInvoiceMapper,
			CustomerInvoiceDetailMapper customerInvoiceDetailMapper,
			CustomerTransactionMapper customerTransactionMapper,
			ProvisionLogMapper provisionLogMapper,
			CompanyDetailMapper companyDetailMapper,
			ApplicationMailer applicationMailer,
			NotificationMapper notificationMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerTransactionMapper = customerTransactionMapper;
		this.customerInvoiceMapper = customerInvoiceMapper;
		this.customerInvoiceDetailMapper = customerInvoiceDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
		this.companyDetailMapper = companyDetailMapper;
		this.applicationMailer = applicationMailer;
		this.notificationMapper = notificationMapper;
	}
	
	@Transactional
	public void registerCustomer(Customer customer, Plan plan, CustomerTransaction customerTransaction) {
		
		customer.setRegister_date(new Date(System.currentTimeMillis()));
		customer.setActive_date(new Date(System.currentTimeMillis()));
		
		this.customerMapper.insertCustomer(customer);
		System.out.println("customer id: " + customer.getId());
		
		customer.getCustomerOrder().setCustomer(customer);
		customer.getCustomerOrder().setOrder_status("paid");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));
		
		CustomerOrderDetail dplan = new CustomerOrderDetail();
		dplan.setCustomerOrder(customer.getCustomerOrder());
		dplan.setDetail_name(plan.getPlan_name());
		dplan.setDetail_desc(plan.getPlan_desc());
		dplan.setDetail_price(plan.getPlan_price());
		dplan.setDetail_data_flow(plan.getData_flow());
		dplan.setDetail_plan_status(plan.getPlan_status());
		dplan.setDetail_plan_type(plan.getPlan_type());
		dplan.setDetail_plan_sort(plan.getPlan_sort());
		dplan.setDetail_plan_group(plan.getPlan_group());
		dplan.setDetail_plan_memo(plan.getMemo());
		dplan.setDetail_unit(plan.getPlan_prepay_months());
		
		
		if ("plan-topup".equals(plan.getPlan_group())) {
			
			dplan.setDetail_type("plan-topup");
			dplan.setDetail_is_next_pay(0);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(dplan);
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getTopup().getTopup_fee());
				
				CustomerOrderDetail dconn = new CustomerOrderDetail();
				dconn.setCustomerOrder(customer.getCustomerOrder());
				dconn.setDetail_name("Broadband New Connection");
				dconn.setDetail_price(plan.getPlan_new_connection_fee());
				dconn.setDetail_is_next_pay(0);
				dconn.setDetail_type("new-connection");
				dconn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(dconn);
				
			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				customer.getCustomerOrder().setOrder_total_price(plan.getTopup().getTopup_fee());
				
				CustomerOrderDetail dtrans = new CustomerOrderDetail();
				dtrans.setCustomerOrder(customer.getCustomerOrder());
				dtrans.setDetail_name("Broadband Transition");
				dtrans.setDetail_is_next_pay(0);
				dtrans.setDetail_type("transition");
				dtrans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(dtrans);
			}
			
			CustomerOrderDetail dtopup = new CustomerOrderDetail();
			dtopup.setCustomerOrder(customer.getCustomerOrder());
			dtopup.setDetail_name("Broadband Top-Up");
			dtopup.setDetail_price(plan.getTopup().getTopup_fee());
			dtopup.setDetail_is_next_pay(0);
			customer.getCustomerOrder().getCustomerOrderDetails().add(dtopup);
			
		} else if ("plan-no-term".equals(plan.getPlan_group())) {
			
			dplan.setDetail_type("plan-no-term");
			dplan.setDetail_is_next_pay(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(dplan);
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getPlan_price() * plan.getPlan_prepay_months());
			
				CustomerOrderDetail dconn = new CustomerOrderDetail();
				dconn.setCustomerOrder(customer.getCustomerOrder());
				dconn.setDetail_name("Broadband New Connection");
				dconn.setDetail_price(plan.getPlan_new_connection_fee());
				dconn.setDetail_is_next_pay(0);
				dconn.setDetail_type("new-connection");
				dconn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(dconn);
				
			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail dtrans = new CustomerOrderDetail();
				dtrans.setCustomerOrder(customer.getCustomerOrder());
				dtrans.setDetail_name("Broadband Transition");
				dtrans.setDetail_is_next_pay(0);
				dtrans.setDetail_type("transition");
				dtrans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(dtrans);
			}
		} else if ("plan-term".equals(plan.getPlan_group())) {
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {

			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {

			}
		}
		
		this.customerOrderMapper.insertCustomerOrder(customer.getCustomerOrder());
		System.out.println("customer order id: " + customer.getCustomerOrder().getId());
		

		CustomerInvoice customerInvoice = new CustomerInvoice();
		customerInvoice.setCustomer(customer);
		customerInvoice.setCustomerOrder(customer.getCustomerOrder());
		customerInvoice.setCreate_date(new Date(System.currentTimeMillis()));
		customerInvoice.setAmount_payable(customer.getCustomerOrder().getOrder_total_price());
		customerInvoice.setAmount_paid(customerTransaction.getAmount());
		customerInvoice.setBalance(customerInvoice.getAmount_payable() - customerInvoice.getAmount_paid());
		customerInvoice.setStatus("paid");
		customerInvoice.setPaid_date(new Date(System.currentTimeMillis()));
		customerInvoice.setPaid_type(customerTransaction.getCard_name());

		CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
		customerOrderDetail.setCustomerOrder(customer.getCustomerOrder());
		customerOrderDetail.setDetail_name(plan.getPlan_name());
		customerOrderDetail.setDetail_desc(plan.getPlan_desc());
		customerOrderDetail.setDetail_price(plan.getPlan_price());
		customerOrderDetail.setDetail_data_flow(plan.getData_flow());
		customerOrderDetail.setDetail_plan_status(plan.getPlan_status());
		customerOrderDetail.setDetail_plan_type(plan.getPlan_type());
		customerOrderDetail.setDetail_plan_sort(plan.getPlan_sort());
		customerOrderDetail.setDetail_plan_group(plan.getPlan_group());
		customerOrderDetail.setDetail_plan_new_connection_fee(plan.getPlan_new_connection_fee());
		customerOrderDetail.setDetail_topup_fee(plan.getTopup().getTopup_fee());
		customerOrderDetail.setDetail_plan_memo(plan.getMemo());
		customerOrderDetail.setDetail_unit(plan.getPlan_prepay_months());
		
		this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);
		
		for (CustomerOrderDetail cod : customer.getCustomerOrder().getCustomerOrderDetails()) {
			this.customerOrderDetailMapper.insertCustomerOrderDetail(cod);
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			cid.setCustomerInvoice(customerInvoice);
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_desc(cod.getDetail_desc());
			cid.setInvoice_detail_price(cod.getDetail_price());
			cid.setInvoice_detail_unit(cod.getDetail_unit());
			this.customerInvoiceDetailMapper.insertCustomerInvoiceDetail(cid);
		}
		
		customerTransaction.setCustomer(customer);
		customerTransaction.setCustomerOrder(customer.getCustomerOrder());
		customerTransaction.setCustomerInvoice(customerInvoice);
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
	public List<CustomerTransaction> queryCustomerTransactionsByCustomerId(int id) {
		return this.customerTransactionMapper.selectCustomerTransactionsByCustomerId(id);
	}

	@Transactional
	public void editCustomerOrder(CustomerOrder customerOrder, ProvisionLog proLog) {
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
		// insert provision
		this.provisionLogMapper.insertProvisionLog(proLog);
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
	
	// manually generating invoice PDF
	@Transactional
	public void createInvoicePDFByInvoiceID(int invoiceId){
		// store company details begin
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		// store company details end
		
		// invoice PDF generator manipulation begin
		// get necessary models begin
		// get last invoice, customer and current invoice it self at the same time by using left join
		CustomerInvoice customerInvoice = this.customerInvoiceMapper.selectInvoiceWithLastInvoiceIdById(invoiceId);
		// get invoice details
		customerInvoice.setCustomerInvoiceDetails(this.customerInvoiceDetailMapper.selectCustomerInvoiceDetailsByCustomerInvoiceId(invoiceId));
		// get customer details from invoice
		Customer customer = customerInvoice.getCustomer();
		// get necessary models end

		// create specific directories and generate invoice PDF
		String filePath = TMUtils.createPath(
				"broadband"
				+File.separator+"customers"
				+File.separator+customer.getId()
				+File.separator+"Invoice - #"+customerInvoice.getId()+".pdf");
		// set file path
		customerInvoice.setInvoice_pdf_path(filePath);
		// add sql condition: id
		customerInvoice.getParams().put("id", customerInvoice.getId());
		this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);
		
		// initialize invoice's important informations
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(customer);
		invoicePDF.setCustomerInvoice(customerInvoice);
		
		try {
			// generate invoice PDF
			invoicePDF.create(filePath);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		// invoice PDF generator manipulation end
		
	}

	@Transactional 
	public void createNextInvoice(CustomerOrder customerOrder, ApplicationEmail applicationEmail) throws ParseException{
		List<CustomerOrder> customerOrdersList = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrder);

		// initialize invoice's important informations
		Iterator<CustomerOrder> customerOrdersIter = customerOrdersList.iterator();
		while(customerOrdersIter.hasNext()){
			System.out.println(customerOrdersIter);
			// store company detail begin
			CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
			// store company detail end
			
			/*
			 * get specific models begin
			 */
			// initiate models begin
			// current customer order model
			customerOrder = customerOrdersIter.next();

			/*
			 * set next invoice date begin
			 */
			
			// get next invoice date
			int nextInvoiceDay = 30;
			Calendar calNextInvoiceDay = Calendar.getInstance();
			calNextInvoiceDay.setTime(customerOrder.getNext_invoice_create_date());
			calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);

			// update customer order's next invoice create day begin
			customerOrder.setNext_invoice_create_date(calNextInvoiceDay.getTime());
			customerOrder.getParams().put("id", customerOrder.getId());
			this.customerOrderMapper.updateCustomerOrder(customerOrder);
			// update customer order's next invoice create day end
			/*
			 * set next invoice date end
			 */
			
			// customer model
			Customer customer = customerOrder.getCustomer();
			// previous invoice model
			CustomerInvoice customerPreviousInvoice = new CustomerInvoice();
			customerPreviousInvoice.getParams().put("customer_id", customer.getId());
			customerPreviousInvoice.getParams().put("order_id", customerOrder.getId());
			customerPreviousInvoice.getParams().put("by_max_id", "");
			// customer previous invoice
			List<CustomerInvoice> customerPreviousInvoicesList = this.customerInvoiceMapper.selectCustomerInvoiceBySome(customerPreviousInvoice);
			if(!customerPreviousInvoicesList.toString().equals("[]")){
				customerPreviousInvoice = customerPreviousInvoicesList.iterator().next();
			}
			// current invoice model
			CustomerInvoice customerInvoice = new CustomerInvoice();
			// initiate models end
			/*
			 * get specific models end
			 */
			
			// set customer's new invoice details begin

			// set invoice create date begin
			
			/*
			 * 
			 * 		invoiceCreateDay HAVE TO ASSIGN TO new Date() BEFORE PRODUCT MODE
			 * 
			 */
			Date invoiceCreateDay = new Date();
			// set invoice due date begin
			int invoiceDueDay = 15;
			Calendar calInvoiceDueDay = Calendar.getInstance();
			calInvoiceDueDay.setTime(invoiceCreateDay);
			calInvoiceDueDay.add(Calendar.DAY_OF_MONTH, invoiceDueDay);
			// set invoice due date end
			
			customerInvoice.setLast_invoice_id(customerPreviousInvoice.getId());
			customerInvoice.setCustomer(customer);
			customerInvoice.setCustomerOrder(customerOrder);
			customerInvoice.setCreate_date(invoiceCreateDay);
			customerInvoice.setDue_date(calInvoiceDueDay.getTime());
			// detail holder begin
			Double amountPayable = 0.0;
			List<CustomerInvoiceDetail> customerInvoiceDetailList = new ArrayList<CustomerInvoiceDetail>();
			// detail holder end
			List<CustomerOrderDetail> customerOrderDetailList = customerOrder.getCustomerOrderDetails();
			Iterator<CustomerOrderDetail> iterCustomerOrderDetails = customerOrderDetailList.iterator();
			while(iterCustomerOrderDetails.hasNext()){
				CustomerOrderDetail customerOrderDetail = iterCustomerOrderDetails.next();
				CustomerInvoiceDetail customerInvoiceDetail = new CustomerInvoiceDetail();
				
				// if next pay equals to 1 then paste this order detail into a new invoice detail
				if(customerOrderDetail.getDetail_is_next_pay().equals(1)){
					// if type contains plan-
					if(customerOrderDetail.getDetail_type().indexOf("plan-")!= -1 && customerOrderDetail.getDetail_type().indexOf("plan-topup") == -1){
						customerInvoiceDetail.setCustomerInvoice(customerInvoice);
						customerInvoiceDetail.setInvoice_detail_name(customerOrderDetail.getDetail_name());
						customerInvoiceDetail.setInvoice_detail_price(customerOrderDetail.getDetail_price());
						customerInvoiceDetail.setInvoice_detail_unit(1);
						// add invoice detail to list
						customerInvoiceDetailList.add(customerInvoiceDetail);
						// increase amountPayable
						amountPayable = customerOrderDetail.getDetail_price() * customerInvoiceDetail.getInvoice_detail_unit();
					}
//					// if type contains new connection
//					if(customerOrderDetail.getDetail_type().indexOf("new-connection")>0){
//						customerInvoiceDetail.setCustomerInvoice(customerInvoice);
//						customerInvoiceDetail.setInvoice_detail_name(customerOrderDetail.getDetail_name());
//						customerInvoiceDetail.setInvoice_detail_price(customerOrderDetail.getDetail_price());
//						customerInvoiceDetail.setInvoice_detail_unit(customerOrderDetail.getDetail_unit());
//						customerInvoiceDetailList.add(customerInvoiceDetail);
//					}
//					// if type contains transition
//					if(customerOrderDetail.getDetail_type().indexOf("transition")>0){
//						customerInvoiceDetail.setCustomerInvoice(customerInvoice);
//						customerInvoiceDetail.setInvoice_detail_name(customerOrderDetail.getDetail_name());
//						customerInvoiceDetail.setInvoice_detail_price(customerOrderDetail.getDetail_price());
//						customerInvoiceDetail.setInvoice_detail_unit(customerOrderDetail.getDetail_unit());
//						customerInvoiceDetailList.add(customerInvoiceDetail);
//					}
//					// if type contains top-up
//					if(customerOrderDetail.getDetail_type().indexOf("top-up")>0){
//						
//					}
//					// if type contains hardware-
//					if(customerOrderDetail.getDetail_type().indexOf("hardware-")>0){
//						
//					}
				}
			}
			customerInvoice.setAmount_payable(amountPayable + customerPreviousInvoice.getBalance());
			customerInvoice.setStatus("unpaid");
			customerInvoice.setAmount_paid(0.0);
			BigDecimal currentTotalPayable = new BigDecimal(String.valueOf((amountPayable + customerPreviousInvoice.getBalance())));
			BigDecimal currentAmountPaid = new BigDecimal(String.valueOf(customerInvoice.getAmount_paid()));
			// balance = payable - paid
			customerInvoice.setBalance(currentTotalPayable.subtract(currentAmountPaid).doubleValue());
			// set customer's new invoice details end
			
			// get generated key while performing insertion
			this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);

			// reset customer invoice from customer invoice got by invoice_id
			customerInvoice = this.customerInvoiceMapper.selectCustomerInvoiceById(customerInvoice.getId());
			
			// inserting customer invoice detail iteratively
			Iterator<CustomerInvoiceDetail> iterCustomerInvoiceDetails = customerInvoiceDetailList.iterator();
			while(iterCustomerInvoiceDetails.hasNext()){
				CustomerInvoiceDetail customerInvoiceDetail = iterCustomerInvoiceDetails.next();
				// mark up invoice id
				customerInvoiceDetail.setCustomerInvoice(customerInvoice);
				this.customerInvoiceDetailMapper.insertCustomerInvoiceDetail(customerInvoiceDetail);
			}
			
			// relatively setting invoice details into invoice
			customerInvoice.setCustomerInvoiceDetails(this.customerInvoiceDetailMapper.selectCustomerInvoiceDetailsByCustomerInvoiceId(customerInvoice.getId()));
			customerInvoice.setLastCustomerInvoice(customerPreviousInvoice);

			InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
			invoicePDF.setCompanyDetail(companyDetail);
			invoicePDF.setCustomer(customer);
			invoicePDF.setCustomerInvoice(customerInvoice);
			
			// create specific directories and generate invoice PDF
			String filePath = TMUtils.createPath(
					"broadband"
					+File.separator+"customers"
					+File.separator+customer.getId()
					+File.separator+"Invoice - #"+customerInvoice.getId()+".pdf");
			// set file path
			customerInvoice.setInvoice_pdf_path(filePath);
			// add sql condition: id
			customerInvoice.getParams().put("id", customerInvoice.getId());
			this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);
			
			try {
				// generate invoice PDF
				invoicePDF.create(filePath);
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			
			// binding attachment name & path to email
			Notification notification = this.notificationMapper.selectNotificationBySort("invoice");
			// call mail at value retriever
			TMUtils.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
			applicationEmail.setAddressee("stevenchen1989930@gmail.com"); // customer.getEmail()
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("Invoice - #" + customerInvoice.getId() + ".pdf");
			applicationEmail.setAttachPath(filePath);
			this.sendMail(applicationEmail);
		}
	}
	
	@Transactional
	public void sendMail(ApplicationEmail applicationEmail){
		this.applicationMailer.sendMailByAsynchronousMode(applicationEmail);
	}
	
	
	/*
	 * CompanyDetail begin
	 */
	
	@Transactional
	public CompanyDetail queryCompanyDetail() {
		return this.companyDetailMapper.selectCompanyDetail();
	}
	
	/*
	 * CompanyDetail end
	 */

}

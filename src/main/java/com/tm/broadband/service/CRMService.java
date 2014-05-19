package com.tm.broadband.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.mapper.CompanyDetailMapper;
import com.tm.broadband.mapper.ContactUsMapper;
import com.tm.broadband.mapper.CustomerInvoiceDetailMapper;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.NotificationMapper;
import com.tm.broadband.mapper.OrganizationMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.pdf.InvoicePDFCreator;
import com.tm.broadband.util.Calculation4PlanTermInvoice;
import com.tm.broadband.util.TMUtils;

/**
 * CRM Module service
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
	private NotificationMapper notificationMapper;
	private OrganizationMapper organizationMapper;
	private ContactUsMapper contactUsMapper;
	
	// service
	private MailerService mailerService;
	private SmserService smserService;

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
			MailerService mailerService,
			NotificationMapper notificationMapper,
			SmserService smserService,
			OrganizationMapper organizationMapper,
			ContactUsMapper contactUsMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerTransactionMapper = customerTransactionMapper;
		this.customerInvoiceMapper = customerInvoiceMapper;
		this.customerInvoiceDetailMapper = customerInvoiceDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
		this.companyDetailMapper = companyDetailMapper;
		this.mailerService = mailerService;
		this.notificationMapper = notificationMapper;
		this.smserService = smserService;
		this.organizationMapper = organizationMapper;
		this.contactUsMapper = contactUsMapper;
	}
	
	
	public void doOrderConfirm(Customer customer, Plan plan) {
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().setOrder_create_date(new Date());
		customer.getCustomerOrder().setOrder_status("paid");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		cod_plan.setDetail_name(plan.getPlan_name());
		cod_plan.setDetail_desc(plan.getPlan_desc());
		cod_plan.setDetail_price(plan.getPlan_price() == null ? 0d : plan.getPlan_price());
		cod_plan.setDetail_data_flow(plan.getData_flow());
		cod_plan.setDetail_plan_status(plan.getPlan_status());
		cod_plan.setDetail_plan_type(plan.getPlan_type());
		cod_plan.setDetail_plan_sort(plan.getPlan_sort());
		cod_plan.setDetail_plan_group(plan.getPlan_group());
		cod_plan.setDetail_plan_class(plan.getPlan_class());
		cod_plan.setDetail_plan_new_connection_fee(plan.getPlan_new_connection_fee());
		cod_plan.setDetail_term_period(plan.getTerm_period());
		customer.getCustomerOrder().setTerm_period(plan.getTerm_period());
		cod_plan.setDetail_plan_memo(plan.getMemo());
		cod_plan.setDetail_unit(plan.getPlan_prepay_months() == null ? 1 : plan.getPlan_prepay_months());
		cod_plan.setDetail_type(plan.getPlan_group());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(cod_plan);
		
		if ("plan-topup".equals(plan.getPlan_group())) {
			
			cod_plan.setDetail_is_next_pay(0);
			cod_plan.setDetail_expired(new Date());
			
			customer.getCustomerOrder().setOrder_total_price(plan.getTopup().getTopup_fee());
			
			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
			cod_topup.setDetail_name("Broadband Top-Up");
			cod_topup.setDetail_price(plan.getTopup().getTopup_fee());
			cod_topup.setDetail_type("topup");
			cod_topup.setDetail_is_next_pay(0);
			cod_topup.setDetail_expired(new Date());
			cod_topup.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_topup);
			
		} else if ("plan-no-term".equals(plan.getPlan_group())) {
			
			customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			System.out.println("Order_total_price: " + customer.getCustomerOrder().getOrder_total_price());
			
			cod_plan.setDetail_is_next_pay(1);
			
		} else if ("plan-term".equals(plan.getPlan_group())) {
			
			customer.getCustomerOrder().setOrder_status("pending");
			
			customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			
			// add plan free pstn
			for (int i = 0; i < plan.getPstn_count(); i++) {
				CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
				if ("personal".equals(plan.getPlan_class())) {
					cod_pstn.setDetail_name("Home Phone Line");
				} else if ("business".equals(plan.getPlan_class())) {
					cod_pstn.setDetail_name("Business Phone Line");
				}
				cod_pstn.setDetail_price(0d);
				cod_pstn.setDetail_is_next_pay(0);
				cod_pstn.setDetail_expired(new Date());
				cod_pstn.setDetail_type("pstn");
				cod_pstn.setDetail_unit(1);
				cod_pstn.setPstn_number(customer.getCustomerOrder().getTransition_porting_number());
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_pstn);
			}
			
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			cod_hd.setDetail_name("Free Router");
			cod_hd.setDetail_price(0d);
			cod_hd.setDetail_is_next_pay(0);
			cod_hd.setDetail_expired(new Date());
			cod_hd.setDetail_unit(1);
			cod_hd.setIs_post(0);
			customer.getCustomerOrder().setHardware_post(customer.getCustomerOrder().getHardware_post() == null ? 1 : customer.getCustomerOrder().getHardware_post() + 1);
			cod_hd.setDetail_type("hardware-router");
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
		}
		
		//**************************************************
		
		if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
			
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getTransition_fee());
			
			CustomerOrderDetail cod_trans = new CustomerOrderDetail();
			cod_trans.setDetail_name("Broadband Transition");
			cod_trans.setDetail_price(plan.getTransition_fee());
			if ("plan-term".equals(plan.getPlan_group())) {
				cod_trans.setDetail_is_next_pay(1);
			} else {
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_expired(new Date());
			}
			cod_trans.setDetail_type("transition");
			cod_trans.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
			
		}  else if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
			
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getPlan_new_connection_fee());
			
			CustomerOrderDetail cod_conn = new CustomerOrderDetail();
			cod_conn.setDetail_name("Broadband New Connection");
			cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
			if ("plan-term".equals(plan.getPlan_group())) {
				cod_conn.setDetail_is_next_pay(1);
			} else {
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_expired(new Date());
			}
			cod_conn.setDetail_type("new-connection");
			cod_conn.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);
			
		} else if ("jackpot".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
			
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getJackpot_fee());
			
			CustomerOrderDetail cod_jackpot = new CustomerOrderDetail();
			cod_jackpot.setDetail_name("Broadband New Connection & Jackpot Installation");
			cod_jackpot.setDetail_price(plan.getJackpot_fee());
			if ("plan-term".equals(plan.getPlan_group())) {
				cod_jackpot.setDetail_is_next_pay(1);
			} else {
				cod_jackpot.setDetail_is_next_pay(0);
				cod_jackpot.setDetail_expired(new Date());
			}
			cod_jackpot.setDetail_type("jackpot");
			cod_jackpot.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_jackpot);
		} 
		
		for (Hardware chd: customer.getCustomerOrder().getHardwares()) {
		
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			cod_hd.setDetail_name(chd.getHardware_name());
			cod_hd.setDetail_price(chd.getHardware_price());
			if ("plan-term".equals(plan.getPlan_group())) {
				cod_hd.setDetail_is_next_pay(1);
			} else {
				cod_hd.setDetail_is_next_pay(0);
				cod_hd.setDetail_expired(new Date());
			}
			cod_hd.setDetail_unit(1);
			cod_hd.setIs_post(0);
			cod_hd.setDetail_type("hardware-router");
			customer.getCustomerOrder().setHardware_post(customer.getCustomerOrder().getHardware_post() == null ? 1 : customer.getCustomerOrder().getHardware_post() + 1);
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + chd.getHardware_price());
		
		}
		
	}
	
	@Transactional
	public void registerCustomer(Customer customer, CustomerTransaction customerTransaction) {
		
		customer.setRegister_date(new Date());
		customer.setActive_date(new Date());
		
		this.customerMapper.insertCustomer(customer);
		//System.out.println("customer id: " + customer.getId());
		
		if ("business".equals(customer.getCustomer_type())) {
			customer.getOrganization().setCustomer_id(customer.getId());
			this.organizationMapper.insertOrganization(customer.getOrganization());
		}
		
		customer.getCustomerOrder().setCustomer_id(customer.getId());
		
		this.customerOrderMapper.insertCustomerOrder(customer.getCustomerOrder());
		//System.out.println("customer order id: " + customer.getCustomerOrder().getId());
		
		CustomerInvoice customerInvoice = new CustomerInvoice();
		customerInvoice.setCustomer_id(customer.getId());
		customerInvoice.setOrder_id(customer.getCustomerOrder().getId());
		customerInvoice.setCreate_date(new Date(System.currentTimeMillis()));
		customerInvoice.setAmount_payable(customer.getCustomerOrder().getOrder_total_price());
		customerInvoice.setAmount_paid(customerTransaction.getAmount());
		customerInvoice.setBalance(customerInvoice.getAmount_payable() - customerInvoice.getAmount_paid());
		customerInvoice.setStatus("paid");
		customerInvoice.setPaid_date(new Date(System.currentTimeMillis()));
		customerInvoice.setPaid_type(customerTransaction.getCard_name());
		
		this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);
		customer.setCustomerInvoice(customerInvoice);
		
		for (CustomerOrderDetail cod : customer.getCustomerOrder().getCustomerOrderDetails()) {
			cod.setOrder_id(customer.getCustomerOrder().getId());
			this.customerOrderDetailMapper.insertCustomerOrderDetail(cod);
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			cid.setInvoice_id(customerInvoice.getId());
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_desc(cod.getDetail_desc());
			cid.setInvoice_detail_price(cod.getDetail_price());
			cid.setInvoice_detail_unit(cod.getDetail_unit());
			this.customerInvoiceDetailMapper.insertCustomerInvoiceDetail(cid);
		}
		
		customerTransaction.setCustomer_id(customer.getId());
		customerTransaction.setOrder_id(customer.getCustomerOrder().getId());
		customerTransaction.setInvoice_id(customerInvoice.getId());
		customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
		
		this.customerTransactionMapper.insertCustomerTransaction(customerTransaction);
	}
	
	@Transactional
	public void saveCustomerOrder(Customer customer, CustomerOrder customerOrder) {
		
		customer.setRegister_date(new Date(System.currentTimeMillis()));
		customer.setActive_date(new Date(System.currentTimeMillis()));
		
		this.customerMapper.insertCustomer(customer);
		
		if ("business".equals(customer.getCustomer_type())) {
			customer.getOrganization().setCustomer_id(customer.getId());
			this.organizationMapper.insertOrganization(customer.getOrganization());
			customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() * 1.15);
		}
		customerOrder.setCustomer_id(customer.getId());
		
		this.customerOrderMapper.insertCustomerOrder(customerOrder);
		
		for (CustomerOrderDetail cod : customerOrder.getCustomerOrderDetails()) {
			cod.setOrder_id(customerOrder.getId());
			this.customerOrderDetailMapper.insertCustomerOrderDetail(cod);
		}
	}
	
	@Transactional
	public void customerTopup(Customer customer, CustomerTransaction customerTransaction) {
		this.customerMapper.updateCustomer(customer);
		this.customerTransactionMapper.insertCustomerTransaction(customerTransaction);
	}
	
	@Transactional
	public int queryExistCustomer(Customer customer) {
		return this.customerMapper.selectExistCustomer(customer);
	}

	@Transactional
	public Customer queryCustomer(Customer customer) {
		return this.customerMapper.selectCustomer(customer);
	}

	@Transactional
	public Customer queryCustomerById(int id) {
		return this.customerMapper.selectCustomerById(id);
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
		return this.customerMapper.selectCustomer(customer);
	}
	
	@Transactional
	public List<CustomerOrder> queryCustomerOrdersByCustomerId(int customer_id) {
		return this.customerOrderMapper.selectCustomerOrdersByCustomerId(customer_id);
	}
	
	@Transactional
	public CustomerOrder queryCustomerOrder(CustomerOrder customerOrder) {
		return this.customerOrderMapper.selectCustomerOrder(customerOrder);
	}
	
	@Transactional
	public List<CustomerOrder> queryCustomerOrders(CustomerOrder customerOrder) {
		return this.customerOrderMapper.selectCustomerOrders(customerOrder);
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
	public void editCustomer(Customer customer) {
		this.customerMapper.updateCustomer(customer);
		if ("business".equals(customer.getCustomer_type())) {
			customer.getOrganization().getParams().put("customer_id", customer.getId());
			this.organizationMapper.updateOrganization(customer.getOrganization());
		}
	}

	@Transactional
	public void removeCustomer(int id) {
		this.customerMapper.deleteCustomerById(id);
		
		// BEGIN delete order area
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("customer_id", id);
		// get order id
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrders(co);
		for (CustomerOrder customerOrder : cos) {
			// delete order related details
			this.customerOrderDetailMapper.deleteCustomerOrderDetailByOrderId(customerOrder.getId());
		}
		// delete order
		this.customerOrderMapper.deleteCustomerOrderByCustomerId(id);
		// END delete order area
		
		// BEGIN delete invoice area
		CustomerInvoice ci = new CustomerInvoice();
		ci.getParams().put("customer_id", id);
		// get invoice id
		List<CustomerInvoice> cis = this.customerInvoiceMapper.selectCustomerInvoices(ci);
		for (CustomerInvoice customerInvoice : cis) {
			// delete invoice related details
			this.customerInvoiceDetailMapper.deleteCustomerInvoiceDetailByInvoiceId(customerInvoice.getId());
		}
		// delete invoice
		this.customerInvoiceMapper.deleteCustomerInvoiceByCustomerId(id);
		// END delete invoice area
		
		// delete transaction
		this.customerTransactionMapper.deleteCustomerTransactionByCustomerId(id);
		
		// delete related organization
		this.organizationMapper.deleteOrganizationByCustomerId(id);
		
	}

	@Transactional
	public void createCustomer(Customer customer) {
		this.customerMapper.insertCustomer(customer);
		if ("business".equals(customer.getCustomer_type())) {
			customer.getOrganization().setCustomer_id(customer.getId());
			this.organizationMapper.insertOrganization(customer.getOrganization());
		}
	}

	@Transactional
	public void createContactUs(ContactUs contactUs) {
		this.contactUsMapper.insertContactUs(contactUs);
	}

	@Transactional
	public void createCustomerOrderDetail(CustomerOrderDetail customerOrderDetail) {
		this.customerOrderDetailMapper.insertCustomerOrderDetail(customerOrderDetail);
	}

	@Transactional
	public void removeCustomerOrderDetailById(int id) {
		this.customerOrderDetailMapper.deleteCustomerOrderDetailById(id);
	}

	@Transactional
	public void editCustomerOrder(CustomerOrder customerOrder, ProvisionLog proLog) {
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
		// insert provision
		this.provisionLogMapper.insertProvisionLog(proLog);
	}
	
	@Transactional
	public void editCustomerOrder(CustomerOrder customerOrder) {
		// edit order
		this.customerOrderMapper.updateCustomerOrder(customerOrder);
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
	public void editCustomerOrderDetail(
			CustomerOrderDetail cod) {
		// edit order detail
		this.customerOrderDetailMapper.updateCustomerOrderDetail(cod);
		
	}
	
	@Transactional
	public void editOrganization(Organization org){
		this.organizationMapper.updateOrganization(org);
	}

	@Transactional 
	public String queryCustomerInvoiceFilePathById(int id){
		return this.customerInvoiceMapper.selectCustomerInvoiceFilePathById(id);
	}
	
	/*
	 * Notification BEGIN
	 */
	
	@Transactional
	public Notification queryNotificationBySort(String sort, String type){
		return this.notificationMapper.selectNotificationBySort(sort, type);
	}
	
	/*
	 * Notification END
	 */

	/*
	 * CompanyDetail BEGIN
	 */
	
	@Transactional
	public CompanyDetail queryCompanyDetail(){
		return this.companyDetailMapper.selectCompanyDetail();
	}
	
	/*
	 * CompanyDetail END
	 */
	
	
	/*
	 * customer invoice
	 * */
	public Double queryCustomerInvoicesBalanceByCid(int cid, String status) {
		return this.customerInvoiceMapper.selectCustomerInvoicesBalanceByCidAndStatus(cid, status);
	}
	
	public CustomerInvoice queryCustomerInvoiceById(int id){
		return this.customerInvoiceMapper.selectCustomerInvoiceById(id);
	}
	
	@Transactional
	public void editCustomerInvoice(CustomerInvoice customerInvoice){
		this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);
	}
	/*
	 * end customer invoice
	 * */
	
	// manually generating invoice PDF by id
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
		
		// initialize invoice's important informations
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(customer);
		invoicePDF.setCurrentCustomerInvoice(customerInvoice);

		// set file path
		try {
			// generate invoice PDF
			customerInvoice.setInvoice_pdf_path(invoicePDF.create());
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		// add sql condition: id
		customerInvoice.getParams().put("id", customerInvoice.getId());
		this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);
		// invoice PDF generator manipulation end
	}
	
	@Transactional
	public void createInvoicePDF(CustomerOrder customerOrder
			,Notification notificationEmail
			,Notification notificationSMS) {

		customerOrder = this.customerOrderMapper.selectCustomerOrderById(customerOrder.getId());
		createInvoicePDFBoth(customerOrder,notificationEmail,notificationSMS, false);	// false : first invoice
	}

	@Transactional 
	public void createNextInvoice(CustomerOrder customerOrderParam) throws ParseException{
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("invoice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("invoice", "sms");
		List<CustomerOrder> customerOrders = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrderParam);

		for (CustomerOrder customerOrder : customerOrders) {
			//System.out.println(customerOrder.getId() + ":" + customerOrder.getNext_invoice_create_date());
			createInvoicePDFBoth(customerOrder,notificationEmail,notificationSMS, true);	// true : next invoice
		}
		
		// BEGIN TOPUP NOTIFICATION
		CustomerOrder topupCustomerOrder = new CustomerOrder();
		topupCustomerOrder.getParams().put("where", "query_topup");
		topupCustomerOrder.getParams().put("order_status", "using");
		topupCustomerOrder.getParams().put("order_type_topup", "order-topup");
		Calendar cal = Calendar.getInstance();

        // using new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-13") under testing environment
		// using new Date() under production environment
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		topupCustomerOrder.getParams().put("order_due_backward_one", cal.getTime());
		cal.add(Calendar.DATE, 1);
		topupCustomerOrder.getParams().put("order_due_backward_two", cal.getTime());
		List<CustomerOrder> topupCustomerOrders = this.customerOrderMapper.selectCustomerOrdersBySome(topupCustomerOrder);
		
		for (CustomerOrder customerOrder : topupCustomerOrders) {
			CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
			Customer c = customerOrder.getCustomer();
			Notification topupNotificationEmail = this.notificationMapper.selectNotificationBySort("topup-notification", "email");
			Notification topupNotificationSMS = this.notificationMapper.selectNotificationBySort("topup-notification", "sms");

			// call mail at value retriever
			TMUtils.mailAtValueRetriever(topupNotificationEmail, c, customerOrder, companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(c.getEmail());
			applicationEmail.setSubject(topupNotificationEmail.getTitle());
			applicationEmail.setContent(topupNotificationEmail.getContent());
			// binding attachment name & path to email
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			TMUtils.mailAtValueRetriever(topupNotificationSMS, c, customerOrder, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(c, topupNotificationSMS);
		}
		// END TOPUP NOTIFICATION
		
	}

	@Transactional 
	public void createTermPlanInvoice() throws ParseException{
		
		// Production environment should edit as shown below
		// where = query_term
		// status = using
		// order_type_term = order-term
		CustomerOrder coTemp = new CustomerOrder();
		coTemp.getParams().put("where", "query_term");
		coTemp.getParams().put("status", "pending");
		coTemp.getParams().put("order_type", "order-term"); 
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrders(coTemp);
		
		// If found any order(s)
		if(cos.size() > 0){
			
			for (CustomerOrder co : cos) {
				// BEGIN get usable beans
				// Customer
				Customer c = co.getCustomer();
				// Previous invoice's preparation
				CustomerInvoice cpi = new CustomerInvoice();
				cpi.getParams().put("where", "by_max_id");
				cpi.getParams().put("customer_id", c.getId());
				cpi.getParams().put("order_id", co.getId());
				// Previous invoice
				cpi = this.customerInvoiceMapper.selectCustomerInvoice(cpi);
				// Current invoice
				CustomerInvoice ci = new CustomerInvoice();
				// Current invoice detail
				List<CustomerInvoiceDetail> cids = new ArrayList<CustomerInvoiceDetail>();
				// END get usable beans

				// If current order don't have any invoice then true, else false
				// Production environment should edit as shown below
				// {true} change to {cpi == null ? true : false;}
				boolean isFirst = //true;
						cpi == null ? true : false;
				// If service giving is gave by this month then true, else false
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				// Production environment should edit as shown below
				// {true} change to {below long statement}
				boolean isServedCurrentMonth = false; 
						//co.getOrder_using_start() != null ? sdf.format(co.getOrder_using_start()).equals(sdf.format(new Date())) : false;
				
				ci.setCustomer_id(c.getId());
				ci.setOrder_id(co.getId());
				ci.setAmount_paid(0d);
				this.customerInvoiceMapper.insertCustomerInvoice(ci);
				// Set id for invoice's update
				ci.getParams().put("id", ci.getId());
				ci.setCreate_date(new Date());
				ci.setDue_date(Calculation4PlanTermInvoice.getInvoiceDueDate());
				ci.setStatus("unpaid");

				// Preparing for calculations, invoice's payable amount
				BigDecimal bigPayableAmount = new BigDecimal(0d);
				
				for (CustomerOrderDetail cod : co.getCustomerOrderDetails()) {
					CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
					
					// If first invoice then add all order details into invoice details
					if(isFirst){
						
						if("plan-term".equals(cod.getDetail_type())){
							
							// Get served month's term plan's remaining charges 
							// Production environment should edit as shown below
							// {new Date()} change to {co.getOrder_using_start()}
							Map<String, Object> resultMap = Calculation4PlanTermInvoice.servedMonthDetails(new Date()
									//co.getOrder_using_start()
									, cod.getDetail_price());
							cid.setInvoice_detail_name(cod.getDetail_name()+" ("+resultMap.get("remainingDays")+" day(s) counting from service start date to end of month)");
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							cid.setInvoice_detail_price((Double)resultMap.get("totalPrice"));
							
							// Preparing for calculations, term plan's remaining price
							BigDecimal bigRemainingPrice = new BigDecimal((Double)resultMap.get("totalPrice"));
							// Add remaining price into payable amount
							bigPayableAmount = bigPayableAmount.add(bigRemainingPrice);
							
							cids.add(cid);
							
							// If is not served in current month, then add plan-term type detail into invoice detail
							if (!isServedCurrentMonth) {
								
								// Reconstruct invoice's detail
								cid = new CustomerInvoiceDetail();
								
								cid.setInvoice_detail_name(cod.getDetail_name());
								cid.setInvoice_detail_unit(cod.getDetail_unit());
								cid.setInvoice_detail_price(cod.getDetail_price());
								
								// Preparing for calculations, term plan's monthly price
								BigDecimal bigMonthlyPrice = new BigDecimal(cod.getDetail_price());
								// Add monthly price into payable amount
								bigPayableAmount = bigPayableAmount.add(bigMonthlyPrice);
								
								cids.add(cid);
							}

						// Else if discount and unexpired then do add discount
						} else if("discount".equals(cod.getDetail_type()) && cod.getDetail_expired().getTime() >= System.currentTimeMillis()){
							
							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_discount(cod.getDetail_price());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							
							// Preparing for calculations, term plan's discount price
							BigDecimal bigDiscountPrice = new BigDecimal(cod.getDetail_price());
							BigDecimal bigDiscountUnit = new BigDecimal(cod.getDetail_unit());
							
							// Payable amount minus ( discount price times discount unit )
							bigPayableAmount = bigPayableAmount.subtract(bigDiscountPrice.multiply(bigDiscountUnit));
							
							cids.add(cid);
							
						// Else add all non plan-term type details into invoice details
						} else {

							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_price(cod.getDetail_price());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							
							// Preparing for calculations, invoice detail's unit price
							BigDecimal bigDetailPrice = new BigDecimal(cod.getDetail_price());
							BigDecimal bigDetailUnit = new BigDecimal(cod.getDetail_unit());

							// Payable amount plus ( detail price times detail unit )
							bigPayableAmount = bigPayableAmount.add(bigDetailPrice.multiply(bigDetailUnit));

							cids.add(cid);
						}
						
					// Else not first invoice, add unexpired order detail(s) into invoice detail(s)
					} else {
						
						// Add previous invoice's details into
						ci.setLast_invoice_id(cpi.getId());
						ci.setLastCustomerInvoice(cpi);

						// If plan-term type, then add detail into invoice detail
						if("plan-term".equals(cod.getDetail_type())){
							
							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							cid.setInvoice_detail_price(cod.getDetail_price());
							
							// Preparing for calculations, term plan's monthly price
							BigDecimal bigMonthlyPrice = new BigDecimal(cod.getDetail_price());
							// Add monthly price into payable amount
							
							bigPayableAmount = bigPayableAmount.add(bigMonthlyPrice);
							
							cids.add(cid);

						// Else if discount and unexpired then do add discount
						} else if("discount".equals(cod.getDetail_type()) && cod.getDetail_expired().getTime() >= System.currentTimeMillis()){
							
							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_discount(cod.getDetail_price());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							
							// Preparing for calculations, term plan's discount price
							BigDecimal bigDiscountPrice = new BigDecimal(cod.getDetail_price());
							BigDecimal bigDiscountUnit = new BigDecimal(cod.getDetail_unit());
							
							// Payable amount minus ( discount price times discount unit )
							bigPayableAmount = bigPayableAmount.subtract(bigDiscountPrice.multiply(bigDiscountUnit));
							
							cids.add(cid);

						// Else if unexpired then add order detail(s) into invoice detail(s)
						} else if(cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {

							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_price(cod.getDetail_price());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							
							// Preparing for calculations, invoice detail's unit price
							BigDecimal bigDetailPrice = new BigDecimal(cod.getDetail_price());
							BigDecimal bigDetailUnit = new BigDecimal(cod.getDetail_unit());

							// Payable amount plus ( detail price times detail unit )
							bigPayableAmount = bigPayableAmount.add(bigDetailPrice.multiply(bigDetailUnit));

							cids.add(cid);
						}
						
					}
				}
				if(!isFirst){
					// Add previous invoice's balance
					BigDecimal bigPreviousInvoiceBalance = new BigDecimal(cpi.getBalance());
					bigPayableAmount = bigPayableAmount.add(bigPreviousInvoiceBalance);
				}
				
				// Set invoice details into 
				ci.setCustomerInvoiceDetails(cids);
				
				// Set current invoice's payable amount
				ci.setAmount_payable(bigPayableAmount.doubleValue());
				
				// Set current invoice's balance = ( payable - paid )
				BigDecimal bigBalance = new BigDecimal(ci.getBalance() != null ? ci.getBalance() : 0d);
				BigDecimal bigPaidAmount = new BigDecimal(ci.getAmount_paid() != null ? ci.getAmount_paid() : 0d);
				bigBalance = bigBalance.add(bigPayableAmount.subtract(bigPaidAmount));
				ci.setBalance(bigBalance.doubleValue());
				
				// Updates invoice
				this.customerInvoiceMapper.updateCustomerInvoice(ci);
				
				// Iteratively inserting invoice detail(s) into tm_invoice_detail table
				for (CustomerInvoiceDetail cid : cids) {
					cid.setInvoice_id(ci.getId());
					this.customerInvoiceDetailMapper.insertCustomerInvoiceDetail(cid);
				}
				

				// store company detail begin
				CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
				// store company detail end

				InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
				invoicePDF.setCompanyDetail(companyDetail);
				invoicePDF.setCustomer(c);
				invoicePDF.setCurrentCustomerInvoice(ci);

				String filePath = "";
				// set file path
				try {
					// generate invoice PDF
					filePath = invoicePDF.create();
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
				ci.setInvoice_pdf_path(filePath);
				
				this.customerInvoiceMapper.updateCustomerInvoice(ci);

//				Notification notificationEmail = this.notificationMapper.selectNotificationBySort("plan-term_invoice", "email");
//				Notification notificationSMS = this.notificationMapper.selectNotificationBySort("plan-term_invoice", "sms");
//
//				// call mail at value retriever
//				TMUtils.mailAtValueRetriever(notificationEmail, c, ci, companyDetail);
//				ApplicationEmail applicationEmail = new ApplicationEmail();
//				applicationEmail.setAddressee(c.getEmail());
//				applicationEmail.setSubject(notificationEmail.getTitle());
//				applicationEmail.setContent(notificationEmail.getContent());
//				// binding attachment name & path to email
//				applicationEmail.setAttachName("invoice_" + ci.getId() + ".pdf");
//				applicationEmail.setAttachPath(filePath);
//				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//
//				// get sms register template from db
//				TMUtils.mailAtValueRetriever(notificationSMS, c, ci, companyDetail);
//				// send sms to customer's mobile phone
//				this.smserService.sendSMSByAsynchronousMode(c, notificationSMS);
			}
		}
	}
	
	
	@Transactional
	public void createInvoicePDFBoth(CustomerOrder customerOrder
			,Notification notificationEmail
			,Notification notificationSMS, Boolean is_Next_Invoice){
		// store company detail begin
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		// store company detail end

		/*
		 * get specific models begin
		 */
		// initiate models begin
		// current customer order model
		//CustomerOrder customerOrder = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrderParam).get(0);

		// customer model
		Customer customer = customerOrder.getCustomer();
		// current invoice model
		CustomerInvoice customerInvoice = new CustomerInvoice();
		// previous invoice model
		CustomerInvoice customerPreviousInvoice = new CustomerInvoice();
		customerPreviousInvoice.getParams().put("where", "by_max_id");
		customerPreviousInvoice.getParams().put("customer_id", customer.getId());
		customerPreviousInvoice.getParams().put("order_id", customerOrder.getId());
		
		// customer previous invoice
		customerPreviousInvoice = this.customerInvoiceMapper.selectCustomerInvoice(customerPreviousInvoice);
		if (customerPreviousInvoice != null ) {	
			// if status is not paid then update the status to discard
			if (!"paid".equals(customerPreviousInvoice.getStatus())) {
				customerPreviousInvoice.setStatus("discard");
				customerPreviousInvoice.getParams().put("id", customerPreviousInvoice.getId());
				this.customerInvoiceMapper.updateCustomerInvoice(customerPreviousInvoice);
			}
			customerInvoice.setLast_invoice_id(customerPreviousInvoice.getId());
			customerInvoice.setLastCustomerInvoice(customerPreviousInvoice);
		}
		// initiate models end
		/*
		 * get specific models end
		 */

		// set customer's new invoice details begin

		// set invoice create date begin

		/*
		 * 
		 * invoiceCreateDay HAVE TO ASSIGN TO new Date() BEFORE PRODUCT MODE
		 */
		// if first time then judge if using start not null then using start else new Date(), else next create date.
		Date invoiceCreateDay = !is_Next_Invoice 
				? (customerOrder.getOrder_using_start() != null 
						? customerOrder.getOrder_using_start() 
						: new Date()) 
				: customerOrder.getNext_invoice_create_date();
		
		// set invoice due date begin
		int invoiceDueDay = 10;
		Calendar calInvoiceDueDay = Calendar.getInstance();
		calInvoiceDueDay.setTime(invoiceCreateDay);
		calInvoiceDueDay.add(Calendar.DAY_OF_MONTH, invoiceDueDay);
		// set invoice due date end
		/*
		 * set next invoice date begin
		 */

		customerInvoice.setCustomer_id(customer.getId());
		customerInvoice.setOrder_id(customerOrder.getId());
		customerInvoice.setCreate_date(invoiceCreateDay);
		customerInvoice.setDue_date(calInvoiceDueDay.getTime());
		// detail holder begin
		Double amountPayable = 0d;
		List<CustomerInvoiceDetail> customerInvoiceDetailList = new ArrayList<CustomerInvoiceDetail>();
		// detail holder end
		List<CustomerOrderDetail> customerOrderDetails = customerOrder.getCustomerOrderDetails();
		
		if (customerOrderDetails != null) {
			for (CustomerOrderDetail cod: customerOrderDetails) {
				
				CustomerInvoiceDetail customerInvoiceDetail = new CustomerInvoiceDetail();
				customerInvoiceDetail.setInvoice_id(customerInvoice.getId());
				customerInvoiceDetail.setInvoice_detail_name(cod.getDetail_name());
				customerInvoiceDetail.setInvoice_detail_unit(cod.getDetail_unit() == null ? 1 : cod.getDetail_unit());

				// if detail type equals discount and detail expire date greater
				// equal than today's date then go into if statement
				if ("discount".equals(cod.getDetail_type())
						&& cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {
					
					customerInvoiceDetail.setInvoice_detail_discount(cod.getDetail_price());
					// decrease amountPayable
					amountPayable -= customerInvoiceDetail.getInvoice_detail_discount() * customerInvoiceDetail.getInvoice_detail_unit();
					// add invoice detail to list
					customerInvoiceDetailList.add(customerInvoiceDetail);
					// else if detail type is discount then this discount is expired
					// and will not be add to the invoice detail list
				} else if (!"discount".equals(cod.getDetail_type())) {
					if (cod.getDetail_expired()==null) {
						// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
						customerInvoiceDetail.setInvoice_detail_unit(cod.getDetail_unit() != null && !is_Next_Invoice ? cod.getDetail_unit() : 1);
						customerInvoiceDetail.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
						// increase amountPayable
						amountPayable += customerInvoiceDetail.getInvoice_detail_price() != null ? customerInvoiceDetail.getInvoice_detail_price() * customerInvoiceDetail.getInvoice_detail_unit() : 0;
						// add invoice detail to list
						customerInvoiceDetailList.add(customerInvoiceDetail);
					}
					if (cod.getDetail_type()!=null && cod.getDetail_type().contains("plan-") 
							&& !"plan-topup".equals(cod.getDetail_type())) {
						
						// get next invoice date
						// if is next invoice then plus one month else plus unit month(s)
						int nextInvoiceMonth = is_Next_Invoice ? 1 : cod.getDetail_unit();
						int nextInvoiceDay = is_Next_Invoice ? 0 : -15;
						Calendar calNextInvoiceDay = Calendar.getInstance();
						calNextInvoiceDay.setTime(!is_Next_Invoice 
									? (customerOrder.getOrder_using_start() != null 
									? customerOrder.getOrder_using_start() 
									: new Date()) 
							: customerOrder.getNext_invoice_create_date());
						calNextInvoiceDay.add(Calendar.MONTH, nextInvoiceMonth);
						calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);

						// update customer order's next invoice create day begin
						customerOrder.setNext_invoice_create_date(calNextInvoiceDay.getTime());
						customerOrder.getParams().put("id", customerOrder.getId());
						
						this.customerOrderMapper.updateCustomerOrder(customerOrder);
						// update customer order's next invoice create day end
						/*
						 * set next invoice date end
						 */
					} else {
						CustomerOrderDetail codTemp = new CustomerOrderDetail();
						codTemp.setDetail_expired(new Date());
						codTemp.getParams().put("id", cod.getId());
						this.customerOrderDetailMapper.updateCustomerOrderDetail(codTemp);
					}
				}
			}
		}
		customerInvoice.setAmount_payable(amountPayable + (customerPreviousInvoice != null && customerPreviousInvoice.getBalance() != null ? customerPreviousInvoice.getBalance() : 0));
		customerInvoice.setStatus("unpaid");
		customerInvoice.setAmount_paid(0d);
		
		BigDecimal currentTotalPayable = new BigDecimal(String.valueOf((amountPayable + (customerPreviousInvoice != null && customerPreviousInvoice.getBalance() != null ? customerPreviousInvoice.getBalance() : 0))));
		BigDecimal currentAmountPaid = new BigDecimal(String.valueOf(customerInvoice.getAmount_paid()));
		// balance = payable - paid
		customerInvoice.setBalance(currentTotalPayable.subtract(currentAmountPaid).doubleValue());
		// set customer's new invoice details end

		// get generated key while performing insertion
		this.customerInvoiceMapper.insertCustomerInvoice(customerInvoice);

		// reset customer invoice from customer invoice got by invoice_id
		//customerInvoice = this.customerInvoiceMapper.selectCustomerInvoiceById(customerInvoice.getId());

		// inserting customer invoice detail iteratively
		for (CustomerInvoiceDetail cid : customerInvoiceDetailList) {
			cid.setInvoice_id(customerInvoice.getId());
			this.customerInvoiceDetailMapper.insertCustomerInvoiceDetail(cid);
		}

		// relatively setting invoice details into invoice
		customerInvoice.setCustomerInvoiceDetails(customerInvoiceDetailList);

		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(customer);
		invoicePDF.setCurrentCustomerInvoice(customerInvoice);

		String filePath = "";
		// set file path
		try {
			// generate invoice PDF
			filePath = invoicePDF.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		customerInvoice.setInvoice_pdf_path(filePath);
		// add sql condition: id
		customerInvoice.getParams().put("id", customerInvoice.getId());
		this.customerInvoiceMapper.updateCustomerInvoice(customerInvoice);


		// call mail at value retriever
		TMUtils.mailAtValueRetriever(notificationEmail, customer, customerInvoice, companyDetail);
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notificationEmail.getTitle());
		applicationEmail.setContent(notificationEmail.getContent());
		// binding attachment name & path to email
		applicationEmail.setAttachName("invoice_" + customerInvoice.getId() + ".pdf");
		applicationEmail.setAttachPath(filePath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);

		// get sms register template from db
		TMUtils.mailAtValueRetriever(notificationSMS, customer, customerInvoice, companyDetail);
		// send sms to customer's mobile phone
		this.smserService.sendSMSByAsynchronousMode(customer, notificationSMS);
	}
	
	/**
	 * BEGIN CustomerOrderDetail
	 */
	
	public String queryCustomerOrderDetailGroupByOrderId(int order_id) {
		List<CustomerOrderDetail> customerOrderDetails = this.customerOrderDetailMapper.selectCustomerOrderDetailsByOrderId(order_id);
		for (CustomerOrderDetail customerOrderDetail : customerOrderDetails) {
			if(customerOrderDetail.getDetail_plan_group()!=null && customerOrderDetail.getDetail_plan_group().indexOf("plan-")>-1){
				return customerOrderDetail.getDetail_plan_group();
			}
		}
		return "";
	}
	
	/**
	 * END CustomerOrderDetail
	 */
	
	
	/**
	 * BEGIN CustomerTransaction
	 */
	
	@Transactional
	public void createCustomerTransaction(CustomerTransaction customerTransaction){
		this.customerTransactionMapper.insertCustomerTransaction(customerTransaction);
	}
	
	/**
	 * END CustomerTransaction
	 */
	
	
	/**
	 * BEGIN Organization
	 */
	
	@Transactional
	public Organization queryOrganizationByCustomerId(int customer_id){
		return this.organizationMapper.selectOrganizationByCustomerId(customer_id);
	}
	
	@Transactional
	public void createOrganization(Organization org){
		this.organizationMapper.insertOrganization(org);
	}
	
	/**
	 * END Organization
	 */
}

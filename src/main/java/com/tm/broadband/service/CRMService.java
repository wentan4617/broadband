package com.tm.broadband.service;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CompanyDetailMapper;
import com.tm.broadband.mapper.ContactUsMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.CustomerCallingRecordCallplusMapper;
import com.tm.broadband.mapper.CustomerInvoiceDetailMapper;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderDetailMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.CustomerServiceRecordMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeParameterMapper;
import com.tm.broadband.mapper.ManualDefrayLogMapper;
import com.tm.broadband.mapper.NotificationMapper;
import com.tm.broadband.mapper.OrganizationMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.mapper.TerminationRefundMapper;
import com.tm.broadband.mapper.TicketCommentMapper;
import com.tm.broadband.mapper.TicketMapper;
import com.tm.broadband.mapper.VoucherMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerServiceRecord;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.ManualDefrayLog;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.Ticket;
import com.tm.broadband.model.TicketComment;
import com.tm.broadband.model.User;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.pdf.EarlyTerminationChargePDFCreator;
import com.tm.broadband.pdf.InvoicePDFCreator;
import com.tm.broadband.pdf.OrderingPDFCreator;
import com.tm.broadband.pdf.ReceiptPDFCreator;
import com.tm.broadband.pdf.TerminationRefundPDFCreator;
import com.tm.broadband.util.Calculation4PlanTermInvoice;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;

/**
 * CRM service
 * 
 * @author Cook1fan, Don Chen
 * 
 */
@Service
public class CRMService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private CustomerOrderDetailMapper customerOrderDetailMapper;
	private CustomerInvoiceMapper ciMapper;
	private CustomerInvoiceDetailMapper ciDetailMapper;
	private CustomerTransactionMapper customerTransactionMapper;
	private ProvisionLogMapper provisionLogMapper;
	private CompanyDetailMapper companyDetailMapper;
	private NotificationMapper notificationMapper;
	private OrganizationMapper organizationMapper;
	private ContactUsMapper contactUsMapper;
	private ManualDefrayLogMapper manualDefrayLogMapper;
	private CustomerCallRecordMapper customerCallRecordMapper;
	private CallInternationalRateMapper callInternationalRateMapper;
	private EarlyTerminationChargeMapper earlyTerminationChargeMapper;
	private EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper;
	private TerminationRefundMapper terminationRefundMapper;
	private CustomerServiceRecordMapper customerServiceRecordMapper;
	private VoucherMapper voucherMapper;
	private CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper;
	private TicketMapper ticketMapper;
	private TicketCommentMapper ticketCommentMapper;
	
	// service
	private MailerService mailerService;
	private SmserService smserService;

	public CRMService() { }
	
	@Autowired
	public CRMService(CustomerMapper customerMapper,
			CustomerOrderMapper customerOrderMapper,
			CustomerOrderDetailMapper customerOrderDetailMapper,
			CustomerInvoiceMapper ciMapper,
			CustomerInvoiceDetailMapper ciDetailMapper,
			CustomerTransactionMapper customerTransactionMapper,
			ProvisionLogMapper provisionLogMapper,
			CompanyDetailMapper companyDetailMapper,
			MailerService mailerService,
			NotificationMapper notificationMapper,
			SmserService smserService,
			OrganizationMapper organizationMapper,
			ContactUsMapper contactUsMapper,
			ManualDefrayLogMapper manualDefrayLogMapper,
			CustomerCallRecordMapper customerCallRecordMapper,
			CallInternationalRateMapper callInternationalRateMapper,
			EarlyTerminationChargeMapper earlyTerminationChargeMapper,
			EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper,
			TerminationRefundMapper terminationRefundMapper,
			CustomerServiceRecordMapper customerServiceRecordMapper,
			VoucherMapper voucherMapper,
			CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper,
			TicketMapper ticketMapper,
			TicketCommentMapper ticketCommentMapper){
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.customerOrderDetailMapper = customerOrderDetailMapper;
		this.customerTransactionMapper = customerTransactionMapper;
		this.ciMapper = ciMapper;
		this.ciDetailMapper = ciDetailMapper;
		this.provisionLogMapper = provisionLogMapper;
		this.companyDetailMapper = companyDetailMapper;
		this.mailerService = mailerService;
		this.notificationMapper = notificationMapper;
		this.smserService = smserService;
		this.organizationMapper = organizationMapper;
		this.contactUsMapper = contactUsMapper;
		this.manualDefrayLogMapper = manualDefrayLogMapper;
		this.customerCallRecordMapper = customerCallRecordMapper;
		this.callInternationalRateMapper = callInternationalRateMapper;
		this.earlyTerminationChargeMapper = earlyTerminationChargeMapper;
		this.earlyTerminationChargeParameterMapper = earlyTerminationChargeParameterMapper;
		this.terminationRefundMapper = terminationRefundMapper;
		this.customerServiceRecordMapper = customerServiceRecordMapper;
		this.voucherMapper = voucherMapper;
		this.customerCallingRecordCallplusMapper = customerCallingRecordCallplusMapper;
		this.ticketMapper = ticketMapper;
		this.ticketCommentMapper = ticketCommentMapper;
	}
	
	
	public void doOrderConfirm(Customer customer, Plan plan) {
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().setOrder_create_date(new Date());
		//customer.getCustomerOrder().setOrder_status("paid");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		cod_plan.setDetail_name(plan.getPlan_name());
		//cod_plan.setDetail_desc(plan.getPlan_desc());
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
			
			//cod_plan.setDetail_is_next_pay(0);
			//cod_plan.setDetail_expired(new Date());
			
			customer.getCustomerOrder().setOrder_total_price(plan.getTopup().getTopup_fee());
			
			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
			cod_topup.setDetail_name("Broadband Top-Up");
			cod_topup.setDetail_price(plan.getTopup().getTopup_fee());
			cod_topup.setDetail_type("topup");
			//cod_topup.setDetail_is_next_pay(0);
			//cod_topup.setDetail_expired(new Date());
			cod_topup.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_topup);
			
		} else if ("plan-no-term".equals(plan.getPlan_group())) {
			
			customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			System.out.println("Order_total_price: " + customer.getCustomerOrder().getOrder_total_price());
			
			//cod_plan.setDetail_is_next_pay(1);
			
		} else if ("plan-term".equals(plan.getPlan_group())) {
			
			if ("personal".equals(plan.getPlan_class())) {
				customer.getCustomerOrder().setOrder_status("paid");
			} else if ("business".equals(plan.getPlan_class())) {
				customer.getCustomerOrder().setOrder_status("pending");
			}
			
			customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			if ("ADSL".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N ADSL2+ Modem Router(Free)");
			} else if ("VDSL".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N VDSL2+ Modem Router(Free)");
			} else if ("UFB".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("UFB Modem Router(Free)");
			}
			
			cod_hd.setDetail_price(0d);
			//cod_hd.setDetail_is_next_pay(0);
			//cod_hd.setDetail_expired(new Date());
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
				//cod_trans.setDetail_is_next_pay(1);
			} else {
				//cod_trans.setDetail_is_next_pay(0);
				//cod_trans.setDetail_expired(new Date());
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
				//cod_conn.setDetail_is_next_pay(1);
			} else {
				//cod_conn.setDetail_is_next_pay(0);
				//cod_conn.setDetail_expired(new Date());
			}
			cod_conn.setDetail_type("new-connection");
			cod_conn.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);
			
		} else if ("jackpot".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
			
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getJackpot_fee());
			
			CustomerOrderDetail cod_jackpot = new CustomerOrderDetail();
			cod_jackpot.setDetail_name("Broadband New Connection & Phone Jack Installation");
			cod_jackpot.setDetail_price(plan.getJackpot_fee());
			if ("plan-term".equals(plan.getPlan_group())) {
				//cod_jackpot.setDetail_is_next_pay(1);
			} else {
				//cod_jackpot.setDetail_is_next_pay(0);
				//cod_jackpot.setDetail_expired(new Date());
			}
			cod_jackpot.setDetail_type("jackpot");
			cod_jackpot.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_jackpot);
		} 
		
		// add plan free pstn
		for (int i = 0; i < plan.getPstn_count(); i++) {
			CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
			if ("personal".equals(plan.getPlan_class())) {
				cod_pstn.setDetail_name("Home Phone Line");
			} else if ("business".equals(plan.getPlan_class())) {
				cod_pstn.setDetail_name("Business Phone Line");
			}
			cod_pstn.setDetail_price(0d);
			//cod_pstn.setDetail_is_next_pay(0);
			//cod_pstn.setDetail_expired(new Date());
			cod_pstn.setDetail_type("pstn");
			cod_pstn.setDetail_unit(1);
			cod_pstn.setPstn_number(customer.getCustomerOrder().getTransition_porting_number());
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_pstn);
		}
		
		for (Hardware chd: customer.getCustomerOrder().getHardwares()) {
		
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			cod_hd.setDetail_name(chd.getHardware_name());
			cod_hd.setDetail_price(chd.getHardware_price());
			if ("plan-term".equals(plan.getPlan_group())) {
				//cod_hd.setDetail_is_next_pay(1);
			} else {
				//cod_hd.setDetail_is_next_pay(0);
				//cod_hd.setDetail_expired(new Date());
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
	public void registerCustomer(Customer customer, List<CustomerTransaction> cts) {
		
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
		
		/*CustomerInvoice ci = new CustomerInvoice();
		ci.setCustomer_id(customer.getId());
		ci.setOrder_id(customer.getCustomerOrder().getId());
		ci.setCreate_date(new Date(System.currentTimeMillis()));
		ci.setAmount_payable(customer.getCustomerOrder().getOrder_total_price());
		ci.setFinal_payable_amount(customer.getCustomerOrder().getOrder_total_price());*/
		
		/*if (cts != null) {
			Double amount = 0d;
			for (CustomerTransaction ct: cts) {
				amount += ct.getAmount();
			}
			
			if (amount >= ci.getAmount_payable())
				ci.setAmount_paid(ci.getAmount_payable());
			else 
				ci.setAmount_paid(amount);
		}
		
		ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getAmount_payable(), ci.getAmount_paid(), "sub"));
		ci.setStatus("paid");
		ci.setPaid_date(new Date(System.currentTimeMillis()));
		ci.setPaid_type(cts.get(0).getCard_name());
		
		this.ciMapper.insertCustomerInvoice(ci);
		customer.setCustomerInvoice(ci);*/
		
		for (CustomerOrderDetail cod : customer.getCustomerOrder().getCustomerOrderDetails()) {
			cod.setOrder_id(customer.getCustomerOrder().getId());
			this.customerOrderDetailMapper.insertCustomerOrderDetail(cod);
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			/*cid.setInvoice_id(ci.getId());
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_desc(cod.getDetail_desc());
			cid.setInvoice_detail_price(cod.getDetail_price());
			cid.setInvoice_detail_unit(cod.getDetail_unit());*/
			this.ciDetailMapper.insertCustomerInvoiceDetail(cid);
		}
		
		if (cts != null) {
			for (CustomerTransaction ct: cts) {
				ct.setCustomer_id(customer.getId());
				ct.setOrder_id(customer.getCustomerOrder().getId());
				//ct.setInvoice_id(ci.getId());
				ct.setTransaction_date(new Date(System.currentTimeMillis()));
				this.customerTransactionMapper.insertCustomerTransaction(ct);
			}
		}
		
		for (Voucher vQuery: customer.getVouchers()) {
			vQuery.setStatus("used");
			vQuery.setCustomer_id(customer.getId());
			vQuery.getParams().put("serial_number", vQuery.getSerial_number());
			vQuery.getParams().put("card_number", vQuery.getCard_number());
			this.voucherMapper.updateVoucher(vQuery);
		}
	}
	
	public void registerCustomerCalling(Customer customer) {
		
		customer.setRegister_date(new Date());
		customer.setActive_date(new Date());
		
		this.customerMapper.insertCustomer(customer);
		//System.out.println("customer id: " + customer.getId());
		
		if ("business".equals(customer.getCustomer_type())) {
			customer.getOrganization().setCustomer_id(customer.getId());
			this.organizationMapper.insertOrganization(customer.getOrganization());
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() * 1.15);
		}
		
		customer.getCustomerOrder().setCustomer_id(customer.getId());
		
		this.customerOrderMapper.insertCustomerOrder(customer.getCustomerOrder());
		//System.out.println("customer order id: " + customer.getCustomerOrder().getId());
		
		/*CustomerInvoice ci = new CustomerInvoice();
		ci.setCustomer_id(customer.getId());
		ci.setOrder_id(customer.getCustomerOrder().getId());
		ci.setCreate_date(new Date(System.currentTimeMillis()));
		ci.setAmount_payable(customer.getCustomerOrder().getOrder_total_price());
		ci.setFinal_payable_amount(customer.getCustomerOrder().getOrder_total_price());
		ci.setAmount_paid(0d);
		
		ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getAmount_payable(), ci.getAmount_paid(), "sub"));
		ci.setStatus("unpaid");
		
		this.ciMapper.insertCustomerInvoice(ci);
		customer.setCustomerInvoice(ci);*/
		
		for (CustomerOrderDetail cod : customer.getCustomerOrder().getCustomerOrderDetails()) {
			cod.setOrder_id(customer.getCustomerOrder().getId());
			this.customerOrderDetailMapper.insertCustomerOrderDetail(cod);
			/*CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			//cid.setInvoice_id(ci.getId());
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_desc(cod.getDetail_desc());
			cid.setInvoice_detail_price(cod.getDetail_price());
			cid.setInvoice_detail_unit(cod.getDetail_unit());
			this.ciDetailMapper.insertCustomerInvoiceDetail(cid);*/
		}
		
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
	public void customerOrderingForm(Customer customer, CustomerOrder order, CustomerTransaction customerTransaction) {
		this.customerMapper.updateCustomer(customer);
		this.customerOrderMapper.updateCustomerOrder(order);
		this.customerTransactionMapper.insertCustomerTransaction(customerTransaction);
	}
	
	@Transactional
	public void customerBalance(CustomerInvoice ci, CustomerTransaction customerTransaction) {
		this.ciMapper.updateCustomerInvoice(ci);
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
	public List<Customer> queryCustomers(Customer customer) {
		return this.customerMapper.selectCustomers(customer);
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
	public Page<CustomerInvoice> queryCustomerInvoicesByPage(Page<CustomerInvoice> page) {
		page.setTotalRecord(this.ciMapper.selectCustomerInvoicesSum(page));
		page.setResults(this.ciMapper.selectCustomerInvoicesByPage(page));
		return page;
	}
	
	@Transactional
	public int queryCustomerInvoicesSumByPage(Page<CustomerInvoice> page) {
		return this.ciMapper.selectCustomerInvoicesSum(page);
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
	public String queryCustomerOrderTypeById(int id) {
		return this.customerOrderMapper.selectCustomerOrderTypeById(id);
	}
	
	@Transactional
	public CustomerOrder queryCustomerOrderById(int id) {
		return this.customerOrderMapper.selectCustomerOrderById(id);
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
		List<CustomerInvoice> cis = this.ciMapper.selectCustomerInvoices(ci);
		for (CustomerInvoice ci2 : cis) {
			// delete invoice related details
			this.ciDetailMapper.deleteCustomerInvoiceDetailByInvoiceId(ci2.getId());
		}
		// delete invoice
		this.ciMapper.deleteCustomerInvoiceByCustomerId(id);
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
	public void createManualDefrayLog(ManualDefrayLog manualDefrayLog) {
		this.manualDefrayLogMapper.insertManualDefrayLog(manualDefrayLog);
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
		return this.ciMapper.selectCustomerInvoiceFilePathById(id);
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
	public void createCustomerInvoice(CustomerInvoice ci){
		this.ciMapper.insertCustomerInvoice(ci);
	}
	
	public CustomerInvoice queryCustomerInvoice(CustomerInvoice ci) {
		List<CustomerInvoice> cis = this.ciMapper.selectCustomerInvoices(ci);
		return cis!=null && cis.size()>0 ? cis.get(0) : null;
	}
	
	public Double queryCustomerInvoicesBalanceByCid(int cid, String status) {
		return this.ciMapper.selectCustomerInvoicesBalanceByCidAndStatus(cid, status);
	}
	
	public CustomerInvoice queryCustomerInvoiceById(int id){
		return this.ciMapper.selectCustomerInvoiceById(id);
	}
	
	@Transactional
	public void editCustomerInvoice(CustomerInvoice ci){
		this.ciMapper.updateCustomerInvoice(ci);
	}
	/*
	 * end customer invoice
	 * */
	
	/**
	 * BEGIN createOrderingForm
	 */
	public String createOrderingFormPDFByDetails(Customer c){
		// call OrderPDFCreator
		OrderingPDFCreator oPDFCreator = new OrderingPDFCreator();
		oPDFCreator.setCustomer(c);
		oPDFCreator.setOrg(c.getOrganization());
		oPDFCreator.setCustomerOrder(c.getCustomerOrder());
		
		String pdfPath = "";
		try {
			pdfPath = oPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", c.getCustomerOrder().getId());
		co.setOrdering_form_pdf_path(pdfPath);
		this.customerOrderMapper.updateCustomerOrder(co);
		
		return pdfPath;
	}
	/**
	 * END createOrderingForm
	 */

	/**
	 * BEGIN createReceipt
	 */
	public String createReceiptPDFByDetails(Customer c){
		// call OrderPDFCreator
		ReceiptPDFCreator rPDFCreator = new ReceiptPDFCreator();
		rPDFCreator.setCustomer(c);
		rPDFCreator.setOrg(c.getOrganization());
		rPDFCreator.setCustomerOrder(c.getCustomerOrder());

		String pdfPath = "";
		try {
			pdfPath = rPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", c.getCustomerOrder().getId());
		co.setReceipt_pdf_path(pdfPath);
		this.customerOrderMapper.updateCustomerOrder(co);
		
		return pdfPath;
	}
	/**
	 * END createReceipt
	 */
	
	// manually generating invoice PDF by id
	@Transactional
	public void createInvoicePDFByInvoiceID(int invoiceId, boolean isOverduePenalty){
		// store company details begin
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		// store company details end
		
		// invoice PDF generator manipulation begin
		// get necessary models begin
		// get last invoice, customer and current invoice it self at the same time by using left join
		CustomerInvoice ci = this.ciMapper.selectInvoiceWithLastInvoiceIdById(invoiceId);
		// get invoice details
		ci.setCustomerInvoiceDetails(this.ciDetailMapper.selectCustomerInvoiceDetailsByCustomerInvoiceId(invoiceId));
		// get customer details from invoice
		Customer customer = ci.getCustomer();
		// get necessary models end
		
		// If is overdue penalty
		if(isOverduePenalty){
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			cid.setInvoice_id(ci.getId());
			cid.setInvoice_detail_name("Overdue Penalty");
			cid.setInvoice_detail_desc("10% of Total Payable Amount");
			cid.setInvoice_detail_unit(1);
			cid.setInvoice_detail_price(TMUtils.bigMultiply(ci.getFinal_payable_amount(), 0.1d));
			this.ciDetailMapper.insertCustomerInvoiceDetail(cid);
			ci.getCustomerInvoiceDetails().add(cid);
		}
		
		// initialize invoice's important informations
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(customer);
		invoicePDF.setCurrentCustomerInvoice(ci);
		invoicePDF.setOrg(this.organizationMapper.selectOrganizationByCustomerId(customer.getId()));

		// set file path
		Map<String, Object> map = null;
		try {
			map = invoicePDF.create();
			// generate invoice PDF
			ci.setInvoice_pdf_path((String) map.get("filePath"));
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		// add sql condition: id
		ci.setAmount_paid((Double) map.get("amount_paid"));
		ci.setAmount_payable((Double) map.get("amount_payable"));
		ci.setFinal_payable_amount((Double) map.get("final_amount_payable"));
		ci.setBalance((Double) map.get("balance")); 
		ci.getParams().put("id", ci.getId());
		
		this.ciMapper.updateCustomerInvoice(ci);
		// invoice PDF generator manipulation end
	}
	
	@Transactional
	public void createInvoicePDF(CustomerOrder customerOrder
			,Notification notificationEmail
			,Notification notificationSMS) {

		customerOrder = this.customerOrderMapper.selectCustomerOrderById(customerOrder.getId());
		createInvoicePDFBoth(customerOrder
				,new Notification(notificationEmail.getTitle(), notificationEmail.getContent())
				,new Notification(notificationEmail.getTitle(), notificationEmail.getContent())
				, false, false);	// false : first invoice
	}

	@Transactional 
	public void createNextInvoice(CustomerOrder customerOrderParam) throws ParseException{
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("invoice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("invoice", "sms");
		List<CustomerOrder> customerOrders = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrderParam);

		for (CustomerOrder customerOrder : customerOrders) {
			//System.out.println(customerOrder.getId() + ":" + customerOrder.getNext_invoice_create_date());
			createInvoicePDFBoth(customerOrder
					,new Notification(notificationEmail.getTitle(), notificationEmail.getContent())
					,new Notification(notificationSMS.getTitle(), notificationSMS.getContent())
					, true, false);	// true : next invoice
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
		
		
		Notification topupNotificationEmail = this.notificationMapper.selectNotificationBySort("topup-notification", "email");
		Notification topupNotificationSMS = this.notificationMapper.selectNotificationBySort("topup-notification", "sms");
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		
		for (CustomerOrder customerOrder : topupCustomerOrders) {
			Customer c = customerOrder.getCustomer();
			
			// Prevent template pollution
			Notification topupNotificationEmailFinal = new Notification(topupNotificationEmail.getTitle(), topupNotificationEmail.getContent());
			Notification topupNotificationSMSFinal = new Notification(topupNotificationSMS.getTitle(), topupNotificationSMS.getContent());

			// call mail at value retriever
			Organization org = this.organizationMapper.selectOrganizationByCustomerId(c.getId());
			c.setOrganization(org);
			MailRetriever.mailAtValueRetriever(topupNotificationEmailFinal, c, customerOrder, companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(c.getEmail());
			applicationEmail.setSubject(topupNotificationEmailFinal.getTitle());
			applicationEmail.setContent(topupNotificationEmailFinal.getContent());
			// binding attachment name & path to email
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			MailRetriever.mailAtValueRetriever(topupNotificationSMSFinal, c, customerOrder, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(c.getCellphone(), topupNotificationSMSFinal.getContent());

			topupNotificationEmailFinal = null;
			topupNotificationSMSFinal = null;
			c = null;
			org = null;
		}
		topupNotificationEmail = null;
		topupNotificationSMS = null;
		// END TOPUP NOTIFICATION
		
	}
	
	@Transactional 
	public void sendTermPlanInvoicePDF() {
		
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("invoice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("invoice", "sms");
		
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		
		CustomerOrder coTemp = new CustomerOrder();
		coTemp.getParams().put("where", "query_term");
		coTemp.getParams().put("status", "using");
		coTemp.getParams().put("order_type", "order-term");
		coTemp.getParams().put("is_ddpay", true);
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrders(coTemp);
		
		for (CustomerOrder co : cos) {
			
			// Prevent template pollution
			Notification notificationEmailFinal = new Notification(notificationEmail.getTitle(), notificationEmail.getContent());
			Notification notificationSMSFinal = new Notification(notificationSMS.getTitle(), notificationSMS.getContent());
			
			
			CustomerInvoice ci = new CustomerInvoice();
			ci.getParams().put("where", "by_max_id");
			ci.getParams().put("customer_id", co.getCustomer().getId());
			ci.getParams().put("order_id", co.getId());
			ci = this.ciMapper.selectCustomerInvoice(ci);
			
			// call mail at value retriever
			Organization org = this.organizationMapper.selectOrganizationByCustomerId(co.getCustomer().getId());
			co.getCustomer().setOrganization(org);

			MailRetriever.mailAtValueRetriever(notificationEmailFinal, co.getCustomer(), co, ci, companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(co.getCustomer().getEmail());
			applicationEmail.setSubject(notificationEmailFinal.getTitle());
			applicationEmail.setContent(notificationEmailFinal.getContent());
			// binding attachment name & path to email
			applicationEmail.setAttachName("invoice_" + ci.getId() + ".pdf");
			applicationEmail.setAttachPath(ci.getInvoice_pdf_path());
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			MailRetriever.mailAtValueRetriever(notificationSMSFinal, co.getCustomer(), co, ci, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(co.getCustomer().getCellphone(), notificationSMSFinal.getContent());
			
			notificationEmailFinal = null;
			notificationSMSFinal = null;
			ci = null;
			org = null;
		}
		notificationEmail = null;
		notificationSMS = null;
	}

	@Transactional
	public void createEarlyTerminationInvoice(Integer order_id
			, Date terminatedDate
			, Integer executor_id) throws ParseException{
		CustomerOrder co = this.customerOrderMapper.selectCustomerOrderById(order_id);
		Customer c = co.getCustomer();
		Map<String, Object> map = TMUtils.earlyTerminationDatesCalculation(co.getOrder_using_start(), terminatedDate);
		
		EarlyTerminationCharge etc = new EarlyTerminationCharge();
		etc.setCustomer_id(c.getId());
		etc.setOrder_id(co.getId());
		etc.setCreate_date(new Date());
		etc.setService_given_date(co.getOrder_using_start());
		etc.setTermination_date(terminatedDate);
		etc.setLegal_termination_date((Date) map.get("legal_termination_date"));
		etc.setDue_date(TMUtils.getInvoiceDueDate(new Date(), 10));
		EarlyTerminationChargeParameter earlyTerminationChargeParam = this.earlyTerminationChargeParameterMapper.selectEarlyTerminationChargeParameter();
		etc.setOverdue_extra_charge(earlyTerminationChargeParam != null ? earlyTerminationChargeParam.getOverdue_extra_charge() : 0d);
		etc.setCharge_amount((Double) map.get("charge_amount"));
		etc.setTotal_payable_amount((Double) map.get("charge_amount") + etc.getOverdue_extra_charge());
		etc.setMonths_between_begin_end((Integer) map.get("months_between_begin_end"));
		etc.setExecute_by(executor_id);
		etc.setStatus("unpaid");
		
		this.earlyTerminationChargeMapper.insertEarlyTerminationCharge(etc);
		
		String invoicePDFPath = "";
		try {
			CompanyDetail cd = this.companyDetailMapper.selectCompanyDetail();
			Organization org = c.getOrganization();
			invoicePDFPath = new EarlyTerminationChargePDFCreator(cd, c, org, etc).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		etc.getParams().put("id", etc.getId());
		etc.setInvoice_pdf_path(invoicePDFPath);
		this.earlyTerminationChargeMapper.updateEarlyTerminationCharge(etc);
	}

	@Transactional
	public void createTerminationRefundInvoice(Integer order_id
			, Date terminatedDate, User u, String accountNo, String accountName, Double monthlyCharge, String productName) throws ParseException{
		CustomerOrder co = this.customerOrderMapper.selectCustomerOrderById(order_id);
		Customer c = co.getCustomer();
		Map<String, Object> map = TMUtils.terminationRefundCalculations(terminatedDate, monthlyCharge);
		
		TerminationRefund tr = new TerminationRefund();
		tr.setCustomer_id(c.getId());
		tr.setOrder_id(co.getId());
		tr.setCreate_date(new Date());
		tr.setTermination_date(terminatedDate);
		tr.setProduct_name(productName);
		tr.setProduct_monthly_price(monthlyCharge);
		tr.setRefund_bank_account_number(accountNo);
		tr.setRefund_bank_account_name(accountName);
		tr.setRefund_amount((Double) map.get("refund_amount"));
		tr.setDays_between_end_last((Integer) map.get("remaining_days"));
		tr.setStatus("unpaid");
		tr.setExecute_by(u.getId());
		tr.setLast_date_of_month((Date) map.get("last_date_of_month"));
		
		
		this.terminationRefundMapper.insertTerminationRefund(tr);
		
		String refundPDFPath = "";
		try {
			CompanyDetail cd = this.companyDetailMapper.selectCompanyDetail();
			Organization org = c.getOrganization();
			refundPDFPath = new TerminationRefundPDFCreator(cd, c, org, tr, u).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		tr.getParams().put("id", tr.getId());
		tr.setRefund_pdf_path(refundPDFPath);
		this.terminationRefundMapper.updateTerminationRefund(tr);
	}

	@Transactional
	public void createTermPlanInvoice() throws ParseException{
		
		// Production environment should edit as shown below
		// where = query_term
		// status = using
		// order_type = order-term
		// is_ddpay = false
		CustomerOrder coTemp = new CustomerOrder();
		coTemp.getParams().put("where", "query_term");
		coTemp.getParams().put("status", "using");
		coTemp.getParams().put("order_type", "order-term");
		coTemp.getParams().put("is_ddpay", true);
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrders(coTemp);
		
		// If found any order(s)
		if(cos.size() > 0){
			
			boolean isRegenerateInvoice = false;
			
			for (CustomerOrder co : cos) {
				createTermPlanInvoiceByOrder(co
						, isRegenerateInvoice);
			}
		}
	}

	@Transactional
	public void createTermPlanInvoiceOverduePenalty() throws ParseException{
		CustomerInvoice ciTemp = new CustomerInvoice();
		Calendar beginCal = Calendar.getInstance();
		beginCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH)-3);
		beginCal.set(Calendar.DAY_OF_MONTH, 1);
		beginCal.set(Calendar.HOUR_OF_DAY, 0);
		beginCal.set(Calendar.MINUTE, 0);
		beginCal.set(Calendar.SECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.DAY_OF_MONTH, 1);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.add(Calendar.DAY_OF_MONTH, -1);
		ciTemp.getParams().put("where", "by_overdue_penalty");
		ciTemp.getParams().put("status", "unpaid");
		ciTemp.getParams().put("begin_date", beginCal.getTime());
		ciTemp.getParams().put("end_date", endCal.getTime());
		List<CustomerInvoice> cis = this.ciMapper.selectCustomerInvoices(ciTemp);
		for (CustomerInvoice ci : cis) {
			createInvoicePDFByInvoiceID(ci.getId(), true);
		}
	}

	@Transactional
	public void createTermPlanInvoiceByOrder(CustomerOrder co
			,boolean isRegenerateInvoice) throws ParseException{
		
		// BEGIN get usable beans
		// Customer
		Customer c = co.getCustomer();
		boolean isBusiness = "business".toUpperCase().equals(c.getCustomer_type().toUpperCase());
		
		// Current invoice
		CustomerInvoice ci = new CustomerInvoice();
		System.out.println("isRegenerateInvoice: " + isRegenerateInvoice);
		
		// Previous invoice's preparation
		CustomerInvoice ciPreQuery = new CustomerInvoice();
		ciPreQuery.getParams().put("customer_id", c.getId());
		ciPreQuery.getParams().put("order_id", co.getId());
		// Previous invoice
		CustomerInvoice cpi = null;
		
		if(isRegenerateInvoice){
			CustomerInvoice ciCurQuery = new CustomerInvoice();
			ciCurQuery.getParams().put("where", "by_max_id");
			ciCurQuery.getParams().put("customer_id", c.getId());
			ciCurQuery.getParams().put("order_id", co.getId());
			ci = ciMapper.selectCustomerInvoice(ciCurQuery);
			if(ci != null && !"paid".equals(ci.getStatus())){
				ciPreQuery.getParams().put("where", "by_second_id");
				cpi = ciMapper.selectCustomerInvoice(ciPreQuery);
			} else if(ci != null) {
				ciPreQuery.getParams().put("where", "by_max_id");
				cpi = ciMapper.selectCustomerInvoice(ciPreQuery);
				ci = new CustomerInvoice();
				ci.setCustomer_id(c.getId());
				ci.setOrder_id(co.getId());
				ci.setCreate_date(new Date());
				ci.setDue_date(TMUtils.getInvoiceDueDate(ci.getCreate_date(), 5));
				System.out.println("due_date_ci_!null: "+ci.getDue_date());
				ciMapper.insertCustomerInvoice(ci);
			} else if(ci == null){
				ci = new CustomerInvoice();
				ci.setCustomer_id(c.getId());
				ci.setOrder_id(co.getId());
				ci.setCreate_date(new Date());
				ci.setDue_date(TMUtils.getInvoiceDueDate(ci.getCreate_date(), 5));
				System.out.println("due_date_ci_null: "+ci.getDue_date());
				ciMapper.insertCustomerInvoice(ci);
			}
		} else {
			ciPreQuery.getParams().put("where", "by_max_id");
			cpi = ciMapper.selectCustomerInvoice(ciPreQuery);
			ci = new CustomerInvoice();
			ci.setCustomer_id(c.getId());
			ci.setOrder_id(co.getId());
			ci.setCreate_date(new Date());
			ci.setDue_date(TMUtils.getInvoiceDueDate(ci.getCreate_date(), 5));
			System.out.println("!regenerate: "+ci.getDue_date());
			ciMapper.insertCustomerInvoice(ci);
			// Set id for invoice's update
		}
		if(ci!=null && ci.getId()!=null){
			this.ciDetailMapper.deleteCustomerInvoiceDetailByInvoiceId(ci.getId());
		}
		ci.setAmount_paid(ci.getAmount_paid()!=null ? ci.getAmount_paid() : 0d);
		ci.setAmount_payable(ci.getAmount_payable()!=null ? ci.getAmount_payable() : 0d);
		ci.setBalance(ci.getBalance()!=null ? ci.getBalance() : 0d);
		ci.getParams().put("id", ci.getId());	// For update invoice use
		
		// Current invoice detail
		List<CustomerInvoiceDetail> cids = new ArrayList<CustomerInvoiceDetail>();
		// END get usable beans

		// If current order don't have any invoice then true, else false
		// Production environment should edit as shown below
		// {true} change to {cpi == null ? true : false;}
		boolean isFirst = //true;
				cpi == null ? true : false;
		// If service giving is gave by this month then true, else false
		// Production environment should edit as shown below
		// {true} change to {below long statement}
		boolean isServedCurrentMonth = //false; 
				co.getOrder_using_start() != null ? TMUtils.isSameMonth(co.getOrder_using_start(), new Date()) : false;

		// Calculate invoice's payable amount
		Double totalAmountPayable = 0d;
		Double totalCreditBack = 0d;
		
		String pstn_number = null;
		List<CustomerOrderDetail> pcms = new ArrayList<CustomerOrderDetail>();

		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		
		for (CustomerOrderDetail cod : co.getCustomerOrderDetails()) {
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			
			if("pstn".equals(cod.getDetail_type())){
				
				if((cod.getPstn_number()!=null && !"".equals(cod.getPstn_number().trim()))
					&& ("".equals(pstn_number) || pstn_number==null)){
					pstn_number = cod.getPstn_number();
					
				}
				
			}
			
			if("termination-credit".equals(cod.getDetail_type())){
				cid.setInvoice_detail_name(cod.getDetail_name());
				cid.setInvoice_detail_unit(cod.getDetail_unit());
				cid.setInvoice_detail_discount(cod.getDetail_price());
				cid.setInvoice_detail_desc(cod.getDetail_desc());
				cids.add(cid);
				
				totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
			}
			if("early-termination-debit".equals(cod.getDetail_type())){
				cid.setInvoice_detail_name(cod.getDetail_name());
				cid.setInvoice_detail_unit(cod.getDetail_unit());
				cid.setInvoice_detail_price(cod.getDetail_price());
				cid.setInvoice_detail_desc(cod.getDetail_desc());
				cids.add(cid);
				
				totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, cod.getDetail_price());
			}
			if("present-calling-minutes".equals(cod.getDetail_type())){
				cid.setInvoice_detail_name(cod.getDetail_name());
				cid.setInvoice_detail_unit(cod.getDetail_unit());
				cids.add(cid);
				pcms.add(cod);
			}
			
			System.out.println("isFirst: " + isFirst);
			// If first invoice then add all order details into invoice details
			if(isFirst){
				
				if("plan-term".equals(cod.getDetail_type())){
//					System.out.println("isNotFirst");
//					System.out.println();
					
					if(!isRegenerateInvoice || (isRegenerateInvoice && "unpaid".equals(ci.getStatus())) || (isRegenerateInvoice && "paid".equals(cpi != null ? cpi.getStatus() : "paid") && ! (cpi != null ? TMUtils.isSameMonth(cpi.getCreate_date(), new Date()) : false))){

						// Add service given to served month's last day
						Calendar cal = Calendar.getInstance(Locale.CHINA);
						if(isFirst){
							cal.setTime(co.getOrder_using_start());
							cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
							cid.setInvoice_detail_desc(co.getOrder_using_start_str()+" - "+TMUtils.dateFormatYYYYMMDD(cal.getTime()));
						}
						System.out.println("isFirst");
						System.out.println("co.getOrder_using_start(): "+co.getOrder_using_start());
						System.out.println("cal.getTime(): "+cal.getTime());
						
						// Get served month's term plan's remaining charges 
						// Production environment should edit as shown below
						// {new Date()} change to {co.getOrder_using_start()}
						Map<String, Object> resultMap = Calculation4PlanTermInvoice.servedMonthDetails(//new Date()
								co.getOrder_using_start()
								, cod.getDetail_price());
						cid.setInvoice_detail_name(cod.getDetail_name()+" ("+resultMap.get("remainingDays")+" day(s) counting from service start date to end of month)");
						cid.setInvoice_detail_unit(cod.getDetail_unit());
						cid.setInvoice_detail_price((Double)resultMap.get("totalPrice"));
						
						// Add remaining price into payable amount
						totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, (Double)resultMap.get("totalPrice"));
						cids.add(cid);
						
						// If is not served in current month, then add plan-term type detail into invoice detail
						if (!isServedCurrentMonth) {
							
							// Reconstruct invoice's detail
							cid = new CustomerInvoiceDetail();
							cid.setInvoice_detail_name(cod.getDetail_name());
							cid.setInvoice_detail_unit(cod.getDetail_unit());
							cid.setInvoice_detail_price(cod.getDetail_price());
							
							// Add first day to last day
							cal = Calendar.getInstance(Locale.CHINA);
							cal.set(Calendar.DAY_OF_MONTH, 1);
							Date firstDay = cal.getTime();
							cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
							Date lastDay = cal.getTime();
							cid.setInvoice_detail_desc(TMUtils.dateFormatYYYYMMDD(firstDay)+" - "+TMUtils.dateFormatYYYYMMDD(lastDay));
							
							// Add monthly price into payable amount
							totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, cod.getDetail_price());
							cids.add(cid);
						}
					}

				// Else if discount and unexpired then do add discount
				} else if("discount".equals(cod.getDetail_type()) && cod.getDetail_expired() != null && cod.getDetail_expired().getTime() >= System.currentTimeMillis()){
					
					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_discount(cod.getDetail_price());
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					// totalCreditBack add ( discount price times discount unit )
					totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
					cids.add(cid);
					
				// Else add all non plan-term, discount, termination-credit, early-termination-debit type details into invoice details
				} else if(!"discount".equals(cod.getDetail_type())
						  && !"termination-credit".equals(cod.getDetail_type())
						  && !"early-termination-debit".equals(cod.getDetail_type())
						  && !"present-calling-minutes".equals(cod.getDetail_type())) {

					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_price(cod.getDetail_price());
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					
					// Payable amount plus ( detail price times detail unit )
					totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
					cids.add(cid);
				}
				
			// Else not first invoice, add unexpired order detail(s) into invoice detail(s)
			} else {
				
				// Add previous invoice's details into
				ci.setLast_invoice_id(cpi.getId());
				ci.setLastCustomerInvoice(cpi);

				// If plan-term type, then add detail into invoice detail
				if("plan-term".equals(cod.getDetail_type())){
					System.out.println("!isRegenerateInvoice: "+!isRegenerateInvoice);
					System.out.println("isRegenerateInvoice && \"paid\".equals(cpi.getStatus()) && TMUtils.isSameMonth(cpi.getCreate_date(), new Date()): "+(isRegenerateInvoice && "paid".equals(cpi.getStatus()) && TMUtils.isSameMonth(cpi.getCreate_date(), new Date())));
					if(!isRegenerateInvoice || (isRegenerateInvoice && "unpaid".equals(ci.getStatus())) || (isRegenerateInvoice && "paid".equals(cpi != null ? cpi.getStatus() : "paid") && ! (cpi != null ? TMUtils.isSameMonth(cpi.getCreate_date(), new Date()) : false))){
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(ci.getCreate_date());
						cal.add(Calendar.DAY_OF_MONTH, 5);
						Date endTo = cal.getTime();
						cal.add(Calendar.MONTH, -1);
						cal.add(Calendar.DAY_OF_MONTH, 1);
						Date startFrom = cal.getTime();
						
						cid.setInvoice_detail_desc(TMUtils.dateFormatYYYYMMDD(startFrom)+" - "+TMUtils.dateFormatYYYYMMDD(endTo));
						
						cid.setInvoice_detail_name(cod.getDetail_name());
						cid.setInvoice_detail_unit(cod.getDetail_unit());
						cid.setInvoice_detail_price(cod.getDetail_price());
						
						// Add monthly price into payable amount
						totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, cod.getDetail_price());
						cids.add(cid);
					}

				// Else if discount and unexpired then do add discount
				} else if("discount".equals(cod.getDetail_type()) && cod.getDetail_expired() != null && cod.getDetail_expired().getTime() >= System.currentTimeMillis()){
					
					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_discount(cod.getDetail_price());
					cid.setInvoice_detail_unit(cod.getDetail_unit());

					// totalCreditBack add ( discount price times discount unit )
					totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
					cids.add(cid);

				// Else if unexpired then add order detail(s) into invoice detail(s)
				} else if(cod.getDetail_expired() != null && cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {

					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_price(cod.getDetail_price());
					cid.setInvoice_detail_unit(cod.getDetail_unit());

					// Payable amount plus ( detail price times detail unit )
					totalAmountPayable = TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
					cids.add(cid);
				}
				
			}
		}

		// store company detail begin
		CompanyDetail companyDetail = companyDetailMapper.selectCompanyDetail();
		// store company detail end
		
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(c);
		invoicePDF.setOrg(organizationMapper.selectOrganizationByCustomerId(c.getId()));
		
		if(pstn_number!=null && !"".equals(pstn_number.trim())){
			if(!isRegenerateInvoice || (isRegenerateInvoice && "unpaid".equals(ci.getStatus())) || (isRegenerateInvoice && "paid".equals(cpi != null ? cpi.getStatus() : "paid") && ! (cpi != null ? TMUtils.isSameMonth(cpi.getCreate_date(), new Date()) : false))){
				
				totalAmountPayable = TMUtils.ccrOperation(pcms, pstn_number, cids, invoicePDF, totalAmountPayable, this.customerCallRecordMapper, this.callInternationalRateMapper, this.customerCallingRecordCallplusMapper, c.getCustomer_type());
			}
		}
		
		// truncate unnecessary reminders, left only two reminders, e.g. 1.0001 change to 1.00
		totalCreditBack = Double.parseDouble(TMUtils.fillDecimalPeriod(totalCreditBack));
		totalAmountPayable = isBusiness ? TMUtils.bigMultiply(totalAmountPayable, 1.15) : totalAmountPayable;
		
		// Set current invoice's payable amount
		ci.setAmount_payable(totalAmountPayable);
		ci.setFinal_payable_amount(TMUtils.bigSub(totalAmountPayable, totalCreditBack));
		ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getFinal_payable_amount(), ci.getAmount_paid(), "sub"));

		// Iteratively inserting invoice detail(s) into tm_invoice_detail table
		for (CustomerInvoiceDetail cid : cids) {
			cid.setInvoice_id(ci.getId());
			ciDetailMapper.insertCustomerInvoiceDetail(cid);
		}
		
		// Set invoice details into 
		ci.setCustomerInvoiceDetails(cids);

		invoicePDF.setCurrentCustomerInvoice(ci);

		// set file path
		Map<String, Object> map = null;
		try {
			map = invoicePDF.create();
			// generate invoice PDF
			ci.setInvoice_pdf_path((String) map.get("filePath"));
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		ci.setStatus("unpaid");
		ciMapper.updateCustomerInvoice(ci);

		// Deleting repeated invoices
		ciMapper.deleteCustomerInvoiceByRepeat();
		
	}
	
	
	@Transactional
	public void createInvoicePDFBoth(CustomerOrder customerOrder
			,Notification notificationEmailFinal
			,Notification notificationSMSFinal, Boolean is_Next_Invoice, boolean isRegenerate){
		// store company detail begin
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		// store company detail end

		Customer customer = customerOrder.getCustomer();

		boolean isBusiness = "business".toUpperCase().equals(customer.getCustomer_type().toUpperCase());
		
		// current invoice model
		CustomerInvoice ci = new CustomerInvoice();
		CustomerInvoice ciCurQuery = new CustomerInvoice();
		ciCurQuery.getParams().put("customer_id", customer.getId());
		ciCurQuery.getParams().put("order_id", customerOrder.getId());
		// previous invoice model
		CustomerInvoice cpi = new CustomerInvoice();
		CustomerInvoice ciPreQuery = new CustomerInvoice();
		ciPreQuery.getParams().put("customer_id", customer.getId());
		ciPreQuery.getParams().put("order_id", customerOrder.getId());
		
		boolean isMostRecentInvoicePaid = false;
		
		if(!isRegenerate){
			// If Not Regenerate then set next invoice create date
			Date invoiceCreateDay = !is_Next_Invoice 
					? (customerOrder.getOrder_using_start() != null 
							? customerOrder.getOrder_using_start() 
							: new Date()) 
					: customerOrder.getNext_invoice_create_date();
			// Next invoice create date
			ci.setCustomer_id(customer.getId());
			ci.setOrder_id(customerOrder.getId());
			ci.setCreate_date(invoiceCreateDay);
			ci.setDue_date(TMUtils.getInvoiceDueDate(invoiceCreateDay, 5));
			ci.setStatus("unpaid");
			// If Not Regenerate then get most recent invoice as previous invoice
			ciPreQuery.getParams().put("where", "by_max_id");
			cpi = this.ciMapper.selectCustomerInvoice(ciPreQuery);
			this.ciMapper.insertCustomerInvoice(ci);
		} else {
			// If Regenerate then get most recent invoice as current invoice
			ciCurQuery.getParams().put("where", "by_max_id");
			ci = this.ciMapper.selectCustomerInvoice(ciCurQuery);
			// If Regenerate then get before than most recent invoice as previous invoice
			if(ci!=null && !"paid".equals(ci.getStatus())){
				ciPreQuery.getParams().put("where", "by_second_id");
				cpi = this.ciMapper.selectCustomerInvoice(ciPreQuery);
			} else if(ci!=null) {
				ciPreQuery.getParams().put("where", "by_max_id");
				cpi = this.ciMapper.selectCustomerInvoice(ciPreQuery);
				isMostRecentInvoicePaid = true;
				ci = new CustomerInvoice();
				ci.setCustomer_id(customer.getId());
				ci.setOrder_id(customerOrder.getId());
				ci.setCreate_date(ci.getCreate_date()!=null ? ci.getCreate_date() : new Date());
				ci.setDue_date(ci.getDue_date()!=null ? ci.getDue_date() : TMUtils.getInvoiceDueDate(ci.getCreate_date(), 5));
				this.ciMapper.insertCustomerInvoice(ci);
			} else if(ci==null) {
				ci = new CustomerInvoice();
				ci.setCustomer_id(customer.getId());
				ci.setOrder_id(customerOrder.getId());
				ci.setCreate_date(ci.getCreate_date()!=null ? ci.getCreate_date() : new Date());
				ci.setDue_date(ci.getDue_date()!=null ? ci.getDue_date() : TMUtils.getInvoiceDueDate(ci.getCreate_date(), 5));
				this.ciMapper.insertCustomerInvoice(ci);
			}
		}
		if(ci!=null && ci.getId()!=null){
			this.ciDetailMapper.deleteCustomerInvoiceDetailByInvoiceId(ci.getId());
		}
		// Customer previous invoice
		if (cpi != null && !ci.getId().equals(cpi.getId())) {
			ci.setLast_invoice_id(cpi.getId());
			ci.setLastCustomerInvoice(cpi);
		} else {
			cpi = null;
		}
		boolean isFirst = cpi==null ? true : false;
		System.out.println("isFirst: "+isFirst);
		
		// detail holder begin
		List<CustomerInvoiceDetail> cids = new ArrayList<CustomerInvoiceDetail>();
		// detail holder end
		List<CustomerOrderDetail> customerOrderDetails = customerOrder.getCustomerOrderDetails();

		Double totalAmountPayable = 0d;
		Double totalCreditBack = 0d;
		
		String pstn_number = "";
		List<CustomerOrderDetail> pcms = new ArrayList<CustomerOrderDetail>();
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		
		for (CustomerOrderDetail cod: customerOrderDetails) {

			if("pstn".equals(cod.getDetail_type())){
				
				if((cod.getPstn_number()!=null && !"".equals(cod.getPstn_number().trim()))
					&& ("".equals(pstn_number) || pstn_number==null)){
					pstn_number = cod.getPstn_number();
				}
				
			}
			
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			cid.setInvoice_id(ci.getId());
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_unit(cod.getDetail_unit() == null ? 1 : cod.getDetail_unit());

			// if detail type equals discount and detail expire date greater
			// equal than today's date then go into if statement
			if ("discount".equals(cod.getDetail_type())
					&& cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {
				
				cid.setInvoice_detail_discount(cod.getDetail_price());
				// decrease amountPayable
				totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cid.getInvoice_detail_discount(), cid.getInvoice_detail_unit()));
				// add invoice detail to list
				cids.add(cid);
				// else if detail type is discount then this discount is expired
				// and will not be add to the invoice detail list
			} else if (!"discount".equals(cod.getDetail_type())) {
				if(cod.getDetail_type()!=null && "termination-credit".equals(cod.getDetail_type())){
					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					cid.setInvoice_detail_discount(cod.getDetail_price());
					cid.setInvoice_detail_desc(cod.getDetail_desc());
					cids.add(cid);
					totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
				} else if(cod.getDetail_type()!=null && "present-calling-minutes".equals(cod.getDetail_type())){
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					cids.add(cid);
					pcms.add(cod);
				
				// Termed & No Termed
				} else if(!isMostRecentInvoicePaid && cod.getDetail_type()!=null && cod.getDetail_type().contains("plan-") && !"plan-topup".equals(cod.getDetail_type())){

					Calendar cal = Calendar.getInstance();
					Date endTo = null;
					Date startFrom = null;
					if(isFirst){
						cal.setTime(customerOrder.getOrder_using_start());
						cal.add(Calendar.MONTH, cod.getDetail_unit());
						cal.add(Calendar.DAY_OF_MONTH, -1);
						startFrom = customerOrder.getOrder_using_start();
						endTo = cal.getTime();
						
					} else {
						cal.setTime(ci.getCreate_date());
						cal.add(Calendar.DAY_OF_MONTH, 5);
						endTo = cal.getTime();
						cal.add(Calendar.MONTH, -1);
						cal.add(Calendar.DAY_OF_MONTH, 1);
						startFrom = cal.getTime();
					}
					
					cid.setInvoice_detail_desc(TMUtils.dateFormatYYYYMMDD(startFrom)+" - "+TMUtils.dateFormatYYYYMMDD(endTo));
					
					// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
					cid.setInvoice_detail_unit(cod.getDetail_unit() != null && !is_Next_Invoice && isFirst ? cod.getDetail_unit() : 1);
					cid.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
					// increase amountPayable
					totalAmountPayable =  cid.getInvoice_detail_price() != null ? TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit())) : 0;
					// add invoice detail to list
					cids.add(cid);
					
				// Topup Plan
				} else if(!isMostRecentInvoicePaid && cod.getDetail_type()!=null && cod.getDetail_type().contains("plan-") && "plan-topup".equals(cod.getDetail_type())){
					
					// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
					cid.setInvoice_detail_unit(cod.getDetail_unit() != null && !is_Next_Invoice && isFirst ? cod.getDetail_unit() : 1);
					cid.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
					// increase amountPayable
					totalAmountPayable =  cid.getInvoice_detail_price() != null ? TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit())) : 0;
					// add invoice detail to list
					cids.add(cid);
				} else if(!isMostRecentInvoicePaid && !is_Next_Invoice && isFirst){
					// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
					cid.setInvoice_detail_unit(cod.getDetail_unit() != null && !is_Next_Invoice && isFirst ? cod.getDetail_unit() : 1);
					cid.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
					// increase amountPayable
					totalAmountPayable =  cid.getInvoice_detail_price() != null ? TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit())) : 0;
					// add invoice detail to list
					cids.add(cid);
				} else if((cod.getDetail_expired()!=null && cod.getDetail_expired().getTime() >= System.currentTimeMillis())
						|| cod.getDetail_type().contains("plan-")){
					// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
					cid.setInvoice_detail_unit(cod.getDetail_unit() != null && !is_Next_Invoice && isFirst ? cod.getDetail_unit() : 1);
					cid.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
					// increase amountPayable
					totalAmountPayable =  cid.getInvoice_detail_price() != null ? TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit())) : 0;
					// add invoice detail to list
					cids.add(cid);
				}
				if (cod.getDetail_type()!=null && cod.getDetail_type().contains("plan-") 
						&& !"plan-topup".equals(cod.getDetail_type()) && !isRegenerate) {
					System.out.println("is_Next_Invoice: "+is_Next_Invoice);
					// if is next invoice then plus one month else plus unit month(s)
					int nextInvoiceMonth = is_Next_Invoice ? 1 : cod.getDetail_unit();
					int nextInvoiceDay = -5;
					Calendar calNextInvoiceDay = Calendar.getInstance();
					calNextInvoiceDay.setTime(!is_Next_Invoice 
								? (customerOrder.getOrder_using_start() != null 
								? customerOrder.getOrder_using_start() 
								: new Date()) 
						: customerOrder.getNext_invoice_create_date_flag());
					calNextInvoiceDay.add(Calendar.MONTH, nextInvoiceMonth);
					// update customer order's next invoice create day flag begin
					customerOrder.setNext_invoice_create_date_flag(calNextInvoiceDay.getTime());
					// update customer order's next invoice create day flag end

					calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
					// update customer order's next invoice create day begin
					customerOrder.setNext_invoice_create_date(calNextInvoiceDay.getTime());
					// update customer order's next invoice create day end
					
					customerOrder.getParams().put("id", customerOrder.getId());
					
					this.customerOrderMapper.updateCustomerOrder(customerOrder);
				}
			}
		}
		Organization org = this.organizationMapper.selectOrganizationByCustomerId(customer.getId());
		invoicePDF.setCompanyDetail(companyDetail);
		invoicePDF.setCustomer(customer);
		invoicePDF.setOrg(this.organizationMapper.selectOrganizationByCustomerId(customer.getId()));
		invoicePDF.setCurrentCustomerInvoice(ci);
		
		if(pstn_number!=null && !"".equals(pstn_number.trim())){
			totalAmountPayable = TMUtils.ccrOperation(pcms, pstn_number, cids, invoicePDF, totalAmountPayable, this.customerCallRecordMapper, this.callInternationalRateMapper, this.customerCallingRecordCallplusMapper, customer.getCustomer_type());
			
		}
		
		totalCreditBack = Double.parseDouble(TMUtils.fillDecimalPeriod(totalCreditBack));
		totalAmountPayable = isBusiness ? TMUtils.bigMultiply(totalAmountPayable, 1.15) : totalAmountPayable;

		ci.setAmount_payable(totalAmountPayable);
		ci.setFinal_payable_amount(TMUtils.bigSub(totalAmountPayable, totalCreditBack));
		ci.setAmount_paid(ci.getAmount_paid() == null ? 0d : ci.getAmount_paid());
		// balance = final payable - paid
		ci.setBalance(TMUtils.bigSub(ci.getFinal_payable_amount(), ci.getAmount_paid()));
		// Add cids into ci
		ci.setCustomerInvoiceDetails(cids);
		ci.setStatus("unpaid");
		
		// Add cids into db
		for (CustomerInvoiceDetail cid : cids) {
			this.ciDetailMapper.insertCustomerInvoiceDetail(cid);
		}

		// Set file path
		Map<String, Object> map = null;
		try {
			map = invoicePDF.create();
			// generate invoice PDF
			ci.setInvoice_pdf_path((String) map.get("filePath"));
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		
		ci.getParams().put("id", ci.getId());
		this.ciMapper.updateCustomerInvoice(ci);

		// Deleting repeated invoices
		this.ciMapper.deleteCustomerInvoiceByRepeat();

		if(!isRegenerate){
			
			// call mail at value retriever
			customer.setOrganization(org);
			MailRetriever.mailAtValueRetriever(notificationEmailFinal, customer,  customerOrder, ci, companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notificationEmailFinal.getTitle());
			applicationEmail.setContent(notificationEmailFinal.getContent());
			// binding attachment name & path to email
			applicationEmail.setAttachName("invoice_" + ci.getId() + ".pdf");
			applicationEmail.setAttachPath((String) map.get("filePath"));
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			MailRetriever.mailAtValueRetriever(notificationSMSFinal, customer, customerOrder, ci, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notificationSMSFinal.getContent());
		}
	}
	
	/**
	 * BEGIN CustomerOrderDetail
	 */
	
	public List<CustomerOrderDetail> queryCustomerOrderDetailsByOrderId(int order_id){
		return this.customerOrderDetailMapper.selectCustomerOrderDetailsByOrderId(order_id);
	}
	
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

	@Transactional 
	public String queryCustomerPreviousProviderInvoiceFilePathById(int id){
		return this.customerOrderMapper.selectCustomerPreviousProviderInvoiceFilePathById(id);
	}

	@Transactional 
	public String queryCustomerCreditFilePathById(int id){
		return this.customerOrderMapper.selectCustomerCreditFilePathById(id);
	}

	@Transactional 
	public String queryCustomerDDPayFormPathById(int id){
		return this.customerOrderMapper.selectCustomerDDPayFormPathById(id);
	}

	@Transactional 
	public String queryCustomerOrderingFormPathById(int id){
		return this.customerOrderMapper.selectCustomerOrderingFormPathById(id);
	}

	@Transactional 
	public String queryCustomerReceiptFormPathById(int id){
		return this.customerOrderMapper.selectCustomerReceiptPathById(id);
	}
	
	/**
	 * BEGIN CustomerServiceRecord
	 */
	@Transactional
	public List<CustomerServiceRecord> queryCustomerServiceRecord(CustomerServiceRecord csr){
		return this.customerServiceRecordMapper.selectCustomerServiceRecord(csr);
	}

	@Transactional
	public Page<CustomerServiceRecord> queryCustomerServiceRecordsByPage(Page<CustomerServiceRecord> page) {
		page.setTotalRecord(this.customerServiceRecordMapper.selectCustomerServiceRecordsSum(page));
		page.setResults(this.customerServiceRecordMapper.selectCustomerServiceRecordsByPage(page));
		return page;
	}

	@Transactional
	public CustomerServiceRecord queryCustomerServiceRecordById(int id){
		return this.customerServiceRecordMapper.selectCustomerServiceRecordById(id);
	}

	@Transactional
	public void createCustomerServiceRecord(CustomerServiceRecord csr){
		this.customerServiceRecordMapper.insertCustomerServiceRecord(csr);
	};

	@Transactional
	public void editCustomerServiceRecord(CustomerServiceRecord csr){
		this.customerServiceRecordMapper.updateCustomerServiceRecord(csr);
	};

	@Transactional
	public void removeCustomerServiceRecord(CustomerServiceRecord csr){
		this.customerServiceRecordMapper.deleteCustomerServiceRecord(csr);
	};
	/**
	 * END CustomerServiceRecord
	 */
	
	/**
	 * BEGIN CustomerServiceGivenPaid
	 */
	@Transactional
	public String serviceGivenPaid(Customer c, Organization org, CustomerOrder co, CompanyDetail cd, User u){
		List<CustomerOrderDetail> cods = this.customerOrderDetailMapper.selectCustomerOrderDetailsByOrderId(co.getId());
		List<CustomerInvoiceDetail> cids = new ArrayList<CustomerInvoiceDetail>();
		
		
		Double totalCreditBack = 0d;
		Double totalAmountPayable = 0d;
		
		for (CustomerOrderDetail cod : cods) {
			
			CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
			cid.setInvoice_detail_name(cod.getDetail_name());
			cid.setInvoice_detail_unit(cod.getDetail_unit() == null ? 1 : cod.getDetail_unit());
			
			if ("discount".equals(cod.getDetail_type())
					&& cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {
				
				cid.setInvoice_detail_discount(cod.getDetail_price());
				// decrease amountPayable
				totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cid.getInvoice_detail_discount(), cid.getInvoice_detail_unit()));
				// add invoice detail to list
				cids.add(cid);
				// else if detail type is discount then this discount is expired
				// and will not be add to the invoice detail list
			} else if ("debit".equals(cod.getDetail_type())
					&& cod.getDetail_expired().getTime() >= System.currentTimeMillis()) {
				
				cid.setInvoice_detail_price(cod.getDetail_price());
				// decrease amountPayable
				totalAmountPayable = TMUtils.bigSub(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit()));
				// add invoice detail to list
				cids.add(cid);
				// else if detail type is discount then this discount is expired
				// and will not be add to the invoice detail list
			} else if (!"discount".equals(cod.getDetail_type())) {
				if(cod.getDetail_type()!=null && "termination-credit".equals(cod.getDetail_type())){
					cid.setInvoice_detail_name(cod.getDetail_name());
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					cid.setInvoice_detail_discount(cod.getDetail_price());
					cid.setInvoice_detail_desc(cod.getDetail_desc());
					cids.add(cid);
					totalCreditBack = TMUtils.bigAdd(totalCreditBack, TMUtils.bigMultiply(cod.getDetail_price(), cod.getDetail_unit()));
				} else if(cod.getDetail_type()!=null && "present-calling-minutes".equals(cod.getDetail_type())){
					cid.setInvoice_detail_unit(cod.getDetail_unit());
					cids.add(cid);
				} else {
					
					if("plan-term".equals(cod.getDetail_type()) || "plan-no-term".equals(cod.getDetail_type())){
						Calendar cal = Calendar.getInstance();
						Date startFrom = TMUtils.parseDateYYYYMMDD(co.getOrder_using_start_str());
						cal.setTime(TMUtils.parseDateYYYYMMDD(co.getOrder_using_start_str()));
						cal.add(Calendar.MONTH, cod.getDetail_unit());
						cal.add(Calendar.DAY_OF_MONTH, -1);
						Date endTo = cal.getTime();
						cid.setInvoice_detail_desc(TMUtils.dateFormatYYYYMMDD(startFrom)+" - "+TMUtils.dateFormatYYYYMMDD(endTo));
					}
					
					// if is first invoice and unit isn't null then assigned from unit, otherwise assign to 1
					cid.setInvoice_detail_unit(cod.getDetail_unit() != null  ? cod.getDetail_unit() : 1);
					cid.setInvoice_detail_price(cod.getDetail_price() == null ? 0d : cod.getDetail_price());
					// increase amountPayable
					totalAmountPayable =  cid.getInvoice_detail_price() != null ? TMUtils.bigAdd(totalAmountPayable, TMUtils.bigMultiply(cid.getInvoice_detail_price(), cid.getInvoice_detail_unit())) : 0;
					// add invoice detail to list
					cids.add(cid);
				}
			}
		}
		
		totalCreditBack = Double.parseDouble(TMUtils.fillDecimalPeriod(totalCreditBack));
		totalAmountPayable = Double.parseDouble(TMUtils.fillDecimalPeriod(totalAmountPayable));
		
		// IF customer's account credit is less than final payable amount
		if(c.getBalance()<TMUtils.bigSub(totalAmountPayable, totalCreditBack)){
			return null;
		}
		
		CustomerInvoice ci = new CustomerInvoice();
		ci.setCustomer_id(c.getId());
		ci.setOrder_id(co.getId());
		ci.setCreate_date(TMUtils.parseDateYYYYMMDD(co.getOrder_using_start_str()));
		ci.setDue_date(TMUtils.getInvoiceDueDate(ci.getCreate_date(), 7));
		this.ciMapper.insertCustomerInvoice(ci);
		
		InvoicePDFCreator invoicePDF = new InvoicePDFCreator();
		invoicePDF.setCompanyDetail(cd);
		invoicePDF.setCustomer(c);
		invoicePDF.setOrg(org);
		invoicePDF.setCurrentCustomerInvoice(ci);

		ci.setAmount_payable("personal".toUpperCase().equals(c.getCustomer_type().toUpperCase()) ? totalAmountPayable : TMUtils.bigMultiply(totalAmountPayable, 1.15));
		ci.setFinal_payable_amount(TMUtils.bigSub(ci.getAmount_payable(), totalCreditBack));
		Double amount_paid = c.getBalance()>=ci.getFinal_payable_amount() ? ci.getFinal_payable_amount() : ci.getAmount_paid() == null ? 0d : ci.getAmount_paid();
		ci.setAmount_paid(amount_paid<=0 ? 0 : amount_paid);
		// balance = final payable - paid
		ci.setBalance(TMUtils.bigSub(ci.getFinal_payable_amount(), ci.getAmount_paid()));
		// Add cids into ci
		ci.setCustomerInvoiceDetails(cids);
		ci.setStatus(c.getBalance()>=ci.getFinal_payable_amount() ? "paid" : "unpaid");

		// If customer's account credit is greater than invoice's final payable amount, then paid off the invoice
		if(c.getBalance()>=ci.getFinal_payable_amount()){
			// BEGIN TRANSACTION ASSIGNMENT
			CustomerTransaction ct = new CustomerTransaction();
			// Assign invoice's paid amount to transaction's amount
			ct.setAmount(ci.getAmount_paid());
			ct.setAmount_settlement(ci.getAmount_paid());
			// Assign card_name as ddpay
			ct.setCard_name("account-credit");
			// Assign transaction's sort as type's return from order by order_id
			ct.setTransaction_sort(co.getOrder_type());
			// Assign customer_id, order_id, invoice_id to transaction's related
			// fields
			ct.setCustomer_id(c.getId());
			ct.setOrder_id(co.getId());
			ct.setInvoice_id(ci.getId());
			// Assign transaction's time as current time
			ct.setTransaction_date(new Date());
			ct.setCurrency_input("NZD");
			ct.setExecutor(u.getId());
			this.customerTransactionMapper.insertCustomerTransaction(ct);
		}

		// If customer's account credit is greater than invoice's final payable amount, then account credit = account credit - invoice's final payable amount
		c.setBalance(c.getBalance()>=ci.getFinal_payable_amount() ? TMUtils.bigSub(c.getBalance(), ci.getFinal_payable_amount()) : c.getBalance());
		
		// Add cids into db
		for (CustomerInvoiceDetail cid : cids) {
			cid.setInvoice_id(ci.getId());
			this.ciDetailMapper.insertCustomerInvoiceDetail(cid);
		}

		// Set file path
		String filePath = null;
		try {
			filePath = (String) invoicePDF.create().get("filePath");
			// generate invoice PDF
			ci.setInvoice_pdf_path(filePath);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		
		ci.getParams().put("id", ci.getId());
		this.ciMapper.updateCustomerInvoice(ci);
		c.getParams().put("id", c.getId());
		this.customerMapper.updateCustomer(c);
		
		
		return filePath;
	}
	
	// Check Pending Order
	@Transactional 
	public void checkPendingOrder(CustomerOrder customerOrderParam) throws ParseException{
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("order-warning-notice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("order-warning-notice", "sms");
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrderParam);
		
		CompanyDetail cd = this.queryCompanyDetail();

		for (CustomerOrder co : cos) {
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setOrder_status("pending-warning");
			customerOrder.getParams().put("id", co.getId());
			this.editCustomerOrder(customerOrder);
			
			Customer c = co.getCustomer();

			// Prevent template pollution
			Notification notificationEmailFinal = new Notification(notificationEmail.getTitle(), notificationEmail.getContent());
			Notification notificationSMSFinal = new Notification(notificationSMS.getTitle(), notificationSMS.getContent());
			
			MailRetriever.mailAtValueRetriever(notificationEmailFinal, c,  customerOrder, cd);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(c.getEmail());
			applicationEmail.setSubject(notificationEmailFinal.getTitle());
			applicationEmail.setContent(notificationEmailFinal.getContent());
			// binding attachment name & path to email
			if(co.getOrdering_form_pdf_path()!=null && !"".equals(co.getOrdering_form_pdf_path())){
				applicationEmail.setAttachName("ordering_form_" + co.getId() + ".pdf");
				applicationEmail.setAttachPath(co.getOrdering_form_pdf_path());
			} else {
				CustomerInvoice ciQuery = new CustomerInvoice();
				ciQuery.getParams().put("order_id", co.getId());
				CustomerInvoice ci = this.queryCustomerInvoice(ciQuery);
				applicationEmail.setAttachName("invoice_" + ci.getId() + ".pdf");
				applicationEmail.setAttachPath(ci.getInvoice_pdf_path());
			}
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			MailRetriever.mailAtValueRetriever(notificationSMSFinal, c, customerOrder, cd);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(c.getCellphone(), notificationSMSFinal.getContent());
		}
	}
	
	// Check Pending Warning Order
	@Transactional 
	public void checkPendingWarningOrder(CustomerOrder customerOrderParam) throws ParseException{
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("order-void-notice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("order-void-notice", "sms");
		List<CustomerOrder> cos = this.customerOrderMapper.selectCustomerOrdersBySome(customerOrderParam);
		
		CompanyDetail cd = this.queryCompanyDetail();

		for (CustomerOrder co : cos) {
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setOrder_status("void");
			customerOrder.getParams().put("id", co.getId());
			this.editCustomerOrder(customerOrder);
			
			Customer c = co.getCustomer();

			// Prevent template pollution
			Notification notificationEmailFinal = new Notification(notificationEmail.getTitle(), notificationEmail.getContent());
			Notification notificationSMSFinal = new Notification(notificationSMS.getTitle(), notificationSMS.getContent());
			
			MailRetriever.mailAtValueRetriever(notificationEmailFinal, c,  customerOrder, cd);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(c.getEmail());
			applicationEmail.setSubject(notificationEmailFinal.getTitle());
			applicationEmail.setContent(notificationEmailFinal.getContent());
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			MailRetriever.mailAtValueRetriever(notificationSMSFinal, c, customerOrder, cd);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(c.getCellphone(), notificationSMSFinal.getContent());
		}
	}
	
	/**
	 * BEGIN Ticket
	 */
	@Transactional
	public List<Ticket> queryTicket(Ticket t){
		return this.ticketMapper.selectTicket(t);
	}
	
	@Transactional
	public void createTicket(Ticket t){
		this.ticketMapper.insertTicket(t);
	}
	
	@Transactional
	public void editTicket(Ticket t){
		this.ticketMapper.updateTicket(t);
	}
	
	@Transactional
	public Page<Ticket> queryTicketsByPage(Page<Ticket> page){
		page.setTotalRecord(this.ticketMapper.selectTicketsSum(page));
		page.setResults(this.ticketMapper.selectTicketsByPage(page));
		return page;
	}
	
	@Transactional
	public void removeTicketById(int id){
		this.ticketMapper.deleteTicketById(id);
	}
	
	@Transactional
	public int queryTicketsBySum(Page<Ticket> page){
		return this.ticketMapper.selectTicketsSum(page);
	}

	/**
	 * END Ticket
	 */
	
	/**
	 * BEGIN TicketComment
	 */
	@Transactional
	public List<TicketComment> queryTicketComment(TicketComment tc){
		return this.ticketCommentMapper.selectTicketComment(tc);
	}
	
	@Transactional
	public void createTicketComment(TicketComment tc){
		this.ticketCommentMapper.insertTicketComment(tc);
	}
	
	@Transactional
	public void editTicketComment(TicketComment tc){
		this.ticketCommentMapper.updateTicketComment(tc);
	}
	
	@Transactional
	public Page<TicketComment> queryTicketCommentsByPage(Page<TicketComment> page){
		page.setTotalRecord(this.ticketCommentMapper.selectTicketCommentsSum(page));
		page.setResults(this.ticketCommentMapper.selectTicketCommentsByPage(page));
		return page;
	}
	
	@Transactional
	public void removeTicketCommentById(int id){
		this.ticketCommentMapper.deleteTicketCommentById(id);
	}
	
	@Transactional
	public void removeTicketCommentByTicketId(int id){
		this.ticketCommentMapper.deleteTicketCommentByTicketId(id);
	}
	
	@Transactional
	public int queryTicketCommentsBySum(Page<TicketComment> page){
		return this.ticketCommentMapper.selectTicketCommentsSum(page);
	}

	/**
	 * END TicketComment
	 */
	
	// Check Pending Warning Order
	@Transactional 
	public void checkInvoiceAfterDueNotice() throws ParseException{
		Notification notificationEmail = this.notificationMapper.selectNotificationBySort("order-void-notice", "email");
		Notification notificationSMS = this.notificationMapper.selectNotificationBySort("order-void-notice", "sms");
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("where", "query_most_unpaid_invoice");
		ciQuery.getParams().put("status", "unpaid");
		List<CustomerInvoice> cis = this.ciMapper.selectCustomerInvoices(ciQuery);
		for (CustomerInvoice ci : cis) {
			
			// Prevent template pollution
			Notification notificationEmailFinal = new Notification(notificationEmail.getTitle(), notificationEmail.getContent());
			Notification notificationSMSFinal = new Notification(notificationSMS.getTitle(), notificationSMS.getContent());
			
		}
	}
	
}

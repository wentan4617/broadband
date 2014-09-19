package com.tm.broadband.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerServiceRecord;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.Ticket;
import com.tm.broadband.model.TicketComment;
import com.tm.broadband.model.User;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.pdf.ApplicationPDFCreator;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@RestController
@SessionAttributes({ "customer", "customerOrder", "hardwares", "plans" })//
public class CRMRestController {

	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;
	private BillingService billingService;
	private PlanService planService;

	@Autowired
	public CRMRestController(CRMService crmService,
			MailerService mailerService, SystemService systemService,
			SmserService smserService
			,BillingService billingService
			,PlanService planService) {
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.smserService = smserService;
		this.billingService = billingService;
		this.planService =planService;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/personal/create", method = RequestMethod.POST)
	public JSONBean<Customer> customerPersonalCreate(
			Model model,
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer,
			BindingResult result) {

		return this.returnJsonCustomer(model, customer, result);
	}

	@RequestMapping(value = "/broadband-user/crm/customer/business/create", method = RequestMethod.POST)
	public JSONBean<Customer> customerBusinessCreate(
			Model model,
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer,
			BindingResult result) {

		return this.returnJsonCustomer(model, customer, result);
	}

	private JSONBean<Customer> returnJsonCustomer(Model model,
			Customer customer, BindingResult result) {

		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		Customer cValid = new Customer();
		cValid.getParams().put("where", "query_exist_customer_by_mobile");
		cValid.getParams().put("cellphone", customer.getCellphone());
		int count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("cellphone", "is already in use");
			return json;
		}

		cValid.getParams().put("where", "query_exist_customer_by_email");
		cValid.getParams().put("email", customer.getEmail());
		count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("email", "is already in use");
			return json;
		}

		customer.setRegister_date(new Date());
		customer.setActive_date(new Date());
		customer.setBalance(0d);

		if ("save".equals(customer.getAction())) {
			this.crmService.createCustomer(customer);
			json.setUrl("/broadband-user/crm/customer/query/redirect");
		} else if ("next".equals(customer.getAction())) {
			model.addAttribute("customer", customer);
			json.setUrl("/broadband-user/crm/customer/order/create");
		}

		return json;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/personal/edit")
	public JSONBean<Customer> customerPersonalEdit(
			Model model,
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer,
			BindingResult result, SessionStatus status) {

		return this.returnJsonCustomerEdit(customer, result, status);
	}

	@RequestMapping(value = "/broadband-user/crm/customer/business/edit")
	public JSONBean<Customer> customerBusinessEdit(
			Model model,
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer,
			BindingResult result, SessionStatus status) {

		return this.returnJsonCustomerEdit(customer, result, status);
	}

	private JSONBean<Customer> returnJsonCustomerEdit(Customer customer,
			BindingResult result, SessionStatus status) {

		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		Customer cValid = new Customer();
		cValid.getParams().put("where", "query_exist_not_self_customer_by_mobile");
		cValid.getParams().put("cellphone", customer.getCellphone());
		cValid.getParams().put("id", customer.getId());
		int count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("cellphone", "is already in use");
			return json;
		}

		cValid.getParams().put("where", "query_exist_not_self_customer_by_email");
		cValid.getParams().put("email", customer.getEmail());
		count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("email", "is already in use");
			return json;
		}

		customer.getParams().put("id", customer.getId());
		this.crmService.editCustomer(customer);

		// status.setComplete();

		json.getSuccessMap().put("alert-success", "Edit customer (ID: " + customer.getId() + ") is successful.");

		return json;
	}

	// BEGIN DDPay
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByDDPay(Model model,
			@RequestParam("process_way") String process_way,
			@RequestParam("pay_way") String pay_way,
			@RequestParam("invoice_id") int invoice_id, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Integer customer_id = null;
		Integer order_id = null;
		Double paid_amount = null;
		String process_sort = null;

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
		ci.getParams().put("id", ci.getId());

		if (ci.getBalance() <= 0d) {
			// If invoice is paid off then no reason for executing the below
			// operations
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			return json;
		}
		customer_id = ci.getCustomer_id();
		order_id = ci.getOrder_id();
		paid_amount = TMUtils.bigSub(ci.getFinal_payable_amount(), ci.getAmount_paid());

		// If not CyberPark then assign balance to paid
		if(!"cyberpark-credit".equals(pay_way)){
			ci.setAmount_paid(ci.getBalance());
		}
		ci.setFinal_payable_amount(ci.getAmount_paid());
		// Assign balance as 0.0, make this invoice paid off
		ci.setBalance(0d);
		// Assign status to paid directly, make this invoice paid off
		ci.setStatus("paid");
		// END INVOICE ASSIGNMENT

		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(order_id)) {
		case "order-term":
			process_sort = "plan-term";
			break;
		case "order-no-term":
			process_sort = "plan-no-term";
			break;
		case "order-topup":
			process_sort = "plan-topup";
			break;
		}

		User userSession = (User) req.getSession().getAttribute("userSession");

		// BEGIN TRANSACTION ASSIGNMENT
		CustomerTransaction ct = new CustomerTransaction();
		// Assign invoice's paid amount to transaction's amount
		ct.setAmount(paid_amount);
		ct.setAmount_settlement(paid_amount);
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related
		// fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("NZD");
		ct.setExecutor(userSession.getId());
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER

		json.getSuccessMap().put("alert-success",
				"Related invoice's balance had successfully been paid off!");
		
		ct = null;
		ci = null;

		return json;
	}
	// END DDPay

	// BEGIN Cash
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/cash", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByCash(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("eliminate_amount") double eliminate_amount,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Integer customer_id = null;
		Integer order_id = null;
		Double paid_amount = null;
		String process_way = "Cash";
		String process_sort = null;

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService
				.queryCustomerInvoiceById(invoice_id);
		ci.getParams().put("id", ci.getId());

		String redirectUrl = "/manual_defray/redirect/" + ci.getCustomer_id()
				+ "/" + ci.getId() + "/" + ci.getOrder_id() + "/" + process_way
				+ "/" + ci.getBalance();
		if (ci.getBalance() <= 0d) {
			// If invoice is paid off then no reason for executing the below
			// operations
			json.setUrl(redirectUrl);
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			return json;
		}
		customer_id = ci.getCustomer_id();
		order_id = ci.getOrder_id();
		paid_amount = eliminate_amount;

		ci.setAmount_paid(Double.parseDouble(TMUtils.fillDecimalPeriod(ci.getAmount_paid())));
		ci.setFinal_payable_amount(Double.parseDouble(TMUtils.fillDecimalPeriod(ci.getFinal_payable_amount())));

		// Assign (paid plus eliminate amount) to paid, make this invoice paid
		// (off)
		ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), paid_amount));
		// Assign balance as 0.0, make this invoice paid off

		ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getFinal_payable_amount(), ci.getAmount_paid(), "sub"));
		// If balance equals to 0d then paid else not_pay_off, make this invoice
		// paid (off)
		if (ci.getBalance() <= 0d) {
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT

		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(order_id)) {
		case "order-term":
			process_sort = "plan-term";
			break;
		case "order-no-term":
			process_sort = "plan-no-term";
			break;
		case "order-topup":
			process_sort = "plan-topup";
			break;
		}

		User userSession = (User) req.getSession().getAttribute("userSession");

		// BEGIN TRANSACTION ASSIGNMENT
		CustomerTransaction ct = new CustomerTransaction();
		// Assign invoice's paid amount to transaction's amount
		ct.setAmount(paid_amount);
		ct.setAmount_settlement(paid_amount);
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related
		// fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("NZD");
		ct.setExecutor(userSession.getId());
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER

		json.setUrl(redirectUrl);

		json.getSuccessMap().put("alert-success", "Cash defray had successfully been operates!");

		return json;
	}
	// END Cash

	// BEGIN Account Credit
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/account-credit", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByAccountCredit(Model model,
			@RequestParam("invoice_id") int invoice_id,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Integer customer_id = null;
		Integer order_id = null;
		Double paid_amount = null;
		String process_way = "Account Credit";
		String process_sort = null;

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
		ci.getParams().put("id", ci.getId());

		if (ci.getBalance() <= 0d) {
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			return json;
		}
		customer_id = ci.getCustomer_id();
		order_id = ci.getOrder_id();
		Customer c = this.crmService.queryCustomerById(customer_id);
		// If account credit greater equal invoice balance
		if(c.getBalance()<=0){
			json.getErrorMap().put("alert-error", "Insufficient Fund!");
			return json;
		}
		if(c.getBalance() >= ci.getBalance()){
			paid_amount = ci.getBalance();
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), ci.getBalance()));
			c.setBalance(TMUtils.bigSub(c.getBalance(), ci.getBalance()));
			ci.setBalance(0d);
		} else {
			paid_amount = c.getBalance();
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), c.getBalance()));
			ci.setBalance(TMUtils.bigSub(ci.getBalance(), c.getBalance()));
			c.setBalance(0d);
		}
		process_way+="# - "+customer_id;
		c.getParams().put("id", customer_id);
		this.crmService.editCustomer(c);
		
		// If balance equals to 0d then paid else not_pay_off, make this invoice
		// paid (off)
		if (ci.getBalance() <= 0d) {
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT

		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(order_id)) {
		case "order-term":
			process_sort = "plan-term";
			break;
		case "order-no-term":
			process_sort = "plan-no-term";
			break;
		case "order-topup":
			process_sort = "plan-topup";
			break;
		}

		User userSession = (User) req.getSession().getAttribute("userSession");

		// BEGIN TRANSACTION ASSIGNMENT
		CustomerTransaction ct = new CustomerTransaction();
		// Assign invoice's paid amount to transaction's amount
		ct.setAmount(paid_amount);
		ct.setAmount_settlement(paid_amount);
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related
		// fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("Account Credit");
		ct.setExecutor(userSession.getId());
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER
		
		
		json.getSuccessMap().put("alert-success", "Account Credit defray had successfully been operates!");

		return json;
	}
	// END Account Credit

	// BEGIN Product Plans
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/product/plans")
	public JSONBean<Plan> doProductPlans(Model model,
			@RequestParam("plan_class") String plan_class,
			@RequestParam("plan_type") String plan_type,
			HttpServletRequest req) {

		JSONBean<Plan> json = new JSONBean<Plan>();
		
		Plan planQuery = new Plan();
		planQuery.getParams().put("plan_class", plan_class);
		planQuery.getParams().put("plan_type", plan_type);
		
		List<Plan> plans = this.planService.queryPlans(planQuery);
		
		json.setModels(plans);		
		
		return json;
		
	}
	// END Product Plans

	// BEGIN Product Hardwares
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/product/hardwares")
	public JSONBean<Hardware> doProductHardwares(Model model,
			@RequestParam("hardware_class") String hardware_class,
			HttpServletRequest req) {

		JSONBean<Hardware> json = new JSONBean<Hardware>();
		
		Hardware hardwareQuery = new Hardware();
		hardwareQuery.getParams().put("where", "query_by_class_with_voip");
		hardwareQuery.getParams().put("hardware_class", "router-"+hardware_class);
		
		List<Hardware> hardwares = this.planService.queryHardwares(hardwareQuery);
		
		json.setModels(hardwares);		
		
		return json;
		
	}
	// END Product Hardwares

	// BEGIN Add Product
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/product/create", method = RequestMethod.POST)
	public JSONBean<String> doProductCreate(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("product_id") Integer product_id,
			@RequestParam("product_unit") Integer product_unit,
			@RequestParam("product_type") String product_type,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		String msg = null;
		
		if(product_type.indexOf("plan") >= 0){
			
			Plan planQuery = new Plan();
			planQuery.getParams().put("id", product_id);
			Plan plan = this.planService.queryPlan(planQuery);

			CustomerOrderDetail codCreate = new CustomerOrderDetail();
			codCreate.setOrder_id(order_id);
			codCreate.setDetail_unit(product_unit);
			codCreate.setDetail_term_period(plan.getTerm_period());
			codCreate.setDetail_name(plan.getPlan_name());
			codCreate.setDetail_desc(plan.getPlan_desc());
			codCreate.setDetail_price(plan.getPlan_price());
			codCreate.setDetail_data_flow(plan.getData_flow());
			codCreate.setDetail_plan_memo(plan.getMemo());
			codCreate.setDetail_plan_status(plan.getPlan_status());
			codCreate.setDetail_plan_type(plan.getPlan_type());
			codCreate.setDetail_plan_sort(plan.getPlan_sort());
			codCreate.setDetail_plan_group(plan.getPlan_group());
			codCreate.setDetail_type(plan.getPlan_group());
			codCreate.setDetail_plan_new_connection_fee(plan.getPlan_new_connection_fee());
			
			this.crmService.createCustomerOrderDetail(codCreate);
			
			msg = "New Plan has added to related Order. Order#"+order_id;
			
		} else if(product_type.indexOf("hardware") >= 0){
			
			Hardware hardwareQuery = new Hardware();
			hardwareQuery.getParams().put("id", product_id);
			Hardware hardware = this.planService.queryHardwareById(product_id);
			
			CustomerOrderDetail codCreate = new CustomerOrderDetail();
			codCreate.setOrder_id(order_id);
			codCreate.setDetail_unit(product_unit);
			codCreate.setDetail_name(hardware.getHardware_name());
			codCreate.setDetail_desc(hardware.getHardware_desc());
			codCreate.setDetail_type("hardware-router");
			codCreate.setIs_post(0);
			
			
			msg = "New Hardware has added to related Order. Order#"+order_id;
			
		}
		
		json.getSuccessMap().put("alert-success", msg);
		
		return json;
		
	}
	// END Add Product

	// BEGIN Voucher
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/voucher", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByVoucher(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("pin_number") String pin_number,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Integer customer_id = null;
		Integer order_id = null;
		String process_way = "Voucher";
		String process_sort = null;
		Voucher v = new Voucher();
		v.getParams().put("card_number", pin_number);
		v.getParams().put("status", "unused");
		List<Voucher> vs = this.billingService.queryVouchers(v);
		v = vs!=null && vs.size()>0 ? vs.get(0) : null;
		
		if(v==null){
			json.getErrorMap().put("alert-error", "We Haven't found this Voucher, or it had been used!");
			return json;
		}

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
		ci.getParams().put("id", ci.getId());

		if (ci.getBalance() <= 0d) {
			// If invoice is paid off then no reason for executing the below
			// operations
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			json.getSuccessMap().put("alert-success", "Voucher Haven't been used! We just change the status");
			return json;
		}
		
		Double paid_amount = v.getFace_value();
		customer_id = ci.getCustomer_id();
		
		// If voucher is less equal balance
		if(v.getFace_value() <= ci.getBalance()){
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), v.getFace_value()));
			ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getBalance(), v.getFace_value(), "sub"));
		// Else voucher is greater than balance
			json.getSuccessMap().put("alert-success", "Voucher defray had successfully been operates!");
		} else {
			Customer c = new Customer();
			c.setId(customer_id);
			c.setBalance(TMUtils.bigAdd(c.getBalance()!=null ? c.getBalance() : 0d, TMUtils.bigSub(v.getFace_value(), ci.getBalance())));
			this.crmService.editCustomer(c);
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), ci.getBalance()));
			ci.setBalance(0d);
			json.getSuccessMap().put("alert-success", "Voucher defray had successfully been operates! Surplus will be add into customer's credit");
		}
		v.setStatus("used");
		v.getParams().put("serial_number", v.getSerial_number());
		v.setCustomer_id(customer_id);
		process_way+="# - "+v.getCard_number();
		this.billingService.editVoucher(v);
		
		order_id = ci.getOrder_id();

		// paid (off)
		if (ci.getBalance() <= 0d) {
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT

		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(order_id)) {
		case "order-term":
			process_sort = "plan-term";
			break;
		case "order-no-term":
			process_sort = "plan-no-term";
			break;
		case "order-topup":
			process_sort = "plan-topup";
			break;
		}

		User userSession = (User) req.getSession().getAttribute("userSession");

		// BEGIN TRANSACTION ASSIGNMENT
		CustomerTransaction ct = new CustomerTransaction();
		// Assign invoice's paid amount to transaction's amount
		ct.setAmount(paid_amount);
		ct.setAmount_settlement(paid_amount);
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related
		// fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("Voucher");
		ct.setExecutor(userSession.getId());
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER

		return json;
	}
	// END Voucher
	
	
	// BEGIN ChangePaymentStatus
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/change-payment-status", method = RequestMethod.POST)
	public JSONBean<String> doChangePaymentStatus(Model model,
			@RequestParam("invoice_id") int invoice_id,
			HttpServletRequest req) {


		JSONBean<String> json = new JSONBean<String>();
		CustomerInvoice ci = new CustomerInvoice();
		CustomerInvoice ciTemp = this.crmService.queryCustomerInvoiceById(invoice_id);
		if("void".equals(ciTemp.getStatus())){
			ci.setStatus(ciTemp.getAmount_paid()>0 ? "not_pay_off" : "unpaid");
		}
		ci.setPayment_status("pending");
		ci.getParams().put("id", invoice_id);
		this.crmService.editCustomerInvoice(ci);

		json.getSuccessMap().put("alert-success", "Change payment status had successfully been operated!");

		return json;
	}

	// END ChangePaymentStatus
	
	
	// BEGIN ChangeStatus
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/change-status", method = RequestMethod.POST)
	public JSONBean<String> doInvoiceChangeStatus(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("status") String status,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerInvoice ci = new CustomerInvoice();
		ci.setStatus(status);
		ci.getParams().put("id", invoice_id);
		this.crmService.editCustomerInvoice(ci);

		json.getSuccessMap().put("alert-success", "Change status has successfully been operated!");

		return json;
	}

	// END ChangeStatus

	// Update order status
	@RequestMapping(value = "/broadband-user/crm/customer/order/status/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderStatusEdit(Model model,
			@RequestParam("id") Integer id,
			@RequestParam("order_status") String order_status,
			@RequestParam("old_order_status") String old_order_status,
			@RequestParam("disconnected_date_str") String disconnected_date_str,
			HttpServletRequest req) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		ProvisionLog pl = new ProvisionLog();
		User user = (User) req.getSession().getAttribute("userSession");
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(id);
		pl.setProcess_way(old_order_status+" to "+order_status);
		
		CustomerOrder co = new CustomerOrder();
		co.setOrder_status(order_status);
		if(!"".equals(disconnected_date_str.trim())){
			co.setDisconnected_date(TMUtils.parseDateYYYYMMDD(disconnected_date_str));
		}
		co.getParams().put("id", id);
		
		this.crmService.editCustomerOrder(co, pl);
		json.setModel(co);

		json.getSuccessMap().put("alert-success", "Order Status Changed!");

		return json;
	}

	// Update order type
	@RequestMapping(value = "/broadband-user/crm/customer/order/type/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderTypeEdit(Model model,
			@RequestParam("id") Integer id,
			@RequestParam("order_type") String order_type,
			HttpServletRequest req) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder co = new CustomerOrder();
		co.setOrder_type(order_type);
		co.getParams().put("id", id);
		
		this.crmService.editCustomerOrder(co);
		
		json.setModel(co);

		json.getSuccessMap().put("alert-success", "Order Type Changed!");

		return json;
	}

	// Empty service giving or next invoice create date
	@RequestMapping(value = "/broadband-user/crm/customer/order/service-giving-next-invoice-create/empty", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerServiceGivingNextInvoiceCreate(Model model,
			@RequestParam("id") Integer id,
			@RequestParam("date_type") String date_type,
			HttpServletRequest req) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder co = new CustomerOrder();
		if("service-giving".equals(date_type)){
			co.setOrder_using_start(new Date());
		} else {
			co.setNext_invoice_create_date(new Date());
		}
		co.getParams().put("id", id);
		
		this.crmService.editCustomerOrderServiceGivingNextInvoiceCreate(co);
		
		json.setModel(co);

		json.getSuccessMap().put("alert-success", "Empty Order "+date_type+" successfully!");

		return json;
	}

	// Update order due date
	@RequestMapping(value = "/broadband-user/crm/customer/order/due_date/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderDueDateEdit(Model model,
			CustomerOrder customerOrder) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		CustomerOrder co = new CustomerOrder();
		co.setOrder_due(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_due_str()));
		co.getParams().put("id", customerOrder.getId());

		this.crmService.editCustomerOrder(co);
		json.setModel(co);

		json.getSuccessMap().put("alert-success", "Order Due Date had successfully been saved!");

		return json;
	}

	// Update order belongs to
	@RequestMapping(value = "/broadband-user/crm/customer/order/belongs_to/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderBelonsToEdit(Model model,
			CustomerOrder customerOrder,
			@RequestParam("user_name") String user_name) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = customerOrder;
		co.getParams().put("id", co.getId());

		this.crmService.editCustomerOrder(co);
		json.setModel(user_name);

		json.getSuccessMap().put("alert-success", "Belongs to had successfully been edited!");

		return json;
	}

	// Update order PPPoE
	@RequestMapping(value = "/broadband-user/crm/customer/order/ppppoe/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerPPPoEEdit(Model model,
			CustomerOrder customerOrder) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		// If loginname is empty
		String loginName = customerOrder.getPppoe_loginname();
		// If password is empty
		String password = customerOrder.getPppoe_password();

		// If any of them is empty
		boolean anyEmpty = "".equals(loginName) || "".equals(password);

		if (anyEmpty) {
			if ("".equals(loginName)) {
				json.getErrorMap().put(
						customerOrder.getId() + "_pppoe_loginname_input",
						"Enter PPPoE login name!");
			}
			if ("".equals(password)) {
				json.getErrorMap().put(
						customerOrder.getId() + "_pppoe_password_input",
						"Enter PPPoE password!");
			}
			return json;
		}
		CustomerOrder co = new CustomerOrder();
		co.setPppoe_loginname(loginName);
		co.setPppoe_password(password);
		co.getParams().put("id", customerOrder.getId());

		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		json.getSuccessMap().put("alert-success",
				"PPPoE details had successfully been saved!");

		return json;
	}

	// Update SV/CVLan & RFS date
	@RequestMapping(value = "broadband-user/crm/customer/order/save/svcvlanrfsdate", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerRFSDateEdit(Model model,
			CustomerOrder customerOrder, @RequestParam("way") String way,
			HttpServletRequest req) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		// If SVLan is empty
		String svLan = customerOrder.getSvlan().trim();
		// If CVLan is empty
		String cvLan = customerOrder.getCvlan().trim();

		// If any of them is empty
		boolean anyLanEmpty = "".equals(svLan) || "".equals(cvLan);

		if (anyLanEmpty) {
			if ("".equals(svLan)) {
				json.getErrorMap().put(customerOrder.getId() + "_svlan_input",
						"Enter SVLan!");
			}
			if ("".equals(cvLan)) {
				json.getErrorMap().put(customerOrder.getId() + "_cvlan_input",
						"Enter CVLan!");
			}
			return json;
		}

		List<CustomerOrderDetail> cods = new ArrayList<CustomerOrderDetail>();
		CustomerOrder tempCO = new CustomerOrder();
		tempCO.getParams().put("id", customerOrder.getId());
		tempCO = this.crmService.queryCustomerOrder(tempCO);
		for (CustomerOrderDetail cod : tempCO.getCustomerOrderDetails()) {
			cods.add(cod);
		}

		Customer customer = this.crmService.queryCustomerById(customerOrder
				.getCustomer_id());
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort(
				"service-installation", "email");
		ApplicationEmail applicationEmail = new ApplicationEmail();
		
		Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
		customer.setOrganization(org);
		// call mail at value retriever
		MailRetriever.mailAtValueRetriever(notification, customer,
				customerOrder, cods, companyDetail);
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);

		// get sms register template from db
		notification = this.systemService.queryNotificationBySort(
				"service-installation", "sms");
		MailRetriever.mailAtValueRetriever(notification, customer,
				customerOrder, cods, companyDetail);
		// send sms to customer's mobile phone
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(),
				notification.getContent());
		
		// Provision record
		ProvisionLog pl = new ProvisionLog();
		User user = (User) req.getSession().getAttribute("userSession");
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(customerOrder.getId());
		pl.setProcess_way(this.crmService.queryCustomerOrderById(customerOrder.getId()).getOrder_status()+" to rfs");

		CustomerOrder co = new CustomerOrder();
		co.setSvlan(svLan);
		co.setCvlan(cvLan);
		co.setRfs_date(TMUtils.parseDateYYYYMMDD(customerOrder
				.getRfs_date_str()));
		co.setOrder_status("rfs");
		co.getParams().put("id", customerOrder.getId());

		this.crmService.editCustomerOrder(co, pl);
		json.setModel(co);
		json.getSuccessMap().put("alert-success",
				"svlan, cvlan and rfs_date had successfully been updated.");
		
		cods = null;
		tempCO = null;
		customer = null;
		companyDetail = null;
		notification = null;
		applicationEmail = null;
		co = null;
		
		
		return json;
	}

	// Update service giving date
	@RequestMapping(value = "/broadband-user/crm/customer/order/service_giving_date", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerServiceGivingDateEdit(Model model,
			CustomerOrder customerOrder,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			@RequestParam("way") String way,
			@RequestParam("pay_status") String pay_status,
			HttpServletRequest req) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		// Get user from session
		User user = (User) req.getSession().getAttribute("userSession");
		// ProvisionLog to insert
		ProvisionLog proLog = new ProvisionLog();
		proLog.setUser_id(user.getId());
		proLog.setOrder_id_customer(customerOrder.getId());
		proLog.setOrder_sort("customer-order");

		// CustomerOrder begin
		CustomerOrder co = new CustomerOrder();
		co.setOrder_using_start(TMUtils.parseDateYYYYMMDD(customerOrder
				.getOrder_using_start_str()));
		co.getParams().put("id", customerOrder.getId());
		co.setOrder_status("using");
		
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Customer customer = this.crmService.queryCustomerById(customerOrder.getCustomer_id());
		Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
		customer.setOrganization(org);

		if (!"order-topup".equals(customerOrder.getOrder_type())
		&& (!"order-term".equals(customerOrder.getOrder_type())
		|| ("order-term".equals(customerOrder.getOrder_type())
		&& (customerOrder.getIs_ddpay()==null
			|| (customerOrder.getIs_ddpay()!=null && !customerOrder.getIs_ddpay()))))) {
			
			Date next_invoice_create_date = null;
			Date next_invoice_create_date_flag = null;
			
			if("personal".equals(customer.getCustomer_type())){
				Calendar calNextInvoiceDay = Calendar.getInstance();
				calNextInvoiceDay.setTime(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
				// Add plan unit months
				calNextInvoiceDay.add(Calendar.MONTH, order_detail_unit);
				// Set next invoice create date flag
				next_invoice_create_date_flag = calNextInvoiceDay.getTime();
				// Minus 7 days
				calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, -7);
				// set next invoice date
				next_invoice_create_date = calNextInvoiceDay.getTime();
			} else {
				Calendar cal_7th_1hr = Calendar.getInstance();
				cal_7th_1hr.setTime(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
				cal_7th_1hr.add(Calendar.MONTH, 1);
				cal_7th_1hr.set(Calendar.DATE, 7);
				cal_7th_1hr.set(Calendar.HOUR, 0);
				cal_7th_1hr.set(Calendar.MINUTE, 0);
				cal_7th_1hr.set(Calendar.SECOND, 0);
				cal_7th_1hr.set(Calendar.MILLISECOND, 0);
				next_invoice_create_date = cal_7th_1hr.getTime();
				cal_7th_1hr.add(Calendar.DATE, 7);
				next_invoice_create_date_flag = cal_7th_1hr.getTime();
				
				// Get millis
//				long seventh12hr = cal_7th_1hr.getTimeInMillis();
//				long currentTime = System.currentTimeMillis();
//
//				// If less than seventh 1 o'clock of the month
//				if(currentTime<seventh12hr){
//					Calendar cal = cal_7th_1hr;
//					cal.set(Calendar.HOUR, 0);
//					next_invoice_create_date = cal.getTime();
//					cal.add(Calendar.DATE, 7);
//					next_invoice_create_date_flag = cal.getTime();
//				} else {
//					Calendar cal = cal_7th_1hr;
//					cal.set(Calendar.HOUR, 0);
//					cal.add(Calendar.MONTH, 1);
//					next_invoice_create_date = cal.getTime();
//					cal.add(Calendar.DATE, 7);
//					next_invoice_create_date_flag = cal.getTime();
//				}
			}
			// Final settings
			co.setNext_invoice_create_date_flag(next_invoice_create_date_flag);
			co.setNext_invoice_create_date(next_invoice_create_date);
			customerOrder.setNext_invoice_create_date(next_invoice_create_date);
			
		} else if("order-topup".equals(customerOrder.getOrder_type())) {
			Calendar cal = Calendar.getInstance(Locale.CHINA);
			cal.setTime(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			cal.add(Calendar.DAY_OF_WEEK, -1);
			// Set next invoice create date flag
			co.setNext_invoice_create_date_flag(cal.getTime());
			// Minus 1 days
			cal.add(Calendar.DAY_OF_WEEK, -2);
			// set next invoice date
			co.setNext_invoice_create_date(cal.getTime());
		}

		if ("save".equals(way)) {
			co.setOrder_type(customerOrder.getOrder_type());
			proLog.setProcess_way(customerOrder.getOrder_status() + " to using");
			
			String attachPath = null;
			String emailSort = "service-giving";
			
			if("paid".equals(pay_status)){
				User userSession = (User) req.getSession().getAttribute("userSession");
				attachPath = this.crmService.serviceGivenPaid(customer, org, customerOrder, companyDetail, userSession);
				
				// If customer account credit insufficient
				if(attachPath==null){
					
					user = null;
					proLog = null;
					co = null;
					companyDetail = null;
					customer = null;
					org = null;
					
					json.getErrorMap().put("alert-error", "Insufficient Customer Account Credit, Service Given Date can not be settled!");
					
					return json;
					
				} else {
					
					emailSort = "service-giving-paid";
				}
			}
			
			/* check order status send mailer */
			Notification notification = this.systemService.queryNotificationBySort(emailSort, "email");
//			MailRetriever.mailAtValueRetriever(notification, customer, customerOrder, companyDetail); /* call mail at value retriever */
//			ApplicationEmail applicationEmail = new ApplicationEmail();
//			applicationEmail.setAddressee(customer.getEmail());
//			applicationEmail.setSubject(notification.getTitle());
//			applicationEmail.setContent(notification.getContent());
//			if("paid".equals(pay_status) && attachPath!=null){
//				applicationEmail.setAttachPath(attachPath);
//			}
//			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			notification = this.systemService.queryNotificationBySort(emailSort, "sms"); /* get sms register template from db */
			MailRetriever.mailAtValueRetriever(notification, customer,customerOrder, companyDetail);
			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); /* send sms to customer's mobile phone */

			/*else if ("ordering-pending".equals(customerOrder.getOrder_status())
					&& !"order-term".equals(customerOrder.getOrder_type())) {

				Notification notificationEmail = this.systemService
						.queryNotificationBySort("service-giving", "email");
				Notification notificationSMS = this.systemService
						.queryNotificationBySort("service-giving", "sms");
				this.crmService.createInvoicePDF(customerOrder,
						notificationEmail, notificationSMS);

			}*/
			json.getSuccessMap().put("alert-success", "Service Given Date successfully settled!");
		} else {
			proLog.setProcess_way("editing service giving");
			Notification notification = this.systemService.queryNotificationBySort("service-giving", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			// call mail at value retriever
			MailRetriever.mailAtValueRetriever(notification, customer, customerOrder, companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			notification = this.systemService.queryNotificationBySort("service-giving", "sms");
			MailRetriever.mailAtValueRetriever(notification, customer, customerOrder, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
			json.getSuccessMap().put("alert-success", "Service Given Date successfully settled!");
		}

		this.crmService.editCustomerOrder(co, proLog);

		json.setModel(customerOrder);
		
		user = null;
		proLog = null;
		co = null;
		companyDetail = null;
		customer = null;
		org = null;
		
		return json;
	}

	// Update optional request
	@RequestMapping(value = "/broadband-user/crm/customer/order/optional_request/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderOptionalRequestEdit(
			Model model, CustomerOrder customerOrder) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		if (!"".equals(customerOrder.getOptional_request().trim())) {
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", customerOrder.getId());
			co.setOptional_request(customerOrder.getOptional_request());
			this.crmService.editCustomerOrder(co);
			json.setModel(co);
			json.getSuccessMap().put("alert-success",
					"Optional Request had just been edited!");
		} else {
			json.getErrorMap().put("alert-error",
					"Please input correct Optional Request!");
		}

		return json;
	}

	// Update is ddpay
	@RequestMapping(value = "/broadband-user/crm/customer/order/is_ddpay/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderIsDDPayEdit(
			Model model, CustomerOrder co) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		co.getParams().put("id", co.getId());
		this.crmService.editCustomerOrder(co);
		json.getSuccessMap().put("alert-success", "Is DDPay had just been edited!");

		return json;
	}

	// Update broadband asid
	@RequestMapping(value = "/broadband-user/crm/customer/order/broadband_asid/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderBroadbandASIDEdit(
			Model model, CustomerOrder customerOrder) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		if (!"".equals(customerOrder.getBroadband_asid().trim())) {
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", customerOrder.getId());
			co.setBroadband_asid(customerOrder.getBroadband_asid());
			this.crmService.editCustomerOrder(co);
			json.setModel(co);
			json.getSuccessMap().put("alert-success",
					"Broadband ASID had just been edited!");
		} else {
			json.getErrorMap().put("alert-error",
					"Please input correct Broadband ASID!");
		}

		return json;
	}

	// Update customer info
	@RequestMapping(value = "/broadband-user/crm/customer/edit")
	public Map<String, Object> toCustomerEdit(Model model,
			@RequestParam("id") int id) {

		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		User user = new User();
		List<User> users = this.systemService.queryUser(user);
		user.getParams().put("user_role", "sales");
		List<User> sales = this.systemService.queryUser(user);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customer", customer);
		map.put("users", users);
		map.put("sales", sales);

		return map;
	}

	// Update Phone
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/phone/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailPSTNEdit(Model model,
			@RequestParam("order_detail_id") int order_detail_id,
			@RequestParam("detail_name") String detail_name,
			@RequestParam("phone_number") String phone_number,
			@RequestParam("phone_type") String phone_type,
			@RequestParam("voip_password") String voip_password,
			@RequestParam("voip_assign_date") String voip_assign_date,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();

		if ("".equals(detail_name.trim()) && "".equals(phone_number.trim())) {
			json.getErrorMap().put("alert-error", "Please fill essential detail(s)!");
			return json;
		}
		
		CustomerOrderDetail cod = new CustomerOrderDetail();
		cod.setDetail_name(detail_name);
		cod.setPstn_number(phone_number);
		cod.setDetail_unit(1);
		cod.setDetail_price(0d);
		cod.setDetail_type(phone_type);
		
		if("voip".equals(phone_type)){
			
			cod.setVoip_password(voip_password);

			if(!"".equals(voip_assign_date)){
				
				String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
				Pattern pat = Pattern.compile(rexp);
				Matcher mat = pat.matcher(voip_assign_date);
				boolean dateType = mat.matches();
				if (dateType && TMUtils.isDateFormat(voip_assign_date, "-")) {
					cod.setVoip_assign_date(TMUtils.parseDateYYYYMMDD(voip_assign_date));
				} else {
					json.getErrorMap().put("alert-error", "VoIP Assign Date Format Incorrect! Must be yyy-mm-dd");
					return json;
				}
			}
		}
		
		cod.getParams().put("id", order_detail_id);
		this.crmService.editCustomerOrderDetail(cod);
		
		json.getSuccessMap().put("alert-success", "Phone number had just been edited!");

		return json;
	}

	// Add discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/offer-calling-minutes/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailCallingMinutesCreate(
			Model model, @RequestParam("order_id") int order_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("charge_amount") Double charge_amount,
			@RequestParam("calling_minutes") String calling_minutes,
			@RequestParam("calling_country") String calling_country,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		if (TMUtils.isNumber(calling_minutes)
				&& !"".trim().equals(calling_country)) {
			CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
			customerOrderDetail.setOrder_id(order_id);
			customerOrderDetail.setDetail_name(calling_minutes
					+ " minutes to " + calling_country.split("-")[0]);
			customerOrderDetail.setDetail_type("present-calling-minutes");
			customerOrderDetail.setDetail_desc(calling_country);
			customerOrderDetail.setDetail_price(charge_amount);
			customerOrderDetail.setDetail_calling_minute(Integer.parseInt(calling_minutes));
			customerOrderDetail.setDetail_unit(1);
			User user = (User) req.getSession().getAttribute("userSession");
			customerOrderDetail.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(customerOrderDetail);
			// Create Customer Order Detail Discount is successful.
			json.getSuccessMap().put(
					"alert-success",
					"Free Calling Minutes had been attached to related order! Order Id: "
							+ order_id);
		} else {
			json.getErrorMap()
					.put("alert-error",
							"Calling Minutes Format Incorrect! Must be digital numbers");
		}

		return json;
	}

	// Remove calling minutes
	@RequestMapping(value = "/broadband-user/crm/customer/order/present-calling-minutes/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailCallingMinutesRemove(
			Model model, @RequestParam("order_detail_id") int order_detail_id,
			@RequestParam("customer_id") int customer_id,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();

		this.crmService.removeCustomerOrderDetailById(order_detail_id);
		// Remove Customer Order Detail Calling Minutes is successful.
		json.getSuccessMap()
				.put("alert-success",
						"Selected Calling Minutes had been detached from related order!");

		return json;
	}

	// Add discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailDiscountCreate(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("detail_name") String detail_name,
			@RequestParam("detail_price") Double detail_price,
			@RequestParam("detail_unit") Integer detail_unit,
			@RequestParam("detail_expired") String detail_expired,
			@RequestParam("detail_desc") String detail_desc,
			@RequestParam("detail_type") String detail_type,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(detail_expired);
		boolean dateType = mat.matches();
		if (dateType && TMUtils.isDateFormat(detail_expired, "-")) {
			CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
			customerOrderDetail.setOrder_id(order_id);
			customerOrderDetail.setDetail_name(detail_name);
			customerOrderDetail.setDetail_price(detail_price);
			customerOrderDetail.setDetail_unit(detail_unit);
			customerOrderDetail.setDetail_expired(TMUtils.parseDateYYYYMMDD(detail_expired));
			customerOrderDetail.setDetail_desc(detail_desc);
			customerOrderDetail.setDetail_type(detail_type);
			User user = (User) req.getSession().getAttribute("userSession");
			customerOrderDetail.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(customerOrderDetail);
			// Create Customer Order Detail Discount is successful.
			String detailType = detail_type.equals("discount") ? "credit" : "debit";
			json.getSuccessMap().put("alert-success", "New " + detailType + " had been attached to related order! Order Id: " + order_id);
		} else {
			json.getErrorMap().put("alert-error", "Expiry Date Format Incorrect! Must be yyy-mm-dd");
		}

		return json;
	}

	// Remove discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailDiscountRemove(Model model,
			@RequestParam("order_detail_id") int order_detail_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("detail_type") String detail_type,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();

		this.crmService.removeCustomerOrderDetailById(order_detail_id);
		// Remove Customer Order Detail Discount is successful.
		String detailType = "discount".equals(detail_type) ? "Credit" : "debit".equals(detail_type) ? "Debit"
						: "early-termination-debit".equals(detail_type) ? "Early Termination Charge"
						: "termination-credit".equals(detail_type) ? "Termination Refund" : detail_type;
		json.getSuccessMap().put("alert-success", "Selected " + detailType + " had been detached from related order!");

		return json;
	}

	// Remove transaction
	@RequestMapping(value = "/broadband-user/crm/customer/transaction/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerTransactionRemove(Model model,
			@RequestParam("tx_id") int tx_id,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();

		this.crmService.removeCustomerTransactionById(tx_id);
		// Remove Customer invoice and related detail is successful.
		
		json.getSuccessMap().put("alert-success", "Selected transaction has been detached from customer, and removed permanently!");

		return json;
	}

	// Remove invoice
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerInvoiceRemove(Model model,
			@RequestParam("ci_id") int ci_id,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		
		this.crmService.removeCustomerInvoiceByIdWithDetail(ci_id);
		// Remove Customer transaction is successful.
		
		json.getSuccessMap().put("alert-success", "Selected invoice and related details has been detached from customer, and removed permanently!");

		return json;
	}

	// Add phone number
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/phone_number/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailPhoneNumberCreate(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("detail_name") String detail_name,
			@RequestParam("phone_number") String phone_number,
			@RequestParam("phone_type") String phone_type,
			@RequestParam("voip_password") String voip_password,
			@RequestParam("voip_assign_date") String voip_assign_date,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		if("".equals(detail_name.trim()) && "".equals(phone_number.trim())){
			json.getErrorMap().put("alert-error", "Please fill essential field(s)!");
			return json;
		}
		
		CustomerOrderDetail cod = new CustomerOrderDetail();
		cod.setOrder_id(order_id);
		cod.setDetail_name(detail_name);
		cod.setPstn_number(phone_number);
		cod.setDetail_unit(1);
		cod.setDetail_price(0d);
		cod.setDetail_type(phone_type);
		
		if("voip".equals(phone_type)){
			
			cod.setVoip_password(voip_password);

			if(!"".equals(voip_assign_date)){
				
				String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
				Pattern pat = Pattern.compile(rexp);
				Matcher mat = pat.matcher(voip_assign_date);
				boolean dateType = mat.matches();
				if (dateType && TMUtils.isDateFormat(voip_assign_date, "-")) {
					cod.setVoip_assign_date(TMUtils.parseDateYYYYMMDD(voip_assign_date));
				} else {
					json.getErrorMap().put("alert-error", "VoIP Assign Date Format Incorrect! Must be yyy-mm-dd");
					return json;
				}
			}
		}

		this.crmService.createCustomerOrderDetail(cod);
		json.getSuccessMap().put("alert-success", "Add new "+phone_type+" successfully!");

		return json;
	}

	// Regenerate invoice PDF
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/generate/{invoiceId}")
	public JSONBean<String> generateInvoicePDF(Model model,
			@PathVariable(value = "invoiceId") int invoiceId) {

		JSONBean<String> json = new JSONBean<String>();

		this.crmService.createInvoicePDFByInvoiceID(invoiceId, false);
		json.getSuccessMap().put("alert-success",
				"Regenerate Invoice Successfully!");

		return json;
	}

	// Regenerate application form PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/application_form/regenerate/{order_id}")
	public JSONBean<String> regenerateOrderApplicationForm(Model model,
			@PathVariable("order_id") int order_id) {

		JSONBean<String> json = new JSONBean<String>();

		CustomerOrder co = this.crmService.queryCustomerOrderById(order_id);

		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
		String orderPDFPath = null;
		try {
			orderPDFPath = new ApplicationPDFCreator(co.getCustomer(), co, co
					.getCustomer().getOrganization()).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		co.getParams().put("id", co.getId());
		co.setOrder_pdf_path(orderPDFPath);
		this.crmService.editCustomerOrder(co);
		// END SET NECESSARY INFO AND GENERATE ORDER PDF
		// Regenerate order application PDF is successful.
		json.getSuccessMap().put("alert-success",
				"Generate New Order Application Successfully!");

		return json;
	}

	// termed manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/termed/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateTermedOrderInvoice(Model model,
			@RequestParam("id") int id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = this.crmService.queryCustomerOrderById(id);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);

		try {
			this.crmService.createTermPlanInvoiceByOrder(co
					, isRegenerateInvoice
					, isRegenerateInvoice ? false : true);
			json.getSuccessMap().put("alert-success",
					"Manually Generate Termed Invoice is successful");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return json;
	}

	// no term manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/no-term/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateNoTermOrderInvoice(Model model,
			@RequestParam("id") int id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = this.crmService.queryCustomerOrderById(id);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);

		try {
			Notification notificationEmail = this.systemService
					.queryNotificationBySort("invoice", "email");
			Notification notificationSMS = this.systemService
					.queryNotificationBySort("invoice", "sms");
			this.crmService.createInvoicePDFBoth(co, notificationEmail,
					notificationSMS,
					isRegenerateInvoice ? false : true,
					isRegenerateInvoice);
			json.getSuccessMap().put("alert-success",
					"Manually Generate No Term Invoice is successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	// termed manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/topup/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateTopupOrderInvoice(Model model,
			@RequestParam("id") int id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = this.crmService.queryCustomerOrderById(id);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);

		try {
			Notification notificationEmail = this.systemService.queryNotificationBySort("invoice", "email");
			Notification notificationSMS = this.systemService.queryNotificationBySort("invoice", "sms");
			this.crmService.createTopupPlanInvoiceByOrder(co
					, new Notification(notificationEmail.getTitle(), notificationEmail.getContent())
					, new Notification(notificationSMS.getTitle(), notificationSMS.getContent())
					, isRegenerateInvoice
					, isRegenerateInvoice ? false : true);
			
			json.getSuccessMap().put("alert-success", "Manually Generate Topup Invoice is successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	// EarlyTerminationCharge Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/early-termination-charge/invoice/generate", method = RequestMethod.POST)
	public JSONBean<String> doEarlyTerminationChargeInvoice(Model model,
			@RequestParam("id") int id,
			@RequestParam("terminatedDate") String terminatedDate,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		try {
			if (TMUtils.isDateFormat(terminatedDate, "-")) {
				this.crmService
						.createEarlyTerminationInvoice(id,
								TMUtils.parseDateYYYYMMDD(terminatedDate),
								user.getId());
				json.getSuccessMap()
						.put("alert-success",
								"Early termination charge invoice had just been generated!");
			} else {
				json.getErrorMap().put("alert-error",
						"Terminated Date Format Incorrect!");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return json;
	}

	// TerminationRefund Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/termination-credit/invoice/generate", method = RequestMethod.POST)
	public JSONBean<String> doTerminationRefundInvoice(Model model,
			@RequestParam("id") int id,
			@RequestParam("terminatedDate") String terminatedDate,
			@RequestParam("productName") String productName,
			@RequestParam("monthlyCharge") Double monthlyCharge,
			@RequestParam("accountNo") String accountNo,
			@RequestParam("accountName") String accountName,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		try {

			if (TMUtils.isDateFormat(terminatedDate, "-")) {
				this.crmService.createTerminationRefundInvoice(id,
						TMUtils.parseDateYYYYMMDD(terminatedDate), user,
						accountNo, accountName, monthlyCharge, productName);
				json.getSuccessMap().put("alert-success",
						"Termination refund invoice had just been generated!");
			} else {
				json.getErrorMap().put("alert-error",
						"Terminated Date Format Incorrect!");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return json;
	}

	// Detail EarlyTerminationCharge Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/early-termination-debit/save", method = RequestMethod.POST)
	public JSONBean<String> doDetailEarlyTerminationChargeInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("terminatedDate") String terminatedDate,
			@RequestParam("detail_type") String detail_type,
			HttpServletRequest req) throws ParseException {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		if (TMUtils.isDateFormat(terminatedDate, "-")) {

			CustomerOrder co = this.crmService.queryCustomerOrderById(order_id);
			Map<String, Object> map = TMUtils.earlyTerminationDatesCalculation(
					co.getOrder_using_start(),
					TMUtils.parseDateYYYYMMDD(terminatedDate));

			CustomerOrderDetail cod = new CustomerOrderDetail();
			cod.setOrder_id(order_id);
			cod.setDetail_name("Early Termination Debit");
			cod.setDetail_desc("Plan End Date: "
					+ TMUtils.dateFormatYYYYMMDD((Date) map
							.get("legal_termination_date")));
			cod.setDetail_plan_memo("Termination Charge: "
					+ TMUtils.fillDecimalPeriod(String.valueOf(map
							.get("charge_amount"))) + " Service Given: "
					+ co.getOrder_using_start_str() + " Terminated Date: "
					+ terminatedDate + " Month Between: "
					+ (Integer) map.get("months_between_begin_end")
					+ " month(s)");
			cod.setDetail_price((Double) map.get("charge_amount"));
			cod.setDetail_type(detail_type);
			cod.setDetail_unit(1);
			cod.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(cod);
			json.getSuccessMap()
					.put("alert-success",
							"Early termination charge detail had been attached to related order!");
		} else {
			json.getErrorMap().put("alert-error", "Terminated Date Format Incorrect!");
		}

		return json;
	}

	// Detail TerminationRefund Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/termination-credit/save", method = RequestMethod.POST)
	public JSONBean<String> doDetailTerminationRefundInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("terminatedDate") String terminatedDate,
			@RequestParam("detail_type") String detail_type,
			@RequestParam("monthlyCharge") Double monthlyCharge,
			HttpServletRequest req) throws ParseException {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		if (TMUtils.isDateFormat(terminatedDate, "-")) {

			Map<String, Object> map = TMUtils.terminationRefundCalculations(TMUtils.parseDateYYYYMMDD(terminatedDate), monthlyCharge);

			CustomerOrderDetail cod = new CustomerOrderDetail();
			cod.setOrder_id(order_id);
			cod.setDetail_name("Termination Credit");
			cod.setDetail_desc("Residual Day(s): " + (Integer) map.get("remaining_days"));
			cod.setDetail_plan_memo("Total Credit Back: "
					+ TMUtils.fillDecimalPeriod((Double) map.get("refund_amount"))
					+ " Terminated Date: "
					+ terminatedDate
					+ " Month Max Date: "
					+ TMUtils.dateFormatYYYYMMDD((Date) map.get("last_date_of_month")));
			cod.setDetail_price((Double) map.get("refund_amount"));
			cod.setDetail_unit(1);
			cod.setDetail_type(detail_type);
			cod.setUser_id(user.getId());

			this.crmService.createCustomerOrderDetail(cod);
			json.getSuccessMap().put("alert-success", "Termination refund detail had been attached to related order!");
		} else {
			json.getErrorMap().put("alert-error", "Terminated Date Format Incorrect!");
		}

		return json;
	}

	// Create new customer service record
	@RequestMapping(value = "/broadband-user/crm/customer-service-record/create", method = RequestMethod.POST)
	public JSONBean<String> doCustomerServiceRecordCreate(Model model
			,@RequestParam("customer_id") Integer customer_id
			,@RequestParam("description") String description
			,HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		if(!"".equals(description.trim())){
			CustomerServiceRecord csr = new CustomerServiceRecord();
			csr.setCustomer_id(customer_id);
			csr.setDescription(description);
			User user = (User) req.getSession().getAttribute("userSession");
			csr.setUser_id(user.getId());
			csr.setCreate_date(new Date());
			this.crmService.createCustomerServiceRecord(csr);
			json.getSuccessMap().put("alert-success", "New Customer Service Record had been attached to related customer!");
		} else {
			json.getErrorMap().put("alert-error", "Description Can't Not Be Empty!");
		}
		
		return json;
	}

	// Edit detail
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/plan/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailPlanEdit(Model model,
			CustomerOrderDetail cod,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		cod.getParams().put("id", cod.getId());
		this.crmService.editCustomerOrderDetail(cod);
		
		json.getSuccessMap().put("alert-success", "Plan detail edited successfully!");
		return json;
	}

	// Edit detail
	@RequestMapping(value = "/broadband-user/crm/customer/order/pay-off/receipt", method = RequestMethod.POST)
	public JSONBean<String> doPayOffOrder(Model model,
			@RequestParam("id") Integer id,
			@RequestParam("customerId") Integer customerId,
			@RequestParam("paid_amount") Double paid_amount,
			@RequestParam("order_pay_way") String order_pay_way,
			@RequestParam("order_total_price") Double order_total_price,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Organization org = this.crmService.queryOrganizationByCustomerId(customerId);
		Customer c = this.crmService.queryCustomerByIdWithCustomerOrder(customerId);
		c.setCustomerOrder(c.getCustomerOrders().get(0));
		c.setOrganization(org);
		
		Double cyberParkCredit = 0d;
		
		if("account-credit".equals(order_pay_way)){
			cyberParkCredit = TMUtils.bigSub(order_total_price, c.getBalance()==null ? 0d : c.getBalance());
			paid_amount = 0d;
		}
		
		if("cyberpark-credit".equals(order_pay_way)){
			cyberParkCredit = order_total_price;
			paid_amount = 0d;
		}
		
		if(!"account-credit".equals(order_pay_way)
		&& !"cyberpark-credit".equals(order_pay_way)
		&& (order_total_price > paid_amount)){
			cyberParkCredit = TMUtils.bigSub(order_total_price, paid_amount);
		}
		
		if(!"account-credit".equals(order_pay_way) || !"cyberpark-credit".equals(order_pay_way)){
			Customer customer = new Customer();
			customer.setBalance(TMUtils.bigAdd(c.getBalance()==null ? 0d : c.getBalance(), paid_amount));
			customer.getParams().put("id", c.getId());
			this.crmService.editCustomer(customer);
		}
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		CustomerTransaction ct = null;
		
		if(paid_amount > 0){
			ct = new CustomerTransaction();
			ct.setAmount(paid_amount);
			ct.setAmount_settlement(paid_amount);
			ct.setCard_name("cash".equals(order_pay_way) ? "Cash" : "Account2Account");
			ct.setTransaction_date(new Date());
			ct.setCustomer_id(customerId);
			ct.setOrder_id(c.getCustomerOrder().getId());
			ct.setTransaction_type("Ordering Form Defrayment");
			ct.setTransaction_sort(c.getCustomerOrder().getOrder_type());
			ct.setExecutor(user.getId());
			this.crmService.createCustomerTransaction(ct);
		}
		
		if(cyberParkCredit > 0d){
			CustomerOrderDetail cod = new CustomerOrderDetail();
			cod.setOrder_id(id);
			cod.setDetail_name("CyberPark Credit");
			cod.setDetail_price(cyberParkCredit);
			cod.setDetail_type("discount");
			cod.setDetail_unit(1);
			cod.setUser_id(user.getId());
			System.out.println("CyberPark Credit: "+cyberParkCredit);
			this.crmService.createCustomerOrderDetail(cod);
		}
		
		CustomerOrder co = new CustomerOrder();
		String receiptPath = this.crmService.createReceiptPDFByDetails(c);
		co.setReceipt_pdf_path(receiptPath);
		if("pending".equals(c.getCustomerOrder().getOrder_status())
		  || "pending-warning".equals(c.getCustomerOrder().getOrder_status())
		  || "void".equals(c.getCustomerOrder().getOrder_status())){
			co.setOrder_status("paid");
		} else if("ordering-pending".equals(c.getCustomerOrder().getOrder_status())) {
			co.setOrder_status("ordering-paid");
		}
		
		ProvisionLog pl = new ProvisionLog();
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(id);
		pl.setProcess_way(c.getCustomerOrder().getOrder_status()+" to "+co.getOrder_status()+" receipt");
		co.getParams().put("id", id);
		this.crmService.editCustomerOrder(co, pl);
		
		json.getSuccessMap().put("alert-success", "Order paid off succeccfully!");
		return json;
	}
	
	@RequestMapping("/broadband-user/crm/customer/view/{pageNo}")
	public Page<Customer> doCustomerView(@PathVariable("pageNo") int pageNo, Customer customerQuery, SessionStatus status) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by register_date desc");
		if (customerQuery != null) {
			page.getParams().put("id", customerQuery.getId());
			page.getParams().put("cellphone", customerQuery.getCellphone());
			page.getParams().put("email", customerQuery.getEmail());
			page.getParams().put("address", customerQuery.getAddress());
			page.getParams().put("pstn", customerQuery.getPstn());
			page.getParams().put("order_id", customerQuery.getOrder_id());
			page.getParams().put("invoice_id", customerQuery.getInvoice_id());
			page.getParams().put("customer_type", customerQuery.getCustomer_type());
		}
		this.crmService.queryCustomersByPage(page);
		status.setComplete();
		return page;
	}
	
	// Ticket
	// Check Customer Existence
	@RequestMapping(value = "broadband-user/crm/ticket/check-customer-existence", method = RequestMethod.POST)
	public JSONBean<Customer> doCheckCustomerExistence(Model model,
			@RequestParam("keyword") String keyword,
			HttpServletRequest req) {

		JSONBean<Customer> json = new JSONBean<Customer>();
		
		if("".equals(keyword.trim())){
			json.getErrorMap().put("customerNull", "Please input something and check again!");
			return json;
		}
		
		Customer cQuery = new Customer();
		cQuery.getParams().put("where", "query_exist_customer_by_keyword");
		cQuery.getParams().put("keyword", keyword);
		List<Customer> cs = this.crmService.queryCustomers(cQuery);
		
		if(cs==null || cs.size()<=0){
			json.getErrorMap().put("customerNull", "Couldn't get customer's essential details by provided keyword!");
		} else {
			json.getSuccessMap().put("customerNotNull", "Got this customer's essential details!");
			
			Customer c = cs.get(0);
			
			// If Business Customer
			if(!"personal".equals(c.getCustomer_type())){
				Organization org = this.crmService.queryOrganizationByCustomerId(c.getId());
				if(org.getHolder_name().contains(" ")){
					c.setFirst_name(org.getHolder_name().split(" ")[0]);
					c.setLast_name(org.getHolder_name().split(" ")[1]);
				} else {
					c.setLast_name(org.getHolder_name());
				}
				
				if(!"".equals(org.getHolder_email().trim())){
					c.setEmail(org.getHolder_email());
				}
			}
			
			json.setModel(c);
		}
		
		return json;
	}

	@RequestMapping("/broadband-user/crm/ticket/view/{pageNo}")
	public Page<Ticket> ticketView(Model model
			, @PathVariable("pageNo") int pageNo
			, HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		Page<Ticket> page = new Page<Ticket>();
		page.setPageNo(pageNo);
		page.setPageSize(500);
		page.getParams().put("orderby", "order by create_date desc");
		page.getParams().put("where", "query_by_public_protected");
		page.getParams().put("public_protected", "public_protected");
		page.getParams().put("protected_viewer", user.getId());
		page.getParams().put("or_user_id", user.getId());
		
		Ticket ticket = (Ticket) req.getSession().getAttribute("ticketFilter");
		
		if(ticket!=null){
			
			boolean userId = ticket.getUser_id()!=null;
			boolean publishType = !"".equals(ticket.getPublish_type());
			boolean ticketType = !"".equals(ticket.getTicket_type());
			boolean ticketStatus = !"".equals(ticket.getNot_yet_viewer());
			boolean existingCustomer = !"".equals(ticket.getExisting_customer_str());
			boolean commented = !"".equals(ticket.getCommented_str());
			
			if(userId || publishType || ticketType || ticketStatus || existingCustomer || commented){
				page.getParams().remove("or_user_id");
			}
			
			if(userId){
				if(ticket.getUser_id()==1){
					page.getParams().put("user_id", user.getId());
				} else {
					page.getParams().put("not_user_id", user.getId());
				}
			}
			if(publishType){
				page.getParams().remove("public_protected");
				if("public".equals(ticket.getPublish_type())){
					page.getParams().put("public", "public");
				}
				if("protected".equals(ticket.getPublish_type()) && userId){
					page.getParams().put("protected", "protected");
				} else if("protected".equals(ticket.getPublish_type())) {
					page.getParams().put("protected", "protected");
				}
			}
			if(ticketType){
				page.getParams().put("ticket_type", ticket.getTicket_type());
			}
			if(ticketStatus){
				if("1".equals(ticket.getNot_yet_viewer())){
					page.getParams().put("not_yet_viewer", user.getId());
				} else {
					page.getParams().put("viewed_viewer", user.getId());
				}
			}
			if(existingCustomer){
				page.getParams().put("existing_customer", "true".equals(ticket.getExisting_customer_str()) ? true : false);
			}
			if(commented){
				page.getParams().put("commented", "true".equals(ticket.getCommented_str()) ? true : false);
			}
		}
		
		this.crmService.queryTicketsByPage(page);
		List<Ticket> ts = new ArrayList<Ticket>();
		for (Ticket t : page.getResults()) {
			if((t.getNot_yet_review_comment_viewer()!=null && t.getNot_yet_review_comment_viewer().contains(user.getId().toString()))
			|| (t.getNot_yet_viewer()!=null && t.getNot_yet_viewer().contains(user.getId().toString()))){
				if(t.getNot_yet_review_comment_viewer()!=null
				   && t.getNot_yet_review_comment_viewer().contains(user.getId().toString())){
					t.setNotYetReview(true);
				}
				if(t.getNot_yet_viewer()!=null
				   && t.getNot_yet_viewer().contains(user.getId().toString())){
					t.setMentioned(true);
				}
				if(t.getUser_id().equals(user.getId())){
					t.setMyTicket(true);
				}
				ts.add(t);
			}
		}
		for (Ticket t : page.getResults()) {
			if((t.getNot_yet_review_comment_viewer()==null || !t.getNot_yet_review_comment_viewer().contains(user.getId().toString()))
			&& (t.getNot_yet_viewer()==null || !t.getNot_yet_viewer().contains(user.getId().toString()))){
				if(t.getUser_id().equals(user.getId())){
					t.setMyTicket(true);
				}
				ts.add(t);
			}
		}
		page.setResults(ts);
		
		user = null;
		ticket = null;
		
		return page;
	}
	
	@RequestMapping(value = "/broadband-user/crm/ticket/view/filter")
	public void doPlanViewFilter(Model model, Ticket ticket
			,HttpServletRequest req) {
		req.getSession().setAttribute("ticketFilter", ticket);
	}

	@RequestMapping(value="/broadband-user/crm/ticket/create", method=RequestMethod.POST)
	public JSONBean<Customer> doTicketCreate(Model model
			, @RequestBody Ticket ticket
			, HttpServletRequest req) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		String first_name = ticket.getFirst_name();
		String last_name = ticket.getLast_name();
		String cellphone = ticket.getCellphone();
		String email = ticket.getEmail();
		String description = ticket.getDescription();
		
		if(("".equals(first_name.trim()) && "".equals(last_name.trim()))
		|| ("".equals(cellphone.trim()) && "".equals(email.trim()))
		|| "".equals(description.trim())
		|| (!"public".equals(ticket.getPublish_type()) && ticket.getUseridArray().length<=0)){
			if("".equals(first_name.trim()) && "".equals(last_name.trim())){
				json.getErrorMap().put("emptyContactName", "At least one of the name field!");
			}
			if("".equals(cellphone.trim()) && "".equals(email.trim())){
				json.getErrorMap().put("emptyContactDetail", "At least one of the contact field!");
			}
			if("".equals(description.trim())){
				json.getErrorMap().put("emptyDescription", "Please describe customer's request(s)!");
			}
			if((!"public".equals(ticket.getPublish_type()) && ticket.getUseridArray().length<=0)){
				json.getErrorMap().put("emptyUseridArray", "Please select one or more users!");
			}
			return json;
		}
		
		User user = (User) req.getSession().getAttribute("userSession");
		ticket.setUser_id(user.getId());
		
		StringBuffer not_yet_viewerBuff = new StringBuffer();
		
		if("public".equals(ticket.getPublish_type())){
			List<User> users = this.systemService.queryUser(new User());
			for (int i = 0; i < users.size(); i++) {
				if("sales".equals(users.get(i).getUser_role())){
					continue;
				}
				not_yet_viewerBuff.append(users.get(i).getId());
				if(i < users.size()-1){
					not_yet_viewerBuff.append(",");
				}
			}
		} else {
			StringBuffer protected_viewerBuff = new StringBuffer();
			
			protected_viewerBuff.append(user.getId()+",");
			
			for (int i = 0; i < ticket.getUseridArray().length; i++) {
				not_yet_viewerBuff.append(ticket.getUseridArray()[i]);
				protected_viewerBuff.append(ticket.getUseridArray()[i]);
				if(i < ticket.getUseridArray().length-1){
					not_yet_viewerBuff.append(",");
					protected_viewerBuff.append(",");
				}
			}
			
			ticket.setProtected_viewer(protected_viewerBuff.toString());
			
			protected_viewerBuff = null;
		}
		
		ticket.setNot_yet_viewer(not_yet_viewerBuff.toString());
		
		ticket.setCreate_date(new Date());
		ticket.setCommented(false);

		ticket.setCustomer_id(ticket.isExisting_customer() ? ticket.getCustomer_id() : null);
		
		this.crmService.createTicket(ticket);
		
		json.setUrl("/broadband-user/crm/ticket/view");
		
		not_yet_viewerBuff = null;
		user = null;
		return json;
	}

	// Update ticket info
	@RequestMapping(value = "/broadband-user/crm/ticket/edit")
	public Map<String, Object> toTicketEdit(Model model,
			@RequestParam("id") int id) {

		Ticket tQuery = new Ticket();
		tQuery.getParams().put("id", id);
		Ticket ticket = this.crmService.queryTicket(tQuery).get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ticket", ticket);

		return map;
	}

	// Update ticket info
	@RequestMapping(value = "/broadband-user/crm/ticket/comment/view")
	public Page<TicketComment> toTicketCommentView(Model model,
			@RequestParam("ticket_id") int ticket_id,
			@RequestParam("pageNo") int pageNo) {

		Page<TicketComment> page = new Page<TicketComment>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by create_date desc");
		page.getParams().put("ticket_id", ticket_id);
		this.crmService.queryTicketCommentsByPage(page);

		return page;
	}

	@RequestMapping(value="/broadband-user/crm/ticket/comment/create", method=RequestMethod.POST)
	public JSONBean<TicketComment> doTicketCommentCreate(Model model,
			@RequestParam("ticket_id") int ticket_id,
			@RequestParam("content") String content,
			HttpServletRequest req) {
		
		JSONBean<TicketComment> json = new JSONBean<TicketComment>();
		
		if(!"".equals(content.trim())){
			
			TicketComment tc = new TicketComment();
			tc.setTicket_id(ticket_id);
			tc.setContent(content);
			User user = (User) req.getSession().getAttribute("userSession");
			tc.setUser_id(user.getId());
			tc.setCreate_date(new Date());
			this.crmService.createTicketComment(tc);
			
			// Reverse all viewed to not yet view
			Ticket t = new Ticket();
			t.getParams().put("id", ticket_id);
			t = this.crmService.queryTicket(t).get(0);
		
			if(t.getViewed_viewer()!=null && !"".equals(t.getViewed_viewer())){
				String viewed[] = t.getViewed_viewer().split(",");
				List<String> finalUserIds = new ArrayList<String>();
				for (int i = 0; i < viewed.length; i++) {
					if(!user.getId().toString().equals(viewed[i])){
						finalUserIds.add(viewed[i]);
					}
				}
				StringBuffer finalNotYetReviewCommentBuff = new StringBuffer();
				for (int i = 0; i < finalUserIds.size(); i++) {
					finalNotYetReviewCommentBuff.append(finalUserIds.get(i));
					if(i<finalUserIds.size()-1){
						finalNotYetReviewCommentBuff.append(",");
					}
				}
				t.setNot_yet_review_comment_viewer(finalNotYetReviewCommentBuff.toString());
				t.setCommented(true);
				t.getParams().put("id", ticket_id);
				this.crmService.editTicket(t);
			}
			
			json.getSuccessMap().put("alert-success", "New Comment had been attached to related ticket!");
			
			tc = null;
			user = null;
		} else {
			json.getErrorMap().put("alert-error", "Conetnt can't not be empty!");
		}
		
		
		
		return json;
		
	}

	@RequestMapping(value="/broadband-user/crm/ticket/edit", method=RequestMethod.POST)
	public JSONBean<Ticket> doTicketEdit(Model model,
			Ticket ticket){
		
		JSONBean<Ticket> json = new JSONBean<Ticket>();
		
		boolean isError = false;
		
		if("".equals(ticket.getFirst_name()) && "".equals(ticket.getLast_name())){
			json.getErrorMap().put("first_name", "At least fill one name!");
			json.getErrorMap().put("last_name", "At least fill one name!");
			isError = true;
		}
		if("".equals(ticket.getCellphone()) && "".equals(ticket.getEmail())){
			json.getErrorMap().put("cellphone", "At least fill one contact detail!");
			json.getErrorMap().put("email", "At least fill one contact details!");
			isError = true;
		}
		if("".equals(ticket.getDescription())){
			json.getErrorMap().put("description", "Can't be left empty!");
			isError = true;
		}
		
		if(isError){
			return json;
		} else {
			ticket.getParams().put("id", ticket.getId());
			this.crmService.editTicket(ticket);
			json.getSuccessMap().put("alert-success", "Successfully modified ticket details!");
		}
		
		json.setModel(ticket);
		
		return json;
	}

	// Regenerate Ordering Form
	@RequestMapping(value = "/broadband-user/crm/customer/order/ordering-form/pdf/regenerate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateOrderingForm(Model model,
			@RequestParam("id") int id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = this.crmService.queryCustomerOrderById(id);
		
		Customer c = this.crmService.queryCustomerByIdWithCustomerOrder(co.getCustomer_id());
		
		c.setOrganization(co.getCustomer().getOrganization());
		
		c.setCustomerOrder(co);
		
		this.crmService.createOrderingFormPDFByDetails(c);
		json.getSuccessMap().put("alert-success", "Successfully Regenerate Ordering Form");

		return json;
	}

	// Regenerate Receipt
	@RequestMapping(value = "/broadband-user/crm/customer/order/receipt/pdf/regenerate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateReceipt(Model model,
			@RequestParam("id") int id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = this.crmService.queryCustomerOrderById(id);
		
		Customer c = this.crmService.queryCustomerByIdWithCustomerOrder(co.getCustomer_id());
		
		c.setOrganization(co.getCustomer().getOrganization());
		
		c.setCustomerOrder(co);
		
		this.crmService.createReceiptPDFByDetails(c);
		json.getSuccessMap().put("alert-success", "Successfully Regenerate Receipt");

		return json;
	}

	
	// BEGIN Topup Account Credit By Pay Way
	@RequestMapping(value = "/broadband-user/crm/customer/account-credit/defrayment/pay-way", method = RequestMethod.POST)
	public JSONBean<String> doTopupAccountCreditByPayway(Model model,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("process_way") String process_way,
			@RequestParam("defray_amount") double defray_amount,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		if(defray_amount<0){
			json.getErrorMap().put("alert-error", "Please input an amount to continue!");
			return json;
		}
		
		Customer c = this.crmService.queryCustomerById(customer_id);
		c.setBalance(TMUtils.bigAdd(c.getBalance()!=null ? c.getBalance() : 0d, defray_amount));
		c.getParams().put("id", customer_id);
		this.crmService.editCustomer(c);
		
		User userSession = (User) req.getSession().getAttribute("userSession");

		CustomerTransaction ct = new CustomerTransaction();
		ct.setAmount(defray_amount);
		ct.setAmount_settlement(defray_amount);
		ct.setCard_name(process_way);
		ct.setCustomer_id(customer_id);
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("NZD");
		ct.setExecutor(userSession.getId());

		this.crmService.createCustomerTransaction(ct);

		json.getSuccessMap().put("alert-success", "Cash defray had successfully been operates!");

		return json;
	}
	// END Topup Account Credit By Pay Way


	// BEGIN Topup Account Credit By Voucher
	@RequestMapping(value = "/broadband-user/crm/customer/account-credit/defrayment/voucher", method = RequestMethod.POST)
	public JSONBean<String> doTopupAccountCreditByVoucher(Model model,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("pin_number") String pin_number,
			HttpServletRequest req) {
		
		return this.crmService.doTopupAccountCreditByVoucher(customer_id, pin_number, req, this.billingService);
		
	}
	// END Topup Account Credit By Voucher
}

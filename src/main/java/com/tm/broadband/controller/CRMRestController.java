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
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerOrderDetailDeleteRecord;
import com.tm.broadband.model.CustomerOrderDetailRecoverableList;
import com.tm.broadband.model.CustomerServiceRecord;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.InviteRates;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.TerminationRefund;
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
import com.tm.broadband.validator.mark.CustomerValidatedMark;
import com.tm.broadband.validator.mark.PromotionCodeValidatedMark;
import com.tm.broadband.validator.mark.TransitionCustomerOrderValidatedMark;

@RestController
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

	@RequestMapping(value = "/broadband-user/crm/customer/sms/send")
	public JSONBean<Customer> customerSendSMSByCellphone(
			Model model,
			@RequestParam("customer_id") Integer customer_id,
			@RequestParam("content") String content) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		
		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customer_id);
		Customer c = this.crmService.queryCustomer(cQuery);
		
		if((c.getCellphone()!=null && !"".equals(c.getCellphone().trim()))
		&& (content!=null && !"".equals(content.trim()))){
			
			this.crmService.sendCustomerSMSByCellphone(c.getCellphone(), content);
			
			json.getSuccessMap().put("alert-success", "Content has been successfully sent to specific cellphone number!");
			
		} else {
			
			json.getErrorMap().put("alert-error", "Could not send SMS without assign a cellphone number or content!");
			
		}
		
		
		return json;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/create", method = RequestMethod.POST)
	public JSONBean<Customer> customerCreate(
			Model model, @RequestBody Customer customer,
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
//			this.crmService.createCustomer(customer);
			json.setUrl("/broadband-user/crm/customer/query/redirect");
		} else if ("next".equals(customer.getAction())) {
			model.addAttribute("customer", customer);
			json.setUrl("/broadband-user/crm/customer/order/create");
		}

		return json;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/edit", method = RequestMethod.POST)
	public JSONBean<Customer> customerEdit(
			Model model, @RequestBody Customer customer,
			BindingResult result, SessionStatus status) {

		return this.returnJsonCustomerEdit(customer, result, status);
	}

	@RequestMapping(value = "/broadband-user/crm/customer/credit-card/create", method = RequestMethod.POST)
	public JSONBean<CustomerCredit> customerCreditCardCreate(Model model,
			CustomerCredit cc) {

		JSONBean<CustomerCredit> json = new JSONBean<CustomerCredit>();
		
		String errorMsg = "";
		
		if("".equals(cc.getCard_number().trim())){
			errorMsg = "Card Number Couldn't be Empty";
		} else if("".equals(cc.getHolder_name().trim())){
			errorMsg = "Holder Name Couldn't be Empty";
		} else if("".equals(cc.getSecurity_code().trim())){
			errorMsg = "Security Code Couldn't be Empty";
		} else if("".equals(cc.getExpiry_date().trim())){
			errorMsg = "Expiry Date Couldn't be Empty";
		}
		if(!"".equals(errorMsg)){
			json.getErrorMap().put("alert-error", errorMsg);
		} else {
			this.crmService.createCustomerCredit(cc);
		}
		
		
		return json;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/credit-card/edit", method = RequestMethod.POST)
	public JSONBean<CustomerCredit> editCreditCardCreate(Model model,
			CustomerCredit cc) {

		JSONBean<CustomerCredit> json = new JSONBean<CustomerCredit>();
		
		cc.getParams().put("id", cc.getId());
		this.crmService.editCustomerCredit(cc);
		
		json.getSuccessMap().put("alert-success", "Successfully Updated Specific Credit Card Details!");
		
		return json;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/credit-card/delete", method = RequestMethod.POST)
	public JSONBean<CustomerCredit> deleteCreditCardCreate(Model model,
			CustomerCredit cc) {

		JSONBean<CustomerCredit> json = new JSONBean<CustomerCredit>();
		
		this.crmService.removeCustomerCreditCardById(cc.getId());
		
		json.getSuccessMap().put("alert-success", "Successfully Remove Specific Credit Card Details!");
		
		return json;
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

//	// BEGIN DDPay
//	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit_cash", method = RequestMethod.POST)
//	public JSONBean<String> doDefrayByDDPay(Model model,
//			@RequestParam("process_way") String process_way,
//			@RequestParam("pay_way") String pay_way,
//			@RequestParam("invoice_id") int invoice_id, HttpServletRequest req) {
//
//		JSONBean<String> json = new JSONBean<String>();
//
//		Integer customer_id = null;
//		Integer order_id = null;
//		Double paid_amount = null;
//		String process_sort = null;
//
//		// BEGIN INVOICE ASSIGNMENT
//		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
//		ci.getParams().put("id", ci.getId());
//
//		if (ci.getBalance() <= 0d) {
//			// If invoice is paid off then no reason for executing the below
//			// operations
//			ci.setStatus("paid");
//			this.crmService.editCustomerInvoice(ci);
//			return json;
//		}
//		customer_id = ci.getCustomer_id();
//		order_id = ci.getOrder_id();
//		paid_amount = TMUtils.bigSub(ci.getFinal_payable_amount(), ci.getAmount_paid());
//
//		// If not CyberPark then assign balance to paid
//		if(!"cyberpark-credit".equals(pay_way)){
//			ci.setAmount_paid(ci.getBalance());
//		}
//		ci.setFinal_payable_amount(ci.getAmount_paid());
//		// Assign balance as 0.0, make this invoice paid off
//		ci.setBalance(0d);
//		// Assign status to paid directly, make this invoice paid off
//		ci.setStatus("paid");
//		// END INVOICE ASSIGNMENT
//
//		// Get order_type
//		switch (this.crmService.queryCustomerOrderTypeById(order_id)) {
//		case "order-term":
//			process_sort = "plan-term";
//			break;
//		case "order-no-term":
//			process_sort = "plan-no-term";
//			break;
//		case "order-topup":
//			process_sort = "plan-topup";
//			break;
//		}
//
//		User userSession = (User) req.getSession().getAttribute("userSession");
//
//		// BEGIN TRANSACTION ASSIGNMENT
//		CustomerTransaction ct = new CustomerTransaction();
//		// Assign invoice's paid amount to transaction's amount
//		ct.setAmount(paid_amount);
//		ct.setAmount_settlement(paid_amount);
//		// Assign card_name as ddpay
//		ct.setCard_name(process_way);
//		// Assign transaction's sort as type's return from order by order_id
//		ct.setTransaction_sort(process_sort);
//		// Assign customer_id, order_id, invoice_id to transaction's related
//		// fields
//		ct.setCustomer_id(customer_id);
//		ct.setOrder_id(order_id);
//		ct.setInvoice_id(invoice_id);
//		// Assign transaction's time as current time
//		ct.setTransaction_date(new Date());
//		ct.setCurrency_input("NZD");
//		ct.setExecutor(userSession.getId());
//		// END TRANSACTION ASSIGNMENT
//
//		// BEGIN CALL SERVICE LAYER
//		this.crmService.editCustomerInvoice(ci);
//		this.crmService.createCustomerTransaction(ct);
//		// END CALL SERVICE LAYER
//
//		json.getSuccessMap().put("alert-success",
//				"Related invoice's balance had successfully been paid off!");
//		
//		ct = null;
//		ci = null;
//
//		return json;
//	}
//	// END DDPay

	// BEGIN Cash
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit_cash", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByCash(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("process_way") String process_way,
			@RequestParam("pay_way") String pay_way,
			@RequestParam("eliminate_amount") double eliminate_amount,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
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
		
		Double amount_paid = TMUtils.bigAdd(ci.getAmount_paid(), eliminate_amount);
		Double balance = TMUtils.bigSub(ci.getFinal_payable_amount(), amount_paid);
		
		Integer customer_id = ci.getCustomer_id();
		Integer order_id = ci.getOrder_id();

		if("CyberPark Credit".equals(process_way)){
			ci.setFinal_payable_amount(TMUtils.bigSub(ci.getFinal_payable_amount(), eliminate_amount));
		} else {
			ci.setAmount_paid(amount_paid);
		}

		// Assign balance as 0.0, make this invoice paid off

		ci.setBalance(balance);
		// If balance equals to 0d then paid else not_pay_off, make this invoice
		// paid (off)
		if ((ci.getBalance() <= 0d) || ("CyberPark Credit".equals(process_way) && balance<=0d)) {
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT

		// Get order_type
		String process_sort = null;
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		switch (coQuery.getOrder_type()) {
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
		ct.setAmount(eliminate_amount);
		ct.setAmount_settlement(eliminate_amount);
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
		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customer_id);
		Customer c = this.crmService.queryCustomer(cQuery);
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
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		switch (coQuery.getOrder_type()) {
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
		
		User user = (User) req.getSession().getAttribute("userSession");
		
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
			codCreate.setUser_id(user.getId());
			
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
			codCreate.setDetail_price(hardware.getHardware_price());
			codCreate.setDetail_desc(hardware.getHardware_desc());
			codCreate.setDetail_type("hardware-router");
			codCreate.setIs_post(0);
			codCreate.setUser_id(user.getId());
			
			this.crmService.createCustomerOrderDetail(codCreate);
			
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
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		switch (coQuery.getOrder_type()) {
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
			@RequestParam("order_id") Integer order_id,
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
		pl.setOrder_id_customer(order_id);
		pl.setProcess_way(old_order_status+" to "+order_status);
		
		CustomerOrder co = new CustomerOrder();
		co.setOrder_status(order_status);
		if(!"".equals(disconnected_date_str.trim())){
			co.setDisconnected_date(TMUtils.parseDateYYYYMMDD(disconnected_date_str));
		}
		co.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrder(co, pl);
		json.setModel(co);

		json.getSuccessMap().put("alert-success", "Order Status Changed!");

		return json;
	}

	// Update order type
	@RequestMapping(value = "/broadband-user/crm/customer/order/type/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderTypeEdit(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("order_type") String order_type,
			HttpServletRequest req) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.setOrder_type(order_type);
		coUpdate.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrder(coUpdate);
		
		json.setModel(coUpdate);

		json.getSuccessMap().put("alert-success", "Order Type Changed!");

		return json;
	}

	// Empty svcvlan, rfs date
	@RequestMapping(value = "/broadband-user/crm/customer/order/svcvlan-rfsdate/empty", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerSVCVLanRFSDateEmpty(Model model,
			@RequestParam("order_id") Integer order_id) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrderSVCVLanRFSDateEmpty(coUpdate);
		
		json.getSuccessMap().put("alert-success", "Empty SV/CVLan, RFS Date successfully!");
		
		return json;
	}

	// Empty service giving or next invoice create date
	@RequestMapping(value = "/broadband-user/crm/customer/order/service-giving-next-invoice-create/empty", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerServiceGivingNextInvoiceEmpty(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("date_type") String date_type) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		if("service-giving".equals(date_type)){
			coUpdate.setOrder_using_start(new Date());
		} else {
			coUpdate.setNext_invoice_create_date(new Date());
		}
		coUpdate.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrderServiceGivingNextInvoiceEmpty(coUpdate);
		
		json.setModel(coUpdate);

		json.getSuccessMap().put("alert-success", "Empty Order "+date_type+" successfully!");

		return json;
	}

	// Empty broadband asid
	@RequestMapping(value = "/broadband-user/crm/customer/order/broadband_asid/empty", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerBroadbandASIDEmpty(Model model,
			@RequestParam("order_id") Integer order_id) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrderBroadbandASIDEmpty(coUpdate);
		
		json.getSuccessMap().put("alert-success", "Empty Broadband ASID successfully!");
		
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

	// Update Order Customer Basic Contact
	@RequestMapping(value = "/broadband-user/crm/customer/order/basic_contact/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderCustomerTypeEdit(Model model,
			CustomerOrder customerOrder,
			@RequestParam("basic_type") String basic_type) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();

		CustomerOrder co = new CustomerOrder();
		if("customer-type".equals(basic_type)){
			co.setCustomer_type(customerOrder.getCustomer_type());
		} else if("customer-title".equals(basic_type)){
			co.setTitle(customerOrder.getTitle());
		} else if("customer-address".equals(basic_type)){
			if(customerOrder.getAddress()==null || "".equals(customerOrder.getAddress())){
				json.getErrorMap().put("address_"+customerOrder.getId(), "Couldn't be empty!");
				return json;
			}
			co.setAddress(customerOrder.getAddress());
		} else if("customer-mobile".equals(basic_type)){
			co.setMobile(customerOrder.getMobile());
		} else if("customer-phone".equals(basic_type)){
			co.setPhone(customerOrder.getPhone());
		} else if("customer-email".equals(basic_type)){
			co.setEmail(customerOrder.getEmail());
		} else if("first-name".equals(basic_type)){
			if(customerOrder.getFirst_name()==null || "".equals(customerOrder.getFirst_name())){
				json.getErrorMap().put("first_name_"+customerOrder.getId(), "Couldn't be empty!");
				return json;
			}
			co.setFirst_name(customerOrder.getFirst_name());
		} else if("last-name".equals(basic_type)){
			if(customerOrder.getLast_name()==null || "".equals(customerOrder.getLast_name())){
				json.getErrorMap().put("last_name_"+customerOrder.getId(), "Couldn't be empty!");
				return json;
			}
			co.setLast_name(customerOrder.getLast_name());
		} else if("org-name".equals(basic_type)){
			if(customerOrder.getOrg_name()==null || "".equals(customerOrder.getOrg_name())){
				json.getErrorMap().put("org_name_"+customerOrder.getId(), "Couldn't be empty!");
				return json;
			}
			co.setOrg_name(customerOrder.getOrg_name());
		} else if("org-type".equals(basic_type)){
			co.setOrg_type(customerOrder.getOrg_type());
		} else if("org-trading-name".equals(basic_type)){
			co.setOrg_trading_name(customerOrder.getOrg_trading_name());
		} else if("org-register-no".equals(basic_type)){
			co.setOrg_register_no(customerOrder.getOrg_register_no());
		} else if("org-incoporate-date".equals(basic_type)){
			String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			Pattern pat = Pattern.compile(rexp);
			Matcher mat = pat.matcher(customerOrder.getOrg_incoporate_date_str());
			boolean dateType = mat.matches();
			if (dateType && TMUtils.isDateFormat(customerOrder.getOrg_incoporate_date_str(), "-")) {
				co.setOrg_incoporate_date(TMUtils.parseDateYYYYMMDD(customerOrder.getOrg_incoporate_date_str()));
			} else {
				json.getErrorMap().put("org_incoporate_date_"+customerOrder.getId(), "Must be yyyy-mm-dd");
				return json;
			}
		} else if("org-trading-months".equals(basic_type)){
			co.setOrg_trading_months(customerOrder.getOrg_trading_months());
		} else if("holder-name".equals(basic_type)){
			co.setHolder_name(customerOrder.getHolder_name());
		} else if("holder-job-title".equals(basic_type)){
			co.setHolder_job_title(customerOrder.getHolder_job_title());
		} else if("holder-phone".equals(basic_type)){
			co.setHolder_phone(customerOrder.getHolder_phone());
		} else if("holder-email".equals(basic_type)){
			co.setHolder_email(customerOrder.getHolder_email());
		}
		co.getParams().put("id", customerOrder.getId());

		this.crmService.editCustomerOrder(co);
		json.getSuccessMap().put("alert-success", basic_type.replaceAll("-", " ")+" has successfully been updated!");

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
				json.getErrorMap().put(customerOrder.getId() + "_svlan_input", "Enter SVLan!");
			}
			if ("".equals(cvLan)) {
				json.getErrorMap().put(customerOrder.getId() + "_cvlan_input", "Enter CVLan!");
			}
			return json;
		}

		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", customerOrder.getId());
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		coQuery.setRfs_date(TMUtils.parseDateYYYYMMDD(customerOrder.getRfs_date_str()));
		
		CustomerOrderDetail codQuery = new CustomerOrderDetail();
		codQuery.getParams().put("order_id", customerOrder.getId());
		List<CustomerOrderDetail> cods = this.crmService.queryCustomerOrderDetails(codQuery);

		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customerOrder.getCustomer_id());
		cQuery = this.crmService.queryCustomer(cQuery);
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("service-installation", "email");
		ApplicationEmail applicationEmail = new ApplicationEmail();
		
		// call mail at value retriever
		MailRetriever.mailAtValueRetriever(notification, cQuery, coQuery, cods, companyDetail);
		applicationEmail.setAddressee(coQuery.getEmail()!=null && !"".equals(coQuery.getEmail()) ? coQuery.getEmail() : cQuery.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);

		// get sms register template from db
		notification = this.systemService.queryNotificationBySort("service-installation", "sms");
		MailRetriever.mailAtValueRetriever(notification, cQuery, coQuery, cods, companyDetail);
		// send sms to customer's mobile phone
		this.smserService.sendSMSByAsynchronousMode(coQuery.getMobile()!=null && !"".equals(coQuery.getMobile()) ? coQuery.getMobile() : cQuery.getCellphone(), notification.getContent());
		
		// Provision record
		ProvisionLog pl = new ProvisionLog();
		User user = (User) req.getSession().getAttribute("userSession");
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(customerOrder.getId());
		pl.setProcess_way(coQuery.getOrder_status()+" to rfs");

		CustomerOrder co = new CustomerOrder();
		co.setSvlan(svLan);
		co.setCvlan(cvLan);
		co.setRfs_date(TMUtils.parseDateYYYYMMDD(customerOrder.getRfs_date_str()));
		co.setOrder_status("rfs");
		co.getParams().put("id", customerOrder.getId());

		this.crmService.editCustomerOrder(co, pl);
		json.setModel(co);
		json.getSuccessMap().put("alert-success", "svlan, cvlan and rfs_date had successfully been updated.");
		
		pl = null;
		user = null;
		cQuery = null;
		cods = null;
		coQuery = null;
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
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", customerOrder.getId());
		CustomerOrder co = this.crmService.queryCustomerOrder(coQuery);
		co.setOrder_using_start(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
		co.getParams().put("id", customerOrder.getId());
		co.setOrder_status("using");
		
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customerOrder.getCustomer_id());
		Customer customer = this.crmService.queryCustomer(cQuery);
		
		if("save".equals(way)){

			if (!"order-topup".equals(customerOrder.getOrder_type())
			&& (!"order-term".equals(customerOrder.getOrder_type())
			|| ("order-term".equals(customerOrder.getOrder_type())
			&& (customerOrder.getIs_ddpay()==null
				|| (customerOrder.getIs_ddpay()!=null && !customerOrder.getIs_ddpay()))))) {
				
				Date next_invoice_create_date = null;
				Date next_invoice_create_date_flag = null;
				
				if("personal".equals(co.getCustomer_type())){
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
//					long seventh12hr = cal_7th_1hr.getTimeInMillis();
//					long currentTime = System.currentTimeMillis();
	//
//					// If less than seventh 1 o'clock of the month
//					if(currentTime<seventh12hr){
//						Calendar cal = cal_7th_1hr;
//						cal.set(Calendar.HOUR, 0);
//						next_invoice_create_date = cal.getTime();
//						cal.add(Calendar.DATE, 7);
//						next_invoice_create_date_flag = cal.getTime();
//					} else {
//						Calendar cal = cal_7th_1hr;
//						cal.set(Calendar.HOUR, 0);
//						cal.add(Calendar.MONTH, 1);
//						next_invoice_create_date = cal.getTime();
//						cal.add(Calendar.DATE, 7);
//						next_invoice_create_date_flag = cal.getTime();
//					}
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
			
		}

		if ("save".equals(way)) {
			
			co.setOrder_type(customerOrder.getOrder_type());
			proLog.setProcess_way(customerOrder.getOrder_status() + " to using");
			
			String attachPath = null;
			String emailSort = "service-giving";
			
			if("paid".equals(pay_status)){
				User userSession = (User) req.getSession().getAttribute("userSession");
				attachPath = this.crmService.serviceGivenPaid(customer, customerOrder, companyDetail, userSession);
				
				// If customer account credit insufficient
				if(attachPath==null){
					
					user = null;
					proLog = null;
					co = null;
					companyDetail = null;
					customer = null;
					
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
			MailRetriever.mailAtValueRetriever(notification, customer, co, companyDetail);
			this.smserService.sendSMSByAsynchronousMode(co.getMobile()!=null && !"".equals(co.getMobile()) ? co.getMobile() : customer.getCellphone(), notification.getContent()); /* send sms to customer's mobile phone */

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
			MailRetriever.mailAtValueRetriever(notification, customer, co, companyDetail);
			applicationEmail.setAddressee(co.getEmail()!=null && !"".equals(co.getEmail()) ? co.getEmail() : customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			notification = this.systemService.queryNotificationBySort("service-giving", "sms");
			MailRetriever.mailAtValueRetriever(notification, customer, co, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(co.getMobile(), notification.getContent());
			json.getSuccessMap().put("alert-success", "Service Given Date successfully settled!");
			
		}

		this.crmService.editCustomerOrder(co, proLog);

		json.setModel(customerOrder);
		
		user = null;
		proLog = null;
		co = null;
		companyDetail = null;
		customer = null;
		
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
	@RequestMapping(value = "/broadband-user/crm/customer/edit", method = RequestMethod.GET)
	public Map<String, Object> toCustomerEdit(Model model,
			@RequestParam("id") int id,
			HttpServletRequest req) {

		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		User user = new User();
		List<User> users = this.systemService.queryUser(user);
		user.getParams().put("user_role", "sales");
		user.getParams().put("user_role2", "agent");
		List<User> sales = this.systemService.queryUser(user);
		CustomerCredit ccQuery = new CustomerCredit();
		ccQuery.getParams().put("customer_id", customer.getId());
		List<CustomerCredit> ccs = this.crmService.queryCustomerCredits(ccQuery);
		
		User userSession = (User) req.getSession().getAttribute("userSession");

		Map<String, Object> map = new HashMap<String, Object>();
		if("system-developer".equals(userSession.getUser_role())
		|| "accountant".equals(userSession.getUser_role())){
			map.put("customerCredits", ccs);
		} else {
			map.put("customerCredits", new ArrayList<CustomerCredit>());
		}
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
			@RequestParam("phone_monthly_price") Double phone_monthly_price,
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
		cod.setDetail_price(phone_monthly_price);
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
					json.getErrorMap().put("alert-error", "VoIP Assign Date Format Incorrect! Must be yyyy-mm-dd");
					return json;
				}
			}
		}
		
		cod.getParams().put("id", order_detail_id);
		this.crmService.editCustomerOrderDetail(cod);
		
		json.getSuccessMap().put("alert-success", "Phone number had just been edited!");

		return json;
	}

	// Add free calling minutes
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
		&&  !"".equals(calling_country.trim())) {
			
			String detail_name = "";
			String detail_type = "";
			String detail_desc = "";
			
			if("super-free-calling".equals(calling_country)){
				
				detail_name = calling_minutes+" minutes to selected area";
				detail_type = "super-free-calling";
				detail_desc = "super-local,voip=super-national,voip=super-mobile,voip=super-40-countries,voip=super-international,voip=super-Bangladesh-both,voip=super-Malaysia-both,voip=super-Cambodia-both,voip=super-Singapore-both,voip=super-Canada-both,voip=super-Korea-both,voip=super-China-both,voip=super-USA-both,voip=super-Hong-Kong-both,voip=super-Vietnam-both,voip=super-India-both,voip=super-Argentina-fixedline,54,voip=super-Germany-fixedline,49,voip=super-Laos-fixedline,856,voip=super-South-Africa-fixedline,27,voip=super-Australia-fixedline,61,voip=super-Greece-fixedline,30,voip=super-Netherlands-fixedline,31,voip=super-Spain-fixedline,34,voip=super-Belgium-fixedline,32,voip=super-Indonesia-fixedline,62,voip=super-Norway-fixedline,47,voip=super-Sweden-fixedline,46,voip=super-Brazil-fixedline,55,voip=super-Ireland-fixedline,353,voip=super-Pakistan-fixedline,92,voip=super-Switzerland-fixedline,41,voip=super-Cyprus-fixedline,357,voip=super-Israel-fixedline,972,voip=super-Poland-fixedline,48,voip=super-Taiwan-fixedline,886,voip=super-Czech-fixedline,420,voip=super-Italy-fixedline,39,voip=super-Portugal-fixedline,351,voip=super-Thailand-fixedline,66,voip=super-Denmark-fixedline,45,voip=super-Japan-fixedline,81,voip=super-Russia-fixedline,7,voip=super-United Kingdom-fixedline,44,voip=super-France-fixedline,33,voip";
				
			} else {
				
				detail_name = calling_country.split("-")[0];
				detail_name = detail_name.split(",")[0];
				detail_name = calling_minutes+" minutes to " + detail_name;
				detail_type = "present-calling-minutes";
				detail_desc = calling_country;
				
			}
			
			CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
			customerOrderDetail.setOrder_id(order_id);
			customerOrderDetail.setDetail_name(detail_name);
			customerOrderDetail.setDetail_type(detail_type);
			customerOrderDetail.setDetail_desc(detail_desc);
			customerOrderDetail.setDetail_price(charge_amount);
			customerOrderDetail.setDetail_calling_minute(Integer.parseInt(calling_minutes));
			customerOrderDetail.setDetail_unit(1);
			User user = (User) req.getSession().getAttribute("userSession");
			customerOrderDetail.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(customerOrderDetail);
			
			// Create Customer Order Detail Discount is successful.
			json.getSuccessMap().put("alert-success", "Free Calling Minutes had been attached to related order! Order Id: "+order_id);
			
		} else {
			
			json.getErrorMap().put("alert-error", "Calling Minutes Format Incorrect! Must be digital numbers");
		}

		return json;
	}

	// Add Static IP
	@RequestMapping(value = "/broadband-user/crm/customer/order/static-ip/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailStaticIPCreate(
			Model model, @RequestParam("order_id") int order_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("static_ip_name") String static_ip_name,
			@RequestParam("static_ip_charge_fee") Double static_ip_charge_fee,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
			
			CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
			customerOrderDetail.setOrder_id(order_id);
			customerOrderDetail.setDetail_name(static_ip_name);
			customerOrderDetail.setDetail_type("static-ip");
			customerOrderDetail.setDetail_price(static_ip_charge_fee);
			customerOrderDetail.setDetail_unit(1);
			User user = (User) req.getSession().getAttribute("userSession");
			customerOrderDetail.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(customerOrderDetail);
			
			// Create Customer Order Detail Discount is successful.
			json.getSuccessMap().put("alert-success", "Static IP had been attached to related order! Order Id: "+order_id);

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
			json.getErrorMap().put("alert-error", "Expiry Date Format Incorrect! Must be yyyy-mm-dd");
		}

		return json;
	}

	// Remove discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailDiscountRemove(Model model,
			@RequestParam("order_detail_id") int order_detail_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("detail_type") String detail_type,
			@RequestParam("delete_reason") String delete_reason,
			RedirectAttributes attr,
			HttpServletRequest request) {

		JSONBean<String> json = new JSONBean<String>();
		
		// 1. Retrieve CustomerOrderDetail
		CustomerOrderDetail codQuery = new CustomerOrderDetail();
		codQuery.getParams().put("id", order_detail_id);
		codQuery = this.crmService.queryCustomerOrderDetail(codQuery);
		
		// 2. Copy Detail And Paste Into Recoverable List
		CustomerOrderDetailRecoverableList codrl = new CustomerOrderDetailRecoverableList();
		codrl.setOrder_id(codQuery.getOrder_id());
		codrl.setDetail_id(codQuery.getId());
		codrl.setDetail_name(codQuery.getDetail_name());
		codrl.setDetail_desc(codQuery.getDetail_desc());
		codrl.setDetail_price(codQuery.getDetail_price());
		codrl.setDetail_data_flow(codQuery.getDetail_data_flow());
		codrl.setDetail_plan_status(codQuery.getDetail_plan_status());
		codrl.setDetail_plan_type(codQuery.getDetail_plan_type());
		codrl.setDetail_plan_sort(codQuery.getDetail_plan_sort());
		codrl.setDetail_plan_group(codQuery.getDetail_plan_group());
		codrl.setDetail_plan_class(codQuery.getDetail_plan_class());
		codrl.setDetail_plan_new_connection_fee(codQuery.getDetail_plan_new_connection_fee());
		codrl.setDetail_term_period(codQuery.getDetail_term_period());
		codrl.setDetail_topup_data_flow(codQuery.getDetail_topup_data_flow());
		codrl.setDetail_topup_fee(codQuery.getDetail_topup_fee());
		codrl.setDetail_plan_memo(codQuery.getDetail_plan_memo());
		codrl.setDetail_unit(codQuery.getDetail_unit());
		codrl.setDetail_calling_minute(codQuery.getDetail_calling_minute());
		codrl.setDetail_type(codQuery.getDetail_type());
		codrl.setDetail_is_next_pay(codQuery.getDetail_is_next_pay());
		codrl.setDetail_expired(codQuery.getDetail_expired());
		codrl.setIs_post(codQuery.getIs_post());
		codrl.setHardware_comment(codQuery.getHardware_comment());
		codrl.setTrack_code(codQuery.getTrack_code());
		codrl.setPstn_number(codQuery.getPstn_number());
		codrl.setUser_id(codQuery.getUser_id());
		codrl.setVoip_password(codQuery.getVoip_password());
		codrl.setVoip_assign_date(codQuery.getVoip_assign_date());
		this.crmService.createCustomerOrderDetailRecoverableList(codrl);

		User user = (User) request.getSession().getAttribute("userSession");
		
		// 3. Insert necessary informations into CustomerOrderDetailDeleteRecord
		CustomerOrderDetailDeleteRecord coddr = new CustomerOrderDetailDeleteRecord();
		coddr.setDelete_reason(delete_reason);
		coddr.setDetail_id(codQuery.getId());
		coddr.setDetail_name(codQuery.getDetail_name());
		coddr.setDetail_type(codQuery.getDetail_type());
		coddr.setExecutor_id(user.getId());
		coddr.setExecuted_date(new Date());
		coddr.setOrder_id(codQuery.getOrder_id());
		coddr.setIs_recoverable(true);
		this.crmService.createCustomerOrderDetailDeleteRecord(coddr);
		
		

		this.crmService.removeCustomerOrderDetailById(order_detail_id);
		// Remove Customer Order Detail Discount is successful.
		String detailType = "discount".equals(detail_type) ? "Credit" : "debit".equals(detail_type) ? "Debit"
						: "early-termination-debit".equals(detail_type) ? "Early Termination Charge"
						: "termination-credit".equals(detail_type) ? "Termination Refund" : detail_type;
		json.getSuccessMap().put("alert-success", "Selected " + detailType + " had been detached from related order!");

		return json;
	}

	// Remove discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/recover", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailRecover(Model model,
			@RequestParam("detail_id") int detail_id) {

		JSONBean<String> json = new JSONBean<String>();
		
		// 1. Retrieve RecoverableOrderDetail
		CustomerOrderDetailRecoverableList codrlQuery = new CustomerOrderDetailRecoverableList();
		codrlQuery.getParams().put("detail_id", detail_id);
		codrlQuery = this.crmService.queryCustomerOrderDetailRecoverableList(codrlQuery);
		
		// 2. Copy Recoverable Detail And Paste Into OrderDetail
		CustomerOrderDetail codCreate = new CustomerOrderDetail();
		codCreate.setOrder_id(codrlQuery.getOrder_id());
		codCreate.setDetail_name(codrlQuery.getDetail_name());
		codCreate.setDetail_desc(codrlQuery.getDetail_desc());
		codCreate.setDetail_price(codrlQuery.getDetail_price());
		codCreate.setDetail_data_flow(codrlQuery.getDetail_data_flow());
		codCreate.setDetail_plan_status(codrlQuery.getDetail_plan_status());
		codCreate.setDetail_plan_type(codrlQuery.getDetail_plan_type());
		codCreate.setDetail_plan_sort(codrlQuery.getDetail_plan_sort());
		codCreate.setDetail_plan_group(codrlQuery.getDetail_plan_group());
		codCreate.setDetail_plan_class(codrlQuery.getDetail_plan_class());
		codCreate.setDetail_plan_new_connection_fee(codrlQuery.getDetail_plan_new_connection_fee());
		codCreate.setDetail_term_period(codrlQuery.getDetail_term_period());
		codCreate.setDetail_topup_data_flow(codrlQuery.getDetail_topup_data_flow());
		codCreate.setDetail_topup_fee(codrlQuery.getDetail_topup_fee());
		codCreate.setDetail_plan_memo(codrlQuery.getDetail_plan_memo());
		codCreate.setDetail_unit(codrlQuery.getDetail_unit());
		codCreate.setDetail_calling_minute(codrlQuery.getDetail_calling_minute());
		codCreate.setDetail_type(codrlQuery.getDetail_type());
		codCreate.setDetail_is_next_pay(codrlQuery.getDetail_is_next_pay());
		codCreate.setDetail_expired(codrlQuery.getDetail_expired());
		codCreate.setIs_post(codrlQuery.getIs_post());
		codCreate.setHardware_comment(codrlQuery.getHardware_comment());
		codCreate.setTrack_code(codrlQuery.getTrack_code());
		codCreate.setPstn_number(codrlQuery.getPstn_number());
		codCreate.setUser_id(codrlQuery.getUser_id());
		codCreate.setVoip_password(codrlQuery.getVoip_password());
		codCreate.setVoip_assign_date(codrlQuery.getVoip_assign_date());
		this.crmService.createCustomerOrderDetail(codCreate);
		
		// 3. Clean Deleted Record & Recoverable Record
		this.crmService.removeCustomerOrderDetailDeleteRecordByDetailId(detail_id);
		this.crmService.removeCustomerOrderDetailRecoverableListByDetailId(detail_id);
		
		json.getSuccessMap().put("alert-success", "Selected Detail had been recovered to related order!");

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

	// Change invoice status
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/status/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerInvoiceStatusEdit(Model model,
			@RequestParam("ci_id") int ci_id,
			@RequestParam("ci_status") String ci_status,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		
		CustomerInvoice ciUpdate = new CustomerInvoice();
		ciUpdate.getParams().put("id", ci_id);
		ciUpdate.setStatus(ci_status);
		
		this.crmService.editCustomerInvoice(ciUpdate);
		// Change Customer invoice status is successful.
		
		json.getSuccessMap().put("alert-success", "Change invoice status to "+ci_status+" successfully!");

		return json;
	}

	// Show invoice(s) by customer_id
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/show_by_customer_id")
	public JSONBean<CustomerInvoice> showCustomerInvoiceByCustomerId(Model model,
			@RequestParam("customer_id") int customer_id,
			RedirectAttributes attr) {

		JSONBean<CustomerInvoice> json = new JSONBean<CustomerInvoice>();
		
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("original_customer_id", customer_id);
		List<CustomerInvoice> cis = this.crmService.queryCustomerInvoices(ciQuery);
		json.setModels(cis);

		return json;
	}

	// Change detail expired date
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/expired_date/edit")
	public JSONBean<CustomerOrderDetail> editCustomerOrderDetailExpiredDate(Model model,
			@RequestParam("detail_id") int detail_id,
			@RequestParam("expired_date") String expired_date,
			RedirectAttributes attr) {

		JSONBean<CustomerOrderDetail> json = new JSONBean<CustomerOrderDetail>();
		
		CustomerOrderDetail codUpdate = new CustomerOrderDetail();
		codUpdate.setDetail_expired(TMUtils.parseDateYYYYMMDD(expired_date));
		codUpdate.getParams().put("id", detail_id);
		this.crmService.editCustomerOrderDetail(codUpdate);
		
		json.getSuccessMap().put("alert-success", "Change detail expired date successfully!");

		return json;
	}

	// Edit Invoice Paid Amount
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/paid_amount/edit")
	public JSONBean<CustomerInvoice> editInvoicePaidAmount(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("amount_paid") Double amount_paid,
			RedirectAttributes attr) {

		JSONBean<CustomerInvoice> json = new JSONBean<CustomerInvoice>();
		CustomerInvoice ciUpdate = new CustomerInvoice();
		ciUpdate.setAmount_paid(amount_paid);
		ciUpdate.getParams().put("id", invoice_id);
		this.crmService.editCustomerInvoice(ciUpdate);
		
		json.getSuccessMap().put("alert-success", "Edit invoice paid amount successfully!");

		return json;
	}

	// Edit Transaction Amount
	@RequestMapping(value = "/broadband-user/crm/customer/transaction/amount/edit")
	public JSONBean<CustomerTransaction> editTransactionAmount(Model model,
			@RequestParam("transaction_id") int transaction_id,
			@RequestParam("transaction_amount") Double transaction_amount,
			RedirectAttributes attr) {

		JSONBean<CustomerTransaction> json = new JSONBean<CustomerTransaction>();
		CustomerTransaction ctUpdate = new CustomerTransaction();
		ctUpdate.setAmount(transaction_amount);
		ctUpdate.setAmount_settlement(transaction_amount);
		ctUpdate.getParams().put("id", transaction_id);
		this.crmService.editCustomerTransaction(ctUpdate);
		
		json.getSuccessMap().put("alert-success", "Edit transaction amount successfully!");

		return json;
	}

	// Edit Next Invoice Create Date
	@RequestMapping(value = "/broadband-user/crm/customer/order/next_invoice_create_date/edit")
	public JSONBean<CustomerOrder> editNextInvoiceCreateDate(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("next_invoice_create_date_str") String next_invoice_create_date_str,
			RedirectAttributes attr) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		Date next_invoice_create_date = TMUtils.parseDateYYYYMMDD(next_invoice_create_date_str);
		Calendar calNextInvoiceCreateDateFlag = Calendar.getInstance();
		calNextInvoiceCreateDateFlag.setTime(next_invoice_create_date);
		calNextInvoiceCreateDateFlag.add(Calendar.DATE, 7);
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.setNext_invoice_create_date(next_invoice_create_date);
		coUpdate.setNext_invoice_create_date_flag(calNextInvoiceCreateDateFlag.getTime());
		coUpdate.getParams().put("id", order_id);
		this.crmService.editCustomerOrder(coUpdate);
		
		json.getSuccessMap().put("alert-success", "Edit next invoice create date successfully!");

		return json;
	}

	// Invoice Operations
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/operation-type/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerInvoiceOperationTypeEdit(Model model,
			@RequestParam("operation_type") String operation_type,
			@RequestParam("checked_invoice_ids_str") String checked_invoice_ids_str,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		
		String msg = "";
		String msg_type = "alert-error";
		String [] invoice_ids = checked_invoice_ids_str.split(",");
		
		if(checked_invoice_ids_str!=null && !"".equals(checked_invoice_ids_str.trim())){
			
			switch (operation_type) {
			case "unbind-invoice":
				for (String invoice_id : invoice_ids) {
					
					CustomerInvoice ciQuery = new CustomerInvoice();
					ciQuery = this.crmService.queryCustomerInvoiceById(Integer.parseInt(invoice_id));
					
					CustomerInvoice ciUpdate = new CustomerInvoice();
					ciUpdate.setOriginal_customer_id(ciQuery.getCustomer_id());
					ciUpdate.setOriginal_order_id(ciQuery.getOrder_id());
					ciUpdate.getParams().put("customer_id_null", "true");
					ciUpdate.getParams().put("order_id_null", "true");
					ciUpdate.getParams().put("id", invoice_id);
					this.crmService.editCustomerInvoice(ciUpdate);
					
				}
				msg_type = "alert-success";
				msg = "Successfully unbind selected invoice(s)!";
				break;
			case "bind-invoice":
				for (String invoice_id : invoice_ids) {
					
					CustomerInvoice ciQuery = new CustomerInvoice();
					ciQuery = this.crmService.queryCustomerInvoiceById(Integer.parseInt(invoice_id));
					
					CustomerInvoice ciUpdate = new CustomerInvoice();
					ciUpdate.setCustomer_id(ciQuery.getOriginal_customer_id());
					ciUpdate.setOrder_id(ciQuery.getOriginal_order_id());
					ciUpdate.getParams().put("original_customer_id_null", "true");
					ciUpdate.getParams().put("original_order_id_null", "true");
					ciUpdate.getParams().put("id", invoice_id);
					this.crmService.editCustomerInvoice(ciUpdate);
					
				}
				msg_type = "alert-success";
				msg = "Successfully bind selected invoice(s)!";
				break;
			default:
				msg_type = "alert-error";
				msg = "Please choose an operation type!";
				break;
			}
			
		} else {
			
			msg_type = "alert-error";
			msg = "Please select at least one invoice to continue!";
			
		}
		
		if("alert-success".equals(msg_type)){
			
			json.getSuccessMap().put(msg_type, msg);
			
		} else {
			
			json.getErrorMap().put(msg_type, msg);
			
		}

		return json;
	}

	// Add phone number
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/phone_number/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailPhoneNumberCreate(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("detail_name") String detail_name,
			@RequestParam("phone_number") String phone_number,
			@RequestParam("phone_monthly_price") Double phone_monthly_price,
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
		cod.setDetail_price(phone_monthly_price);
		cod.setDetail_type(phone_type);
		User user = (User) req.getSession().getAttribute("userSession");
		cod.setUser_id(user.getId());
		
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
					json.getErrorMap().put("alert-error", "VoIP Assign Date Format Incorrect! Must be yyyy-mm-dd");
					return json;
				}
			}
		}

		this.crmService.createCustomerOrderDetail(cod);
		json.getSuccessMap().put("alert-success", "Add new "+phone_type+" successfully!");

		return json;
	}

	// GetDeleteOrderDetailsByOrderId
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/delete_record")
	public JSONBean<Map<String, Object>> getDeletedOrderDetailsByOrderId(Model model,
			@RequestParam("order_id") Integer order_id) {

		JSONBean<Map<String, Object>> json = new JSONBean<Map<String, Object>>();
		
		CustomerOrderDetailDeleteRecord coddrQuery = new CustomerOrderDetailDeleteRecord();
		coddrQuery.getParams().put("orderby", "order by executed_date DESC");
		coddrQuery.getParams().put("order_id", order_id);
		List<CustomerOrderDetailDeleteRecord> coddrs = this.crmService.queryCustomerOrderDetailDeleteRecords(coddrQuery);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		List<User> users = this.systemService.queryUser();
		
		jsonMap.put("users", users);
		jsonMap.put("coddrs", coddrs);
		
		json.setModel(jsonMap);
		
		
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

		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);

		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
		String orderPDFPath = null;
		try {
			orderPDFPath = new ApplicationPDFCreator(coQuery).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		coQuery.setOrder_pdf_path(orderPDFPath);
		this.crmService.editCustomerOrder(coQuery);
		// END SET NECESSARY INFO AND GENERATE ORDER PDF
		// Regenerate order application PDF is successful.
		json.getSuccessMap().put("alert-success",
				"Generate New Order Application Successfully!");

		return json;
	}

	// termed manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/termed/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateTermedOrderInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr,
			HttpServletRequest request) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);
		
		try {
//			Map<String, Object> resultMap = 
					this.crmService.createTermPlanInvoiceByOrder(coQuery
					, isRegenerateInvoice
					, isRegenerateInvoice ? false : true);
			
//			Customer c = (Customer) resultMap.get("customer");
//			CustomerInvoice ci = (CustomerInvoice) resultMap.get("customerInvoice");
			
//			if(ci.getFinal_payable_amount()>0){
//				System.out.println("============================== TERMED INVOICE ON THE WAY 2 XERO ==============================");
//				Post2Xero.postSingleInvoice(request, c, coQuery, ci, "Broadband Monthly Payment");
//				System.out.println("============================== TERMED INVOICE SENT 2 XERO ==============================");
//			}
			
			json.getSuccessMap().put("alert-success", "Manually Generate Termed Invoice is successful");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return json;
	}

	// no term manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/no-term/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateNoTermOrderInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr,
			HttpServletRequest request) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);

		try {
			Notification notificationEmail = this.systemService.queryNotificationBySort("invoice", "email");
			Notification notificationSMS = this.systemService.queryNotificationBySort("invoice", "sms");
//			Map<String, Object> resultMap = 
					this.crmService.createInvoicePDFBoth(coQuery, notificationEmail,
					notificationSMS,
					isRegenerateInvoice ? false : true,
					isRegenerateInvoice);
			
//			Customer c = (Customer) resultMap.get("customer");
//			CustomerInvoice ci = (CustomerInvoice) resultMap.get("customerInvoice");

//			if(ci.getFinal_payable_amount()>0){
//				System.out.println("============================== NON-TERMED INVOICE ON THE WAY 2 XERO ==============================");
//				Post2Xero.postSingleInvoice(request, c, coQuery, ci, "Broadband Monthly Payment");
//				System.out.println("============================== NON-TERMED INVOICE SENT 2 XERO ==============================");
//			}
			
			
			json.getSuccessMap().put("alert-success", "Manually Generate No Term Invoice is successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	// termed manually-generate
	@RequestMapping(value = "/broadband-user/crm/customer/order/invoice/topup/manually-generate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateTopupOrderInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr,
			HttpServletRequest request) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		boolean isRegenerateInvoice = "regenerate".equals(generateType);

		try {
			Notification notificationEmail = this.systemService.queryNotificationBySort("invoice", "email");
			Notification notificationSMS = this.systemService.queryNotificationBySort("invoice", "sms");
//			Map<String, Object> resultMap = 
					this.crmService.createTopupPlanInvoiceByOrder(coQuery
					, new Notification(notificationEmail.getTitle(), notificationEmail.getContent())
					, new Notification(notificationSMS.getTitle(), notificationSMS.getContent())
					, isRegenerateInvoice
					, isRegenerateInvoice ? false : true);
			
//			Customer c = (Customer) resultMap.get("customer");
//			CustomerInvoice ci = (CustomerInvoice) resultMap.get("customerInvoice");

//			if(ci.getFinal_payable_amount()>0){
//				System.out.println("============================== TOPUP INVOICE ON THE WAY 2 XERO ==============================");
//				Post2Xero.postSingleInvoice(request, c, coQuery, ci, "Broadband Monthly Payment");
//				System.out.println("============================== TOPUP INVOICE SENT 2 XERO ==============================");
//			}
			
			json.getSuccessMap().put("alert-success", "Manually Generate Topup Invoice is successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	// EarlyTerminationCharge Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/early-termination-charge/invoice/generate", method = RequestMethod.POST)
	public JSONBean<String> doEarlyTerminationChargeInvoice(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("terminatedDate") String terminatedDate,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		try {
			if (TMUtils.isDateFormat(terminatedDate, "-")) {
				this.crmService.createEarlyTerminationInvoice(order_id, TMUtils.parseDateYYYYMMDD(terminatedDate), user.getId());
				json.getSuccessMap().put("alert-success", "Early termination charge invoice had just been generated!");
			} else {
				json.getErrorMap().put("alert-error", "Terminated Date Format Incorrect!");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return json;
	}

	// TerminationRefund Controller
	@RequestMapping(value = "/broadband-user/crm/customer/order/termination-credit/invoice/generate", method = RequestMethod.POST)
	public JSONBean<String> doTerminationRefundInvoice(Model model,
			TerminationRefund tr,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		User user = (User) req.getSession().getAttribute("userSession");

		try {

			if (TMUtils.isDateFormat(tr.getTermination_date_str(), "-")) {
				
				if("".equals(tr.getRefund_bank_account_number().trim())){
					json.getErrorMap().put("alert-error", "Account Number Couldn't Be Empty!");
					return json;
				} else if("".equals(tr.getRefund_bank_account_name().trim())){
					json.getErrorMap().put("alert-error", "Account Name Couldn't Be Empty!");
					return json;
				}
				
				this.crmService.createTerminationRefundInvoice(tr.getOrder_id(),
						TMUtils.parseDateYYYYMMDD(tr.getTermination_date_str()), user,
						tr.getRefund_bank_account_number(),
						tr.getRefund_bank_account_name(),
						tr.getProduct_monthly_price(), tr.getProduct_name());
				json.getSuccessMap().put("alert-success", "Termination refund invoice had just been generated!");
			} else {
				json.getErrorMap().put("alert-error", "Terminated Date Format Incorrect!");
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

			CustomerOrder coQuery = new CustomerOrder();
			coQuery.getParams().put("id", order_id);
			coQuery = this.crmService.queryCustomerOrder(coQuery);
			Map<String, Object> map = TMUtils.earlyTerminationDatesCalculation(
					coQuery.getOrder_using_start(),
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
					+ coQuery.getOrder_using_start_str() + " Terminated Date: "
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

	// Get Deleted Detail Record Counts
	@RequestMapping(value = "/broadband-user/crm/customer/order/deleted_detail_record_count", method = RequestMethod.POST)
	public Map<String, Object> getDeletedDetailRecordCounts(Model model,
			@RequestParam("order_id") Integer order_id) {
		
		Page<CustomerOrderDetailDeleteRecord> page = new Page<CustomerOrderDetailDeleteRecord>();
		page.getParams().put("order_id", order_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_id", order_id);
		map.put("sum", this.crmService.queryCustomerOrderDetailDeleteRecordsSumByPage(page));
		
		return map;
		
	}

	// Edit detail plan
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
	@RequestMapping(value = "/broadband-user/crm/customer/order/detail/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailEdit(Model model,
			CustomerOrderDetail cod,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		cod.getParams().put("id", cod.getId());
		this.crmService.editCustomerOrderDetail(cod);
		
		json.getSuccessMap().put("alert-success", "Detail edited successfully!");
		return json;
	}

	// Switch Is Send Invoice 2 Xero
	@RequestMapping(value = "/broadband-user/crm/customer/order/is_send_invoice_2_xero/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderIsSendInvoice2XeroEdit(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("is_send_xero_invoice") Boolean is_send_xero_invoice) {

		JSONBean<String> json = new JSONBean<String>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.getParams().put("id", order_id);
		coUpdate.setIs_send_xero_invoice(is_send_xero_invoice);
		this.crmService.editCustomerOrder(coUpdate);
		
		json.getSuccessMap().put("alert-success", "Switch Send Invoice 2 Xero to "+(is_send_xero_invoice ? "ACTIVE" : "DISABLED")+" successfully!");
		return json;
	}

	// Switch Xero Invoice Status
	@RequestMapping(value = "/broadband-user/crm/customer/order/xero_invoice_status/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderXeroInvoiceStatusEdit(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("xero_invoice_status") Boolean xero_invoice_status) {

		JSONBean<String> json = new JSONBean<String>();
		
		CustomerOrder coUpdate = new CustomerOrder();
		coUpdate.getParams().put("id", order_id);
		coUpdate.setXero_invoice_status(xero_invoice_status ? "AUTHORISED" : "DRAFT");
		this.crmService.editCustomerOrder(coUpdate);
		
		json.getSuccessMap().put("alert-success", "Switch Invoice Status Xero 2 Xero to "+(xero_invoice_status ? "AUTHORISED" : "DRAFT")+" successfully!");
		return json;
	}

	// Pay off order
	@RequestMapping(value = "/broadband-user/crm/customer/order/pay-off/receipt", method = RequestMethod.POST)
	public JSONBean<String> doPayOffOrder(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("customerId") Integer customer_id,
			@RequestParam("paid_amount") Double paid_amount,
			@RequestParam("order_pay_way") String order_pay_way,
			@RequestParam("order_total_price") Double order_total_price,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customer_id);
		Customer c = this.crmService.queryCustomer(cQuery);
		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		CustomerOrder co = this.crmService.queryCustomerOrder(coQuery);
		
		Double cyberParkCredit = 0d;
		
		if ("account-credit".equals(order_pay_way)){
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
			customer.getParams().put("id", customer_id);
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
			ct.setCustomer_id(customer_id);
			ct.setOrder_id(order_id);
			ct.setTransaction_type("Ordering Form Defrayment");
			ct.setTransaction_sort(co.getOrder_type());
			ct.setExecutor(user.getId());
			this.crmService.createCustomerTransaction(ct);
		}
		
		if(cyberParkCredit > 0d){
			CustomerOrderDetail cod = new CustomerOrderDetail();
			cod.setOrder_id(order_id);
			cod.setDetail_name("CyberPark Credit");
			cod.setDetail_price(cyberParkCredit);
			cod.setDetail_type("discount");
			cod.setDetail_unit(1);
			cod.setUser_id(user.getId());
			this.crmService.createCustomerOrderDetail(cod);
		}
		
		CustomerOrder coUpdate = new CustomerOrder();
		String receiptPath = this.crmService.createReceiptPDFByDetails(co);
		coUpdate.setReceipt_pdf_path(receiptPath);
		if("pending".equals(co.getOrder_status())
		  || "pending-warning".equals(co.getOrder_status())
		  || "void".equals(co.getOrder_status())){
			coUpdate.setOrder_status("paid");
		} else if("ordering-pending".equals(co.getOrder_status())) {
			coUpdate.setOrder_status("ordering-paid");
		}
		
		ProvisionLog pl = new ProvisionLog();
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(order_id);
		pl.setProcess_way(co.getOrder_status()+" to "+coUpdate.getOrder_status()+" receipt");
		coUpdate.getParams().put("id", order_id);
		this.crmService.editCustomerOrder(coUpdate, pl);
		
		json.getSuccessMap().put("alert-success", "Order paid off succeccfully!");
		return json;
	}

	// Generate order receipt
	@RequestMapping(value = "/broadband-user/crm/customer/order/generate/receipt", method = RequestMethod.POST)
	public JSONBean<String> doGenerateOrderReceipt(Model model,
			@RequestParam("order_id") Integer order_id,
			RedirectAttributes attr, HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();
		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		CustomerOrder co = this.crmService.queryCustomerOrder(coQuery);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		CustomerOrder coUpdate = new CustomerOrder();
		String receiptPath = this.crmService.createReceiptPDFByDetails(co);
		coUpdate.setReceipt_pdf_path(receiptPath);
		if("pending".equals(co.getOrder_status())
		  || "pending-warning".equals(co.getOrder_status())
		  || "void".equals(co.getOrder_status())){
			coUpdate.setOrder_status("paid");
		} else if("ordering-pending".equals(co.getOrder_status())) {
			coUpdate.setOrder_status("ordering-paid");
		}
		
		ProvisionLog pl = new ProvisionLog();
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(order_id);
		pl.setProcess_way(co.getOrder_status()+" to "+coUpdate.getOrder_status()+" receipt");
		co.getParams().put("id", order_id);
		this.crmService.editCustomerOrder(co, pl);
		
		json.getSuccessMap().put("alert-success", "Order status to paid and generate a receipt succeccfully!");
		return json;
	}
	
	@RequestMapping("/broadband-user/crm/customer/order/view/{pageNo}")
	public Page<CustomerOrder> doCustomerView(@PathVariable("pageNo") int pageNo, CustomerOrder coQuery, SessionStatus status) {
		
		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("where", "query_customer_by_field");
		page.getParams().put("orderby", "order by order_create_date desc");
		if (coQuery != null) {
			page.getParams().put("order_id", coQuery.getId());
			page.getParams().put("customer_id", coQuery.getCustomer_id());
			page.getParams().put("first_name", coQuery.getFirst_name());
			page.getParams().put("last_name", coQuery.getLast_name());
			page.getParams().put("mobile", coQuery.getMobile());
			page.getParams().put("email", coQuery.getEmail());
			page.getParams().put("address", coQuery.getAddress());
			page.getParams().put("pstn", coQuery.getPstn());
			page.getParams().put("voip", coQuery.getVoip());
			page.getParams().put("invoice_id", coQuery.getInvoice_id());
			page.getParams().put("customer_type", coQuery.getCustomer_type());
			page.getParams().put("broadband_asid", coQuery.getBroadband_asid());
		}
		System.out.println("coQuery.getMobile(): "+coQuery.getMobile());
		this.crmService.queryCustomerOrdersByPage(page);
		status.setComplete();
		return page;
	}
	
	// Ticket
	// Check Customer Existence
	@RequestMapping(value = "broadband-user/crm/ticket/check-customer-existence", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCheckCustomerExistence(Model model,
			@RequestParam("keyword") String keyword,
			HttpServletRequest req) {

		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		if("".equals(keyword.trim())){
			json.getErrorMap().put("customerNull", "Please input something and check again!");
			return json;
		}
		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("where", "query_exist_customer_by_keyword");
		coQuery.getParams().put("keyword", keyword);
		List<CustomerOrder> cos = this.crmService.queryCustomerOrders(coQuery);
		
		if(cos==null || cos.size()<=0){
			json.getErrorMap().put("customerNull", "Couldn't get order's essential details by provided keyword!");
		} else {
			json.getSuccessMap().put("customerNotNull", "Got this order's essential details!");
			
			CustomerOrder co = cos.get(0);
			
			// If Business Customer
			if(!"personal".equals(co.getCustomer_type())){
				co.setFirst_name(co.getOrg_name());
				co.setLast_name("");
			}
			
			json.setModel(co);
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
			@RequestParam("order_id") int order_id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		
		CustomerOrderDetail codQuery = new CustomerOrderDetail();
		codQuery.getParams().put("order_id", order_id);
		List<CustomerOrderDetail> cods = this.crmService.queryCustomerOrderDetails(codQuery);
		coQuery.setCustomerOrderDetails(cods);
		
		Customer c = this.crmService.queryCustomerByIdWithCustomerOrder(coQuery.getCustomer_id());
		
		c.setCustomerOrder(coQuery);
		
		this.crmService.createOrderingFormPDFByDetails(c);
		json.getSuccessMap().put("alert-success", "Successfully Regenerate Ordering Form");

		return json;
	}

	// Regenerate Receipt
	@RequestMapping(value = "/broadband-user/crm/customer/order/receipt/pdf/regenerate", method = RequestMethod.POST)
	public JSONBean<String> doManuallyGenerateReceipt(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("generateType") String generateType,
			RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		
		CustomerOrderDetail codQuery = new CustomerOrderDetail();
		codQuery.getParams().put("order_id", order_id);
		List<CustomerOrderDetail> cods = this.crmService.queryCustomerOrderDetails(codQuery);
		coQuery.setCustomerOrderDetails(cods);
		
		this.crmService.createReceiptPDFByDetails(coQuery);
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

		Customer cQuery = new Customer();
		cQuery.getParams().put("id", customer_id);
		Customer c = this.crmService.queryCustomer(cQuery);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/broadband-user/crm/plans/loading")
	public Map<String, Map<String, List<Plan>>> loadingPlans(HttpSession session) {
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		
		Plan plan = new Plan();
		plan.getParams().put("plan_type", customerRegAdmin.getSelect_plan_type());
		plan.getParams().put("plan_class", customerRegAdmin.getSelect_customer_type());
		//plan.getParams().put("plan_status", "selling");
		System.out.println("customerRegAdmin.getSelect_plan_group(): " + customerRegAdmin.getSelect_plan_group());
		if ("plan-topup".equals(customerRegAdmin.getSelect_plan_group())) {
			System.out.println("topup");
			plan.getParams().put("plan_group", "plan-topup");
		} else {
			System.out.println("!!!topup");
			plan.getParams().put("plan_group_false", "plan-topup");
		}
		plan.getParams().put("orderby", "order by plan_price");
		
		if ("VDSL".equals(customerRegAdmin.getSelect_plan_type()) 
				&& customerRegAdmin.getCustomerOrder().getSale_id() != null
				&& "personal".equals(customerRegAdmin.getSelect_customer_type())) {
			plan.getParams().put("id", 42);
		}
		
		List<Plan> plans = this.planService.queryPlans(plan);
		
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		
		this.wiredPlanMapBySort(planTypeMap, plans);
		
		return planTypeMap;
	}
	
	private void wiredPlanMapBySort(Map<String, Map<String, List<Plan>>> planTypeMap, List<Plan> plans) {
		if (plans != null) {
			for (Plan p: plans) {
				Map<String, List<Plan>> planMap = planTypeMap.get(p.getPlan_type());
				if (planMap == null) {
					planMap = new HashMap<String, List<Plan>>();	
					if ("CLOTHED".equals(p.getPlan_sort())) {
						List<Plan> plansClothed = new ArrayList<Plan>();
						plansClothed.add(p);
						planMap.put("plansClothed", plansClothed);
					} else if("NAKED".equals(p.getPlan_sort())) {
						List<Plan> plansNaked = new ArrayList<Plan>();
						plansNaked.add(p);
						planMap.put("plansNaked", plansNaked);
					}
					planTypeMap.put(p.getPlan_type(), planMap);
				} else {
					if ("CLOTHED".equals(p.getPlan_sort())) {
						List<Plan> plansClothed = planMap.get("plansClothed");
						if (plansClothed == null) {
							plansClothed = new ArrayList<Plan>();
							plansClothed.add(p);
							planMap.put("plansClothed", plansClothed);
						} else {
							plansClothed.add(p);
						}
					} else if("NAKED".equals(p.getPlan_sort())) {
						List<Plan> plansNaked = planMap.get("plansNaked");
						if (plansNaked == null) {
							plansNaked = new ArrayList<Plan>();
							plansNaked.add(p);
							planMap.put("plansNaked", plansNaked);
						} else {
							plansNaked.add(p);
						}
					}
				}
			}
		}
	}
	
	
	@RequestMapping("/broadband-user/crm/plans/hardware/loading")
	public List<Hardware> loadingHardwares(HttpSession session) {
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		hardware.getParams().put("hardware_type", "router");
		if ("ADSL".equals(customerRegAdmin.getSelect_plan_type())) {
			hardware.getParams().put("router_adsl", true);
		} else if ("VDSL".equals(customerRegAdmin.getSelect_plan_type())) {
			hardware.getParams().put("router_vdsl", true);
		} else if ("UFB".equals(customerRegAdmin.getSelect_plan_type())) {
			hardware.getParams().put("router_ufb", true);
		}
		
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		
		return hardwares;
	}
	
	private JSONBean<Customer> returnJsonCustomer(Customer customer, BindingResult result) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			if (!"transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				json.getErrorMap().remove("customerOrder.transition_porting_number");
				json.getErrorMap().remove("customerOrder.transition_provider_name");
				json.getErrorMap().remove("customerOrder.transition_account_number");
				json.getErrorMap().remove("customerOrder.transition_account_holder_name");
			}
			
			if (customer.getNewOrder()) {
				System.out.println("new order, so remove some field");
				json.getErrorMap().remove("cellphone");
				json.getErrorMap().remove("email");
				json.getErrorMap().remove("first_name");
				json.getErrorMap().remove("last_name");
				json.getErrorMap().remove("identity_number");
			}
			
			if (json.isHasErrors()) return json;
		}

		if (!customer.getNewOrder()) {
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
		}
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/apply/promotion-code", method = RequestMethod.POST)
	public JSONBean<InviteRates> applyPromotionCode(HttpSession session,	
			@Validated(value = { PromotionCodeValidatedMark.class }) InviteRates irates, BindingResult result) {
		
		JSONBean<InviteRates> json = new JSONBean<InviteRates>();
		json.setModel(irates);
		
		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		InviteRates ir = this.crmService.applyPromotionCode(irates.getPromotion_code());
		System.out.println("ir: " + ir);
		
		if (ir == null) {
			json.getErrorMap().put("promotion_code", "Sorry dear, this is invalid promotion code.");
			return json;
		}
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		
		customerRegAdmin.setIr(ir);
		json.setModel(ir);
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/cancel/promotion-code", method = RequestMethod.POST)
	public void cancelPromotionCode(HttpSession session) {
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		customerRegAdmin.setIr(null);
	}
	
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/confirm/personal", method = RequestMethod.POST)
	public JSONBean<Customer> doPlanOrderConfirmPersonal(
			@Validated(value = { CustomerValidatedMark.class, TransitionCustomerOrderValidatedMark.class }) 
			@RequestBody Customer customer, BindingResult result, HttpSession session) {
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		User userSession = (User) session.getAttribute("userSession");
		
		customerRegAdmin.setCurrentOperateUserid(userSession.getId());
		customerRegAdmin.setCellphone(customer.getCellphone());
		customerRegAdmin.setEmail(customer.getEmail());
		customerRegAdmin.setTitle(customer.getTitle());
		customerRegAdmin.setFirst_name(customer.getFirst_name());
		customerRegAdmin.setLast_name(customer.getLast_name());
		customerRegAdmin.setIdentity_type(customer.getIdentity_type());
		customerRegAdmin.setIdentity_number(customer.getIdentity_number());
		
		customerRegAdmin.setCustomerOrder(customer.getCustomerOrder());
		
		JSONBean<Customer> json = this.returnJsonCustomer(customerRegAdmin, result);
		
		this.crmService.doPlansOrderConfirm(customerRegAdmin);
		
		json.setUrl("/broadband-user/crm/plans/order/summary");
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/confirm/business", method = RequestMethod.POST)
	public JSONBean<Customer> doPlanOrderConfirmBusiness(
			@Validated(value = { TransitionCustomerOrderValidatedMark.class }) 
			@RequestBody Customer customer, BindingResult result, HttpSession session) {
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		User userSession = (User) session.getAttribute("userSession");
		
		customerRegAdmin.setCurrentOperateUserid(userSession.getId());
		customerRegAdmin.setCellphone(customer.getCellphone());
		customerRegAdmin.setEmail(customer.getEmail());
		customerRegAdmin.setTitle(customer.getTitle());
		customerRegAdmin.setFirst_name(customer.getFirst_name());
		customerRegAdmin.setLast_name(customer.getLast_name());
		customerRegAdmin.setIdentity_type(customer.getIdentity_type());
		customerRegAdmin.setIdentity_number(customer.getIdentity_number());
		
		customerRegAdmin.setCustomerOrder(customer.getCustomerOrder());
		
		JSONBean<Customer> json = this.returnJsonCustomer(customerRegAdmin, result);
		
		this.crmService.doPlansOrderConfirm(customerRegAdmin);
		
		json.setUrl("/broadband-user/crm/plans/order/summary");
		
		return json;
	}	
}

package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.google.code.kaptcha.Constants;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.DateUsage;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherBannedList;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.DataService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.CheckScriptInjection;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.ChangePasswordValidatedMark;
import com.tm.broadband.validator.mark.ContactUsValidatedMark;
import com.tm.broadband.validator.mark.CustomerForgottenPasswordValidatedMark;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;
import com.tm.broadband.validator.mark.OnlinePayByVoucherValidatedMark;

@RestController
@SessionAttributes(value = { "customer", "orderPlan"})
public class CustomerRestController {
	
	private CRMService crmService;
	private MailerService mailerService;
	private SmserService smserService;
	private SystemService systemService;
	private DataService dataService;
	private BillingService billingService;

	@Autowired
	public CustomerRestController(CRMService crmService, MailerService mailerService
			,SystemService systemService, SmserService smserService, DataService dataService
			,BillingService billingService) {
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.smserService = smserService;
		this.systemService = systemService;
		this.dataService = dataService;
		this.billingService = billingService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONBean<Customer> login(
			@Validated(CustomerLoginValidatedMark.class) Customer customer, BindingResult result, 
			HttpServletRequest req) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		customer.getParams().put("where", "when_login");
		customer.getParams().put("cellphone", customer.getLogin_name());
		customer.getParams().put("email", customer.getLogin_name());
		customer.getParams().put("password", customer.getPassword());
		customer.getParams().put("status", "active");
		Customer customerSession = this.crmService.queryCustomerWhenLogin(customer);

		if (customerSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}
		
		req.getSession().setAttribute("customerSession", customerSession);
		json.setUrl("/customer/home/redirect");

		return json;
	}
	
	@RequestMapping(value = "/forgotten-password", method = RequestMethod.POST)
	public JSONBean<Customer> forgottenPassword(
			@Validated(CustomerForgottenPasswordValidatedMark.class) Customer customer,
			BindingResult result,
			@RequestParam("code") String code,
			HttpServletRequest req) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		// If contains script> value then this is a script injection
		if(CheckScriptInjection.isScriptInjection(customer)){
			json.getErrorMap().put("alert-error", "Malicious actions are not allowed!");
			return json;
		}
		
		// if verification does not matched!
		if(!code.equalsIgnoreCase(req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString().trim())){
			json.getErrorMap().put("code", "verification code does not matched!");
			return json;
		}

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		if("email".equals(customer.getType())){
			
			customer.getParams().put("where", "query_forgotten_password_email");
			customer.setEmail(customer.getLogin_name());
			customer.getParams().put("email", customer.getLogin_name());
			
		} else if("cellphone".equals(customer.getType())){
			
			customer.getParams().put("where", "query_forgotten_password_cellphone");
			customer.setCellphone(customer.getLogin_name());
			customer.getParams().put("cellphone", customer.getLogin_name());
			
		}
		customer.getParams().put("status", "active");
		Customer customerSession = this.crmService.queryCustomer(customer);

		if (customerSession == null) {
			
			json.getErrorMap().put("alert-error", "email".equals(customer.getType()) ? "The email address you've just given isn't existed!" : "The mobile number you've just given isn't existed!");
			
		} else {
			customer.setPassword(TMUtils.generateRandomString(6));
			this.crmService.editCustomer(customer);
			
			CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
			Notification notification = this.systemService.queryNotificationBySort("forgotten-password", "email");
			
			String msg = "";

			if("email".equals(customer.getType())){
				msg = "We’ve sent an email to your email address containing a random login password. Please check your spam folder if the email doesn’t appear within a few minutes.";
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail); // call mail at value retriever
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				customer.getParams().put("email", customer.getLogin_name());
			} else if("cellphone".equals(customer.getType())){
				msg = "We’ve sent an message to your cellphone containing a random login password. Please check your spam folder if the message doesn’t appear within a few minutes.";
				notification = this.systemService.queryNotificationBySort("forgotten-password", "sms"); // get sms register template from db
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
				customer.getParams().put("cellphone", customer.getLogin_name());
			}
			
			json.getSuccessMap().put("alert-success", msg);
			
		}
		return json;
	}
	
	@RequestMapping(value = "/customer/change-password", method = RequestMethod.POST)
	public JSONBean<Customer> changePassword(
			@Validated(ChangePasswordValidatedMark.class) Customer customer, BindingResult result, 
			HttpServletRequest req) {
		
		Customer customerSession = (Customer)req.getSession().getAttribute("customerSession");
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		// If contains script> value then this is a script injection
		if(CheckScriptInjection.isScriptInjection(customer)){
			json.getErrorMap().put("alert-error", "Malicious actions are not allowed!");
			return json;
		}

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		if (!customer.getPassword().equals(customer.getCk_password())) {
			json.getErrorMap().put("password", "new password and confirm password is different");
			return json;
		}
		
		if (!customer.getOld_password().equals(customerSession.getPassword())) {
			json.getErrorMap().put("old_password", "old password is wrong");
			return json;
		}
 
		Customer tempC = new Customer();
		tempC.setPassword(customer.getPassword());
		tempC.getParams().put("id", customerSession.getId());
		this.crmService.editCustomer(tempC);
		customerSession.setPassword(customer.getPassword());
		
		req.getSession().setAttribute("customerSession", customerSession);
		json.setUrl("/customer/change-password/redirect");

		return json;
	}

	@RequestMapping(value = "/order/personal", method = RequestMethod.POST)
	public JSONBean<Customer> doOrderPersonal(Model model,
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer, BindingResult result, 
			HttpServletRequest req) {

		model.addAttribute("customer", customer);
		JSONBean<Customer> json = this.returnJsonCustomer(customer, result);
		json.setUrl("/order/personal/confirm");
		
		return json;
	}	
	
	@RequestMapping(value = "/order/business", method = RequestMethod.POST)
	public JSONBean<Customer> doOrderBusiness(Model model,
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer, BindingResult result, 
			HttpServletRequest req) {

		model.addAttribute("customer", customer);
		JSONBean<Customer> json = this.returnJsonCustomer(customer, result);
		json.setUrl("/order/business/confirm");
		
		return json;
	}
	
	private JSONBean<Customer> returnJsonCustomer(Customer customer, BindingResult result) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		// If contains script> value then this is a script injection
		if(CheckScriptInjection.isScriptInjection(customer)){
			json.getErrorMap().put("alert-error", "Malicious actions are not allowed!");
			return json;
		}
		
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
		return json;
	}
	
	@RequestMapping(value = "/contact-us", method = RequestMethod.POST)
	public JSONBean<ContactUs> doContactUs(Model model,
			@Validated(ContactUsValidatedMark.class) ContactUs contactUs, BindingResult result, 
			HttpServletRequest req) {
		
		JSONBean<ContactUs> json = new JSONBean<ContactUs>();
		
		// If contains script> value then this is a script injection
		if (CheckScriptInjection.isScriptInjection(contactUs)) {
			json.getErrorMap().put("alert-error", "Malicious actions are not allowed!");
			return json;
		}
		
		// if verification does not matched!
		if (!contactUs.getCode().equalsIgnoreCase(req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString().trim())) {
			json.getErrorMap().put("code", "Verification code does not matched!");
			return json;
		}

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		contactUs.setStatus("new");
		contactUs.setSubmit_date(new Date());
		this.crmService.createContactUs(contactUs);
		
		json.getSuccessMap().put("alert-success", "Your request has been submitted, we will respond you as fast as we can.");

		return json;
	}
	
	@RequestMapping("/customer/data/view/{calculator_date}")
	public List<DateUsage> doCustomerUsageView(HttpServletRequest req,
			@PathVariable("calculator_date") String calculator_date){
		
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
		CustomerOrder co = customer.getCustomerOrders().get(0);
		String svlan = co.getSvlan()
				, cvlan = co.getCvlan();
		
		Calendar c = Calendar.getInstance();
		
		String[] date_array = calculator_date.split("-");
		int year = Integer.parseInt(date_array[0]);
		int month = Integer.parseInt(date_array[1]);
		
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		
		int days = TMUtils.judgeDay(year, month);

		List<DateUsage> dateUsages = new ArrayList<DateUsage>();
		for (int i = 0; i < days; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			Date date = c.getTime();
			
			DateUsage dateUsage = new DateUsage();
			dateUsage.setDate(TMUtils.dateFormatYYYYMMDD(date));
			dateUsages.add(dateUsage);
		}
		
		NetworkUsage u = new NetworkUsage();
		u.getParams().put("where", "query_currentMonth");
		u.getParams().put("vlan", svlan + cvlan);
		u.getParams().put("currentYear", year);
		u.getParams().put("currentMonth", month);
		
		List<NetworkUsage> usages = this.dataService.queryUsages(u);

		if (usages != null && usages.size() > 0) {
			for (NetworkUsage usage: usages) {
				for (DateUsage dateUsage: dateUsages) {
					//System.out.println(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()));
					if (dateUsage.getDate().equals(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()))) {
						dateUsage.setUsage(usage);
						System.out.println(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()));
						break;
					}
				}
			}
		}
		
		return dateUsages;
		
	}
	
	// BEGIN Voucher
	@RequestMapping(value = "/customer/invoice/defray/voucher", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByVoucher(Model model,
			@RequestParam("invoice_id") int invoice_id,
			@RequestParam("pin_number") String pin_number,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(invoice_id);
		ci.getParams().put("id", ci.getId());

		Integer customer_id = ci.getCustomer_id();
		Integer order_id = ci.getOrder_id();
		String process_way = "Voucher";
		String process_sort = null;
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
		// Determine whether in banned list or not
		VoucherBannedList vbl = new VoucherBannedList();
		vbl.getParams().put("customer_id", customer_id);
		vbl.getParams().put("where", "query_less_equal_date");
		List<VoucherBannedList> vbls = this.billingService.queryVoucherBannedList(vbl);
		vbl = vbls!=null && vbls.size()>0 ? vbls.get(0) : null;
		
		if(vbl!=null){
			json.getErrorMap().put("alert-error", "You had been banned for being attempted brute force!");
			return json;
		}
		
		Voucher v = new Voucher();
		v.getParams().put("card_number", pin_number);
		v.getParams().put("status", "unused");
		List<Voucher> vs = this.billingService.queryVouchers(v);
		v = vs!=null && vs.size()>0 ? vs.get(0) : null;
		
		if(v==null){
			vbl = null;
			vbl = new VoucherBannedList();
			vbl.setCustomer_id(customer_id);
			vbl.getParams().put("customer_id", customer_id);
			vbls = this.billingService.queryVoucherBannedList(vbl);
			// If have attempts or banned record
			if(vbls!=null && vbls.size()>0){
				Integer attempts = vbls.get(0).getAttempt_times();
				// If greater equal to three, then banned!
				if(attempts >= 2){
					vbl.setForbad_date(new Date());
					vbl.setAttempt_times(0);
					this.billingService.editVoucherBannedList(vbl);
					json.getErrorMap().put("alert-error", "You have tried 3 times incoorect! You had been banned for being attempted brute force!");
					return json;
				// If not greater equal to three, then attempts+1
				} else {
					vbl.setAttempt_times(attempts+1);
					this.billingService.editVoucherBannedList(vbl);
					json.getErrorMap().put("alert-error", "You have tried "+ (attempts+1) +" times incorrect! If you have tried 3 times incorrect then you will temporarily be blocked into voucher banned list. But this won't be affected to your other functions.");
					return json;
				}
			// Else no attempts or banned record
			} else {
				vbl.setAttempt_times(1);
				this.billingService.createVoucherBannedList(vbl);
			}
			json.getErrorMap().put("alert-error", "You have tried 1 times incorrect! If you have tried 3 times incorrect then you will temporarily be blocked into voucher banned list. But this won't be affected to your other functions.");
			return json;
		}

		if (ci.getBalance() <= 0d) {
			// If invoice is paid off then no reason for executing the below
			// operations
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			json.getSuccessMap().put("alert-success", "Voucher Haven't been used! We just change the status");
			return json;
		}
		
		Double paid_amount = v.getFace_value();
		
		Customer c = null;
		// If voucher is less equal balance
		if(v.getFace_value() <= ci.getBalance()){
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), v.getFace_value()));
			ci.setBalance(TMUtils.bigOperationTwoReminders(ci.getBalance(), v.getFace_value(), "sub"));
		// Else voucher is greater than balance
			json.getSuccessMap().put("alert-success", "Voucher defray had successfully been operates! Please refresh your browser by lightly press on F5 key on the keyboard to see the changes of invoice's balance.");
		} else {
			c = this.crmService.queryCustomerById(customer_id);
			c.setBalance(TMUtils.bigAdd(c.getBalance()!=null ? c.getBalance() : 0d, TMUtils.bigSub(v.getFace_value(), ci.getBalance())));
			c.getParams().put("id", customer_id);
			this.crmService.editCustomer(c);
			ci.setAmount_paid(TMUtils.bigAdd(ci.getAmount_paid(), ci.getBalance()));
			ci.setBalance(0d);
			json.getSuccessMap().put("alert-success", "Voucher defray had successfully been operates! Surplus will be add into your credit. Please refresh your browser by lightly press on F5 key on the keyboard to see the changes of invoice's balance and your account credit.");
		}
		v.setStatus("used");
		v.getParams().put("serial_number", v.getSerial_number());
		v.setCustomer_id(customer_id);
		process_way+="# - "+v.getCard_number();
		this.billingService.editVoucher(v);

		// paid (off)
		if (ci.getBalance() <= 0d) {
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT

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
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER
		
		Customer customerSession = (Customer) req.getSession().getAttribute("customerSession");
		customerSession.setBalance(c!=null ? c.getBalance() : customerSession.getBalance());
		customerSession.getCustomerInvoice().setBalance(ci.getBalance());

		req.getSession().setAttribute("customerSession", customerSession);

		return json;
	}
	// END Voucher

	
	// BEGIN Voucher Topup
	@RequestMapping(value = "/customer/account/topup/voucher", method = RequestMethod.POST)
	public JSONBean<String> doAccountTopupByVoucher(Model model,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("pin_number") String pin_number,
			HttpServletRequest req) {

		JSONBean<String> json = new JSONBean<String>();

		String process_way = "Voucher";
		
		// Determine whether in banned list or not
		VoucherBannedList vbl = new VoucherBannedList();
		vbl.getParams().put("customer_id", customer_id);
		vbl.getParams().put("where", "query_less_equal_date");
		List<VoucherBannedList> vbls = this.billingService.queryVoucherBannedList(vbl);
		vbl = vbls!=null && vbls.size()>0 ? vbls.get(0) : null;
		
		if(vbl!=null){
			json.getErrorMap().put("alert-error", "You had been banned for being attempted brute force!");
			return json;
		}
		
		Voucher v = new Voucher();
		v.getParams().put("card_number", pin_number);
		v.getParams().put("status", "unused");
		List<Voucher> vs = this.billingService.queryVouchers(v);
		v = vs!=null && vs.size()>0 ? vs.get(0) : null;
		
		if(v==null){
			vbl = null;
			vbl = new VoucherBannedList();
			vbl.setCustomer_id(customer_id);
			vbl.getParams().put("customer_id", customer_id);
			vbls = this.billingService.queryVoucherBannedList(vbl);
			// If have attempts or banned record
			if(vbls!=null && vbls.size()>0){
				Integer attempts = vbls.get(0).getAttempt_times();
				// If greater equal to three, then banned!
				if(attempts >= 2){
					vbl.setForbad_date(new Date());
					vbl.setAttempt_times(0);
					this.billingService.editVoucherBannedList(vbl);
					json.getErrorMap().put("alert-error", "You have tried 3 times incoorect! You had been banned for being attempted brute force!");
					return json;
				// If not greater equal to three, then attempts+1
				} else {
					vbl.setAttempt_times(attempts+1);
					this.billingService.editVoucherBannedList(vbl);
					json.getErrorMap().put("alert-error", "You have tried "+ (attempts+1) +" times incorrect! If you have tried 3 times incorrect then you will temporarily be blocked into voucher banned list. But this won't be affected to your other functions.");
					return json;
				}
			// Else no attempts or banned record
			} else {
				vbl.setAttempt_times(1);
				this.billingService.createVoucherBannedList(vbl);
			}
			json.getErrorMap().put("alert-error", "You have tried 1 times incorrect! If you have tried 3 times incorrect then you will temporarily be blocked into voucher banned list. But this won't be affected to your other functions.");
			return json;
		}

		Customer c = this.crmService.queryCustomerById(customer_id);
		c.setBalance(TMUtils.bigAdd(c.getBalance()!=null ? c.getBalance() : 0d, v.getFace_value()));
		c.getParams().put("id", customer_id);
		this.crmService.editCustomer(c);
		json.getSuccessMap().put("alert-success", "Voucher topup had successfully been operates! Voucher amount had been added into your account credit. Please refresh your browser by lightly press on F5 key on the keyboard to see the changes of your account credit, if no changes please try logout and then logon again.");

		v.setStatus("used");
		v.getParams().put("serial_number", v.getSerial_number());
		v.setCustomer_id(customer_id);
		process_way+="# - "+v.getCard_number();
		this.billingService.editVoucher(v);


		// BEGIN TRANSACTION ASSIGNMENT
		CustomerTransaction ct = new CustomerTransaction();
		// Assign invoice's paid amount to transaction's amount
		ct.setAmount(v.getFace_value());
		ct.setAmount_settlement(v.getFace_value());
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort
		ct.setTransaction_sort("voucher-topup");
		// fields
		ct.setCustomer_id(customer_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		ct.setCurrency_input("Voucher");
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER
		
		Customer customerSession = (Customer) req.getSession().getAttribute("customerSession");
		customerSession.setBalance(c.getBalance());

		req.getSession().setAttribute("customerSession", customerSession);
		

		return json;
	}
	
	
	@RequestMapping(value="/plans/plan-topup/online-pay-by-voucher", method = RequestMethod.POST)
	public JSONBean<Voucher> onlinePayByVoucher(
			@Validated(OnlinePayByVoucherValidatedMark.class) Voucher voucher, BindingResult result, 
			HttpServletRequest req) {
		
		JSONBean<Voucher> json = new JSONBean<Voucher>();
		json.setModel(voucher);
		
		json.setJSONErrorMap(result, voucher.getIndex());
		
		if (json.isHasErrors()) return json;
		
		voucher.getParams().put("serial_number", voucher.getSerial_number());
		voucher.getParams().put("card_number", voucher.getCard_number());
		//voucher.getParams().put("status", "unused");
		
		Voucher v = this.billingService.queryVoucher(voucher);
		if (v == null) {
			json.getErrorMap().put("serial_number" + voucher.getIndex(), "Incorrect Card Number or Pin Number.");
			return json;
		}
		if ("used".equals(v.getStatus())) {
			json.getErrorMap().put("serial_number" + voucher.getIndex(), "Voucher has been used.");
			return json;
		}
		
		Customer customer = (Customer) req.getSession().getAttribute("customer");
		
		for (Voucher vQuery: customer.getVouchers()) {
			if (vQuery.getSerial_number().equals(voucher.getSerial_number())
					&& vQuery.getCard_number().equals(voucher.getCard_number())) {
				json.getErrorMap().put("serial_number" + voucher.getIndex(), "Voucher has been applied.");
				return json;
			}
		}
		
		customer.getVouchers().add(v);
		
		json.setModel(v);
		json.getSuccessMap().put("alert-success", "Cyberpark Voucher " + voucher.getSerial_number() + " has been applied.");
		
		return json;
	}
	
	@RequestMapping(value="/plans/plan-topup/cancel-voucher-apply", method = RequestMethod.POST)
	public JSONBean<Voucher> cancelVoucherApply(Voucher voucher, HttpServletRequest req) {
		
		JSONBean<Voucher> json = new JSONBean<Voucher>();
		json.setModel(voucher);
		
		Customer customer = (Customer) req.getSession().getAttribute("customer");
		
		for (Voucher vQuery: customer.getVouchers()) {
			if (vQuery.getSerial_number().equals(voucher.getSerial_number())
					&& vQuery.getCard_number().equals(voucher.getCard_number())) {
				json.getSuccessMap().put("alert-success", "Voucher Card Number " + voucher.getSerial_number() + " is available.");
				customer.getVouchers().remove(vQuery);
				json.setModel(vQuery);
				break;
			}
		}
		
		return json;
	}
	// END Voucher

	// END Voucher Topup

	// BEGIN Account Credit
	@RequestMapping(value = "/customer/invoice/defray/account-credit", method = RequestMethod.POST)
	public JSONBean<String> doDefrayByCash(Model model,
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
		// END TRANSACTION ASSIGNMENT

		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		// END CALL SERVICE LAYER
		
		Customer customerSession = (Customer) req.getSession().getAttribute("customerSession");
		customerSession.setBalance(c.getBalance());
		customerSession.getCustomerInvoice().setBalance(ci.getBalance());

		req.getSession().setAttribute("customerSession", customerSession);
		
		json.getSuccessMap().put("alert-success", "Account Credit defray had successfully been operates! Please refresh your browser by lightly press on F5 key on the keyboard to see the changes of invoice's balance and your account credit.");

		return json;
	}
	// END Account Credit
	
}

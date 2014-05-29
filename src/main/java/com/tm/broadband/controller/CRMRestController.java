package com.tm.broadband.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.ManualDefrayLog;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.pdf.OrderPDFCreator;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@RestController
@SessionAttributes({ "customer", "customerOrder", "hardwares", "plans" })
public class CRMRestController {
	
	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;

	@Autowired
	public CRMRestController(CRMService crmService, MailerService mailerService, SystemService systemService,
			SmserService smserService) {
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.smserService = smserService;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/personal/create", method = RequestMethod.POST)
	public JSONBean<Customer> customerPersonalCreate(Model model, 
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer, BindingResult result) {

		return this.returnJsonCustomer(model, customer, result);
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/business/create", method = RequestMethod.POST)
	public JSONBean<Customer> customerBusinessCreate(Model model, 
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer, BindingResult result) {

		return this.returnJsonCustomer(model, customer, result);
	}
	
	private JSONBean<Customer> returnJsonCustomer(Model model, Customer customer, BindingResult result) {
		
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
	public JSONBean<Customer> customerPersonalEdit( Model model,
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer, BindingResult result, 
			SessionStatus status) {
		
		return this.returnJsonCustomerEdit(customer, result, status);
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/business/edit")
	public JSONBean<Customer> customerBusinessEdit( Model model,
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer, BindingResult result, 
			SessionStatus status) {
		
		return this.returnJsonCustomerEdit(customer, result, status);
	}
	
	private JSONBean<Customer> returnJsonCustomerEdit(Customer customer, BindingResult result, SessionStatus status) {
		
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
		
		//status.setComplete();
		
		json.getSuccessMap().put("alert-success", "Edit customer (ID: " + customer.getId() + ") is successful.");
		
		return json;
	}
	
	// BEGIN DDPay
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/ddpay", method = RequestMethod.POST)
	public JSONBean<ManualDefrayLog> doDefrayByDDPay(Model model,
			ManualDefrayLog manualDefrayLog, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<ManualDefrayLog> json = new JSONBean<ManualDefrayLog>();
		
		Integer customer_id = null;
		Integer order_id = null;
		Integer invoice_id = null;
		Double paid_amount = null;
		String process_way = "DDPay";
		String process_sort = null;
		
		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(manualDefrayLog.getInvoice_id());
		ci.getParams().put("id", ci.getId());
		
		String redirectUrl = "/manual_defray/redirect/"+ci.getCustomer_id()+"/"+ci.getId()+"/"+ci.getOrder_id()+"/"+process_way+"/"+ci.getBalance();
		if(ci.getBalance() <= 0d){
			// If invoice is paid off then no reason for executing the below operations
			json.setUrl(redirectUrl);
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			return json;
		}
		customer_id = ci.getCustomer_id();
		order_id = ci.getOrder_id();
		invoice_id = ci.getId();
		paid_amount = ci.getAmount_payable();
		
		// Assign payable to paid, make this invoice paid off
		ci.setAmount_paid(paid_amount);
		// Assign balance as 0.0, make this invoice paid off
		ci.setBalance(0d);
		// Assign status to paid directly, make this invoice paid off
		ci.setStatus("paid");
		// END INVOICE ASSIGNMENT
		
		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(ci.getOrder_id())) {
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
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		// END TRANSACTION ASSIGNMENT
		
		// BEGIN MANUAL DEFRAY LOG ASSIGNMENT
		User userSession = (User) req.getSession().getAttribute("userSession");
		ManualDefrayLog mdl = new ManualDefrayLog();
		
		mdl.setUser_id(userSession.getId());
		mdl.setCustomer_id(customer_id);
		mdl.setOrder_id(order_id);
		mdl.setInvoice_id(invoice_id);
		mdl.setEliminate_amount(paid_amount);
		mdl.setEliminate_way(process_way);
		mdl.setEliminate_time(new Date());
		// END MANUAL DEFRAY LOG ASSIGNMENT
		
		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		this.crmService.createManualDefrayLog(mdl);
		// END CALL SERVICE LAYER
		
		// BEGIN REGENERATE INVOICE PDF
		this.crmService.createInvoicePDFByInvoiceID(ci.getId());
		// END REGENERATE INVOICE PDF

		json.setUrl(redirectUrl);
		
		return json;
	}
	// END DDPay
	
	// BEGIN Cash
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/defray/cash", method = RequestMethod.POST)
	public JSONBean<ManualDefrayLog> doDefrayByCash(Model model,
			ManualDefrayLog manualDefrayLog, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<ManualDefrayLog> json = new JSONBean<ManualDefrayLog>();
		
		Integer customer_id = null;
		Integer order_id = null;
		Integer invoice_id = null;
		Double paid_amount = null;
		String process_way = "Cash";
		String process_sort = null;
		
		// BEGIN INVOICE ASSIGNMENT
		CustomerInvoice ci = this.crmService.queryCustomerInvoiceById(manualDefrayLog.getInvoice_id());
		ci.getParams().put("id", ci.getId());
		
		String redirectUrl = "/manual_defray/redirect/"+ci.getCustomer_id()+"/"+ci.getId()+"/"+ci.getOrder_id()+"/"+process_way+"/"+ci.getBalance();
		if(ci.getBalance() <= 0d){
			// If invoice is paid off then no reason for executing the below operations
			json.setUrl(redirectUrl);
			ci.setStatus("paid");
			this.crmService.editCustomerInvoice(ci);
			return json;
		}
		customer_id = ci.getCustomer_id();
		order_id = ci.getOrder_id();
		invoice_id = ci.getId();
		paid_amount = manualDefrayLog.getEliminate_amount();
		
		// Assign (paid plus eliminate amount) to paid, make this invoice paid (off)
		ci.setAmount_paid(ci.getAmount_paid() + paid_amount);
		// Assign balance as 0.0, make this invoice paid off
		ci.setBalance(ci.getBalance() - paid_amount);
		// If balance equals to 0d then paid else not_pay_off, make this invoice paid (off)
		if(ci.getBalance() <= 0d){
			ci.setStatus("paid");
		} else {
			ci.setStatus("not_pay_off");
		}
		// END INVOICE ASSIGNMENT
		
		// Get order_type
		switch (this.crmService.queryCustomerOrderTypeById(ci.getOrder_id())) {
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
		// Assign card_name as ddpay
		ct.setCard_name(process_way);
		// Assign transaction's sort as type's return from order by order_id
		ct.setTransaction_sort(process_sort);
		// Assign customer_id, order_id, invoice_id to transaction's related fields
		ct.setCustomer_id(customer_id);
		ct.setOrder_id(order_id);
		ct.setInvoice_id(invoice_id);
		// Assign transaction's time as current time
		ct.setTransaction_date(new Date());
		// END TRANSACTION ASSIGNMENT
		
		// BEGIN MANUAL DEFRAY LOG ASSIGNMENT
		User userSession = (User) req.getSession().getAttribute("userSession");
		ManualDefrayLog mdl = new ManualDefrayLog();
		
		mdl.setUser_id(userSession.getId());
		mdl.setCustomer_id(customer_id);
		mdl.setOrder_id(order_id);
		mdl.setInvoice_id(invoice_id);
		mdl.setEliminate_amount(paid_amount);
		mdl.setEliminate_way(process_way);
		mdl.setEliminate_time(new Date());
		// END MANUAL DEFRAY LOG ASSIGNMENT
		
		// BEGIN CALL SERVICE LAYER
		this.crmService.editCustomerInvoice(ci);
		this.crmService.createCustomerTransaction(ct);
		this.crmService.createManualDefrayLog(mdl);
		// END CALL SERVICE LAYER
		
		// BEGIN REGENERATE INVOICE PDF
		this.crmService.createInvoicePDFByInvoiceID(ci.getId());
		// END REGENERATE INVOICE PDF
		

		json.setUrl(redirectUrl);
		
		return json;
	}
	// END Cash

	// Update order status
	@RequestMapping(value = "/broadband-user/crm/customer/order/status/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderStatusEdit( Model model,
			CustomerOrder customerOrder) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		CustomerOrder co = customerOrder;
		co.getParams().put("id", co.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update order due date
	@RequestMapping(value = "/broadband-user/crm/customer/order/due_date/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderDueDateEdit( Model model,
			CustomerOrder customerOrder) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		CustomerOrder co = new CustomerOrder();
		co.setOrder_due(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_due_str()));
		co.getParams().put("id", customerOrder.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update order belongs to
	@RequestMapping(value = "/broadband-user/crm/customer/order/belongs_to/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderBelonsToEdit( Model model,
			CustomerOrder customerOrder,
			@RequestParam("user_name") String user_name) {
		
		JSONBean<String> json = new JSONBean<String>();
		CustomerOrder co = customerOrder;
		co.getParams().put("id", co.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(user_name);
		return json;
	}
	
	// Update order PPPoE
	@RequestMapping(value = "/broadband-user/crm/customer/order/ppppoe/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerPPPoEEdit(Model model,
			CustomerOrder customerOrder) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		// If loginname is empty
		boolean loginNameEmpty = customerOrder.getPppoe_loginname().trim().equals("");
		// If password is empty
		boolean passwordEmpty = customerOrder.getPppoe_password().trim().equals("");
		
		// If any of them is empty
		boolean anyEmpty = loginNameEmpty || passwordEmpty;
		
		if (anyEmpty) {
			if(loginNameEmpty){
				json.getErrorMap().put(customerOrder.getId()+"_pppoe_loginname_input", "Enter PPPoE login name!");
			}
			if(passwordEmpty){
				json.getErrorMap().put(customerOrder.getId()+"_pppoe_password_input", "Enter PPPoE password!");
			}
			return json;
		}
		CustomerOrder co = new CustomerOrder();
		co.setPppoe_loginname(customerOrder.getPppoe_loginname());
		co.setPppoe_password(customerOrder.getPppoe_password());
		co.getParams().put("id", customerOrder.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update SV/CVLan & RFS date
	@RequestMapping(value = "broadband-user/crm/customer/order/save/svcvlanrfsdate", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerRFSDateEdit( Model model,
			CustomerOrder customerOrder,
			@RequestParam("way") String way) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		
		// If SVLan is empty
		boolean svLanEmpty = customerOrder.getSvlan().trim().equals("");
		// If CVLan is empty
		boolean cvLanEmpty = customerOrder.getCvlan().trim().equals("");
		
		// If any of them is empty
		boolean anyLanEmpty = svLanEmpty || cvLanEmpty;
		
		if (anyLanEmpty) {
			if(svLanEmpty){
				json.getErrorMap().put(customerOrder.getId()+"_svlan_input", "Enter SVLan!");
			}
			if(cvLanEmpty){
				json.getErrorMap().put(customerOrder.getId()+"_cvlan_input", "Enter CVLan!");
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
		
		Customer customer = this.crmService.queryCustomerById(customerOrder.getCustomer_id());
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("service-installation", "email");
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// call mail at value retriever
		TMUtils.mailAtValueRetriever(notification, customer, customerOrder, cods,  companyDetail);
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);

		// get sms register template from db
		notification = this.systemService.queryNotificationBySort("service-installation", "sms");
		TMUtils.mailAtValueRetriever(notification, customer, customerOrder, cods, companyDetail);
		// send sms to customer's mobile phone
		this.smserService.sendSMSByAsynchronousMode(customer, notification);
		
		
		CustomerOrder co = new CustomerOrder();
		co.setSvlan(customerOrder.getSvlan());
		co.setCvlan(customerOrder.getCvlan());
		co.setRfs_date(TMUtils.parseDateYYYYMMDD(customerOrder.getRfs_date_str()));
		co.getParams().put("id", customerOrder.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update service giving date
	@RequestMapping(value = "/broadband-user/crm/customer/order/service_giving_date", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerServiceGivingDateEdit( Model model,
			CustomerOrder customerOrder,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			@RequestParam("way") String way,
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
		co.setOrder_using_start(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
		co.getParams().put("id", customerOrder.getId());
		co.setOrder_status("using");
		
		if (!"order-topup".equals(customerOrder.getOrder_type()) && !"order-term".equals(customerOrder.getOrder_type())) {
			Calendar calNextInvoiceDay = Calendar.getInstance();
			calNextInvoiceDay.setTime(TMUtils.parseDateYYYYMMDD(customerOrder.getOrder_using_start_str()));
			// Add plan unit months
			calNextInvoiceDay.add(Calendar.MONTH, order_detail_unit);
			// Go back 15 days
			calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, -15);
			// set next invoice date
			co.setNext_invoice_create_date(calNextInvoiceDay.getTime());
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			co.setOrder_due(cal.getTime());
		}
		
		if("save".equals(way)){
			co.setOrder_type(customerOrder.getOrder_type());
			proLog.setProcess_way(customerOrder.getOrder_status() + " to using");
			// check order status
			if ("ordering-paid".equals(customerOrder.getOrder_status()) || "order-term".equals(customerOrder.getOrder_type())) {

				// send mailer
				Customer customer = this.crmService.queryCustomerById(customerOrder.getCustomer_id());
				CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
				Notification notification = this.systemService.queryNotificationBySort("service-giving", "email");
				TMUtils.mailAtValueRetriever(notification, customer, customerOrder, companyDetail); // call mail at value retriever
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				notification = this.systemService.queryNotificationBySort("service-giving", "sms"); // get sms register template from db
				TMUtils.mailAtValueRetriever(notification, customer, customerOrder, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer, notification); // send sms to customer's mobile phone
				
			} else if ("ordering-pending".equals(customerOrder.getOrder_status()) && !"order-term".equals(customerOrder.getOrder_type())) {
				
				Notification notificationEmail = this.systemService.queryNotificationBySort("service-giving", "email");
				Notification notificationSMS = this.systemService.queryNotificationBySort("service-giving", "sms");
				this.crmService.createInvoicePDF(customerOrder, notificationEmail, notificationSMS);
					
			}
		} else {
			proLog.setProcess_way("editing service giving");
			Customer customer = this.crmService.queryCustomerById(customerOrder.getCustomer_id());
			CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
			Notification notification = this.systemService.queryNotificationBySort("service-giving", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			// call mail at value retriever
			TMUtils.mailAtValueRetriever(notification, customer, customerOrder,  companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);

			// get sms register template from db
			notification = this.systemService.queryNotificationBySort("service-giving", "sms");
			TMUtils.mailAtValueRetriever(notification, customer, customerOrder, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer, notification);
		}
		
		this.crmService.editCustomerOrder(co, proLog);
		
		json.setModel(co);
		return json;
	}

	// Update optional request
	@RequestMapping(value = "/broadband-user/crm/customer/order/optional_request/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderOptionalRequestEdit( Model model,
			CustomerOrder customerOrder) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		CustomerOrder co = customerOrder;
		co.getParams().put("id", co.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update broadband asid
	@RequestMapping(value = "/broadband-user/crm/customer/order/broadband_asid/edit", method = RequestMethod.POST)
	public JSONBean<CustomerOrder> doCustomerOrderBroadbandASIDEdit( Model model,
			CustomerOrder customerOrder) {
		
		JSONBean<CustomerOrder> json = new JSONBean<CustomerOrder>();
		CustomerOrder co = customerOrder;
		co.getParams().put("id", co.getId());
		
		this.crmService.editCustomerOrder(co);
		json.setModel(co);
		return json;
	}

	// Update customer info
	@RequestMapping(value = "/broadband-user/crm/customer/edit")
	public Map<String, Object> toCustomerEdit(Model model,
			@RequestParam("id") int id) {

		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		User user = new User();
		user.getParams().put("user_role", "sales");
		List<User> users = this.systemService.queryUser(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customer", customer);
		map.put("users", users);
		
		return map;
	}

	// Update PSTN
	@RequestMapping(value = "/broadband-user/crm/customer/order/pstn/edit", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailPSTNEdit(Model model
			,@RequestParam("order_detail_id") int order_detail_id
			,@RequestParam("customer_id") int customer_id
			,@RequestParam("pstn_number") String pstn_number
			,RedirectAttributes attr) {

		JSONBean<String> json = new JSONBean<String>();
		
		CustomerOrderDetail cod = new CustomerOrderDetail();
		cod.getParams().put("id", order_detail_id);
		cod.setPstn_number(pstn_number);
		
		this.crmService.editCustomerOrderDetail(cod);
		
		// Update Customer Order Detail PSTN is successful.

		return json;
	}

	// Add discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/discount/save", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailDiscountCreate(Model model
			,@RequestParam("order_id") int order_id
			,@RequestParam("customer_id") int customer_id
			,@RequestParam("detail_name") String detail_name
			,@RequestParam("detail_price") Double detail_price
			,@RequestParam("detail_unit") Integer detail_unit
			,@RequestParam("detail_expired") String detail_expired
			,@RequestParam("detail_type") String detail_type
			,RedirectAttributes attr) {
		
		CustomerOrderDetail customerOrderDetail = new CustomerOrderDetail();
		customerOrderDetail.setOrder_id(order_id);
		customerOrderDetail.setDetail_name(detail_name);
		customerOrderDetail.setDetail_price(detail_price);
		customerOrderDetail.setDetail_unit(detail_unit);
		customerOrderDetail.setDetail_expired(TMUtils.parseDateYYYYMMDD(detail_expired));
		customerOrderDetail.setDetail_type(detail_type);
		this.crmService.createCustomerOrderDetail(customerOrderDetail);

		JSONBean<String> json = new JSONBean<String>();
		
		// Create Customer Order Detail Discount is successful.

		return json;
	}

	// Remove discount
	@RequestMapping(value = "/broadband-user/crm/customer/order/discount/remove", method = RequestMethod.POST)
	public JSONBean<String> doCustomerOrderDetailDiscountRemove(Model model
			,@RequestParam("order_detail_id") int order_detail_id
			,@RequestParam("customer_id") int customer_id
			,RedirectAttributes attr) {
		
		JSONBean<String> json = new JSONBean<String>();
		this.crmService.removeCustomerOrderDetailById(order_detail_id);

		// Remove Customer Order Detail Discount is successful.

		return json;
	}

	// Regenerate invoice PDF
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/generate/{invoiceId}")
	public void generateInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId){
		this.crmService.createInvoicePDFByInvoiceID(invoiceId);
	}

	// Regenerate application form PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/application_form/regenerate/{order_id}")
	public void regenerateOrderApplicationForm(Model model
			,@PathVariable("order_id") int order_id) {
		
		CustomerOrder co = this.crmService.queryCustomerOrderById(order_id);

		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
		String orderPDFPath = null;
		try {
			orderPDFPath = new OrderPDFCreator(co.getCustomer(), co, co.getCustomer().getOrganization()).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		co.getParams().put("id", co.getId());
		co.setOrder_pdf_path(orderPDFPath);
		
		this.crmService.editCustomerOrder(co);
		// END SET NECESSARY INFO AND GENERATE ORDER PDF
		
		// Regenerate order application PDF is successful.

	}
	
}

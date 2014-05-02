package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes({ "customer", "customerOrder", "hardwares", "plans" })
public class CRMController {

	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private PlanService planService;
	private SmserService smserService;

	@Autowired
	public CRMController(CRMService crmService, MailerService mailerService, SystemService systemService,
			PlanService planService,
			SmserService smserService) {
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.planService = planService;
		this.smserService = smserService;
	}

	@RequestMapping("/broadband-user/crm/customer/view/{pageNo}")
	public String customerView(Model model, @PathVariable("pageNo") int pageNo) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by register_date desc");
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("customerQuery", new Customer());
		return "broadband-user/crm/customer-view";
	}

	@RequestMapping("/broadband-user/crm/customer/query/{pageNo}")
	public String customerQuery(Model model, 
			@PathVariable("pageNo") int pageNo,
			@ModelAttribute("customerQuery") Customer customer, RedirectAttributes attr) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by register_date desc");
		page.getParams().put("id", customer.getId());
		page.getParams().put("login_name", customer.getLogin_name());
		page.getParams().put("phone", customer.getPhone());
		page.getParams().put("cellphone", customer.getCellphone());
		page.getParams().put("email", customer.getEmail());
		page.getParams().put("svlan", customer.getCustomerOrder().getSvlan());
		page.getParams().put("cvlan", customer.getCustomerOrder().getCvlan());
		
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		return "broadband-user/crm/customer-view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/edit/{id}")
	public String toCustomerEdit(Model model, @PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Customer Edit");
		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		model.addAttribute("customer", customer);
		return "broadband-user/crm/customer";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/remove/{id}")
	public String customerRemove(Model model,
			@PathVariable(value = "id") int id, RedirectAttributes attr) {
		this.crmService.removeCustomer(id);
		attr.addFlashAttribute("success", "Remove customer is successful.");
		return "redirect:/broadband-user/crm/customer/query/1";
	}
	
	@RequestMapping(value = "/broadband-user/crm/transaction/view/{pageNo}/{customerId}")
	@ResponseBody
	public Page<CustomerTransaction> transactionPage(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "customerId") int customerId) {
		
		Page<CustomerTransaction> transactionPage = new Page<CustomerTransaction>();
		transactionPage.setPageNo(pageNo);
		transactionPage.setPageSize(30);
		transactionPage.getParams().put("orderby", "order by transaction_date desc");
		transactionPage.getParams().put("customer_id", customerId);
		this.crmService.queryCustomerTransactionsByPage(transactionPage);

		return transactionPage;
	}
	
	@RequestMapping(value = "/broadband-user/crm/invoice/view/{pageNo}/{customerId}")
	@ResponseBody
	public Map<String, Object> InvoicePage(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "customerId") int customerId) {
		
		Page<CustomerInvoice> invoicePage = new Page<CustomerInvoice>();
		invoicePage.setPageNo(pageNo);
		invoicePage.setPageSize(12);
		invoicePage.getParams().put("orderby", "order by create_date desc");
		invoicePage.getParams().put("customer_id", customerId);
		this.crmService.queryCustomerInvoicesByPage(invoicePage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("invoicePage", invoicePage);
		map.put("transactionsList", this.crmService.queryCustomerTransactionsByCustomerId(customerId));
		return map;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/save")
	@ResponseBody
	public CustomerOrder saveCustomerOrderEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("cvlan_input") String cvlan_input,
			@RequestParam("svlan_input") String svlan_input,
			@RequestParam("order_using_start_input") String order_using_start_input,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			@RequestParam("order_status") String order_status,
			@RequestParam("order_type") String order_type,
			HttpServletRequest req) {

		// new CustomerOrder to update 
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrder_status("using");
		customerOrder.setId(order_id); // important
		customerOrder.setOrder_type(order_type); // important too, for createInvoicPDF checking, if order_type!=null
		customerOrder.setSvlan(svlan_input);
		customerOrder.setCvlan(cvlan_input);
		customerOrder.setOrder_using_start(TMUtils.parseDateYYYYMMDD(order_using_start_input));
		customerOrder.getParams().put("id", order_id);
		
		// new ProvisionLog to insert
		ProvisionLog proLog = new ProvisionLog();
		User userSession = (User) req.getSession().getAttribute("userSession"); // get user from userSession
		proLog.setUser_id(userSession.getId());
		proLog.setOrder_id_customer(order_id);
		proLog.setOrder_sort("customer-order");
		proLog.setProcess_way(order_status + " to using");

		if (!"order-topup".equals(order_type)) {
			int nextInvoiceMonth = order_detail_unit;
			int nextInvoiceDay = -15;
			Calendar calNextInvoiceDay = Calendar.getInstance();
			calNextInvoiceDay.setTime(customerOrder.getOrder_using_start());
			calNextInvoiceDay.add(Calendar.MONTH, nextInvoiceMonth);
			calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
			// set next invoice date
			customerOrder.setNext_invoice_create_date(calNextInvoiceDay.getTime());
		}
		
		// check order status
		if ("ordering-paid".equals(order_status)) {

			this.crmService.editCustomerOrder(customerOrder, proLog);

			// send mailer
			Customer customer = this.crmService.queryCustomerById(customer_id);
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
		}

		if ("ordering-pending".equals(order_status)) {
			this.crmService.editCustomerOrder(customerOrder, proLog);
			Notification notificationEmail = this.systemService.queryNotificationBySort("register-post-pay", "email");
			Notification notificationSMS = this.systemService.queryNotificationBySort("register-post-pay", "sms");
			this.crmService.createInvoicePDF(customerOrder, notificationEmail, notificationSMS);
		}

		return customerOrder;
	}

	
	@RequestMapping(value = "/broadband-user/crm/customer/order/discount/save")
	public String doCustomerOrderDetailDiscountCreate(Model model
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
		
		attr.addFlashAttribute("success", "Create Customer Order Detail Discount is successful.");

		return "redirect:/broadband-user/crm/customer/edit/"+customer_id;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/pstn/edit")
	public String doCustomerOrderDetailPSTNEdit(Model model
			,@RequestParam("order_detail_id") int order_detail_id
			,@RequestParam("customer_id") int customer_id
			,@RequestParam("pstn_number") String pstn_number
			,RedirectAttributes attr) {
		
		CustomerOrderDetail cod = new CustomerOrderDetail();
		cod.getParams().put("id", order_detail_id);
		cod.setPstn_number(pstn_number);
		
		this.crmService.editCustomerOrderDetail(cod);
		
		attr.addFlashAttribute("success", "Update Customer Order Detail PSTN is successful.");

		return "redirect:/broadband-user/crm/customer/edit/"+customer_id;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/discount/remove")
	public String doCustomerOrderDetailDiscountRemove(Model model
			,@RequestParam("order_detail_id") int order_detail_id
			,@RequestParam("customer_id") int customer_id
			,RedirectAttributes attr) {
		
		this.crmService.removeCustomerOrderDetailById(order_detail_id);
		
		attr.addFlashAttribute("success", "Remove Customer Order Detail Discount is successful.");

		return "redirect:/broadband-user/crm/customer/edit/"+customer_id;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/edit")
	@ResponseBody
	public CustomerOrder toCustomerInvoiceEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("customer_id") int customer_id,
			@RequestParam("cvlan_input") String cvlan_input,
			@RequestParam("svlan_input") String svlan_input,
			@RequestParam("order_using_start_input") String order_using_start_input,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			@RequestParam("order_type") String order_type,
			HttpServletRequest req) {
		
		// get user from session
		User user = (User) req.getSession().getAttribute("userSession");
		
		// CustomerOrder begin
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setId(order_id);
		customerOrder.setSvlan(svlan_input);
		customerOrder.setCvlan(cvlan_input);
		customerOrder.setOrder_using_start(TMUtils.parseDateYYYYMMDD(order_using_start_input));
		customerOrder.getParams().put("id", order_id);
		
		if (!"order-topup".equals(order_type)) {
			int nextInvoiceMonth = order_detail_unit;
			int nextInvoiceDay = -15;
			Calendar calNextInvoiceDay = Calendar.getInstance();
			calNextInvoiceDay.setTime(customerOrder.getOrder_using_start());
			calNextInvoiceDay.add(Calendar.MONTH, nextInvoiceMonth);
			calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
			// set next invoice date
			customerOrder.setNext_invoice_create_date(calNextInvoiceDay.getTime());
		}
		
		// ProvisionLog begin
		ProvisionLog proLog = new ProvisionLog();
		proLog.setUser(user);
		//proLog.setOrder_id_customer(customerOrder);
		proLog.setOrder_sort("customer-order");
		proLog.setProcess_way("editing service giving");
		// ProvisionLog end
		
		this.crmService.editCustomerOrderAndCreateProvision(customerOrder, proLog);
		
		Customer customer = this.crmService.queryCustomerById(customer_id);
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
		
		return customerOrder;
	}

	// create invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/generate/{invoiceId}")
	@ResponseBody
	public void generateInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId){
		this.crmService.createInvoicePDFByInvoiceID(invoiceId);
	}
	
	// download invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/download/{invoiceId}")
    public ResponseEntity<byte[]> downloadInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId) throws IOException {
		String filePath = this.crmService.queryCustomerInvoiceFilePathById(invoiceId);
		
		// get file path
        Path path = Paths.get(filePath);
        byte[] contents = null;
        // transfer file contents to bytes
        contents = Files.readAllBytes( path );
        
        HttpHeaders headers = new HttpHeaders();
        // set spring framework media type
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        // get file name with file's suffix
        String filename = URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".pdf", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }
    
    /*
     * application mail controller begin
     */
    
	// send invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/send/{invoiceId}/{customerId}")
	public String sendInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId
    		,@PathVariable(value = "customerId") int customerId){
		String filePath = this.crmService.queryCustomerInvoiceFilePathById(invoiceId);
		Customer customer = this.crmService.queryCustomerById(customerId);
		Notification notification = this.systemService.queryNotificationBySort("invoice", "email");
		CustomerInvoice inv = new CustomerInvoice();
		inv.setId(invoiceId);
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		TMUtils.mailAtValueRetriever(notification, customer, inv, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(customer.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("Invoice-" + invoiceId + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		return "broadband-user/progress-accomplished";
	}

    /*
     * application mail controller end
     */
	
	/**
	 * BEGIN back-end create customer model
	 */
	
	@RequestMapping(value = "/broadband-user/crm/customer/{type}/create")
	public String toCustomerCreate(Model model,
			@PathVariable("type") String type) {
		
		model.addAttribute("customer", new Customer());
		
		String url = "";
		if ("personal".equals(type)) {
			url = "broadband-user/crm/customer-create-personal";
		} else if ("business".equals(type)) {
			url = "broadband-user/crm/customer-create-business";
		}
		return url;
	}

	@RequestMapping("/broadband-user/crm/customer/query/redirect")
	public String redirectPlanView(RedirectAttributes attr, SessionStatus status) {
		attr.addFlashAttribute("success", "Create Customer is successful.");
		status.setComplete();
		return "redirect:/broadband-user/crm/customer/query/1";
	}
	

	@RequestMapping(value = "/broadband-user/crm/customer/order/create")
	public String toCustomerOrderCreate(Model model,
			@ModelAttribute("customer") Customer customer,
			BindingResult result) {

		if (result.hasErrors()) {
			if ("personal".equals(customer.getType())) {
				return "broadband-user/crm/customer-create-personal";
			} else if ("business".equals(customer.getType())) {
				return "broadband-user/crm/customer-create-business";
			}
		}
		model.addAttribute("customerOrder", new CustomerOrder());
		
		Plan plan = new Plan();
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("orderby", "order by plan_type");
		List<Plan> plans = this.planService.queryPlans(plan);
		model.addAttribute("plans", plans);

		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		model.addAttribute("hardwares", hardwares);

		return "broadband-user/crm/order-create";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/create", method = RequestMethod.POST)
	public String doCustomerOrderCreate(Model model,
			@ModelAttribute("customer") Customer customer,
			@ModelAttribute("customerOrder") @Validated(CustomerOrderValidatedMark.class) CustomerOrder customerOrder, BindingResult co_result,
			RedirectAttributes attr, @RequestParam("action") String action, SessionStatus status) {
		
		if (co_result.hasErrors()) {
			return "broadband-user/crm/order-create";
		}
		
		return "redirect:/broadband-user/crm/customer/order/confirm";
	}
	
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/confirm")
	public String orderConfirm(Model model,
			@ModelAttribute("customer") Customer customer,
			@ModelAttribute("customerOrder") CustomerOrder customerOrder,
			@ModelAttribute("plans") List<Plan> plans, 
			@ModelAttribute("hardwares") List<Hardware> hardwares, 
			RedirectAttributes attr) {
		
		customer.setCustomerOrder(customerOrder);
		customerOrder.setOrder_create_date(new Date());
		
		if (plans != null) {
			for (Plan plan: plans) {
				if (plan.getId() == customerOrder.getPlan().getId()) {
					plan.setTopup(customerOrder.getPlan().getTopup());
					customerOrder.setPlan(plan);
					break;
				}
			}
		}
		
		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		
		cod_plan.setDetail_name(customerOrder.getPlan().getPlan_name());
		cod_plan.setDetail_desc(customerOrder.getPlan().getPlan_desc());
		cod_plan.setDetail_price(customerOrder.getPlan().getPlan_price() == null ? 0d : customerOrder.getPlan().getPlan_price());
		cod_plan.setDetail_data_flow(customerOrder.getPlan().getData_flow());
		cod_plan.setDetail_plan_status(customerOrder.getPlan().getPlan_status());
		cod_plan.setDetail_plan_type(customerOrder.getPlan().getPlan_type());
		cod_plan.setDetail_plan_sort(customerOrder.getPlan().getPlan_sort());
		cod_plan.setDetail_plan_group(customerOrder.getPlan().getPlan_group());
		cod_plan.setDetail_plan_class(customerOrder.getPlan().getPlan_class());
		cod_plan.setDetail_plan_new_connection_fee(customerOrder.getPlan().getPlan_new_connection_fee());
		cod_plan.setDetail_term_period(customerOrder.getPlan().getTerm_period());
		customerOrder.setTerm_period(customerOrder.getPlan().getTerm_period());
		cod_plan.setDetail_plan_memo(customerOrder.getPlan().getMemo());
		cod_plan.setDetail_unit(customerOrder.getPlan().getPlan_prepay_months() == null ? 1 : customerOrder.getPlan().getPlan_prepay_months());
		cod_plan.setDetail_type(customerOrder.getPlan().getPlan_group());
		
		customerOrder.getCustomerOrderDetails().add(0, cod_plan);
		
		if ("plan-topup".equals(customerOrder.getPlan().getPlan_group())) {
			
			cod_plan.setDetail_type("plan-topup");
			cod_plan.setDetail_is_next_pay(0);
			
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
				
				customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_new_connection_fee() + customerOrder.getPlan().getTopup().getTopup_fee());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Installation");
				cod_conn.setDetail_price(customerOrder.getPlan().getPlan_new_connection_fee());
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_conn);
				
			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {
				
				customerOrder.setOrder_total_price(customerOrder.getPlan().getTopup().getTopup_fee());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_trans);
			}
			
			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
			
			cod_topup.setDetail_name("Broadband Top-Up");
			cod_topup.setDetail_price(customerOrder.getPlan().getTopup().getTopup_fee());
			cod_topup.setDetail_type("topup");
			cod_topup.setDetail_is_next_pay(0);
			cod_topup.setDetail_unit(1);
			
			customerOrder.getCustomerOrderDetails().add(cod_topup);
			
		} else if ("plan-no-term".equals(customerOrder.getPlan().getPlan_group())) {
			
			cod_plan.setDetail_type("plan-no-term");
			cod_plan.setDetail_is_next_pay(1);
			
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
				
				customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_new_connection_fee() + customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
			
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Installation");
				cod_conn.setDetail_price(customerOrder.getPlan().getPlan_new_connection_fee());
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_conn);
				
			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {
				
				customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_trans);
			}
			
		} else if ("plan-term".equals(customerOrder.getPlan().getPlan_group())) {
			
			if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
				
				customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_new_connection_fee() + customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Installation");
				cod_conn.setDetail_price(customerOrder.getPlan().getPlan_new_connection_fee());
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_conn);

			} else if ("transition".equals(customerOrder.getOrder_broadband_type())) {
					
				customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_unit(1);
				
				customerOrder.getCustomerOrderDetails().add(cod_trans);
			}
			
			CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
			if ("personal".equals(customerOrder.getPlan().getPlan_class())) {
				cod_pstn.setDetail_name("Home Phone Line");
			} else if ("business".equals(customerOrder.getPlan().getPlan_class())) {
				cod_pstn.setDetail_name("Business Phone Line");
			}
			
			cod_pstn.setDetail_price(0d);
			cod_pstn.setDetail_is_next_pay(0);
			cod_pstn.setDetail_expired(new Date());
			cod_pstn.setDetail_type("pstn");
			cod_pstn.setDetail_unit(1);
			cod_pstn.setPstn_number(customer.getCustomerOrder().getTransition_porting_number());
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_pstn);
			
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			cod_hd.setDetail_name("Free Router");
			cod_hd.setDetail_price(0d);
			cod_hd.setDetail_is_next_pay(0);
			cod_hd.setDetail_expired(new Date());
			cod_hd.setDetail_unit(1);
			cod_hd.setIs_post(0);
			cod_hd.setDetail_type("hardware-router");
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
		}
		
		if (customerOrder.getCustomerOrderDetails() != null) {
			for (CustomerOrderDetail cod : customerOrder.getCustomerOrderDetails()) {
				if ("hardware-router".equals(cod.getDetail_type())) {
					cod.setDetail_is_next_pay(0);
					cod.setIs_post(0);
					customerOrder.setHardware_post(customerOrder.getHardware_post() == null ? 1 : customerOrder.getHardware_post() + 1);
					customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + cod.getDetail_price());
				} else if ("pstn".equals(cod.getDetail_type()) 
						|| "voip".equals(cod.getDetail_type())){
					cod.setDetail_unit(1);
					cod.setDetail_is_next_pay(1);
					customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + cod.getDetail_price());
				}
			}
		}
		
		return "broadband-user/crm/order-confirm";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/{type}/create/back")
	public String toBackCustomerCreate(Model model,
			@PathVariable("type") String type) {
		String url = "";
		if ("personal".equals(type)) {
			url = "broadband-user/crm/customer-create-personal";
		} else if ("business".equals(type)) {
			url = "broadband-user/crm/customer-create-business";
		}
		return url;
	}


	@RequestMapping(value = "/broadband-user/crm/customer/order/create/back")
	public String toBackOrderCreate(Model model,
			@ModelAttribute("customerOrder") CustomerOrder customerOrder) {
		customerOrder.setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customerOrder.setHardware_post(0);
		return "broadband-user/crm/order-create";
	}
	
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/confirm/save")
	public String orderSave(Model model,
			@ModelAttribute("customer") Customer customer, 
			@ModelAttribute("customerOrder") CustomerOrder customerOrder,
			RedirectAttributes attr, SessionStatus status) {
		
		customer.setUser_name(customer.getLogin_name());
		customerOrder.setOrder_status("pending");
		customerOrder.setOrder_type(customerOrder.getPlan().getPlan_group().replace("plan", "order"));
		
		this.crmService.saveCustomerOrder(customer, customerOrder);
		attr.addFlashAttribute("success", "Create Customer " + customer.getLogin_name() + " is successful.");
		status.setComplete();
		
		return "redirect:/broadband-user/crm/customer/query/1";
	}
	/**
	 * END back-end create customer model
	 */
	
	/**
	 * BEGIN PPPoE Controller
	 */
	@RequestMapping(value = "/broadband-user/crm/customer/order/ppppoe/save")
	@ResponseBody
	public CustomerOrder saveCustomerOrderPPPPoEEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("order_pppoe_loginname_input") String order_pppoe_loginname_input,
			@RequestParam("order_pppoe_password_input") String order_pppoe_password_input,
			HttpServletRequest req) {

		// customer order begin
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.getParams().put("id", order_id);
		customerOrder.setPppoe_loginname(order_pppoe_loginname_input);
		customerOrder.setPppoe_password(order_pppoe_password_input);

		this.crmService.editCustomerOrder(customerOrder);
		
		return customerOrder;
	}

	@RequestMapping(value = "/broadband-user/crm/customer/order/ppppoe/edit")
	@ResponseBody
	public CustomerOrder toCustomerPPPoEEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("order_pppoe_loginname_input") String order_pppoe_loginname_input,
			@RequestParam("order_pppoe_password_input") String order_pppoe_password_input,
			HttpServletRequest req) {
		
		// CustomerOrder begin
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.getParams().put("id", order_id);
		customerOrder.setPppoe_loginname(order_pppoe_loginname_input);
		customerOrder.setPppoe_password(order_pppoe_password_input);

		this.crmService.editCustomerOrder(customerOrder);
		
		return customerOrder;
	}
	/**
	 * END PPPoE Controller
	 */
	
	
	/**
	 * BEGIN Payment
	 */

	@RequestMapping(value = "/broadband-user/crm/customer/invoice/payment/credit-card/{invoice_id}")
	public String toInvoicePayment(Model model, HttpServletRequest req, RedirectAttributes attr
			,@PathVariable("invoice_id") Integer invoice_id) {

		GenerateRequest gr = new GenerateRequest();
		
		CustomerInvoice customerInvoice = this.crmService.queryCustomerInvoiceById(invoice_id);
		gr.setAmountInput(new DecimalFormat("#.00").format(customerInvoice.getBalance()));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");

		String path = req.getScheme()+"://"+(req.getLocalAddr().equals("127.0.0.1") ? "localhost" : req.getLocalAddr())+(req.getLocalPort()==80 ? "" : ":"+req.getLocalPort())+req.getContextPath();
		String wholePath = path+"/broadband-user/crm/customer/invoice/payment/credit-card/result/"+invoice_id;
		
		gr.setUrlFail(wholePath);
		gr.setUrlSuccess(wholePath);

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/payment/credit-card/result/{invoice_id}")
	public String toSignupPayment(Model model
			,@PathVariable("invoice_id") Integer invoice_id
			, RedirectAttributes attr
			,@RequestParam(value = "result", required = true) String result
			,SessionStatus status
			) throws Exception {
		
		Response responseBean = null;
		CustomerInvoice customerInvoice = this.crmService.queryCustomerInvoiceById(invoice_id);
		Customer customer = this.crmService.queryCustomerById(customerInvoice.getCustomer_id());

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {

			CustomerTransaction customerTransaction = new CustomerTransaction();
			customerTransaction.setAmount(Double.parseDouble(responseBean.getAmountSettlement()));
			customerTransaction.setAuth_code(responseBean.getAuthCode());
			customerTransaction.setCardholder_name(responseBean.getCardHolderName());
			customerTransaction.setCard_name(responseBean.getCardName());
			customerTransaction.setCard_number(responseBean.getCardNumber());
			customerTransaction.setClient_info(responseBean.getClientInfo());
			customerTransaction.setCurrency_input(responseBean.getCurrencyInput());
			customerTransaction.setAmount_settlement(Double.parseDouble(responseBean.getAmountSettlement()));
			customerTransaction.setExpiry_date(responseBean.getDateExpiry());
			customerTransaction.setDps_txn_ref(responseBean.getDpsTxnRef());
			customerTransaction.setMerchant_reference(responseBean.getMerchantReference());
			customerTransaction.setResponse_text(responseBean.getResponseText());
			customerTransaction.setSuccess(responseBean.getSuccess());
			customerTransaction.setTxnMac(responseBean.getTxnMac());
			customerTransaction.setTransaction_type(responseBean.getTxnType());
			customerTransaction.setTransaction_sort(this.crmService.queryCustomerOrderDetailGroupByOrderId(customerInvoice.getOrder_id()));
			customerTransaction.setCustomer_id(customer.getId());
			customerTransaction.setOrder_id(customerInvoice.getOrder_id());
			customerTransaction.setInvoice_id(customerInvoice.getId());
			customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
			this.crmService.createCustomerTransaction(customerTransaction);
			
			customerInvoice.setStatus("paid");
			customerInvoice.setAmount_paid(customerInvoice.getAmount_paid() + customerTransaction.getAmount());
			customerInvoice.setBalance(customerInvoice.getAmount_payable() - customerInvoice.getAmount_paid());
			customerInvoice.getParams().put("id", invoice_id);
			this.crmService.editCustomerInvoice(customerInvoice);
			
			this.crmService.createInvoicePDFByInvoiceID(invoice_id);
			
			Notification notification = this.crmService.queryNotificationBySort("payment", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			// call mail at value retriever
			TMUtils.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			// binding attachment name & path to email
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			// get sms register template from db
			notification = this.crmService.queryNotificationBySort("payment", "sms");
			TMUtils.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer, notification);
		} else {

		}

		attr.addFlashAttribute("success", "PAYMENT "+responseBean.getResponseText());

		return "redirect:/broadband-user/crm/customer/edit/"+customer.getId();
	}

	/**
	 * END Payment
	 */
	
	/**
	 * BEGIN order info
	 */
	@RequestMapping(value = "/broadband-user/crm/customer/order/info/edit")
	@ResponseBody
	public CustomerOrder doCustomerOrderInfoEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("order_status") String order_status,
			@RequestParam("due_date") String due_date,
			HttpServletRequest req) {

		CustomerOrder customerOrder = new CustomerOrder();
		// set order to current status
		customerOrder.getParams().put("id", order_id);
		customerOrder.setOrder_status(order_status);
		customerOrder.setOrder_due(TMUtils.parseDateYYYYMMDD(due_date));
		// get order to get previous status
		CustomerOrder customerOrderPrevious = this.crmService.queryCustomerOrder(customerOrder);
		
		// new ProvisionLog to insert
		ProvisionLog proLog = new ProvisionLog();
		User userSession = (User) req.getSession().getAttribute("userSession"); // get user from userSession
		proLog.setUser_id(userSession.getId());
		proLog.setOrder_id_customer(order_id);
		proLog.setOrder_sort("customer-order");
		// get both previous status and current status
		proLog.setProcess_way(customerOrderPrevious.getOrder_status() + " to " + order_status);


		this.crmService.editCustomerOrder(customerOrder, proLog);
		
		return customerOrder;
	}
	/**
	 * END order info
	 */

}

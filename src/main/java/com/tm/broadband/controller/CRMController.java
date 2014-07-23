package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerServiceRecord;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.Ticket;
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
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;

@Controller
@SessionAttributes({ "customer", "customerOrder", "hardwares", "plans" })//
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

	@RequestMapping("/broadband-user/crm/customer/view")
	public String toCustomerView(SessionStatus status) {
		status.setComplete();
		return "broadband-user/crm/customer-view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/edit/{id}")
	public String toCustomerEdit(Model model, @PathVariable(value = "id") int id,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Customer Edit");
		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		if(customer==null){
			attr.addFlashAttribute("error", "Customer Not Exist!");
			return "redirect:/broadband-user/crm/customer-service-record/view/1";
		}
		User user = new User();
		user.getParams().put("user_role", "sales");
		List<User> users = this.systemService.queryUser(user);
		model.addAttribute("customer", customer);
		model.addAttribute("users", users);
		return "broadband-user/crm/customer";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer-service-record/view/{pageNo}/{customer_id}")
	@ResponseBody
	public Map<String, Object> toCustomerServiceRecord(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("customer_id") int customer_id) {

		Page<CustomerServiceRecord> page = new Page<CustomerServiceRecord>();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		page.getParams().put("orderby", "order by create_date desc");
		page.getParams().put("customer_id", customer_id);
		this.crmService.queryCustomerServiceRecordsByPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", this.systemService.queryUser(new User()));
		map.put("page", page);
		return map;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/remove/{id}")
	public String customerRemove(@PathVariable(value = "id") int id, RedirectAttributes attr, SessionStatus status) {
		this.crmService.removeCustomer(id);
		attr.addFlashAttribute("success", "Remove customer is successful.");
		status.setComplete();
		return "redirect:/broadband-user/crm/customer/view";
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
		map.put("users", this.systemService.queryUser(new User()));
		return map;
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
	public String sendInvoicePDF(Model model,
			@PathVariable(value = "invoiceId") int invoiceId,
			@PathVariable(value = "customerId") int customerId) {
		String filePath = this.crmService.queryCustomerInvoiceFilePathById(invoiceId);
		Customer customer = this.crmService.queryCustomerById(customerId);
		Notification notification = this.systemService.queryNotificationBySort("invoice", "email");
		CustomerInvoice inv = new CustomerInvoice();
		inv.setId(invoiceId);
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		Organization org = this.crmService.queryOrganizationByCustomerId(customerId);
		customer.setOrganization(org);
		MailRetriever.mailAtValueRetriever(notification, customer, inv, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(customer.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("invoice_" + invoiceId + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		filePath = null;
		customer = null;
		notification = null;
		inv = null;
		company = null;
		org = null;
		applicationEmail = null;
		
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
		return "redirect:/broadband-user/crm/customer/view";
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
		//plan.getParams().put("plan_status", "active");
		plan.getParams().put("orderby", "order by plan_type");
		List<Plan> plans = this.planService.queryPlans(plan);
		model.addAttribute("plans", plans);

		Hardware hardware = new Hardware();
		//hardware.getParams().put("hardware_status", "active");
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
		
		List<CustomerOrderDetail> retains = new ArrayList<CustomerOrderDetail>();
		if (customerOrder.getCustomerOrderDetails() != null) {
			for (CustomerOrderDetail cod : customerOrder.getCustomerOrderDetails()) {
				if (("hardware-router".equals(cod.getDetail_type()) 
						|| "pstn".equals(cod.getDetail_type())
						|| "voip".equals(cod.getDetail_type()))
					&& !cod.getDetail_name().contains("Free")
					&& !cod.getDetail_name().equals("Home Phone Line")
					&& !cod.getDetail_name().equals("Business Phone Line")) {
					System.out.println(cod.getDetail_name() + ", " + cod.getDetail_type());
					retains.add(cod);
				}
			}
		}
		customerOrder.getCustomerOrderDetails().retainAll(retains);
		
		System.out.println("size: " + customerOrder.getCustomerOrderDetails().size());
		System.out.println("order_plan_id_before: " + customerOrder.getPlan().getId());
		
		if (plans != null) {
			for (Plan plan: plans) {
				if (plan.getId() == customerOrder.getPlan().getId()) {
					plan.setTopup(customerOrder.getPlan().getTopup());
					customerOrder.setPlan(plan);
					System.out.println("plan_id: " + plan.getId());
					System.out.println("plan_name: " + plan.getPlan_name());
					System.out.println("order_plan_id_after: " + customerOrder.getPlan().getId());
					System.out.println("order_plan_name: " + customerOrder.getPlan().getPlan_name());
					break;
				}
			}
		}
		
		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		
		
		cod_plan.setDetail_name(customerOrder.getPlan().getPlan_name());
		//cod_plan.setDetail_desc(customerOrder.getPlan().getPlan_desc());
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
			//cod_plan.setDetail_is_next_pay(0);
			
			customerOrder.setOrder_total_price(customerOrder.getPlan().getTopup().getTopup_fee());
			
			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
			cod_topup.setDetail_name("Broadband Top-Up");
			cod_topup.setDetail_price(customerOrder.getPlan().getTopup().getTopup_fee());
			cod_topup.setDetail_type("topup");
			//cod_topup.setDetail_is_next_pay(0);
			cod_topup.setDetail_unit(1);
			
			customerOrder.getCustomerOrderDetails().add(cod_topup);
			
		} else if ("plan-no-term".equals(customerOrder.getPlan().getPlan_group())) {
			
			customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
			
			cod_plan.setDetail_type("plan-no-term");
			//cod_plan.setDetail_is_next_pay(1);
			
		} else if ("plan-term".equals(customerOrder.getPlan().getPlan_group())) {
			
			customerOrder.setOrder_status("pending");
			
			customerOrder.setOrder_total_price(customerOrder.getPlan().getPlan_price() * customerOrder.getPlan().getPlan_prepay_months());
			
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			if ("ADSL".equals(customerOrder.getPlan().getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N ADSL2+ Modem Router(Free)");
			} else if ("VDSL".equals(customerOrder.getPlan().getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N VDSL2+ Modem Router(Free)");
			} else if ("UFB".equals(customerOrder.getPlan().getPlan_type())) {
				cod_hd.setDetail_name("UFB Modem Router(Free)");
			}
			cod_hd.setDetail_price(0d);
			//cod_hd.setDetail_is_next_pay(0);
			//cod_hd.setDetail_expired(new Date());
			cod_hd.setDetail_unit(1);
			cod_hd.setIs_post(0);
			cod_hd.setDetail_type("hardware-router");
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
		}
		
		// *********************************************************************
		
		if ("transition".equals(customerOrder.getOrder_broadband_type())) {
			
			customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + customerOrder.getPlan().getTransition_fee());
			
			CustomerOrderDetail cod_trans = new CustomerOrderDetail();
			cod_trans.setDetail_name("Broadband Transition");
			cod_trans.setDetail_price(customerOrder.getPlan().getTransition_fee());
			//cod_trans.setDetail_is_next_pay(0);
			cod_trans.setDetail_type("transition");
			cod_trans.setDetail_unit(1);
			
			customerOrder.getCustomerOrderDetails().add(cod_trans);
			
		} else if ("new-connection".equals(customerOrder.getOrder_broadband_type())) {
			
			customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + customerOrder.getPlan().getPlan_new_connection_fee());
			
			CustomerOrderDetail cod_conn = new CustomerOrderDetail();
			cod_conn.setDetail_name("Broadband New Connection");
			cod_conn.setDetail_price(customerOrder.getPlan().getPlan_new_connection_fee());
			//cod_conn.setDetail_is_next_pay(0);
			cod_conn.setDetail_type("new-connection");
			cod_conn.setDetail_unit(1);
			
			customerOrder.getCustomerOrderDetails().add(cod_conn);
			
		} else if ("jackpot".equals(customerOrder.getOrder_broadband_type())) {
			
			customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + customerOrder.getPlan().getJackpot_fee());
			
			CustomerOrderDetail cod_jackpot = new CustomerOrderDetail();
			cod_jackpot.setDetail_name("Broadband New Connection & Phone Jack Installation");
			cod_jackpot.setDetail_price(customerOrder.getPlan().getJackpot_fee());
			//cod_jackpot.setDetail_is_next_pay(0);
			cod_jackpot.setDetail_type("jackpot");
			cod_jackpot.setDetail_unit(1);
			
			customerOrder.getCustomerOrderDetails().add(cod_jackpot);
		}
		
		// add plan free pstn
		for (int i = 0; i < customerOrder.getPlan().getPstn_count(); i++) {
			CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
			if ("personal".equals(customerOrder.getPlan().getPlan_class())) {
				cod_pstn.setDetail_name("Home Phone Line");
			} else if ("business".equals(customerOrder.getPlan().getPlan_class())) {
				cod_pstn.setDetail_name("Business Phone Line");
			}
			cod_pstn.setDetail_price(0d);
			//cod_pstn.setDetail_is_next_pay(0);
			//cod_pstn.setDetail_expired(new Date());
			cod_pstn.setDetail_type("pstn");
			cod_pstn.setDetail_unit(1);
			cod_pstn.setPstn_number(customerOrder.getTransition_porting_number());
			customerOrder.getCustomerOrderDetails().add(cod_pstn);
		}
		
		if (customerOrder.getCustomerOrderDetails() != null) {
			for (CustomerOrderDetail cod : customerOrder.getCustomerOrderDetails()) {
				if ("hardware-router".equals(cod.getDetail_type())) {
					//cod.setDetail_is_next_pay(0);
					cod.setIs_post(0);
					customerOrder.setHardware_post(customerOrder.getHardware_post() == null ? 1 : customerOrder.getHardware_post() + 1);
					customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + cod.getDetail_price());
				} else if ("pstn".equals(cod.getDetail_type()) 
						|| "voip".equals(cod.getDetail_type())){
					cod.setDetail_unit(1);
					//cod.setDetail_is_next_pay(1);
					customerOrder.setOrder_total_price(customerOrder.getOrder_total_price() + cod.getDetail_price());
				}
			}
		}
		
		return "broadband-user/crm/order-confirm";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/{type}/create/back")
	public String toBackCustomerCreate(Model model, @PathVariable("type") String type) {
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
			RedirectAttributes attr, SessionStatus status,
			HttpServletRequest req) {
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customerOrder.setOrder_status("pending");
		customerOrder.setOrder_type(customerOrder.getPlan().getPlan_group().replace("plan", "order"));
		
		User user = (User) req.getSession().getAttribute("userSession");
		customerOrder.setUser_id(user.getId());
		
		this.crmService.registerCustomerCalling(customer);
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("online-ordering", "email");
		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("online-ordering", "sms"); // get sms register template from db
		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
		
		/*this.crmService.createInvoicePDFByInvoiceID(customer.getCustomerInvoice().getId(), false);

		String filePath = TMUtils.createPath(
				"broadband" 
				+ File.separator
				+ "customers" + File.separator + customer.getId()
				+ File.separator + "invoice_" + customer.getCustomerInvoice().getId() + ".pdf");
		
		Notification notification = this.crmService.queryNotificationBySort("register-post-pay", "email");
		ApplicationEmail applicationEmail = new ApplicationEmail();
		CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
		Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
		customer.setOrganization(org);
		// call mail at value retriever
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		// binding attachment name & path to email
		applicationEmail.setAttachName("invoice_" + customer.getCustomerInvoice().getId() + ".pdf");
		applicationEmail.setAttachPath(filePath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		// get sms register template from db
		notification = this.crmService.queryNotificationBySort("register-post-pay", "sms");
		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
		// send sms to customer's mobile phone
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());*/
		//status.setComplete();
		
		if ("personal".equals(customer.getCustomer_type())) {
			attr.addFlashAttribute("success", "Create Customer " + customer.getLast_name() + " " + customer.getFirst_name() + " is successful.");
		} else if ("business".equals(customer.getCustomer_type())){
			attr.addFlashAttribute("success", "Create Customer " + customer.getOrganization().getOrg_name() + " is successful.");
		}
		
		
		status.setComplete();
		
		user = null;
		notification = null;
		applicationEmail = null;
		companyDetail = null;
		/*org = null;*/
		
		return "redirect:/broadband-user/crm/customer/view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/checkout", method = RequestMethod.POST)
	public String orderCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
			@ModelAttribute("customer") Customer customer, 
			@ModelAttribute("customerOrder") CustomerOrder customerOrder
			) { // @Validated(CustomerValidatedMark.class)  , BindingResult result
		
		if ("business".equals(customer.getCustomer_type())) {
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() * 1.15);
		}
		
		Double orderTotalPrice = customer.getCustomerOrder().getOrder_total_price();
		
		String redirectUrl = "";
		
		customer.setBalance(orderTotalPrice);
		
		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(orderTotalPrice));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
		System.out.println(redirectUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/checkout")
	public String toSignupPayment(Model model,
			@ModelAttribute("customer") Customer customer, 
			@ModelAttribute("customerOrder") CustomerOrder customerOrder, 
			RedirectAttributes attr,
			@RequestParam(value = "result", required = true) String result,
			SessionStatus status
			) throws Exception {
		
		String url = "redirect:/order/result/error";

		Response responseBean = null;

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			url = "redirect:/broadband-user/crm/customer/view";
			
			customer.setStatus("active");
			customer.setUser_name(customer.getLogin_name());
			customer.setPassword(TMUtils.generateRandomString(6));
			customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
			//customer.setBalance(plan.getTopup().getTopup_fee() == null ? 0 : plan.getTopup().getTopup_fee());
			
			List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();

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
			customerTransaction.setTransaction_sort(customer.getCustomerOrder().getPlan().getPlan_group());
			
			cts.add(customerTransaction);
			
			customer.getCustomerOrder().setOrder_status("paid");
			this.crmService.registerCustomer(customer, cts);
			
			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
			
			/*this.crmService.createInvoicePDFByInvoiceID(customer.getCustomerInvoice().getId(), false);

			String filePath = TMUtils.createPath(
					"broadband" 
					+ File.separator
					+ "customers" + File.separator + customer.getId()
					+ File.separator + "invoice_" + customer.getCustomerInvoice().getId() + ".pdf");*/
			
			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			// call mail at value retriever
			MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			// binding attachment name & path to email
			applicationEmail.setAttachName("receipt_" + customer.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(receiptPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			// get sms register template from db
			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
			MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
			if ("personal".equals(customer.getCustomer_type())) {
				attr.addFlashAttribute("success", "Create Customer " + customer.getLast_name() + " " + customer.getFirst_name() + " is successful.");
			} else if ("business".equals(customer.getCustomer_type())){
				attr.addFlashAttribute("success", "Create Customer " + customer.getOrganization().getOrg_name() + " is successful.");
			}
			status.setComplete();
		} else {
			attr.addFlashAttribute("error", "PAYMENT "+responseBean.getResponseText());
		}
		
		attr.addFlashAttribute("responseBean", responseBean);

		return url; //"redirect:/order/result";
	}
	
	/**
	 * END back-end create customer model
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
			
			this.crmService.createInvoicePDFByInvoiceID(invoice_id, false);
			
			Notification notification = this.crmService.queryNotificationBySort("payment", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
			customer.setOrganization(org);
			// call mail at value retriever
			MailRetriever.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			// binding attachment name & path to email
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			// get sms register template from db
			notification = this.crmService.queryNotificationBySort("payment", "sms");
			MailRetriever.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
			attr.addFlashAttribute("success", "PAYMENT "+responseBean.getResponseText());
			
			notification = null;
			applicationEmail = null;
			companyDetail = null;
			org = null;
			
		} else {
			attr.addFlashAttribute("error", "PAYMENT "+responseBean.getResponseText());
		}
		
		responseBean = null;
		customerInvoice = null;
		Integer customerId = customer.getId();
		customer = null;
		
		return "redirect:/broadband-user/crm/customer/edit/"+customerId;
	}

	/**
	 * END Payment
	 */
	
	// BEGIN DDPay
	@RequestMapping(value = "/manual_defray/redirect/{customer_id}/{invoice_id}/{order_id}/{process_way}/{invoice_balance}")
	public String toDDPayRedirect(Model model, RedirectAttributes attr,
			@PathVariable("customer_id") Integer customer_id,
			@PathVariable("invoice_id") Integer invoice_id,
			@PathVariable("order_id") Integer order_id,
			@PathVariable("process_way") String process_way,
			@PathVariable("invoice_balance") Double invoice_balance) {
		if(invoice_balance <= 0d){
			attr.addFlashAttribute("error", "Manipulation of pay by " + process_way + " for Invoice# - " + invoice_id + " which is related to Order# - " + order_id + " has not successfully been processed, this invoice had been paid off already!!");
		} else {
			attr.addFlashAttribute("success", "Manipulation of pay by " + process_way + " for Invoice# - " + invoice_id + " which is related to Order# - " + order_id + " has successfully been processed!!");
		}
		return "redirect:/broadband-user/crm/customer/edit/"+customer_id;
	}
	// END DDPay
	
	// Upload Previous Provider's invoice PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/previous_provider_invoice/pdf/upload")
	public String uploadOrderForms(Model model
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("previous_provider_invoice_path") MultipartFile previous_provider_invoice_path
			, @RequestParam("application_form_path") MultipartFile application_form_path
			, @RequestParam("credit_card_form_path") MultipartFile credit_card_form_path
			, @RequestParam("ddpay_form_path") MultipartFile ddpay_form_path
			, HttpServletRequest req) {
		
		Boolean isFile = false;

		CustomerOrder co = new CustomerOrder();
		if(!previous_provider_invoice_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "previous_provider_invoice_" + order_id
					+ ".pdf");
			try {
				previous_provider_invoice_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			co.setPrevious_provider_invoice(order_path);
			isFile = true ;
		}
		if(!application_form_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "application_" + order_id
					+ ".pdf");
			try {
				application_form_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			co.setOrder_pdf_path(order_path);
			isFile = true ;
		}
		if(!credit_card_form_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "credit_card_" + order_id
					+ ".pdf");
			try {
				credit_card_form_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			co.setCredit_pdf_path(order_path);
			isFile = true ;
		}
		if(!ddpay_form_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "ddpay_" + order_id
					+ ".pdf");
			try {
				ddpay_form_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			co.setDdpay_pdf_path(order_path);
			isFile = true ;
		}
		if(isFile){
			co.getParams().put("id", order_id);
			this.crmService.editCustomerOrder(co);
		}
		
		return "redirect:/broadband-user/crm/customer/edit/" + customer_id;
	}
	
	// download previous provider's invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/order/previous_provider_invoice/pdf/download/{orderId}")
    public ResponseEntity<byte[]> downloadPreviousProviderInvoicePDF(Model model
    		,@PathVariable(value = "orderId") int orderId) throws IOException {
		String filePath = this.crmService.queryCustomerPreviousProviderInvoiceFilePathById(orderId);
		
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
	
	// download customer credit form PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/order/credit/pdf/download/{orderId}")
    public ResponseEntity<byte[]> downloadCustomerCreditFormPDF(Model model
    		,@PathVariable(value = "orderId") int orderId) throws IOException {
		String filePath = this.crmService.queryCustomerCreditFilePathById(orderId);
		
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
	
	// download customer ddpay form PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/order/ddpay/pdf/download/{orderId}")
    public ResponseEntity<byte[]> downloadDDPayFormPDF(Model model
    		,@PathVariable(value = "orderId") int orderId) throws IOException {
		String filePath = this.crmService.queryCustomerDDPayFormPathById(orderId);
		
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
	
	// download customer ordering form PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/order/ordering-form/pdf/download/{orderId}")
    public ResponseEntity<byte[]> downloadOrderingFormPDF(Model model
    		,@PathVariable(value = "orderId") int orderId) throws IOException {
		
    	String filePath = this.crmService.queryCustomerOrderingFormPathById(orderId);
		
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
	
	// download customer receipt PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/order/receipt/pdf/download/{orderId}")
    public ResponseEntity<byte[]> downloadReceiptPDF(Model model
    		,@PathVariable(value = "orderId") int orderId) throws IOException {
		String filePath = this.crmService.queryCustomerReceiptFormPathById(orderId);
		
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

	@RequestMapping("/broadband-user/crm/customer-service-record/view/{pageNo}")
	public String customerServiceRecordView(Model model, @PathVariable("pageNo") int pageNo) {
		
		Page<CustomerServiceRecord> page = new Page<CustomerServiceRecord>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by create_date desc");
		this.crmService.queryCustomerServiceRecordsByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("users", this.systemService.queryUser(new User()));
		return "broadband-user/crm/customer-service-record-view";
	}

	@RequestMapping("/broadband-user/crm/ticket/view/{pageNo}/{publish_type}")
	public String ticketView(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("publish_type") String publish_type
			, HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		Page<Ticket> page = new Page<Ticket>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by create_date desc");
		if("public".equals(publish_type)){
			page.getParams().put("where", "query_by_public");
			model.addAttribute("publicActive", "active");
		} else if("protected".equals(publish_type)){
			page.getParams().put("where", "query_by_protected");
			page.getParams().put("protected_viewer", user.getId());
			model.addAttribute("protectedActive", "active");
		}
		this.crmService.queryTicketsByPage(page);
		model.addAttribute("page", page);
		
		Page<Ticket> pageSum = new Page<Ticket>();
		pageSum.getParams().put("where", "query_by_public");
		model.addAttribute("publicSum", this.crmService.queryTicketsBySum(pageSum));
		pageSum = new Page<Ticket>();
		pageSum.getParams().put("where", "query_by_protected");
		pageSum.getParams().put("protected_viewer", user.getId());
		model.addAttribute("protectedSum", this.crmService.queryTicketsBySum(pageSum));
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		Ticket t = new Ticket();
		t.setExisting_customer(true);
		model.addAttribute("ticket", t);
		
		return "broadband-user/crm/ticket-view";
	}

}

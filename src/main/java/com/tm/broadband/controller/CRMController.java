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
import javax.servlet.http.HttpSession;

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

	@RequestMapping("/broadband-user/crm/customer/order/view")
	public String toCustomerOrderView(SessionStatus status,
			Model model) {
		status.setComplete();
		
		Page<CustomerOrder> coPageQuery = new Page<CustomerOrder>();
		model.addAttribute("allSum", this.crmService.queryCustomerOrdersSumByPage(coPageQuery));
		coPageQuery.getParams().put("customer_type", "personal");
		model.addAttribute("personalSum", this.crmService.queryCustomerOrdersSumByPage(coPageQuery));
		coPageQuery.getParams().put("customer_type", "business");
		model.addAttribute("businessSum", this.crmService.queryCustomerOrdersSumByPage(coPageQuery));
		
		return "broadband-user/crm/customer-order-view";
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
	
	@RequestMapping(value = "/broadband-user/crm/customer-ticket-record/view/{pageNo}/{customer_id}")
	@ResponseBody
	public Map<String, Object> toCustomerTicketRecord(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("customer_id") int customer_id) {

		Page<Ticket> page = new Page<Ticket>();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		page.getParams().put("orderby", "order by create_date desc");
		page.getParams().put("customer_id", customer_id);
		this.crmService.queryTicketsByPage(page);
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
		return "redirect:/broadband-user/crm/customer/order/view";
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
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/send/{invoiceId}/{orderId}")
	public String sendInvoicePDF(Model model,
			@PathVariable(value = "invoiceId") int invoiceId,
			@PathVariable(value = "orderId") int orderId) {
		String filePath = this.crmService.queryCustomerInvoiceFilePathById(invoiceId);
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", orderId);
		CustomerOrder co = this.crmService.queryCustomerOrder(coQuery);
		Notification notification = this.systemService.queryNotificationBySort("invoice", "email");
		CustomerInvoice inv = this.crmService.queryCustomerInvoiceById(invoiceId);
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		Customer cQuery = new Customer();
		cQuery.getParams().put("id", co.getCustomer_id());
		cQuery = this.crmService.queryCustomer(cQuery);
		
		MailRetriever.mailAtValueRetriever(notification, cQuery, co, inv, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(co.getEmail());
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
		
		coQuery = null;
		co = null;
		filePath = null;
		notification = null;
		inv = null;
		company = null;
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
		return "redirect:/broadband-user/crm/customer/order/view";
	}
	

//	@RequestMapping(value = "/broadband-user/crm/customer/order/create")
//	public String toCustomerOrderCreate(Model model,
//			@ModelAttribute("customer") Customer customer,
//			BindingResult result) {
//
//		if (result.hasErrors()) {
//			if ("personal".equals(customer.getCustomer_type())) {
//				return "broadband-user/crm/customer-create-personal";
//			} else if ("business".equals(customer.getType())) {
//				return "broadband-user/crm/customer-create-business";
//			}
//		}
//		model.addAttribute("customerOrder", new CustomerOrder());
//		
//		Plan plan = new Plan();
//		//plan.getParams().put("plan_status", "active");
//		plan.getParams().put("orderby", "order by plan_type");
//		plan.getParams().put("plan_class", customer.getCustomer_type());
//		List<Plan> plans = this.planService.queryPlans(plan);
//		model.addAttribute("plans", plans);
//
//		Hardware hardware = new Hardware();
//		//hardware.getParams().put("hardware_status", "active");
//		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
//		model.addAttribute("hardwares", hardwares);
//
//		return "broadband-user/crm/order-create";
//	}
	
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
			
//			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
//			cod_topup.setDetail_name("Broadband Top-Up");
//			cod_topup.setDetail_price(customerOrder.getPlan().getTopup().getTopup_fee());
//			cod_topup.setDetail_type("topup");
//			//cod_topup.setDetail_is_next_pay(0);
//			cod_topup.setDetail_unit(1);
//			
//			customerOrder.getCustomerOrderDetails().add(cod_topup);
			
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
	
//	@RequestMapping(value = "/broadband-user/crm/customer/order/confirm/save")
//	public String orderSave(Model model,
//			@ModelAttribute("customer") Customer customer, 
//			@ModelAttribute("customerOrder") CustomerOrder customerOrder,
//			RedirectAttributes attr, SessionStatus status,
//			HttpServletRequest req) {
//		
//		customer.setPassword(TMUtils.generateRandomString(6));
//		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
//		customer.setUser_name(customer.getLogin_name());
//		customerOrder.setOrder_status("pending");
//		customerOrder.setOrder_type(customerOrder.getPlan().getPlan_group().replace("plan", "order"));
//		
//		User user = (User) req.getSession().getAttribute("userSession");
//		customerOrder.setUser_id(user.getId());
//		
//		this.crmService.registerCustomerCalling(customer, customerOrder);
//		
//		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
//		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customerOrder.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
//		MailRetriever.mailAtValueRetriever(notification, customer, customerOrder, companyDetail); // call mail at value retriever
//		ApplicationEmail applicationEmail = new ApplicationEmail();
//		applicationEmail.setAddressee(customer.getEmail());
//		applicationEmail.setSubject(notification.getTitle());
//		applicationEmail.setContent(notification.getContent());
//		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
//		applicationEmail.setAttachPath(orderingPath);
//		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//		notification = this.systemService.queryNotificationBySort("personal".equals(customerOrder.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); // get sms register template from db
//		MailRetriever.mailAtValueRetriever(notification, customer, customerOrder, companyDetail);
//		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
//		
//		/*this.crmService.createInvoicePDFByInvoiceID(customer.getCustomerInvoice().getId(), false);
//
//		String filePath = TMUtils.createPath(
//				"broadband" 
//				+ File.separator
//				+ "customers" + File.separator + customer.getId()
//				+ File.separator + "invoice_" + customer.getCustomerInvoice().getId() + ".pdf");
//		
//		Notification notification = this.crmService.queryNotificationBySort("register-post-pay", "email");
//		ApplicationEmail applicationEmail = new ApplicationEmail();
//		CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
//		Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
//		customer.setOrganization(org);
//		// call mail at value retriever
//		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
//		applicationEmail.setAddressee(customer.getEmail());
//		applicationEmail.setSubject(notification.getTitle());
//		applicationEmail.setContent(notification.getContent());
//		// binding attachment name & path to email
//		applicationEmail.setAttachName("invoice_" + customer.getCustomerInvoice().getId() + ".pdf");
//		applicationEmail.setAttachPath(filePath);
//		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//		
//		// get sms register template from db
//		notification = this.crmService.queryNotificationBySort("register-post-pay", "sms");
//		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
//		// send sms to customer's mobile phone
//		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());*/
//		//status.setComplete();
//		
//		if ("personal".equals(customerOrder.getCustomer_type())) {
//			attr.addFlashAttribute("success", "Create Customer " + customer.getLast_name() + " " + customer.getFirst_name() + " is successful.");
//		} else if ("business".equals(customerOrder.getCustomer_type())){
//			attr.addFlashAttribute("success", "Create Customer " + customerOrder.getOrg_name() + " is successful.");
//		}
//		
//		
//		status.setComplete();
//		
//		user = null;
//		notification = null;
//		applicationEmail = null;
//		companyDetail = null;
//		/*org = null;*/
//		
//		return "redirect:/broadband-user/crm/customer/order/view";
//	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/checkout", method = RequestMethod.POST)
	public String orderCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
			@ModelAttribute("customer") Customer customer, 
			@ModelAttribute("customerOrder") CustomerOrder customerOrder
			) { // @Validated(CustomerValidatedMark.class)  , BindingResult result
		
		if ("business".equals(customerOrder.getCustomer_type())) {
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
	
//	@RequestMapping(value = "/broadband-user/crm/customer/order/checkout")
//	public String toSignupPayment(Model model,
//			@ModelAttribute("customer") Customer customer, 
//			@ModelAttribute("customerOrder") CustomerOrder customerOrder, 
//			RedirectAttributes attr,
//			@RequestParam(value = "result", required = true) String result,
//			SessionStatus status
//			) throws Exception {
//		
//		String url = "redirect:/order/result/error";
//
//		Response responseBean = null;
//
//		if (result != null)
//			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
//
//		if (responseBean != null && responseBean.getSuccess().equals("1")) {
//			
//			url = "redirect:/broadband-user/crm/customer/order/view";
//			
//			customer.setStatus("active");
//			customer.setUser_name(customer.getLogin_name());
//			customer.setPassword(TMUtils.generateRandomString(6));
//			customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
//			//customer.setBalance(plan.getTopup().getTopup_fee() == null ? 0 : plan.getTopup().getTopup_fee());
//			
//			List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();
//
//			CustomerTransaction customerTransaction = new CustomerTransaction();
//			customerTransaction.setAmount(Double.parseDouble(responseBean.getAmountSettlement()));
//			customerTransaction.setAuth_code(responseBean.getAuthCode());
//			customerTransaction.setCardholder_name(responseBean.getCardHolderName());
//			customerTransaction.setCard_name(responseBean.getCardName());
//			customerTransaction.setCard_number(responseBean.getCardNumber());
//			customerTransaction.setClient_info(responseBean.getClientInfo());
//			customerTransaction.setCurrency_input(responseBean.getCurrencyInput());
//			customerTransaction.setAmount_settlement(Double.parseDouble(responseBean.getAmountSettlement()));
//			customerTransaction.setExpiry_date(responseBean.getDateExpiry());
//			customerTransaction.setDps_txn_ref(responseBean.getDpsTxnRef());
//			customerTransaction.setMerchant_reference(responseBean.getMerchantReference());
//			customerTransaction.setResponse_text(responseBean.getResponseText());
//			customerTransaction.setSuccess(responseBean.getSuccess());
//			customerTransaction.setTxnMac(responseBean.getTxnMac());
//			customerTransaction.setTransaction_type(responseBean.getTxnType());
//			customerTransaction.setTransaction_sort(customer.getCustomerOrder().getPlan().getPlan_group());
//			
//			cts.add(customerTransaction);
//			
//			customer.getCustomerOrder().setOrder_status("paid");
//			this.crmService.registerCustomer(customer, cts);
//			
//			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
//			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
//			
//			/*this.crmService.createInvoicePDFByInvoiceID(customer.getCustomerInvoice().getId(), false);
//
//			String filePath = TMUtils.createPath(
//					"broadband" 
//					+ File.separator
//					+ "customers" + File.separator + customer.getId()
//					+ File.separator + "invoice_" + customer.getCustomerInvoice().getId() + ".pdf");*/
//			
//			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
//			ApplicationEmail applicationEmail = new ApplicationEmail();
//			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
//			// call mail at value retriever
//			MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
//			applicationEmail.setAddressee(customer.getEmail());
//			applicationEmail.setSubject(notification.getTitle());
//			applicationEmail.setContent(notification.getContent());
//			// binding attachment name & path to email
//			applicationEmail.setAttachName("receipt_" + customer.getCustomerOrder().getId() + ".pdf");
//			applicationEmail.setAttachPath(receiptPath);
//			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//			
//			// get sms register template from db
//			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
//			MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
//			// send sms to customer's mobile phone
//			this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
//			if ("personal".equals(customerOrder.getCustomer_type())) {
//				attr.addFlashAttribute("success", "Create Customer " + customer.getLast_name() + " " + customer.getFirst_name() + " is successful.");
//			} else if ("business".equals(customerOrder.getCustomer_type())){
//				attr.addFlashAttribute("success", "Create Customer " + customer.getOrganization().getOrg_name() + " is successful.");
//			}
//			status.setComplete();
//		} else {
//			attr.addFlashAttribute("error", "PAYMENT "+responseBean.getResponseText());
//		}
//		
//		attr.addFlashAttribute("responseBean", responseBean);
//
//		return url; //"redirect:/order/result";
//	}
	
	/**
	 * END back-end create customer model
	 */
	
	/**
	 * BEGIN Payment
	 */
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/payment/credit-card/{id}/{redirect_from}/{invoice_status}")
	public String toInvoicePayment(Model model, HttpServletRequest req, RedirectAttributes attr
			,@PathVariable("id") Integer id
			,@PathVariable("redirect_from") String redirect_from
			,@PathVariable("invoice_status") String invoice_status) {

		GenerateRequest gr = new GenerateRequest();
		
		if("crm_invoice".equals(redirect_from) || "agent_invoice".equals(redirect_from)){
			CustomerInvoice customerInvoice = this.crmService.queryCustomerInvoiceById(id);
			gr.setAmountInput(new DecimalFormat("#.00").format(customerInvoice.getBalance()));
		} else if("agent_ordering".equals(redirect_from)) {
			CustomerOrder coQuery = new CustomerOrder();
			coQuery.getParams().put("id", id);
			coQuery = this.crmService.queryCustomerOrder(coQuery);
			gr.setAmountInput(new DecimalFormat("#.00").format(coQuery.getOrder_total_price()));
		}
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");

//		String path = req.getScheme()+"://"+(req.getLocalAddr().equals("127.0.0.1") ? "localhost" : req.getLocalAddr())+(req.getLocalPort()==80 ? "" : ":"+req.getLocalPort())+req.getContextPath();
//		String wholePath = path+"/broadband-user/crm/customer/invoice/payment/credit-card/result/"+invoice_id;
		
		String url = req.getRequestURL().toString();
		// cut /id/redirect_form/invoice_status off
		String finalUrl = url.substring(0, url.lastIndexOf("/"));
		finalUrl = finalUrl.substring(0, finalUrl.lastIndexOf("/"));
		finalUrl = finalUrl.substring(0, finalUrl.lastIndexOf("/")+1);

		gr.setUrlFail(finalUrl+"result/"+id+"/"+redirect_from+"/"+invoice_status);
		gr.setUrlSuccess(finalUrl+"result/"+id+"/"+redirect_from+"/"+invoice_status);
//		gr.setUrlFail(wholePath);
//		gr.setUrlSuccess(wholePath);

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
//	@RequestMapping(value = "/broadband-user/crm/customer/invoice/payment/credit-card/result/{id}/{redirect_from}/{invoice_status}")
//	public String toSignupPayment(Model model
//			,@PathVariable("id") Integer id
//			,@PathVariable("redirect_from") String redirect_from
//			,@PathVariable("invoice_status") String invoice_status
//			, RedirectAttributes attr
//			,@RequestParam(value = "result", required = true) String result
//			,SessionStatus status
//			,HttpServletRequest req
//			) throws Exception {
//		
//		Response responseBean = null;
//		CustomerInvoice customerInvoice = null;
//		CustomerOrder customerOrder = null;
//		Integer customer_id = 0;
//		Integer order_id = 0;
//		String txSort = "";
//		
//		if("crm_invoice".equals(redirect_from) || "agent_invoice".equals(redirect_from)){
//			customerInvoice = this.crmService.queryCustomerInvoiceById(id);
//			customer_id = customerInvoice.getCustomer_id();
//			order_id = customerInvoice.getOrder_id();
//			txSort = this.crmService.queryCustomerOrderDetailGroupByOrderId(customerInvoice.getOrder_id());
//		} else if("agent_ordering".equals(redirect_from)) {
//			customerOrder = this.crmService.queryCustomerOrderById(id);
//			customer_id = customerOrder.getCustomer_id();
//			order_id = customerOrder.getId();
//			txSort = this.crmService.queryCustomerOrderDetailGroupByOrderId(customerOrder.getId());
//		}
//		
//		Customer cQuery = new Customer();
//		cQuery.getParams().put("id", customer_id);
//		Customer customer = this.crmService.queryCustomer(cQuery);
//
//		if (result != null)
//			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
//
//		if (responseBean != null && responseBean.getSuccess().equals("1")) {
//
//			CustomerTransaction customerTransaction = new CustomerTransaction();
//			customerTransaction.setAmount(Double.parseDouble(responseBean.getAmountSettlement()));
//			customerTransaction.setAuth_code(responseBean.getAuthCode());
//			customerTransaction.setCardholder_name(responseBean.getCardHolderName());
//			customerTransaction.setCard_name(responseBean.getCardName());
//			customerTransaction.setCard_number(responseBean.getCardNumber());
//			customerTransaction.setClient_info(responseBean.getClientInfo());
//			customerTransaction.setCurrency_input(responseBean.getCurrencyInput());
//			customerTransaction.setAmount_settlement(Double.parseDouble(responseBean.getAmountSettlement()));
//			customerTransaction.setExpiry_date(responseBean.getDateExpiry());
//			customerTransaction.setDps_txn_ref(responseBean.getDpsTxnRef());
//			customerTransaction.setMerchant_reference(responseBean.getMerchantReference());
//			customerTransaction.setResponse_text(responseBean.getResponseText());
//			customerTransaction.setSuccess(responseBean.getSuccess());
//			customerTransaction.setTxnMac(responseBean.getTxnMac());
//			customerTransaction.setTransaction_type(responseBean.getTxnType());
//			customerTransaction.setCustomer_id(customer.getId());
//			customerTransaction.setTransaction_sort(txSort);
//			customerTransaction.setOrder_id(order_id);
//			customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
//			
//			if("crm_invoice".equals(redirect_from) || "agent_invoice".equals(redirect_from)){
//				
//				customerTransaction.setInvoice_id(customerInvoice.getId());
//				
//				customerInvoice.setStatus("paid");
//				customerInvoice.setAmount_paid(customerInvoice.getAmount_paid() + customerTransaction.getAmount());
//				customerInvoice.setBalance(customerInvoice.getAmount_payable() - customerInvoice.getAmount_paid());
//				customerInvoice.getParams().put("id", id);
//				this.crmService.editCustomerInvoice(customerInvoice);
//				
//				this.crmService.createInvoicePDFByInvoiceID(id, false);
//				
//				Notification notification = this.crmService.queryNotificationBySort("payment", "email");
//				ApplicationEmail applicationEmail = new ApplicationEmail();
//				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
//				Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
//				customer.setOrganization(org);
//				
//				// call mail at value retriever
//				MailRetriever.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
//				applicationEmail.setAddressee(customer.getEmail());
//				applicationEmail.setSubject(notification.getTitle());
//				applicationEmail.setContent(notification.getContent());
//				// binding attachment name & path to email
//				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//				
//				// get sms register template from db
//				notification = this.crmService.queryNotificationBySort("payment", "sms");
//				MailRetriever.mailAtValueRetriever(notification, customer, customerInvoice, companyDetail);
//				// send sms to customer's mobile phone
//				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
//				
//				notification = null;
//				applicationEmail = null;
//				companyDetail = null;
//				org = null;
//				
//			} else if("agent_ordering".equals(redirect_from)) {
//				
//				customer.setBalance(TMUtils.bigAdd(customer.getBalance()==null ? 0d : customer.getBalance(), customerTransaction.getAmount()));
//				customer.getParams().put("id", customer.getId());
//				this.crmService.editCustomer(customer);
//				
//				customer.setCustomerOrder(customerOrder);
//				
//				CustomerOrder co = new CustomerOrder();
//				Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
//				customer.setOrganization(org);
//				String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
//				co.setReceipt_pdf_path(receiptPath);
//				if("pending".equals(customer.getCustomerOrder().getOrder_status())
//				  || "pending-warning".equals(customer.getCustomerOrder().getOrder_status())
//				  || "void".equals(customer.getCustomerOrder().getOrder_status())){
//					co.setOrder_status("paid");
//				} else if("ordering-pending".equals(customer.getCustomerOrder().getOrder_status())) {
//					co.setOrder_status("ordering-paid");
//				}
//				
//				User user = (User) req.getSession().getAttribute("userSession");
//				
//				ProvisionLog pl = new ProvisionLog();
//				pl.setUser_id(user.getId());
//				pl.setProcess_datetime(new Date());
//				pl.setOrder_sort("customer-order");
//				pl.setOrder_id_customer(id);
//				pl.setProcess_way(customer.getCustomerOrder().getOrder_status()+" to "+co.getOrder_status()+" receipt");
//				co.getParams().put("id", id);
//				this.crmService.editCustomerOrder(co, pl);
//				
//			}
//			
//			this.crmService.createCustomerTransaction(customerTransaction);
//			attr.addFlashAttribute("success", "PAYMENT "+responseBean.getResponseText());
//			
//		} else {
//			attr.addFlashAttribute("error", "PAYMENT "+responseBean.getResponseText());
//		}
//		
//		responseBean = null;
//		customerInvoice = null;
//		Integer customerId = customer.getId();
//		customer = null;
//		
//		String url = "";
//		
//		if("crm_invoice".equals(redirect_from)){
//			url = "redirect:/broadband-user/crm/customer/edit/"+customerId;
//		} else if("agent_invoice".equals(redirect_from)){
//			url = "redirect:/broadband-user/agent/billing/invoice/view/1/"+invoice_status;
//		} else if("agent_ordering".equals(redirect_from)){
//			url = "redirect:/broadband-user/sale/online/ordering/view/1/0";
//		}
//		
//		return url;
//	}
	
	
	

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
	
	@RequestMapping(value = "/broadband-user/crm/ticket/view")
	public String toTicketView(Model model,
			HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		Page<Ticket> pageSum = new Page<Ticket>();
		pageSum.getParams().put("where", "query_by_public_protected");
		pageSum.getParams().put("public_protected", "public_protected");
		pageSum.getParams().put("protected_viewer", user.getId());
		pageSum.getParams().put("double_not_yet_viewer", user.getId());
		pageSum.getParams().put("existing_customer", true);
		req.getSession().setAttribute("existingSum", this.crmService.queryTicketsBySum(pageSum));
		pageSum.getParams().put("existing_customer", false);
		req.getSession().setAttribute("newSum", this.crmService.queryTicketsBySum(pageSum));
		model.addAttribute("users", this.systemService.queryUser(new User()));
		return "broadband-user/crm/ticket-view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/ticket/edit/{id}")
	public String toTicketEdit(Model model
			, @PathVariable(value = "id") int id,
			RedirectAttributes attr,
			HttpServletRequest req) {
		
		model.addAttribute("panelheading", "Ticket Edit");
		Ticket tQuery = new Ticket();
		tQuery.getParams().put("id", id);		
		Ticket ticket = this.crmService.queryTicket(tQuery).get(0);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		// If Not Yet View Ticket contains current user's id then rub off
		if(ticket.getNot_yet_viewer()!=null
		   && ticket.getNot_yet_viewer().contains(user.getId().toString())){
			String[] userIds = ticket.getNot_yet_viewer().split(",");
			List<String> finalUserIds = new ArrayList<String>();
			for (int i = 0; i < userIds.length; i++) {
				if(!userIds[i].equals(user.getId().toString())){
					finalUserIds.add(userIds[i]);
				}
			}
			StringBuffer userIdsBuff = new StringBuffer();
			for (int i = 0; i < finalUserIds.size(); i++) {
				userIdsBuff.append(finalUserIds.get(i));
				if(i < finalUserIds.size()-1){
					userIdsBuff.append(",");
				}
			}
			if(ticket.getViewed_viewer()!=null && !"".equals(ticket.getViewed_viewer().trim())){
				ticket.setViewed_viewer(ticket.getViewed_viewer()+","+user.getId().toString());
			} else {
				ticket.setViewed_viewer(user.getId().toString());
			}
			ticket.setNot_yet_viewer(userIdsBuff.toString());
			ticket.getParams().put("id", id);
			this.crmService.editTicket(ticket);
		} else {
			// If Not Yet View Ticket Comments contains current user's id then rub off
			if(ticket.getNot_yet_review_comment_viewer()!=null
			   && ticket.getNot_yet_review_comment_viewer().contains(user.getId().toString())){
				String[] userIds = ticket.getNot_yet_review_comment_viewer().split(",");
				List<String> finalUserIds = new ArrayList<String>();
				for (int i = 0; i < userIds.length; i++) {
					if(!userIds[i].equals(user.getId().toString())){
						finalUserIds.add(userIds[i]);
					}
				}
				StringBuffer userIdsBuff = new StringBuffer();
				for (int i = 0; i < finalUserIds.size(); i++) {
					userIdsBuff.append(finalUserIds.get(i));
					if(i < finalUserIds.size()-1){
						userIdsBuff.append(",");
					}
				}
				ticket.setNot_yet_review_comment_viewer(userIdsBuff.toString());
				ticket.getParams().put("id", id);
				this.crmService.editTicket(ticket);
			}
		}
		
		
		List<User> users = this.systemService.queryUser();
		model.addAttribute("ticket", ticket);
		model.addAttribute("users", users);
		
		return "broadband-user/crm/ticket";
	}

	@RequestMapping(value="/broadband-user/crm/ticket/remove", method=RequestMethod.POST)
	public String doTicketRemove(Model model,
			@RequestParam("id") Integer id,
			RedirectAttributes attr){
		
		this.crmService.removeTicketById(id);
		this.crmService.removeTicketCommentByTicketId(id);
		
		attr.addFlashAttribute("success", "Successfully removed specific ticket!");
		
		return "redirect:/broadband-user/crm/ticket/view";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/broadband-user/crm/plans")
	public String crmPlans(HttpSession session){
		session.removeAttribute("customerRegAdmin");
		return "broadband-user/crm/plans/plans";
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/{customerid}")
	public String crmPlansNewOrder(Model model
			, HttpSession session
			, @PathVariable("customerid") int customerid){
		session.removeAttribute("customerRegAdmin");
		Customer cQ = new Customer();
		cQ.getParams().put("id", customerid);
		Customer customerBackSession = this.crmService.queryCustomer(cQ);
		session.setAttribute("customerBackSession", customerBackSession);
		model.addAttribute("neworder", "/new-order");
		return "broadband-user/crm/plans/plans";
	}
	
	// new order topup
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/topup")
	public String customerNewOrderTopup(HttpSession session){
		System.out.println("this is new order customerRegAdmin, Topup");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setId(customerBackSession.getId());
		customerRegAdmin.setCellphone(customerBackSession.getCellphone());
		customerRegAdmin.setEmail(customerBackSession.getEmail());
		customerRegAdmin.setNewOrder(true);
		customerRegAdmin.setSelect_plan_group("plan-topup");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
	
	// new order personal
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/personal")
	public String customerNewOrderPersonal(HttpSession session){
		System.out.println("this is new order customerRegAdmin, Personal");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setId(customerBackSession.getId());
		customerRegAdmin.setCellphone(customerBackSession.getCellphone());
		customerRegAdmin.setEmail(customerBackSession.getEmail());
		customerRegAdmin.setNewOrder(true);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
		
	// new order business
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/business")
	public String customerNewOrderBusiness(HttpSession session){
		System.out.println("this is new order customerRegAdmin, Business");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setId(customerBackSession.getId());
		customerRegAdmin.setCellphone(customerBackSession.getCellphone());
		customerRegAdmin.setEmail(customerBackSession.getEmail());
		customerRegAdmin.setNewOrder(true);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("business");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
	
	// new order promotion ipadmini
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/promotion/ipadmini")
	public String customerNewOrderPromotioniPadMini(HttpSession session){
		System.out.println("this is new order customerRegAdmin, promotion ipadmini");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setId(customerBackSession.getId());
		customerRegAdmin.setCellphone(customerBackSession.getCellphone());
		customerRegAdmin.setEmail(customerBackSession.getEmail());
		customerRegAdmin.setNewOrder(true);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setSale_id(10023);
		customerRegAdmin.getCustomerOrder().setPromotion(true);
		customerRegAdmin.setLanguage("en");
		session.setAttribute("customerBackSession", customerBackSession);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
		
	// new order promotion hd
	@RequestMapping(value = "/broadband-user/crm/plans/new-order/promotion/hd")
	public String customerNewOrderPromotionHD(HttpSession session){
		System.out.println("this is new order customerRegAdmin, promotion hd");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setId(customerBackSession.getId());
		customerRegAdmin.setCellphone(customerBackSession.getCellphone());
		customerRegAdmin.setEmail(customerBackSession.getEmail());
		customerRegAdmin.setNewOrder(true);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setSale_id(20023);
		customerRegAdmin.getCustomerOrder().setPromotion(true);
		customerRegAdmin.setLanguage("en");
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
	
	// topup
	@RequestMapping(value = "/broadband-user/crm/plans/topup")
	public String crmPlansTopup(HttpSession session){
		System.out.println("this is new customerRegAdmin, Topup");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setNewOrder(false);
		customerRegAdmin.setSelect_plan_group("plan-topup");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
	
	// personal
	@RequestMapping(value = "/broadband-user/crm/plans/personal")
	public String crmPlansPersonal(HttpSession session){
		System.out.println("this is new customerRegAdmin, Personal");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setNewOrder(false);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
		
	// business
	@RequestMapping(value = "/broadband-user/crm/plans/business")
	public String crmPlansBusiness(HttpSession session){
		System.out.println("this is new customerRegAdmin, Business");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setNewOrder(false);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("business");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
		
	// promotion ipadmini
	@RequestMapping(value = "/broadband-user/crm/plans/promotion/ipadmini")
	public String crmPlansPromotioniPadMini(HttpSession session){
		System.out.println("this is new customerRegAdmin, promotion ipadmini");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setNewOrder(false);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setSale_id(10023);
		customerRegAdmin.getCustomerOrder().setPromotion(true);
		customerRegAdmin.setLanguage("en");
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
		
	// promotion hd
	@RequestMapping(value = "/broadband-user/crm/plans/promotion/hd")
	public String crmPlansPromotionHD(HttpSession session){
		System.out.println("this is new customerRegAdmin, promotion hd");
		Customer customerRegAdmin = new Customer();
		customerRegAdmin.setNewOrder(false);
		customerRegAdmin.setSelect_plan_group("");
		customerRegAdmin.setSelect_customer_type("personal");
		customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegAdmin.getCustomerOrder().setSale_id(20023);
		customerRegAdmin.getCustomerOrder().setPromotion(true);
		customerRegAdmin.setLanguage("en");
		session.setAttribute("customerRegAdmin", customerRegAdmin);
		return "redirect:/broadband-user/crm/plans/address-check";
	}
	
	// ***
	
	@RequestMapping("/broadband-user/crm/plans/address-check") 
	public String toAddressCheck(Model model, HttpSession session) {
		return "broadband-user/crm/plans/address-check";
	}
	
	// ***
	
	@RequestMapping("/broadband-user/crm/plans/define/{type}")
	public String plansDefineType(HttpSession session, @PathVariable("type") String type) {
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		customerRegAdmin.setSelect_plan_type(type);
		return "redirect:/broadband-user/crm/plans/order";
	}
	
	// clear address
	
	@RequestMapping("/broadband-user/crm/plans/address/clear") 
	public String addressClear(HttpSession session) {
		session.removeAttribute("customerRegAdmin");
		return "redirect:/broadband-user/crm/plans";
	}	
	
	// order

	@RequestMapping("/broadband-user/crm/plans/order") 
	public String toOrderPlan(Model model, HttpSession session) {
		
		String url = "broadband-user/crm/plans/customer-order";
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		if (!customerRegAdmin.isServiceAvailable()) {
			System.out.println("customerRegAdmin.isServiceAvailable(): " + customerRegAdmin.isServiceAvailable());
			url = "redirect:/broadband-user/crm/plans";
		}
		return url;
	}
	
	@RequestMapping("/broadband-user/crm/plans/order/summary") 
	public String plansOrderSummary(Model model, HttpSession session) {
		return "broadband-user/crm/plans/order-summary";
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/bankdeposit", method = RequestMethod.POST)
	public String plansOrderBankDeposit(RedirectAttributes attr, HttpSession session) {
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession"); System.out.println("customerBackSession: " + customerBackSession);
		
		String send_email = "", send_mobile = "";
		
		if (customerRegAdmin.getNewOrder()) {
			customerRegAdmin.setPassword("*********");
			customerRegAdmin.setMd5_password(DigestUtils.md5Hex(customerRegAdmin.getPassword()));
			customerRegAdmin.setStatus("active");
			customerRegAdmin.getCustomerOrder().setOrder_status("pending");
			customerRegAdmin.setBalance(0d);
			if (customerBackSession != null) {
				send_email = customerRegAdmin.getEmail();
				send_mobile = customerRegAdmin.getCellphone();
				customerRegAdmin.setEmail(customerBackSession.getEmail());
				customerRegAdmin.setCellphone(customerBackSession.getCellphone());
			}
		} else {
			customerRegAdmin.setPassword(TMUtils.generateRandomString(6));
			customerRegAdmin.setMd5_password(DigestUtils.md5Hex(customerRegAdmin.getPassword()));
			customerRegAdmin.setUser_name(customerRegAdmin.getLogin_name());
			customerRegAdmin.setStatus("active");
			customerRegAdmin.getCustomerOrder().setOrder_status("pending");
			customerRegAdmin.setBalance(0d);
			send_email = customerRegAdmin.getEmail();
			send_mobile = customerRegAdmin.getCellphone();
		}
		
		User user = (User) session.getAttribute("userSession");
		customerRegAdmin.getCustomerOrder().setUser_id(user.getId());
		customerRegAdmin.setCompany_name(customerRegAdmin.getCustomerOrder().getOrg_name());
		this.crmService.saveCustomerOrder(customerRegAdmin, customerRegAdmin.getCustomerOrder(), null);
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customerRegAdmin);
		
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customerRegAdmin.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
		MailRetriever.mailAtValueRetriever(notification, customerRegAdmin, customerRegAdmin.getCustomerOrder(), companyDetail); 
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(send_email);
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customerRegAdmin.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("personal".equals(customerRegAdmin.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); 
		MailRetriever.mailAtValueRetriever(notification, customerRegAdmin, customerRegAdmin.getCustomerOrder(), companyDetail);
		this.smserService.sendSMSByAsynchronousMode(send_mobile, notification.getContent()); 
		
		Response responseBean = new Response();
		responseBean.setSuccess("1");
		attr.addFlashAttribute("responseBean", responseBean);
		
		return "redirect:/broadband-user/crm/plans/order/result";
	}
	
	

	
	@RequestMapping(value = "/broadband-user/crm/plans/order/result")
	public String planOrderRtoOrderResult(HttpSession session) {
		session.removeAttribute("customerRegAdmin");
		return "broadband-user/crm/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/result/success")
	public String planOrderResultSuccess(HttpSession session) {
		session.removeAttribute("customerRegAdmin");
		return "broadband-user/crm/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/result/error")
	public String planOrderResultError(HttpSession session) {
		return "broadband-user/crm/plans/customer-order-result-error";
	}

}

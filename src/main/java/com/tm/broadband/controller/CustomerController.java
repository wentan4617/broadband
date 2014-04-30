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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
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
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes(value = { "customer", "orderPlan"})
public class CustomerController {

	private PlanService planService;
	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;

	@Autowired
	public CustomerController(PlanService planService, CRMService crmService
			,MailerService mailerService
			,SystemService systemService,
			SmserService smserService) {
		this.planService = planService;
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.smserService = smserService;
	}

	@RequestMapping("/home")
	public String home(Model model) {
		return "broadband-customer/home";
	}

	@RequestMapping("/plans/{group}/{class}")
	public String plans(Model model, 
			@PathVariable("group") String group,
			@PathVariable("class") String classz) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
		model.addAttribute("customer", customer);
		
		List<Plan> plans = null;
		Map<String, List<Plan>> planMaps = new HashMap<String, List<Plan>>(); // key = plan_type
		String url = "";
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", group);
		plan.getParams().put("plan_class", classz);
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("orderby", "order by data_flow");
		
		plans = this.planService.queryPlans(plan);
		
		if (plans != null) {
			for (Plan p: plans) {
				List<Plan> list = planMaps.get(p.getPlan_type());
				if (list == null) {
					list = new ArrayList<Plan>();
					list.add(p);
					planMaps.put(p.getPlan_type(), list);
				} else {
					list.add(p);
				}
			}
		}
		
		model.addAttribute("planMaps", planMaps);
		
		if ("plan-topup".equals(group)) {	
			url = "broadband-customer/plan-detail-topup";
		} else if ("plan-no-term".equals(group) && "personal".equals(classz)) {
			url = "broadband-customer/plan-detail-no-term";
		} else if ("plan-term".equals(group) && "personal".equals(classz)) {
			url = "broadband-customer/plan-detail-term-personal";
		} else if ("plan-term".equals(group) && "business".equals(classz)) {
			url = "broadband-customer/plan-detail-term-business";
		}

		return url;
	}

	@RequestMapping("/order/{id}")
	public String orderPlanNoTerm(Model model, 
			@PathVariable("id") int id,
			HttpServletRequest req) {

		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		model.addAttribute("hardwares", hardwares);
		
		String url = "";
		if ("personal".equals(plan.getPlan_class())) {
			url = "broadband-customer/customer-order-personal";
		} else if ("business".equals(plan.getPlan_class())) {
			url = "broadband-customer/customer-order-business";
		}
		
		return url;
	}
	
	@RequestMapping("/order/{id}/topup/{amount}")
	public String orderPlanTopup(Model model, 
			@PathVariable("id") int id,
			@PathVariable("amount") Double amount) {

		Plan plan = this.planService.queryPlanById(id);
		if (amount == 20d || amount == 30d || amount == 50d || amount == 100d || amount == 120d || amount == 150d || amount == 180d) {
			plan.getTopup().setTopup_fee(amount);
		} else {
			plan.getTopup().setTopup_fee(20d);
		}
		
		model.addAttribute("orderPlan", plan);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		model.addAttribute("hardwares", hardwares);
		
		String url = "";
		if ("personal".equals(plan.getPlan_class())) {
			url = "broadband-customer/customer-order-personal";
		} else if ("business".equals(plan.getPlan_class())) {
			url = "broadband-customer/customer-order-business";
		}
		
		return url;
	}

	@RequestMapping(value = "/order/personal/confirm")
	public String orderPersonalConfirm(Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order-personal";
		}
		
		this.crmService.doOrderConfirm(customer, plan);
		
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("company", cd);
		
		return "broadband-customer/customer-order-confirm";
	}
	
	@RequestMapping(value = "/order/business/confirm")
	public String orderBusinessConfirm(Model model,
			@ModelAttribute("customer") @Validated(CustomerOrganizationValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order-business";
		}
		
		this.crmService.doOrderConfirm(customer, plan);
		
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("company", cd);
		
		return "broadband-customer/customer-order-confirm";
	}
	
	@RequestMapping(value = "/order/checkout", method = RequestMethod.POST)
	public String orderCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult result) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order";
		}

		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(customer.getCustomerOrder().getOrder_total_price()));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
		System.out.println(redirectUrl);

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/order/checkout")
	public String toSignupPayment(Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult error,
			@ModelAttribute("orderPlan") Plan plan, RedirectAttributes attr,
			@RequestParam(value = "result", required = true) String result,
			SessionStatus status
			) throws Exception {
		
		if (error.hasErrors()) {
			return "broadband-customer/customer-order";
		}

		Response responseBean = null;

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			customer.setStatus("active");
			customer.setCustomer_type("personal");
			customer.setUser_name(customer.getLogin_name());
			customer.setPassword(TMUtils.generateRandomString(6));
			customer.setBalance(plan.getTopup().getTopup_fee() == null ? 0 : plan.getTopup().getTopup_fee());

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
			customerTransaction.setTransaction_sort(plan.getPlan_group());

			this.crmService.registerCustomer(customer, customerTransaction);
			
			this.crmService.createInvoicePDFByInvoiceID(customerTransaction.getInvoice_id());

			String filePath = TMUtils.createPath(
					"broadband"
					+File.separator+"customers"
					+File.separator+customer.getId()
					+File.separator+"Invoice-"+customerTransaction.getInvoice_id()+".pdf");
			
			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			// call mail at value retriever
			TMUtils.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			// binding attachment name & path to email
			applicationEmail.setAttachName("Invoice-" + customerTransaction.getInvoice_id() + ".pdf");
			applicationEmail.setAttachPath(filePath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			// get sms register template from db
			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
			TMUtils.mailAtValueRetriever(notification, customer, companyDetail);
			// send sms to customer's mobile phone
			this.smserService.sendSMSByAsynchronousMode(customer, notification);
			status.setComplete();
		} else {

		}

		attr.addFlashAttribute("responseBean", responseBean);

		return "redirect:/order/result";
	}
	
	@RequestMapping(value = "/order/result")
	public String toOrderResult(SessionStatus status) {
		
		return "broadband-customer/customer-order-result";
	}

	@RequestMapping(value = "/login")
	public String toLogin(Model model) {
		return "broadband-customer/login";
	}

	@RequestMapping(value = "/forgotten-password")
	public String toForgottenPassword(Model model) {
		return "broadband-customer/forgotten-password";
	}


	
	@RequestMapping("/customer/home/redirect")
	public String redirectCustomerHome(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Welcome to CyberTech Customer Home.");
		return "redirect:/customer/home";
	}

	@RequestMapping(value = "/customer/home")
	public String customerHome(Model model, HttpServletRequest req) {
		
		model.addAttribute("home", "active");
		
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.getParams().put("where", "query_status_no_discard_cancel");
		customerOrder.getParams().put("customer_id", customer.getId());
		customerOrder.getParams().put("order_status", "discard");
		customerOrder.getParams().put("order_status_1", "cancel");
		
		List<CustomerOrder> customerOrders = this.crmService.queryCustomerOrders(customerOrder);
		customer.setCustomerOrders(customerOrders);
		
		customer.getCustomerInvoice().setBalance(this.crmService.queryCustomerInvoicesBalanceByCid(customer.getId(), "unpaid"));
		
		model.addAttribute("customerOrders", customerOrders);
		
		return "broadband-customer/customer-home";
	}

	@RequestMapping(value = "/customer/data")
	public String customerData(Model model) {
		model.addAttribute("data", "active");
		return "broadband-customer/customer-data";
	}

	@RequestMapping(value = "/customer/billing/{pageNo}")
	public String customerBilling(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			HttpServletRequest request) {
		
		model.addAttribute("bills", "active");
		Customer customer = (Customer) request.getSession().getAttribute("customerSession");
		Page<CustomerInvoice> invoicePage = new Page<CustomerInvoice>();
		invoicePage.setPageNo(pageNo);
		invoicePage.setPageSize(12);
		invoicePage.getParams().put("orderby", "order by create_date desc");
		invoicePage.getParams().put("customer_id", customer.getId());
		this.crmService.queryCustomerInvoicesByPage(invoicePage);
		
		model.addAttribute("page", invoicePage);
		model.addAttribute("transactionsList", this.crmService.queryCustomerTransactionsByCustomerId(customer.getId()));
		return "broadband-customer/customer-billing";
	}

	@RequestMapping(value = "/customer/billing/discard/{pageNo}")
	public String customerDiscardBilling(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			HttpServletRequest request) {

		model.addAttribute("discard_bills", "active");
		Customer customer = (Customer) request.getSession().getAttribute("customerSession");
		Page<CustomerInvoice> invoicePage = new Page<CustomerInvoice>();
		invoicePage.setPageNo(pageNo);
		invoicePage.setPageSize(12);
		invoicePage.getParams().put("orderby", "order by create_date desc");
		invoicePage.getParams().put("customer_id", customer.getId());
		invoicePage.getParams().put("status", "discard");
		this.crmService.queryCustomerInvoicesByPage(invoicePage);
		
		model.addAttribute("page", invoicePage);
		return "broadband-customer/customer-discard-billing";
	}
	
	@RequestMapping(value = "/customer/change-password")
	public String changePassword(Model model, HttpServletRequest request) {
		
		model.addAttribute("change_password", "active");
		return "broadband-customer/customer-change-password";
	}
	
	@RequestMapping(value = "/customer/change-password/redirect")
	public String changePasswordRedirect(Model model, RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Update passowrd is successful.");
		return "redirect:/customer/change-password";
	}
	
	@RequestMapping("/customer/topup")
	public String customerTopup(Model model) {
		model.addAttribute("home", "active");
		return "broadband-customer/customer-payment-topup";
	}
	
	@RequestMapping(value = "/customer/topup/checkout", method = RequestMethod.POST)
	public String topupCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
			@RequestParam("topup") Double topup) {

		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(topup));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/topup/checkout")
	public String toTopupSuccess(Model model,
			@RequestParam(value = "result", required = true) String result,
			RedirectAttributes attr, HttpServletRequest request
			) throws Exception {
		
		Customer customer =  (Customer) request.getSession().getAttribute("customerSession");

		Response responseBean = null;

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			customer.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
			customer.getParams().put("id", customer.getId());

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
			customerTransaction.setTransaction_sort("");
			
			customerTransaction.setCustomer_id(customer.getId());
			customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
			
			this.crmService.customerTopup(customer, customerTransaction);
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/topup";
	}
	

	@RequestMapping(value = "/customer/payment")
	public String customerPayment(Model model) {
		model.addAttribute("payment", "active");
		return "broadband-customer/customer-payment";
	}

	@RequestMapping(value = "/signout")
	public String signout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/home";
	}
	
	// download invoice PDF directly
	@RequestMapping(value = "/broadband-customer/billing/invoice/pdf/download/{invoiceId}")
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
        headers.setContentType( MediaType.parseMediaType( "application/pdf" ) );
        // get file name with file's suffix
        String filename = "Invoice_"+URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".pdf", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }
    
	@RequestMapping(value = "/about-us")
	public String toAboutUs(Model model) {
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/about-us";
	}
	
	@RequestMapping(value = "/wifi-solution")
	public String toWifi(Model model) {
		return "broadband-customer/wifi-solution";
	}
	
	@RequestMapping(value = "/contact-us")
	public String toContactUs(Model model) {
		return "broadband-customer/contact-us";
	}

	@RequestMapping(value = "/term-and-conditions")
	public String toTermAndConditions(Model model) {
		
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/term-and-conditions";
	}

	@RequestMapping(value = "/chorus-googlemap")
	public String toChorusGoogleMap(Model model) {
		return "broadband-customer/chorus-googlemap";
	}

}

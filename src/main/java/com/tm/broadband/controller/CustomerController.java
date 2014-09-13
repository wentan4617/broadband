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
import org.codehaus.jackson.map.ObjectMapper;
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
import com.tm.broadband.model.CallInternationalRate;
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
import com.tm.broadband.model.Voucher;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.DataService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.test.Console;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes(value = { "customer", "orderPlan", "planTypeMap"})
public class CustomerController {

	private PlanService planService;
	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;
	private DataService dataService;
	private BillingService billingService;

	@Autowired
	public CustomerController(PlanService planService, CRMService crmService,
			MailerService mailerService, SystemService systemService,
			SmserService smserService, DataService dataService, BillingService billingService) {
		this.planService = planService;
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.smserService = smserService;
		this.dataService = dataService;
		this.billingService = billingService;
	}

	@RequestMapping(value = { "", "/home" })
	public String home(Model model, HttpSession session) {
		session.setAttribute("seoSession", this.systemService.querySEO());
		model.addAttribute("nofollow", "nofollow");
		return "broadband-customer/home";
	}

	@RequestMapping("/plans/{group}/{class}")
	public String plans(Model model, 
			@PathVariable("group") String group,
			@PathVariable("class") String classz,
			HttpSession session) {
		
		session.removeAttribute("customerReg");
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
		model.addAttribute("customer", customer);
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		//Map<String, List<Plan>> planMap = new HashMap<String, List<Plan>>(); // key = plan_type
		String url = "";
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", group);
		plan.getParams().put("plan_class", classz);
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("orderby", "order by place_sort");
		
		plans = this.planService.queryPlans(plan);
		
		//this.wiredPlanMap(planTypeMap, plans, true);
		this.wiredPlanMapBySort(planTypeMap, plans);
		
		model.addAttribute("planTypeMap", planTypeMap);
		
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
	
	@RequestMapping("/plans/{group}/{class}/{type}/promotion")
	public String plansPromotion(Model model, 
			@PathVariable("group") String group,
			@PathVariable("class") String classz,
			@PathVariable("type") String type) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
		model.addAttribute("customer", customer);
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		//Map<String, List<Plan>> planMap = new HashMap<String, List<Plan>>(); // key = plan_type
		String url = "";
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", group);
		plan.getParams().put("plan_class", classz);
		plan.getParams().put("promotion", true);
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("orderby", "order by place_sort");
		
		plans = this.planService.queryPlans(plan);
		
		this.wiredPlanMap(planTypeMap, plans, false);
		//this.wiredPlanMapBySort(planTypeMap, plans);
		
		model.addAttribute("planTypeMap", planTypeMap);
		model.addAttribute("selectdType", type);
		
		if ("personal".equals(classz)) {	
			url = "broadband-customer/plan-detail-term-personal-promotion";
		} else if ("business".equals(classz)) {
			url = "broadband-customer/plan-detail-term-business-promotion";
		} 

		return url;
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
	
	private void wiredPlanMap(Map<String, Map<String, List<Plan>>> planTypeMap, List<Plan> plans, boolean wiredPromotion) {
		if (plans != null) {
			for (Plan p: plans) {
				Map<String, List<Plan>> planMap = planTypeMap.get(p.getPlan_type());
				if (planMap == null) {
					planMap = new HashMap<String, List<Plan>>();	
					if (p.getPromotion() != null && p.getPromotion().booleanValue()) {
						List<Plan> plansPromotion = new ArrayList<Plan>();
						if (wiredPromotion) {
							plansPromotion.add(p); 
						} else if (p.getOriginal_price() != null && p.getOriginal_price() > 0) {
							plansPromotion.add(p); 
						}
						planMap.put("plansPromotion", plansPromotion);
					} else {
						List<Plan> list = new ArrayList<Plan>();
						list.add(p);
						planMap.put("plans", list);
					}
					planTypeMap.put(p.getPlan_type(), planMap);
				} else {
					if (p.getPromotion() != null && p.getPromotion().booleanValue()) {
						List<Plan> plansPromotion = planMap.get("plansPromotion");
						if (plansPromotion == null) {
							plansPromotion = new ArrayList<Plan>();
							if (wiredPromotion) {
								plansPromotion.add(p); 
							} else if (p.getOriginal_price() != null && p.getOriginal_price() > 0) {
								plansPromotion.add(p); 
							}
							planMap.put("plansPromotion", plansPromotion);
						} else {
							if (wiredPromotion) {
								plansPromotion.add(p); 
							} else if (p.getOriginal_price() != null && p.getOriginal_price() > 0) {
								plansPromotion.add(p); 
							}
						}
					} else {
						List<Plan> list = planMap.get("plans");
						if (list == null) {
							list = new ArrayList<Plan>();
							list.add(p);
							planMap.put("plans", list);
						} else {
							list.add(p);
						}
					}
				}
			}
		}
	}
	
	@RequestMapping("/plans/{group}/{class}/{type}/address-check/{id}") 
	public String toAddressCheck(Model model,
			@PathVariable("group") String group,
			@PathVariable("class") String classz, 
			@PathVariable("type") String type,
			@PathVariable("id") int id) {
		
		model.addAttribute("select_plan_id", id);
		model.addAttribute("select_plan_type", type);
		return "broadband-customer/address-check";
	}

	@RequestMapping("/order/{id}") 
	public String orderPlanNoTermOrTerm(Model model, 
			@PathVariable("id") int id,
			HttpServletRequest req) {
		
		Plan plan = new Plan();
		plan.getParams().put("id", id);
		plan = this.planService.queryPlan(plan);
		if (plan.getId() == 26) {
			plan.getTopup().setTopup_fee(20d);
		} else if (plan.getId() == 53) {
			plan.getTopup().setTopup_fee(30d);
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
	
	@RequestMapping("/order/{id}/topup/{amount}")
	public String orderPlanTopup(Model model, 
			@PathVariable("id") int id,
			@PathVariable("amount") Double amount) {

		Plan plan = new Plan();
		plan.getParams().put("id", id);
		plan = this.planService.queryPlan(plan);
		if (plan.getId() == 26) {
			plan.getTopup().setTopup_fee(amount);
		} else if (plan.getId() == 53) {
			plan.getTopup().setTopup_fee(amount);
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
	
	/*
	 * /order/plan-term/personal/save
	 * /order/plan-term/business/save
	 */
	
	@RequestMapping(value = "/order/plan-term/personal/save", method = RequestMethod.POST)
	public String orderTermPersonalSave(Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr, SessionStatus status) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order-personal";
		}
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		customer.getCustomerOrder().setOrder_status("pending");
		
		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder());
		
		//status.setComplete();
		
		Response responseBean = new Response();
		responseBean.setSuccess("1");
		attr.addFlashAttribute("responseBean", responseBean);
		
		return "redirect:/order/result";
	}
	
	@RequestMapping(value = "/order/plan-term/business/save", method = RequestMethod.POST)
	public String orderTermBusinessSave(Model model,
			@ModelAttribute("customer") @Validated(CustomerOrganizationValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr, SessionStatus status) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order-business";
		}
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		
		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder());
		
		//status.setComplete();
		
		Response responseBean = new Response();
		responseBean.setSuccess("1");
		attr.addFlashAttribute("responseBean", responseBean);
		
		return "redirect:/order/result";
	}
	
	/*
	 * end *****************************************************
	 */
	
	@RequestMapping(value = "/order/checkout", method = RequestMethod.POST)
	public String orderCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
			@ModelAttribute("customer") Customer customer,
			@ModelAttribute("orderPlan") Plan plan
			) { // @Validated(CustomerValidatedMark.class)  , BindingResult result
		
		if ("business".equals(customer.getCustomer_type())) {
			customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() * 1.15);
		}
		
		Double orderTotalPrice = customer.getCustomerOrder().getOrder_total_price();
		Double vprice = 0d;
		
		for (Voucher vQuery: customer.getVouchers()) {
			orderTotalPrice -= vQuery.getFace_value();
			vprice += vQuery.getFace_value();
		}
		
		String redirectUrl = "";
		
		if (orderTotalPrice > 0) {
			
			customer.setBalance(vprice + orderTotalPrice);
			
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

		} else {
			redirectUrl = "/order/result/success";
			
			customer.setStatus("active");
			customer.setCustomer_type("personal");
			customer.setUser_name(customer.getLogin_name());
			customer.setPassword(TMUtils.generateRandomString(6));
			customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
			customer.setBalance(Math.abs(vprice));
			
			List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();
			
			for (Voucher vQuery: customer.getVouchers()) {
				CustomerTransaction ctVoucher = new CustomerTransaction();
				ctVoucher.setAmount(vQuery.getFace_value());
				ctVoucher.setTransaction_type("purchare");
				ctVoucher.setTransaction_sort("voucher");
				ctVoucher.setCard_name("voucher: " + vQuery.getSerial_number());
				cts.add(ctVoucher);
			}

			customer.getCustomerOrder().setOrder_status("paid");
			this.crmService.registerCustomer(customer, cts);
			
			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
			
			//this.crmService.createInvoicePDFByInvoiceID(cts.get(0).getInvoice_id(), false);

			/*String filePath = TMUtils.createPath(
					"broadband" 
					+ File.separator
					+ "customers" + File.separator + customer.getId()
					+ File.separator + "invoice_" + cts.get(0).getInvoice_id() + ".pdf");*/
			
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

			Response responseBean = new Response();
			responseBean.setSuccess("1");
			attr.addFlashAttribute("responseBean", responseBean);

		}

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/order/checkout")
	public String toSignupPayment(Model model,
			@ModelAttribute("customer") Customer customer,// BindingResult error, //@Validated(CustomerValidatedMark.class) 
			@ModelAttribute("orderPlan") Plan plan, RedirectAttributes attr,
			@RequestParam(value = "result", required = true) String result,
			SessionStatus status
			) throws Exception {
		
		String url = "redirect:/order/result/error";

		Response responseBean = null;

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			url = "redirect:/order/result/success";
			
			customer.setStatus("active");
			customer.setCustomer_type("personal");
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
			customerTransaction.setTransaction_sort(plan.getPlan_group());
			
			cts.add(customerTransaction);
			
			for (Voucher vQuery: customer.getVouchers()) {
				CustomerTransaction ctVoucher = new CustomerTransaction();
				ctVoucher.setAmount(vQuery.getFace_value());
				ctVoucher.setTransaction_type("purchare");
				ctVoucher.setTransaction_sort("voucher");
				ctVoucher.setCard_name("voucher: " + vQuery.getSerial_number());
				cts.add(ctVoucher);
			}
			
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
			//status.setComplete();
		} else {

		}

		attr.addFlashAttribute("responseBean", responseBean);

		return url; //"redirect:/order/result";
	}
	
	
	
	@RequestMapping(value = "/order/result")
	public String toOrderResult(SessionStatus status, HttpSession session) {
		status.setComplete();
		session.removeAttribute("customerReg");
		return "broadband-customer/customer-order-result-success";
	}
	
	@RequestMapping(value = "/order/result/success")
	public String toOrderPaidResultSuccess(SessionStatus status) {
		status.setComplete();
		return "broadband-customer/customer-order-result-success";
	}
	
	@RequestMapping(value = "/order/result/error")
	public String toOrderPaidResultError(SessionStatus status) {
		
		return "broadband-customer/customer-order-result-error";
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

	@RequestMapping("/login/{type}/{customer_id}/{md5_password}")
	public String loginBilling(Model model,
			RedirectAttributes attr,
			@PathVariable("type") String type,
			@PathVariable("customer_id") Integer customer_id,
			@PathVariable("md5_password") String md5_password,
			HttpServletRequest req) {
		
		Customer c = new Customer();
		c.getParams().put("where", "when_login_by_id_md5pass");
		c.getParams().put("id", customer_id);
		c.getParams().put("md5_password", md5_password.substring(3, md5_password.length()-3));
		c.getParams().put("status", "active");
		Customer customerSession = this.crmService.queryCustomerWhenLogin(c);
		
		System.out.println(customerSession);
		
		if(customerSession==null){
			System.out.println("SESSION IS NULL");
			return "redirect:/login";
		} else {
			System.out.println("SESSION IS NOT NULL");
			req.getSession().setAttribute("customerSession", customerSession);
		}

		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("where", "query_status_no_discard_cancel");
		coQuery.getParams().put("customer_id", customer_id);
		coQuery.getParams().put("order_status", "discard");
		coQuery.getParams().put("order_status_1", "cancel");
		List<CustomerOrder> customerOrders = this.crmService.queryCustomerOrders(coQuery);
		customerSession.setCustomerOrders(customerOrders);
		
		if("home".equals(type)){
			
			model.addAttribute("success", "Welcome to CyberTech Customer Home.");
			
			customerSession.getCustomerInvoice().setBalance(this.crmService.queryCustomerInvoicesBalanceByCid(customer_id, "unpaid"));
			
			model.addAttribute("customerOrders", customerOrders);
			
			return "broadband-customer/customer-home";
		}
		
		attr.addFlashAttribute("success", "Welcome to CyberTech Customer Billing.");
		
		return "redirect:/customer/billing/view";
	}

	@RequestMapping(value = "/customer/home")
	public String customerHome(Model model, HttpServletRequest req) {
		
		model.addAttribute("home", "active");
		
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("where", "query_status_no_discard_cancel");
		coQuery.getParams().put("customer_id", customer.getId());
		coQuery.getParams().put("order_status", "discard");
		coQuery.getParams().put("order_status_1", "cancel");
		
		List<CustomerOrder> customerOrders = this.crmService.queryCustomerOrders(coQuery);
		customer.setCustomerOrders(customerOrders);
		
		customer.getCustomerInvoice().setBalance(this.crmService.queryCustomerInvoicesBalanceByCid(customer.getId(), "unpaid"));
		
		model.addAttribute("customerOrders", customerOrders);
		
		return "broadband-customer/customer-home";
	}

	@RequestMapping(value = "/customer/data")
	public String customerData(Model model, HttpServletRequest req) {
		
		model.addAttribute("data", "active");
		model.addAttribute("current_date", TMUtils.dateFormatYYYYMMDD(new Date()).substring(0, 7));
		
		return "broadband-customer/customer-data";
	}

	@RequestMapping(value = "/customer/billing/view")
	public String customerBilling(Model model, HttpServletRequest req) {
		model.addAttribute("bills", "active");
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
			@RequestParam("prepaymonths") Integer months) {
		
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
		CustomerOrderDetail cod = customer.getCustomerOrders().get(0).getCustomerOrderDetails().get(0);
		Double price = cod.getDetail_price();
		Double total = 0d;
		
		if (months == 1) {
			total = price;
		} else if (months == 3) {
			Double temp = price * 3 * 0.03;
			total = price * 3 - temp.intValue();
		} else if (months == 6) {
			Double temp = price * 6 * 0.07;
			total = price * 6 - temp.intValue();
		} else if (months == 12) {
			Double temp = price * 12 * 0.15;
			total = price * 12 - temp.intValue();
		} else {
			total = new Double(months);
		}

		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(total));
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
			
			Customer c = new Customer();
			customer.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
			c.setBalance(customer.getBalance());
			c.getParams().put("id", customer.getId());

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
			
			this.crmService.customerTopup(c, customerTransaction);
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/topup";
	}
	
	@RequestMapping(value = "/customer/invoice/checkout", method = RequestMethod.POST)
	public String balanceCheckout(Model model, 
			@RequestParam("invoice_id") int invoice_id,
			HttpServletRequest req, RedirectAttributes attr) {
		
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
		
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("id", invoice_id);
		ciQuery.getParams().put("customer_id", customer.getId());
		
		CustomerInvoice ci = this.crmService.queryCustomerInvoice(ciQuery);
		customer.setCustomerInvoice(ci);
		
		System.out.println("customer_id: " + customer.getId() + ", invoice_id: " + invoice_id + ", balance: " + ci.getBalance());

		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(ci.getBalance()));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/invoice/checkout")
	public String toBalanceSuccess(Model model,
			@RequestParam(value = "result", required = true) String result,
			RedirectAttributes attr, HttpServletRequest request
			) throws Exception {
		
		Customer customer =  (Customer) request.getSession().getAttribute("customerSession");

		Response responseBean = null;

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
			customerTransaction.setTransaction_sort("");
			
			customerTransaction.setCustomer_id(customer.getId());
			customerTransaction.setOrder_id(customer.getCustomerInvoice().getOrder_id());
			customerTransaction.setInvoice_id(customer.getCustomerInvoice().getId());
			customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
			
			CustomerInvoice customerInvoice = new CustomerInvoice();
			customerInvoice.setStatus("paid");
			customerInvoice.setAmount_paid(customerTransaction.getAmount());
			customerInvoice.setBalance(TMUtils.bigOperationTwoReminders(customer.getCustomerInvoice().getBalance(), customerInvoice.getAmount_paid(), "sub"));
			customerInvoice.getParams().put("id", customer.getCustomerInvoice().getId());
			
			this.crmService.customerBalance(customerInvoice, customerTransaction);
			
			Notification notification = this.crmService.queryNotificationBySort("payment", "email");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
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
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/billing/view";
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
    public ResponseEntity<byte[]> downloadInvoicePDF(Model model , @PathVariable(value = "invoiceId") int invoiceId) throws IOException {
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
	
	@RequestMapping(value = "/e-commerce")
	public String toECommerce(Model model) {
		return "broadband-customer/e-commerce";
	}
	
	@RequestMapping(value = "/contact-us")
	public String toContactUs(Model model) {
		return "broadband-customer/contact-us";
	}
	
	@RequestMapping(value = "/contact-us/redirect")
	public String toContactUsRedirect(Model model, RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Your request has been submitted, we will respond you as fast as we can.");
		return "redirect:/contact-us";
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
	
	@RequestMapping("/home-phone-calling-rates")
	public String homePhoneCallingRates(Model model) {
		
		List<CallInternationalRate> letterCirs = new ArrayList<CallInternationalRate>();
		letterCirs.add(new CallInternationalRate("A"));
		letterCirs.add(new CallInternationalRate("B"));
		letterCirs.add(new CallInternationalRate("C"));
		letterCirs.add(new CallInternationalRate("D"));
		letterCirs.add(new CallInternationalRate("E"));
		letterCirs.add(new CallInternationalRate("F"));
		letterCirs.add(new CallInternationalRate("G"));
		letterCirs.add(new CallInternationalRate("H"));
		letterCirs.add(new CallInternationalRate("I"));
		letterCirs.add(new CallInternationalRate("J"));
		letterCirs.add(new CallInternationalRate("K"));
		letterCirs.add(new CallInternationalRate("L"));
		letterCirs.add(new CallInternationalRate("M"));
		letterCirs.add(new CallInternationalRate("N"));
		letterCirs.add(new CallInternationalRate("O"));
		letterCirs.add(new CallInternationalRate("P"));
		letterCirs.add(new CallInternationalRate("Q"));
		letterCirs.add(new CallInternationalRate("R"));
		letterCirs.add(new CallInternationalRate("S"));
		letterCirs.add(new CallInternationalRate("T"));
		letterCirs.add(new CallInternationalRate("U"));
		letterCirs.add(new CallInternationalRate("V"));
		letterCirs.add(new CallInternationalRate("W"));
		letterCirs.add(new CallInternationalRate("X"));
		letterCirs.add(new CallInternationalRate("Y"));
		letterCirs.add(new CallInternationalRate("Z"));
		letterCirs.add(new CallInternationalRate("Other"));
		
		List<CallInternationalRate> cirs = this.billingService.queryCallInternationalRatesGroupBy();
		if (cirs != null) {
			for (CallInternationalRate cir: cirs) {
				boolean b = false;
				for (CallInternationalRate lCir: letterCirs) {
						
					if (!"".equals(cir.getArea_name())
							&& lCir.getLetter().equals(String.valueOf(cir.getArea_name().toUpperCase().charAt(0)))) {
						//System.out.println(lCir.getLetter() + ", " + cir.getArea_name().toUpperCase().charAt(0));
						lCir.getCirs().add(cir);
						b = true;
						break;
					} 
				}
				if (!b) {
					letterCirs.get(letterCirs.size() - 1).getCirs().add(cir);
				}
			}
		}
		model.addAttribute("letterCirs", letterCirs);
		
		return "broadband-customer/calling-rates";
	}

	@RequestMapping(value = "/voucher")
	public String toVoucherChecking(Model model) {
		
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/voucher";
	}
	
	// download customer ordering form PDF directly
	@RequestMapping(value = "/customer/home/ordering-form/pdf/download")
    public ResponseEntity<byte[]> downloadOrderingFormPDF(Model model, HttpServletRequest req) throws IOException {
		
    	Customer customer =  (Customer) req.getSession().getAttribute("customerSession");
    	
    	String filePath = this.crmService.queryCustomerOrderingFormPathById(customer.getCustomerOrders().get(0).getId());
		
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
 	@RequestMapping(value = "/customer/home/receipt/pdf/download")
 	public ResponseEntity<byte[]> downloadReceiptPDF(Model model, HttpServletRequest req) throws IOException {
 		
 		Customer customer =  (Customer) req.getSession().getAttribute("customerSession");
 		
 		String filePath = this.crmService.queryCustomerReceiptFormPathById(customer.getCustomerOrders().get(0).getId());
 		
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
    
    @RequestMapping(value = "/customer/ordering-form/checkout", method = RequestMethod.POST)
	public String orderingFormCheckout(Model model, HttpServletRequest req, RedirectAttributes attr) {
    	
		Customer customer = (Customer) req.getSession().getAttribute("customerSession");

		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput(new DecimalFormat("#.00").format(customer.getCustomerOrders().get(0).getOrder_total_price()));
		//gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/ordering-form/checkout")
	public String toOrderingFormSuccess(Model model,
			@RequestParam(value = "result", required = true) String result,
			RedirectAttributes attr, HttpServletRequest request
			) throws Exception {
		
		Customer customer =  (Customer) request.getSession().getAttribute("customerSession");
		
		System.out.println("customer: " + new ObjectMapper().writeValueAsString(customer));

		Response responseBean = null;

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
	
		//System.out.println("responseBean: " + new ObjectMapper().writeValueAsString(responseBean));

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			Customer c = new Customer();
			customer.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
			c.setBalance((customer.getBalance() != null ? customer.getBalance() : 0));
			c.getParams().put("id", customer.getId());

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
			
			CustomerOrder order = new CustomerOrder();
			order.setOrder_status("paid");
			order.getParams().put("id", customer.getCustomerOrders().get(0).getId());
			this.crmService.customerOrderingForm(c, order, customerTransaction);
			
			customer.setCustomerOrder(customer.getCustomerOrders().get(0));
			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
			
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
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/home";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	@RequestMapping("/plans/{type}")
	public String broadband(Model model,
			@PathVariable("type") String type, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		
		if (customer == null) {
			customer = new Customer();
			customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customer.setSelect_plan_id(0);
			customer.setSelect_plan_type("0");
			session.setAttribute("customerReg", customer);
		}
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		String url = "";
		
		if ("broadband".equals(type)) {
			model.addAttribute("title", "");
			model.addAttribute("adsl", "active");
			type = "ADSL";
			url = "broadband-customer/plans/broadband";
		} else if ("ultra-fast-vdsl".equals(type)) {
			model.addAttribute("title", "");
			model.addAttribute("vdsl", "active");
			type = "VDSL";
			url = "broadband-customer/plans/broadband";
			//url = "broadband-customer/plans/ultra-fast-vdsl";
		} else if ("ultra-fast-fibre".equals(type)) {
			model.addAttribute("title", "");
			model.addAttribute("ufb", "active");
			type = "UFB";
			url = "broadband-customer/plans/broadband";
			//url = "broadband-customer/plans/ultra-fast-fibre";
		}
		
		model.addAttribute("type", type);
		
		String type_search = type;
		
		if (customer.getBroadband() != null && !customer.getBroadband().getServices_available().contains(type)) {
			type_search = "ADSL";
		}
		
		model.addAttribute("type_search", type_search);
		
		Plan plan = new Plan();
		plan.getParams().put("plan_type", type_search);
		plan.getParams().put("plan_class", "personal");
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("plan_group_false", "plan-topup");
		plan.getParams().put("orderby", "order by plan_price");
		
		plans = this.planService.queryPlans(plan);
		
		this.wiredPlanMapBySort(planTypeMap, plans);
		
		model.addAttribute("planTypeMap", planTypeMap);
		
		return url;
	}
	
	@RequestMapping("/plans/address-check/{type}/{id}") 
	public String toAddressCheck(Model model,
			@PathVariable("type") String type,
			@PathVariable("id") int id,
			HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		customer.setSelect_plan_id(id);
		customer.setSelect_plan_type(type);
		
		model.addAttribute("select_plan_id", id);
		model.addAttribute("select_plan_type", type);
		
		if ("en".equals(customer.getLanguage())) {
			model.addAttribute("en", "en");
		}
		return "broadband-customer/plans/address-check";
	}
	
	@RequestMapping("/plans/promotion") 
	public String plansNewZelandPromotion(Model model, HttpSession session) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");
		customer.getCustomerOrder().setSale_id(10023);
		customer.setLanguage("cn");
		session.setAttribute("customerReg", customer);
		
		return "redirect:/plans/address-check/0/0";
	}
	
	@RequestMapping("/plans/promotion/en") 
	public String plansNewZelandPromotionEn(Model model, HttpSession session) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");
		customer.getCustomerOrder().setSale_id(10023);
		customer.setLanguage("en");
		session.setAttribute("customerReg", customer);
		
		return "redirect:/plans/address-check/0/0";
	}
	
	
	@RequestMapping("/plans/order") 
	public String toOrderPlan(Model model, HttpSession session,
			@RequestParam(value = "select_plan_type", required = false) String select_plan_type) {
		
		String url = "broadband-customer/plans/customer-order";
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		
		if (select_plan_type != null && !"".equals(select_plan_type)) {
			customer.setSelect_plan_type(select_plan_type);
		}
		if ("0".equals(customer.getSelect_plan_type())) {
			customer.setSelect_plan_type("VDSL");
		}
		
		if (!customer.isServiceAvailable()) {
			System.out.println("customer.isServiceAvailable(): " + customer.isServiceAvailable());
			String type = "broadband";
			if ("ADSL".equals(customer.getSelect_plan_type())) {
				type = "broadband";
			} else if ("VDSL".equals(customer.getSelect_plan_type())){
				type = "ultra-fast-vdsl";
			} else if ("UFB".equals(customer.getSelect_plan_type())) {
				type = "ultra-fast-fibre";
			}
			url = "redirect:/plans/" + type;
		}
		
		return url;
	}
	
	@RequestMapping("/plans/address/clear") 
	public String addressClear(Model model, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		String type = "broadband", url = "";
		if ("ADSL".equals(customer.getSelect_plan_type())) {
			type = "broadband";
		} else if ("VDSL".equals(customer.getSelect_plan_type())){
			type = "ultra-fast-vdsl";
		} else if ("UFB".equals(customer.getSelect_plan_type())) {
			type = "ultra-fast-fibre";
		}
		url = "redirect:/plans/" + type;
		session.removeAttribute("customerReg");
		
		return url;
	}
	
	@RequestMapping("/plans/order/summary") 
	public String plansOrderSummary(Model model, HttpSession session) {
		return "broadband-customer/plans/order-summary";
	}
	
	
	@RequestMapping(value = "/plans/order/bankdeposit", method = RequestMethod.POST)
	public String plansOrderBankDeposit(RedirectAttributes attr, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		customer.getCustomerOrder().setOrder_status("pending");
		customer.setBalance(0d);
	
		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder());
		
		Response responseBean = new Response();
		responseBean.setSuccess("1");
		attr.addFlashAttribute("responseBean", responseBean);
		
		System.out.println("1 :" + customer.getCustomerOrder().getOrder_total_price());
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
		
		System.out.println("2 :" + customer.getCustomerOrder().getOrder_total_price());
		
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("online-ordering", "email");
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("online-ordering", "sms"); // get sms register template from db
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
		
		return "redirect:/plans/order/result";
	}
	
	
	@RequestMapping(value = "/plans/order/dps", method = RequestMethod.POST)
	public String planOrderDPS(Model model, HttpServletRequest req, HttpSession session, RedirectAttributes attr) { 
		
		Customer customer = (Customer) session.getAttribute("customerReg");
		
		Double orderTotalPrice = customer.getCustomerOrder().getOrder_total_price();
		Double vprice = 0d;
		
		for (Voucher vQuery: customer.getVouchers()) {
			orderTotalPrice -= vQuery.getFace_value();
			vprice += vQuery.getFace_value();
		}
		
		String redirectUrl = "";
		
		if (orderTotalPrice > 0) {
			
			customer.setBalance(vprice + orderTotalPrice);
			
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

		} else {
			redirectUrl = "/plans/order/result/success";
			
			customer.setStatus("active");
			customer.setCustomer_type("personal");
			customer.setUser_name(customer.getLogin_name());
			customer.setPassword(TMUtils.generateRandomString(6));
			customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
			customer.setBalance(Math.abs(vprice));
			
			List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();
			
			for (Voucher vQuery: customer.getVouchers()) {
				CustomerTransaction ctVoucher = new CustomerTransaction();
				ctVoucher.setAmount(vQuery.getFace_value());
				ctVoucher.setTransaction_type("purchare");
				ctVoucher.setTransaction_sort("voucher");
				ctVoucher.setCard_name("voucher: " + vQuery.getSerial_number());
				cts.add(ctVoucher);
			}

			customer.getCustomerOrder().setOrder_status("paid");
			this.crmService.registerCustomer(customer, cts);
			
			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
			
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

			Response responseBean = new Response();
			responseBean.setSuccess("1");
			attr.addFlashAttribute("responseBean", responseBean);

		}

		return "redirect:" + redirectUrl;
	}
	
	
	@RequestMapping(value = "/plans/order/dps")
	public String planOrderDPS(Model model,
			@RequestParam(value = "result", required = true) String result,
			HttpSession session, RedirectAttributes attr) throws Exception {
		
		String url = "redirect:/plans/order/result/error";

		Response responseBean = null;
		
		Customer customer = (Customer) session.getAttribute("customerReg");

		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			url = "redirect:/plans/order/result/success";
			
			customer.setStatus("active");
			customer.setCustomer_type("personal");
			customer.setUser_name(customer.getLogin_name());
			customer.setPassword(TMUtils.generateRandomString(6));
			customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
			
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
			
			cts.add(customerTransaction);
			
			for (Voucher vQuery: customer.getVouchers()) {
				CustomerTransaction ctVoucher = new CustomerTransaction();
				ctVoucher.setAmount(vQuery.getFace_value());
				ctVoucher.setTransaction_type("purchare");
				ctVoucher.setTransaction_sort("voucher");
				ctVoucher.setCard_name("voucher: " + vQuery.getSerial_number());
				cts.add(ctVoucher);
			}
			
			customer.getCustomerOrder().setOrder_status("paid");
			this.crmService.registerCustomer(customer, cts);
			
			String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
			
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
			//status.setComplete();
		} else {

		}

		attr.addFlashAttribute("responseBean", responseBean);

		return url; //"redirect:/order/result";
	}
	
	@RequestMapping(value = "/plans/order/result")
	public String planOrderRtoOrderResult(HttpSession session) {
		session.removeAttribute("customerReg");
		return "broadband-customer/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/plans/order/result/success")
	public String planOrderResultSuccess(HttpSession session) {
		session.removeAttribute("customerReg");
		return "broadband-customer/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/plans/order/result/error")
	public String planOrderResultError(HttpSession session) {
		return "broadband-customer/plans/customer-order-result-error";
	}
}

package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.VOSVoIPRate;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.CustomerService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;

@Controller
public class CustomerController {

	private PlanService planService;
	private CRMService crmService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;
	private BillingService billingService;
	private CustomerService customerService;

	@Autowired
	public CustomerController(PlanService planService
			, CRMService crmService
			, MailerService mailerService
			, SystemService systemService
			, SmserService smserService
			, BillingService billingService
			, CustomerService customerService) {
		this.planService = planService;
		this.crmService = crmService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.smserService = smserService;
		this.billingService = billingService;
		this.customerService = customerService;
	}

	@RequestMapping(value = { "", "/home" })
	public String home(Model model, HttpSession session) {
		session.setAttribute("seoSession", this.systemService.querySEO());
		session.setAttribute("wedSession", this.systemService.queryWebsiteEditableDetail(null));
		session.setAttribute("wsrSession", this.systemService.queryWebsiteStaticResource(null));
		session.setAttribute("piSession", this.systemService.queryPlanIntroduction(null));
		session.setAttribute("tcSession", this.systemService.queryTermsCondition(null));
		model.addAttribute("nofollow", "nofollow");
		session.removeAttribute("customerReg");
		return "broadband-customer/home";
	}
	
	@RequestMapping(value = "/sign-in")
	public String toLogin(Model model, HttpSession session) {
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		String url = "broadband-customer/sign-in";
		if (customerSession != null) {
			url = "redirect:/customer/home";
		} 
		return url;
	}

	@RequestMapping(value = "/forgotten-password")
	public String toForgottenPassword(Model model, HttpSession session) {
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		String url = "broadband-customer/forgotten-password";
		if (customerSession != null) {
			url = "redirect:/customer/home";
		} 
		return url;
	}

	@RequestMapping("/plans/{group}/{class}")
	public String plans(Model model, HttpSession session
			, @PathVariable("group") String group
			, @PathVariable("class") String classz) {
	
		System.out.println("this is new " + classz + " customerReg, " + group);
		Customer customerReg = new Customer();
		customerReg.setNewOrder(false);
		customerReg.setSelect_plan_group(group);
		customerReg.setSelect_customer_type(classz);
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setPromotion(false);
		customerReg.setLanguage("en");
		session.setAttribute("customerReg", customerReg);
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
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
		} else if ("plan-term".equals(group) && "business".equals(classz)) {
			url = "broadband-customer/plan-detail-term-business";
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

	@RequestMapping(value = "/about-us")
	public String toAboutUs(Model model) {
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/about-us";
	}
	
	@RequestMapping(value = "/voucher")
	public String toVoucherChecking(Model model) {
		
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/voucher";
	}
	
	@RequestMapping("/phone-calling-rates")
	public String phoneCallingRates(Model model) {
		
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
					if (!"".equals(cir.getArea_name()) && lCir.getLetter().equals(String.valueOf(cir.getArea_name().toUpperCase().charAt(0)))) {
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
		
		return "broadband-customer/phone-calling-rates";
	}
	
	@RequestMapping("/voip-calling-rates")
	public String voipCallingRates(Model model) {
		
		List<VOSVoIPRate> letterVrs = new ArrayList<VOSVoIPRate>();
		letterVrs.add(new VOSVoIPRate("A"));
		letterVrs.add(new VOSVoIPRate("B"));
		letterVrs.add(new VOSVoIPRate("C"));
		letterVrs.add(new VOSVoIPRate("D"));
		letterVrs.add(new VOSVoIPRate("E"));
		letterVrs.add(new VOSVoIPRate("F"));
		letterVrs.add(new VOSVoIPRate("G"));
		letterVrs.add(new VOSVoIPRate("H"));
		letterVrs.add(new VOSVoIPRate("I"));
		letterVrs.add(new VOSVoIPRate("J"));
		letterVrs.add(new VOSVoIPRate("K"));
		letterVrs.add(new VOSVoIPRate("L"));
		letterVrs.add(new VOSVoIPRate("M"));
		letterVrs.add(new VOSVoIPRate("N"));
		letterVrs.add(new VOSVoIPRate("O"));
		letterVrs.add(new VOSVoIPRate("P"));
		letterVrs.add(new VOSVoIPRate("Q"));
		letterVrs.add(new VOSVoIPRate("R"));
		letterVrs.add(new VOSVoIPRate("S"));
		letterVrs.add(new VOSVoIPRate("T"));
		letterVrs.add(new VOSVoIPRate("U"));
		letterVrs.add(new VOSVoIPRate("V"));
		letterVrs.add(new VOSVoIPRate("W"));
		letterVrs.add(new VOSVoIPRate("X"));
		letterVrs.add(new VOSVoIPRate("Y"));
		letterVrs.add(new VOSVoIPRate("Z"));
		letterVrs.add(new VOSVoIPRate("Other"));
		
		List<VOSVoIPRate> vrs = this.billingService.queryVOSVoIPRatesGroupBy();
		if (vrs != null) {
			for (VOSVoIPRate vr: vrs) {
				boolean b = false;
				for (VOSVoIPRate lvr: letterVrs) {	
					if (!"".equals(vr.getArea_name()) && lvr.getLetter().equals(String.valueOf(vr.getArea_name().toUpperCase().charAt(0)))) {
						//System.out.println(lCir.getLetter() + ", " + cir.getArea_name().toUpperCase().charAt(0));
						lvr.getVrs().add(vr);
						b = true;
						break;
					} 
				}
				if (!b) {
					letterVrs.get(letterVrs.size() - 1).getVrs().add(vr);
				}
			}
		}
		model.addAttribute("letterVrs", letterVrs);
		
		return "broadband-customer/voip-calling-rates";
	}
	
	@RequestMapping(value = "/wifi-solution")
	public String toWifi(Model model) {
		return "broadband-customer/wifi-solution";
	}
	
	@RequestMapping(value = "/e-commerce")
	public String toECommerce(Model model) {
		return "broadband-customer/e-commerce";
	}
	
	@RequestMapping(value = "/term-and-conditions")
	public String toTermAndConditions(Model model) {
		CompanyDetail cd = this.systemService.queryCompanyDetail();
		model.addAttribute("cyberpark", cd);
		return "broadband-customer/term-and-conditions";
	}
	
	@RequestMapping("/customer/home/redirect")
	public String redirectCustomerHome(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Welcome to CyberTech Customer Home.");
		return "redirect:/customer/home";
	}

	@RequestMapping("/login/{type}/{customer_id}/{md5_password}")
	public String loginBilling(Model model, RedirectAttributes attr,
			@PathVariable("type") String type,
			@PathVariable("customer_id") Integer customer_id,
			@PathVariable("md5_password") String md5_password,
			HttpServletRequest req) {

		Customer c = new Customer();
		c.getParams().put("where", "when_login_by_id_md5pass");
		c.getParams().put("id", customer_id);
		c.getParams().put("md5_password", md5_password.substring(3, md5_password.length() - 3));
		c.getParams().put("status", "active");
		Customer customerSession = this.crmService.queryCustomerWhenLogin(c);

		System.out.println(customerSession);

		if (customerSession == null) {
			System.out.println("SESSION IS NULL");
			return "redirect:/sign-in";
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

		if ("home".equals(type)) {

			model.addAttribute("success", "Welcome to CyberTech Customer Home.");

			customerSession.getCustomerInvoice().setBalance(this.crmService.queryCustomerInvoicesBalanceByCid(customer_id, "unpaid"));

			model.addAttribute("customerOrders", customerOrders);

			return "broadband-customer/customer-home";
		}

		attr.addFlashAttribute("success", "Welcome to CyberTech Customer Billing.");

		return "redirect:/customer/billing/view";
	}

	@RequestMapping(value = "/customer/home")
	public String customerHome(Model model, HttpSession session) {
		model.addAttribute("home", "active");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		customerSession.getParams().put("id", customerSession.getId());
		session.setAttribute("customerSession", this.customerService.queryCustomer(customerSession));
		return "broadband-customer/customer-home";
	}
	
	@RequestMapping(value = "/customer/orders")
	public String customerOrders(Model model, HttpSession session) {	
		model.addAttribute("orders", "active");
		return "broadband-customer/customer-orders";
	}

	@RequestMapping(value = "/customer/new-order")
	public String newOrder(Model model, HttpSession session) {
		model.addAttribute("neworder", "active");
		return "broadband-customer/customer-new-order";
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
		return "broadband-customer/customer-topup-for-account";
	}
	
	@RequestMapping("/customer/topup-plan/{orderid}")
	public String customerTopupPlan(Model model
			, HttpSession session
			, @PathVariable("orderid") int orderid) {
		model.addAttribute("orders", "active");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		List<CustomerOrder> orders = customerSession.getCustomerOrders();
		if (orders != null && orders.size() > 0) {
    		for (CustomerOrder order : orders) {
    			if (order.getId() == orderid) {
    				customerSession.setCustomerOrder(order);
    				break;
    			}
    		}
    	}
		return "broadband-customer/customer-topup-for-plan";
	}
	
//	@RequestMapping(value = "/customer/topup/checkout", method = RequestMethod.POST)
//	public String topupCheckout(Model model, HttpServletRequest req, RedirectAttributes attr,
//			@RequestParam("prepaymonths") Integer months) {
//		
//		Customer customer = (Customer) req.getSession().getAttribute("customerSession");
//		CustomerOrderDetail cod = customer.getCustomerOrders().get(0).getCustomerOrderDetails().get(0);
//		Double price = cod.getDetail_price();
//		Double total = 0d;
//		
//		if (months == 1) {
//			total = price;
//		} else if (months == 3) {
//			Double temp = price * 3 * 0.03;
//			total = price * 3 - temp.intValue();
//		} else if (months == 6) {
//			Double temp = price * 6 * 0.07;
//			total = price * 6 - temp.intValue();
//		} else if (months == 12) {
//			Double temp = price * 12 * 0.15;
//			total = price * 12 - temp.intValue();
//		} else {
//			total = new Double(months);
//		}
//
//		GenerateRequest gr = new GenerateRequest();
//
//		gr.setAmountInput(new DecimalFormat("#.00").format(total));
//		//gr.setAmountInput("1.00");
//		gr.setCurrencyInput("NZD");
//		gr.setTxnType("Purchase");
//		
//		System.out.println(req.getRequestURL().toString());
//		gr.setUrlFail(req.getRequestURL().toString());
//		gr.setUrlSuccess(req.getRequestURL().toString());
//
//		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
//
//		return "redirect:" + redirectUrl;
//	}
//	
//	@RequestMapping(value = "/customer/topup/checkout")
//	public String toTopupSuccess(Model model,
//			@RequestParam(value = "result", required = true) String result,
//			RedirectAttributes attr, HttpServletRequest request
//			) throws Exception {
//		
//		Customer customer =  (Customer) request.getSession().getAttribute("customerSession");
//
//		Response responseBean = null;
//
//		if (result != null)
//			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
//
//		if (responseBean != null && responseBean.getSuccess().equals("1")) {
//			
//			Customer c = new Customer();
//			customer.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
//			c.setBalance(customer.getBalance());
//			c.getParams().put("id", customer.getId());
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
//			customerTransaction.setTransaction_sort("");
//			
//			customerTransaction.setCustomer_id(customer.getId());
//			customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
//			
//			this.crmService.customerTopup(c, customerTransaction);
//			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
//			
//		} else {
//			
//			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
//		}
//
//		return "redirect:/customer/topup";
//	}
	
	

	@RequestMapping(value = "/signout")
	public String signout(HttpSession session) {
		session.removeAttribute("customerSession");
		return "redirect:/home";
	}
     
	@RequestMapping(value = "/customer/orders/{pdftype}/pdf/download/{id}")
    public ResponseEntity<byte[]> downloadOrderingFormReceiptInvoicePDF(HttpSession session
    		, @PathVariable("pdftype") String pdftype
    		, @PathVariable("id") int id) throws IOException {
    	
    	Customer customerSession =  (Customer) session.getAttribute("customerSession");
    	List<CustomerOrder> orders = customerSession.getCustomerOrders();
    	List<CustomerInvoice> invoices = customerSession.getCustomerInvoices(); //System.out.println("invoices.size(): " + invoices.size());
    	String filePath = "";
    	
    	if ("invoice".equals(pdftype)) {
    		if (invoices != null && invoices.size() > 0) {
        		for (CustomerInvoice invoice : invoices) {
        			if (invoice.getId() == id) {
        				filePath = invoice.getInvoice_pdf_path(); System.out.println(filePath);
        				break;
        			}
        		}
        	}
    	} else {
    		if (orders != null && orders.size() > 0) {
        		for (CustomerOrder order : orders) {
        			if (order.getId() == id) {
        				if ("ordering-form".equals(pdftype)) {
        					filePath = order.getOrdering_form_pdf_path();
        				} else if ("receipt".equals(pdftype)) {
        					filePath = order.getReceipt_pdf_path();
        				}
        				break;
        			}
        		}
        	}
    	}
    	
    	ResponseEntity<byte[]> response = null;
    	
    	if (!"".equals(filePath)) {
    		byte[] contents = Files.readAllBytes(Paths.get(filePath));
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.parseMediaType("application/pdf"));
    		String filename = URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.indexOf(".")) + ".pdf", "UTF-8");
    		headers.setContentDispositionFormData(filename, filename);
    		response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
    	}
    	
		return response;
    }
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// new order topup
	@RequestMapping(value = "/customer/new-order/topup")
	public String customerNewOrderTopup(HttpSession session){
		System.out.println("this is new order customerReg, Topup");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		Customer customerReg = new Customer();
		customerReg.setId(customerSession.getId());
		customerReg.setCellphone(customerSession.getCellphone());
		customerReg.setEmail(customerSession.getEmail());
		customerReg.setNewOrder(true);
		customerReg.setSelect_plan_group("plan-topup");
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerReg", customerReg);
		return "redirect:/plans/address-check";
	}
	
	// new order personal
	@RequestMapping(value = "/customer/new-order/personal")
	public String customerNewOrderPersonal(HttpSession session){
		System.out.println("this is new order customerReg, Personal");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		Customer customerReg = new Customer();
		customerReg.setId(customerSession.getId());
		customerReg.setCellphone(customerSession.getCellphone());
		customerReg.setEmail(customerSession.getEmail());
		customerReg.setNewOrder(true);
		customerReg.setSelect_plan_group("");
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerReg", customerReg);
		return "redirect:/plans/address-check";
	}
		
	// new order business
	@RequestMapping(value = "/customer/new-order/business")
	public String customerNewOrderBusiness(HttpSession session){
		System.out.println("this is new order customerReg, Business");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		Customer customerReg = new Customer();
		customerReg.setId(customerSession.getId());
		customerReg.setCellphone(customerSession.getCellphone());
		customerReg.setEmail(customerSession.getEmail());
		customerReg.setNewOrder(true);
		customerReg.setSelect_plan_group("");
		customerReg.setSelect_customer_type("business");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerReg", customerReg);
		return "redirect:/plans/address-check";
	}
	
	// new order promotion ipadmini
	@RequestMapping(value = "/customer/new-order/promotion/ipadmini")
	public String customerNewOrderPromotioniPadMini(HttpSession session){
		System.out.println("this is new order customerReg, promotion ipadmini");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		Customer customerReg = new Customer();
		customerReg.setId(customerSession.getId());
		customerReg.setCellphone(customerSession.getCellphone());
		customerReg.setEmail(customerSession.getEmail());
		customerReg.setNewOrder(true);
		customerReg.setSelect_plan_group("");
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setSale_id(10023);
		customerReg.getCustomerOrder().setPromotion(true);
		customerReg.setLanguage("en");
		session.setAttribute("customerReg", customerReg);
		return "redirect:/plans/address-check";
	}
		
	// new order promotion hd
	@RequestMapping(value = "/customer/new-order/promotion/hd")
	public String customerNewOrderPromotionHD(HttpSession session){
		System.out.println("this is new order customerReg, promotion hd");
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		Customer customerReg = new Customer();
		customerReg.setId(customerSession.getId());
		customerReg.setCellphone(customerSession.getCellphone());
		customerReg.setEmail(customerSession.getEmail());
		customerReg.setNewOrder(true);
		customerReg.setSelect_plan_group("");
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setSale_id(20023);
		customerReg.getCustomerOrder().setPromotion(true);
		customerReg.setLanguage("en");
		session.setAttribute("customerReg", customerReg);
		return "redirect:/plans/address-check";
	}
	
	
	// 1
	
	@RequestMapping("/plans/define/{type}")
	public String plansDefineType(HttpSession session, @PathVariable("type") String type) {
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		customerReg.setSelect_plan_type(type);
		return "redirect:/plans/order";
	}
	
	// 2
	
	@RequestMapping("/plans/{type}")
	public String broadband(Model model, HttpSession session
			, @PathVariable("type") String type) {
		
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		
		if (customerReg == null) {
			System.out.println("this is new customerReg, 2");
			customerReg = new Customer();
			customerReg.setNewOrder(false);
			customerReg.setSelect_plan_group("");
			customerReg.setSelect_customer_type("personal");
			customerReg.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerReg.getCustomerOrder().setPromotion(false);
			session.setAttribute("customerReg", customerReg);
		} else {
			customerReg.setSelect_plan_group("");
			customerReg.setSelect_customer_type("personal");
			customerReg.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
		}
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		String url = "broadband-customer/plans/broadband";
		
		if ("broadband".equals(type)) {
			model.addAttribute("adsl", "active");
			type = "ADSL";
		} else if ("ultra-fast-vdsl".equals(type)) {
			model.addAttribute("vdsl", "active");
			type = "VDSL";
		} else if ("ultra-fast-fibre".equals(type)) {
			model.addAttribute("ufb", "active");
			type = "UFB";
		}
		
		model.addAttribute("type", type);
		
		String type_search = type;
		
		if (customerReg.getBroadband() != null && !customerReg.getBroadband().getServices_available().contains(type)) {
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
	
	@RequestMapping("/plans/define/{type}/{id}")
	public String plansDefineTypeId(HttpSession session
			, @PathVariable("type") String type
			, @PathVariable("id") int id) {
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		customerReg.setSelect_plan_type(type);
		customerReg.setSelect_plan_id(id);
		return "redirect:/plans/address-check";
	}
	
	// 3
	
	@RequestMapping("/plans/promotions")
	public String plansPromotions() {
		return "broadband-customer/plans/plan-promotion";
	}
	
	@RequestMapping("/plans/promotion") 
	public String plansNewZelandPromotion(Model model, HttpSession session) {
		
		Customer customerReg = new Customer();
		System.out.println("this is new customerReg, 3");
		customerReg.setNewOrder(false);
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setSale_id(10023);
		customerReg.getCustomerOrder().setPromotion(true);
		customerReg.setLanguage("cn");
		session.setAttribute("customerReg", customerReg);
		
		return "redirect:/plans/address-check/0/0";
	}
	
	@RequestMapping("/plans/promotion/en") 
	public String plansNewZelandPromotionEn(Model model, HttpSession session) {
		
		System.out.println("this is new customerReg, 3");
		Customer customerReg = new Customer();
		customerReg.setNewOrder(false);
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setSale_id(10023);
		customerReg.getCustomerOrder().setPromotion(true);
		customerReg.setLanguage("en");
		session.setAttribute("customerReg", customerReg);
		
		return "redirect:/plans/address-check";
	}
	
	@RequestMapping("/plans/promotion/hd/en") 
	public String plansHDPromotionEn(Model model, HttpSession session) {
		
		System.out.println("this is new customerReg, 3");
		Customer customerReg = new Customer();
		customerReg.setNewOrder(false);
		customerReg.setSelect_customer_type("personal");
		customerReg.getCustomerOrder().setOrder_broadband_type("transition");
		customerReg.getCustomerOrder().setSale_id(20023);
		customerReg.getCustomerOrder().setPromotion(true);
		customerReg.setLanguage("en");
		session.setAttribute("customerReg", customerReg);
		
		return "redirect:/plans/address-check";
	}
	
	// ***
	
	@RequestMapping("/plans/address-check") 
	public String toAddressCheck(Model model, HttpSession session) {
		return "broadband-customer/plans/address-check";
	}
	
	// ***
	
	
	@RequestMapping("/plans/order") 
	public String toOrderPlan(Model model, HttpSession session) {
		
		String url = "broadband-customer/plans/customer-order";
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		if (!customerReg.isServiceAvailable()) {
			System.out.println("customerReg.isServiceAvailable(): " + customerReg.isServiceAvailable());
			String type = "broadband";
			if ("ADSL".equals(customerReg.getSelect_plan_type())) {
				type = "broadband";
			} else if ("VDSL".equals(customerReg.getSelect_plan_type())){
				type = "ultra-fast-vdsl";
			} else if ("UFB".equals(customerReg.getSelect_plan_type())) {
				type = "ultra-fast-fibre";
			}
			url = "redirect:/plans/" + type;
		}
		return url;
	}
	
	@RequestMapping("/plans/address/clear") 
	public String addressClear(Model model, HttpSession session) {
		
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		
		String url = "";
		if (customerReg.getNewOrder()) {
			url = "redirect:/customer/new-order";
		} else {
			if ("personal".equals(customerReg.getSelect_customer_type())) {
				if (customerReg.getCustomerOrder().getPromotion()) {
					url = "redirect:/plans/promotions";
				} else if ("plan-topup".equals(customerReg.getSelect_plan_group())) {
					url = "redirect:/plans/plan-topup/personal";
				} else {
					String type = "broadband";
					if ("ADSL".equals(customerReg.getSelect_plan_type())) {
						type = "broadband";
					} else if ("VDSL".equals(customerReg.getSelect_plan_type())){
						type = "ultra-fast-vdsl";
					} else if ("UFB".equals(customerReg.getSelect_plan_type())) {
						type = "ultra-fast-fibre";
					}
					url = "redirect:/plans/" + type;
				}
				
			} else if ("business".equals(customerReg.getSelect_customer_type())) {
				url = "redirect:/plans/plan-term/business";
			}
		}
		
		session.removeAttribute("customerReg");
		
		return url;
	}
	
	// +++
	
	@RequestMapping("/plans/order/summary") 
	public String plansOrderSummary(Model model, HttpSession session) {
		return "broadband-customer/plans/order-summary";
	}
	
	@RequestMapping(value = "/plans/order/bankdeposit", method = RequestMethod.POST)
	public String plansOrderBankDeposit(RedirectAttributes attr, HttpSession session) {
		
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		Customer customerSession = (Customer) session.getAttribute("customerSession"); System.out.println("customerSession: " + customerSession);
		
		String send_email = "", send_mobile = "";
		
		if (customerReg.getNewOrder()) {
			customerReg.setPassword("*********");
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.setStatus("active");
			customerReg.getCustomerOrder().setOrder_status("pending");
			customerReg.setBalance(0d);
			if (customerSession != null) {
				send_email = customerReg.getEmail();
				send_mobile = customerReg.getCellphone();
				customerReg.setEmail(customerSession.getEmail());
				customerReg.setCellphone(customerSession.getCellphone());
			}
		} else {
			customerReg.setPassword(TMUtils.generateRandomString(6));
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.setUser_name(customerReg.getLogin_name());
			customerReg.setStatus("active");
			customerReg.getCustomerOrder().setOrder_status("pending");
			customerReg.setBalance(0d);
			send_email = customerReg.getEmail();
			send_mobile = customerReg.getCellphone();
		}
		
		customerReg.setCompany_name(customerReg.getCustomerOrder().getOrg_name());
		this.crmService.saveCustomerOrder(customerReg, customerReg.getCustomerOrder(), null);
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customerReg);
		
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomerOrder().getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
		MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(send_email);
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customerReg.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomerOrder().getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); 
		MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
		this.smserService.sendSMSByAsynchronousMode(send_mobile, notification.getContent()); 
		
		Response responseBean = new Response();
		responseBean.setSuccess("1");
		attr.addFlashAttribute("responseBean", responseBean);
		
		return "redirect:/plans/order/result";
	}
	
	@RequestMapping(value = "/plans/order/result")
	public String planOrderToOrderResult(HttpSession session) {
		session.removeAttribute("customerReg");
		return "broadband-customer/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/plans/order/result/success")
	public String planOrderResultSuccess(HttpSession session) {
		return "broadband-customer/plans/customer-order-result-success";
	}
	
	@RequestMapping(value = "/plans/order/result/error")
	public String planOrderResultError(HttpSession session) {
		return "broadband-customer/plans/customer-order-result-error";
	}
    
}

package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ResponseMessage;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;
import com.tm.broadband.validator.mark.PlanValidatedMark;

@Controller
@SessionAttributes(value = { "customer", "orderPlan" })
public class CustomerController {

	private PlanService planService;
	private CRMService crmService;

	@Autowired
	public CustomerController(PlanService planService, CRMService crmService) {
		this.planService = planService;
		this.crmService = crmService;
	}

	@RequestMapping("/home")
	public String home(Model model) {

		model.addAttribute("customer", new Customer());
		return "broadband-customer/home";
	}

	@RequestMapping("/plans/{sort}")
	public String plans(Model model, @PathVariable("sort") String sort) {

		/*
		 * List<Plan> plans = null; if ("all".equals(sort)) { plans =
		 * this.planService.queryPlans(); } else { plans =
		 * this.planService.queryPlansByType(sort);
		 * 
		 * if ("adsl".equals(sort)) { model.addAttribute("nonNakedTitle",
		 * "ADSL Plus"); model.addAttribute("nakedTitle", "Naked ADLS"); } else
		 * if ("vdsl".equals(sort)) { model.addAttribute("nonNakedTitle",
		 * "VDSL Plus"); model.addAttribute("nakedTitle", "Naked VDLS"); } else
		 * if ("ufb".equals(sort)) { model.addAttribute("nonNakedTitle",
		 * "UFB Plus"); model.addAttribute("nakedTitle", "Naked UFB"); } }
		 */

		/*
		 * if (plans != null) {
		 * 
		 * List<Plan> nonNakedPlans = new ArrayList<Plan>(); List<Plan>
		 * nakedPlans = new ArrayList<Plan>();
		 * 
		 * for (Plan plan : plans) { if
		 * ("non-naked".equals(plan.getPlan_sort())) { nonNakedPlans.add(plan);
		 * } else if ("naked".equals(plan.getPlan_sort())) {
		 * nakedPlans.add(plan); } }
		 * 
		 * model.addAttribute("nonNakedPlans", nonNakedPlans);
		 * model.addAttribute("nakedPlans", nakedPlans); }
		 */

		String url = "";

		if ("adsl".equals(sort)) {
			url = "broadband-customer/plan-detail-adsl";
		} else if ("vdsl".equals(sort)) {
			url = "broadband-customer/plan-detail-vdsl";
		} else if ("ufb".equals(sort)) {
			url = "broadband-customer/plan-detail-ufb";
		}

		return url;
	}

	@RequestMapping("/order/{id}")
	public String order(Model model, @PathVariable("id") int id) {

		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);

		Customer customer = (Customer) model.asMap().get("customer");
		if (customer == null) {
			model.addAttribute("customer", new Customer());
		}
		return "broadband-customer/customer-order";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String doOrder(
			Model model,
			@ModelAttribute("customer") @Validated(CustomerOrderValidatedMark.class) Customer customer,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "broadband-customer/customer-order";
		}

		int count = this.crmService.queryExistCustomerByLoginName(customer.getLogin_name());

		if (count > 0) {
			result.rejectValue("login_name", "duplicate", "");
			return "broadband-customer/customer-order";
		}

		if (!customer.getPassword().equals(customer.getCk_password())) {
			result.rejectValue("ck_password", "incorrectConfirmPassowrd", "");
			return "broadband-customer/customer-order";
		}

		return "redirect:order/confirm";
	}

	@RequestMapping(value = "/order/confirm")
	public String orderConfirm(Model model) {

		return "broadband-customer/customer-order-confirm";
	}

	@RequestMapping(value = "/order/submit", method = RequestMethod.POST)
	public String orderSubmit(Model model, HttpServletRequest req, RedirectAttributes attr) {
		
		GenerateRequest gr = new GenerateRequest();

		gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		System.out.println(req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
		
		return "redirect:" + redirectUrl;
	}
	
	
	@RequestMapping(value = "/order/submit")
	public String toSignupPayment(Model model, 
			@ModelAttribute("customer") Customer customer,
			@ModelAttribute("orderPlan") Plan plan, RedirectAttributes attr, SessionStatus status,
			@RequestParam(value = "result", required = true) String result) throws Exception {
		
		Response responseBean = null;
		
		if (result != null)
			responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
		
		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			customer.setStatus("active");
			customer.setUser_name(customer.getLogin_name());
			
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

			this.crmService.registerCustomer(customer, plan, customerTransaction);
		} else {
			
		}
		
		model.addAttribute("responseBean", responseBean);
		
		status.isComplete();
		
		return "broadband-customer/customer-order-result";
	}

	/*@RequestMapping(value = "/order/result")
	public String orderResult(Model model, SessionStatus status) {

		status.isComplete();
		return "broadband-customer/customer-order-result";
	}*/

	@RequestMapping(value = "/login")
	public String login(Model model) {
		
		model.addAttribute("customer", new Customer());
		return "broadband-customer/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, 
			@ModelAttribute("customer") @Validated(CustomerLoginValidatedMark.class) Customer customer,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "broadband-customer/login";
		}
		
		customer.setStatus("active");
		Customer customerSession = this.crmService.queryCustomerWhenLogin(customer);
		
		if (customerSession == null) {
			model.addAttribute("error", "Incorrect login name or password");
			return "broadband-customer/login";
		}
		
		req.getSession().setAttribute("customerSession", customerSession);
		attr.addFlashAttribute("success", "Welcome to CyberTech Customer Home.");
		
		return "redirect:/customer/home";
	}
	
	
	@RequestMapping(value = "/customer/home")
	public String customerHome(Model model) {
		model.addAttribute("home", "active");
		return "broadband-customer/customer-home";
	}
	
	@RequestMapping(value = "/customer/data")
	public String customerData(Model model) {
		model.addAttribute("data", "active");
		return "broadband-customer/customer-data";
	}
	
	@RequestMapping(value = "/customer/billing")
	public String customerBilling(Model model) {
		model.addAttribute("billing", "active");
		return "broadband-customer/customer-billing";
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

}

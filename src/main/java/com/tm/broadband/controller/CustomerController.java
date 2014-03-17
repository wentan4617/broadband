package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.tm.broadband.model.CustomerOrderDetail;
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
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes(value = { "customer", "orderPlan", "hardwares" })
public class CustomerController {

	private PlanService planService;
	private CRMService crmService;
	private MailerService mailerService;

	@Autowired
	public CustomerController(PlanService planService, CRMService crmService
			,MailerService mailerService) {
		this.planService = planService;
		this.crmService = crmService;
		this.mailerService = mailerService;
	}

	@RequestMapping("/home")
	public String home(Model model) {
		return "broadband-customer/home";
	}

	@RequestMapping("/plans/{group}")
	public String plans(Model model, @PathVariable("group") String group) {
		
		List<Plan> plans = null;
		String url = "";
		
		if ("t".equals(group)) {
			
			Plan plan = new Plan();
			plan.getParams().put("plan_group", "plan-topup");
			plan.getParams().put("plan_status", "selling");
			plan.getParams().put("plan_sort", "naked");
			
			plans = this.planService.queryPlansBySome(plan);
			
			// key = plan_type
			Map<String, Plan> planMaps = new HashMap<String, Plan>();
			
			if (plans != null) {
				for (Plan p: plans) {
					planMaps.put(p.getPlan_type(), p);
				}
			}
			
			model.addAttribute("planMaps", planMaps);
						
			url = "broadband-customer/plan-detail-topup";
			
		} else if ("p".equals(group)) {
			
			Plan plan = new Plan();
			plan.getParams().put("plan_group", "plan-no-term");
			plan.getParams().put("plan_status", "selling");
			plan.getParams().put("plan_sort", "naked");
			plan.getParams().put("orderby", "order by data_flow");
			plans = this.planService.queryPlansBySome(plan);
			
			// key = plan_type
			Map<String, List<Plan>> planMaps = new HashMap<String, List<Plan>>();
			
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
			
			url = "broadband-customer/plan-detail-no-term";
		}

		return url;
	}

	@RequestMapping("/order/{id}")
	public String orderPlanNoTerm(Model model, 
			@PathVariable("id") int id) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("new-connection");
		model.addAttribute("customer", customer);

		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwaresBySome(hardware);
		model.addAttribute("hardwares", hardwares);
		
		return "broadband-customer/customer-order";
	}
	
	@RequestMapping("/order/{id}/topup/{amount}")
	public String orderPlanTopup(Model model, 
			@PathVariable("id") int id,
			@PathVariable("amount") Double amount) {
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("new-connection");
		model.addAttribute("customer", customer);

		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		plan.getTopup().setTopup_fee(amount);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwaresBySome(hardware);
		model.addAttribute("hardwares", hardwares);
		
		return "broadband-customer/customer-order";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String doOrder(
			Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer,
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
	public String orderConfirm(Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			@ModelAttribute("hardwares") List<Hardware> hardwares, 
			RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "broadband-customer/customer-order";
		}
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().setOrder_create_date(new Date());

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		cod_plan.setDetail_name(plan.getPlan_name());
		cod_plan.setDetail_price(plan.getPlan_price());
		cod_plan.setDetail_unit(plan.getPlan_prepay_months());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(cod_plan);
		
		if ("plan-topup".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getTopup().getTopup_fee());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Broadband New Connection");
				cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
				cod_conn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);
				
			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getTopup().getTopup_fee());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
			}
			
			CustomerOrderDetail cod_topup = new CustomerOrderDetail();
			cod_topup.setDetail_name("Broadband Top-Up");
			cod_topup.setDetail_price(plan.getTopup().getTopup_fee());
			cod_topup.setDetail_unit(1);
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_topup);
			
		} else if ("plan-no-term".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_new_connection_fee() + plan.getPlan_price() * plan.getPlan_prepay_months());
			
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Broadband New Connection");
				cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
				cod_conn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);
				
			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
			}
			
		} else if ("plan-term".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {

			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {

			}
		}
		
		
		if (hardwares != null) {
			for (Hardware chd: customer.getCustomerOrder().getHardwares()) {
				//System.out.println(chd.getId());
				for (Hardware hd : hardwares) {
					if (chd.getId() == hd.getId()) {
						CustomerOrderDetail cod_hd = new CustomerOrderDetail();
						cod_hd.setDetail_name(hd.getHardware_name());
						cod_hd.setDetail_price(hd.getHardware_price());
						cod_hd.setDetail_unit(1);
						customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
						customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + hd.getHardware_price());
						break;
					}
				}
			}
		}
		
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
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult error,
			@ModelAttribute("orderPlan") Plan plan, RedirectAttributes attr,
			@ModelAttribute("hardwares") List<Hardware> hardwares, 
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
			customer.setUser_name(customer.getLogin_name());
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

			this.crmService.registerCustomer(customer, plan, hardwares, customerTransaction);
			
			this.crmService.createInvoicePDFByInvoiceID(customerTransaction.getInvoice_id());

			String filePath = TMUtils.createPath(
					"broadband"
					+File.separator+"customers"
					+File.separator+customer.getId()
					+File.separator+"Invoice - #"+customerTransaction.getInvoice_id()+".pdf");
			
			Notification notification = this.crmService.queryNotificationBySort("register");
			ApplicationEmail applicationEmail = new ApplicationEmail();
			CompanyDetail companyDetail = new CompanyDetail();
			// call mail at value retriever
			TMUtils.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
			applicationEmail.setAddressee(customer.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			// binding attachment name & path to email
			applicationEmail.setAttachName("Invoice - #" + customerTransaction.getInvoice_id() + ".pdf");
			applicationEmail.setAttachPath(filePath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
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
		
		model.addAttribute("page",invoicePage);
		model.addAttribute("transactionsList",this.crmService.queryCustomerTransactionsByCustomerId(customer.getId()));
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
		
		model.addAttribute("page",invoicePage);
		return "broadband-customer/customer-discard-billing";
	}
	
	@RequestMapping("/customer/topup")
	public String customerTopup(Model model) {
		model.addAttribute("home", "active");
		return "broadband-customer/customer-payment-topup";
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

}

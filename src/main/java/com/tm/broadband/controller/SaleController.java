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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.pdf.ApplicationPDFCreator;
import com.tm.broadband.pdf.CreditPDFCreator;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SaleService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.test.Console;
import com.tm.broadband.validator.mark.CustomerCreditValidatedMark;

@Controller
@SessionAttributes(value = {"orderCustomer", "orderCustomerOrderDetails", "orderPlan"})
public class SaleController {
	
	private PlanService planService;
	private CRMService crmService;
	private SaleService saleService;
	private MailerService mailerService;
	private SmserService smserService;
	private SystemService systemService;

	@Autowired
	public SaleController(PlanService planService, CRMService crmService, MailerService mailerService, SystemService systemService, SmserService smserService
			, SaleService saleService) {
		this.planService = planService;
		this.crmService = crmService;
		this.saleService = saleService;
		this.mailerService = mailerService;
		this.smserService = smserService;
		this.systemService = systemService;
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/plans/{class}")
	public String plans(Model model,
			@PathVariable("class") String classz) {
		
		model.addAttribute("classz", classz);
		
		Customer customer = new Customer();
		customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
		model.addAttribute("orderCustomer", customer);
		
		List<Plan> plans = null;
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", "plan-term");
		plan.getParams().put("plan_class", classz);
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("orderby", "order by data_flow");
		
		plans = this.planService.queryPlans(plan);
		
		model.addAttribute("plans", plans);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		model.addAttribute("hardwares", hardwares);
		
		// Recycle
		customer = null;
		plans = null;
		plan = null;
		hardware = null;
		hardwares = null;
		
		return "broadband-user/sale/online-ordering";
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/order/{id}")
	public String orderPlanTerm(Model model, @PathVariable("id") int id) {
		
		Plan plan = new Plan();
		plan.getParams().put("id", id);
		plan = this.planService.queryPlan(plan);
		model.addAttribute("orderPlan", plan);
		
		StringBuffer url = new StringBuffer();
		if ("personal".equals(plan.getPlan_class())) {
			url.append("broadband-user/sale/online-ordering-personal-info");
		} else if ("business".equals(plan.getPlan_class())) {
			url.append("broadband-user/sale/online-ordering-business-info");
		}
		
		// Recycle
		plan = null;
		
		return url.toString();
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm")
	public String orderConfirm(Model model,
			@ModelAttribute("orderPlan") Plan plan, 
			@ModelAttribute("orderCustomer") Customer customer, 
			@ModelAttribute("orderCustomerOrderDetails") List<CustomerOrderDetail> cods, 
			HttpServletRequest req,
			RedirectAttributes attr) {
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().getCustomerOrderDetails().addAll(cods);
		customer.getCustomerOrder().setOrder_create_date(new Date());
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		
		cod_plan.setDetail_name(plan.getPlan_name());
		//cod_plan.setDetail_desc(plan.getPlan_desc());
		cod_plan.setDetail_price(plan.getPlan_price() == null ? 0d : plan.getPlan_price());
		cod_plan.setDetail_data_flow(plan.getData_flow());
		cod_plan.setDetail_plan_status(plan.getPlan_status());
		cod_plan.setDetail_plan_type(plan.getPlan_type());
		cod_plan.setDetail_plan_sort(plan.getPlan_sort());
		cod_plan.setDetail_plan_group(plan.getPlan_group());
		cod_plan.setDetail_plan_class(plan.getPlan_class());
		cod_plan.setDetail_plan_new_connection_fee(plan.getPlan_new_connection_fee());
		cod_plan.setDetail_term_period(plan.getTerm_period());
		customer.getCustomerOrder().setTerm_period(plan.getTerm_period());
		cod_plan.setDetail_plan_memo(plan.getMemo());
		cod_plan.setDetail_unit(plan.getPlan_prepay_months() == null ? 1 : plan.getPlan_prepay_months());
		cod_plan.setDetail_type(plan.getPlan_group());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(0, cod_plan);
		
		if ("plan-term".equals(plan.getPlan_group())) {
			
			customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
			
			if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getTransition_fee());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_price(plan.getTransition_fee());
				//cod_trans.setDetail_is_next_pay(1);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
				
				// Recycle
				cod_trans = null;
				
			} else if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getPlan_new_connection_fee());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Broadband New Connection");
				cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
				//cod_conn.setDetail_is_next_pay(1);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);
				
				// Recycle
				cod_conn = null;

			} else if ("jackpot".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + plan.getJackpot_fee());
				
				CustomerOrderDetail cod_jackpot = new CustomerOrderDetail();
				cod_jackpot.setDetail_name("Broadband New Connection & Jackpot Installation");
				cod_jackpot.setDetail_price(plan.getJackpot_fee());
				//cod_jackpot.setDetail_is_next_pay(1);
				cod_jackpot.setDetail_type("jackpot");
				cod_jackpot.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_jackpot);
				
				// Recycle
				cod_jackpot = null;
				
			}
			
			// add plan free pstn
			for (int i = 0; i < plan.getPstn_count(); i++) {
				CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
				if ("personal".equals(plan.getPlan_class())) {
					cod_pstn.setDetail_name("Home Phone Line");
				} else if ("business".equals(plan.getPlan_class())) {
					cod_pstn.setDetail_name("Business Phone Line");
				}
				cod_pstn.setDetail_price(0d);
				//cod_pstn.setDetail_is_next_pay(0);
				//cod_pstn.setDetail_expired(new Date());
				cod_pstn.setDetail_type("pstn");
				cod_pstn.setDetail_unit(1);
				cod_pstn.setPstn_number(customer.getCustomerOrder().getTransition_porting_number());
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_pstn);
				
				// Recycle
				cod_pstn = null;
			}
			
			CustomerOrderDetail cod_hd = new CustomerOrderDetail();
			if ("ADSL".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N ADSL2+ Modem Router(Free)");
			} else if ("VDSL".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("TP - LINK 150Mbps Wireless N VDSL2+ Modem Router(Free)");
			} else if ("UFB".equals(plan.getPlan_type())) {
				cod_hd.setDetail_name("UFB Modem Router(Free)");
			}
			cod_hd.setDetail_price(0d);
			//cod_hd.setDetail_is_next_pay(0);
			//cod_hd.setDetail_expired(new Date());
			cod_hd.setDetail_unit(1);
			cod_hd.setIs_post(0);
			cod_hd.setDetail_type("hardware-router");
			
			customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hd);
			
			// Recycle
			cod_hd = null;
		}
		
		if (cods != null) {
			for (CustomerOrderDetail cod : customer.getCustomerOrder().getCustomerOrderDetails()) {
				if ("hardware-router".equals(cod.getDetail_type())) {
					//cod.setDetail_is_next_pay(0);
					cod.setIs_post(0);
					customer.getCustomerOrder().setHardware_post(customer.getCustomerOrder().getHardware_post() == null ? 1 : customer.getCustomerOrder().getHardware_post() + 1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				} else if ("pstn".equals(cod.getDetail_type()) 
						|| "voip".equals(cod.getDetail_type())){
					cod.setDetail_unit(1);
					//cod.setDetail_is_next_pay(1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				}
			}
		}
		
		// Recycle
		cod_plan = null;
		
		// BEGIN TO CREDIT INFO

		// BEGIN CREDIT CARD YEAR
		List<String> years = new ArrayList<String>();
		for (int i = 14; i <= 99; i++) {
			if(i<10){
				years.add(String.format("%02d",i));
			} else {
				years.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD YEAR

		// BEGIN CREDIT CARD MONTH
		List<String> months = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			if(i<10){
				months.add(String.format("%02d",i));
			} else {
				months.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD MONTH

		model.addAttribute("months", months);
		model.addAttribute("years", years);
		
		// Recycle
		years = null;
		months = null;

		// END TO CREDIT INFO
		
		return "broadband-user/sale/online-ordering-confirm";
	}
	
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm/save", method = RequestMethod.POST)
	public String orderSave(Model model, 
			@ModelAttribute("orderPlan") Plan plan, 
			@ModelAttribute("orderCustomer") Customer customer, 
			@RequestParam("card_type") String card_type,
			@RequestParam("holder_name") String holder_name,
			@RequestParam("card_number") String card_number,
			@RequestParam("security_code") String security_code,
			@RequestParam("expiry_month") String expiry_month,
			@RequestParam("expiry_year") String expiry_year,
			@RequestParam("optional_request") String optional_request,
			RedirectAttributes attr, SessionStatus status, HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");
		Integer sale_id = 0;
		if("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())){
			sale_id = user.getId();
		}
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));
		customer.getCustomerOrder().setSale_id(customer.getId());
		customer.getCustomerOrder().setSignature("unsigned");
		
		customer.getCustomerOrder().setSale_id(user.getId());
		customer.getCustomerOrder().setOptional_request(optional_request);
		
		this.crmService.registerCustomerCalling(customer);
		
		ApplicationPDFCreator appPDFCreator = new ApplicationPDFCreator();
		appPDFCreator.setCustomer(customer);
		appPDFCreator.setOrg(customer.getOrganization());
		appPDFCreator.setCustomerOrder(customer.getCustomerOrder());
		
		String applicationPDFPath = null;
		try {
			applicationPDFPath = appPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", customer.getCustomerOrder().getId());
		co.setOrder_pdf_path(applicationPDFPath);
		this.crmService.editCustomerOrder(co);
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); // get sms register template from db
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
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
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
		
		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
		String orderPDFPath = null;
		try {
			orderPDFPath = new ApplicationPDFCreator(customer, customer.getCustomerOrder(), customer.getOrganization()).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", customer.getCustomerOrder().getId());
		co.setOrder_pdf_path(orderPDFPath);
		
		this.crmService.editCustomerOrder(co);
		// END SET NECESSARY INFO AND GENERATE ORDER PDF

		//CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		notification = this.systemService.queryNotificationBySort("online-ordering", "email");
		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail); // call mail at value retriever
		applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("online-ordering", "sms"); // get sms register template from db
		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
*/		
		status.setComplete();
		
		user = null;
		notification = null;
		applicationEmail = null;
		orderingPath = null;
		companyDetail = null;
		/*org = null;
		co = null;*/
		
		
		// BEGIN Credit Card
		
		if(!"".equals(holder_name) && !"".equals(card_number) && !"".equals(security_code)){
			CustomerCredit cc = new CustomerCredit();
			cc.setCard_type(card_type);
			cc.setHolder_name(holder_name);
			cc.setCard_number(card_number);
			cc.setSecurity_code(security_code);
			cc.setExpiry_date(
					"20"+expiry_year
					+"-"+expiry_month);
			co = null;
			co = new CustomerOrder();
			co.setId(customer.getCustomerOrder().getId());
			this.saleService.createCustomerCredit(cc);

			// BEGIN CREDIT PDF
			CreditPDFCreator cPDFCreator = new CreditPDFCreator();
			cc.setCustomer(this.crmService.queryCustomerById(customer.getId()));
			cPDFCreator.setCc(cc);
			cPDFCreator.setCo(co);
			cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer.getId()));
			String creditPDFPath = null;
			try {
				creditPDFPath = cPDFCreator.create();
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			co.getParams().put("id", co.getId());
			co.setCredit_pdf_path(creditPDFPath);
			this.crmService.editCustomerOrder(co);
			cPDFCreator = null;
			creditPDFPath = null;
			// END CREDIT PDF
			
			
			// Recycle
			co = null;
		}

		// BEGIN Credit Card
		
		return "redirect:/broadband-user/sale/online/ordering/view/by/"+sale_id;
	}
	
	@RequestMapping(value = "/broadband-user/sales/online-ordering/redirect")
	public String toOnlineOrdering(Model model){
		return "redirect:/broadband-user/sale/online/ordering/plans/business";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/{customer_id}/{order_id}")
	public String toCredit(Model model, @PathVariable("customer_id") Integer customer_id
			, @PathVariable("order_id") Integer order_id) {
		
		model.addAttribute("customer_id", customer_id);
		model.addAttribute("order_id", order_id);
		model.addAttribute("customerCredit", new CustomerCredit());

		// BEGIN CREDIT CARD YEAR
		List<String> years = new ArrayList<String>();
		for (int i = 14; i <= 99; i++) {
			if(i<10){
				years.add(String.format("%02d",i));
			} else {
				years.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD YEAR

		// BEGIN CREDIT CARD MONTH
		List<String> months = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			if(i<10){
				months.add(String.format("%02d",i));
			} else {
				months.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD MONTH

		model.addAttribute("months", months);
		model.addAttribute("years", years);
		
		// Recycle
		years = null;
		months = null;
		
		return "broadband-user/sale/online-ordering-credit";
	}
	
	// DOWNLOAD ORDER PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/pdf/download/{order_id}")
    public ResponseEntity<byte[]> downloadOrderPDF(Model model
    		,@PathVariable(value = "order_id") int order_id) throws IOException {
		String filePath = this.saleService.queryCustomerOrderFilePathById(order_id);
		
		System.out.println("order_id: "+order_id);
		System.out.println("filePath: "+filePath);
		
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
        
        // Recycle
        path = null;
        contents = null;
        headers = null;
        
        return response;
    }
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/create", method = RequestMethod.POST)
	public String doCredit(Model model,
			@ModelAttribute("customerCredit") @Validated(CustomerCreditValidatedMark.class) CustomerCredit customerCredit
			,@RequestParam("customer_id") Integer customer_id
			,@RequestParam("order_id") Integer order_id
			,BindingResult result
			,RedirectAttributes attr
			,HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");
		Integer sale_id = 0;
		if("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())){
			sale_id = user.getId();
		}
		
		customerCredit.setExpiry_date(
				"20"+customerCredit.getExpiry_year()
				+"-"+customerCredit.getExpiry_month());
		
		model.addAttribute("panelheading", "Customer Credit Card Information");
		model.addAttribute("action", "/broadband-user/sale/online/ordering/order/credit/create");

		if (result.hasErrors()) {
			return "broadband-user/sale/online-ordering-credit";
		}
		
		CustomerOrder co = new CustomerOrder();
		co.setId(order_id);
		this.saleService.createCustomerCredit(customerCredit);

		// BEGIN CREDIT PDF
		CreditPDFCreator cPDFCreator = new CreditPDFCreator();
		customerCredit.setCustomer(this.crmService.queryCustomerById(customer_id));
		cPDFCreator.setCc(customerCredit);
		cPDFCreator.setCo(co);
		cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer_id));
		String creditPDFPath = null;
		try {
			creditPDFPath = cPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		co.getParams().put("id", order_id);
		co.setCredit_pdf_path(creditPDFPath);
		this.crmService.editCustomerOrder(co);
		// END CREDIT PDF
		
		// Recycle
		co = null;
		cPDFCreator = null;
		creditPDFPath = null;
		

		return "redirect:/broadband-user/sale/online/ordering/view/by/"+sale_id;
	}

	@RequestMapping(value = "/broadband-user/sale/online-ordering-upload-result/{customer_id}/{order_id}")
	public String toOnlineOrderingResult(Model model,
			@PathVariable("customer_id") Integer customer_id,
			@PathVariable("order_id") Integer order_id){
		model.addAttribute("customer_id",customer_id);
		model.addAttribute("order_id",order_id);
		return "/broadband-user/sale/online-ordering-upload-result";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online-ordering-result/{order_id}/{customer_id}")
	public String toCreditResult(Model model
			, @PathVariable("order_id") Integer order_id
			, @PathVariable("customer_id") Integer customer_id
			,HttpServletRequest req) {
		
		CustomerOrder co = new CustomerOrder();
		co.setId(order_id);
		model.addAttribute("customerOrder", co);

		User user = (User) req.getSession().getAttribute("userSession");
		Integer userId = user.getId();
		
		// Recycle
		co = null;
		user = null;
		
		return "redirect:/broadband-user/sale/online/ordering/view/"+1+"/"+userId;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/upload")
	public String uploadSignedPDF(Model model
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("order_pdf_path") MultipartFile order_pdf_path
			, @RequestParam("credit_pdf_path") MultipartFile credit_pdf_path
			, HttpServletRequest req) {

		User user = (User) req.getSession().getAttribute("userSession");
		Integer userId = user.getId();
		if(!order_pdf_path.isEmpty() && !credit_pdf_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "application_" + order_id
					+ ".pdf");
			String credit_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "credit_" + order_id
					+ ".pdf");
			try {
				order_pdf_path.transferTo(new File(order_path));
				credit_pdf_path.transferTo(new File(credit_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.setOrder_pdf_path(order_path);
			co.setCredit_pdf_path(credit_path);
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
			
			// Recycle
			order_path = null;
			credit_path = null;
			co = null;
		}
		
		// Recycle
		user = null;
		
		return "redirect:/broadband-user/sale/online/ordering/view/1/" + userId;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/upload-single")
	public String uploadSinglePDF(Model model
			, @RequestParam("sale_id") Integer sale_id
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("order_pdf_path") MultipartFile order_pdf_path
			, @RequestParam("credit_pdf_path") MultipartFile credit_pdf_path
			,HttpServletRequest req) {

		
		if(!order_pdf_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "application_" + order_id
					+ ".pdf");
			try {
				order_pdf_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.setOrder_pdf_path(order_path);
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
			
			// Recycle
			order_path = null;
			co = null;
		}
		if(!credit_pdf_path.isEmpty()){
			String credit_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "credit_" + order_id
					+ ".pdf");
			try {
				credit_pdf_path.transferTo(new File(credit_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.setCredit_pdf_path(credit_path);
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
			
			// Recycle
			credit_path = null;
			co = null;
		}
		
		return "redirect:/broadband-user/sale/online/ordering/view/1/" + sale_id;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/upload_previous_provider_invoice_pdf")
	public String uploadPreviousProviderInvoicePDF(Model model
			, @RequestParam("sale_id") Integer sale_id
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("previous_provider_invoice_path") MultipartFile previous_provider_invoice_path
			,HttpServletRequest req) {

		
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
			
			CustomerOrder co = new CustomerOrder();
			co.setPrevious_provider_invoice(order_path);
			co.getParams().put("id", order_id);
			this.crmService.editCustomerOrder(co);
			
			// Recycle
			order_path = null;
			co = null;
		}
		
		return "redirect:/broadband-user/sale/online/ordering/view/1/" + sale_id;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/{pageNo}/{sale_id}")
	public String onlineOrderView(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("sale_id") int sale_id
			,HttpServletRequest req) {

		model.addAttribute("bothActive", "active");

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by order_create_date desc");
		page.getParams().put("where", "query_sale_id_not_null");

		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		pageSignatureSum.getParams().put("orderby", "order by order_create_date desc");
		pageSignatureSum.getParams().put("where", "query_sale_id_not_null");

		User user = (User) req.getSession().getAttribute("userSession");
		if(user.getUser_role().equals("sales") || user.getUser_role().equals("agent")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		List<User> salesUsers = this.saleService.queryUsersWhoseIdExistInOrder();
		
		model.addAttribute("page", page);
		model.addAttribute("salesUsers", salesUsers);
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("bothSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		// Recycle
		page = null;
		salesUsers = null;
		pageSignatureSum = null;
		
		return "broadband-user/sale/online-order-view";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/by/{sale_id}")
	public String onlineOrderViewBySalesId(Model model
			,HttpServletRequest req
			,@PathVariable("sale_id") Integer sale_id) {

		model.addAttribute("bothActive", "active");

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(1);
		page.getParams().put("orderby", "order by order_create_date desc");
		page.getParams().put("where", "query_sale_id_not_null");


		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		pageSignatureSum.getParams().put("where", "query_sale_id_not_null");

		User user = (User) req.getSession().getAttribute("userSession");
		if(user.getUser_role().equals("sales")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("salesUsers", this.saleService.queryUsersWhoseIdExistInOrder());
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("bothSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		//Recycle
		page = null;
		pageSignatureSum = null;
		
		return "broadband-user/sale/online-order-view";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/by/{sale_id}/{signature}")
	public String onlineOrderViewBySignature(Model model
			,HttpServletRequest req
			, @PathVariable("signature") String signature
			, @PathVariable("sale_id") int sale_id) {
		
		if(signature.equals("signed")){
			model.addAttribute("signedActive", "active");
		} else if(signature.equals("unsigned")){
			model.addAttribute("unsignedActive", "active");
		} else if(signature.equals("both")){
			model.addAttribute("bothActive", "active");
		}

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(1);
		if(!signature.equals("both")){
			page.getParams().put("signature", signature);
		}
		page.getParams().put("orderby", "order by order_create_date desc");
		page.getParams().put("where", "query_sale_id_not_null");
		User user = (User) req.getSession().getAttribute("userSession");

		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		pageSignatureSum.getParams().put("where", "query_sale_id_not_null");
		
		if(user.getUser_role().equals("sales")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("salesUsers", this.saleService.queryUsersWhoseIdExistInOrder());
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("bothSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		// Recycle
		page = null;
		pageSignatureSum = null;
		
		return "broadband-user/sale/online-order-view";
	}
	
	// BEGIN InvoiceView
	@RequestMapping("/broadband-user/agent/billing/invoice/view/{pageNo}/{status}")
	public String toInvoice(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status,
			HttpServletRequest req) {
		
		User user = (User) req.getSession().getAttribute("userSession");

		Page<CustomerInvoice> pageCis = new Page<CustomerInvoice>();
		pageCis.getParams().put("status", status);
		pageCis.getParams().put("sale_id", user.getId());
		pageCis.setPageNo(pageNo);
		pageCis.setPageSize(30);
		pageCis.getParams().put("orderby", "ORDER BY create_date DESC");
		
		if("orderNoInvoice".equals(status)){
			Page<CustomerOrder> pageCos = new Page<CustomerOrder>();
			pageCos.setPageNo(pageNo);
			pageCos.setPageSize(30);
			pageCos.getParams().put("where", "query_no_invoice");
			pageCos.getParams().put("orderby", "ORDER BY order_create_date DESC");
			pageCos.getParams().put("sale_id", user.getId());
			model.addAttribute("pageCos", this.crmService.queryCustomerOrdersByPage(pageCos));
			
			pageCos = null;
		} else if("unpaid".equals(status)){
			pageCis.getParams().put("where", "non_pending");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("not_pay_off".equals(status)){
			pageCis.getParams().put("where", "non_pending");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("pending".equals(status)) {
			pageCis = new Page<CustomerInvoice>();
			pageCis.getParams().put("where", "pending");
			pageCis.getParams().put("sale_id", user.getId());
			pageCis.getParams().put("payment_status", status);
			pageCis.getParams().put("status1", "unpaid");
			pageCis.getParams().put("status2", "not_pay_off");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("void".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("paid".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
		}
		pageCis = null;
		
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");
		model.addAttribute("users", this.systemService.queryUser(new User()));
		

		// BEGIN QUERY SUM BY STATUS
		Page<CustomerInvoice> pageStatusSum = new Page<CustomerInvoice>();
		pageStatusSum.getParams().put("where", "pending");
		pageStatusSum.getParams().put("sale_id", user.getId());
		pageStatusSum.getParams().put("payment_status", "pending");
		pageStatusSum.getParams().put("status1", "unpaid");
		pageStatusSum.getParams().put("status2", "not_pay_off");
		model.addAttribute("pendingSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum = null;
		
		pageStatusSum = new Page<CustomerInvoice>();
		pageStatusSum.getParams().put("sale_id", user.getId());
		pageStatusSum.getParams().put("status", "paid");
		model.addAttribute("paidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		
		pageStatusSum.getParams().put("status", "void");
		model.addAttribute("voidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));

		pageStatusSum.getParams().put("where", "non_pending");
		pageStatusSum.getParams().put("status", "unpaid");
		model.addAttribute("unpaidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		
		pageStatusSum.getParams().put("status", "not_pay_off");
		model.addAttribute("notPayOffSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum = null;
		
		Page<CustomerOrder> pageStatusCoSum = new Page<CustomerOrder>();
		pageStatusCoSum.getParams().put("sale_id", user.getId());
		pageStatusCoSum.getParams().put("where", "query_no_invoice");
		model.addAttribute("orderNoInvoiceSum", this.crmService.queryCustomerOrdersSumByPage(pageStatusCoSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/sale/invoice-view";
	}

	// Update optional request
	@RequestMapping(value = "/broadband-user/sale/online/ordering/optional_request/edit", method = RequestMethod.POST)
	public String doCustomerOrderOptionalRequestEdit(Model model,
			@RequestParam("sale_id") Integer sale_id,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("optional_request") String optional_request,
			RedirectAttributes attr) {


		if (!"".equals(optional_request.trim())) {
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", order_id);
			co.setOptional_request(optional_request);
			this.crmService.editCustomerOrder(co);
			attr.addFlashAttribute("success", "Request Record had just been edited!");
		} else {
			attr.addFlashAttribute("error", "Please input correct Request Record!");
		}

		return "redirect:/broadband-user/sale/online/ordering/view/1/" + sale_id;
	}

	// Update optional request
	@RequestMapping(value = "/broadband-user/sale/online/ordering/status/edit", method = RequestMethod.POST)
	public String doCustomerOrderStatusEdit(Model model,
			@RequestParam("order_id") Integer order_id,
			@RequestParam("sale_id") Integer sale_id,
			@RequestParam("status") String status,
			@RequestParam("old_status") String old_status,
			RedirectAttributes attr,
			HttpServletRequest req) {

		ProvisionLog pl = new ProvisionLog();
		User user = (User) req.getSession().getAttribute("userSession");
		pl.setUser_id(user.getId());
		pl.setProcess_datetime(new Date());
		pl.setOrder_sort("customer-order");
		pl.setOrder_id_customer(order_id);
		pl.setProcess_way(old_status+" to "+status);
		
		CustomerOrder co = new CustomerOrder();
		co.setOrder_status(status);
		co.getParams().put("id", order_id);
		
		this.crmService.editCustomerOrder(co, pl);
		
		attr.addFlashAttribute("success", "Order Has Successfully Been Voided!");

		return "redirect:/broadband-user/sale/online/ordering/view/1/" + sale_id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/broadband-user/sale/plans/{type}/{clasz}")
	public String broadband(Model model,
			@PathVariable("type") String type, 
			@PathVariable("clasz") String clasz, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerRegSale");
		
		if (customer == null) {
			customer = new Customer();
			customer.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			session.setAttribute("customerRegSale", customer);
		}
		
		List<Plan> plans = null;
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		String url = "";
		
		url = "broadband-user/sale/plans/broadband";
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
		model.addAttribute("clasz", clasz);
		
		String type_search = type;
		
		if (customer.getBroadband() != null && !customer.getBroadband().getServices_available().contains(type)) {
			type_search = "ADSL";
		}
		
		model.addAttribute("type_search", type_search);
		
		Plan plan = new Plan();
		plan.getParams().put("plan_type", type_search);
		plan.getParams().put("plan_class", clasz);
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("plan_group_false", "plan-topup");
		plan.getParams().put("orderby", "order by plan_price");
		
		plans = this.planService.queryPlans(plan);
		
		this.wiredPlanMapBySort(planTypeMap, plans);
		
		model.addAttribute("planTypeMap", planTypeMap);
		
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
	
	@RequestMapping("/broadband-user/sale/plans/address-check/{type}/{clasz}/{id}") 
	public String toAddressCheck(Model model,
			@PathVariable("type") String type,
			@PathVariable("clasz") String clasz,
			@PathVariable("id") int id,
			HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerRegSale");
		if (customer == null) {
			customer = new Customer();
			customer.setSelect_plan_id(id);
			customer.setSelect_plan_type(type);
			customer.setSelect_customer_type(clasz);
			session.setAttribute("customerRegSale", customer);
		}
		
		
		model.addAttribute("select_plan_id", id);
		model.addAttribute("select_plan_type", type);
		model.addAttribute("select_customer_type", clasz);
		return "broadband-user/sale/plans/address-check";
	}
	
	
	@RequestMapping("/broadband-user/sale/plans/order") 
	public String toOrderPlan(Model model, HttpSession session,
			@RequestParam(value = "select_plan_type", required = false) String select_plan_type,
			@RequestParam(value = "select_customer_type", required = false) String select_customer_type) {
		
		String url = "broadband-user/sale/plans/customer-order";
		
		Customer customer = (Customer) session.getAttribute("customerRegSale");
		
		if (select_customer_type != null) {
			customer.setSelect_customer_type(select_customer_type);
		}
		
		if (select_plan_type != null && !"".equals(select_plan_type)) {
			customer.setSelect_plan_type(select_plan_type);
		}
		if ("0".equals(customer.getSelect_plan_type())) {
			customer.setSelect_plan_type("VDSL");
		}
		if (!customer.isServiceAvailable()) {
			String type = "broadband";
			if ("ADSL".equals(customer.getSelect_plan_type())) {
				type = "broadband";
			} else if ("VDSL".equals(customer.getSelect_plan_type())){
				type = "ultra-fast-vdsl";
			} else if ("UFB".equals(customer.getSelect_plan_type())) {
				type = "ultra-fast-fibre";
			}
			url = "redirect:/broadband-user/sales/plans/" + type + "/" + customer.getSelect_customer_type();
		}
		
		return url;
	}
	
	@RequestMapping("/broadband-user/sale/plans/address/clear") 
	public String addressClear(Model model, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("customerRegSale");
		String type = "broadband", url = "";
		if ("ADSL".equals(customer.getSelect_plan_type())) {
			type = "broadband";
		} else if ("VDSL".equals(customer.getSelect_plan_type())){
			type = "ultra-fast-vdsl";
		} else if ("UFB".equals(customer.getSelect_plan_type())) {
			type = "ultra-fast-fibre";
		}
		url = "redirect:/broadband-user/sale/plans/" + type + "/" + customer.getSelect_customer_type();
		session.removeAttribute("customerRegSale");
		
		return url;
	}
	
	@RequestMapping("/broadband-user/sale/plans/order/summary") 
	public String plansOrderSummary(Model model, HttpSession session) {
		
		// BEGIN CREDIT CARD YEAR
		List<String> years = new ArrayList<String>();
		for (int i = 14; i <= 99; i++) {
			if(i<10){
				years.add(String.format("%02d",i));
			} else {
				years.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD YEAR

		// BEGIN CREDIT CARD MONTH
		List<String> months = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			if(i<10){
				months.add(String.format("%02d",i));
			} else {
				months.add(String.format("%d",i));
			}
		}
		// END CREDIT CARD MONTH

		model.addAttribute("months", months);
		model.addAttribute("years", years);
		
		return "broadband-user/sale/plans/order-summary";
	}
	
	
	
	@RequestMapping(value = "/broadband-user/sale/plans/confirm/save", method = RequestMethod.POST)
	public String sale(Model model, 
			@RequestParam("card_type") String card_type,
			@RequestParam("holder_name") String holder_name,
			@RequestParam("card_number") String card_number,
			@RequestParam("security_code") String security_code,
			@RequestParam("expiry_month") String expiry_month,
			@RequestParam("expiry_year") String expiry_year,
			@RequestParam("optional_request") String optional_request,
			RedirectAttributes attr, HttpSession session) {
		
		User user = (User) session.getAttribute("userSession");
		Integer sale_id = 0;
		if("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())){
			sale_id = user.getId();
		}
		
		Customer customer = (Customer)session.getAttribute("customerRegSale");
		
		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setSignature("unsigned");
		customer.getCustomerOrder().setSale_id(customer.getId());
		customer.getCustomerOrder().setSale_id(user.getId());
		customer.getCustomerOrder().setOptional_request(optional_request);
		
		this.crmService.registerCustomerCalling(customer);
		
		ApplicationPDFCreator appPDFCreator = new ApplicationPDFCreator();
		appPDFCreator.setCustomer(customer);
		appPDFCreator.setOrg(customer.getOrganization());
		appPDFCreator.setCustomerOrder(customer.getCustomerOrder());
		
		String applicationPDFPath = null;
		try {
			applicationPDFPath = appPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", customer.getCustomerOrder().getId());
		co.setOrder_pdf_path(applicationPDFPath);
		this.crmService.editCustomerOrder(co);
		
		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); // get sms register template from db
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone

		user = null;
		notification = null;
		applicationEmail = null;
		orderingPath = null;
		companyDetail = null;
		/*org = null;
		co = null;*/
		
		
		// BEGIN Credit Card
		
		if(!"".equals(holder_name) && !"".equals(card_number) && !"".equals(security_code)){
			CustomerCredit cc = new CustomerCredit();
			cc.setCard_type(card_type);
			cc.setHolder_name(holder_name);
			cc.setCard_number(card_number);
			cc.setSecurity_code(security_code);
			cc.setExpiry_date(
					"20"+expiry_year
					+"-"+expiry_month);
			co = null;
			co = new CustomerOrder();
			co.setId(customer.getCustomerOrder().getId());
			this.saleService.createCustomerCredit(cc);

			// BEGIN CREDIT PDF
			CreditPDFCreator cPDFCreator = new CreditPDFCreator();
			cc.setCustomer(this.crmService.queryCustomerById(customer.getId()));
			cPDFCreator.setCc(cc);
			cPDFCreator.setCo(co);
			cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer.getId()));
			String creditPDFPath = null;
			try {
				creditPDFPath = cPDFCreator.create();
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			co.getParams().put("id", co.getId());
			co.setCredit_pdf_path(creditPDFPath);
			this.crmService.editCustomerOrder(co);
			cPDFCreator = null;
			creditPDFPath = null;
			// END CREDIT PDF
			
			
			// Recycle
			co = null;
		}

		// BEGIN Credit Card
		
		session.removeAttribute("customerRegSale");
		
		return "redirect:/broadband-user/sale/online/ordering/view/by/"+sale_id;
	}
	
	
}

package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
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
import com.tm.broadband.validator.mark.CustomerCreditValidatedMark;

@Controller
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
	
	
//	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm/save", method = RequestMethod.POST)
//	public String orderSave(Model model, 
//			@ModelAttribute("orderPlan") Plan plan, 
//			@ModelAttribute("orderCustomer") Customer customer, 
//			@RequestParam("card_type") String card_type,
//			@RequestParam("holder_name") String holder_name,
//			@RequestParam("card_number") String card_number,
//			@RequestParam("security_code") String security_code,
//			@RequestParam("expiry_month") String expiry_month,
//			@RequestParam("expiry_year") String expiry_year,
//			@RequestParam("optional_request") String optional_request,
//			RedirectAttributes attr, SessionStatus status, HttpServletRequest req) {
//		
//		User user = (User) req.getSession().getAttribute("userSession");
//		Integer sale_id = 0;
//		if("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())){
//			sale_id = user.getId();
//		}
//		
//		customer.setPassword(TMUtils.generateRandomString(6));
//		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
//		customer.setUser_name(customer.getLogin_name());
//		customer.setStatus("active");
//		customer.getCustomerOrder().setOrder_status("pending");
//		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));
//		customer.getCustomerOrder().setSale_id(customer.getId());
//		customer.getCustomerOrder().setSignature("unsigned");
//		
//		customer.getCustomerOrder().setSale_id(user.getId());
//		customer.getCustomerOrder().setOptional_request(optional_request);
//		
//		this.crmService.registerCustomerCalling(customer);
//		
//		ApplicationPDFCreator appPDFCreator = new ApplicationPDFCreator();
//		appPDFCreator.setCustomer(customer);
//		appPDFCreator.setOrg(customer.getOrganization());
//		appPDFCreator.setCustomerOrder(customer.getCustomerOrder());
//		
//		String applicationPDFPath = null;
//		try {
//			applicationPDFPath = appPDFCreator.create();
//		} catch (DocumentException | IOException e) {
//			e.printStackTrace();
//		}
//		CustomerOrder co = new CustomerOrder();
//		co.getParams().put("id", customer.getCustomerOrder().getId());
//		co.setOrder_pdf_path(applicationPDFPath);
//		this.crmService.editCustomerOrder(co);
//		
//		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
//		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		Notification notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
//		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail); // call mail at value retriever
//		ApplicationEmail applicationEmail = new ApplicationEmail();
//		applicationEmail.setAddressee(customer.getEmail());
//		applicationEmail.setSubject(notification.getTitle());
//		applicationEmail.setContent(notification.getContent());
//		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
//		applicationEmail.setAttachPath(orderingPath);
//		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//		notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms"); // get sms register template from db
//		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
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
//		
//		Organization org = this.crmService.queryOrganizationByCustomerId(customer.getId());
//		customer.setOrganization(org);
//		
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
//		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
//		
//		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
//		String orderPDFPath = null;
//		try {
//			orderPDFPath = new ApplicationPDFCreator(customer, customer.getCustomerOrder(), customer.getOrganization()).create();
//		} catch (DocumentException | IOException e) {
//			e.printStackTrace();
//		}
//		CustomerOrder co = new CustomerOrder();
//		co.getParams().put("id", customer.getCustomerOrder().getId());
//		co.setOrder_pdf_path(orderPDFPath);
//		
//		this.crmService.editCustomerOrder(co);
//		// END SET NECESSARY INFO AND GENERATE ORDER PDF
//
//		//CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		notification = this.systemService.queryNotificationBySort("online-ordering", "email");
//		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail); // call mail at value retriever
//		applicationEmail = new ApplicationEmail();
//		applicationEmail.setAddressee(customer.getEmail());
//		applicationEmail.setSubject(notification.getTitle());
//		applicationEmail.setContent(notification.getContent());
//		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//		notification = this.systemService.queryNotificationBySort("online-ordering", "sms"); // get sms register template from db
//		MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
//		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
//*/		
//		status.setComplete();
//		
//		user = null;
//		notification = null;
//		applicationEmail = null;
//		orderingPath = null;
//		companyDetail = null;
//		/*org = null;
//		co = null;*/
//		
//		
//		// BEGIN Credit Card
//		
//		if(!"".equals(holder_name) && !"".equals(card_number) && !"".equals(security_code)){
//			CustomerCredit cc = new CustomerCredit();
//			cc.setCard_type(card_type);
//			cc.setHolder_name(holder_name);
//			cc.setCard_number(card_number);
//			cc.setSecurity_code(security_code);
//			cc.setExpiry_date(
//					"20"+expiry_year
//					+"-"+expiry_month);
//			co = null;
//			co = new CustomerOrder();
//			co.setId(customer.getCustomerOrder().getId());
//			this.saleService.createCustomerCredit(cc);
//
//			// BEGIN CREDIT PDF
//			CreditPDFCreator cPDFCreator = new CreditPDFCreator();
//			Customer cQuery = new Customer();
//			cQuery.getParams().put("id", customer.getId());
//			cc.setCustomer(this.crmService.queryCustomer(cQuery));
//			cPDFCreator.setCc(cc);
//			cPDFCreator.setCo(co);
//			cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer.getId()));
//			String creditPDFPath = null;
//			try {
//				creditPDFPath = cPDFCreator.create();
//			} catch (DocumentException | IOException e) {
//				e.printStackTrace();
//			}
//			co.getParams().put("id", co.getId());
//			co.setCredit_pdf_path(creditPDFPath);
//			this.crmService.editCustomerOrder(co);
//			cPDFCreator = null;
//			creditPDFPath = null;
//			// END CREDIT PDF
//			
//			
//			// Recycle
//			co = null;
//		}
//
//		// BEGIN Credit Card
//		
//		return "redirect:/broadband-user/sale/online/ordering/view/by/"+sale_id;
//	}
	
	@RequestMapping(value = "/broadband-user/sales/online-ordering/redirect")
	public String toOnlineOrdering(Model model){
		return "redirect:/broadband-user/sale/plans/select-customer-business";
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
	
//	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/create", method = RequestMethod.POST)
//	public String doCredit(Model model,
//			@ModelAttribute("customerCredit") @Validated(CustomerCreditValidatedMark.class) CustomerCredit customerCredit
//			,@RequestParam("customer_id") Integer customer_id
//			,@RequestParam("order_id") Integer order_id
//			,BindingResult result
//			,RedirectAttributes attr
//			,HttpServletRequest req) {
//		
//		User user = (User) req.getSession().getAttribute("userSession");
//		Integer sale_id = 0;
//		if("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())){
//			sale_id = user.getId();
//		}
//		
//		customerCredit.setExpiry_date(
//				"20"+customerCredit.getExpiry_year()
//				+"-"+customerCredit.getExpiry_month());
//		
//		model.addAttribute("panelheading", "Customer Credit Card Information");
//		model.addAttribute("action", "/broadband-user/sale/online/ordering/order/credit/create");
//
//		if (result.hasErrors()) {
//			return "broadband-user/sale/online-ordering-credit";
//		}
//		
//		CustomerOrder co = new CustomerOrder();
//		co.setId(order_id);
//		this.saleService.createCustomerCredit(customerCredit);
//
//		// BEGIN CREDIT PDF
//		CreditPDFCreator cPDFCreator = new CreditPDFCreator();
//		Customer cQuery = new Customer();
//		cQuery.getParams().put("id", customer_id);
//		customerCredit.setCustomer(this.crmService.queryCustomer(cQuery)); 
//		cPDFCreator.setCc(customerCredit);
//		cPDFCreator.setCo(co);
//		cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer_id));
//		String creditPDFPath = null;
//		try {
//			creditPDFPath = cPDFCreator.create();
//		} catch (DocumentException | IOException e) {
//			e.printStackTrace();
//		}
//		co.getParams().put("id", order_id);
//		co.setCredit_pdf_path(creditPDFPath);
//		this.crmService.editCustomerOrder(co);
//		// END CREDIT PDF
//		
//		// Recycle
//		co = null;
//		cPDFCreator = null;
//		creditPDFPath = null;
//		
//
//		return "redirect:/broadband-user/sale/online/ordering/view/by/"+sale_id;
//	}

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
			for (CustomerInvoice ci : pageCis.getResults()) {
				List<CustomerInvoiceDetail> cids = this.crmService.queryCustomerInvoiceDetailsByCustomerInvoiceId(ci.getId());
				for (CustomerInvoiceDetail cid : cids) {
					if(cid.getInvoice_detail_type()!=null && cid.getInvoice_detail_type().contains("plan")){
						Double ci_commission = ci.getCommission()!=null ? ci.getCommission() : 0d;
						Double plan_price = cid.getInvoice_detail_price()!=null ? cid.getInvoice_detail_price() : 0d;
						Double commission_rates = user.getAgent_commission_rates()!=null ? user.getAgent_commission_rates()/100 : 0d;
						Integer unit = cid.getInvoice_detail_unit();
						// If plan prepay month greater equals than 3 months 
						if(unit>=3){
							ci.setCommission(TMUtils.bigAdd(ci_commission, plan_price+((unit-3) * plan_price * commission_rates)));
						// If plan prepay month less equals than 3 months
						} else {
							ci.setCommission(TMUtils.bigAdd(ci_commission, plan_price*commission_rates));
						}
					}
				}
			}
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/broadband-user/sale/plans")
	public String salePlans(HttpSession session){
		session.removeAttribute("customerRegSale");
		return "broadband-user/sale/plans/plans";
	}
	
	// topup
	@RequestMapping(value = "/broadband-user/sale/plans/topup")
	public String salePlansTopup(HttpSession session){
		System.out.println("this is new customerRegSale, Topup");
		Customer customerRegSale = new Customer();
		customerRegSale.setNewOrder(false);
		customerRegSale.setSelect_plan_group("plan-topup");
		customerRegSale.setSelect_customer_type("personal");
		customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegSale.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegSale", customerRegSale);
		return "redirect:/broadband-user/sale/plans/address-check";
	}
	
	// personal
	@RequestMapping(value = "/broadband-user/sale/plans/personal")
	public String salePlansPersonal(HttpSession session){
		System.out.println("this is new customerRegSale, Personal");
		Customer customerRegSale = new Customer();
		customerRegSale.setNewOrder(false);
		customerRegSale.setSelect_plan_group("");
		customerRegSale.setSelect_customer_type("personal");
		customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegSale.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegSale", customerRegSale);
		return "redirect:/broadband-user/sale/plans/address-check";
	}
		
	// business
	@RequestMapping(value = "/broadband-user/sale/plans/business")
	public String salePlansBusiness(HttpSession session){
		System.out.println("this is new customerRegSale, Business");
		Customer customerRegSale = new Customer();
		customerRegSale.setNewOrder(false);
		customerRegSale.setSelect_plan_group("");
		customerRegSale.setSelect_customer_type("business");
		customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegSale.getCustomerOrder().setPromotion(false);
		session.setAttribute("customerRegSale", customerRegSale);
		return "redirect:/broadband-user/sale/plans/address-check";
	}
		
	// promotion ipadmini
	@RequestMapping(value = "/broadband-user/sale/plans/promotion/ipadmini")
	public String salePlansPromotioniPadMini(HttpSession session){
		System.out.println("this is new customerRegSale, promotion ipadmini");
		Customer customerRegSale = new Customer();
		customerRegSale.setNewOrder(false);
		customerRegSale.setSelect_plan_group("");
		customerRegSale.setSelect_customer_type("personal");
		customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegSale.getCustomerOrder().setSale_id(10023);
		customerRegSale.getCustomerOrder().setPromotion(true);
		customerRegSale.setLanguage("en");
		session.setAttribute("customerRegSale", customerRegSale);
		return "redirect:/broadband-user/sale/plans/address-check";
	}
		
	// promotion hd
	@RequestMapping(value = "/broadband-user/sale/plans/promotion/hd")
	public String salePlansPromotionHD(HttpSession session){
		System.out.println("this is new customerRegSale, promotion hd");
		Customer customerRegSale = new Customer();
		customerRegSale.setNewOrder(false);
		customerRegSale.setSelect_plan_group("");
		customerRegSale.setSelect_customer_type("personal");
		customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");
		customerRegSale.getCustomerOrder().setSale_id(20023);
		customerRegSale.getCustomerOrder().setPromotion(true);
		customerRegSale.setLanguage("en");
		session.setAttribute("customerRegSale", customerRegSale);
		return "redirect:/broadband-user/sale/plans/address-check";
	}
	
	// ***
	
	@RequestMapping("/broadband-user/sale/plans/address-check") 
	public String toAddressCheck(Model model, HttpSession session) {
		return "broadband-user/sale/plans/address-check";
	}
	
	// ***
	
	@RequestMapping("/broadband-user/sale/plans/define/{type}")
	public String plansDefineType(HttpSession session, @PathVariable("type") String type) {
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		customerRegSale.setSelect_plan_type(type);
		return "redirect:/broadband-user/sale/plans/order";
	}
	
	// clear address
	@RequestMapping("/broadband-user/sale/plans/address/clear") 
	public String addressClear(HttpSession session) {
		session.removeAttribute("customerRegSale");
		return "redirect:/broadband-user/sale/plans";
	}	
	
	// order
	@RequestMapping("/broadband-user/sale/plans/order") 
	public String toOrderPlan(Model model, HttpSession session) {
		
		String url = "broadband-user/sale/plans/customer-order";
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		if (!customerRegSale.isServiceAvailable()) {
			System.out.println("customerRegSale.isServiceAvailable(): " + customerRegSale.isServiceAvailable());
			url = "redirect:/broadband-user/sale/plans";
		}
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
			@RequestParam("card_type") String card_type
			, @RequestParam("holder_name") String holder_name
			, @RequestParam("card_number") String card_number
			, @RequestParam("security_code") String security_code
			, @RequestParam("expiry_month") String expiry_month
			, @RequestParam("expiry_year") String expiry_year
			, @RequestParam("optional_request") String optional_request
			, RedirectAttributes attr, HttpSession session) {
		
		User user = (User) session.getAttribute("userSession");
		Integer sale_id = 0;
		if ("sales".equals(user.getUser_role()) || "agent".equals(user.getUser_role())) {
			sale_id = user.getId();
		}

		Customer customer = (Customer) session.getAttribute("customerRegSale");

		customer.setPassword(TMUtils.generateRandomString(6));
		customer.setMd5_password(DigestUtils.md5Hex(customer.getPassword()));
		customer.setUser_name(customer.getLogin_name());
		customer.setStatus("active");
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setSignature("unsigned");
		customer.getCustomerOrder().setSale_id(customer.getId());
		customer.getCustomerOrder().setSale_id(user.getId());
		customer.getCustomerOrder().setOptional_request(optional_request);

		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder(), null);

		ApplicationPDFCreator appPDFCreator = new ApplicationPDFCreator();
		appPDFCreator.setCo(customer.getCustomerOrder());

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
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(customer.getEmail());
		applicationEmail.setSubject(notification.getTitle());
		applicationEmail.setContent(notification.getContent());
		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
		applicationEmail.setAttachPath(orderingPath);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		notification = this.systemService.queryNotificationBySort("personal".equals(customer.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms");
		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());

		// BEGIN Credit Card

		if (!"".equals(holder_name) && !"".equals(card_number) && !"".equals(security_code)) {
			CustomerCredit cc = new CustomerCredit();
			cc.setCard_type(card_type);
			cc.setHolder_name(holder_name);
			cc.setCard_number(card_number);
			cc.setSecurity_code(security_code);
			cc.setExpiry_date("20" + expiry_year + "-" + expiry_month);
			co = new CustomerOrder();
			co.setId(customer.getCustomerOrder().getId());
			this.saleService.createCustomerCredit(cc);

			// BEGIN CREDIT PDF
			CreditPDFCreator cPDFCreator = new CreditPDFCreator();
			Customer cQuery = new Customer();
			cQuery.getParams().put("id", customer.getId());
			cc.setCustomer(customer);//this.crmService.queryCustomer(cQuery)
			cPDFCreator.setCc(cc);
			cPDFCreator.setCo(customer.getCustomerOrder());
			String creditPDFPath = null;
			try {
				creditPDFPath = cPDFCreator.create();
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			co.getParams().put("id", co.getId());
			co.setCredit_pdf_path(creditPDFPath);
			this.crmService.editCustomerOrder(co);
			// END CREDIT PDF
		}

		// BEGIN Credit Card

		session.removeAttribute("customerRegSale");

		return "redirect:/broadband-user/sale/online/ordering/view/by/" + sale_id;
	}
	
	
}

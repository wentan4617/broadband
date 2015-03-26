package com.tm.broadband.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.pdf.ReceiptPDFCreator;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.CustomerService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;

@Controller
public class CustomerDPSController {
	
	private CRMService crmService;
	private CustomerService customerService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;
	
	@Autowired
	public CustomerDPSController(CRMService crmService
			, CustomerService customerService
			, MailerService mailerService
			, SystemService systemService
			, SmserService smserService) {
		this.crmService = crmService;
		this.customerService = customerService;
		this.systemService = systemService;
		this.mailerService = mailerService;
		this.smserService = smserService;
	}
	
	@RequestMapping(value = "/customer/ordering-form/checkout", method = RequestMethod.POST)
	public String orderingFormCheckout(HttpServletRequest req
			, HttpSession session
			, @PathParam("orderid") int orderid) {
    	
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		List<CustomerOrder> orders = customerSession.getCustomerOrders();
		Double order_total_price = 0d;
		
		if (orders != null && orders.size() > 0) {
    		for (CustomerOrder order : orders) {
    			if (order.getId() == orderid) {
    				order_total_price = order.getOrder_total_price();
    				break;
    			}
    		}
    	}

		GenerateRequest gr = new GenerateRequest();
		gr.setMerchantReference(customerSession.getId() + "," + orderid);
		gr.setAmountInput(new DecimalFormat("#.00").format(order_total_price));
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		System.out.println("/customer/ordering-form/checkout: " + req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString() + "/result");
		gr.setUrlSuccess(req.getRequestURL().toString() + "/result");

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/ordering-form/checkout/result")
	public String orderingFormCheckoutResult(
			@RequestParam(value = "result", required = true) String result
			, RedirectAttributes attr) throws Exception {
		
		System.out.println("result: " + result);

		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
		
		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			int customer_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[0]);
			int order_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[1]);
			
			Customer customerQ = new Customer();
			customerQ.getParams().put("id", customer_id);
			customerQ.getCustomerOrder().getParams().put("id", order_id);
			
			Customer customer = this.customerService.queryCustomerWithOrderWithDetails(customerQ);
			
			if (!result.equals(customer.getResult())) {
				
				Customer customerUpdate = new Customer();
				customerUpdate.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
				customerUpdate.setResult(result);
				customerUpdate.getParams().put("id", customer_id);
				
				ReceiptPDFCreator receiptCreator = new ReceiptPDFCreator();
				receiptCreator.setCo(customer.getCustomerOrder());
				String receiptPath = receiptCreator.create();
				
				CustomerOrder orderUpdate = new CustomerOrder();
				orderUpdate.setOrder_status("paid");
				orderUpdate.setReceipt_pdf_path(receiptPath);
				orderUpdate.getParams().put("id", order_id);
				
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
				customerTransaction.setCustomer_id(customer_id);
				customerTransaction.setOrder_id(order_id);
				customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
				
				this.crmService.customerOrderingForm(customerUpdate, orderUpdate, customerTransaction);
				
				Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
				
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getCustomerOrder().getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				applicationEmail.setAttachName("receipt_" + order_id + ".pdf");
				applicationEmail.setAttachPath(receiptPath);
				
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				
				notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCustomerOrder().getMobile(), notification.getContent());
				
			}
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/home";
	}
	
	
	
	@RequestMapping(value = "/plans/order/dps", method = RequestMethod.POST)
	public String plansOrderDPS(HttpServletRequest req
			, HttpSession session
			, RedirectAttributes attr) throws Exception { 
		
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		Customer customerSession = (Customer) session.getAttribute("customerSession"); System.out.println("customerSession: " + customerSession);
		List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();
		
		Double order_total_price = customerReg.getCustomerOrder().getOrder_total_price();
		Double voucher_total_price = 0d;
		
		for (Voucher voucher: customerReg.getVouchers()) {
			order_total_price -= voucher.getFace_value();
			voucher_total_price += voucher.getFace_value();
			
			CustomerTransaction ctVoucher = new CustomerTransaction();
			ctVoucher.setAmount(voucher.getFace_value());
			ctVoucher.setTransaction_type("purchare");
			ctVoucher.setTransaction_sort("voucher");
			ctVoucher.setCard_name("voucher: " + voucher.getSerial_number());
			cts.add(ctVoucher);
		}
		
		String redirectUrl = "";
		String send_email = "", send_mobile = "";
		
		if (customerReg.getNewOrder()) {
			customerReg.setStatus("active");
			customerReg.setPassword("*********");
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.getCustomerOrder().setOrder_status("pending");
			if (customerSession != null) {
				send_email = customerReg.getEmail();
				send_mobile = customerReg.getCellphone();
				customerReg.setEmail(customerSession.getEmail());
				customerReg.setCellphone(customerSession.getCellphone());
			}
		} else {
			customerReg.setStatus("active");
			customerReg.setPassword(TMUtils.generateRandomString(6));
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.getCustomerOrder().setOrder_status("pending");
			send_email = customerReg.getEmail();
			send_mobile = customerReg.getCellphone();
		}
		
		if (order_total_price > 0) {
			
			customerReg.setBalance(voucher_total_price);
			
			customerReg.setCompany_name(customerReg.getCustomerOrder().getOrg_name());
			this.crmService.saveCustomerOrder(customerReg, customerReg.getCustomerOrder(), cts);
			
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customerReg);
			CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
			Notification notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail); 
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(send_email);
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("ordering_form_" + customerReg.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(orderingPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms");
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
			this.smserService.sendSMSByAsynchronousMode(send_mobile, notification.getContent()); 
			
			GenerateRequest gr = new GenerateRequest();
			gr.setMerchantReference(customerReg.getId() + "," + customerReg.getCustomerOrder().getId());
			gr.setAmountInput(new DecimalFormat("#.00").format(order_total_price));
			gr.setCurrencyInput("NZD");
			gr.setTxnType("Purchase");
			System.out.println("/plans/order/dps: " + req.getRequestURL().toString());
			gr.setUrlFail(req.getRequestURL().toString() + "/result");
			gr.setUrlSuccess(req.getRequestURL().toString() + "/result");
			
			redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
			System.out.println(redirectUrl);
			
		} else {
			redirectUrl = "/plans/order/result/success";
			
			customerReg.setBalance(voucher_total_price);
			customerReg.getCustomerOrder().setOrder_status("paid");
			
			customerReg.setCompany_name(customerReg.getCustomerOrder().getOrg_name());
			this.crmService.saveCustomerOrder(customerReg, customerReg.getCustomerOrder(), cts);
			this.crmService.createOrderingFormPDFByDetails(customerReg);
			
			ReceiptPDFCreator receiptCreator = new ReceiptPDFCreator();
			receiptCreator.setCo(customerReg.getCustomerOrder());
			String receiptPath = receiptCreator.create();
			
			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(customerReg.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("receipt_" + customerReg.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(receiptPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
			this.smserService.sendSMSByAsynchronousMode(customerReg.getCellphone(), notification.getContent());
			
			Response responseBean = new Response();
			responseBean.setSuccess("1");
			attr.addFlashAttribute("responseBean", responseBean);
		}
		
		session.removeAttribute("customerReg");

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/plans/order/dps/result")
	public String plansOrderDPSResult(
			@RequestParam(value = "result", required = true) String result
			, RedirectAttributes attr) throws Exception {
		
		String url = "";

		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			int customer_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[0]);
			int order_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[1]);
			
			Customer customerQ = new Customer();
			customerQ.getParams().put("id", customer_id);
			customerQ.getCustomerOrder().getParams().put("id", order_id);
			
			Customer customer = this.customerService.queryCustomerWithOrderWithDetails(customerQ);
			
			if (!result.equals(customer.getResult())) {
				
				Customer customerUpdate = new Customer();
				customerUpdate.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
				customerUpdate.setResult(result);
				customerUpdate.getParams().put("id", customer_id);
				
				ReceiptPDFCreator receiptCreator = new ReceiptPDFCreator();
				receiptCreator.setCo(customer.getCustomerOrder());
				String receiptPath = receiptCreator.create();
				
				CustomerOrder orderUpdate = new CustomerOrder();
				orderUpdate.setOrder_status("paid");
				orderUpdate.setReceipt_pdf_path(receiptPath);
				orderUpdate.getParams().put("id", order_id);
				
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
				customerTransaction.setCustomer_id(customer_id);
				customerTransaction.setOrder_id(order_id);
				customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
				
				this.crmService.customerOrderingForm(customerUpdate, orderUpdate, customerTransaction);
				
				Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
				
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getCustomerOrder().getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				applicationEmail.setAttachName("receipt_" + order_id + ".pdf");
				applicationEmail.setAttachPath(receiptPath);
				
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				
				notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCustomerOrder().getMobile(), notification.getContent());
				
				url = "redirect:/plans/order/result/success";
			}
			
		} else {
			url = "redirect:/plans/order/result/error";
		}

		attr.addFlashAttribute("responseBean", responseBean);

		return url;
	}
	
	@RequestMapping(value = "/customer/topup/checkout", method = RequestMethod.POST)
	public String topupCheckout(HttpServletRequest req
			, HttpSession session 
			, @RequestParam("topupAmount") int topupAmount) {
		
		Customer customerSession = (Customer) session.getAttribute("customerSession");

		GenerateRequest gr = new GenerateRequest();
		gr.setMerchantReference(customerSession.getId().toString());
		gr.setAmountInput(new DecimalFormat("#.00").format(topupAmount));
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		System.out.println("/customer/topup/checkout: " + req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString() + "/result");
		gr.setUrlSuccess(req.getRequestURL().toString() + "/result");

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/topup/checkout/result")
	public String topupCheckoutResult(Model model,
			@RequestParam(value = "result", required = true) String result,
			RedirectAttributes attr) throws Exception {

		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			int customer_id = Integer.parseInt(responseBean.getMerchantReference());
			
			Customer customerQ = new Customer();
			customerQ.getParams().put("id", customer_id);
			
			Customer customer = this.customerService.queryCustomer(customerQ);
			
			if (!result.equals(customer.getResult())) {
				
				Customer customerUpdate = new Customer();
				customerUpdate.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
				customerUpdate.setResult(result);
				customerUpdate.getParams().put("id", customer_id);

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
				
				this.crmService.customerTopup(customerUpdate, customerTransaction);
				
				Notification notification = this.crmService.queryNotificationBySort("payment", "email");
				ApplicationEmail applicationEmail = new ApplicationEmail();
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				notification = this.crmService.queryNotificationBySort("payment", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
				
			}
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/topup";
	}
	
	@RequestMapping(value = "/customer/topup-plan/checkout", method = RequestMethod.POST)
	public String topupPlanCheckout(HttpServletRequest req
			, HttpSession session 
			, @RequestParam("prepaymonths") int months) {
		
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		CustomerOrderDetail cod = customerSession.getCustomerOrder().getCustomerOrderDetails().get(0);
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
		} 

		GenerateRequest gr = new GenerateRequest();
		gr.setMerchantReference(customerSession.getId().toString());
		gr.setAmountInput(new DecimalFormat("#.00").format(total));
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		System.out.println("/customer/topup-plan/checkout: " + req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString() + "/result");
		gr.setUrlSuccess(req.getRequestURL().toString() + "/result");

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/topup-plan/checkout/result")
	public String topupPlanCheckoutResult(Model model,
			@RequestParam(value = "result", required = true) String result,
			RedirectAttributes attr) throws Exception {

		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			int customer_id = Integer.parseInt(responseBean.getMerchantReference());
			
			Customer customerQ = new Customer();
			customerQ.getParams().put("id", customer_id);
			
			Customer customer = this.customerService.queryCustomer(customerQ);
			
			if (!result.equals(customer.getResult())) {
				
				Customer customerUpdate = new Customer();
				customerUpdate.setBalance((customer.getBalance() != null ? customer.getBalance() : 0) + Double.parseDouble(responseBean.getAmountSettlement()));
				customerUpdate.setResult(result);
				customerUpdate.getParams().put("id", customer_id);

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
				
				this.crmService.customerTopup(customerUpdate, customerTransaction);
				
				Notification notification = this.crmService.queryNotificationBySort("payment", "email");
				ApplicationEmail applicationEmail = new ApplicationEmail();
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				notification = this.crmService.queryNotificationBySort("payment", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
				
			}
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/home";
	}
	
	@RequestMapping(value = "/customer/invoice/checkout", method = RequestMethod.POST)
	public String invoiceCheckout(HttpSession session
			, @RequestParam("invoiceid") int invoiceid
			, HttpServletRequest req) {
		
		Customer customerSession = (Customer) session.getAttribute("customerSession");
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("id", invoiceid);
		
		CustomerInvoice ci = this.crmService.queryCustomerInvoice(ciQuery);
		
//		List<CustomerInvoice> invoices = customerSession.getCustomerInvoices();
//		
//		if (invoices != null && invoices.size() > 0) {
//    		for (CustomerInvoice invoice : invoices) {
//    			if (invoice.getId() == invoiceid) {
//    				ci = invoice;
//    				break;
//    			}
//    		}
//    	}
		
		String redirectUrl = "";
		
		if (ci != null) {
			GenerateRequest gr = new GenerateRequest();
			gr.setMerchantReference(customerSession.getId() + "," + ci.getOrder_id() + "," + invoiceid);
			gr.setAmountInput(new DecimalFormat("#.00").format(ci.getBalance()));
			gr.setCurrencyInput("NZD");
			gr.setTxnType("Purchase");
			System.out.println("/customer/invoice/checkout: " + req.getRequestURL().toString());
			gr.setUrlFail(req.getRequestURL().toString() + "/result");
			gr.setUrlSuccess(req.getRequestURL().toString() + "/result");

			redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
		}

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/invoice/checkout/result")
	public String invoiceCheckoutResult(
			@RequestParam(value = "result", required = true) String result
			, RedirectAttributes attr) throws Exception {
		
		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			int customer_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[0]);
			int order_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[1]);
			int invoice_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[2]);
			
			Customer customerQ = new Customer();
			customerQ.getParams().put("id", customer_id);
			customerQ.getCustomerOrder().getParams().put("id", order_id);
			customerQ.getCustomerInvoice().getParams().put("id", invoice_id);
			
			Customer customer = this.customerService.queryCustomerWithOrderWithInvoice(customerQ);
			
			if (!result.equals(customer.getResult())) {
				
				CustomerTransaction ctQuery = new CustomerTransaction();
				ctQuery.getParams().put("customer_id", customer.getId());
				ctQuery.getParams().put("order_id", customer.getCustomerInvoice().getOrder_id());
				ctQuery.getParams().put("invoice_id", customer.getCustomerInvoice().getId());
				ctQuery.getParams().put("amount", Double.parseDouble(responseBean.getAmountSettlement()));
				ctQuery.getParams().put("auth_code", responseBean.getAuthCode());
				ctQuery.getParams().put("dps_txn_ref", responseBean.getDpsTxnRef());
				ctQuery.getParams().put("merchant_reference", responseBean.getMerchantReference());
				ctQuery.getParams().put("txnMac", responseBean.getTxnMac());
				CustomerTransaction ct = this.crmService.queryCustomerTransaction(ctQuery);
				
				if(ct==null){
					
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
					
					CustomerInvoice ciUpdate = new CustomerInvoice();
					ciUpdate.setStatus("paid");
					ciUpdate.setAmount_paid(customerTransaction.getAmount());
					ciUpdate.setBalance(TMUtils.bigOperationTwoReminders(customer.getCustomerInvoice().getBalance(), ciUpdate.getAmount_paid(), "sub"));
					ciUpdate.getParams().put("id", customer.getCustomerInvoice().getId());
					
					this.crmService.customerBalance(ciUpdate, customerTransaction);
					
					Notification notification = this.crmService.queryNotificationBySort("payment", "email");
					ApplicationEmail applicationEmail = new ApplicationEmail();
					CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
					MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
					applicationEmail.setAddressee(customer.getCustomerOrder().getEmail()); System.out.println("customer.getCustomerOrder().getEmail(): " + customer.getCustomerOrder().getEmail());
					applicationEmail.setSubject(notification.getTitle());
					applicationEmail.setContent(notification.getContent());
					this.mailerService.sendMailByAsynchronousMode(applicationEmail);
					notification = this.crmService.queryNotificationBySort("payment", "sms");
					MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
					this.smserService.sendSMSByAsynchronousMode(customer.getCustomerOrder().getMobile(), notification.getContent()); System.out.println("customer.getCustomerOrder().getMobile(): " + customer.getCustomerOrder().getMobile());
					
				}
				
			}
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/orders";
	}
	
}

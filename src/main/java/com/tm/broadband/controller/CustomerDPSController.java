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
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
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
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/customer/ordering-form/checkout")
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
				
				CustomerOrder orderUpdate = new CustomerOrder();
				orderUpdate.setOrder_status("paid");
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
				
				String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
				
				Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
				
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				applicationEmail.setAttachName("receipt_" + order_id + ".pdf");
				applicationEmail.setAttachPath(receiptPath);
				
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				
				notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
				
			}
			
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT " + responseBean.getResponseText());
		}

		return "redirect:/customer/home";
	}
	
	
	
	@RequestMapping(value = "/plans/order/dps", method = RequestMethod.POST)
	public String plansOrderDPS(Model model
			, HttpServletRequest req
			, HttpSession session
			, RedirectAttributes attr) { 
		
		Customer customerReg = (Customer) session.getAttribute("customerReg");
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
		
		if (customerReg.getNewOrder()) {
			customerReg.setStatus("active");
			customerReg.setPassword("*********");
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.getCustomerOrder().setOrder_status("pending");
		} else {
			customerReg.setStatus("active");
			customerReg.setPassword(TMUtils.generateRandomString(6));
			customerReg.setMd5_password(DigestUtils.md5Hex(customerReg.getPassword()));
			customerReg.getCustomerOrder().setOrder_status("pending");
		}
		
		if (order_total_price > 0) {
			
			customerReg.setBalance(voucher_total_price);
			
			this.crmService.saveCustomerOrder(customerReg, customerReg.getCustomerOrder(), cts);
			
			String orderingPath = this.crmService.createOrderingFormPDFByDetails(customerReg);
			CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
			Notification notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "email");
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail); 
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(customerReg.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("ordering_form_" + customerReg.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(orderingPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			notification = this.systemService.queryNotificationBySort("personal".equals(customerReg.getCustomer_type()) ? "online-ordering" : "online-ordering-business", "sms");
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerOrder(), companyDetail);
			this.smserService.sendSMSByAsynchronousMode(customerReg.getCellphone(), notification.getContent()); 
			
			GenerateRequest gr = new GenerateRequest();
			gr.setMerchantReference(customerReg.getId() + "," + customerReg.getCustomerOrder().getId());
			gr.setAmountInput(new DecimalFormat("#.00").format(order_total_price));
			gr.setCurrencyInput("NZD");
			gr.setTxnType("Purchase");
			System.out.println("/plans/order/dps: " + req.getRequestURL().toString());
			gr.setUrlFail(req.getRequestURL().toString());
			gr.setUrlSuccess(req.getRequestURL().toString());
			
			redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
			System.out.println(redirectUrl);
			
		} else {
			redirectUrl = "/plans/order/result/success";
			
			customerReg.setBalance(voucher_total_price);
			customerReg.getCustomerOrder().setOrder_status("paid");
			
			this.crmService.saveCustomerOrder(customerReg, customerReg.getCustomerOrder(), cts);
			this.crmService.createOrderingFormPDFByDetails(customerReg);
			String receiptPath = this.crmService.createReceiptPDFByDetails(customerReg);
			
			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			MailRetriever.mailAtValueRetriever(notification, customerReg, customerReg.getCustomerInvoice(), companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(customerReg.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("receipt_" + customerReg.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(receiptPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
			MailRetriever.mailAtValueRetriever(notification, customerReg, companyDetail);
			this.smserService.sendSMSByAsynchronousMode(customerReg.getCellphone(), notification.getContent());
			
			Response responseBean = new Response();
			responseBean.setSuccess("1");
			attr.addFlashAttribute("responseBean", responseBean);
		}
		
		session.removeAttribute("customerReg");

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/plans/order/dps")
	public String plansOrderDPSResult(Model model,
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
				
				CustomerOrder orderUpdate = new CustomerOrder();
				orderUpdate.setOrder_status("paid");
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
				
				String receiptPath = this.crmService.createReceiptPDFByDetails(customer);
				
				Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
				CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerInvoice(), companyDetail);
				
				ApplicationEmail applicationEmail = new ApplicationEmail();
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				applicationEmail.setAttachName("receipt_" + order_id + ".pdf");
				applicationEmail.setAttachPath(receiptPath);
				
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				
				notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
				
				url = "redirect:/plans/order/result/success";
			}
			
		} else {
			url = "redirect:/plans/order/result/error";
		}

		attr.addFlashAttribute("responseBean", responseBean);

		return url;
	}
}

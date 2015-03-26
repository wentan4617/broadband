package com.tm.broadband.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.tm.broadband.pdf.ReceiptPDFCreator;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.CustomerService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;


@Controller
public class CRMDPSController {
	
	private CRMService crmService;
	private CustomerService customerService;
	private MailerService mailerService;
	private SystemService systemService;
	private SmserService smserService;
	
	@Autowired
	public CRMDPSController(CRMService crmService
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
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/dps", method = RequestMethod.POST)
	public String plansOrderDPS(HttpServletRequest req
			, HttpSession session
			, RedirectAttributes attr) throws Exception { 
		
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		Customer customerBackSession = (Customer) session.getAttribute("customerBackSession"); System.out.println("customerBackSession: " + customerBackSession);
		List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();
		
		Double order_total_price = customerRegAdmin.getCustomerOrder().getOrder_total_price();
		Double voucher_total_price = 0d;
		
		for (Voucher voucher: customerRegAdmin.getVouchers()) {
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
		
		if (customerRegAdmin.getNewOrder()) {
			customerRegAdmin.setStatus("active");
			customerRegAdmin.setPassword("*********");
			customerRegAdmin.setMd5_password(DigestUtils.md5Hex(customerRegAdmin.getPassword()));
			customerRegAdmin.getCustomerOrder().setOrder_status("pending");
			if (customerBackSession != null) {
				send_email = customerRegAdmin.getEmail();
				send_mobile = customerRegAdmin.getCellphone();
				customerRegAdmin.setEmail(customerBackSession.getEmail());
				customerRegAdmin.setCellphone(customerBackSession.getCellphone());
			}
		} else {
			customerRegAdmin.setStatus("active");
			customerRegAdmin.setPassword(TMUtils.generateRandomString(6));
			customerRegAdmin.setMd5_password(DigestUtils.md5Hex(customerRegAdmin.getPassword()));
			customerRegAdmin.getCustomerOrder().setOrder_status("pending");
			send_email = customerRegAdmin.getEmail();
			send_mobile = customerRegAdmin.getCellphone();
		}
		
		if (order_total_price > 0) {
			
			customerRegAdmin.setBalance(voucher_total_price);
			
			customerRegAdmin.setCompany_name(customerRegAdmin.getCustomerOrder().getOrg_name());
			this.crmService.saveCustomerOrder(customerRegAdmin, customerRegAdmin.getCustomerOrder(), cts);
			
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
			
			GenerateRequest gr = new GenerateRequest();
			gr.setMerchantReference(customerRegAdmin.getId() + "," + customerRegAdmin.getCustomerOrder().getId());
			gr.setAmountInput(new DecimalFormat("#.00").format(order_total_price));
			gr.setCurrencyInput("NZD");
			gr.setTxnType("Purchase");
			System.out.println("/plans/order/dps: " + req.getRequestURL().toString());
			gr.setUrlFail(req.getRequestURL().toString());
			gr.setUrlSuccess(req.getRequestURL().toString());
			
			redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
			System.out.println(redirectUrl);
			
		} else {
			redirectUrl = "/broadband-user/crm/plans/order/result/success";
			
			customerRegAdmin.setBalance(voucher_total_price);
			customerRegAdmin.getCustomerOrder().setOrder_status("paid");
			
			customerRegAdmin.setCompany_name(customerRegAdmin.getCustomerOrder().getOrg_name());
			this.crmService.saveCustomerOrder(customerRegAdmin, customerRegAdmin.getCustomerOrder(), cts);
			this.crmService.createOrderingFormPDFByDetails(customerRegAdmin);
			
			ReceiptPDFCreator receiptCreator = new ReceiptPDFCreator();
			receiptCreator.setCo(customerRegAdmin.getCustomerOrder());
			String receiptPath = receiptCreator.create();
			
			Notification notification = this.crmService.queryNotificationBySort("register-pre-pay", "email");
			CompanyDetail companyDetail = this.systemService.queryCompanyDetail();
			MailRetriever.mailAtValueRetriever(notification, customerRegAdmin, customerRegAdmin.getCustomerOrder(), companyDetail);
			ApplicationEmail applicationEmail = new ApplicationEmail();
			applicationEmail.setAddressee(customerRegAdmin.getEmail());
			applicationEmail.setSubject(notification.getTitle());
			applicationEmail.setContent(notification.getContent());
			applicationEmail.setAttachName("receipt_" + customerRegAdmin.getCustomerOrder().getId() + ".pdf");
			applicationEmail.setAttachPath(receiptPath);
			this.mailerService.sendMailByAsynchronousMode(applicationEmail);
			
			notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
			MailRetriever.mailAtValueRetriever(notification, customerRegAdmin, customerRegAdmin.getCustomerOrder(), companyDetail);
			this.smserService.sendSMSByAsynchronousMode(customerRegAdmin.getCellphone(), notification.getContent());
			
			Response responseBean = new Response();
			responseBean.setSuccess("1");
			attr.addFlashAttribute("responseBean", responseBean);
		}
		
		session.removeAttribute("customerRegAdmin");

		return "redirect:" + redirectUrl;
		
		
	}
	
	
	@RequestMapping(value = "/broadband-user/crm/plans/order/dps")
	public String planOrderDPS(
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
				applicationEmail.setAddressee(customer.getEmail());
				applicationEmail.setSubject(notification.getTitle());
				applicationEmail.setContent(notification.getContent());
				applicationEmail.setAttachName("receipt_" + order_id + ".pdf");
				applicationEmail.setAttachPath(receiptPath);
				
				this.mailerService.sendMailByAsynchronousMode(applicationEmail);
				
				notification = this.crmService.queryNotificationBySort("register-pre-pay", "sms");
				MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
				this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent());
				
				url = "redirect:/broadband-user/crm/plans/order/result/success";
			}
			
		} else {
			url = "redirect:/broadband-user/crm/plans/order/result/error";
		}

		attr.addFlashAttribute("responseBean", responseBean);

		return url;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/topup/account-credit")
	public String toTopupAccountCredit(HttpServletRequest req
			, RedirectAttributes attr
			, @RequestParam("customer_id") int customer_id
			, @RequestParam("topup_amount") Double topup_amount) {
		
		if (topup_amount == null || topup_amount <= 0) {
			attr.addFlashAttribute("error", "Please input an amount to continue!");
			return "redirect:/broadband-user/crm/customer/edit/" + customer_id;
		}

		GenerateRequest gr = new GenerateRequest();
		gr.setMerchantReference(String.valueOf(customer_id));
		gr.setAmountInput(new DecimalFormat("#.00").format(topup_amount));
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		System.out.println("/broadband-user/crm/customer/topup/account-credit: " + req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString().replace("broadband-user", "dps"));
		gr.setUrlSuccess(req.getRequestURL().toString().replace("broadband-user", "dps"));

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);
		System.out.println("redirectUrl: "+redirectUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/dps/crm/customer/topup/account-credit")
	public String toSignupTopupAccountCredit(RedirectAttributes attr
			, @RequestParam(value = "result", required = true) String result) throws Exception {
		
		Response responseBean = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
		
		int customer_id = Integer.parseInt(responseBean.getMerchantReference().split(",")[0]);

		if (responseBean != null && responseBean.getSuccess().equals("1")) {
			
			Customer cQuery = new Customer();
			cQuery.getParams().put("id", customer_id);
			
			Customer customer = this.crmService.queryCustomer(cQuery);
			
			if (!result.equals(customer.getResult())) {
				
				Customer cUpdate = new Customer();
				cUpdate.setBalance(TMUtils.bigAdd(customer.getBalance() != null ? customer.getBalance() : 0d, Double.parseDouble(responseBean.getAmountSettlement())));
				cUpdate.setResult(result);
				cUpdate.getParams().put("id", customer_id);
				
				this.crmService.editCustomer(cUpdate);

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
				customerTransaction.setCustomer_id(customer.getId());
				customerTransaction.setTransaction_date(new Date(System.currentTimeMillis()));
				
				this.crmService.createCustomerTransaction(customerTransaction);
				
			}
					
			attr.addFlashAttribute("success", "PAYMENT " + responseBean.getResponseText());
			
		} else {
			
			attr.addFlashAttribute("error", "PAYMENT "+responseBean.getResponseText());
			
		}
		
		return "redirect:/broadband-user/crm/customer/edit/" + customer_id;
	}
	
}

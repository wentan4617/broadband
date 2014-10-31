package com.tm.broadband.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.Notification;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;

@Aspect
@Component
public class SendEmailAspect {
	
	private CRMService crmService;
	private SystemService systemService;
	private MailerService mailerService;
	private SmserService smserService;
	
	@Autowired
	public SendEmailAspect(CRMService crmService, SystemService systemService, MailerService mailerService, SmserService smserService) {
		this.crmService = crmService;
		this.systemService = systemService;
		this.mailerService = mailerService;
		this.smserService = smserService;
	}
	
	public SendEmailAspect(){}
	
	@Pointcut(value="execution(* com.tm.broadband.controller.CustomerController.orderTerm*(..))")
	public void orderTerm() {}
	
	@AfterReturning("orderTerm()")
	public void sendEmailAfterOrderTerm(JoinPoint jp) {
		Object [] objs = jp.getArgs();
		Customer customer = (Customer)objs[1];
		
//		String orderingPath = this.crmService.createOrderingFormPDFByDetails(customer);
//		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		Notification notification = this.systemService.queryNotificationBySort("online-ordering", "email");
//		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail); // call mail at value retriever
//		ApplicationEmail applicationEmail = new ApplicationEmail();
//		applicationEmail.setAddressee(customer.getEmail());
//		applicationEmail.setSubject(notification.getTitle());
//		applicationEmail.setContent(notification.getContent());
//		applicationEmail.setAttachName("ordering_form_" + customer.getCustomerOrder().getId() + ".pdf");
//		applicationEmail.setAttachPath(orderingPath);
//		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
//		notification = this.systemService.queryNotificationBySort("online-ordering", "sms"); // get sms register template from db
//		MailRetriever.mailAtValueRetriever(notification, customer, customer.getCustomerOrder(), companyDetail);
//		this.smserService.sendSMSByAsynchronousMode(customer.getCellphone(), notification.getContent()); // send sms to customer's mobile phone
	}

}

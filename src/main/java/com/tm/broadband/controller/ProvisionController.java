package com.tm.broadband.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.ProvisionService;
import com.tm.broadband.service.SystemService;

@Controller
public class ProvisionController {
	
	private ProvisionService provisionService;
	private MailerService mailerService;
	private SystemService systemService;

	@Autowired
	public ProvisionController(ProvisionService provisionService, MailerService mailerService, SystemService systemService) {
		this.provisionService = provisionService;
		this.mailerService = mailerService;
		this.systemService = systemService;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/view/{pageNo}/{order_status}")
	public String provisionCustomer(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "order_status") String order_status) {

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.getParams().put("where", "query_order_status");
		page.getParams().put("orderby", "order by co.id desc");
		page.getParams().put("status", "active");
		if (order_status.equals("ordering")) {
			page.getParams().put("order_status", order_status + "-paid");
			page.getParams().put("order_status_1", order_status + "-pending");
		} else {
			page.getParams().put("order_status", order_status);
		}
		
		this.provisionService.queryCustomerOrdersByPage(page);
		model.addAttribute("page", page);
		
		if ("pending".equals(order_status)) {
			model.addAttribute("pendingActive", "active");
			model.addAttribute("panelheading", "Order Pending Customers View");
		} else if ("paid".equals(order_status)) {
			model.addAttribute("paidActive", "active");
			model.addAttribute("panelheading", "Order Paid Customers View");
		} else if ("ordering".equals(order_status)) {
			model.addAttribute("orderingActive", "active");
			model.addAttribute("panelheading", "Order Ordering Customers View");
		} else if ("using".equals(order_status)) {
			model.addAttribute("usingActive", "active");
			model.addAttribute("panelheading", "Order Using Customers View");
		} else if ("cancel".equals(order_status)) {
			model.addAttribute("cancelActive", "active");
			model.addAttribute("panelheading", "Order Cancel Customers View");
		} else if ("discard".equals(order_status)) {
			model.addAttribute("discardActive", "active");
			model.addAttribute("panelheading", "Order Discard Customers View");
		}
		
		Page<CustomerOrder> p = new Page<CustomerOrder>();
		p.getParams().put("where", "query_order_status");
		p.getParams().put("status", "active");
		p.getParams().put("order_status", "pending");
		model.addAttribute("pendingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "paid");
		model.addAttribute("paidSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "ordering-pending");
		p.getParams().put("order_status_1", "ordering-paid");
		model.addAttribute("orderingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		p.getParams().remove("order_status_1");
		
		p.getParams().put("order_status", "using");
		model.addAttribute("usingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "cancel");
		model.addAttribute("cancelSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "discard");
		model.addAttribute("discardSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		model.addAttribute("order_status", order_status);
		

		return "broadband-user/provision/provision-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/status", method = RequestMethod.POST)
	public String changeCustomerOrderStatus(Model model,
			@RequestParam(value = "checkbox_orders", required = false) String[] order_ids,
			@RequestParam(value = "process_way") String process_way,
			@RequestParam(value = "order_status") String order_status,
			@RequestParam(value = "change_order_status") String change_order_status,
			HttpServletRequest req, RedirectAttributes attr) {

		if (order_ids == null) {
			attr.addFlashAttribute("error", "Please choose one customer at least.");
			return "redirect:/broadband-user/provision/customer/view/1/" + order_status;
		}
		
		User userSession = (User)req.getSession().getAttribute("userSession");
		
		List<CustomerOrder> list = new ArrayList<CustomerOrder>();
		
		for (String order_id : order_ids) {
			
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setOrder_status(change_order_status);
			customerOrder.getParams().put("id", Integer.parseInt(order_id));
			
			ProvisionLog log = new ProvisionLog();
			log.setUser_id(userSession.getId());
			log.setProcess_datetime(new Date(System.currentTimeMillis()));
			log.setOrder_sort("customer-order");
			log.setOrder_id_customer(Integer.parseInt(order_id));
			log.setProcess_way(process_way);
			
			customerOrder.setTempProvsionLog(log);
			
			list.add(customerOrder);
		}
		
		this.provisionService.changeCustomerOrderStatus(list);
		
		attr.addFlashAttribute("success", order_ids.length + " order of cusotmers are transformed status (" + process_way + ").");
		
		return "redirect:/broadband-user/provision/customer/view/1/" + order_status;
	}

	
	@RequestMapping(value = "/broadband-user/provision/view/{pageNo}")
	public String provisionLogView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<ProvisionLog> page = new Page<ProvisionLog>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by process_datetime desc");
		this.provisionService.queryProvisionLogsByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/provision/provision-log-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/contact-us/view/{pageNo}/{status}")
	public String contactUsView(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status
			,HttpServletRequest req) {

		Page<ContactUs> page = new Page<ContactUs>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by id desc");
		page.getParams().put("status", status);
		page = this.provisionService.queryContactUssByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");

		// BEGIN QUERY SUM BY STATUS
		Page<ContactUs> pageStatusSum = new Page<ContactUs>();
		pageStatusSum.getParams().put("status", "new");
		model.addAttribute("newSum", this.provisionService.queryContactUssSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "closed");
		model.addAttribute("closedSum", this.provisionService.queryContactUssSumByPage(pageStatusSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/provision/contact-us-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/contact-us/respond", method = RequestMethod.POST)
	public String respondContactUs(Model model
			, @RequestParam("id") Integer id
			, @RequestParam("email") String email
			, @RequestParam("respond_content") String respond_content
			, @RequestParam("pageNo") Integer pageNo
			,HttpServletRequest req) {
		
		ContactUs contactUs = new ContactUs();
		contactUs.getParams().put("id", id);
		contactUs.setStatus("closed");
		contactUs.setRespond_date(new Date());
		contactUs.setRespond_content(respond_content);
		
		this.provisionService.editContactUs(contactUs);
		
		contactUs = this.provisionService.queryContactUsById(id);
		
//		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		Notification notification = this.systemService.queryNotificationBySort("contact-us-response", "email");
//		TMUtils.mailAtValueRetriever(notification, contactUs, companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(email);
		applicationEmail.setSubject("CyberPark Limited's feedback");
		applicationEmail.setContent(respond_content);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		return "redirect:/broadband-user/provision/contact-us/view/"+pageNo+"/new";
	}
	
	@RequestMapping(value = "/broadband-user/provision/sale/view/{pageNo}")
	public String toSalesView(Model model
			, @PathVariable("pageNo") int pageNo
			,HttpServletRequest req) {

		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by user_name");
		page.getParams().put("user_role", "sales");
		
		this.systemService.queryUsersByPage(page);
		model.addAttribute("page", page);
		return "broadband-user/sale/sales-view";
	}

}

package com.tm.broadband.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.ProvisionService;
import com.tm.broadband.service.SystemService;

@Controller
public class ProvisionController {
	
	private ProvisionService provisionService;
	private MailerService mailerService;
	private SystemService systemService;
	private CRMService crmService;

	@Autowired
	public ProvisionController(ProvisionService provisionService, MailerService mailerService, SystemService systemService,
			CRMService crmService) {
		this.provisionService = provisionService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.crmService = crmService;
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
		page.getParams().put("order_status", order_status);
		
		this.provisionService.queryCustomerOrdersByPage(page);
		
		List<CustomerOrder> cos = page.getResults();
		for (int i=0; i<cos.size(); i++) {
			List<CustomerOrderDetail> cods = this.crmService.queryCustomerOrderDetailsByOrderId(cos.get(i).getId());
			for (CustomerOrderDetail cod : cods) {
				if(cod.getDetail_plan_group()!=null && cod.getDetail_plan_group().contains("plan")){
					CustomerOrder coTemp = cos.get(i);
					coTemp.setOrder_total_price(cod.getDetail_price());
					Collections.replaceAll(cos, cos.get(i), coTemp);
				}
			}
		}
		page.setResults(cos);
		model.addAttribute("page", page);
		
		// New Order
		if ("pending".equals(order_status)) {
			model.addAttribute("pendingActive", "active");
			model.addAttribute("panelheading", "New Order &gt; Pending");
		} else if ("paid".equals(order_status)) {
			model.addAttribute("paidActive", "active");
			model.addAttribute("panelheading", "New Order &gt; Paid");
		} else if ("pending-warning".equals(order_status)) {
			model.addAttribute("pendingWarningActive", "active");
			model.addAttribute("panelheading", "New Order &gt; Pending Warning");
		} 
		
		// Provision
		else if ("ordering-pending".equals(order_status)) {
			model.addAttribute("orderingPendingActive", "active");
			model.addAttribute("panelheading", "Provision &gt; Ordering Pending");
		} else if ("ordering-paid".equals(order_status)) {
			model.addAttribute("orderingPaidActive", "active");
			model.addAttribute("panelheading", "Provision &gt; Ordering Paid");
		} else if ("rfs".equals(order_status)) {
			model.addAttribute("rfsActive", "active");
			model.addAttribute("panelheading", "Provision &gt; RFS");
		} 
		
		// In Service
		else if ("using".equals(order_status)) {
			model.addAttribute("usingActive", "active");
			model.addAttribute("panelheading", "In Service &gt; Using");
		} 
		
		// Suspension
		else if ("overflow".equals(order_status)) {
			model.addAttribute("overflowActive", "active");
			model.addAttribute("panelheading", "Suspension &gt; Over Flow");
		} else if ("suspended".equals(order_status)) {
			model.addAttribute("suspendedActive", "active");
			model.addAttribute("panelheading", "Suspension &gt; Suspended");
		} 
		
		// Disconnect
		else if ("waiting-for-disconnect".equals(order_status)) {
			model.addAttribute("waitingForDisconnectActive", "active");
			model.addAttribute("panelheading", "Disconnect &gt; Waiting For Disconnect");
		} else if ("disconnected".equals(order_status)) {
			model.addAttribute("disconnectedActive", "active");
			model.addAttribute("panelheading", "Disconnect &gt; Disconnected");
		}
		
		// Void Order
		else if ("void".equals(order_status)) {
			model.addAttribute("voidActive", "active");
			model.addAttribute("panelheading", "Void Order &gt; Void");
		}
		
		// Upgrade Order
		else if ("upgrade".equals(order_status)) {
			model.addAttribute("upgradeActive", "active");
			model.addAttribute("panelheading", "Upgrade Order &gt; Upgrade");
		}
		
		Page<CustomerOrder> p = new Page<CustomerOrder>();
		p.getParams().put("where", "query_order_status");
		p.getParams().put("status", "active");
		
		// New Order
		p.getParams().put("order_status", "pending");
		model.addAttribute("pendingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "paid");
		model.addAttribute("paidSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "pending-warning");
		model.addAttribute("pendingWarningSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// Provision
		p.getParams().put("order_status", "ordering-pending");
		model.addAttribute("orderingPendingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "ordering-paid");
		model.addAttribute("orderingPaidSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "rfs");
		model.addAttribute("rfsSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// In Service
		p.getParams().put("order_status", "using");
		model.addAttribute("usingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// Suspension
		p.getParams().put("order_status", "overflow");
		model.addAttribute("overflowSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "suspended");
		model.addAttribute("suspendedSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// Disconnect
		p.getParams().put("order_status", "waiting-for-disconnect");
		model.addAttribute("waitingForDisconnectSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		p.getParams().put("order_status", "disconnected");
		model.addAttribute("disconnectedSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// Void Order
		
		p.getParams().put("order_status", "void");
		model.addAttribute("voidSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		// Upgrade Order
		p.getParams().put("order_status", "upgrade");
		model.addAttribute("upgradeSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
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
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
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
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		contactUs.setUser_id(user.getId());
		
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

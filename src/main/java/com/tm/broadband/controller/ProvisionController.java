package com.tm.broadband.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.ProvisionService;

@Controller
public class ProvisionController {
	
	private ProvisionService provisionService;
	private CRMService crmService;

	@Autowired
	public ProvisionController(ProvisionService provisionService, CRMService crmService) {
		this.provisionService = provisionService;
		this.crmService = crmService;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/view/{pageNo}/{order_status}")
	public String provisionCustomer(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "order_status") String order_status) {

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.getParams().put("where", "query_order_status");
		page.getParams().put("orderby", "order by co.order_create_date");
		page.getParams().put("status", "active");
		page.getParams().put("order_status", order_status);
		this.provisionService.queryCustomerOrdersByPage(page);
		model.addAttribute("page", page);
		
		if ("paid".equals(order_status)) {
			model.addAttribute("paidActive", "active");
			model.addAttribute("panelheading", "Order Paid Customers View");
		} else if ("ordering".equals(order_status)) {
			model.addAttribute("orderingActive", "active");
			model.addAttribute("panelheading", "Order Ordering Customers View");
		} else if ("using".equals(order_status)) {
			model.addAttribute("usingActive", "active");
			model.addAttribute("panelheading", "Order Using Customers View");
		}
		
		Page<CustomerOrder> p = new Page<CustomerOrder>();
		p.getParams().put("where", "query_order_status");
		p.getParams().put("status", "active");
		p.getParams().put("order_status", "paid");
		model.addAttribute("paidSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		p.getParams().put("order_status", "ordering");
		model.addAttribute("orderingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		p.getParams().put("order_status", "using");
		model.addAttribute("usingSum", this.provisionService.queryCustomerOrdersSumByPage(p));
		
		model.addAttribute("order_status", order_status);
		

		return "broadband-user/provision/provision-view";
	}
	
	/*@RequestMapping(value = "/broadband-user/provision/customer/{id}")
	@ResponseBody
	public Customer queryCustomerOrderWithCustomer(Model model,
			@PathVariable(value = "id") int id) {

		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		
		return customer;
	}*/
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/{id}")
	@ResponseBody
	public CustomerOrder queryCustomerOrderWithCustomer(Model model,
			@PathVariable(value = "id") int id) {

		CustomerOrder customerOrder = this.provisionService.queryCustomerOrderById(id);
		return customerOrder;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/status", method = RequestMethod.POST)
	public String changeCustomerOrderStatus(Model model,
			@RequestParam(value = "checkbox_customers", required = false) String[] customer_ids,
			@RequestParam(value = "process_way") String process_way,
			@RequestParam(value = "order_status") String order_status,
			HttpServletRequest req, RedirectAttributes attr) {

		if (customer_ids == null) {
			attr.addFlashAttribute("error", "Please choose one customer at least.");
			return "redirect:/broadband-user/provision/customer/view/1/" + order_status;
		}
		
		User userSession = (User)req.getSession().getAttribute("userSession");
		
		
		attr.addFlashAttribute("success", customer_ids.length + " order of cusotmers are transformed status (" + process_way + ").");
		
		return "redirect:/broadband-user/provision/customer/view/1/" + order_status;
	}

}

package com.tm.broadband.controller;

import java.util.List;

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
	
	@RequestMapping(value = "/broadband-user/provision/customer/view/{pageNo}")
	public String provisionCustomer(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by register_date");
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/provision/provision-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/{id}")
	@ResponseBody
	public Customer getCustomerWithCustomerOrderInformation(Model model,
			@PathVariable(value = "id") int id) {

		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		
		return customer;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/status", method = RequestMethod.POST)
	public String changeCustomerOrderStatus(Model model,
			@RequestParam(value = "checkbox_customers", required = false) String[] customer_ids,
			@RequestParam(value = "process_way") String process_way,
			HttpServletRequest req, RedirectAttributes attr) {

		if (customer_ids == null) {
			attr.addFlashAttribute("error", "Please choose one customer at least.");
			return "redirect:/broadband-user/provision/customer/view/1";
		}
		
		User userSession = (User)req.getSession().getAttribute("userSession");
		
		//this.provisionService.activeRegisterCustomers(customer_ids, userSession.getId());
		attr.addFlashAttribute("success", customer_ids.length + " cusotmers are transformed status (verify to active) and order status (" + process_way + ").");
		
		return "redirect:/broadband-user/provision/customer/view/1";
	}

}

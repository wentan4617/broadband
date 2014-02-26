package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.CRMService;

@Controller
public class CRMController {

	private CRMService crmService;

	@Autowired
	public CRMController(CRMService crmService) {
		this.crmService = crmService;
	}

	@RequestMapping("/broadband-user/crm/customer/view/{pageNo}")
	public String customerView(Model model, @PathVariable("pageNo") int pageNo) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by c.status");
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("customerQuery", new Customer());
		return "broadband-user/crm/customer-view";
	}

	@RequestMapping("/broadband-user/crm/customer/query/{pageNo}")
	public String customerQuery(Model model, @PathVariable("pageNo") int pageNo,
			@ModelAttribute("customerQuery") Customer customer, RedirectAttributes attr) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by c.status");
		page.getParams().put("id", customer.getId());
		page.getParams().put("login_name", customer.getLogin_name());
		page.getParams().put("phone", customer.getPhone());
		page.getParams().put("cellphone", customer.getCellphone());
		page.getParams().put("email", customer.getEmail());
		page.getParams().put("svlan", customer.getSvlan());
		page.getParams().put("cvlan", customer.getCvlan());
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		return "broadband-user/crm/customer-view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/edit/{id}")
	public String toPlanEdit(Model model,
			@PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Customer Edit");
		model.addAttribute("action", "/broadband-user/plan/edit");
		
		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		
		model.addAttribute("customer", customer);
		
		return "broadband-user/crm/customer";
	}

}

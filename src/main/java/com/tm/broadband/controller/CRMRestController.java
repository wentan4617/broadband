package com.tm.broadband.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@RestController
@SessionAttributes({ "customer", "customerOrder", "hardwares", "plans" })
public class CRMRestController {
	
	private CRMService crmService;

	@Autowired
	public CRMRestController(CRMService crmService) {
		this.crmService = crmService;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/create", method = RequestMethod.POST)
	public JSONBean<Customer> doCustomerCreate(Model model, 
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer, BindingResult result) {

		JSONBean<Customer> json = this.returnJsonCustomer(customer, result);
		
		if (result.hasErrors()) {
			return json;
		}
		
		customer.setRegister_date(new Date());
		customer.setActive_date(new Date());
		customer.setBalance(0d);
		
		if ("save".equals(customer.getAction())) {
			this.crmService.createCustomer(customer);
			json.setUrl("/broadband-user/crm/customer/query/redirect");
		} else if ("next".equals(customer.getAction())) {
			model.addAttribute("customer", customer);
			json.setUrl("/broadband-user/crm/customer/order/create");
		}
		
		return json;
	}
	
	private JSONBean<Customer> returnJsonCustomer(Customer customer, BindingResult result) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		Customer cValid = new Customer();
		cValid.getParams().put("where", "query_exist_customer_by_mobile");
		cValid.getParams().put("cellphone", customer.getCellphone());
		int count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("cellphone", "is already in use");
			return json;
		}
		
		cValid.getParams().put("where", "query_exist_customer_by_email");
		cValid.getParams().put("email", customer.getEmail());
		count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("email", "is already in use");
			return json;
		}
		return json;
	}

}

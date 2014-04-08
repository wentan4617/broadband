package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.ChangePasswordValidatedMark;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.UserLoginValidatedMark;

@RestController
public class CustomerRestController {
	
	private CRMService crmService;

	@Autowired
	public CustomerRestController(CRMService crmService) {
		this.crmService = crmService;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONBean<Customer> login(
			@Validated(CustomerLoginValidatedMark.class) Customer customer, BindingResult result, 
			HttpServletRequest req) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		customer.getParams().put("where", "when_login");
		customer.getParams().put("cellphone", customer.getLogin_name());
		customer.getParams().put("email", customer.getLogin_name());
		customer.getParams().put("password", customer.getPassword());
		customer.getParams().put("status", "active");
		Customer customerSession = this.crmService.queryCustomerWhenLogin(customer);

		if (customerSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}
		
		req.getSession().setAttribute("customerSession", customerSession);
		json.setUrl("/customer/home/redirect");

		return json;
	}
	
	@RequestMapping(value = "/customer/change-password", method = RequestMethod.POST)
	public JSONBean<Customer> changePassword(
			@Validated(ChangePasswordValidatedMark.class) Customer customer, BindingResult result, 
			HttpServletRequest req) {
		
		Customer customerSession = (Customer)req.getSession().getAttribute("customerSession");
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		if (!customer.getPassword().equals(customer.getCk_password())) {
			json.getErrorMap().put("password", "new password and confirm password is different");
			return json;
		}
		
		if (!customer.getOld_password().equals(customerSession.getPassword())) {
			json.getErrorMap().put("old_password", "old password is wrong");
			return json;
		}
 
		Customer tempC = new Customer();
		tempC.setPassword(customer.getPassword());
		tempC.getParams().put("id", customerSession.getId());
		this.crmService.editCustomer(tempC);
		customerSession.setPassword(customer.getPassword());
		
		req.getSession().setAttribute("customerSession", customerSession);
		json.setUrl("/customer/change-password/redirect");

		return json;
	}


}

package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@RestController
@SessionAttributes("customer")
public class SaleRestController {
	
	private CRMService crmService;

	@Autowired
	public SaleRestController(CRMService crmService) {
		this.crmService = crmService;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order", method = RequestMethod.POST)
	public JSONBean<Customer> doOrder(Model model,
			@Validated(CustomerValidatedMark.class) Customer customer, BindingResult result, HttpServletRequest req) {

		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		int count = this.crmService.queryExistCustomerByLoginName(customer.getLogin_name());

		if (count > 0) {
			result.rejectValue("login_name", "duplicate", "is already in use");
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		if (!customer.getPassword().equals(customer.getCk_password())) {
			result.rejectValue("ck_password", "incorrectConfirmPassowrd", "Confirm the password and the new password is different");
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		model.addAttribute("customer", customer);
		
		json.setUrl("/broadband-user/sale/online/ordering/order/confirm");

		return json;
	}

}

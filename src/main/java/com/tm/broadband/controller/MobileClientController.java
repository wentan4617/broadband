package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.service.CRMService;

@RestController
public class MobileClientController {
	
	private CRMService crmService;
	
	@Autowired
	public MobileClientController(CRMService crmService) {
		this.crmService = crmService;
	}



	@RequestMapping(value="/broadband-user/mobile-client/login", method=RequestMethod.POST)
	public JSONBean<Customer> doLogin(Model model,
		@RequestParam("login_account") String login_account,
		@RequestParam("login_password") String login_password){
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		
		Customer cLogin = new Customer();
		cLogin.getParams().put("where", "when_login");
		cLogin.getParams().put("status", "active");
		cLogin.getParams().put("cellphone", login_account);
		cLogin.getParams().put("email", login_account);
		cLogin.getParams().put("password", login_password);
		
		Customer c = this.crmService.queryCustomerWhenLogin(cLogin);
		
		if(c!=null){
			json.setModel(c);
			json.getSuccessMap().put("alert-success", "Welcome Back!!");
		} else {
			json.getErrorMap().put("alert-error", "Please check your account or password!");
		}
		
		return json;
	}

}

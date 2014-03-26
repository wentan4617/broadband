package com.tm.broadband.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.TestUser;
import com.tm.broadband.model.User;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.validator.mark.UserLoginValidatedMark;

@Controller
public class TestController {

	private SystemService systemService;

	@Autowired
	public TestController(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping("/test/json")
	public String toTestJson() {
		return "test/test-json";
	}
	
	@RequestMapping("/test/alert")
	public String redirectIndex(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Welcome to CyberTech Broadband Manager System.");
		return "redirect:/broadband-user/index";
	}
	
	@RequestMapping(value = "/test/json",  method = RequestMethod.POST)
	@ResponseBody //不加这个注解，报404
	public JSONBean<User> doTestJson(
			@Validated(UserLoginValidatedMark.class) User user, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<User> json = new JSONBean<User>();
		json.setModel(user);
		
		if (result.hasErrors()) {
			
			List<FieldError> fields = result.getFieldErrors();
			for (FieldError field: fields) {
				json.getErrorMap().put(field.getField(), field.getDefaultMessage());
			}
			return json;
		}
		
		User userSession = this.systemService.queryUserLogin(user);

		if (userSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}

		req.getSession().setAttribute("userSession", userSession);
		json.setUrl("/test/alert");
		
		return json;
	}

}

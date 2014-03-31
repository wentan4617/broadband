package com.tm.broadband.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.User;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.UserLoginValidatedMark;

@RestController
public class SystemRestController {

	private SystemService systemService;

	@Autowired
	public SystemRestController(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value = "/broadband-user/login", method = RequestMethod.POST)
	public JSONBean<User> userLogin(
			@Validated(UserLoginValidatedMark.class) User user, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<User> json = new JSONBean<User>();
		json.setModel(user);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		User userSession = this.systemService.queryUserLogin(user);

		if (userSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}
		
		req.getSession().setAttribute("userSession", userSession);
		json.setUrl("/broadband-user/index/redirect");

		return json;
	}

}

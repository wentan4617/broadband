package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.validator.mark.UserLoginValidatedMark;
import com.tm.broadband.model.ResponseMessage;
import com.tm.broadband.model.User;
import com.tm.broadband.service.SystemService;

/**
 * system controller
 * 
 * @author Cook1fan
 * 
 */
@Controller
public class SystemController {

	private SystemService systemService;

	@Autowired
	public SystemController(SystemService systemService) {
		this.systemService = systemService;
	}

	@RequestMapping(value = { "/broadband-user", "/broadband-user/login" })
	public String userHome(Model model) {

		model.addAttribute("user", new User());
		return "broadband-user/login";
	}

	@RequestMapping(value = "/broadband-user/login", method = RequestMethod.POST)
	public String userLogin(
			Model model,
			@ModelAttribute("user") @Validated(UserLoginValidatedMark.class) User user,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "broadband-user/login";
		}

		User userSession = this.systemService.queryUserLogin(user);

		if (userSession == null) {
			model.addAttribute("error", "Incorrect account or password");
			return "broadband-user/login";
		}

		req.getSession().setAttribute("userSession", userSession);
		attr.addFlashAttribute("success", "Welcome to CyberTech Broadband Manager System.");

		return "redirect:/broadband-user/index";
	}
	
	@RequestMapping(value = "/broadband-user/index")
	public String userIndex(Model model) {
		return "broadband-user/index";
	}

	@RequestMapping(value = "/broadband-user/signout")
	public String signout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/broadband-user/login";
	}

}

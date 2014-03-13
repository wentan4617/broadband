package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.validator.mark.CompanyDetailValidatedMark;
import com.tm.broadband.validator.mark.NotificationValidatedMark;
import com.tm.broadband.validator.mark.UserLoginValidatedMark;
import com.tm.broadband.validator.mark.UserValidatedMark;

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

	/*
	 * User Controller begin
	 */

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
		attr.addFlashAttribute("success",
				"Welcome to CyberTech Broadband Manager System.");

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

	@RequestMapping(value = "/broadband-user/system/user/create")
	public String toUserCreate(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("panelheading", "User Create");
		model.addAttribute("action", "/broadband-user/system/user/create");

		return "broadband-user/system/user";
	}

	@RequestMapping(value = "/broadband-user/system/user/create", method = RequestMethod.POST)
	public String doUserCreate(
			Model model,
			@ModelAttribute("user") @Validated(UserValidatedMark.class) User user,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		model.addAttribute("panelheading", "User Create");
		model.addAttribute("action", "/broadband-user/system/user/create");

		if (result.hasErrors()) {
			return "broadband-user/system/user";
		}
		int count = this.systemService.queryExistUserByName(user
				.getLogin_name());

		if (count > 0) {
			result.rejectValue("login_name", "duplicate", "");
			return "broadband-user/system/user";
		}

		this.systemService.saveUser(user);
		attr.addFlashAttribute("success", "Create User " + user.getLogin_name()
				+ " is successful.");

		return "redirect:/broadband-user/system/user/view/1";
	}

	@RequestMapping(value = "/broadband-user/system/user/view/{pageNo}")
	public String userView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by user_role");
		this.systemService.queryUsersByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/system/user-view";
	}

	@RequestMapping(value = "/broadband-user/system/user/edit/{id}")
	public String toUserEdit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("panelheading", "User Edit");
		model.addAttribute("action", "/broadband-user/system/user/edit");

		User user = this.systemService.queryUserById(id);

		model.addAttribute("user", user);

		return "broadband-user/system/user";
	}

	@RequestMapping(value = "/broadband-user/system/user/edit", method = RequestMethod.POST)
	public String doUserEdit(
			Model model,
			@ModelAttribute("user") @Validated(UserValidatedMark.class) User user,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		model.addAttribute("panelheading", "User Edit");
		model.addAttribute("action", "/broadband-user/system/user/edit");

		if (result.hasErrors()) {
			return "broadband-user/system/user";
		}

		int count = this.systemService.queryExistNotSelfUserfByName(
				user.getLogin_name(), user.getId());

		if (count > 0) {
			result.rejectValue("login_name", "duplicate", "");
			return "broadband-user/system/user";
		}

		user.getParams().put("id", user.getId());
		
		this.systemService.editUser(user);

		attr.addFlashAttribute("success", "Edit User " + user.getUser_name()
				+ " is successful.");

		return "redirect:/broadband-user/system/user/view/1";
	}

	/*
	 * User Controller end
	 */

	/*
	 * Notification Controller begin
	 */

	@RequestMapping(value = "/broadband-user/system/notification/create")
	public String toNotificationCreate(Model model) {

		model.addAttribute("notification", new Notification());
		model.addAttribute("panelheading", "Notification Create");
		model.addAttribute("action", "/broadband-user/system/notification/create");

		return "broadband-user/system/notification";
	}

	@RequestMapping(value = "/broadband-user/system/notification/create", method = RequestMethod.POST)
	public String doNotificationCreate(
			Model model,
			@ModelAttribute("notification") @Validated(NotificationValidatedMark.class) Notification notification,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		model.addAttribute("panelheading", "Notification Create");
		model.addAttribute("action", "/broadband-user/system/notification/create");

		if (result.hasErrors()) {
			return "broadband-user/system/notification";
		}

		this.systemService.saveNotification(notification);
		attr.addFlashAttribute("success", "Create Notification " + notification.getTitle()
				+ " is successful.");

		return "redirect:/broadband-user/system/notification/view/1";
	}

	@RequestMapping(value = "/broadband-user/system/notification/view/{pageNo}")
	public String notificationView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Notification> page = new Page<Notification>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by type");
		this.systemService.queryNotificationsByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/system/notification-view";
	}

	@RequestMapping(value = "/broadband-user/system/notification/edit/{id}")
	public String toNotificationEdit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("panelheading", "Notification Edit");
		model.addAttribute("action", "/broadband-user/system/notification/edit");

		Notification notification = this.systemService.queryNotificationById(id);

		model.addAttribute("notification", notification);

		return "broadband-user/system/notification";
	}

	@RequestMapping(value = "/broadband-user/system/notification/edit", method = RequestMethod.POST)
	public String doNotificationEdit(
			Model model,
			@ModelAttribute("notification") @Validated(NotificationValidatedMark.class) Notification notification,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		model.addAttribute("panelheading", "Notification Edit");
		model.addAttribute("action", "/broadband-user/system/notification/edit");

		if (result.hasErrors()) {
			return "broadband-user/system/notification";
		}
		notification.getParams().put("id", notification.getId());
		this.systemService.editNotification(notification);

		attr.addFlashAttribute("success", "Edit Notification " + notification.getTitle()
				+ " is successful.");

		return "redirect:/broadband-user/system/notification/view/1";
	}
	
	/*
	 * Notification Controller end
	 */
	
	/*
	 * CompanyDetail Controller begin
	 */

	@RequestMapping(value = "/broadband-user/system/company-detail/pre-edit")
	public String toCompanyDetailEdit(Model model) {

		model.addAttribute("panelheading", "Company Detail Edit");
		model.addAttribute("action", "/broadband-user/system/company-detail/edit");

		CompanyDetail companyDetail = this.systemService.queryCompanyDetail();

		model.addAttribute("companyDetail", companyDetail);

		return "broadband-user/system/company-detail";
	}
	
	@RequestMapping(value = "/broadband-user/system/company-detail/edit", method = RequestMethod.POST)
	public String doCompanyDetailEdit(
			Model model,
			@ModelAttribute("companyDetail") @Validated(CompanyDetailValidatedMark.class) CompanyDetail companyDetail,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("panelheading", "Company Detail Edit");
		model.addAttribute("action", "/broadband-user/system/company-detail/edit");

		if (result.hasErrors()) {
			return "/broadband-user/system/company-detail";
		}

		this.systemService.editCompanyDetail(companyDetail);

		attr.addFlashAttribute("success", "Edit company detail successful.");

		return "redirect:/broadband-user/system/company-detail/pre-edit";
	}
	/*
	 * CompanyDetail Controller end
	 */
}

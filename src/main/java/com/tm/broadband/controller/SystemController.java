package com.tm.broadband.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.ManualManipulationRecord;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.RegisterCustomer;
import com.tm.broadband.model.User;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CompanyDetailValidatedMark;
import com.tm.broadband.validator.mark.NotificationValidatedMark;
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
	private BillingService billingService;
	private CRMService crmService;

	@Autowired
	public SystemController(SystemService systemService
			,BillingService billingService
			,CRMService crmService) {
		this.systemService = systemService;
		this.billingService = billingService;
		this.crmService = crmService;
	}

	/*
	 * User Controller begin
	 */

	@RequestMapping(value = { "/broadband-user", "/broadband-user/login" })
	public String userHome(Model model) {
		
		model.addAttribute("title", "CyberPark Broadband Manager System");
		return "broadband-user/login";
	}
	
	@RequestMapping("/broadband-user/index/redirect")
	public String redirectIndex(RedirectAttributes attr) {
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
		
		// iterating auths of this user
		if (user.getAuth() != null && !"".equals(user.getAuth())) {
			user.setAuthArray(user.getAuth().split(","));
		}

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

	@RequestMapping(value = "/broadband-user/system/company-detail/edit")
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

		return "redirect:/broadband-user/system/company-detail/edit";
	}
	/*
	 * CompanyDetail Controller end
	 */
	
	/**
	 * 
	 */

	@RequestMapping(value = "/broadband-user/system/chart/customer-register/{yearMonth}")
	public String toChartCustomerRegister(Model model,
			@PathVariable(value = "yearMonth") String yearMonth) {
		
		model.addAttribute("panelheading", "Customer Register Statistics");
		
		/**
		 * WEEK STATISTIC BEGIN
		 */
		List<RegisterCustomer> weekRegisterStatistics = new ArrayList<RegisterCustomer>();
		TMUtils.thisWeekDateForRegisterStatistic(weekRegisterStatistics);
		
		List<Customer> weekCustomers = this.systemService.queryCustomersByRegisterDate(
				// monday
				weekRegisterStatistics.get(0).getRegisterDate()
				// sunday
				,weekRegisterStatistics.get(weekRegisterStatistics.size()-1).getRegisterDate()
			);
		for (RegisterCustomer registerCustomer : weekRegisterStatistics) {
			for (Customer customer : weekCustomers) {
				if(TMUtils.dateFormatYYYYMMDD(registerCustomer.getRegisterDate())
						.equals(TMUtils.dateFormatYYYYMMDD(customer.getRegister_date()))){
					registerCustomer.setRegisterCount(registerCustomer.getRegisterCount()+1);
				}
			}
		}
		
		model.addAttribute("weekRegisterStatistics", weekRegisterStatistics);
		/**
		 * WEEK STATISTIC END
		 */
		
		/**
		 * MONTH STATISTIC BEGIN
		 */

		Integer year = null;
		Integer month = null;
		
		if(yearMonth.equals("0")){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
			// get this month
			month = c.get(Calendar.MONTH)+1;
			yearMonth = year.toString()+"-"+month.toString();
		} else {
			String[] temp = yearMonth.split("-");
			year = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
		}
		
		List<RegisterCustomer> monthRegisterStatistics = new ArrayList<RegisterCustomer>();
		TMUtils.thisMonthDateForRegisterStatistic(year, month, monthRegisterStatistics);
		List<Customer> monthCustomers = this.systemService.queryCustomersByRegisterDate(
				// first date of month
				monthRegisterStatistics.get(0).getRegisterDate()
				// last date of month
				,monthRegisterStatistics.get(monthRegisterStatistics.size()-1).getRegisterDate()
			);
		for (RegisterCustomer registerCustomer : monthRegisterStatistics) {
			for (Customer customer : monthCustomers) {
				
				// if registerCustomer's register date(filtered monthly dates) equals to customer's register date
				if(TMUtils.dateFormatYYYYMMDD(registerCustomer.getRegisterDate()).equals(TMUtils.dateFormatYYYYMMDD(customer.getRegister_date()))){
					registerCustomer.setRegisterCount(registerCustomer.getRegisterCount()+1);
				}
			}
		}
		
		model.addAttribute("monthRegisterStatistics", monthRegisterStatistics);
		model.addAttribute("yearMonth",yearMonth);
		
		/**
		 * MONTH STATISTIC END
		 */
		
		
		return "broadband-user/system/customer-register-chart";
	}

	
	// BEGIN Call Charge Rate
	@RequestMapping(value = "/broadband-user/system/call_charge_rate/view/{pageNo}")
	public String callChargeRateView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<CallChargeRate> page = new Page<CallChargeRate>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by customer_type");
		this.billingService.queryCallChargeRatesByPage(page);
		model.addAttribute("page", page);

		return "/broadband-user/system/call-charge-rate-view";
	}

	@RequestMapping(value = "/broadband-user/system/call_charge_rate/create")
	public String toCallChargeRateCreate(Model model) {

		model.addAttribute("ccr", new CallChargeRate());
		model.addAttribute("panelheading", "Call Charge Rate Create");
		model.addAttribute("action", "/broadband-user/system/call_charge_rate/create");

		return "broadband-user/system/create-call-billing";
	}

	@RequestMapping(value = "/broadband-user/system/call_charge_rate/create", method = RequestMethod.POST)
	public String doCallChargeRateCreate(
			Model model,
			@ModelAttribute("callChargeRate") CallChargeRate ccr,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		this.billingService.createCallChargeRate(ccr);
		
		attr.addFlashAttribute("success", "Create Call Charge Rate is successful.");

		return "redirect:/broadband-user/system/call_charge_rate/view/1";
	}
	// END Call Charge Rate


	// BEGIN ManualManipulationRecord
	@RequestMapping(value = "/broadband-user/system/manual-manipulation-record/view/{pageNo}/{manipulation_type}")
	public String manualManipulationRecordView(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "manipulation_type") String manipulation_type) {

		Page<ManualManipulationRecord> page = new Page<ManualManipulationRecord>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY manipulation_time DESC");
		page.getParams().put("manipulation_type", manipulation_type);
		this.systemService.queryManualManipulationRecordsByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/system/manual-manipulation/manual-termed-invoice-view";
	}

	@RequestMapping(value = "/broadband-user/system/manual-manipulation-record/create/{pageNo}", method = RequestMethod.POST)
	public String doManualManipulationRecordCreate(
			@PathVariable(value = "pageNo") int pageNo,
			@ModelAttribute("manualManipulationRecord") ManualManipulationRecord mmr,
			HttpServletRequest req,
			RedirectAttributes attr) {
		
		try {
			this.crmService.createTermPlanInvoice();
		} catch (ParseException e) { e.printStackTrace(); }
		
		// RECORDING MANIPULATOR'S DETAILS
		User user = (User) req.getSession().getAttribute("userSession");
		mmr.setAdmin_id(user.getId());
		mmr.setAdmin_name(user.getUser_name());
		mmr.setManipulation_time(new Date());
		mmr.setManipulation_name("Manually Generate Termed Invoices");
		this.systemService.createManualManipulationRecord(mmr);
		
		attr.addFlashAttribute("success", "Create Manual Manipulation Record is successful.");

		return "redirect:/broadband-user/system/manual-manipulation-record/view/" + pageNo + "/" + mmr.getManipulation_type();
	}
	// END ManualManipulationRecord
	
}

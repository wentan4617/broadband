package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.InviteRates;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.PDFResources;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.SEO;
import com.tm.broadband.model.StatisticCustomer;
import com.tm.broadband.model.Ticket;
import com.tm.broadband.model.User;
import com.tm.broadband.model.WebsiteStaticResources;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.ProvisionService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.FileUtils;
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
	private ProvisionService provisionService;
	private CRMService crmService;

	@Autowired
	public SystemController(SystemService systemService,
			ProvisionService provisionService,
			CRMService crmService) {
		this.systemService = systemService;
		this.provisionService = provisionService;
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
		attr.addFlashAttribute("success", "Welcome to Broadband Management System.");
		return "redirect:/broadband-user/index";
	}

	@RequestMapping(value = "/broadband-user/index")
	public String userIndex(Model model,
			HttpServletRequest req) {

		// BEGIN QUERY SUM BY CONTACT US STATUS
		Page<ContactUs> pageContactUsStatusSum = new Page<ContactUs>();
		pageContactUsStatusSum.getParams().put("status", "new");
		model.addAttribute("newContactUsSum", this.provisionService.queryContactUssSumByPage(pageContactUsStatusSum));
		// END QUERY SUM BY CONTACT US STATUS
		

		User user = (User) req.getSession().getAttribute("userSession");
		
		Page<Ticket> pageSum = new Page<Ticket>();
		pageSum.getParams().put("where", "query_by_public_protected");
		pageSum.getParams().put("public_protected", "public_protected");
		pageSum.getParams().put("protected_viewer", user.getId());
		pageSum.getParams().put("double_not_yet_viewer", user.getId());
		pageSum.getParams().put("existing_customer", true);
		req.getSession().setAttribute("existingSum", this.crmService.queryTicketsBySum(pageSum));
		pageSum.getParams().put("existing_customer", false);
		req.getSession().setAttribute("newSum", this.crmService.queryTicketsBySum(pageSum));
		
		
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
		page.setPageSize(50);
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
		page.setPageSize(50);
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
	
	/*
	 * SEO Controller begin
	 */

	@RequestMapping(value = "/broadband-user/system/seo/edit")
	public String toSEOEdit(Model model) {

		model.addAttribute("panelheading", "SEO Edit");
		model.addAttribute("action", "/broadband-user/system/seo/edit");

		SEO seo = this.systemService.querySEO();

		model.addAttribute("seo", seo);

		return "broadband-user/system/seo";
	}
	
	@RequestMapping(value = "/broadband-user/system/seo/edit", method = RequestMethod.POST)
	public String doSEOEdit(
			Model model,
			@ModelAttribute("seo") SEO seo,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("panelheading", "SEO Edit");
		model.addAttribute("action", "/broadband-user/system/seo/edit");

		if (result.hasErrors()) {
			return "/broadband-user/system/seo";
		}

		this.systemService.editSEO(seo);

		attr.addFlashAttribute("success", "Edit SEO successful.");

		return "redirect:/broadband-user/system/seo/edit";
	}
	/*
	 * SEO Controller end
	 */
	
	/*
	 * InviteRates Controller begin
	 */

	@RequestMapping(value = "/broadband-user/system/ir/edit")
	public String toInviteRates(Model model) {

		model.addAttribute("panelheading", "Invite Rates Edit");
		model.addAttribute("action", "/broadband-user/system/ir/edit");

		InviteRates ir = this.systemService.queryInviteRate();

		model.addAttribute("ir", ir);

		return "broadband-user/system/invite_rates";
	}
	
	@RequestMapping(value = "/broadband-user/system/ir/edit", method = RequestMethod.POST)
	public String doInviteRatesEdit(
			Model model,
			@ModelAttribute("ir") InviteRates ir,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("panelheading", "Invite Rates Edit");
		model.addAttribute("action", "/broadband-user/system/ir/edit");

		if (result.hasErrors()) {
			return "/broadband-user/system/invite_rates";
		}

		this.systemService.editInviteRate(ir);

		attr.addFlashAttribute("success", "Edit Invite Rates successful.");

		return "redirect:/broadband-user/system/ir/edit";
	}
	/*
	 * InviteRates Controller end
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
		List<StatisticCustomer> weekRegisterStatistics = new ArrayList<StatisticCustomer>();
		TMUtils.thisWeekDateForRegisterStatistic(weekRegisterStatistics);
		
		Customer cQuery = new Customer();
		cQuery.getParams().put("register_date1",
				// monday
				weekRegisterStatistics.get(0).getRegisterDate());
		cQuery.getParams().put("register_date2",
				// sunday
				weekRegisterStatistics.get(weekRegisterStatistics.size()-1).getRegisterDate());
		List<Customer> weekCustomers = this.crmService.queryCustomers(cQuery);
		for (StatisticCustomer registerCustomer : weekRegisterStatistics) {
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
		
		List<StatisticCustomer> monthRegisterStatistics = new ArrayList<StatisticCustomer>();
		TMUtils.thisMonthDateForRegisterStatistic(year, month, monthRegisterStatistics);
		Customer cQueryMonth = new Customer();
		cQueryMonth.getParams().put("register_date1",
				// first date of month
				monthRegisterStatistics.get(0).getRegisterDate());
		cQueryMonth.getParams().put("register_date2",
				// last date of month
				monthRegisterStatistics.get(monthRegisterStatistics.size()-1).getRegisterDate());
		List<Customer> monthCustomers = this.crmService.queryCustomers(cQuery);
		for (StatisticCustomer registerCustomer : monthRegisterStatistics) {
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
	
	@RequestMapping(value = "/broadband-user/user/is-provision", method = RequestMethod.POST)
	public String deletePlans(
			Model model,
			@RequestParam(value = "checkbox_users", required = false) String[] user_ids,
			@RequestParam("is_provision") Boolean is_provision,
			HttpServletRequest req, RedirectAttributes attr) {

		if (user_ids == null) {
			attr.addFlashAttribute("error", "Please choose one user at least.");
			return "redirect:/broadband-user/system/user/view/1";
		} else {
			List<User> users = new ArrayList<User>();
			for (String id: user_ids) {
				User user = new User();
				user.setIs_provision(is_provision);
				user.getParams().put("id", Integer.parseInt(id));
				users.add(user);
			}
			this.systemService.editUsers(users);
		}
		
		attr.addFlashAttribute("success", "Delete selected user(s) successfully!");
		return "redirect:/broadband-user/system/user/view/1";
	}
	
	@RequestMapping(value = "/broadband-user/system/website_editable_details", method = RequestMethod.GET)
	public String toWebsiteEditDetails(
			Model model){
		
		model.addAttribute("panelheading", "View Website Editable Details");
		
		return "broadband-user/system/website-editable-details";
	}
	
	@RequestMapping(value = "/broadband-user/system/plan_introductions", method = RequestMethod.GET)
	public String toPlanIntroductions(
			Model model){
		
		model.addAttribute("panelheading", "View Plan Introductions");
		
		return "broadband-user/system/plan-introductions";
	}
	
	@RequestMapping(value = "/broadband-user/system/website_static_resources", method = RequestMethod.GET)
	public String toWebsiteStaticResources(
			Model model){
		
		model.addAttribute("panelheading", "View Website Static Resources");
		
		model.addAttribute("wsr", this.systemService.queryWebsiteStaticResource(null));
		
		return "broadband-user/system/website-static-resources";
	}
	
	@RequestMapping(value = "/broadband-user/system/website_static_resources/upload")
	public String doWebsiteStaticResourcesUpload(Model model,
			@RequestParam("facebook_url") String facebook_url,
			@RequestParam("googleplus_url") String googleplus_url,
			@RequestParam("twitter_url") String twitter_url,
			@RequestParam("youtube_url") String youtube_url,
			@RequestParam("logo_path") MultipartFile logo_file,
			@RequestParam("facebook_lg_path") MultipartFile facebook_lg_file,
			@RequestParam("googleplus_lg_path") MultipartFile googleplus_lg_file,
			@RequestParam("twitter_lg_path") MultipartFile twitter_lg_file,
			@RequestParam("youtube_lg_path") MultipartFile youtube_lg_file,
			@RequestParam("two_dimensional_code_path") MultipartFile two_dimensional_code_file,
			RedirectAttributes attr,
			HttpServletRequest req) throws IllegalStateException, IOException{
		
		WebsiteStaticResources wsr = new WebsiteStaticResources();
		wsr.setFacebook_url(facebook_url);
		wsr.setGoogleplus_url(googleplus_url);
		wsr.setTwitter_url(twitter_url);
		wsr.setYoutube_url(youtube_url);
		
		Boolean isFile = false;
		
		if(!"".equals(facebook_url) || !"".equals(googleplus_url) || !"".equals(twitter_url) || !"".equals(youtube_url)){
			isFile = true;
		}
		
		String order_path = req.getSession().getServletContext().getRealPath("/") + File.separator + "upload" + File.separator + "front-end" + File.separator;
		
		File directory = new File(order_path);
		
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		if(!logo_file.isEmpty()){
			wsr.setLogo_path("upload/front-end/logo.png");
			logo_file.transferTo(new File(order_path + "logo.png"));
			isFile = true;
		}
		if(!facebook_lg_file.isEmpty()){
			wsr.setFacebook_lg_path("upload/front-end/facebook_lg.png");
			facebook_lg_file.transferTo(new File(order_path + "facebook_lg.png"));
			isFile = true;
		}
		if(!googleplus_lg_file.isEmpty()){
			wsr.setGoogleplus_lg_path("upload/front-end/googleplus_lg.png");
			googleplus_lg_file.transferTo(new File(order_path + "googleplus_lg.png"));
			isFile = true;
		}
		if(!twitter_lg_file.isEmpty()){
			wsr.setTwitter_lg_path("upload/front-end/twitter_lg.png");
			twitter_lg_file.transferTo(new File(order_path + "twitter_lg.png"));
			isFile = true;
		}
		if(!youtube_lg_file.isEmpty()){
			wsr.setYoutube_lg_path("upload/front-end/youtube_lg.png");
			youtube_lg_file.transferTo(new File(order_path + "youtube_lg.png"));
			isFile = true;
		}
		if(!two_dimensional_code_file.isEmpty()){
			wsr.setTwo_dimensional_code_path("upload/front-end/two_dimensional_code.png");
			two_dimensional_code_file.transferTo(new File(order_path + "two_dimensional_code.png"));
			isFile = true;
		}
		
		
		if(isFile){
			
			WebsiteStaticResources wsrQuery = this.systemService.queryWebsiteStaticResource(null);
			
			if(wsrQuery==null){
				this.systemService.createWebsiteStaticResources(wsr);
			} else {
				this.systemService.editWebsiteStaticResources(wsr);
			}
			
			attr.addFlashAttribute("success", "Successfully Upload the specific file(s)!");
			
		} else {
			attr.addFlashAttribute("error", "Please Specific at lease one file to upload!");
		}
		
		return "redirect:/broadband-user/system/website_static_resources";
	}
	
	@RequestMapping(value = "/broadband-user/system/terms_conditions", method = RequestMethod.GET)
	public String toTermsConditions(
			Model model){
		
		model.addAttribute("panelheading", "View Terms & Conditions");
		
		return "broadband-user/system/terms-conditions";
	}
	
	@RequestMapping(value = "/broadband-user/system/pdf_resources", method = RequestMethod.GET)
	public String toPDFResources(
			Model model){
		
		model.addAttribute("panelheading", "View PDF Resources");
		
		model.addAttribute("pdfr", this.systemService.queryPDFResource(null));
		
		return "broadband-user/system/pdf-resources";
	}
	
	@RequestMapping(value = "/broadband-user/system/pdf_resources/upload")
	public String doPDFResourcesUpload(Model model,
			@RequestParam("common_company_lg_path") MultipartFile common_company_lg_file,
			@RequestParam("invoice_company_lg_path") MultipartFile invoice_company_lg_file,
			@RequestParam("company_lg_customer_service_bar_path") MultipartFile company_lg_customer_service_bar_file,
			@RequestParam("two_dimensional_code_path") MultipartFile two_dimensional_code_file,
			RedirectAttributes attr,
			HttpServletRequest req) throws IllegalStateException, IOException{

		PDFResources pdfr = new PDFResources();
		
		String order_path = req.getSession().getServletContext().getRealPath("/") + File.separator + "upload" + File.separator + "pdf" + File.separator;
		
		File directory = new File(order_path);
		
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		Boolean isFile = false;
		
		String output_path = TMUtils.createPath("broadband" + File.separator + "pdf_resources" + File.separator);
		
		if(!common_company_lg_file.isEmpty()){
			pdfr.setCommon_company_lg_path("upload/pdf/common_company_logo.png");
			common_company_lg_file.transferTo(new File(order_path + "common_company_logo.png"));
			FileUtils.copy(order_path + "common_company_logo.png", output_path + "common_company_logo.png");
			isFile = true;
		}
		if(!invoice_company_lg_file.isEmpty()){
			pdfr.setInvoice_company_lg_path("upload/pdf/invoice_company_logo.png");
			invoice_company_lg_file.transferTo(new File(order_path + "invoice_company_logo.png"));
			FileUtils.copy(order_path + "invoice_company_logo.png", output_path + "invoice_company_logo.png");
			isFile = true;
		}
		if(!company_lg_customer_service_bar_file.isEmpty()){
			pdfr.setCompany_lg_customer_service_bar_path("upload/pdf/company_lg_customer_service_bar.png");
			company_lg_customer_service_bar_file.transferTo(new File(order_path + "company_lg_customer_service_bar.png"));
			FileUtils.copy(order_path + "company_lg_customer_service_bar.png", output_path + "company_lg_customer_service_bar.png");
			isFile = true;
		}
		if(!two_dimensional_code_file.isEmpty()){
			pdfr.setTwo_dimensional_code_path("upload/pdf/two_dimensional_code.png");
			two_dimensional_code_file.transferTo(new File(order_path + "two_dimensional_code.png"));
			FileUtils.copy(order_path + "two_dimensional_code.png", output_path + "two_dimensional_code.png");
			isFile = true;
		}
		
		if(isFile){
			
			PDFResources pdfrQuery = this.systemService.queryPDFResource(null);
			
			if(pdfrQuery==null){
				this.systemService.createPDFResource(pdfr);
			} else {
				this.systemService.editPDFResources(pdfr);
			}
			
			attr.addFlashAttribute("success", "Successfully Upload the specific file(s)!");
			
		} else {
			attr.addFlashAttribute("error", "Please Specific at lease one file to upload!");
		}
		
		return "redirect:/broadband-user/system/pdf_resources";
	}
	
}

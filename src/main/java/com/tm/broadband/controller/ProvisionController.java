package com.tm.broadband.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.CustomerChorusBroadbandASIDRecord;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.model.VOSVoIPCallRecord;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.ProvisionService;
import com.tm.broadband.service.SystemService;

@Controller
public class ProvisionController {
	
	private ProvisionService provisionService;
	private MailerService mailerService;
	private SystemService systemService;
	private CRMService crmService;
	private BillingService billingService;

	@Autowired
	public ProvisionController(ProvisionService provisionService
			, MailerService mailerService
			, SystemService systemService
			, CRMService crmService
			, BillingService billingService) {
		this.provisionService = provisionService;
		this.mailerService = mailerService;
		this.systemService = systemService;
		this.crmService = crmService;
		this.billingService = billingService;
	}
	
	@RequestMapping(value = "/broadband-user/provision/orders/view")
	public String provisionOrders() {
		return "broadband-user/provision/provision-view";
	}
	
	

	
	@RequestMapping(value = "/broadband-user/provision/view/{pageNo}")
	public String provisionLogView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<ProvisionLog> page = new Page<ProvisionLog>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by process_datetime desc");
		this.provisionService.queryProvisionLogsByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/provision/provision-log-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/contact-us/view/{pageNo}/{status}")
	public String contactUsView(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status
			,HttpServletRequest req) {

		Page<ContactUs> page = new Page<ContactUs>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by id desc");
		page.getParams().put("status", status);
		page = this.provisionService.queryContactUssByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");

		// BEGIN QUERY SUM BY STATUS
		Page<ContactUs> pageStatusSum = new Page<ContactUs>();
		pageStatusSum.getParams().put("status", "new");
		model.addAttribute("newSum", this.provisionService.queryContactUssSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "closed");
		model.addAttribute("closedSum", this.provisionService.queryContactUssSumByPage(pageStatusSum));
		// END QUERY SUM BY STATUS
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		return "broadband-user/provision/contact-us-view";
	}
	
	@RequestMapping(value = "/broadband-user/provision/contact-us/respond", method = RequestMethod.POST)
	public String respondContactUs(Model model
			, @RequestParam("id") Integer id
			, @RequestParam("email") String email
			, @RequestParam("respond_content") String respond_content
			, @RequestParam("pageNo") Integer pageNo
			,HttpServletRequest req) {
		
		ContactUs contactUs = new ContactUs();
		contactUs.getParams().put("id", id);
		contactUs.setStatus("closed");
		contactUs.setRespond_date(new Date());
		contactUs.setRespond_content(respond_content);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		contactUs.setUser_id(user.getId());
		
		this.provisionService.editContactUs(contactUs);
		
		contactUs = this.provisionService.queryContactUsById(id);
		
//		CompanyDetail companyDetail = this.crmService.queryCompanyDetail();
//		Notification notification = this.systemService.queryNotificationBySort("contact-us-response", "email");
//		TMUtils.mailAtValueRetriever(notification, contactUs, companyDetail); // call mail at value retriever
		ApplicationEmail applicationEmail = new ApplicationEmail();
		applicationEmail.setAddressee(email);
		applicationEmail.setSubject("CyberPark Limited's feedback");
		applicationEmail.setContent(respond_content);
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		return "redirect:/broadband-user/provision/contact-us/view/"+pageNo+"/new";
	}
	
	@RequestMapping(value = "/broadband-user/provision/sale/view/{pageNo}")
	public String toSalesView(Model model
			, @PathVariable("pageNo") int pageNo
			,HttpServletRequest req) {

		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by user_name");
		page.getParams().put("user_role", "sales");
		
		this.systemService.queryUsersByPage(page);
		model.addAttribute("page", page);
		return "broadband-user/sale/sales-view";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/broadband-user/provision/number-couldnot-find/{billingType}/{statusType}")
	public String toMonthlyBillingExceptions(Model model,
			@PathVariable(value = "billingType") String billingType,
			@PathVariable(value = "statusType") String statusType) {
		
		model.addAttribute("panelheading", "Monthly Billing Statistics");
		
		Map<String, Object> resultMap = this.billingService.queryMonthlyCallingStatistics(billingType, statusType);
		
		Calendar calLastMonth = Calendar.getInstance();
		calLastMonth.add(Calendar.MONTH, -1);
		
		int chorusUnmatchedSize = 0;
		int ncaUnmatchedSize = 0;
		int vosUnmatchedSize = 0;
		int asidUnmatchedSize = 0;
		
		int chorusDisconnectedSize = 0;
		int ncaDisconnectedSize = 0;
		int vosDisconnectedSize = 0;
		int asidDisconnectedSize = 0;

		if("unmatched".equals(statusType)){

			// Unmatched
			List<CustomerCallRecord> chorusUnmatched = (List<CustomerCallRecord>) resultMap.get("chorusUnmatched");
			model.addAttribute("chorusUnmatched", chorusUnmatched);
			
			List<CustomerCallingRecordCallplus> ncaUnmatched = (List<CustomerCallingRecordCallplus>) resultMap.get("ncaUnmatched");
			model.addAttribute("ncaUnmatched", ncaUnmatched);
			
			List<VOSVoIPCallRecord> vosUnmatched = (List<VOSVoIPCallRecord>) resultMap.get("vosUnmatched");
			model.addAttribute("vosUnmatched", vosUnmatched);
			
			List<VOSVoIPCallRecord> asidUnmatched = (List<VOSVoIPCallRecord>) resultMap.get("asidUnmatched");
			model.addAttribute("asidUnmatched", asidUnmatched);
			
		}
		
		CustomerCallRecord chorusUnmatchedQuery = new CustomerCallRecord();
		chorusUnmatchedQuery.getParams().put("where", "query_unmatched_records");
		chorusUnmatchedQuery.getParams().put("groupby", "group by clear_service_id");
		chorusUnmatchedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerCallRecord> ccrsUnmatched = this.billingService.queryCustomerCallRecords(chorusUnmatchedQuery);
		chorusUnmatchedSize = ccrsUnmatched!=null ? ccrsUnmatched.size() : 0;
		
		CustomerCallingRecordCallplus ncaUnmatchedQuery = new CustomerCallingRecordCallplus();
		ncaUnmatchedQuery.getParams().put("where", "query_unmatched_records");
		ncaUnmatchedQuery.getParams().put("groupby", "group by original_number");
		ncaUnmatchedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerCallingRecordCallplus> ccrcsUnmatched = this.billingService.queryCustomerCallingRecordCallpluss(ncaUnmatchedQuery);
		ncaUnmatchedSize = ccrcsUnmatched!=null ? ccrcsUnmatched.size() : 0;
		
		VOSVoIPCallRecord vosUnmatchedQuery = new VOSVoIPCallRecord();
		vosUnmatchedQuery.getParams().put("where", "query_unmatched_records");
		vosUnmatchedQuery.getParams().put("groupby", "group by ori_number");
		vosUnmatchedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<VOSVoIPCallRecord> vossUnmatched = this.billingService.queryVOSVoIPCallRecord(vosUnmatchedQuery);
		vosUnmatchedSize = vossUnmatched!=null ? vossUnmatched.size() : 0;
		
		CustomerChorusBroadbandASIDRecord asidQuery = new CustomerChorusBroadbandASIDRecord();
		asidQuery.getParams().put("where", "query_unmatched_records");
		asidQuery.getParams().put("groupby", "group by clear_service_id");
		asidQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerChorusBroadbandASIDRecord> asidsUnmatched = this.billingService.queryCustomerChorusBroadbandASIDRecords(asidQuery);
		asidUnmatchedSize = asidsUnmatched!=null ? asidsUnmatched.size() : 0;
		
		

		if("disconnected".equals(statusType)){
	
			// Disconnected
			List<CustomerCallRecord> chorusDisconnected = (List<CustomerCallRecord>) resultMap.get("chorusDisconnected");
			model.addAttribute("chorusDisconnected", chorusDisconnected);
			
			List<CustomerCallingRecordCallplus> ncaDisconnected = (List<CustomerCallingRecordCallplus>) resultMap.get("ncaDisconnected");
			model.addAttribute("ncaDisconnected", ncaDisconnected);
			
			List<VOSVoIPCallRecord> vosDisconnected = (List<VOSVoIPCallRecord>) resultMap.get("vosDisconnected");
			model.addAttribute("vosDisconnected", vosDisconnected);
			
			List<VOSVoIPCallRecord> asidDisconnected = (List<VOSVoIPCallRecord>) resultMap.get("asidDisconnected");
			model.addAttribute("asidDisconnected", asidDisconnected);
			
		}
		
		CustomerCallRecord ccrDisconnectedQuery = new CustomerCallRecord();
		ccrDisconnectedQuery.getParams().put("where", "query_disconnected_records");
		ccrDisconnectedQuery.getParams().put("groupby", "group by clear_service_id");
		ccrDisconnectedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerCallRecord> ccrsDisconnected = this.billingService.queryCustomerCallRecords(ccrDisconnectedQuery);
		chorusDisconnectedSize = ccrsDisconnected!=null ? ccrsDisconnected.size() : 0;
		
		CustomerCallingRecordCallplus ncaDisconnectedQuery = new CustomerCallingRecordCallplus();
		ncaDisconnectedQuery.getParams().put("where", "query_disconnected_records");
		ncaDisconnectedQuery.getParams().put("groupby", "group by original_number");
		ncaDisconnectedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerCallingRecordCallplus> ccrcsDisconnected = this.billingService.queryCustomerCallingRecordCallpluss(ncaDisconnectedQuery);
		ncaDisconnectedSize = ccrcsDisconnected!=null ? ccrcsDisconnected.size() : 0;
		
		VOSVoIPCallRecord vosDisconnectedQuery = new VOSVoIPCallRecord();
		vosDisconnectedQuery.getParams().put("where", "query_disconnected_records");
		vosDisconnectedQuery.getParams().put("groupby", "group by ori_number");
		vosDisconnectedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<VOSVoIPCallRecord> vossDisconnected = this.billingService.queryVOSVoIPCallRecord(vosDisconnectedQuery);
		vosDisconnectedSize = vossDisconnected!=null ? vossDisconnected.size() : 0;
		
		CustomerChorusBroadbandASIDRecord asidDisconnectedQuery = new CustomerChorusBroadbandASIDRecord();
		asidDisconnectedQuery.getParams().put("where", "query_disconnected_records");
		asidDisconnectedQuery.getParams().put("groupby", "group by clear_service_id");
		asidDisconnectedQuery.getParams().put("last_month_date", calLastMonth.getTime());
		List<CustomerChorusBroadbandASIDRecord> asidsDisconnected = this.billingService.queryCustomerChorusBroadbandASIDRecords(asidDisconnectedQuery);
		asidDisconnectedSize = asidsDisconnected!=null ? asidsDisconnected.size() : 0;
		
		
		if("unmatched".equals(statusType)){

			model.addAttribute("chorusUnmatchedSize", chorusUnmatchedSize);

			model.addAttribute("ncaUnmatchedSize", ncaUnmatchedSize);

			model.addAttribute("vosUnmatchedSize", vosUnmatchedSize);

			model.addAttribute("asidUnmatchedSize", asidUnmatchedSize);
			
		}
		
		if("disconnected".equals(statusType)){

			model.addAttribute("chorusDisconnectedSize", chorusDisconnectedSize);

			model.addAttribute("ncaDisconnectedSize", ncaDisconnectedSize);

			model.addAttribute("vosDisconnectedSize", vosDisconnectedSize);

			model.addAttribute("asidDisconnectedSize", asidDisconnectedSize);
			
		}
		
		model.addAttribute("allUnmatchedSize", chorusUnmatchedSize+ncaUnmatchedSize+vosUnmatchedSize+asidUnmatchedSize);
		model.addAttribute("allDisconnectedSize", chorusDisconnectedSize+ncaDisconnectedSize+vosDisconnectedSize+asidDisconnectedSize);
		model.addAttribute("billingType", billingType);
		model.addAttribute("statusType", statusType);
		model.addAttribute(billingType+"Active", "active");
		model.addAttribute(statusType+"Active", "active");
		
		return "broadband-user/provision/number-couldnot-find";
	}

	@RequestMapping(value = "/broadband-user/provision/pstn-position-view/{pageNo}/{pstn_position}")
	public String toPSTNPosition(Model model,
			@PathVariable(value = "pageNo") Integer pageNo,
			@PathVariable(value = "pstn_position") String pstn_position) {

		Page<CustomerOrderDetail> page = new Page<CustomerOrderDetail>();
		page.setPageNo(pageNo);
		page.getParams().put("where", "query_by_pstn_position");
		page.getParams().put("orderby", "order by id desc");
		switch (pstn_position) {
		case "chorus":
			page.getParams().put("is_nca", false);
			break;
		case "nca":
			page.getParams().put("is_nca", true);
			break;
		}
		this.crmService.queryCustomerOrderDetailsByPage(page);
		model.addAttribute("page", page);
		
		model.addAttribute(pstn_position+"Active", "active");
		
		Page<CustomerOrderDetail> codChorusSum = new Page<CustomerOrderDetail>();
		codChorusSum.getParams().put("where", "query_by_pstn_position");
		codChorusSum.getParams().put("is_nca", false);
		model.addAttribute("chorusSum", this.crmService.queryCustomerOrderDetailsBySum(codChorusSum));
		
		Page<CustomerOrderDetail> codNCASum = new Page<CustomerOrderDetail>();
		codNCASum.getParams().put("where", "query_by_pstn_position");
		codNCASum.getParams().put("is_nca", true);
		model.addAttribute("ncaSum", this.crmService.queryCustomerOrderDetailsBySum(codNCASum));
		
		if("nca".equals(pstn_position)){
			model.addAttribute("users", this.systemService.queryUser(new User()));
		}
		
		return "broadband-user/provision/pstn-position-view";
	}

	@RequestMapping(value = "/broadband-user/provision/pstn-position-view/customer/view/{order_id}")
	public String toPSTNPositionCustomerView(Model model,
			@PathVariable(value = "order_id") Integer order_id) {
		
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", order_id);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		
		return "redirect:/broadband-user/crm/customer/edit/"+coQuery.getCustomer_id();
	}

	@RequestMapping(value = "/broadband-user/provision/pstn-position-view/switch-between-chorus-nca", method = RequestMethod.POST)
	public String doPSTNPositionSwitchIntoNCA(Model model,
			@RequestParam("id") Integer id,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pstn_position") String pstn_position,
			RedirectAttributes attr,
			HttpServletRequest req) {
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		CustomerOrderDetail codUpdate = new CustomerOrderDetail();
		codUpdate.getParams().put("id", id);
		codUpdate.setIs_nca("chorus".equals(pstn_position) ? true : false);
		codUpdate.setTo_nca_by_who(userSession.getId());
		this.crmService.editCustomerOrderDetail(codUpdate);
		
		attr.addFlashAttribute("success", "Successfully switch PSTN from "+("chorus".equals(pstn_position) ? "Chorus into NCA" : "NCA to Chorus"));
		
		return "redirect:/broadband-user/provision/pstn-position-view/"+pageNo+"/"+pstn_position;
	}

}

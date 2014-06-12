package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.SystemService;

@Controller
public class BillingController {

	private BillingService billingService;
	private SystemService systemService;
	
	@Autowired
	public BillingController(BillingService billingService,
			SystemService systemService) {
		this.billingService = billingService;
		this.systemService = systemService;
	}
	
	// BEGIN EarlyTerminationCharge
	@RequestMapping("/broadband-user/billing/early-termination-charge/view/{pageNo}")
	public String toEarlyTerminationCharge(Model model
			, @PathVariable("pageNo") int pageNo) {

		Page<EarlyTerminationCharge> page = new Page<EarlyTerminationCharge>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY due_date DESC");
		page = this.billingService.queryEarlyTerminationChargesByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		return "broadband-user/billing/early-termination-charge-view";
	}
}

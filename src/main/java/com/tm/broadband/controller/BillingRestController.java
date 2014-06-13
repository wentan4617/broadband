package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.service.BillingService;

@RestController
public class BillingRestController {

	private BillingService billingService;
	
	@Autowired
	public BillingRestController(BillingService billingService) {
		this.billingService = billingService;
	}
	
	// BEGIN EarlyTerminationCharge
	@RequestMapping(value = "/broadband-user/billing/early-termination-charge/insert", method = RequestMethod.POST)
	public JSONBean<String> doEarlyTerminationChargeCreate(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("overdue_extra_charge") Double overdue_extra_charge) {
		
		JSONBean<String> json = new JSONBean<String>();
		
		EarlyTerminationChargeParameter etcp = new EarlyTerminationChargeParameter();
		etcp.setOverdue_extra_charge(overdue_extra_charge);
		
		this.billingService.editEarlyTerminationChargeParameter(etcp);
		
		json.getSuccessMap().put("alert-success", "Overdue Extra Charge had successfully been updated!");
		
		return json;
	}
	// END EarlyTerminationCharge
}

package com.tm.broadband.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tm.broadband.service.BillingService;

@Controller
public class BillingController {

	private BillingService billingService;
	
	@Autowired
	public BillingController(BillingService billingService) {
		this.billingService = billingService;
	}

	@RequestMapping("/broadband-user/billing/billing-file-upload")
	public String toBillingFileUpload(Model model) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		model.addAttribute("cur_year", year);
		model.addAttribute("cur_month", month);
		
		return "broadband-user/billing/billing-file-upload";
	}
}

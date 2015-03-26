package com.tm.broadband.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerDDPay;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;

@RestController
public class BillingRestController {

	private BillingService billingService;
	private CRMService crmService;
	
	@Autowired
	public BillingRestController(BillingService billingService,
			CRMService crmService) {
		this.billingService = billingService;
		this.crmService = crmService;
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

	
	/**
	 * BEGIN DD/CC Invoice View
	 */
	@RequestMapping("/broadband-user/billing/ddccinvoice/view/{pageNo}/{customerType}/{yearMonth}")
	public Page<CustomerInvoice> toDDCCPay(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable(value = "customerType") String customerType
			, @PathVariable(value = "yearMonth") String yearMonth) {
		
		if(yearMonth.equals("0")){
			yearMonth = TMUtils.dateFormatYYYYMM(new Date());
		}

		Page<CustomerInvoice> page = new Page<CustomerInvoice>();
		page.setPageSize(30);
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "ORDER BY create_date DESC");
		page.getParams().put("where", "query_non_paid_status");
		if(!customerType.equals("all")){
			page.getParams().put("customer_type", customerType);
		}
		page.getParams().put("create_date_month", yearMonth);
		page.getParams().put("non_paid_status", "true");
		page = this.crmService.queryCustomerInvoicesByPage(page);
		
		for (CustomerInvoice ci : page.getResults()) {

			CustomerCredit ccQuery = new CustomerCredit();
			ccQuery.getParams().put("where", "query_by_invoice_id");
			ccQuery.getParams().put("invoice_id", ci.getId());
			List<CustomerCredit> ccsQuery = this.crmService.queryCustomerCredits(ccQuery);
			ci.setCcs(ccsQuery);
			
			CustomerDDPay ddQuery = new CustomerDDPay();
			ddQuery.getParams().put("where", "query_by_invoice_id");
			ddQuery.getParams().put("invoice_id", ci.getId());
			List<CustomerDDPay> ddsQuery = this.crmService.queryCustomerDDPays(ddQuery);
			ci.setDds(ddsQuery);
			
		}
		
		return page;
	}
}

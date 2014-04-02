package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes("orderPlan")
public class SaleController {
	
	private PlanService planService;

	@Autowired
	public SaleController(PlanService planService) {
		this.planService = planService;
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/plans")
	public String plans(Model model) {
		
		List<Plan> plans = null;
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", "plan-term");
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("plan_sort", "non-naked");
		plan.getParams().put("orderby", "order by data_flow");
		
		plans = this.planService.queryPlansBySome(plan);
		
		model.addAttribute("plans", plans);
		
		return "broadband-user/sale/online-ordering";
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/order/{id}")
	public String orderPlanTerm(Model model, @PathVariable("id") int id) {
		
		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		
		/*Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwaresBySome(hardware);
		model.addAttribute("hardwares", hardwares);*/
		
		return "broadband-user/sale/online-ordering-customer-info";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm")
	public String orderConfirm(Model model,
			@ModelAttribute("customer") @Validated(CustomerValidatedMark.class) Customer customer, BindingResult result,
			@ModelAttribute("orderPlan") Plan plan, 
			//@ModelAttribute("hardwares") List<Hardware> hardwares, 
			RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "broadband-user/sale/online-ordering-customer-info";
		}
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().setOrder_create_date(new Date());

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		
		cod_plan.setDetail_name(plan.getPlan_name());
		cod_plan.setDetail_desc(plan.getPlan_desc());
		cod_plan.setDetail_price(plan.getPlan_price() == null ? 0d : plan.getPlan_price());
		cod_plan.setDetail_data_flow(plan.getData_flow());
		cod_plan.setDetail_plan_status(plan.getPlan_status());
		cod_plan.setDetail_plan_type(plan.getPlan_type());
		cod_plan.setDetail_plan_sort(plan.getPlan_sort());
		cod_plan.setDetail_plan_group(plan.getPlan_group());
		cod_plan.setDetail_plan_memo(plan.getMemo());
		cod_plan.setDetail_unit(plan.getPlan_prepay_months() == null ? 1 : plan.getPlan_prepay_months());
		cod_plan.setDetail_type(plan.getPlan_group());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(cod_plan);
		
		if ("plan-term".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Broadband New Connection");
				cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);

			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
					
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
			}
		}
		
		CustomerOrderDetail cod_hardware = new CustomerOrderDetail();
		cod_hardware.setDetail_name("TP Link Router/Modem");
		cod_hardware.setDetail_price(0d);
		cod_hardware.setDetail_is_next_pay(0);
		cod_hardware.setDetail_price(0d);
		cod_hardware.setDetail_type("hardware-router");
		cod_hardware.setDetail_unit(1);
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(cod_hardware);
		
		CustomerOrderDetail cod_pstn = new CustomerOrderDetail();
		cod_pstn.setDetail_name("Landline");
		cod_pstn.setDetail_price(0d);
		cod_pstn.setDetail_is_next_pay(1);
		cod_pstn.setDetail_price(0d);
		cod_pstn.setDetail_type("pstn");
		cod_pstn.setDetail_unit(1);
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(cod_pstn);
		
		return "broadband-user/sale/online-ordering-confirm";
	}

}

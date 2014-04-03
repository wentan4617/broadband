package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@Controller
@SessionAttributes("orderPlan")
public class SaleController {
	
	private PlanService planService;
	private CRMService crmService;

	@Autowired
	public SaleController(PlanService planService, CRMService crmService) {
		this.planService = planService;
		this.crmService = crmService;
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/plans")
	public String plans(Model model) {
		
		List<Plan> plans = null;
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", "plan-term");
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("plan_sort", "CLOTHING");
		plan.getParams().put("orderby", "order by data_flow");
		
		plans = this.planService.queryPlansBySome(plan);
		
		model.addAttribute("plans", plans);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwaresBySome(hardware);
		model.addAttribute("hardwares", hardwares);
		
		return "broadband-user/sale/online-ordering";
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/order/{id}")
	public String orderPlanTerm(Model model, @PathVariable("id") int id) {
		
		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		
		return "broadband-user/sale/online-ordering-customer-info";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm")
	public String orderConfirm(Model model,
			@ModelAttribute("orderPlan") Plan plan, 
			HttpServletRequest req,
			RedirectAttributes attr) {
		
		Customer customer = (Customer)req.getSession().getAttribute("orderCustomer");
		List<CustomerOrderDetail> cods = (List<CustomerOrderDetail>)req.getSession().getAttribute("orderCustomerOrderDetails");
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().getCustomerOrderDetails().addAll(cods);
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
		cod_plan.setDetail_term_period(plan.getTerm_period());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(0, cod_plan);
		
		if ("plan-term".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Installation");
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
		
		if (cods != null) {
			for (CustomerOrderDetail cod : cods) {
				if ("hardware-router".equals(cod.getDetail_type())) {
					cod.setDetail_is_next_pay(0);
					cod.setIs_post(0);
					customer.getCustomerOrder().setHardware_post(customer.getCustomerOrder().getHardware_post() == null ? 1 : customer.getCustomerOrder().getHardware_post() + 1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				} else if ("pstn".equals(cod.getDetail_type()) 
						|| "voip".equals(cod.getDetail_type())){
					cod.setDetail_unit(1);
					cod.setDetail_is_next_pay(1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				}
			}
		}
		
		
		return "broadband-user/sale/online-ordering-confirm";
	}
	
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm/save")
	public String orderSave(Model model, @ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr, SessionStatus status, HttpServletRequest req) {
		
		Customer customer = (Customer)req.getSession().getAttribute("orderCustomer");
		
		customer.setUser_name(customer.getLogin_name());
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));
		customer.getCustomerOrder().setSale_id(customer.getId());
		
		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder());
		
		
		status.setComplete();
		req.getSession().removeAttribute("orderCustomer");
		
		return "redirect:/broadband-user/sale/online/ordering/order/credit/" + customer.getId();
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/{id}")
	public String toCredit(Model model, @PathVariable("id") Integer id) {
		
		model.addAttribute("customer_id", id);
		return "broadband-user/sale/online-ordering-credit";
	}

}

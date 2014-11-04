package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.InviteRates;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;
import com.tm.broadband.validator.mark.PromotionCodeValidatedMark;
import com.tm.broadband.validator.mark.TransitionCustomerOrderValidatedMark;

@RestController
public class SaleRestController {
	
	private CRMService crmService;
	private PlanService planService;

	@Autowired
	public SaleRestController(CRMService crmService, PlanService planService) {
		this.crmService = crmService;
		this.planService = planService;
	}
	
	private JSONBean<Customer> returnJsonCustomer(Customer customer, BindingResult result) {
		
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			if (!"transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				json.getErrorMap().remove("customerOrder.transition_porting_number");
				json.getErrorMap().remove("customerOrder.transition_provider_name");
				json.getErrorMap().remove("customerOrder.transition_account_number");
				json.getErrorMap().remove("customerOrder.transition_account_holder_name");
			}
			
			if (json.isHasErrors()) return json;
		}

		Customer cValid = new Customer();
		cValid.getParams().put("where", "query_exist_customer_by_mobile");
		cValid.getParams().put("cellphone", customer.getCellphone());
		int count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("cellphone", "is already in use");
			return json;
		}
		
		cValid.getParams().put("where", "query_exist_customer_by_email");
		cValid.getParams().put("email", customer.getEmail());
		count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("email", "is already in use");
			return json;
		}
		json.setUrl("/broadband-user/sale/online/ordering/order/confirm");
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/details", method = RequestMethod.POST)
	public JSONBean<CustomerOrderDetail> doOrderDetails(Model model,
			@RequestBody List<CustomerOrderDetail> cods, HttpServletRequest req) {

		JSONBean<CustomerOrderDetail> json = new JSONBean<CustomerOrderDetail>();
		json.setModels(cods);
		
		for (CustomerOrderDetail customerOrderDetail : cods) {
			System.out.println(customerOrderDetail.getPstn_number());
		}
		
		System.out.println(cods.size());
		
		model.addAttribute("orderCustomerOrderDetails", cods);
		
		json.setUrl("/broadband-user/sale/online/ordering/order/");

		return json;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/broadband-user/sale/plans/loading")
	public Map<String, Map<String, List<Plan>>> loadingPlans(HttpSession session) {
		
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		
		Plan plan = new Plan();
		plan.getParams().put("plan_type", customerRegSale.getSelect_plan_type());
		plan.getParams().put("plan_class", customerRegSale.getSelect_customer_type());
		plan.getParams().put("plan_status", "selling");
		System.out.println("customerRegSale.getSelect_plan_group(): " + customerRegSale.getSelect_plan_group());
		if ("plan-topup".equals(customerRegSale.getSelect_plan_group())) {
			System.out.println("topup");
			plan.getParams().put("plan_group", "plan-topup");
		} else {
			System.out.println("!!!topup");
			plan.getParams().put("plan_group_false", "plan-topup");
		}
		plan.getParams().put("orderby", "order by plan_price");
		
		if ("VDSL".equals(customerRegSale.getSelect_plan_type()) 
				&& customerRegSale.getCustomerOrder().getSale_id() != null
				&& "personal".equals(customerRegSale.getSelect_customer_type())) {
			plan.getParams().put("id", 42);
		}
		
		List<Plan> plans = this.planService.queryPlans(plan);
		
		Map<String, Map<String, List<Plan>>> planTypeMap = new HashMap<String, Map<String, List<Plan>>>();
		
		this.wiredPlanMapBySort(planTypeMap, plans);
		
		return planTypeMap;
	}
	
	private void wiredPlanMapBySort(Map<String, Map<String, List<Plan>>> planTypeMap, List<Plan> plans) {
		if (plans != null) {
			for (Plan p: plans) {
				Map<String, List<Plan>> planMap = planTypeMap.get(p.getPlan_type());
				if (planMap == null) {
					planMap = new HashMap<String, List<Plan>>();	
					if ("CLOTHED".equals(p.getPlan_sort())) {
						List<Plan> plansClothed = new ArrayList<Plan>();
						plansClothed.add(p);
						planMap.put("plansClothed", plansClothed);
					} else if("NAKED".equals(p.getPlan_sort())) {
						List<Plan> plansNaked = new ArrayList<Plan>();
						plansNaked.add(p);
						planMap.put("plansNaked", plansNaked);
					}
					planTypeMap.put(p.getPlan_type(), planMap);
				} else {
					if ("CLOTHED".equals(p.getPlan_sort())) {
						List<Plan> plansClothed = planMap.get("plansClothed");
						if (plansClothed == null) {
							plansClothed = new ArrayList<Plan>();
							plansClothed.add(p);
							planMap.put("plansClothed", plansClothed);
						} else {
							plansClothed.add(p);
						}
					} else if("NAKED".equals(p.getPlan_sort())) {
						List<Plan> plansNaked = planMap.get("plansNaked");
						if (plansNaked == null) {
							plansNaked = new ArrayList<Plan>();
							plansNaked.add(p);
							planMap.put("plansNaked", plansNaked);
						} else {
							plansNaked.add(p);
						}
					}
				}
			}
		}
	}
	
	
	@RequestMapping("/broadband-user/sale/plans/hardware/loading")
	public List<Hardware> loadingHardwares(HttpSession session) {
		
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		hardware.getParams().put("hardware_type", "router");
		if ("ADSL".equals(customerRegSale.getSelect_plan_type())) {
			hardware.getParams().put("router_adsl", true);
		} else if ("VDSL".equals(customerRegSale.getSelect_plan_type())) {
			hardware.getParams().put("router_vdsl", true);
		} else if ("UFB".equals(customerRegSale.getSelect_plan_type())) {
			hardware.getParams().put("router_ufb", true);
		}
		
		List<Hardware> hardwares = this.planService.queryHardwares(hardware);
		
		return hardwares;
	}
	
	@RequestMapping(value = "/broadband-user/sale/plans/order/apply/promotion-code", method = RequestMethod.POST)
	public JSONBean<InviteRates> applyPromotionCode(HttpSession session,	
			@Validated(value = { PromotionCodeValidatedMark.class }) InviteRates irates, BindingResult result) {
		
		JSONBean<InviteRates> json = new JSONBean<InviteRates>();
		json.setModel(irates);
		
		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		InviteRates ir = this.crmService.applyPromotionCode(irates.getPromotion_code());
		System.out.println("ir: " + ir);
		
		if (ir == null) {
			json.getErrorMap().put("promotion_code", "Sorry dear, this is invalid promotion code.");
			return json;
		}
		
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		
		customerRegSale.setIr(ir);
		json.setModel(ir);
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/sale/plans/order/cancel/promotion-code", method = RequestMethod.POST)
	public void cancelPromotionCode(HttpSession session) {
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		customerRegSale.setIr(null);
	}
	
	@RequestMapping(value = "/broadband-user/sale/plans/order/confirm/personal", method = RequestMethod.POST)
	public JSONBean<Customer> doPlanOrderConfirmPersonal(
			@Validated(value = { CustomerValidatedMark.class, TransitionCustomerOrderValidatedMark.class }) 
			@RequestBody Customer customer, BindingResult result, HttpSession session) {
		
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		User userSession = (User) session.getAttribute("userSession");
		
		customerRegSale.setCurrentOperateUserid(userSession.getId());
		customerRegSale.setCellphone(customer.getCellphone());
		customerRegSale.setEmail(customer.getEmail());
		customerRegSale.setTitle(customer.getTitle());
		customerRegSale.setFirst_name(customer.getFirst_name());
		customerRegSale.setLast_name(customer.getLast_name());
		customerRegSale.setIdentity_type(customer.getIdentity_type());
		customerRegSale.setIdentity_number(customer.getIdentity_number());
		
		customerRegSale.setCustomerOrder(customer.getCustomerOrder());
		
		JSONBean<Customer> json = this.returnJsonCustomer(customerRegSale, result);
		
		this.crmService.doPlansOrderConfirm(customerRegSale);
		
		json.setUrl("/broadband-user/sale/plans/order/summary");
		
		return json;
	}	
	
	@RequestMapping(value = "/broadband-user/sale/plans/order/confirm/business", method = RequestMethod.POST)
	public JSONBean<Customer> doPlanOrderConfirmBusiness(
			@Validated(value = { CustomerOrganizationValidatedMark.class, TransitionCustomerOrderValidatedMark.class }) 
			@RequestBody Customer customer, BindingResult result, HttpSession session) {
		
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		User userSession = (User) session.getAttribute("userSession");
		
		customerRegSale.setCurrentOperateUserid(userSession.getId());
		customerRegSale.setCellphone(customer.getCellphone());
		customerRegSale.setEmail(customer.getEmail());
		customerRegSale.setTitle(customer.getTitle());
		customerRegSale.setFirst_name(customer.getFirst_name());
		customerRegSale.setLast_name(customer.getLast_name());
		customerRegSale.setIdentity_type(customer.getIdentity_type());
		customerRegSale.setIdentity_number(customer.getIdentity_number());
		
		customerRegSale.setCustomerOrder(customer.getCustomerOrder());
		
		JSONBean<Customer> json = this.returnJsonCustomer(customerRegSale, result);
		
		this.crmService.doPlansOrderConfirm(customerRegSale);
		
		json.setUrl("/broadband-user/sale/plans/order/summary");
		
		return json;
	}	
	
}

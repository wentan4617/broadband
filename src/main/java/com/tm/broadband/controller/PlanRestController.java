package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.HardwareValidatedMark;
import com.tm.broadband.validator.mark.PlanValidatedMark;

@RestController
@SessionAttributes("planFilter")
public class PlanRestController {

	private PlanService planService;

	@Autowired
	public PlanRestController(PlanService planService) {
		this.planService = planService;
	}

	/*
	 * Plan Controller begin
	 */
	
	@RequestMapping(value = "/broadband-user/plan/view/{pageNo}")
	public Page<Plan> doPlanView(@PathVariable(value = "pageNo") int pageNo, HttpServletRequest req) {

		Page<Plan> page = new Page<Plan>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by plan_status desc, plan_type");
		
		Plan planFilter = (Plan) req.getSession().getAttribute("planFilter");
		if (planFilter != null) {
			if (!"".equals(planFilter.getPlan_group()))
				page.getParams().put("plan_group", planFilter.getPlan_group());
			if (!"".equals(planFilter.getPlan_class()))
				page.getParams().put("plan_class", planFilter.getPlan_class());
			if (!"".equals(planFilter.getPlan_type()))
				page.getParams().put("plan_type", planFilter.getPlan_type());
		}
		
		this.planService.queryPlansByPage(page);
		System.out.println(page.getTotalPage());

		return page;
	}
	
	@RequestMapping(value = "/broadband-user/plan/view/filter")
	public void doPlanViewFilter(Model model, Plan plan) {
		model.addAttribute("planFilter", plan);
		System.out.println("do plan filter");
	}
	
	@RequestMapping(value = "/broadband-user/plan/create", method = RequestMethod.POST)
	public JSONBean<Plan> doPlanCreate(Model model,
			@Validated(PlanValidatedMark.class) Plan plan,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		JSONBean<Plan> json = new JSONBean<Plan>();
		json.setModel(plan);

		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		Plan pValid = new Plan();
		pValid.getParams().put("where", "query_exist_plan_by_name");
		pValid.getParams().put("plan_name", plan.getPlan_name());
		
		int count = this.planService.queryExistPlan(pValid);

		if (count > 0) {
			json.getErrorMap().put("plan_name", "duplicate");
			return json;
		}
		
		this.planService.savePlan(plan);

		json.setUrl("/broadband-user/plan/view/redirect");

		return json;
	}
	
	@RequestMapping(value = "/broadband-user/plan/edit", method = RequestMethod.POST)
	public JSONBean<Plan> doPlanEdit(
			Model model,
			@Validated(PlanValidatedMark.class) Plan plan,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		JSONBean<Plan> json = new JSONBean<Plan>();
		json.setModel(plan);

		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}

		Plan pValid = new Plan();
		pValid.getParams().put("where", "query_exist_not_self_plan_by_name");
		pValid.getParams().put("plan_name", plan.getPlan_name());
		pValid.getParams().put("id", plan.getId());
		
		int count = this.planService.queryExistPlan(pValid);

		if (count > 0) {
			json.getErrorMap().put("plan_name", "duplicate");
			return json;
		}
		
		plan.getParams().put("where", "update_plan");
		plan.getParams().put("id", plan.getId());
		this.planService.editPlan(plan);
		
		json.setUrl("/broadband-user/plan/view/redirect");

		return json;
	}

	/*
	 * Plan Controller end
	 */
	

	/*
	 * Hardware Controller begin
	 */
	
	@RequestMapping(value = "/broadband-user/plan/hardware/create", method = RequestMethod.POST)
	public JSONBean<Hardware> doHardwareCreate(
			Model model,
			@Validated(HardwareValidatedMark.class) Hardware hardware,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		JSONBean<Hardware> json = new JSONBean<Hardware>();
		json.setModel(hardware);

		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		this.planService.saveHardware(hardware);
		
		json.setUrl("/broadband-user/plan/hardware/view/redirect");

		return json;
	}

	@RequestMapping(value = "/broadband-user/plan/hardware/edit", method = RequestMethod.POST)
	public JSONBean<Hardware> doHardwareEdit(
			Model model,
			@Validated(HardwareValidatedMark.class) Hardware hardware,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		JSONBean<Hardware> json = new JSONBean<Hardware>();
		json.setModel(hardware);

		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		hardware.getParams().put("id", hardware.getId());
		this.planService.editHardware(hardware);
		
		json.setUrl("/broadband-user/plan/hardware/view/redirect");

		return json;
	}

	/*
	 * Hardware Controller end
	 */

}

package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.HardwareValidatedMark;
import com.tm.broadband.validator.mark.PlanValidatedMark;

@RestController
public class PlanRestController {

	private PlanService planService;

	@Autowired
	public PlanRestController(PlanService planService) {
		this.planService = planService;
	}

	/*
	 * Plan Controller begin
	 */
	
	@RequestMapping(value = "/broadband-user/plan/create", method = RequestMethod.POST)
	public JSONBean<Plan> doPlanCreate(
			Model model
			,@Validated(PlanValidatedMark.class) Plan plan
			,BindingResult result, HttpServletRequest req
			,RedirectAttributes attr) {
		
		JSONBean<Plan> json = new JSONBean<Plan>();
		json.setModel(plan);

		if (result.hasErrors()) {
			json.setJSONErrorMap(result);
			return json;
		}
		
		int count = this.planService.queryExistPlanByName(plan.getPlan_name());

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

		int count = this.planService.queryExistNotSelfPlanByName(plan.getPlan_name(), plan.getId());

		if (count > 0) {
			json.getErrorMap().put("plan_name", "duplicate");
			return json;
		}
		
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

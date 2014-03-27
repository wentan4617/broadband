package com.tm.broadband.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.Topup;
import com.tm.broadband.model.User;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.validator.mark.HardwareValidatedMark;
import com.tm.broadband.validator.mark.PlanValidatedMark;
import com.tm.broadband.validator.mark.TopupValidatedMark;

/**
 * plan controller
 * @author Cook1fan
 *
 */
@Controller
public class PlanController {
	
	private PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	/*
	 * PLAN AREA
	 */
	
	@RequestMapping(value = "/broadband-user/plan/create")
	public String toPlanCreate(Model model) {

		model.addAttribute("plan", new Plan());
		model.addAttribute("panelheading", "Plan Create");
		model.addAttribute("action", "/broadband-user/plan/create");
		
		// topup
		List<Topup> topups = this.planService.queryTopups();
		model.addAttribute("topups",topups);
		
		return "broadband-user/plan/plan";
	}
	
	@RequestMapping(value = "/broadband-user/plan/create", method = RequestMethod.POST)
	public String doPlanCreate(
			Model model,
			@ModelAttribute("plan") @Validated(PlanValidatedMark.class) Plan plan,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {

		model.addAttribute("panelheading", "Plan Create");
		model.addAttribute("action", "/broadband-user/plan/create");

		if (result.hasErrors()) {
			// topup
			List<Topup> topups = this.planService.queryTopups();
			model.addAttribute("topups",topups);
			return "broadband-user/plan/plan";
		}
		
		int count = this.planService.queryExistPlanByName(plan.getPlan_name());
		
		if (count > 0) {
			result.rejectValue("plan_name", "duplicate", "");
			// topup
			List<Topup> topups = this.planService.queryTopups();
			model.addAttribute("topups",topups);
			return "broadband-user/plan/plan";
		}
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		plan.setCreate_by(userSession.getId());
		plan.setCreate_date(new Date());
		
		this.planService.savePlan(plan);
		attr.addFlashAttribute("success", "Create Plan " + plan.getPlan_name() + " is successful.");

		return "redirect:/broadband-user/plan/view/1";
	}
	
	@RequestMapping(value = "/broadband-user/plan/view/{pageNo}")
	public String planView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Plan> page = new Page<Plan>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by plan_status desc, plan_type");
		this.planService.queryPlansByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/plan/plan-view";
	}
	
	@RequestMapping(value = "/broadband-user/plan/edit/{id}")
	public String toPlanEdit(Model model,
			@PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Plan Edit");
		model.addAttribute("action", "/broadband-user/plan/edit");
		
		Plan plan = this.planService.queryPlanById(id);
		
		if (plan.getPlan_topupid_array() != null && !"".equals(plan.getPlan_topupid_array())) {
			plan.setTopupArray(plan.getPlan_topupid_array().split(","));
		}
		
		model.addAttribute("plan", plan);
		
		// topup
		List<Topup> topups = this.planService.queryTopups();
		model.addAttribute("topups",topups);
		
		return "broadband-user/plan/plan";
	}
	
	
	@RequestMapping(value = "/broadband-user/plan/edit", method = RequestMethod.POST)
	public String doPlanEdit(
			Model model,
			@ModelAttribute("plan") @Validated(PlanValidatedMark.class) Plan plan,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Plan Edit");
		model.addAttribute("action", "/broadband-user/plan/edit");
		
		if (result.hasErrors()) {
			// topup
			List<Topup> topups = this.planService.queryTopups();
			model.addAttribute("topups",topups);
			return "broadband-user/plan/plan";
		}
		
		int count = this.planService.queryExistNotSelfPlanfByName(plan.getPlan_name(), plan.getId());
		
		if (count > 0) {
			result.rejectValue("plan_name", "duplicate", "");
			// topup
			List<Topup> topups = this.planService.queryTopups();
			model.addAttribute("topups",topups);
			return "broadband-user/plan/plan";
		}
		
		User userSession = (User)req.getSession().getAttribute("userSession");
		plan.setLast_update_by(userSession.getId());
		plan.setLast_update_date(new Date(System.currentTimeMillis()));
		plan.getParams().put("id", plan.getId());
		
		this.planService.editPlan(plan);
		
		attr.addFlashAttribute("success", "Edit Plan " + plan.getPlan_name() + " is successful.");

		return "redirect:/broadband-user/plan/view/1";
	}
	
	/*
	 * END PLAN AREA
	 */
	
	
	
	/*
	 * Hardware Controller begin
	 */
	
	@RequestMapping(value = "/broadband-user/plan/hardware/view/{pageNo}")
	public String hardwareView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Hardware> page = new Page<Hardware>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by hardware_status desc, hardware_type");
		this.planService.queryHardwaresByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/plan/hardware-view";
	}

	@RequestMapping(value = "/broadband-user/plan/hardware/create")
	public String toHardwareCreate(Model model) {

		model.addAttribute("hardware", new Hardware());
		model.addAttribute("panelheading", "Hardware Create");
		model.addAttribute("action", "/broadband-user/plan/hardware/create");
		
		return "broadband-user/plan/hardware";
	}

	@RequestMapping(value = "/broadband-user/plan/hardware/create", method = RequestMethod.POST)
	public String doHardwareCreate(
			Model model,
			@ModelAttribute("hardware") @Validated(HardwareValidatedMark.class) Hardware hardware,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Hardware Create");
		model.addAttribute("action", "/broadband-user/plan/hardware/create");

		if (result.hasErrors()) {
			return "broadband-user/plan/hardware";
		}
		
		
		this.planService.saveHardware(hardware);
		attr.addFlashAttribute("success", "Create Hardware " + hardware.getHardware_name() + " is successful.");

		return "redirect:/broadband-user/plan/hardware/view/1";
	}

	@RequestMapping(value = "/broadband-user/plan/hardware/edit/{id}")
	public String toHardwareEdit(Model model,
			@PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Hardware Edit");
		model.addAttribute("action", "/broadband-user/plan/hardware/edit");
		
		Hardware hardware = this.planService.queryHardwareById(id);
		
		model.addAttribute("hardware", hardware);
		
		return "broadband-user/plan/hardware";
	}

	@RequestMapping(value = "/broadband-user/plan/hardware/edit", method = RequestMethod.POST)
	public String doHardwareEdit(
			Model model,
			@ModelAttribute("hardware") @Validated(HardwareValidatedMark.class) Hardware hardware,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Hardware Edit");
		model.addAttribute("action", "/broadband-user/plan/hardware/edit");
		
		if (result.hasErrors()) {
			return "broadband-user/plan/hardware";
		}
		
		hardware.getParams().put("id", hardware.getId());
		
		this.planService.editHardware(hardware);
		
		attr.addFlashAttribute("success", "Edit Hardware " + hardware.getHardware_name() + " is successful.");
	
		return "redirect:/broadband-user/plan/hardware/view/1";
	}

	/*
	 * Hardware Controller end
	 */

}

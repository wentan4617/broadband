package com.tm.broadband.controller;

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

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.Topup;
import com.tm.broadband.model.User;
import com.tm.broadband.service.PlanService;
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
	
	
	@RequestMapping(value = "/broadband-user/plan/create")
	public String toPlanCreate(Model model) {

		model.addAttribute("plan", new Plan());
		model.addAttribute("panelheading", "Plan Create");
		model.addAttribute("action", "/broadband-user/plan/create");
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
			return "broadband-user/plan/plan";
		}
		
		int count = this.planService.queryExistPlanByName(plan.getPlan_name());
		
		if (count > 0) {
			result.rejectValue("plan_name", "duplicate", "");
			return "broadband-user/plan/plan";
		}
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		plan.setCreate_by(userSession);
		
		this.planService.savePlan(plan);
		attr.addFlashAttribute("success", "Create Plan " + plan.getPlan_name() + " is successful.");

		return "redirect:/broadband-user/plan/view/1";
	}
	
	@RequestMapping(value = "/broadband-user/plan/view/{pageNo}")
	public String planView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Plan> page = new Page<Plan>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by plan_type");
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
		
		model.addAttribute("plan", plan);
		
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
			return "broadband-user/plan/plan";
		}
		
		int count = this.planService.queryExistNotSelfPlanfByName(plan.getPlan_name(), plan.getId());
		
		if (count > 0) {
			result.rejectValue("plan_name", "duplicate", "");
			return "broadband-user/plan/plan";
		}
		
		User userSession = (User)req.getSession().getAttribute("userSession");
		plan.setLast_update_by(userSession);
		
		this.planService.editPlan(plan);
		
		attr.addFlashAttribute("success", "Edit Plan " + plan.getPlan_name() + " is successful.");

		return "redirect:/broadband-user/plan/view/1";
	}
	
	/*
	 * Topup Controller start
	 */
	
	@RequestMapping("/broadband-user/plan/topup/view/{pageNo}")
	public String topupView(Model model, @PathVariable("pageNo") int pageNo){
		Page<Topup> page = new Page<Topup>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by topup_status");
		this.planService.queryTopupsByPage(page);
		model.addAttribute("page",page);
		return "/broadband-user/plan/topup-view";
	}
	
	@RequestMapping("/broadband-user/plan/topup/create")
	public String toTopupCreate(Model model){
		model.addAttribute("topup", new Topup());
		model.addAttribute("panelheading", "Topup Create");
		model.addAttribute("action", "/broadband-user/plan/topup/create");
		return "/broadband-user/plan/topup";
	}
	
	@RequestMapping(value = "/broadband-user/plan/topup/create", method = RequestMethod.POST)
	public String doTopupCreate(
			Model model,
			@ModelAttribute("topup") @Validated(TopupValidatedMark.class) Topup topup,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Topup Create");
		model.addAttribute("action", "/broadband-user/plan/topup/create");

		if (result.hasErrors()) {
			return "broadband-user/plan/topup";
		}
		
		this.planService.saveTopup(topup);
		attr.addFlashAttribute("success", "Create Topup " + topup.getTopup_name() + " is successful.");

		return "redirect:/broadband-user/plan/topup/view/1";
	}
	
	@RequestMapping(value = "/broadband-user/plan/topup/edit/{id}")
	public String toTopupEdit(Model model,
			@PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Topup Edit");
		model.addAttribute("action", "/broadband-user/plan/topup/edit");
		
		Topup topup= this.planService.queryTopupById(id);
		
		model.addAttribute("topup", topup);
		
		return "broadband-user/plan/topup";
	}
	
	
	@RequestMapping(value = "/broadband-user/plan/topup/edit", method = RequestMethod.POST)
	public String doTopupEdit(
			Model model,
			@ModelAttribute("topup") @Validated(TopupValidatedMark.class) Topup topup,
			BindingResult result, HttpServletRequest req,
			RedirectAttributes attr) {
		
		model.addAttribute("panelheading", "Topup Edit");
		model.addAttribute("action", "/broadband-user/plan/topup/edit");
		
		if (result.hasErrors()) {
			return "broadband-user/plan/topup";
		}
		
		this.planService.editTopup(topup);
		
		attr.addFlashAttribute("success", "Edit Topup " + topup.getTopup_name() + " is successful.");

		return "redirect:/broadband-user/plan/topup/view/1";
	}
	
	/*
	 * Topup Controller end
	 */

}

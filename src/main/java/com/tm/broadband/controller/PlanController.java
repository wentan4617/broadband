package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;

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
	
	@RequestMapping(value = "/broadband-user/plan/view")
	public String toPlanView() {
		return "broadband-user/plan/plan-view";
	}
	
	@RequestMapping("/broadband-user/plan/view/redirect")
	public String redirectPlanView(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Plan Operation is successful.");
		return "redirect:/broadband-user/plan/view";
	}
	
	@RequestMapping(value = "/broadband-user/plan/create")
	public String toPlanCreate(Model model) {
		model.addAttribute("plan", new Plan());
		model.addAttribute("panelheading", "Plan Create");
		model.addAttribute("action", "/broadband-user/plan/create");
		return "broadband-user/plan/plan";
	}
	
	@RequestMapping(value = "/broadband-user/plan/edit/{id}")
	public String toPlanEdit(Model model, @PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Plan Edit");
		model.addAttribute("action", "/broadband-user/plan/edit");
		
		Plan plan = new Plan();
		plan.getParams().put("id", id);
		plan = this.planService.queryPlan(plan);
		
		model.addAttribute("plan", plan);
		
		return "broadband-user/plan/plan";
	}
	
	@RequestMapping(value = "/broadband-user/plan/pic/edit", method = RequestMethod.POST)
	public String doPlanPicEdit(Model model, @ModelAttribute("plan") Plan plan,
			BindingResult result, HttpServletRequest req,
			@RequestParam("imgs") MultipartFile[] imgs, RedirectAttributes attr) {

		String fileName = String.valueOf(plan.getId());
		String plan_pic_path = req.getSession().getServletContext().getRealPath("/public/upload");

		for (int i = 0; i < imgs.length; i++) {
			if (!imgs[i].isEmpty()) {
				fileName += i + imgs[i].getOriginalFilename();
				try {
					FileUtils.copyInputStreamToFile(imgs[i].getInputStream(), new File(plan_pic_path, fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (i == 0) {
					plan.setImg1(fileName);
				}
				if (i == 1) {
					plan.setImg2(fileName);
				}
				if (i == 2) {
					plan.setImg3(fileName);
				}
			}
			fileName = String.valueOf(plan.getId());
		}
		
		plan.getParams().put("id", plan.getId());
		
		this.planService.editPlan(plan);
		
		attr.addFlashAttribute("success", "Edit Plan Pic " + plan.getPlan_name() + " is successful.");

		return "redirect:/broadband-user/plan/view";
	}
	
	@RequestMapping(value = "/broadband-user/plan/delete", method = RequestMethod.POST)
	public String deletePlans(
			Model model, @RequestParam(value = "checkbox_plans", required = false) String[] plan_ids,
			HttpServletRequest req, RedirectAttributes attr) {

		if (plan_ids == null) {
			attr.addFlashAttribute("error", "Please choose one plan at least.");
			return "redirect:/broadband-user/plan/view";
		} else {
			List<Plan> plans = new ArrayList<Plan>();
			for (String id: plan_ids) {
				Plan plan = new Plan();
				plan.getParams().put("where", "delete_plan");
				plan.getParams().put("id", Integer.parseInt(id));
				plans.add(plan);
			}
			this.planService.removePlans(plans);
		}
		
		attr.addFlashAttribute("success", "Delete selected plan(s) successfully!");
		return "redirect:/broadband-user/plan/view";
	}

	@RequestMapping(value = "/broadband-user/plan/options/edit", method = RequestMethod.POST)
	public String changePlanOptionsById(
			Model model,
			@RequestParam(value = "checkbox_plans", required = false) String[] plan_ids,
			HttpServletRequest req, RedirectAttributes attr,
			@RequestParam("value") String value,
			@RequestParam("type") String type) {

		if (plan_ids == null) {
			attr.addFlashAttribute("error", "Please choose one plan at least.");
			return "redirect:/broadband-user/plan/view";
		} else {
			
			List<Plan> plans = new ArrayList<Plan>();
			for (String id: plan_ids) {
				Plan plan = new Plan();
				if ("plan-group".equals(type)) {
					plan.setPlan_group(value);
				} else if ("plan-class".equals(type)) {
					plan.setPlan_class(value);
				} else if ("plan-sort".equals(type)) {
					plan.setPlan_sort(value);
				} else if ("plan-type".equals(type)) {
					plan.setPlan_type(value);
				} else if ("plan-status".equals(type)) {
					plan.setPlan_status(value);
				}
				plan.getParams().put("where", "update_plan");
				plan.getParams().put("id", Integer.parseInt(id));
				plans.add(plan);
			}
			this.planService.editPlans(plans);
		}
		attr.addFlashAttribute("success", "Change selected plan(s) successfully!");
		return "redirect:/broadband-user/plan/view";
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
	
	@RequestMapping("/broadband-user/plan/hardware/view/redirect")
	public String redirectHardwareView(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Hardware Operation is successful.");
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

	@RequestMapping(value = "/broadband-user/plan/hardware/delete", method = RequestMethod.POST)
	public String deleteHardwaresById(Model model
			,@RequestParam(value = "checkbox_hardwares", required = false) String[] hardware_ids
			,HttpServletRequest req, RedirectAttributes attr){

		if (hardware_ids == null) {
			attr.addFlashAttribute("error", "Please choose one hardware at least.");
			return "redirect:/broadband-user/plan/hardware/view/1";
		} else {
			for (int i = 0; i < hardware_ids.length; i++) {
				this.planService.removeHardwareById(Integer.parseInt(hardware_ids[i]));
			}
		}
		
		attr.addFlashAttribute("success", "Delete selected hardware(s) successfully!");
		return "redirect:/broadband-user/plan/hardware/view/1";
	}
	
	@RequestMapping(value = "/broadband-user/plan/hardware/pic/edit", method = RequestMethod.POST)
	public String doHardwarePicEdit(Model model,
			@ModelAttribute("hardware") Hardware hardware,
			BindingResult result, HttpServletRequest req,
			@RequestParam("imgs") MultipartFile[] imgs, RedirectAttributes attr) {

		String fileName = String.valueOf(hardware.getId());
		String plan_pic_path = req.getSession().getServletContext()
				.getRealPath("/public/upload");

		for (int i = 0; i < imgs.length; i++) {
			if (!imgs[i].isEmpty()) {
				fileName += i + imgs[i].getOriginalFilename();
				try {
					FileUtils.copyInputStreamToFile(imgs[i].getInputStream(),
							new File(plan_pic_path, fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (i == 0) {
					hardware.setImg1(fileName);
				}
				if (i == 1) {
					hardware.setImg2(fileName);
				}
				if (i == 2) {
					hardware.setImg3(fileName);
				}
			}
			fileName = String.valueOf(hardware.getId());
		}

		hardware.getParams().put("id", hardware.getId());

		this.planService.editHardwarePic(hardware);

		attr.addFlashAttribute("success", "Edit Hardware Pic " + hardware.getHardware_name() + " is successful.");

		return "redirect:/broadband-user/plan/hardware/view";
	}

	/*
	 * Hardware Controller end
	 */

}

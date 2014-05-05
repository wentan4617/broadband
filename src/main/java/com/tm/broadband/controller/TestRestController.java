package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;

@RestController
public class TestRestController {

	private PlanService planService;

	@Autowired
	public TestRestController(PlanService planService) {
		this.planService = planService;
	}
	
	@RequestMapping(value = "/test/plan/view/page/{pageNo}")
	public Page<Plan> planView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Plan> page = new Page<Plan>();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		page.getParams().put("orderby", "order by plan_status desc, plan_type");
		this.planService.queryPlansByPage(page);

		return page;
	}

}

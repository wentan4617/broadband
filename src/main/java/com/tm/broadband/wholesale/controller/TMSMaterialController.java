package com.tm.broadband.wholesale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tm.broadband.model.TMSMaterial;
import com.tm.broadband.model.TMSMaterialCategory;
import com.tm.broadband.model.TMSMaterialGroup;
import com.tm.broadband.model.TMSMaterialType;
import com.tm.broadband.model.TMSWholesaler;
import com.tm.broadband.wholesale.service.TMSMaterialService;
import com.tm.broadband.wholesale.service.TMSSystemService;

@Controller
public class TMSMaterialController {

	private TMSMaterialService tmsMaterialService;
	private TMSSystemService tmsSystemService;

	@Autowired
	public TMSMaterialController(TMSMaterialService tmsMaterialService,
			TMSSystemService tmsSystemService) {
		this.tmsMaterialService = tmsMaterialService;
		this.tmsSystemService = tmsSystemService;
	}

	/**
	 * BEGIN TMSMaterial
	 */
	@RequestMapping("/broadband-wholesale/material/material-combo/view")
	public String toMaterialComboView(Model model){
		
		return "broadband-wholesale/material/material-combo";
	}
	
	@RequestMapping("/broadband-wholesale/material/create")
	public String toMaterialCreate(Model model) {
		
		List<TMSMaterialGroup> tmsmgs = this.tmsMaterialService.queryTMSMaterialGroup(new TMSMaterialGroup());
		List<TMSMaterialCategory> tmsmcs = this.tmsMaterialService.queryTMSMaterialCategory(new TMSMaterialCategory());
		
		TMSWholesaler tmswQuery = new TMSWholesaler();
		tmswQuery.getParams().put("where", "query_primary_wholesaler");
		model.addAttribute("tmsws", this.tmsSystemService.queryTMSWholesaler(tmswQuery));
		model.addAttribute("tmsmgs", tmsmgs);
		model.addAttribute("tmsmcs", tmsmcs);
		model.addAttribute("m", new TMSMaterial());
		
		return "broadband-wholesale/material/material";
	}
	
	@RequestMapping("/broadband-wholesale/material/edit/{id}")
	public String toMaterialEdit(Model model,
			@PathVariable("id") Integer id){
		
		List<TMSMaterialGroup> tmsmgs = this.tmsMaterialService.queryTMSMaterialGroup(new TMSMaterialGroup());
		
		// List material by id
		TMSMaterial tmsmQuery = new TMSMaterial();
		tmsmQuery.getParams().put("id", id);
		TMSMaterial tmsm = this.tmsMaterialService.queryTMSMaterial(tmsmQuery).get(0);
		
		// List group by group_name
		TMSMaterialGroup groupQuery = new TMSMaterialGroup();
		groupQuery.getParams().put("group_name", tmsm.getMaterial_group());
		TMSMaterialGroup group = this.tmsMaterialService.queryTMSMaterialGroup(groupQuery).get(0);
		
		// List types by group_id
		TMSMaterialType typeQuery = new TMSMaterialType();
		typeQuery.getParams().put("group_id", group.getId());
		List<TMSMaterialType> tmsmts = this.tmsMaterialService.queryTMSMaterialType(typeQuery);

		TMSWholesaler tmswQuery = new TMSWholesaler();
		tmswQuery.getParams().put("where", "query_primary_wholesaler");
		model.addAttribute("tmsws", this.tmsSystemService.queryTMSWholesaler(tmswQuery));
		model.addAttribute("tmsmcs", this.tmsMaterialService.queryTMSMaterialCategory(new TMSMaterialCategory()));
		model.addAttribute("tmsmgs", tmsmgs);
		model.addAttribute("tmsmts", tmsmts);
		model.addAttribute("m", tmsm);
		
		return "broadband-wholesale/material/material";
	}
	/**
	 * END TMSMaterial
	 */
	
}

package com.tm.broadband.wholesale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TMSCombo;
import com.tm.broadband.model.TMSMaterial;
import com.tm.broadband.model.TMSMaterialCategory;
import com.tm.broadband.model.TMSMaterialGroup;
import com.tm.broadband.model.TMSMaterialType;
import com.tm.broadband.wholesale.service.TMSMaterialService;

@RestController
public class TMSMaterialRestController {

	private TMSMaterialService tmsMaterialService;

	@Autowired
	public TMSMaterialRestController(TMSMaterialService tmsMaterialService) {
		this.tmsMaterialService = tmsMaterialService;
	}
	
	/**
	 * BEGIN TMSMaterialGroup
	 */	
	@RequestMapping(value="/broadband-wholesale/material/group-type/create", method=RequestMethod.POST)
	public JSONBean<String> doMaterialGroupCreate(Model model,
			@RequestParam("group_id") String group_id,
			@RequestParam("group_or_type") String group_or_type,
			@RequestParam("group_or_type_name") String group_or_type_name) {
		
		JSONBean<String> json = new JSONBean<String>();
		
		if("group".equals(group_or_type)){
			TMSMaterialGroup group = new TMSMaterialGroup();
			group.getParams().put("group_name", group_or_type_name);
			if(this.tmsMaterialService.queryTMSMaterialGroup(group)!=null && this.tmsMaterialService.queryTMSMaterialGroup(group).size() > 0){
				json.getErrorMap().put("alert-error", "Group name existed, can't be recreate!");
				return json;
			}
			group.setGroup_name(group_or_type_name);
			this.tmsMaterialService.createTMSMaterialGroup(group);
		} else if("type".equals(group_or_type)) {
			TMSMaterialType type = new TMSMaterialType();
			type.getParams().put("type_name", group_or_type_name);
			if(this.tmsMaterialService.queryTMSMaterialType(type)!=null && this.tmsMaterialService.queryTMSMaterialType(type).size() > 0){
				json.getErrorMap().put("alert-error", "Type name existed, can't be recreate!");
				return json;
			}
			type.setGroup_id(Integer.parseInt(group_id));
			type.setType_name(group_or_type_name);
			this.tmsMaterialService.createTMSMaterialType(type);
		} else {
			TMSMaterialCategory category = new TMSMaterialCategory();
			category.getParams().put("category_name", group_or_type_name);
			if(this.tmsMaterialService.queryTMSMaterialCategory(category)!=null && this.tmsMaterialService.queryTMSMaterialCategory(category).size() > 0){
				json.getErrorMap().put("alert-error", "Category name existed, can't be recreate!");
				return json;
			}
			category.setCategory_name(group_or_type_name);
			this.tmsMaterialService.createTMSMaterialCategory(category);
		}
		
		
		json.getSuccessMap().put("alert-success", "New Material "+group_or_type+" has just been created!");
		
		return json;
	}
	
	@RequestMapping(value="/broadband-wholesale/material/group-type/edit", method=RequestMethod.POST)
	public JSONBean<TMSMaterialType> doMaterialGroupTypeEdit(Model model,
			TMSMaterialType tmsmt) {
		
		JSONBean<TMSMaterialType> json = new JSONBean<TMSMaterialType>();
		
		this.tmsMaterialService.createTMSMaterialType(tmsmt);
		
		json.getSuccessMap().put("alert-success", "New Material Type has just been created!");
		
		return json;
	}

	@RequestMapping(value="/broadband-wholesale/material/groups/show")
	public JSONBean<TMSMaterialGroup> showMaterialGroups(Model model) {
		
		JSONBean<TMSMaterialGroup> json = new JSONBean<TMSMaterialGroup>();
		
		List<TMSMaterialGroup> groups = this.tmsMaterialService.queryTMSMaterialGroup(new TMSMaterialGroup());
		
		json.setModels(groups);
		
		return json;
	}
	/**
	 * END TMSMaterialGroup
	 */

	/**
	 * BEGIN TMSMaterialType
	 */
	@RequestMapping("/broadband-wholesale/material/type/show")
	public JSONBean<TMSMaterialType> showMaterialTypes(Model model,
			@RequestParam("group_id") Integer group_id) {
		
		JSONBean<TMSMaterialType> json = new JSONBean<TMSMaterialType>();
		
		TMSMaterialType type = new TMSMaterialType();
		type.getParams().put("group_id", group_id);
		
		List<TMSMaterialType> types = this.tmsMaterialService.queryTMSMaterialType(type);
		
		json.setModels(types);
		
		return json;
	}
	/**
	 * END TMSMaterialType
	 */

	
	/**
	 * BEGIN TMSMaterial
	 */
	
	@RequestMapping(value="/broadband-wholesale/material/update_create",method=RequestMethod.POST)
	public JSONBean<TMSMaterial> doMaterialCreate(Model model,
			TMSMaterial tmsm,
			@RequestParam("submit_type") String submit_type,
			@RequestParam("material_group_id") Integer material_group_id){
		
		JSONBean<TMSMaterial> json = new JSONBean<TMSMaterial>();
		
		if(tmsm.getName().trim().equals("")){
			
			if("All".equals(tmsm.getMaterial_group())){
				TMSMaterialGroup groupQuery = new TMSMaterialGroup();
				groupQuery.getParams().put("id", material_group_id);
				tmsm.setMaterial_group(this.tmsMaterialService.queryTMSMaterialGroup(groupQuery).get(0).getGroup_name());
			}
			
			if("update".equals(submit_type)){
				tmsm.getParams().put("id", tmsm.getId());
				this.tmsMaterialService.editTMSMaterial(tmsm);
			} else {
				this.tmsMaterialService.createTMSMaterial(tmsm);
			}
			
			json.getSuccessMap().put("alert-success", "Successfully "+("update".equals(submit_type)?"update current":"create new")+" material!");
			
		} else {

			json.getErrorMap().put("alert-error", "Please fill essential detail(s) for the material!");
			
		}
		
		return json;
	}
	
	@RequestMapping("/broadband-wholesale/material/view/{pageNo}")
	public Page<TMSMaterial> toMaterialView(Model model,
			@PathVariable("pageNo") Integer pageNo){
		
		Page<TMSMaterial> page = new Page<TMSMaterial>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY material_category ASC");
		
		this.tmsMaterialService.queryTMSMaterialByPage(page);
		
		return page;
	}
	
	@RequestMapping("/broadband-wholesale/material/combo/view/{pageNo}")
	public Page<TMSCombo> toComboView(Model model,
			@PathVariable("pageNo") Integer pageNo){
		
		Page<TMSCombo> page = new Page<TMSCombo>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		
		this.tmsMaterialService.queryTMSComboByPage(page);
		
		return page;
	}
	
	/**
	 * END TMSMaterial
	 */
	
}

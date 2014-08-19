package com.tm.broadband.wholesale.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.TMSWholesaler;
import com.tm.broadband.wholesale.service.TMSMaterialService;
import com.tm.broadband.wholesale.service.TMSSystemService;

@Controller
public class TMSSystemController {
	
	private TMSMaterialService tmsMaterialService;
	private TMSSystemService tmsSystemService;

	@Autowired
	public TMSSystemController(TMSMaterialService tmsMaterialService,
			TMSSystemService tmsSystemService) {
		this.tmsMaterialService = tmsMaterialService;
		this.tmsSystemService = tmsSystemService;
	}
	
	/**
	 * BEGIN Login
	 */

	@RequestMapping(value = { "/broadband-wholesale", "/broadband-wholesale/login" })
	public String userHome(Model model) {
		model.addAttribute("title", "TMS Broadband Whoesale System");
		return "broadband-wholesale/login";
	}

	@RequestMapping(value = "/broadband-wholesale/signout")
	public String signout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/broadband-wholesale/login";
	}
	
	@RequestMapping("/broadband-wholesale/index/redirect")
	public String redirectIndex(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Welcome to TMS Broadband Wholesale System.");
		return "redirect:/broadband-wholesale/index";
	}

	@RequestMapping(value = "/broadband-wholesale/index")
	public String wholesaleIndex(Model model,
			HttpServletRequest req) {
		
		return "broadband-wholesale/index";
	}
	
	/**
	 * BEGIN Login
	 */
	
	/**
	 * BEGIN Wholesaler
	 */

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/create")
	public String toWholesaleCreate(Model model) {
		
		TMSWholesaler wholesalerQuery = new TMSWholesaler();
		wholesalerQuery.getParams().put("where", "query_primary_wholesaler");

		model.addAttribute("primaryWholesalers", this.tmsSystemService.queryTMSWholesaler(wholesalerQuery));
		model.addAttribute("wholesaler", new TMSWholesaler());
		model.addAttribute("panelheading", "Wholesale Create");
		model.addAttribute("action", "/broadband-wholesale/system/wholesaler/create");

		return "broadband-wholesale/system/wholesaler";
	}

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/create", method=RequestMethod.POST)
	public String doWholesaleCreate(Model model,
			@ModelAttribute("wholesaler") TMSWholesaler wholesaler,
			RedirectAttributes attr,
			HttpServletRequest req) {
		
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < wholesaler.getAuthArray().length; i++) {
			buff.append(wholesaler.getAuthArray()[i]);
			if(i<wholesaler.getAuthArray().length-1){
				buff.append(",");
			}
		}
		
		// Set authorizations
		wholesaler.setAuth(String.valueOf(buff));
		
		TMSWholesaler wholesalerSession = (TMSWholesaler) req.getSession().getAttribute("wholesalerSession");
		
		// If not create by user then use current wholesaler's company name as new wholesaler's company name
		if(wholesalerSession!=null){
			wholesaler.setCompany_name(wholesalerSession.getCompany_name());
			wholesaler.setWholesaler_id(wholesalerSession.getId());
		} else {
			TMSWholesaler wholesalerQuery = new TMSWholesaler();
			wholesalerQuery.getParams().put("where", "query_primary_wholesaler_by_company_name");
			wholesalerQuery.getParams().put("company_name", wholesaler.getCompany_name());
			List<TMSWholesaler> wholesalers = this.tmsSystemService.queryTMSWholesaler(wholesalerQuery);
			if(wholesalers!=null && wholesalers.size()>0){
				wholesaler.setWholesaler_id(wholesalers.get(0).getId());
			}
		}
		
		this.tmsSystemService.createTMSWholesaler(wholesaler);

		attr.addFlashAttribute("success", "Successfully create new wholesaler account.");

		return "redirect:/broadband-wholesale/system/wholesaler/view";
	}

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/edit/{id}")
	public String toWholesaleEdit(Model model,
			@PathVariable("id") Integer id) {
		
		TMSWholesaler wholesaler = new TMSWholesaler();
		wholesaler.getParams().put("id", id);
		wholesaler = this.tmsSystemService.queryTMSWholesaler(wholesaler).get(0);
		
		// iterating auths of this wholesaler
		if (wholesaler.getAuth() != null && !"".equals(wholesaler.getAuth())) {
			wholesaler.setAuthArray(wholesaler.getAuth().split(","));
		}
		
		model.addAttribute("wholesaler", wholesaler);
		model.addAttribute("panelheading", "Wholesale Update");
		model.addAttribute("action", "/broadband-wholesale/system/wholesaler/edit");

		return "broadband-wholesale/system/wholesaler";
	}

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/edit", method=RequestMethod.POST)
	public String doWholesaleEdit(Model model,
			@ModelAttribute("wholesaler") TMSWholesaler wholesaler,
			RedirectAttributes attr) {
		
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < wholesaler.getAuthArray().length; i++) {
			buff.append(wholesaler.getAuthArray()[i]);
			if(i<wholesaler.getAuthArray().length-1){
				buff.append(",");
			}
		}
		
		wholesaler.setAuth(String.valueOf(buff));
		wholesaler.getParams().put("id", wholesaler.getId());
		
		this.tmsSystemService.editTMSWholesaler(wholesaler);

		attr.addFlashAttribute("success", "Successfully update wholesaler details.");

		return "redirect:/broadband-wholesale/system/wholesaler/view";
	}

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/view")
	public String toWholesaleView(Model model) {
		return "broadband-wholesale/system/wholesaler-view";
	}
	
	/**
	 * END Wholesaler
	 */

}

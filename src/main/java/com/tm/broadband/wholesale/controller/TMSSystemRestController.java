package com.tm.broadband.wholesale.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TMSWholesaler;
import com.tm.broadband.model.User;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.wholesale.service.TMSMaterialService;
import com.tm.broadband.wholesale.service.TMSSystemService;

@RestController
public class TMSSystemRestController {
	
	private TMSMaterialService tmsMaterialService;
	private TMSSystemService tmsSystemService;

	@Autowired
	public TMSSystemRestController(TMSMaterialService tmsMaterialService,
			TMSSystemService tmsSystemService) {
		this.tmsMaterialService = tmsMaterialService;
		this.tmsSystemService = tmsSystemService;
	}
	
	/**
	 * BEGIN Login
	 */
	
	@RequestMapping(value = "/broadband-wholesale/login", method = RequestMethod.POST)
	public JSONBean<TMSWholesaler> wholesaleLogin(
			TMSWholesaler wholesaler, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<TMSWholesaler> json = new JSONBean<TMSWholesaler>();
		json.setModel(wholesaler);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}
		
		wholesaler.getParams().put("login_name", wholesaler.getLogin_name());
		wholesaler.getParams().put("login_password", wholesaler.getLogin_password());
		TMSWholesaler wholesalerSession = null;
		List<TMSWholesaler> wholesalers = this.tmsSystemService.queryTMSWholesaler(wholesaler);
		
		if(wholesalers!=null && wholesalers.size()>0){
			wholesalerSession = this.tmsSystemService.queryTMSWholesaler(wholesaler).get(0);
		}

		if (wholesalerSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}
		
		String url = "/broadband-wholesale/index/redirect";
		
		req.getSession().setAttribute("wholesalerSession", wholesalerSession);
		json.setUrl(url);

		return json;
	}
	
	/**
	 * END Login
	 */
	
	/**
	 * BEGIN Wholesaler
	 */

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/view/{pageNo}")
	public Page<TMSWholesaler> toWholesaleView(Model model,
			@PathVariable("pageNo") Integer pageNo,
			HttpServletRequest req) {
		
		Page<TMSWholesaler> page = new Page<TMSWholesaler>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		if(user==null){
			TMSWholesaler wholesalerSession = (TMSWholesaler) req.getSession().getAttribute("wholesalerSession");
			if(wholesalerSession.getWholesaler_id()==null){
				page.getParams().put("where", "query_by_wholesaler_id");
				page.getParams().put("id", wholesalerSession.getId());
			} else {
				page.getParams().put("id", wholesalerSession.getId());
			}
		}
		
		this.tmsSystemService.queryTMSWholesalerByPage(page);

		return page;
	}

	@RequestMapping(value = "/broadband-wholesale/system/wholesaler/remove/{id}", method=RequestMethod.POST)
	public JSONBean<TMSWholesaler> doWholesaleRemove(Model model,
			@PathVariable("id") Integer id,
			RedirectAttributes attr) {
		
		JSONBean<TMSWholesaler> json = new JSONBean<TMSWholesaler>();
		
		this.tmsSystemService.removeTMSWholesalerById(id);
		
		json.getSuccessMap().put("alert-success", "Successfully remove wholesaler account.");

		return json;
	}
	
	/**
	 * END Wholesaler
	 */

}

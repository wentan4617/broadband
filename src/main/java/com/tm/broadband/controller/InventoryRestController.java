package com.tm.broadband.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Equip;
import com.tm.broadband.model.EquipLog;
import com.tm.broadband.model.EquipPattern;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.InventoryService;
import com.tm.broadband.service.SystemService;

@RestController
public class InventoryRestController {
	
	private InventoryService inventoryService;
	private CRMService crmService;
	private SystemService systemService;

	@Autowired
	public InventoryRestController(InventoryService inventoryService,
			CRMService crmService,
			SystemService systemService) {
		this.inventoryService = inventoryService;
		this.crmService = crmService;
		this.systemService = systemService;
	}
	
	
	/**
	 * BEGIN Equip
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/view/{pageNo}/{equip_status}")
	public Page<Equip> equipView(Model model,
			@PathVariable("pageNo") int pageNo,
			@PathVariable("equip_status") String equip_status){
		
		Page<Equip> page = new Page<Equip>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		if(!"all".equals(equip_status)){
			page.getParams().put("equip_status", equip_status);
		}
		page.getParams().put("orderby", "order by warehousing_date desc");
		this.inventoryService.queryEquipsByPage(page);
		
		List<Equip> es = page.getResults();
		for (Equip equip : es) {
			
			if(equip.getOrder_detail_id()!=null && !"".equals(equip.getOrder_detail_id())){
				CustomerOrderDetail codQuery = new CustomerOrderDetail();
				codQuery.getParams().put("id", equip.getOrder_detail_id());
				codQuery = this.crmService.queryCustomerOrderDetail(codQuery);
				equip.setOrder_id(codQuery.getOrder_id());
				CustomerOrder coQuery = new CustomerOrder();
				coQuery.getParams().put("id", codQuery.getOrder_id());
				coQuery = this.crmService.queryCustomerOrder(coQuery);
				Customer cQuery = new Customer();
				cQuery.getParams().put("id", coQuery.getCustomer_id());
				cQuery = this.crmService.queryCustomer(cQuery);
				equip.setCustomer_id(cQuery.getId());
			}
			
		}
		
		return page;
	}
	
	@RequestMapping("/broadband-user/inventory/equip/create")
	public JSONBean<Equip> equipCreate(Model model,
			Equip equip){
		
		JSONBean<Equip> json = new JSONBean<Equip>();
		
		Equip equipQuery = new Equip();
		equipQuery.getParams().put("equip_sn", equip.getEquip_sn());
		equipQuery = this.inventoryService.queryEquip(equipQuery);
		if(equipQuery!=null){
			
			json.getErrorMap().put("alert-error", "Equip SN Duplicate!");
			
		} else {
			
			equip.setEquip_status("inactive");
			equip.setWarehousing_date(new Date());
			this.inventoryService.createEquip(equip);
			
			json.getSuccessMap().put("alert-success", "New Equipment Created!");
			
		}
		
		return json;
	}
	
	@RequestMapping("/broadband-user/inventory/equip/remove")
	public JSONBean<Equip> equipRemove(Model model,
			Equip equip,
			HttpServletRequest req){
		
		JSONBean<Equip> json = new JSONBean<Equip>();
		
		equip.getParams().put("id", equip.getId());
		equip = this.inventoryService.queryEquip(equip);
		
		EquipLog equipLog = new EquipLog();
		equipLog.setEquip_id(equip.getId());
		equipLog.setLog_date(new Date());
		equipLog.setOper_type("DELETED");
		String preDelete = "<strong>DELETED=></strong><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;Equipment Detail:</b> [name="+equip.getEquip_name()+"] - [type="+equip.getEquip_type()+"] - [purpose="+equip.getEquip_purpose()+"] - [sn="+equip.getEquip_sn()+"] - [status="+equip.getEquip_status()+"].";
		equipLog.setLog_desc(preDelete);
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		equipLog.setUser_id(userSession.getId());
		this.inventoryService.createEquipLog(equipLog);

		this.inventoryService.removeEquipById(equip.getId());

		
		if(equip.getOrder_detail_id()!=null && !"".equals(equip.getOrder_detail_id())){
			
			CustomerOrderDetail codUpdate = new CustomerOrderDetail();
			codUpdate.getParams().put("id", equip.getOrder_detail_id());
			codUpdate.getParams().put("equip_id_null", true);
			this.crmService.editCustomerOrderDetail(codUpdate);
			
		}
		
		json.getSuccessMap().put("alert-success", "Specific Equipment Removed!");
		
		return json;
	}
	
	/**
	 * END Equip
	 */
	
	
	/**
	 * BEGIN Equip Pattern
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/view/{pageNo}")
	public Page<EquipPattern> equipPatternView(Model model,
			@PathVariable("pageNo") int pageNo){
		
		Page<EquipPattern> page = new Page<EquipPattern>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		this.inventoryService.queryEquipPatternsByPage(page);
		
		return page;
	}
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/create")
	public JSONBean<EquipPattern> equipPatternCreate(Model model,
			EquipPattern equipPattern){
		
		JSONBean<EquipPattern> json = new JSONBean<EquipPattern>();
		this.inventoryService.createEquipPattern(equipPattern);
		
		json.getSuccessMap().put("alert-success", "New Equipment Pattern Created!");
		
		return json;
	}
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/remove")
	public JSONBean<EquipPattern> equipPatternRemove(Model model,
			EquipPattern equipPattern,
			HttpServletRequest req){
		
		JSONBean<EquipPattern> json = new JSONBean<EquipPattern>();
		this.inventoryService.removeEquipPatternById(equipPattern.getId());
		
		json.getSuccessMap().put("alert-success", "Specific Equipment Pattern Removed!");
		
		return json;
	}
	
	@RequestMapping(value="/broadband-user/inventory/equip/pattern/get", method=RequestMethod.GET)
	public List<EquipPattern> equipPatternGet(){
		
		List<EquipPattern> equipPatterns = this.inventoryService.queryEquipPatterns(new EquipPattern());
		
		return equipPatterns;
	}
	
	/**
	 * END Equip Pattern
	 */
	
	
	/**
	 * BEGIN Equip Log
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/log/view/{pageNo}")
	public Map<String, Object> equipLogView(Model model,
			@PathVariable("pageNo") int pageNo){
		
		Page<EquipLog> page = new Page<EquipLog>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "order by log_date desc");
		this.inventoryService.queryEquipLogsByPage(page);
		
		List<User> users = this.systemService.queryUser();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("users", users);
		resultMap.put("page", page);
		
		return resultMap;
	}

	/**
	 * END Equip Log
	 */
	
	
	
}

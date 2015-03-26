package com.tm.broadband.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Equip;
import com.tm.broadband.model.EquipLog;
import com.tm.broadband.model.EquipPattern;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.InventoryService;
import com.tm.broadband.service.SystemService;

@Controller
public class InventoryController {
	
	private InventoryService inventoryService;
	private CRMService crmService;
	private SystemService systemService;

	@Autowired
	public InventoryController(InventoryService inventoryService,
			CRMService crmService,
			SystemService systemService) {
		this.inventoryService = inventoryService;
		this.crmService = crmService;
		this.systemService = systemService;
	}

	
	/**
	 * BEGIN Equip
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/view")
	public String toEquipView(Model model){
		model.addAttribute("equipActive", "active");
		
		Page<Equip> page = new Page<Equip>();
		int allSum = this.inventoryService.queryEquipsSumByPage(page);
		page.getParams().put("equip_status", "inactive");
		int inactiveSum = this.inventoryService.queryEquipsSumByPage(page);
		page.getParams().put("equip_status", "dispatched");
		int dispatchedSum = this.inventoryService.queryEquipsSumByPage(page);
		page.getParams().put("equip_status", "retrieved");
		int retrievedSum = this.inventoryService.queryEquipsSumByPage(page);
		
		model.addAttribute("allSum", allSum);
		model.addAttribute("inactiveSum", inactiveSum);
		model.addAttribute("dispatchedSum", dispatchedSum);
		model.addAttribute("retrievedSum", retrievedSum);
		
		return "broadband-user/inventory/equip-view";
	}
	
	@RequestMapping("/broadband-user/inventory/equip/edit/{equip_id}")
	public String toEquipEdit(Model model,
			@PathVariable("equip_id") Integer equip_id){
		
		Equip equipQuery = new Equip();
		equipQuery.getParams().put("id", equip_id);
		equipQuery = this.inventoryService.queryEquip(equipQuery);
		
		if(equipQuery.getOrder_detail_id()!=null && !"".equals(equipQuery.getOrder_detail_id())){
			
			CustomerOrderDetail codQuery = new CustomerOrderDetail();
			codQuery.getParams().put("id", equipQuery.getOrder_detail_id());
			codQuery = this.crmService.queryCustomerOrderDetail(codQuery);
			CustomerOrder coQuery = new CustomerOrder();
			coQuery.getParams().put("id", codQuery.getOrder_id());
			coQuery = this.crmService.queryCustomerOrder(coQuery);
			if(coQuery!=null){
				equipQuery.setOrder_id(coQuery.getId());
				equipQuery.setCustomer_id(coQuery.getCustomer_id());
			}
			
		}
		
		EquipLog equipLogQuery = new EquipLog();
		equipLogQuery.getParams().put("orderby", "order by log_date desc");
		equipLogQuery.getParams().put("equip_id", equip_id);
		List<EquipLog> equipLogs = this.inventoryService.queryEquipLogs(equipLogQuery);
		
		List<User> users = this.systemService.queryUser();
		
		model.addAttribute("equip", equipQuery);
		model.addAttribute("equipLogs", equipLogs);
		model.addAttribute("users", users);
		
		return "broadband-user/inventory/equip";
	}
	
	@RequestMapping("/broadband-user/inventory/equip/update")
	public String equipUpdate(Model model,
			@ModelAttribute("equip") Equip equip,
			HttpServletRequest req,
			RedirectAttributes attr){
		
		Equip equipQuery = new Equip();
		equipQuery.getParams().put("id", equip.getId());
		equipQuery = this.inventoryService.queryEquip(equipQuery);
		
		equip.getParams().put("id", equip.getId());
		this.inventoryService.editEquip(equip);
		
		
		EquipLog equipLog = new EquipLog();
		equipLog.setEquip_id(equip.getId());
		equipLog.setLog_date(new Date());
		equipLog.setOper_type("UPDATED");
		String postUpdate = "<strong>UPDATED=></strong><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;Previous:</b> [name="+equip.getEquip_name()+"] - [type="+equip.getEquip_type()+"] - [purpose="+equip.getEquip_purpose()+"] - [sn="+equip.getEquip_sn()+"] - [status="+equip.getEquip_status()+"].";
		String preUpdate = "<br/><b>&nbsp;&nbsp;&nbsp;&nbsp;Current:</b> [name="+equipQuery.getEquip_name()+"] - [type="+equipQuery.getEquip_type()+"] - [purpose="+equipQuery.getEquip_purpose()+"] - [sn="+equipQuery.getEquip_sn()+"] - [status="+equipQuery.getEquip_status()+"].";
		equipLog.setLog_desc(postUpdate + preUpdate);
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		equipLog.setUser_id(userSession.getId());
		this.inventoryService.createEquipLog(equipLog);
		

		attr.addFlashAttribute("equipActive", "active");
		attr.addFlashAttribute("success", "Specific Equipment Updated!");

		return "redirect:/broadband-user/inventory/equip/view";
	}
	
	@RequestMapping("/broadband-user/inventory/equip/delete/{equip_id}")
	public String equipUpdate(Model model,
			@PathVariable("equip_id") Integer equip_id,
			HttpServletRequest req,
			RedirectAttributes attr){
		

		Equip equipQuery = new Equip();
		equipQuery.getParams().put("id", equip_id);
		equipQuery = this.inventoryService.queryEquip(equipQuery);
		
		this.inventoryService.removeEquipById(equip_id);
		
		EquipLog equipLog = new EquipLog();
		equipLog.setEquip_id(equip_id);
		equipLog.setLog_date(new Date());
		equipLog.setOper_type("DELETED");
		String postDelete = "<strong>DELETED=></strong><br/><b>&nbsp;&nbsp;&nbsp;&nbsp;Equipment Detail:</b> [name="+equipQuery.getEquip_name()+"] - [type="+equipQuery.getEquip_type()+"] - [purpose="+equipQuery.getEquip_purpose()+"] - [sn="+equipQuery.getEquip_sn()+"] - [status="+equipQuery.getEquip_status()+"].";
		equipLog.setLog_desc(postDelete);
		
		User userSession = (User) req.getSession().getAttribute("userSession");
		equipLog.setUser_id(userSession.getId());
		this.inventoryService.createEquipLog(equipLog);
		
		if(equipQuery.getOrder_detail_id()!=null && !"".equals(equipQuery.getOrder_detail_id())){
			
			CustomerOrderDetail codUpdate = new CustomerOrderDetail();
			codUpdate.getParams().put("id", equipQuery.getOrder_detail_id());
			codUpdate.getParams().put("equip_id_null", true);
			this.crmService.editCustomerOrderDetail(codUpdate);
			
		}

		attr.addFlashAttribute("equipActive", "active");
		attr.addFlashAttribute("success", "Specific Equipment Removed!");

		return "redirect:/broadband-user/inventory/equip/view";
	}
	
	/**
	 * END Equip
	 */

	
	/**
	 * BEGIN Equip Log
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/log/view")
	public String toEquipLogView(Model model){
		return "broadband-user/inventory/equip-log-view";
	}
	
	/**
	 * END Equip Log
	 */

	
	/**
	 * BEGIN Equip Pattern
	 */
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/edit/{equip_pattern_id}")
	public String toEquipPatternEdit(Model model,
			@PathVariable("equip_pattern_id") Integer equip_pattern_id){
		
		EquipPattern equipPatternQuery = new EquipPattern();
		equipPatternQuery.getParams().put("id", equip_pattern_id);
		equipPatternQuery = this.inventoryService.queryEquipPattern(equipPatternQuery);
		
		model.addAttribute("equipPattern", equipPatternQuery);
		
		return "broadband-user/inventory/equip-pattern";
	}
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/update")
	public String equipPatternUpdate(Model model,
			@ModelAttribute("equipPattern") EquipPattern equipPattern,
			HttpServletRequest req,
			RedirectAttributes attr){
		
		equipPattern.getParams().put("id", equipPattern.getId());
		this.inventoryService.editEquipPattern(equipPattern);

		attr.addFlashAttribute("equipPatternActive", "active");
		attr.addFlashAttribute("success", "Specific Equipment Pattern Updated!");

		return "redirect:/broadband-user/inventory/equip/view";
	}
	
	@RequestMapping("/broadband-user/inventory/equip/pattern/delete/{equip_id}")
	public String equipPatternUpdate(Model model,
			@PathVariable("equip_pattern_id") Integer equip_pattern_id,
			RedirectAttributes attr){
		
		
		this.inventoryService.removeEquipPatternById(equip_pattern_id);

		attr.addFlashAttribute("equipPatternActive", "active");
		attr.addFlashAttribute("success", "Specific Equipment Pattern Removed!");

		return "redirect:/broadband-user/inventory/equip/view";
	}
	
	/**
	 * END Equip Pattern
	 */
	
	
	
}
